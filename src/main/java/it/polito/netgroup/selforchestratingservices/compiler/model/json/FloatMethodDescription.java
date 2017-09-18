package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;

public class FloatMethodDescription implements GenerateJavaCode
{
	Float value;
	@JsonCreator
	public FloatMethodDescription(Float _value)
	{
		value = _value;
	}

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		return value.toString();
	}
}
