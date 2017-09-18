package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BooleanMethodDescription implements GenerateJavaCode
{
	String value;
	
	
	@JsonCreator
	public BooleanMethodDescription(String _value)
	{
		value = _value;
	}
	
	
	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
