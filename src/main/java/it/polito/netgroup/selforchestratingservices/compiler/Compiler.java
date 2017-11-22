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
		new File(path).mkdirs();

		for(ServiceDescription serviceDescription : model.serviceDescriptions)
		{
			writeClass("", model, pack, path, serviceDescription);
			
			for ( ImplementationDescription implementationDescription : serviceDescription.implementations)
			{
				writeClass(serviceDescription.name,model,pack,path,implementationDescription);
				
				for (ResourceRequirementsDescription resource : implementationDescription.resources_used)
				{
					writeClass(serviceDescription.name+implementationDescription.name,model,pack,path,resource);
				}
			}			
		}
		
		for(InfrastructureVNFTemplateDescription template : model.templates)
		{
			writeClass("",model,pack,path,template);			
		}

		if ( model.events != null ) {
			for (EventDescription event : model.events) {
				writeClass("", model, pack, path, event);
			}
		}

		if ( model.infraEvents != null) {
			writeClass("", model, pack, path, model.infraEvents);
		}

		if ( model.types != null ) {
			for (VariableTypeDescription type : model.types) {
				writeClass("", model, pack, path, type);
			}
		}
		
		writeClass("", model,pack,path,model);

	}
	
	public static void writeClass(String prefix,SelfOrchestratorModel model,String pack , String path , GenerateJavaClass gjc)
	{
		String javaclass = gjc.getJavaClass(prefix,model,pack);

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
