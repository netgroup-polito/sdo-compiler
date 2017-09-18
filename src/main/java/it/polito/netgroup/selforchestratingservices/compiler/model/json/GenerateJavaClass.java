package it.polito.netgroup.selforchestratingservices.compiler.model.json;

public interface GenerateJavaClass
{
	public String getJavaClassName(String prefix);
	String getJavaClass(String prefix, SelfOrchestratorModel model, String pack);

}
