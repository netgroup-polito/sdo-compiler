package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;

public class ActionParameterDescription implements GenerateJavaCode
{
	@JsonProperty("name")
	public String name;
	@JsonProperty("type")
	public String type;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		return "";
	}
}
