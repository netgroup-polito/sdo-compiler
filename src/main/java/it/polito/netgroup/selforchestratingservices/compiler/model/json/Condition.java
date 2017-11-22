package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class Condition implements GenerateJavaCode
{
	public enum COMPARE_MODE {
		equal_to,
		equal_to_obj,
		greater_equal_to
	}
	
	@JsonProperty("operand1")
	public ParamDescription operand1;
	@JsonProperty("operand2")
	public ParamDescription operand2;
	
	@JsonProperty("compare_mode")
	public COMPARE_MODE compare_mode;
	
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String end="";
		if ( compare_mode == COMPARE_MODE.equal_to_obj)
		{
			end=")";
		}
		return operand1.getJavaCode(false,tabs,model )+ compareModeJavaCode(compare_mode) + operand2.getJavaCode(false,tabs,model ) + end;
	}

	private String compareModeJavaCode(COMPARE_MODE cm)
	{
		if ( cm == COMPARE_MODE.equal_to)
		{
			return " == ";
		}
		if ( cm == COMPARE_MODE.equal_to_obj)
		{
			return ".equals(";
		}
		else if ( cm == COMPARE_MODE.greater_equal_to)
		{
			return " >= ";
		}
		return " ?? ";
	}
	
}
