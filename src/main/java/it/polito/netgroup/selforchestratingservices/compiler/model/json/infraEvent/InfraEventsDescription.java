package it.polito.netgroup.selforchestratingservices.compiler.model.json.infraEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polito.netgroup.selforchestratingservices.compiler.TabGenerators;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.GenerateJavaClass;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.PackageGenerator;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.SelfOrchestratorModel;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.VariableDescription;
import it.polito.netgroup.selforchestratingservices.compiler.model.json.event.EventDescription;

import java.util.ArrayList;
import java.util.List;

public class InfraEventsDescription implements GenerateJavaClass {

	List<InfraEventDescription> infraEventDescriptions;

	@JsonCreator
	public InfraEventsDescription(List<InfraEventDescription> events)
	{
		infraEventDescriptions = new ArrayList<>(events);
	}

	@Override
	public String getJavaClassName(String prefix) {
		return "MyInfrastructureEventHandler";
	}

	@Override
	public String getJavaClass(String prefix, SelfOrchestratorModel model, String pack)
	{
		String java = "package it.polito.netgroup.selforchestratingservices.auto;\n" +
				"\n" +
				PackageGenerator.getPackage() +
				"\n" +
				"public class MyInfrastructureEventHandler extends AbstractInfrastructureEventHandler {\n" +
				"\n" +
				"\tpublic MyInfrastructureEventHandler(Variables var)\n" +
				"\t{\n" +
				"\t\tsuper(var);\n" +
				"\t};\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic void on_resource_added(Resource resource) throws Exception {\n";

		for(VariableDescription var : model.variables)
		{
			java +=  var.getJavaCodeUsage(2)+"\n";
		}

		Boolean looped = false;
		for( InfraEventDescription infraEventsDescription: infraEventDescriptions)
		{
			if (infraEventsDescription.type == InfraEventDescription.TYPE.NEW)
			{
				java += infraEventsDescription.getJavaCode(true,2,model);
			}
		}
		java +=	"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic void on_resource_removing(Resource resource) throws Exception {\n";

		for(VariableDescription var : model.variables)
		{
			java +=  var.getJavaCodeUsage(2)+"\n";
		}

		looped=false;
		for( InfraEventDescription infraEventsDescription: infraEventDescriptions)
		{
			if (infraEventsDescription.type == InfraEventDescription.TYPE.REMOVING)
			{
				java += infraEventsDescription.getJavaCode(true,2,model);
			}
		}
		java += "\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic void on_resource_removed(Resource resource) throws Exception {\n";

		for(VariableDescription var : model.variables)
		{
			java +=  var.getJavaCodeUsage(2)+"\n";
		}

		looped=false;
		for( InfraEventDescription infraEventsDescription: infraEventDescriptions)
		{
			if (infraEventsDescription.type == InfraEventDescription.TYPE.REMOVED)
			{
				java += infraEventsDescription.getJavaCode(true,2,model);
			}
		}

		java +=	"\t}\n" +
				"}";

		return java;
	}
}
