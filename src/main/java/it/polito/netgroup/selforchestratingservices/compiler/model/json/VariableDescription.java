package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class VariableDescription
{
	@JsonProperty("name")
	public String name;
	@JsonProperty("type")
	public String type;
	@JsonProperty("key_of")
	public String key_of;
	@JsonProperty("value_of")
	public String value_of;
	@JsonProperty("list_of")
	public String list_of;
	@JsonProperty("params")
	public List<ParamDescription> params;

	public String getJavaCodeUsage(int tabs)
	{
		String java = TabGenerators.genTabs(tabs)+"";
		
		java += type;
		
		if ( key_of != null || list_of != null || value_of != null) java += "<";

		if ( key_of != null ) java += key_of;
		if ( list_of != null ) java += list_of;
		if ( value_of != null ) java += ","+value_of;		
			
		if ( key_of != null || list_of != null || value_of != null) java += ">";
		
		java += " "+name+" = null;\n";
		
		java += TabGenerators.genTabs(tabs)+"try\n";
		java += TabGenerators.genTabs(tabs)+"{\n";

		java += TabGenerators.genTabs(tabs+1) + name+" = var.getVar(\""+name+"\","+type+".class);\n";

		java += TabGenerators.genTabs(tabs) + "}catch(Exception e){\n";
		java += TabGenerators.genTabs(tabs+1) + "e.printStackTrace();\n";
		java += TabGenerators.genTabs(tabs) + "}\n";

		return java;
	}

	public String getJavaCodeInit(int tabs,SelfOrchestratorModel model)
	{
		String java = "";
		
		java += type;
		
		if ( key_of != null || list_of != null || value_of != null) java += "<";

		if ( key_of != null ) java += key_of;
		if ( list_of != null ) java += list_of;
		if ( value_of != null ) java += ","+value_of;		
			
		if ( key_of != null || list_of != null || value_of != null) java += ">";
		
		java += " "+name+" = new "+type;
		
		if ( key_of != null || list_of != null || value_of != null) java += "<>";
		
		java += "(";
		
		if ( params != null )
		{
			for(ParamDescription param : params)
			{
				java += param.getJavaCode(false,tabs,model )+", ";
			}
			java = java.substring(0, java.length()-2);
		}
		
		java += ");\n";
		
		
		return java;
	}
}
