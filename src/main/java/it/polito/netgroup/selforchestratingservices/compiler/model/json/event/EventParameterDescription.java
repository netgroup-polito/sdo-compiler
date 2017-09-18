package it.polito.netgroup.selforchestratingservices.compiler.model.json.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventParameterDescription
{
	@JsonProperty("name")
	public String name;
	@JsonProperty("type")
	public String type;
}
