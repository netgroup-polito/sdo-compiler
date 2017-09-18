package it.polito.netgroup.selforchestratingservices.compiler.model.json.flowrule;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.object.ObjectDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class FlowRuleDescription extends ObjectDescription
{
	@JsonProperty("id")
	public ParamDescription id;
	@JsonProperty("match")
	public FlowRuleMatchDescription match;
	@JsonProperty("actions")
	public FlowRuleActionDescription actions;
	@JsonProperty("priority")
	public ParamDescription priority;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		return "new FlowRule("+id.getJavaCode(false,tabs,model)+",\"\","+priority.getJavaCode(false,tabs,model)+","+match.getJavaCode(false,tabs,model)+","+actions.getJavaCode(false,tabs,model)+")";
	}

}
