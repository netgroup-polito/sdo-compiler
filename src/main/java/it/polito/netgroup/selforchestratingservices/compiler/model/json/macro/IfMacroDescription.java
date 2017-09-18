package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import java.security.InvalidParameterException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.IfCondition;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;

public class IfMacroDescription extends MacroDescription
{
	@JsonProperty("condition")
	IfCondition condition;
	
	@JsonProperty("on_true")
	List<MacroDescription> on_true;
	
	@JsonProperty("on_false")
	List<MacroDescription> on_false;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		if ( !from_root )
		{
			throw new InvalidParameterException("Unable to use if outside the root context");
		}

		String java = super.getJavaCode(from_root,tabs,model);


		java += "if ( "+ condition.getJavaCode(false,tabs,model )+ " ) { \n";
		
		if ( on_true != null )
		{
			for (MacroDescription macroD : on_true)
			{
				java = java + macroD.getJavaCode(true,tabs+1,model )+";\n";
			}		
		}
		
		java = java + TabGenerators.genTabs(tabs) + "}else{\n";
		
		if ( on_false != null )
		{
			for (MacroDescription macroD : on_false)
			{
				java = java + macroD.getJavaCode(true,tabs+1, model)+";\n";
			}
		}
		
		java = java + TabGenerators.genTabs(tabs) + "}\n";
		
		return java;
	}
}
