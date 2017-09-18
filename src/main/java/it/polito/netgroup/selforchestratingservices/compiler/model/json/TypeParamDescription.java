package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeParamDescription
{
	@JsonProperty("type")
	public String type;
	
	@JsonProperty("name")
	public String name;	
}
