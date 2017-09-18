package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class FilterMacroDescription extends MacroDescription
{	
	
	@JsonProperty("filter_method")
	String filter_method;
	@JsonProperty("equal_to")
	ParamDescription equal_to;
	
	@JsonProperty("on")
	ParamDescription on;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String java = super.getJavaCode(from_root,tabs,model);

		java += "("+on.getJavaCode(false,tabs,model )+".stream().filter(x -> x."+filter_method+"().equals("+equal_to.getJavaCode(false,tabs,model )+")).collect(Collectors.toCollection(ArrayList::new)) )";
		return java;
	}

}
