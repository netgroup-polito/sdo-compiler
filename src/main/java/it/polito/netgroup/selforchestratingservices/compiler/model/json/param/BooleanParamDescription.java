package it.polito.netgroup.selforchestratingservices.compiler.model.json.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;

public class BooleanParamDescription extends ParamDescription
{
	@JsonProperty("value")
	Boolean value;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		return value.toString();
	}

}
