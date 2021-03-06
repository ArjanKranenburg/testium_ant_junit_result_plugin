package net.sf.testium.configuration;

import java.io.File;

import org.testtoolinterfaces.utils.GenericTagAndStringXmlHandler;
import org.testtoolinterfaces.utils.RunTimeData;
import org.testtoolinterfaces.utils.TTIException;
import org.testtoolinterfaces.utils.Trace;
import org.testtoolinterfaces.utils.XmlHandler;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;

public class AntJunitResultPluginConfigurationXmlHandler extends XmlHandler {
	private static final String START_ELEMENT = "SurefireReportConfiguration";

	private static final String	CFG_REPORTSDIR	= "ReportsDir";
	
	private final RunTimeData myRtData;

	private File myReportsDir;
	
	public AntJunitResultPluginConfigurationXmlHandler(XMLReader anXmlReader, RunTimeData anRtData)
	{
		super(anXmlReader, START_ELEMENT);
		
		myRtData = anRtData;

	    this.addElementHandler(new GenericTagAndStringXmlHandler(anXmlReader, CFG_REPORTSDIR));
	}

	@Override
	public void handleStartElement(String aQualifiedName)
	{
		// nop
	}

	@Override
	public void processElementAttributes(String aQualifiedName, Attributes anAtt)
	{
		// nop
	}

	@Override
	public void handleGoToChildElement(String aQualifiedName)
	{
		// nop
	}

	@Override
	public void handleCharacters(String aValue)
	{
			// nop
	}

	@Override
	public void handleEndElement(String aQualifiedName)
	{
		// nop
	}

	@Override
	public void handleReturnFromChildElement(String aQualifiedName,
			XmlHandler aChildXmlHandler) throws TTIException {
	    Trace.println(Trace.UTIL, "handleReturnFromChildElement( " + aQualifiedName + " )", true);
	    
		if (aQualifiedName.equalsIgnoreCase(CFG_REPORTSDIR))
		{
			String reportsDirName = ((GenericTagAndStringXmlHandler) aChildXmlHandler).getValue();
			reportsDirName = myRtData.substituteVars(reportsDirName);
			aChildXmlHandler.reset();	

			myReportsDir = new File( reportsDirName );
		}
	}

	public AntJunitResultPluginConfiguration getConfiguration()
	{
		return new AntJunitResultPluginConfiguration(myReportsDir);
	}

}
