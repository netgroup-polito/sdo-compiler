package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.param.ParamDescription;

public class ImplementationDescription implements GenerateJavaClass
{

	@JsonProperty("name")
	public String name;
	@JsonProperty("qos")
	public ParamDescription qos;
	
	@JsonProperty("resources")
	public List<ResourceRequirementsDescription> resources;

	@Override
	public String getJavaClass(String prefix,SelfOrchestratorModel model,String pack) {
		String java = "package " + pack + ";\n" +
				"\n" +
				PackageGenerator.getPackage() +
				"\n" +
				"//Autogenerted class\n" +
				"public class " + getJavaClassName(prefix) + " extends AbstractImplementation\n" +
				"{\n" +
				"\tpublic " + getJavaClassName(prefix) + "(Variables var,List<Class<? extends ResourceTemplate>> resourceTemplates)\n" +
				"\t{\n" +
				"\t\tsuper(var,resourceTemplates);\n";

		for (ResourceRequirementsDescription description : resources) {
			java += "\t\tresources.add(new " + description.getJavaClassName(prefix + name) + "(var));\n";
		}

		java += "\t\tname = \"" + name + "\";\n" +
				"\t}\n" +
				"\n" +
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
				"\t\t\treturn "+qos.getJavaCode(false, 2, model) +";\n" +
				"\t\t}catch(Exception ex){\n" +
				"\t\t\treturn Double.NaN;\n" +
				"\t\t}\n" +
				"\t}\n" +
				"}\n";
				
		return java;
	}

	@Override
	public String getJavaClassName(String prefix )
	{
		String className = prefix+name;

		return Character.toUpperCase(className.charAt(0)) + className.substring(1) +"Implementation";
	}
}
