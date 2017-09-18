package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Resource;

public class ElementaryServiceDescription implements GenerateJavaClass
{
	@JsonProperty("name")
	public String name;
	@JsonProperty("implementations")
	public List<ImplementationDescription> implementations;
	
	public String getJavaClass(String prefix,SelfOrchestratorModel model,String pack)
	{
		String java = "package "+pack+";\n" + 
				"\n" +
				PackageGenerator.getPackage() +
				"\n" + 
				"//Autogenerted class\n" + 
				"public class "+getJavaClassName(prefix)+" extends AbstractElementaryService\n" + 
				"{\n" + 
				"\tpublic "+getJavaClassName(prefix)+"(Variables var)\n" + 
				"\t{\n" + 
				"\t\tsuper(var);\n"+
				"\t\tList<Class<? extends ResourceTemplate>> resourceTemplates = new ArrayList<>();\n";

		for(ResourceTemplateDescription template : model.getTemplates()) {
			java += "\t\tresourceTemplates.add("+template.getJavaClassName(prefix)+".class);\n";
		}

		for(ImplementationDescription implementation : implementations)
		{
			java += "\t\timplementations.add(new "+implementation.getJavaClassName(prefix+name)+"(var,resourceTemplates));\n";
		}

		java +=	"\t\tname = \""+name+"\";\n" +
				"\t}\n" + 
				"}";
		return java;
	}


	@Override
	public String getJavaClassName(String prefix)
	{
		String className = prefix+name;
		return Character.toUpperCase(className.charAt(0)) + className.substring(1) +"ElementaryService";
	}
}
