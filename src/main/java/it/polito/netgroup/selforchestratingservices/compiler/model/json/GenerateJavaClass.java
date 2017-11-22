package it.polito.netgroup.selforchestratingservices.compiler.model.json;

public interface GenerateJavaClass
{
	String getJavaClassName(String prefix);
	String getJavaClass(String prefix, SelfOrchestratorModel model, String pack);

}
