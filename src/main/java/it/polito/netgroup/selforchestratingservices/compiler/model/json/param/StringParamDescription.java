package it.polito.netgroup.selforchestratingservices.compiler.model.json.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;

public class StringParamDescription extends ParamDescription
{
	@JsonProperty("value")
	String value;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		return "\""+value+"\"";
	}

}
