package it.polito.netgroup.selforchestratingservices.compiler.model.json;

public class PackageGenerator {


	public static String getPackage()
	{
		return 	"import java.security.*;\n"+
				"import java.util.*;\n" +
				"import java.util.stream.*;\n" +
				"import it.polito.netgroup.configurationorchestrator.*;\n" +
				"import it.polito.netgroup.configurationorchestrator.json.nat.*;\n" +
				"import it.polito.netgroup.selforchestratingservices.*;\n" +
				"import it.polito.netgroup.nffg.json.*;\n" +
				"import it.polito.netgroup.selforchestratingservices.declarative_new.*;\n" +
				"import it.polito.netgroup.selforchestratingservices.declarative.*;\n" +
				"import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;\n" +
				"import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.*;\n";
	}
}
