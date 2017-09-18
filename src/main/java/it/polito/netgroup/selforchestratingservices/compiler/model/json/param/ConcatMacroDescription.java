package it.polito.netgroup.selforchestratingservices.compiler.model.json.param;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.macro.MacroDescription;

public class ConcatMacroDescription extends MacroDescription
{
	@JsonProperty("params")
	List<ParamDescription> params;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String java = "";
		
		for(ParamDescription param : params)
		{
			java = java + param.getJavaCode(from_root,tabs,model) + " + ";
		}
		java = java.substring(0,java.length()-2);

		return java;
	}

}
