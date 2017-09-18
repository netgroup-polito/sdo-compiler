package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

import java.security.InvalidParameterException;

public class MaxMacroDescription extends MacroDescription {

	@JsonProperty("on")
	ParamDescription on;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		String java=super.getJavaCode(from_root,tabs,model);

		if ( assign_to != null )
		{
			if( from_root == false )
			{
				throw new InvalidParameterException("Unable use 'assign_to' inside a non-root context");
			}
			if ( type == null )
			{
				throw new InvalidParameterException("Unable use 'assign_to' without 'type' ");
			}

			java += type + " "+ assign_to+ " = ";
		}
		java += on.getJavaCode(false,tabs,model)+".stream().max(Map.Entry.comparingByValue())";


		return java;
	}
}
