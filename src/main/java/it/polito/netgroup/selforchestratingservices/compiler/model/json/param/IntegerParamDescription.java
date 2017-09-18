package it.polito.netgroup.selforchestratingservices.compiler.model.json.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;

public class IntegerParamDescription extends ParamDescription
{
	@JsonProperty("value")
	Integer value;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		return value.toString();
	}

}
