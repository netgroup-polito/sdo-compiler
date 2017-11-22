package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

import java.security.InvalidParameterException;
import java.util.List;

public class ReturnMacroDescription extends MacroDescription
{
	@JsonProperty("method")
	String method;

	@JsonProperty("value")
	ParamDescription value;
	@Override
	
	
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		String java=super.getJavaCode(from_root,tabs,model);

		if ( from_root != true)
		{
			throw new InvalidParameterException("Return statement outside root context");
		}

		java = java + "return "+value.getJavaCode(false,tabs,model ) ;

		return java;
	}
}
