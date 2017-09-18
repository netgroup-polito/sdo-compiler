package it.polito.netgroup.selforchestratingservices.compiler.model.json.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventCheckOnDescription
{
	@JsonProperty("type")
	public String type;
	@JsonProperty("name")
	public String name;
}
