package it.polito.netgroup.selforchestratingservices.compiler.model.json.flowrule;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class FlowRuleMatchDescription implements GenerateJavaCode
{
	@JsonProperty("portIn")
	ParamDescription portIn;
	@JsonProperty("sourceMac")
	ParamDescription sourceMac;
	@JsonProperty("destMac")
	ParamDescription destMac;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		String java = "new Match(";

		if ( portIn != null )
		{
			java += portIn.getJavaCode(false,tabs,model);
		}
		else
		{
			java += "null";
		}
		java += ",";
		if ( sourceMac != null )
		{
			java += sourceMac.getJavaCode(false,tabs,model);
		}
		else
		{
			java += "null";
		}
		java += ",";

		if ( destMac != null )
		{
			java += destMac.getJavaCode(false,tabs,model);
		}
		else
		{
			java += "null";
		}
		java += ")";

		return java;
	}
}
