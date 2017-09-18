package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;

public class IntegerExpressionDescription implements GenerateJavaCode
{
	Integer value;

	@JsonCreator
	public IntegerExpressionDescription(Integer _value)
	{
		value = _value;
	}

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		return value.toString();
	}
}
