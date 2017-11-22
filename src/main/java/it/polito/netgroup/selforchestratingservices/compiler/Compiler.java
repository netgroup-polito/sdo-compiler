package it.polito.netgroup.selforchestratingservices.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.ServiceDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaClass;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.ImplementationDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.resourceRequirement.ResourceRequirementsDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.InfrastructureVNFTemplateDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.VariableTypeDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.event.EventDescription;

public class Compiler
{
	public static void main(String[] args)
	{
		if (args.length < 1 )
		{
			System.err.println("USAGE: model_filename java_package base_directory");
			System.exit(1);
		}

		//Reading JSON model
		String model_json_filename="";
		try
		{
			model_json_filename = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		String pack=args[1];
		String base_dir=args[2];
		
		ObjectMapper mapper = new ObjectMapper();

		//Parsing JSON model
		SelfOrchestratorModel model=null;
		try
		{
			model = mapper.readValue(model_json_filename, SelfOrchestratorModel.class);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	
		String path = base_dir+pack.replace(".", "/")+"/";
		//Creating the output directory from base directory and package name
		new File(path).mkdirs();

		//Foreach elementary service
		for(ServiceDescription serviceDescription : model.serviceDescriptions)
		{
			//Create the associated class
			writeClass("", model, pack, path, serviceDescription);

			//Foreach implementation of a elementary service
			for ( ImplementationDescription implementationDescription : serviceDescription.implementations)
			{
				//Create the associated class
				writeClass(serviceDescription.name,model,pack,path,implementationDescription);

				//Foreach ResourceRequirement of the implementation
				for (ResourceRequirementsDescription resource : implementationDescription.resources_used)
				{
					//Create the associated class
					writeClass(serviceDescription.name+implementationDescription.name,model,pack,path,resource);
				}
			}			
		}

		//ForEach template defined inside the model
		for(InfrastructureVNFTemplateDescription template : model.templates)
		{
			//Create the associated class
			writeClass("",model,pack,path,template);			
		}

		//If there are some events that must be handled
		if ( model.events != null ) {
			for (EventDescription event : model.events) {
				//Create the associated class
				writeClass("", model, pack, path, event);
			}
		}

		//If there are some infrastructure events that must be handled
		if ( model.infraEvents != null) {
			//Create the associated class
			writeClass("", model, pack, path, model.infraEvents);
		}


		//If there are custom types
		if ( model.types != null ) {
			for (VariableTypeDescription type : model.types) {
				//Create the associated class
				writeClass("", model, pack, path, type);
			}
		}

		//Create the main class that describe the service
		writeClass("", model,pack,path,model);

	}

	//This function write inside a file the Java class associated to a JSON object
	public static void writeClass(String prefix,SelfOrchestratorModel model,String pack , String path , GenerateJavaClass gjc)
	{
		//Get the Java code associated to the JSON object
		String javaclass = gjc.getJavaClass(prefix,model,pack);

		//Write inside a Java file
		FileOutputStream out;
		try
		{
			out = new FileOutputStream(path+gjc.getJavaClassName(prefix)+".java");
			out.write(javaclass.getBytes());
			out.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}	
	}

}
