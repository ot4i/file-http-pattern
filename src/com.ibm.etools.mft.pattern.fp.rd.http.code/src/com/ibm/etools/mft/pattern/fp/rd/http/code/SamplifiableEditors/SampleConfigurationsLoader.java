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

package com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors;


import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SampleConfigurationsLoader {
	
	/**
	 * Pattern's preset configuration data
	 */
	private static final String XML_DATA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<sample>\r\n" + 
			"	<entry id=\"routing_table\" type=\"table\">\r\n" + 
			"		<rows>\r\n" + 
			"			<row>\r\n" + 
			"				<data>$Root/JSON/Data/country</data>\r\n" + 
			"				<data>United Kingdom</data>\r\n" + 
			"				<data>http://localhost:3000/country/uk</data>\r\n" + 
			"			</row>\r\n" + 
			"			<row>\r\n" + 
			"				<data>$Root/JSON/Data/country</data>\r\n" + 
			"				<data>United States</data>\r\n" + 
			"				<data>http://localhost:3000/country/us</data>\r\n" + 
			"			</row>\r\n" + 
			"		</rows>\r\n" + 
			"	</entry>\r\n" + 
			"	\r\n" + 
			"	<entry id=\"web_service_url\" type=\"text\">http://localhost:3000/country/default</entry>\r\n" + 
			"	<entry id=\"input_directory_path\" type=\"text\">{temp_directory}file_to_http_sample</entry>\r\n" + 
			"\r\n" + 
			"</sample>";
	
	public static SampleConfigurationsLoader INSTANCE;
	
	private HashMap<String, ElementConfig<?>> properties;
	
	public static SampleConfigurationsLoader getInstance() {
		
		if (INSTANCE == null) {
			INSTANCE = new SampleConfigurationsLoader();
		}
		return INSTANCE;
	}
	
	public SampleConfigurationsLoader() {
		this.properties = new HashMap<String, ElementConfig<?>>();
		this.parse();
	}
	
	private void parse() {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(XML_DATA)));
			doc.getDocumentElement().normalize();
			
			NodeList entryNodes = doc.getElementsByTagName("entry");

			 
			for (int entryIndex = 0; entryIndex < entryNodes.getLength(); entryIndex++) {
				 
				Node entryNode = entryNodes.item(entryIndex);
		 
				if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element entryElement = (Element) entryNode;
					
					String entryType = entryNode.getAttributes().getNamedItem("type").getNodeValue();
					String entryId = entryNode.getAttributes().getNamedItem("id").getNodeValue();
					
					if (entryType.equals("text")) {
						String content = ContentFormatter.format(entryNode.getTextContent());
						this.properties.put(entryId, new TextConfig(content));
					} else if (entryType.equals("table")) {
						NodeList rowElements = entryElement.getElementsByTagName("row");
						TableData tableData = new TableData();
						 
						for (int rowIndex = 0; rowIndex < rowElements.getLength(); rowIndex++) {
							 
							Node rowNode = rowElements.item(rowIndex);
							
							if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
					 
								Element rowElement = (Element) rowNode;
								TableRow row = new TableRow();
					 
								NodeList dataElements = rowElement.getElementsByTagName("data");
								 
								for (int dataIndex = 0; dataIndex < dataElements.getLength(); dataIndex++) {
									Node dataNode = dataElements.item(dataIndex);
									if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
										Element dataElement = (Element) dataNode;
										String content = dataElement.getTextContent();	
										row.addCell(ContentFormatter.format(content));
									}
								}
								
								tableData.addRow(row);
							}
						}
						
						this.properties.put(entryId, new TableConfig(tableData));
					}
					
				}
			}
			 
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	public ElementConfig<?> getSampleContent(String sampleContentId) {
		return this.properties.get(sampleContentId);
	}
}

class ContentFormatter {
	public static String format(String content) {
		return (new ContentFormatter()).parse(content);
	}
	
	private String parse(String value) {
		
		boolean variable_open = false;
		String variable_name = ""; 
		String finalText = "";
		
		for (int i = 0; i < value.length(); i++) {
			//Look for variables
			if (value.charAt(i) == '{') {
				variable_open = true;
			} else if (value.charAt(i) == '}') {
				
				if (variable_open) {
					
					finalText += this.transformVariable(variable_name);
					
					variable_open = false;
					variable_name = "";
				}
			} else {
				if (!variable_open) {
					finalText += value.charAt(i);
				} else {
					variable_name += value.charAt(i);
				}
			}

		}
		
		
		return finalText;
	}
	
	private String transformVariable(String variable) {
		if (variable.equals("temp_directory")) {
			return System.getProperty("java.io.tmpdir");
		}
		
		return "";
	}
}