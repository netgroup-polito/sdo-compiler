package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

import java.security.InvalidParameterException;

public class GroupByMacroDescription extends MacroDescription {

	@JsonProperty("groupby_method")
	String groupby_method;
	@JsonProperty("on")
	ParamDescription on;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String a = "";
		String java = super.getJavaCode(from_root,tabs,model);

		if (groupby_method == null)
		{
			a = "";
		}
		else
		{
			a = "."+groupby_method+"()";
		}

		java += on.getJavaCode(false,tabs,model)+".stream().collect(Collectors.groupingBy(n->n"+a+",Collectors.counting())).entrySet()";

		return java;
	}
}
