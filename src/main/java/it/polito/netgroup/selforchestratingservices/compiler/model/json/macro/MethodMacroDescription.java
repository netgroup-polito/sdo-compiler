package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import java.security.InvalidParameterException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class MethodMacroDescription extends MacroDescription
{
	@JsonProperty("method")
	String method;

	@JsonProperty("on")
	ParamDescription on;
	@JsonProperty("params")
	List<ParamDescription> params;
	@Override
	
	
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String java=super.getJavaCode(from_root,tabs,model);

		if ( on != null )
		{
			java += on.getJavaCode(false,tabs,model)+".";
		}


		java += method+"(";

		if ( params != null )
		{
			for (ParamDescription macroD : params)
			{
				java = java + macroD.getJavaCode(false,tabs,model ) +", ";
			}
			java = java.substring(0, java.length()-2);
		}
		java = java + ")";
		return java;
	}
}
