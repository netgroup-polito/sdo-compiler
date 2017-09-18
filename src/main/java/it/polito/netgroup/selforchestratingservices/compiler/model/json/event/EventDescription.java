package it.polito.netgroup.selforchestratingservices.compiler.model.json.event;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaClass;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.PackageGenerator;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.VariableDescription;

public class EventDescription implements GenerateJavaClass
{
	public enum TYPE
	{
		NEW,
		DEL,
		CHANGE,
	};
	
	@JsonProperty("id")
	public String id;
	@JsonProperty("type")
	public TYPE type;
	@JsonProperty("on")
	public String on;
	
	@JsonProperty("params")
	public List<EventParameterDescription> params;
	@JsonProperty("actions")
	public List<String> actions;
	
	
	@Override
	public String getJavaClassName(String prefix)
	{
		String className = prefix+id;

		return Character.toUpperCase(className.charAt(0)) + className.substring(1) +"EventHandler";
	}
	@Override
	public String getJavaClass(String prefix, SelfOrchestratorModel model, String pack)
	{
		String java = "package "+pack+";\n" + 
				"\n" +
				PackageGenerator.getPackage() +
				"\n" + 
				"public class "+getJavaClassName(prefix)+" \n" +
				"{\n" +
				"\tpublic static boolean fire(Variables var,";
		for(EventParameterDescription p : params)
		{
			java += p.type+" "+p.name+", ";
		}
		java = java.substring(0, java.length()-2);
		
		java +=	") throws Exception\n" +
				"\t{\n";
			
		for(VariableDescription var : model.variables)
		{
			java +=  var.getJavaCodeUsage(2)+"\n";
		}

		for(String action : actions)
		{
			java += model.getAction(action).getJavaCode(true,2, model)+"\n";
		}
		if ( actions.size() > 0 )
		{
			java += "\t\treturn true;\n";
		}
		else
		{
			java += "\t\treturn false;\n";
		}



		java +=	"\t}\n" + 
				"\n" + 
				"}";
		
		return java;
	}	
}
