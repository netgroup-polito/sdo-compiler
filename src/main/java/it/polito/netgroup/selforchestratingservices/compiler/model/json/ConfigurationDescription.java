package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.macro.MacroDescription;

import java.util.List;

public class ConfigurationDescription implements  GenerateJavaCode{

	@JsonProperty("qos_value")
	Integer qos_value;
	@JsonProperty("configuration")
	List<MacroDescription> actions;

	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		String java = "";

		java += TabGenerators.genTabs(tabs)+"if ( configurations == "+qos_value+")\n";

		java += TabGenerators.genTabs(tabs) +"{\n";
		for(MacroDescription macro : actions)
		{
			java += macro.getJavaCode(true,tabs+1,model)+";\n";
		}
		java += TabGenerators.genTabs(tabs)+"}\n";

		java += TabGenerators.genTabs(tabs)+"else\n";

		return java;
	}
}
