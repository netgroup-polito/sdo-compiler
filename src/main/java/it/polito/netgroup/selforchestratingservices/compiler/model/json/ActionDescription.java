package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.macro.MacroDescription;

public class ActionDescription implements GenerateJavaCode
{
	@JsonProperty("id")
	public String id;
	@JsonProperty("params")
	public List<ActionParameterDescription> params;
	@JsonProperty("steps")
	public List<MacroDescription> steps;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String java = "";

		for (MacroDescription macro : steps)
		{
			java += macro.getJavaCode(from_root,tabs, model)+";\n";
		}
		return java;
	}
}
