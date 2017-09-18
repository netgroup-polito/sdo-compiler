package it.polito.netgroup.selforchestratingservices.compiler.model.json.macro;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.internal.rngom.digested.DDataPattern;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaCode;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

import java.security.InvalidParameterException;

public class MapMacroDescription extends MacroDescription{

	@JsonProperty("on")
	ParamDescription on;
	@JsonProperty("map_type")
	String map_type;
	@JsonProperty("map_method")
	String map_method;

	@Override
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model) {
		String java = super.getJavaCode(from_root,tabs,model);

		java += on.getJavaCode(false,tabs,model)+".stream().map("+map_type+"::"+map_method+").collect(Collectors.toCollection(ArrayList::new))";
		return java;
	}
}
