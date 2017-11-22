package it.polito.netgroup.selforchestratingservices.compiler.model.json.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ObjectParamDescription.class, name = "object"),
    @JsonSubTypes.Type(value = StringParamDescription.class, name = "string"),
    @JsonSubTypes.Type(value = IntegerParamDescription.class, name = "integer"),
	@JsonSubTypes.Type(value = DoubleParamDescription.class, name = "double"),
	@JsonSubTypes.Type(value = BooleanParamDescription.class, name = "boolean"),
    @JsonSubTypes.Type(value = VariableParamDescription.class, name = "variable"),
    @JsonSubTypes.Type(value = MacroParamDescription.class, name = "macro"),
})

public abstract class ParamDescription implements GenerateJavaCode
{
	public enum TYPE
	{
		object,
		string,
		variable,
		macro
	}
	@JsonProperty("type")
	TYPE type;
}
