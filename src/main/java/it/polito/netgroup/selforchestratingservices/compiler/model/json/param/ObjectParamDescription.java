package it.polito.netgroup.selforchestratingservices.compiler.model.json.param;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.object.ObjectDescription;

public class ObjectParamDescription extends ParamDescription
{
	@JsonProperty("object")
	ObjectDescription object;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		return object.getJavaCode(false,tabs,model );
	}

}
