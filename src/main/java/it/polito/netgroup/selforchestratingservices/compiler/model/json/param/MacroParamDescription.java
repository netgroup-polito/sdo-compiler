package it.polito.netgroup.selforchestratingservices.compiler.model.json.param;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.macro.MacroDescription;

public class MacroParamDescription extends ParamDescription
{
	@JsonProperty("macro")
	MacroDescription macro;
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		return macro.getJavaCode(from_root,tabs, model);
	}

}
