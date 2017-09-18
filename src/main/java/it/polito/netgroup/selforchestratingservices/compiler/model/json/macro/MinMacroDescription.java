package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

import java.security.InvalidParameterException;

public class MinMacroDescription extends MacroDescription {

	@JsonProperty("on")
	ParamDescription on;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		String java=super.getJavaCode(from_root,tabs,model);

		java += on.getJavaCode(false,tabs,model)+".stream().min(Map.Entry.comparingByValue())";

		return java;
	}
}
