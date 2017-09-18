package it.polito.netgroup.selforchestratingservices.compiler.model.json;

public interface GenerateJavaCode
{
	public String getJavaCode(boolean from_root, int tabs, SelfOrchestratorModel model);
}
