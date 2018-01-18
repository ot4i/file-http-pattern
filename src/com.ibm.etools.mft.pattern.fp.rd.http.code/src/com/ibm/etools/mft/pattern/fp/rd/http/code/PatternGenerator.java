/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and other Contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM - initial implementation
 *******************************************************************************/
package com.ibm.etools.mft.pattern.fp.rd.http.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import com.ibm.broker.config.appdev.MessageFlow;
import com.ibm.broker.config.appdev.NamespacePrefixMap;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.OutputTerminal;
import com.ibm.broker.config.appdev.Point;
import com.ibm.broker.config.appdev.StickyNote;
import com.ibm.broker.config.appdev.SubFlowNode;
import com.ibm.broker.config.appdev.nodes.FileInputNode;
import com.ibm.broker.config.appdev.nodes.HTTPRequestNode;
import com.ibm.broker.config.appdev.nodes.InputNode;
import com.ibm.broker.config.appdev.nodes.OutputNode;
import com.ibm.broker.config.appdev.nodes.PassthroughNode;
import com.ibm.broker.config.appdev.nodes.RouteNode;
import com.ibm.broker.config.appdev.nodes.RouteNode.FilterTable;
import com.ibm.broker.config.appdev.nodes.RouteNode.FilterTableRow;
import com.ibm.broker.config.appdev.patterns.GeneratePatternInstanceTransform;
import com.ibm.broker.config.appdev.patterns.PatternInstanceManager;

public class PatternGenerator implements GeneratePatternInstanceTransform {

	private static final String PATTERN_PROJECT_NAME = "HTTP one-way";
	private static final String SAMPLE_ARTIFACTS_PROJECT_NAME = "HTTP-oneway_sample";

	// Flows
	private static final String RECORD_DISTRIBUTOR_FLOW = "RecordDistributor.msgflow";
	private static final String ROUTE_SUBFLOW = "Route.subflow";
	private static final String RECORD_PROCESSOR_SUBFLOW = "RecordProcessor.subflow";

	// Properties IDs
	private static final String PROPERTY_INCLUDE_SAMPLE_ID = "includesample";
	private static final String PROPERTY_FILENAME_PATTERN_ID = "filenamepattern";
	private static final String PROPERTY_INPUT_DIRECTORY_ID = "inputdir";
	private static final String PROPERTY_USE_FTP = "useftp";
	private static final String PROPERTY_FTP_SERVICE = "ftpservice";
	private static final String PROPERTY_ROUTING_TYPE_ID = "routingtype";

	// Routing Types Property IDs
	private static final String PROPERTY_ROUTING_TYPE_NO_ROUTE = "no_routing";
	private static final String PROPERTY_ROUTING_TYPE_SPECIFIC_ROUTES = "specific_routes";
	private static final String PROPERTY_ROUTING_TYPE_LOOKUP_ROUTES = "lookup_routes";
	private static final String PROPERTY_ROUTING_TYPE_USER_DEFINED = "user_defined_routes";

	// Nodes
	private static final String FILE_INPUT_NODE = "File Input";
	private static final String ROUTE_NODE = "Route by Country";
	private static final String ROUTE_FLOW_INPUT_NODE = "RouteInput";
	private static final String ROUTE_FLOW_OUTPUT_NODE = "RouteOutput";
	private static final String RECORD_PROCESSOR_NODE = "Record Processor";
	private static final String RECORD_PROCESSOR_FLOW_OUTPUT_NODE = "Output";
	private static final String RECORD_PROCESSOR_FLOW_INPUT_NODE = "Input";

	// Misc.
	private static String MESSAGE_DOMAIN = "BLOB";

	private PatternInstanceManager patternInstanceManager;
	private boolean includeSample;
	private String patternInstanceName;
	private String routingType;

