package com.ibm.etools.mft.pattern.fp.rd.http.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class SummaryFileGenerator {
	private final static String HTML_CONTENT = "<html>\r\n" + 
			"<head>\r\n" + 
			"<title>Record Distribution to HTTP: one-way pattern</title>\r\n" + 
			"</head>\r\n" + 
			"<body>\r\n" + 
			"		<h1 class=\"topictitle1\">Using the sample data with the \r\n" + 
			"	<span>Record Distribution to HTTP: one-way</span> pattern</h1>\r\n" + 
			"	<p class=\"shortdesc\">To test the pattern without creating your own input data and HTTP web service, \r\n" + 
			"	you can use the sample data that has been provided with this pattern instance.</p>\r\n" + 
			"	\r\n" + 
			"	<p><b>Note:</b> To run the HTTP web services that are provided with the pattern, you must have the Node.js runtime environment installed on your computer. The \r\n" + 
			"	Node.js runtime environment can be downloaded from the <span>Node.js web site: <a href=\"http://nodejs.org/download/\" title=\"Node.js web site\">http://nodejs.org/download/</a></span>.\r\n" + 
			"	</p> \r\n" + 
			"<h4>Index</h4>\r\n" + 
			"<ul>\r\n" + 
			"<li><a href=\"#about\">About the sample data</a></li>\r\n" + 
			"<li><a href=\"#running\">Running the web server</a></li>\r\n" + 
			"</ul>\r\n" + 
			"\r\n" + 
			"<h2 id=\"about\">About the sample data</h2>\r\n" + 
			"\r\n" + 
			"	<p>There are two sample configurations available with the <span>Record Distribution to HTTP: one-way</span> pattern.\r\n" + 
			"	</p>\r\n" + 
			"	\r\n" + 
			"	<p>If you have selected the <b>Single Destination</b> as the sample configuration, then the records that are extracted from the files are all sent \r\n" + 
			"	to the Default web service.</p>\r\n" + 
			"	<p>If you have selected <b>Three Possible Destinations</b> as the sample configuration, then the records that are extracted from the files are \r\n" + 
			"	examined before they are routed to a web service:\r\n" + 
			"		<ul>\r\n" + 
			"			<li>If the Country field in the record is set to \"United Kingdom\", the record is routed to the UK web service.</li>\r\n" + 
			"			<li>If the Country field in the record is set to \"United States\", the record is routed to the US web service.</li>\r\n" + 
			"			<li>If the Country field in the record is not set to \"United Kingdom\" or \"United States\", \r\n" + 
			"			the record is routed to the Default web service.</li>	\r\n" + 
			"		</ul>		\r\n" + 
			"	</p>\r\n" + 
			"	\r\n" + 
			"	<p>The following sample resources have been created when you generated the pattern's instance:\r\n" + 
			"	<ul>\r\n" + 
			"		<li>An input directory where you add the files to be processed by the application:\r\n" + 
			"			<ul><li>On Windows: <span class=\"filepath\"><tt><i>workspace</i>\\<i>app_name</i>_HTTP-oneway_sample\\Input_Directory</tt></span></li>\r\n" + 
			"			<li>On Linux: <span class=\"filepath\"><tt><i>workspace</i>/<i>app_name</i>_HTTP-oneway_sample/Input_Directory</tt></span></li></ul>\r\n" + 
			"			where <i>workspace</i> is your Integration Studio workspace directory, and <i>app_name</i> is the name of your application. </li><br/>\r\n" + 
			"		<li>Three sample files that contain user records:\r\n" + 
			"			<ul><li>One record has a Country field set to \"United Kingdom\".</li>\r\n" + 
			"			<li>One record has a Country field set to \"United States\".</li>\r\n" + 
			"			<li>One record has a Country field set to \"Canada\".</li></ul></li><br/>\r\n" + 
			"		<li>A Node.js web server with three web services available from the following URLs:\r\n" + 
			"			<ul><li>UK web service: <tt>http://localhost:<i>port</i>/country/uk.html</tt></li>\r\n" + 
			"			<li>US web service: <tt>http://localhost:<i>port</i>/country/us.html</tt></li>\r\n" + 
			"			<li>Default web service: <tt>http://localhost:<i>port</i>/country/default.html</tt></li></ul></li> \r\n" + 
			"			where <i>port</i> is an available port. By default the port will be <tt>3000</tt>, unless this port is not available.\r\n" + 
			"	</ul>\r\n" + 
			"		</p>\r\n" + 
			"<h2 id=\"running\">Running the web server</h2>\r\n" + 
			"<p>To run the sample web server on Windows, navigate to <tt><i>workspace</i>/<i>app_name</i>_HTTP-oneway_sample/webserver</tt> and run the script <tt><i>start_server.bat</i></tt></p>\r\n" + 
			"<p>To run the sample web server on Linux, navigate to <tt><i>workspace</i>/<i>app_name</i>_HTTP-oneway_sample/webserver</tt> and run the script <tt><i>start_server.sh</i></tt></p>\r\n" + 
			"\r\n" + 
			"</body>\r\n" + 
			"</html>";
	
	public void generate(String workspaceLocation, String patternInstanceName) {
		String summaryFileDest = workspaceLocation + File.separator + patternInstanceName + File.separator + "Pattern Configuration" + File.separator + 
				patternInstanceName + "_summary.html";
		
		Writer writer = null;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(summaryFileDest)));
			writer.write(HTML_CONTENT);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
