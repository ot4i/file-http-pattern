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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class SummaryFileGenerator {
	
	
	private final static String SUMMARY_RESOURCES_PATH = "summary_resources";
	private HashMap<String, String> properties;
	private ArrayList<String> additionalResources;
	
	public SummaryFileGenerator() {
		this.properties = new HashMap<String, String>();
		this.additionalResources = new ArrayList<String>();
	}
	
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	public void addAdditionalResource(String file) {
		this.additionalResources.add(file);
	}
	
	/**
	 * Generate the summary file and all additional resources
	 * @param workspaceLocation
	 * @param patternInstanceName
	 */
	public void generate(String workspaceLocation, String patternInstanceName) {
		
		String patternInstancePath = workspaceLocation + File.separator + patternInstanceName + File.separator + "Pattern Configuration";
		
		String summaryFileDest = patternInstancePath + File.separator + patternInstanceName + "_summary.html";
		
		Writer writer = null;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(summaryFileDest)));
			//writer.write(this.parseContent(HTML_CONTENT));
			
			writer.write(this.parseContent(this.getSummaryFileContent()));
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
		
		//Add additional resources
		for (String filename : this.additionalResources) {
			String destPath = patternInstancePath + File.separator + filename;
			this.copyAdditionalResourceFile(filename, destPath);
		}
	}
	
	/**
	 * Copy an additional resources file to the pattern's instance directory
	 * @param filename
	 * @param dest
	 */
	private void copyAdditionalResourceFile(String filename, String dest) {
		InputStream  reader = SummaryFileGenerator.class.getResourceAsStream(
						SUMMARY_RESOURCES_PATH + File.separator + filename);
		
		FileOutputStream writer = null;
		
		try {
			writer = new FileOutputStream(dest);
		
			byte[] buffer = new byte[4096]; //Init buffer
			int byteCount;
			//Copy bytes
			while( (byteCount = reader.read(buffer)) >= 0 ) {
			  writer.write(buffer, 0, byteCount);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Get the HTML content of the summary file
	 * @return
	 */
	private String getSummaryFileContent() {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(SummaryFileGenerator.class.getResourceAsStream(SUMMARY_RESOURCES_PATH + File.separator + "summary.html")));
		String content = "";
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				content += line + '\n';
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return content;
	}
	
	/**
	 * Go through content and replace variables with values
	 * @param content
	 * @return
	 */
	private String parseContent(String content) {
		
		String parsedContent = "";
		
		int curIndex = -1;
		
		while (++curIndex < content.length()) {
			
			char curChar = content.charAt(curIndex);
			
			if (curIndex + 1 < content.length()) {
				
				char nextChar = content.charAt(curIndex + 1);
				
				if (curChar == '{' && nextChar == '%') {
					curIndex++;
					String curVar = ""; 
					
					do {
						curChar = content.charAt(++curIndex);
						curVar += curChar;
					} while (curIndex < content.length() &&
							(content.charAt(curIndex + 1) != '%' && 
							content.charAt(curIndex + 2) != '}'));
					
					if (this.properties.containsKey(curVar)) {
						parsedContent += this.properties.get(curVar);
					}
					
					curIndex += 2; //Skip % and }
					
				} else {
					parsedContent += curChar;
				}
			} else { //fix to missing last char
				parsedContent += curChar;
			}
			
			
		}
	
		
		return parsedContent;
	}
}