	@Override
	public void onGeneratePatternInstance(
			PatternInstanceManager patternInstanceManager) {

		// The location for the generated projects
		String workspaceLocation = patternInstanceManager
				.getWorkspaceLocation();

		// The pattern instance name for this generation
		this.patternInstanceName = patternInstanceManager
				.getPatternInstanceName();
		this.patternInstanceManager = patternInstanceManager;

		this.includeSample = !patternInstanceManager.getParameterValue(
				PROPERTY_INCLUDE_SAMPLE_ID).equals("sample_none");

		// If Sample not included set message domain to JSON
		if (includeSample) {
			MESSAGE_DOMAIN = "JSON";
		}

		this.setDefaultFlowProperties();

		// Configure Routing
		routingType = patternInstanceManager
				.getParameterValue(PROPERTY_ROUTING_TYPE_ID);

		if (routingType.equals(PROPERTY_ROUTING_TYPE_NO_ROUTE)) {
			this.setupNoRouting();
		} else if (routingType.equals(PROPERTY_ROUTING_TYPE_SPECIFIC_ROUTES)) {
			this.setupSpecificRouting();
		} else if (routingType.equals(PROPERTY_ROUTING_TYPE_LOOKUP_ROUTES)) {
			this.setupLookupRouting();
		} else if (routingType.equals(PROPERTY_ROUTING_TYPE_USER_DEFINED)) {
			this.setupUserDefinedRouting();
		}

		if (this.includeSample) {
			this.addSampleNotes();

			this.createWebServerScript();

			// Generate Summary Page
			SummaryFileGenerator sfg = new SummaryFileGenerator();
			sfg.addProperty("port", String.valueOf(this.getHTTPPort()));
			sfg.addAdditionalResource("recorddistributorflow.png");
			sfg.addAdditionalResource("routeflow.png");
			sfg.addAdditionalResource("recordprocessorflow.png");
			sfg.generate(workspaceLocation, this.patternInstanceName);
		}

	}

	/**
	 * Create the start_server script to install dependencies and run the server
	 * 
	 */
	private void createWebServerScript() {
	
		int httpPortNumber = this.getHTTPPort();

		String artifacts_path = this.patternInstanceManager
				.getWorkspaceLocation()
				+ File.separator
				+ patternInstanceName
				+ "_" + SAMPLE_ARTIFACTS_PROJECT_NAME + File.separator;
		String webserver_path = artifacts_path + "webserver" + File.separator;

		this.writeStartServerScript(webserver_path + "start_server.bat",
				httpPortNumber); // Windows
		this.writeStartServerScript(webserver_path + "start_server.sh",
				httpPortNumber); // Linux

	}

	private int getHTTPPort() {
		// Get Port
		int httpPortNumber = 3000;
		URL webserviceURL;
		try {
			if (routingType.equals(PROPERTY_ROUTING_TYPE_NO_ROUTE)) {
				webserviceURL = new URL(
						patternInstanceManager
								.getParameterValue("noroutingweburl"));
			} else if (routingType
					.equals(PROPERTY_ROUTING_TYPE_SPECIFIC_ROUTES)) {
				webserviceURL = new URL(
						this.patternInstanceManager
								.getParameterValue("specificroutesurl"));
			} else {
				webserviceURL = new URL("");
			}

			httpPortNumber = webserviceURL.getPort();
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}
		
		return httpPortNumber;
	}

