package it.polito.netgroup.selforchestratingservices.compiler.model.json.resourceRequirement;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.Condition;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class ResourceRequirementLimit implements GenerateJavaCode{

	@JsonProperty("resources_needed")
	ParamDescription resources_needed;
	@JsonProperty("condition")
	Condition condition;


	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		String java = "";

		java += TabGenerators.genTabs(tabs)+"if ("+condition.getJavaCode(false,0,model)+") {\n";
		java += TabGenerators.genTabs(tabs+1)+"return "+ resources_needed.getJavaCode(false,tabs+1,model)+";\n";
		java += TabGenerators.genTabs(tabs)+"}\n";
		java += TabGenerators.genTabs(tabs)+"else\n";

		return java;
	}
}
