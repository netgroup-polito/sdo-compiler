package it.polito.netgroup.selforchestratingservices.compiler;

public class TabGenerators
{

	public static String genTabs(int tabs)
	{
		String ret = "";
		for(int i=0; i < tabs ; i++) ret += "\t";
		return ret ;
	}

}
