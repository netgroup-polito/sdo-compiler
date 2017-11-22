package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.resourceRequirement.ResourceRequirementsDescription;

public class ImplementationDescription implements GenerateJavaClass
{

	@JsonProperty("name")
	public String name;
	@JsonProperty("configurations")
	public List<ConfigurationDescription> configurations;

	@JsonProperty("scale_horizontaly")
	public Boolean scale_horizontaly=false;
	@JsonProperty("scale_verticaly")
	public Boolean scale_verticaly=false;

	@JsonProperty("microservice_type")
	public String microservice_type;
	@JsonProperty("microservice_template")
	public String microservice_template;

	@JsonProperty("resources_used")
	public List<ResourceRequirementsDescription> resources_used;

	@Override
	public String getJavaClass(String prefix,SelfOrchestratorModel model,String pack) {
		String java = "package " + pack + ";\n" +
				"\n" +
				PackageGenerator.getPackage() +
				"\n" +
				"//Autogenerted class\n" +
				"public class " + getJavaClassName(prefix) + " extends AbstractImplementation\n" +
				"{\n" +
				"\t"+microservice_type+" ivnf=null;\n" +
				"\tpublic " + getJavaClassName(prefix) + "(Variables var,List<Class<? extends VNFTemplate>> resourceTemplates)\n" +
				"\t{\n" +
				"\t\tsuper(var,resourceTemplates,new TranscoderConfiguration(\"\",\"\",\"\",\"\"));\n";

		for (ResourceRequirementsDescription description : resources_used) {
			java += "\t\tresources.add(new " + description.getJavaClassName(prefix + name) + "(var));\n";
		}

		java += "\t\tname = \"" + name + "\";\n" +
				"\t}\n" +
				"\n";
				/*
				"\t@Override\n" +
				"\tpublic Double getQoS(Collection<Resource> resourcesUsed)\n" +
				"\t{\n";

		for (VariableDescription var : model.variables) {
			java += var.getJavaCodeUsage(2) + "\n";
		}
		//java += "\t\t//Shadow internal resourcesUsed variable\n";
		//java += "\t\tList<InfrastructureResource> resourcesUsed = resources.stream().filter(x -> x.isUsed()).collect(Collectors.toCollection(ArrayList::new));\n";
		//java += "\n";

		java += "\t\ttry{\n" +
				"\t\t\treturn "+configurations.getJavaCode(false, 2, model) +";\n" +
				"\t\t}catch(Exception ex){\n" +
				"\t\t\treturn Double.NaN;\n" +
				"\t\t}\n" +
				"\t}\n";
				*/

		java += "\t@Override\n" +
				"\tpublic "+microservice_type+" getInfrastructureVNF(Infrastructure infrastructure) {\n" +
				"\t\tif ( ivnf == null ) ivnf = new "+microservice_type+"(infrastructure,name);\n" +
				"\t\treturn ivnf;\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic ConfigurationSDN getConfiguration(ConfigurationSDN actualConfiguration, Integer configurations) {\n" +
				"\t\tTranscoderConfiguration configuration = (TranscoderConfiguration)actualConfiguration.copy();\n" +
				"\n";

		for(ConfigurationDescription qosD : configurations)
		{
			java += qosD.getJavaCode(true,2,model);
		}
		java += "\t\t{}\n";

/*
				"\t\tif ( configurations > 50 )\n" +
				"\t\t{\n" +
				"\t\t\tconfiguration.setEnabled(true);\n" +
				"\t\t}\n" +
				"\t\telse\n" +
				"\t\t{\n" +
				"\t\t\tconfiguration.setEnabled(false);\n" +
				"\t\t}\n";*/

		java +=	"\n" +
				"\t\treturn configuration;\n" +
				"\t}";

		java +=	"}\n";
				
		return java;
	}

	@Override
	public String getJavaClassName(String prefix )
	{
		String className = prefix+name;

		return Character.toUpperCase(className.charAt(0)) + className.substring(1) +"Implementation";
	}
}
