package net.sf.testium.configuration;

import java.io.File;

public class AntJunitResultPluginConfiguration
{
	private final File myReportsDir;
	
	public AntJunitResultPluginConfiguration(File aReportsDir)
	{
		myReportsDir = aReportsDir;
	}

	public File getReportsDir()
	{
		return myReportsDir;
	}
}
