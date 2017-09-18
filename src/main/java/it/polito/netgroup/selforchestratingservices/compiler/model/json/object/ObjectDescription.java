package it.polito.netgroup.selforchestratingservices.compiler.model.json.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.flowrule.FlowRuleDescription;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FlowRuleDescription.class, name = "flowrule")
})
public abstract class ObjectDescription implements GenerateJavaCode
{
	public enum TYPE {
		flowrule
	}
	
	@JsonProperty("type")
	TYPE type;
}
