package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class DivisionMacroDescription extends MacroDescription
{
	@JsonProperty("params")
	List<ParamDescription> params;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String java = super.getJavaCode(from_root,tabs,model);

		/*
		if ( on_exception != null)
		{
			if ( from_root == false)
			{
				throw new InvalidParameterException("Unable to use 'on_exception' inside a non-root context.");
			}
		}
		*/

		for (ParamDescription param : params)
		{
			java = java + param.getJavaCode(false,tabs+1,model ) + "*1.0/";
		}
		java = java.substring(0, java.length()-1);

		return java;
	}
}
