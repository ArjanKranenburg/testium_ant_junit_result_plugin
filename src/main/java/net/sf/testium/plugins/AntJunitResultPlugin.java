package net.sf.testium.plugins;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.testium.Testium;
import net.sf.testium.configuration.ConfigurationException;
import net.sf.testium.configuration.AntJunitResultPluginConfiguration;
import net.sf.testium.configuration.AntJunitResultPluginConfigurationXmlHandler;
import net.sf.testium.reports.AntJunitResultPluginWriter;

import org.testtoolinterfaces.utils.RunTimeData;
import org.testtoolinterfaces.utils.Trace;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


/**
 * @author Arjan Kranenburg
 *
 */
public final class AntJunitResultPlugin implements Plugin
{
	public AntJunitResultPlugin()
	{
		super();
		Trace.println(Trace.CONSTRUCTOR);
	}

	public void loadPlugIn(PluginCollection aPluginCollection,
			RunTimeData anRtData) throws ConfigurationException
	{
		Trace.println(Trace.UTIL, "loadPlugIn( " + aPluginCollection + " )", true );

		AntJunitResultPluginConfiguration config = readConfigFiles( anRtData );
		
		AntJunitResultPluginWriter surefireReportWriter = new AntJunitResultPluginWriter( config );
		aPluginCollection.addTestGroupResultWriter(surefireReportWriter);
	}

	public AntJunitResultPluginConfiguration readConfigFiles( RunTimeData anRtData ) throws ConfigurationException
	{
		Trace.println(Trace.UTIL);

		File configDir = (File) anRtData.getValue(Testium.CONFIGDIR);
		File configFile = new File( configDir, "surefireReport.xml" );
		AntJunitResultPluginConfiguration globalConfig = readConfigFile( configFile, anRtData );
		
		return globalConfig;
	}

	public AntJunitResultPluginConfiguration readConfigFile( File aConfigFile,
			RunTimeData aRtData ) throws ConfigurationException
	{
		Trace.println(Trace.UTIL, "readConfigFile( " + aConfigFile.getName() + " )", true );
        // create a parser
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(false);
        SAXParser saxParser;
        AntJunitResultPluginConfigurationXmlHandler handler = null;
		try
		{
			saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();

	        // create a handler
			handler = new AntJunitResultPluginConfigurationXmlHandler(xmlReader, aRtData);

	        // assign the handler to the parser
	        xmlReader.setContentHandler(handler);

	        // parse the document
	        xmlReader.parse( aConfigFile.getAbsolutePath() );
		}
		catch (ParserConfigurationException e)
		{
			Trace.print(Trace.UTIL, e);
			throw new ConfigurationException( e );
		}
		catch (SAXException e)
		{
			Trace.print(Trace.UTIL, e);
			throw new ConfigurationException( e );
		}
		catch (IOException e)
		{
			Trace.print(Trace.UTIL, e);
			throw new ConfigurationException( e );
		}
		
		AntJunitResultPluginConfiguration configuration = handler.getConfiguration();
		
		return configuration;
	}
}
