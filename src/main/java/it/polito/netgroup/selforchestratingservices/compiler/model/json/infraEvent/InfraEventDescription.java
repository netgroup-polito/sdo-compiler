package it.polito.netgroup.selforchestratingservices.compiler.model.json.infraEvent;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;

import java.util.List;

public class InfraEventDescription implements GenerateJavaCode {

	public enum TYPE
	{
		NEW_RESOURCE,
		REMOVING_RESOURCE,
		REMOVED_RESOURCE,
		NEW_VNF,
		REMOVING_VNF,
		REMOVED_VNF
	};

	@JsonProperty("id")
	public String id;
	@JsonProperty("type")
	public TYPE type;
	@JsonProperty("resource_type")
	public String resource_type;
	@JsonProperty("resource_name")
	public String resource_name;
	@JsonProperty("actions")
	public List<String> actions;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {

		String java = "";

		java = TabGenerators.genTabs(tabs)+ "if ( resource instanceof "+resource_type+ ") {\n" +
				TabGenerators.genTabs(tabs)+"\t"+resource_type+" "+resource_name+" = ("+resource_type+") resource;\n";

		for(String action : actions)
		{
			java += model.getAction(action).getJavaCode(true,tabs+1,model);
		}
		java += TabGenerators.genTabs(tabs)+ "}\n";
		return java;
	}
}
