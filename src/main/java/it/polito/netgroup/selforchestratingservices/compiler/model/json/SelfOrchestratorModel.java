package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.event.EventCheckOnDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.event.EventDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.event.StateDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.infraEvent.InfraEventsDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelfOrchestratorModel implements GenerateJavaClass
{
	@JsonProperty("name")
	public String name;
	@JsonProperty("types")
	public List<VariableTypeDescription> types;
	@JsonProperty("variables")
	public List<VariableDescription> variables;
	@JsonProperty("state")
	public List<StateDescription> states;
	@JsonProperty("events")
	public List<EventDescription> events;
	@JsonProperty("infra_events")
	public InfraEventsDescription infraEvents;
	@JsonProperty("actions")
	public List<ActionDescription> actions;
	@JsonProperty("elementary_services")
	public List<ElementaryServiceDescription> elementaryServices;
	@JsonProperty("templates")
	public List<ResourceTemplateDescription> templates;
	@JsonProperty("default_nffg_filename")
	public String default_nffg_filename;
	
	
	@Override
	public String getJavaClassName(String prefix)
	{
		//String className = prefix+name;
		//return Character.toUpperCase(className.charAt(0)) + className.substring(1) +"SelfOrchestrator";
		return "MySelfOrchestrator";
	}
	
	
	@Override
	public String getJavaClass(String prefix, SelfOrchestratorModel model, String pack)
	{
		String java = "package "+pack+";\n" + 
				"\n" +
				PackageGenerator.getPackage() +
				"\n" + 
				"//Autogenerated method\n" + 
				"public class "+getJavaClassName(prefix)+" extends AbstractSelfOrchestrator\n" + 
				"{\n";

		for(ElementaryServiceDescription elementaryService : elementaryServices)
		{
			java = java + "\t" + elementaryService.getJavaClassName("") + " " + elementaryService.getJavaClassName("").toLowerCase() + ";\n";
		}
		
		java += "\tpublic "+getJavaClassName(prefix)+"(Framework framework)\n" +
				"\t{\n"+
				"\t\tsuper(framework,\""+default_nffg_filename+"\");\n";
		
		for(VariableDescription var : variables) {
			java += "\t\t" + var.getJavaCodeInit(0,model);
			java += "\t\t" + "variables.setVar(\"" + var.name + "\", " + var.name + ");\n";

			StateDescription state = model.getStateForVariable(var.name);
			if ( state != null)
			{
				java += "\t\t" + var.name + ".setEventHandler(new DirtyCheckerEventHandler<"+var.key_of+", "+var.value_of+">() {\n" +
							"\t\t\t@Override\n" +
							"\t\t\tpublic boolean on_new("+var.key_of+" object, "+var.value_of+" value) throws Exception\n" +
							"\t\t\t{\n";

				boolean looped = false;
				for( EventDescription v : model.getEventsOn(state.id))
				{
					if (v.type.equals(EventDescription.TYPE.NEW))
					{
						java += "\t\t\t\treturn "+v.getJavaClassName("")+".fire(variables,object,value);\n";
						looped=true;
					}
				}
				if(!looped)
				{
					java +=	"\t\t\t\treturn false;\n";
				}

				java +=	"\t\t\t}\n" +
						"\t\t\t@Override\n" +
						"\t\t\tpublic boolean on_del("+var.key_of+" object, "+var.value_of+" value)  throws Exception\n" +
						"\t\t\t{\n";

				looped = false;
				for( EventDescription v : model.getEventsOn(state.id))
				{
					if (v.type.equals(EventDescription.TYPE.DEL))
					{
						java += "\t\t\t\treturn "+v.getJavaClassName("")+".fire(variables,object,value);\n";
						looped=true;
					}
				}
				if(!looped)
				{
					java +=	"\t\t\t\treturn false;\n";
				}

				java += "\t\t\t}\n" +
						"\t\t\t@Override\n" +
						"\t\t\tpublic boolean on_change("+var.key_of+" object, "+var.value_of+" old, "+var.value_of+" now) throws Exception\n" +
						"\t\t\t{\n";

				looped = false;
				for( EventDescription v : model.getEventsOn(state.id))
				{
					if (v.type.equals(EventDescription.TYPE.CHANGE))
					{
						java += "\t\t\t\t\treturn "+v.getJavaClassName("")+".fire(variables,object,old,now);\n";
						looped=true;
					}
				}
				if(!looped) {
					java += "\t\t\t\treturn false;\n";
				}
				java += "\t\t\t}\n";
				java += "\t\t});\n";

				java += "\t\tframework.addDirtyChecker("+var.name+");\n";
			}

			java += "\t\t\n";
		}
		java += "\n";

		for(ElementaryServiceDescription elementaryService : elementaryServices)
		{
			java += "\t\t" + elementaryService.getJavaClassName("").toLowerCase() + "  = new " + elementaryService.getJavaClassName("") + "(variables);\n";
		}

		for(ElementaryServiceDescription elementaryService : elementaryServices)
		{
			java += "\t\telementaryServices.put(" + elementaryService.getJavaClassName("").toLowerCase() + ".getName()," + elementaryService.getJavaClassName("").toLowerCase() + ");\n";
		}

		java += "\t\tinfrastructureEventHandler = new MyInfrastructureEventHandler(variables);\n" +
				"\t\tname=\"NAT_LB\";\n" +
				"\t}\n"+
				"}\n";
		
		return java;
	}

	private StateDescription getStateForVariable(String name) {
		for(StateDescription state: states)
		{
			if (state.type == StateDescription.TYPE.internal)
			{
				for(EventCheckOnDescription check_on : state.check_on)
				{
					if ( check_on.name.equals(name))
					{
						return state;
					}
				}
			}
		}
		return null;
	}


	public ActionDescription getAction(String action_name)
	{
		for(ActionDescription action: actions)
		{
			if ( action.id.equals(action_name))
			{
				return action;
			}
		}
		throw new InvalidParameterException("Invalid action with name '"+action_name+"'");
	}


	public List<StateDescription> getStateForType(String type)
	{
		List<StateDescription> ret = new ArrayList<>();
		
		for(StateDescription state : states )
		{
			for(EventCheckOnDescription on: state.check_on)
			{
				if ( on.type != null && on.type.equals(type))
				{
					ret.add(state);
				}
			}
		}
			
		return ret;
	}


	public List<EventDescription> getEventsOn(String state_id)
	{
		List<EventDescription> ret = new ArrayList<>();
		
		for(EventDescription event : events)
		{
			if ( event.on.equals(state_id))
			{
				ret.add(event);
			}
		}
		return ret;
	}

	public ResourceTemplateDescription getTemplateByName(String template_name) {
		for(ResourceTemplateDescription template: templates)
		{
			if ( template.id.equals(template_name))
			{
				return template;
			}
		}
		throw new InvalidParameterException("Invalid resource template with name '"+template_name+"'");
	}

	public List<ResourceTemplateDescription> getTemplates() {
		return templates;
	}
}
