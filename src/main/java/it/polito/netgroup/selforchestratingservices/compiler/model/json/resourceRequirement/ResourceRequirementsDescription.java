package it.polito.netgroup.selforchestratingservices.compiler.model.json.resourceRequirement;


import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.*;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "resource")
@JsonSubTypes({
		@JsonSubTypes.Type(value = CPUResourceRequirement.class, name = "CPU"),
})
public abstract class ResourceRequirementsDescription implements GenerateJavaClass
{
}