	/**
	 * Creates the start_server script files
	 * 
	 * @param path
	 * @param port
	 */
	private void writeStartServerScript(String path, int port) {
		Writer writer = null;
		File filePath  = new File(path);
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filePath)));
			writer.write("npm install & node server.js "
					+ port);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the default flow properties and nodes
	 */
	private void setDefaultFlowProperties() {
		String inputDirectory = patternInstanceManager
				.getParameterValue(PROPERTY_INPUT_DIRECTORY_ID);
		String filenamePattern = patternInstanceManager
				.getParameterValue(PROPERTY_FILENAME_PATTERN_ID);

		// If input directory does not exist then create it
		File inputDirectoryFile = new File(inputDirectory);

		if (!inputDirectoryFile.exists()) {
			inputDirectoryFile.mkdir();
		}

		MessageFlow mainFlow = patternInstanceManager.getMessageFlow(
				PATTERN_PROJECT_NAME, RECORD_DISTRIBUTOR_FLOW);
		FileInputNode fileInputNode = new FileInputNode();
		fileInputNode.setNodeName(FILE_INPUT_NODE);
		fileInputNode.setInputDirectory(inputDirectory);
		fileInputNode.setFilenamePattern(filenamePattern);
		fileInputNode.setMessageDomainProperty(MESSAGE_DOMAIN);
		
		//SJP : 13 July 2015: Fix implementation: The following two lines for ccsid and encoding
		fileInputNode.setMessageCodedCharSetIdProperty("1208") ;
		fileInputNode.setMessageEncodingProperty("546");
		
		fileInputNode.setLocation(30, 190);
		mainFlow.addNode(fileInputNode);

		// FTP??
		String useftp = (String) patternInstanceManager
				.getParameterValue(PROPERTY_USE_FTP);
		if (useftp.equals("true")) {
			String ftpService = (String) patternInstanceManager
					.getParameterValue(PROPERTY_FTP_SERVICE);
			fileInputNode.setFileFtp(true);
			fileInputNode.setFileFtpServer(ftpService);
		}
	}

	/**
	 * Sets up No Routing nodes and properties
	 */
	private void setupNoRouting() {

		MessageFlow routeFlow = this.getRouteFlow();

		InputNode inputNode = (InputNode) routeFlow
				.getNodeByName(ROUTE_FLOW_INPUT_NODE);

		// Add nodes
		PassthroughNode passThruNode = new PassthroughNode();
		routeFlow.addNode(passThruNode);

		OutputNode outputNode = new OutputNode();
		outputNode.setNodeName(ROUTE_FLOW_OUTPUT_NODE);
		routeFlow.addNode(outputNode);

		// Connect nodes
		routeFlow.connect(inputNode.OUTPUT_TERMINAL_OUT,
				passThruNode.INPUT_TERMINAL_IN);
		routeFlow.connect(passThruNode.OUTPUT_TERMINAL_OUT,
				outputNode.INPUT_TERMINAL_IN);

		String webserviceURI = patternInstanceManager
				.getParameterValue("noroutingweburl");

		Node recordProcessor = this.createRecordProcessorNode();
		Node routeNode = this.createRouteNode(recordProcessor
				.getOutputTerminal(RECORD_PROCESSOR_FLOW_OUTPUT_NODE));

		this.connectHttpRequest(
				routeNode.getOutputTerminal(ROUTE_FLOW_OUTPUT_NODE),
				webserviceURI, "HTTP Request", 0);

	}

	/**
	 * Sets up Specific Routing nodes and properties
	 */
	private void setupSpecificRouting() {

		MessageFlow routeFlow = this.getRouteFlow();

		// Get route flow's input node
		InputNode inputNode = (InputNode) routeFlow
				.getNodeByName(ROUTE_FLOW_INPUT_NODE);

		// Add Route node
		RouteNode routeNode = new RouteNode();
		routeNode.setNodeName(ROUTE_NODE);
		routeNode.setLocation(inputNode.getLocation().x + 150,
				inputNode.getLocation().y);

		// Add namespaces if applicable
		String namespaceValues = this.patternInstanceManager.getParameterValue(
				"specificroutesnamespace").trim();

		if (!namespaceValues.isEmpty()) {
			String[] namespaceRows = namespaceValues.split("\n");

			Vector<NamespacePrefixMap> NsMaps = new Vector<NamespacePrefixMap>();

			for (String namespaceRow : namespaceRows) {
				String[] cells = namespaceRow.split(",");
				String prefix = cells[0];
				String uri = cells[1];
				NamespacePrefixMap NsMap = new NamespacePrefixMap();
				NsMap.setNsPrefix(prefix);
				NsMap.setNamespace(uri);
				NsMaps.add(NsMap);
			}

			if (namespaceRows.length > 0) {
				routeNode.setNsmappingtables(NsMaps);
			}
		}

		routeFlow.addNode(routeNode);

		// Connect route node to input
		routeFlow.connect(inputNode.OUTPUT_TERMINAL_OUT,
				routeNode.INPUT_TERMINAL_IN);

		// Parse routing values
		String routeValues = this.patternInstanceManager
				.getParameterValue("specificroutesrouting");
		String[] rows = routeValues.split("\n");

		Node recordProcessor = this.createRecordProcessorNode();
		Node routeSubFlowNode = this.createRouteNode(recordProcessor
				.getOutputTerminal(RECORD_PROCESSOR_FLOW_OUTPUT_NODE));

		// Store list of webservice uris
		ArrayList<String> uris = new ArrayList<String>();

		// Create Route table
		FilterTable filterTable = routeNode.getFilterTable();
		int rowI = 0;

		// Configure routing table based
		for (String row : rows) {
			String[] cells = row.split(",");

			String keyLocation = cells[0];
			String keyValue = cells[1];
			String webserviceUri = cells[2];

			// Add uri to list
			uris.add(webserviceUri);

			FilterTableRow tableRow = filterTable.createRow();
			tableRow.setFilterPattern(keyLocation + "=\"" + keyValue + "\"");
			tableRow.setRoutingOutputTerminal(keyValue);

			filterTable.addRow(tableRow);

			OutputNode outputNode = new OutputNode();
			outputNode.setNodeName(keyValue);
			outputNode.setLocation(routeNode.getLocation().x + 150,
					routeNode.getLocation().y + (150 * rowI));
			routeFlow.addNode(outputNode);


			routeFlow.connect(routeNode.getOutputTerminal(keyValue),
					outputNode.INPUT_TERMINAL_IN);

			// Connect to http request nodes

			this.connectHttpRequest(
					routeSubFlowNode.getOutputTerminal(keyValue),
					webserviceUri, "HTTP Request: " + keyValue, rowI - 1);

			rowI++;

		}

		// Add default route
		String defaultURI = this.patternInstanceManager
				.getParameterValue("specificroutesurl");

		if (!defaultURI.isEmpty()) {
			OutputNode outputNode = new OutputNode();
			outputNode.setNodeName("default");
			outputNode.setLocation(routeNode.getLocation().x + 150,
					routeNode.getLocation().y - 150);
			routeFlow.addNode(outputNode);
			routeFlow.connect(routeNode.OUTPUT_TERMINAL_DEFAULT,
					outputNode.INPUT_TERMINAL_IN);

			this.connectHttpRequest(
					routeSubFlowNode.getOutputTerminal("default"), defaultURI,
					"HTTP Request: Default", rowI - 1);

			uris.add(defaultURI);
		}

	}

	private void setupLookupRouting() {

	}

	private void setupUserDefinedRouting() {

		MessageFlow mainFlow = this.getMainFlow();
		MessageFlow routeFlow = this.getRouteFlow();

		OutputNode outputNode = new OutputNode();
		outputNode.setNodeName(ROUTE_FLOW_OUTPUT_NODE);
		routeFlow.addNode(outputNode);

		Node recordProcessor = this.createRecordProcessorNode();
		Node routeNode = this.createRouteNode(recordProcessor
				.getOutputTerminal(RECORD_PROCESSOR_FLOW_OUTPUT_NODE));

		this.connectHttpRequest(
				routeNode.getOutputTerminal(ROUTE_FLOW_OUTPUT_NODE), "",
				"HTTP Request", 0);

		// Add note
		StickyNote note = new StickyNote(
				"Define your user-defined routing flow in Route.msgflow");
		Vector<Node> associatedNodes = new Vector<Node>();
		associatedNodes.add(routeNode);
		note.setAssociatedNodes(associatedNodes);
		mainFlow.addStickyNote(note);

	}

	/**
	 * Returns the Route subflow object
	 * 
	 * @return MessageFlow
	 */
	private MessageFlow getRouteFlow() {
		return this.patternInstanceManager.getMessageFlow(PATTERN_PROJECT_NAME,
				ROUTE_SUBFLOW);
	}

	/**
	 * Creates the RecordProcessor subflow node
	 * 
	 * @return Node
	 */
	private Node createRecordProcessorNode() {
		MessageFlow mainFlow = this.getMainFlow();
		MessageFlow recordProcessorFlow = this.patternInstanceManager
				.getMessageFlow(PATTERN_PROJECT_NAME, RECORD_PROCESSOR_SUBFLOW);

		SubFlowNode recordProcessorNode = new SubFlowNode();
		recordProcessorNode.setNodeName(RECORD_PROCESSOR_NODE);
		recordProcessorNode.setSubFlow(recordProcessorFlow);

		// Position
		Point fileInputNodePoint = mainFlow.getNodeByName(FILE_INPUT_NODE)
				.getLocation();
		Point point = new Point(fileInputNodePoint.x + 150,
				fileInputNodePoint.y);
		recordProcessorNode.setLocation(point);

		mainFlow.addNode(recordProcessorNode);

		// Connect node
		FileInputNode fileInputNode = (FileInputNode) mainFlow
				.getNodeByName(FILE_INPUT_NODE);
		mainFlow.connect(fileInputNode.OUTPUT_TERMINAL_OUT, recordProcessorNode
				.getInputTerminal(RECORD_PROCESSOR_FLOW_INPUT_NODE));

		return recordProcessorNode;
	}

	/**
	 * Creates the Route subflow node
	 * 
	 * @param input
	 * @return
	 */
	private Node createRouteNode(OutputTerminal input) {
		MessageFlow mainFlow = this.getMainFlow();
		MessageFlow routeFlow = this.getRouteFlow();

		// Create Route Node
		SubFlowNode routeSubflowNode = new SubFlowNode();
		routeSubflowNode.setNodeName(ROUTE_NODE);
		routeSubflowNode.setSubFlow(routeFlow);

		// Position
		Point recordProcessorNodePoint = mainFlow.getNodeByName(
				RECORD_PROCESSOR_NODE).getLocation();
		Point point = new Point(recordProcessorNodePoint.x + 150,
				recordProcessorNodePoint.y);
		routeSubflowNode.setLocation(point);

		mainFlow.addNode(routeSubflowNode);

		// Connect Node
		mainFlow.connect(input,
				routeSubflowNode.getInputTerminal(ROUTE_FLOW_INPUT_NODE));

		return routeSubflowNode;
	}

	/**
	 * 
	 * Creates and connects a HTTP request node
	 * 
	 * @param outputTerminal
	 * @param uri
	 * @param nodeName
	 * @param index
	 */
	private void connectHttpRequest(OutputTerminal outputTerminal, String uri,
			String nodeName, int index) {

		MessageFlow mainFlow = this.getMainFlow();

		// Create HTTPRequest Node
		HTTPRequestNode httpRequestNode = new HTTPRequestNode();
		httpRequestNode.setURLSpecifier(uri);
		
		//SJP : 13 July 2015: Fix implementation: Msg domain should be BLOB not JSON
		// httpRequestNode.setMessageDomainProperty(MESSAGE_DOMAIN);
		httpRequestNode.setMessageDomainProperty("BLOB");
		
		httpRequestNode.setNodeName(nodeName);
		


		// Position
		Node routeNode = mainFlow.getNodeByName(ROUTE_NODE);
		Point routeNodePoint = routeNode.getLocation();

		Point point = new Point(routeNodePoint.x + 200, routeNodePoint.y
				+ (150 * index));
		httpRequestNode.setLocation(point);

		mainFlow.addNode(httpRequestNode);

		mainFlow.connect(outputTerminal, httpRequestNode.INPUT_TERMINAL_IN);
	}


	/**
	 * Returns the RecordDistributor flow object
	 * 
	 * @return MessageFlow
	 */
	private MessageFlow getMainFlow() {
		return this.patternInstanceManager.getMessageFlow(PATTERN_PROJECT_NAME,
				RECORD_DISTRIBUTOR_FLOW);
	}

	/**
	 * Creates a sticky note and attaches it to a node
	 * 
	 * @param note
	 * @param targetNode
	 * @return
	 */
	private StickyNote createStickyNote(String note, Node targetNode) {

		StickyNote stickyNote = new StickyNote(note);

		if (targetNode != null) {
			Vector<Node> associatedNodes = new Vector<Node>();
			associatedNodes.add(targetNode);
			stickyNote.setAssociatedNodes(associatedNodes);
			// Position
			stickyNote.setLocation(targetNode.getLocation().x, 500);
		}

		return stickyNote;
	}

	/**
	 * Adds Sticky notes to the the RecordDistributor flow if a sample option is
	 * selected
	 */
	private void addSampleNotes() {
		MessageFlow mainFlow = this.getMainFlow();

		FileInputNode fileInputNode = (FileInputNode) mainFlow
				.getNodeByName(FILE_INPUT_NODE);

		String note = "To invoke the flow copy one or all JSON files \n "
				+ "from the \"" + this.patternInstanceName
				+ "_HTTP one-way Sample Artifacts\" project \n "
				+ "to the monitored input directory \n \""
				+ fileInputNode.getInputDirectory() + "\"";
		mainFlow.addStickyNote(this.createStickyNote(note, fileInputNode));

	}
}
