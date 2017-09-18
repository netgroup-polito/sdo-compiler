package it.polito.netgroup.selforchestratingservices.compiler.model.json.event;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;

public class StateDescription
{
	public enum TYPE
	{
		polling,
		internal,
	};
	
	@JsonProperty("id")
	public String id;
	@JsonProperty("type")
	public TYPE type;
	@JsonProperty("check_every")
	public String check_every;
	@JsonProperty("check_method")
	public String check_method;
	@JsonProperty("check_type")
	public String check_type;
	@JsonProperty("param_type")
	public String param_type;
	@JsonProperty("check_on")
	public List<EventCheckOnDescription> check_on;

	
	private String firstCharUpper(String string)
	{
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);		
	}


	private String getJavaType()
	{
		return firstCharUpper(check_type)+"<"+param_type+">";
	}

	public String getJavaInit(SelfOrchestratorModel model ,int tabs)
	{
		String java = "";
	
		for(EventCheckOnDescription on : check_on)
		{
			if ( on.type != null)
			{
				java += TabGenerators.genTabs(tabs)+"if ( ! (resource instanceof "+on.type+"))\n" +
						TabGenerators.genTabs(tabs)+"{\n" +
						TabGenerators.genTabs(tabs)+"\tthrow new InvalidParameterException(\"Resource is not 'NatInfrastructureResource'\");\n" +
						TabGenerators.genTabs(tabs)+"}\n" +
						TabGenerators.genTabs(tabs)+on.type+" r = ("+on.type+") resource;\n" +
						TabGenerators.genTabs(tabs)+"framework.addDirtyChecker(new "+firstCharUpper(check_type)+"DirtyChecker<"+on.type+","+param_type+">(\""+id+"\",r,\n" +
						TabGenerators.genTabs(tabs)+"\t\t\tnew DirtyExecute<"+getJavaType()+">() {\n" +
						TabGenerators.genTabs(tabs)+"\t\t\t\t@Override\n" +
						TabGenerators.genTabs(tabs)+"\t\t\t\tpublic "+getJavaType()+" execute() throws Exception {\n" +
						TabGenerators.genTabs(tabs)+"\t\t\t\t\treturn r."+check_method+"();\n" +
						TabGenerators.genTabs(tabs)+"\t\t\t\t}\n" +
						TabGenerators.genTabs(tabs)+"\t\t\t},\""+check_every+"\", new DirtyCheckerEventHandler<"+on.type+","+param_type+">()\n" +
						TabGenerators.genTabs(tabs)+"{\n" + 
						TabGenerators.genTabs(tabs)+"\n" + 
						TabGenerators.genTabs(tabs)+"\t@Override\n" + 
						TabGenerators.genTabs(tabs)+"\tpublic boolean on_new("+on.type+" object, "+param_type+" value) throws Exception\n" +
						TabGenerators.genTabs(tabs)+"\t{\n";

				boolean looped = false;
				for( EventDescription v : model.getEventsOn(id))
				{
					if (v.type.equals(EventDescription.TYPE.NEW))
					{
						java += TabGenerators.genTabs(tabs)+"\t\treturn "+v.getJavaClassName("")+".fire(var,object,value);\n";
						looped=true;
					}
				}

				if(!looped)
				{
					java +=	TabGenerators.genTabs(tabs)+"\t\treturn false;\n";
				}

				java +=	TabGenerators.genTabs(tabs)+"\t}\n" + 
						TabGenerators.genTabs(tabs)+"\tpublic boolean on_del("+on.type+" object, "+param_type+" value) throws Exception\n" +
						TabGenerators.genTabs(tabs)+"\t{\n";

				looped = false;
				for( EventDescription v : model.getEventsOn(id))
				{
					if (v.type.equals(EventDescription.TYPE.DEL))
					{
						java += TabGenerators.genTabs(tabs)+"\t\treturn "+v.getJavaClassName("")+".fire(var,object,value);\n";
						looped=true;
					}
				}
				if(!looped)
				{
					java +=	TabGenerators.genTabs(tabs)+"\t\treturn false;\n";
				}

				java += TabGenerators.genTabs(tabs)+"\t}\n" + 
						TabGenerators.genTabs(tabs)+"\t@Override\n" + 
						TabGenerators.genTabs(tabs)+"\tpublic boolean on_change("+on.type+" object, "+param_type+" old, "+param_type+" now) throws Exception\n" +
						TabGenerators.genTabs(tabs)+"\t{\n";

				looped = false;
				for( EventDescription v : model.getEventsOn(id))
				{
					if (v.type.equals(EventDescription.TYPE.CHANGE))
					{
						java += TabGenerators.genTabs(tabs)+"\t\t\treturn "+v.getJavaClassName("")+".fire(var,object,old,now);\n";
						looped=true;
					}
				}
				if(!looped)
				{
					java +=	TabGenerators.genTabs(tabs)+"\t\treturn false;\n";
				}

				java += TabGenerators.genTabs(tabs)+"\t}\n" + 
						TabGenerators.genTabs(tabs)+"}));\n"; 
			}
		}
			return java;
	}
}
