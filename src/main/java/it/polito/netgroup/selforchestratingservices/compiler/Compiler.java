package it.polito.netgroup.selforchestratingservices.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.netgroup.selforchestratingservices.compiler.model.json.ElementaryServiceDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaClass;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.ImplementationDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.ResourceRequirementsDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.ResourceTemplateDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.VariableTypeDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.event.EventDescription;

public class Compiler
{
	//TODO il campo params delle azioni non viene considerato
	//TODO il campo params degli eventi viene considerato "poco"

	public static void main(String[] args)
	{
		if (args.length < 1 )
		{
			System.err.println("USAGE: modello.json");
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

		for(ElementaryServiceDescription elementaryServiceDescription : model.elementaryServices)
		{
			writeClass("", model, pack, path, elementaryServiceDescription);
			
			for ( ImplementationDescription implementationDescription : elementaryServiceDescription.implementations)
			{
				writeClass(elementaryServiceDescription.name,model,pack,path,implementationDescription);
				
				for (ResourceRequirementsDescription resource : implementationDescription.resources)
				{
					writeClass(elementaryServiceDescription.name+implementationDescription.name,model,pack,path,resource);
				}
			}			
		}
		
		for(ResourceTemplateDescription template : model.templates)
		{
			writeClass("",model,pack,path,template);			
		}
		
		for(EventDescription event : model.events)
		{
			writeClass("",model,pack,path,event);
		}


		writeClass("",model,pack,path,model.infraEvents);

		for( VariableTypeDescription type : model.types)
		{
			writeClass("", model,pack,path,type);
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
