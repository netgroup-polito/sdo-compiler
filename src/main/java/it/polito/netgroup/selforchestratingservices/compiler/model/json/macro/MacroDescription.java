package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ConcatMacroDescription;

import java.security.InvalidParameterException;
import java.security.acl.Group;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "macro")
@JsonSubTypes({
    @JsonSubTypes.Type(value = NewMacroDescription.class, name = "new"),
    @JsonSubTypes.Type(value = FilterMacroDescription.class, name = "filter"),
    @JsonSubTypes.Type(value = MethodMacroDescription.class, name = "method"),
    @JsonSubTypes.Type(value = IfMacroDescription.class, name = "if"),
    @JsonSubTypes.Type(value = DivisionMacroDescription.class, name = "division"),
    @JsonSubTypes.Type(value = ConcatMacroDescription.class, name = "concat"),
	@JsonSubTypes.Type(value = MaxMacroDescription.class, name = "max"),
	@JsonSubTypes.Type(value = MinMacroDescription.class, name = "min"),
	@JsonSubTypes.Type(value = GroupByMacroDescription.class, name = "groupby"),
	@JsonSubTypes.Type(value = MapMacroDescription.class, name = "map"),



})
public abstract class MacroDescription implements GenerateJavaCode
{
	@JsonProperty("macro")
	String macro;	
	@JsonProperty("assign_to")
	String assign_to;
	@JsonProperty("type")
	String type;


	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model)
	{
		String java = "";

		if ( from_root )
		{
			java += TabGenerators.genTabs(tabs);
		}

		if ( assign_to != null )
		{
			if( from_root == false )
			{
				throw new InvalidParameterException("Unable use 'assign_to' inside a non-root context");
			}
			if ( type == null )
			{
				throw new InvalidParameterException("Unable use 'assign_to' without 'type' ");
			}

			java += type + " "+ assign_to+ " = ";
		}

		return java;
	}
}
