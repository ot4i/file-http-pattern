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

package com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.editors;

import java.util.HashMap;

import com.ibm.broker.config.appdev.patterns.ui.*;

public abstract class SamplifiedEditor extends BasePatternPropertyEditor {
	
	public enum SAMPLE_CHOICE {
		NONE, SINGLE_DESTINATION, SPECIFIC_ROUTING, LOOKUP_ROUTING, USER_DEFINED_ROUTING
	};
	
	private HashMap<String, String> configurations;
	
	@Override
	public void configureEditor(PatternPropertyEditorSite site,
			boolean required, String configurationValues) {

		super.configureEditor(site, required, configurationValues);
		
		this.configurations = new HashMap<String, String>();
		
		String[] configurations = configurationValues.split(";");
		
		for (String configuration : configurations) {
			String[] configurationBits = configuration.split("=");
			if (configurationBits.length == 2) {
				this.configurations.put(configurationBits[0], configurationBits[1]);
			}
		}
		
	}

	protected String getConfiguration(String key) {
		if (this.configurations.containsKey(key)) {
			return this.configurations.get(key);
		} else {
			return "";
		}
	}

	@Override
	public abstract void createControls(Object arg0);
	
	protected abstract void sampleChoiceChanged(SAMPLE_CHOICE sampleChoice);
	
	@Override
	public void notifyChanged(String arg0, String arg1) {
		
		if (arg0.equals("includesample")) {
			SAMPLE_CHOICE sampleChoice = SAMPLE_CHOICE.NONE; 
			
			System.out.println(arg1);
			
			if (arg1.equals("Single Destination")) {
				sampleChoice = SAMPLE_CHOICE.SINGLE_DESTINATION;
			} else if (arg1.equals("Specify Routes")) {
				sampleChoice = SAMPLE_CHOICE.SPECIFIC_ROUTING;
			} else if (arg1.equals("Lookup Routing")) {
				sampleChoice = SAMPLE_CHOICE.LOOKUP_ROUTING;
			} else if (arg1.equals("User-defined Routing")) {
				sampleChoice = SAMPLE_CHOICE.USER_DEFINED_ROUTING;
			}
			
			this.sampleChoiceChanged(sampleChoice);
		}
		
		super.notifyChanged(arg0, arg1);
	}
}
