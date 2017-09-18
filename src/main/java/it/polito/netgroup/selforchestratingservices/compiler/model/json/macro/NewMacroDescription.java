package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import java.security.InvalidParameterException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class NewMacroDescription extends MacroDescription
{
	@JsonProperty("params")
	List<ParamDescription> params;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String java=super.getJavaCode(from_root,tabs,model);

		java += "( new "+type+"(";
		
		for(ParamDescription param : params)
		{
			java = java + param.getJavaCode(false,tabs, model) + ",";
		}
		java = java.substring(0, java.length() - 1);
		
		java = java + ") )";
		
		return java;
	}	
}
