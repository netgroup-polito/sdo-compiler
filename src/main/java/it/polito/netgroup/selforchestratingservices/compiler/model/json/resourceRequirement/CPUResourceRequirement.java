package it.polito.netgroup.selforchestratingservices.compiler.model.json.resourceRequirement;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.PackageGenerator;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.MacroParamDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

import java.security.InvalidParameterException;
import java.util.List;

public class CPUResourceRequirement extends ResourceRequirementsDescription {

	@JsonProperty("limit")
	List<ResourceRequirementLimit> limits;
	@JsonProperty("resource")
	String type;

	public CPUResourceRequirement()
	{
		type="CPU";
	}

	@Override
	public String getJavaClass(String prefix, SelfOrchestratorModel model, String pack) {

		//TODO porcata da risolvere, perchè type è null qui ??
		//if ( type == null )
		//{
		//	throw new InvalidParameterException("type is null");
		//}
		type="CPU";

		String java = "package "+pack+";\n" +
				"\n" +
				PackageGenerator.getPackage() +
				"//Autogenerated file\n" +
				"public class "+getJavaClassName(prefix)+" implements ResourceRequirement {\n" +
				"\n" +
				"\tVariables var;\n" +
				"\n" +
				"\tpublic "+getJavaClassName(prefix)+"(Variables var) {\n" +
				"\t\tthis.var = var;\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic Integer minimum(ConfigurationSDN configurationP)\n" +
				"\t{\n" +
				"\n" +
				"\t\ttry\n" +
				"\t\t{\n" +
				"\t\t\tTranscoderConfiguration configuration = (TranscoderConfiguration) configurationP;\n";

		//TODO
		for(ResourceRequirementLimit rrl : limits) {
			java += rrl.getJavaCode(true, 3, model);
		}
		java += "\t\t\t{\n";
		java += "\t\t\t\treturn Integer.MAX_VALUE;\n";
		java += "\t\t\t}\n";


		java +=	"\n" +
				"\t\t}\n" +
				"\t\tcatch(Exception e)\n" +
				"\t\t{\n" +
				"\t\t\treturn Integer.MAX_VALUE;\n" +
				"\t\t}\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic ResourceType getResourceType() {\n" +
				"\t\treturn ResourceType.valueOf(\""+type+"\");\n" +
				"\t}\n" +
				"}";


		return java;
	}

	@Override
	public String getJavaClassName(String prefix)
	{
		String className = prefix+type;
		return Character.toUpperCase(className.charAt(0)) + className.substring(1) +"ResourceRequirement";
	}
}