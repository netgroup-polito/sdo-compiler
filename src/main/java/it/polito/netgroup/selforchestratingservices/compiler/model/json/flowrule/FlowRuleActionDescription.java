package it.polito.netgroup.selforchestratingservices.compiler.model.json.flowrule;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class FlowRuleActionDescription implements GenerateJavaCode
{
	@JsonProperty("outputToPort")
	public ParamDescription outputToPort;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		return "new Action("+outputToPort.getJavaCode(false,tabs,model)+")";
	}
}
