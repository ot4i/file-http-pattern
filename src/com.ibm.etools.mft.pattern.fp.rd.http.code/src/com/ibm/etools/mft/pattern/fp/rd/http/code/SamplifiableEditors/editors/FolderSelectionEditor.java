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


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.ibm.broker.config.appdev.patterns.ui.PatternPropertyEditorSite;
import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.SampleConfigurationsLoader;
import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.TextConfig;

public class FolderSelectionEditor extends SamplifiedEditor {

	private Text text;

	@Override
	public void configureEditor(PatternPropertyEditorSite site,
			boolean required, String configurationValues) {
		// This method is called by the site immediately after the user-defined editor is created.
		// It provides the editor with its site interface and also some basic configuration such
		// as whether the pattern parameter is required (mandatory) or optional. If the pattern
		// author has set up some configuration values then they are also passed in here. 

		// Must call the base class to pass on the property site
		super.configureEditor(site, required, configurationValues);

		
	}

	@Override
	public void createControls(Object parent) {
		// This method is called with the parent Composite	
		final Composite composite = (Composite) parent;
		
		 
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		
		// Add the controls for this user-defined editor
		text = new Text(composite, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		text.setLayoutData(gridData);
		
		// Add SWT layout information so the text box grows and shrinks
		// whenever the pattern user moves the sash between the pattern
		// parameters and the browser showing the pattern parameter help.


		// This is invoked by SWT whenever the value of the text box changes.
		// This is the hint to tell our site that the value of this editor
		// has changed - the base class for this user-defined editor provides
		// the getSite() helper method (passed into configureEditor earlier!).

		text.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				PatternPropertyEditorSite site = getSite();
				site.valueChanged();
			}
		});
		
		Button browseBtn = new Button(composite, SWT.NONE);
		browseBtn.setText("Browse");
		
		browseBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				 DirectoryDialog browseDialog = new DirectoryDialog(composite.getShell());
				 String dialogResult = browseDialog.open();
				 if (dialogResult != null) {
					 text.setText(dialogResult);
			 	 }
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		composite.pack();
		

	}

	@Override
	public void setValue(String value) {

		// Sets our current value from the site - this can be one of four cases:

		//	1. A null value indicating a new pattern instance is being opened
		//		and this pattern parameter does not have a default value.
		//		The pattern parameter also does not have a watermark defined.
		//	2. The default value indicating a new pattern instance is being opened
		//		and this pattern parameter has been configured with a default value.
		//	3. A watermark string beginning with < and ending with a > character.
		//		This is the case when the pattern parameter has been marked as 
		//		required (mandatory), no default value has been specified and
		//		a watermark has also been configured to help guide the pattern user.
		//	4. The current value passed in is the pattern parameter value previously 
		//		saved to a pattern instance configuration file. In other words, 
		//		the pattern instance is being re-opened by the pattern user.

		// A good starting point is to simply ignore null values and watermarks!

		
		
		if (value != null) {
			if (value.startsWith("<") == false) {
				text.setText(value);
			}
		}
	}

	@Override
	public String getValue() {
		// Return the current value of the user-defined editor to the site
		String value = text.getText();
		if (value.length() > 0) {
			return value;
		}
		return null;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// Notification that we should enable or disable the user-defined editor
		text.setEnabled(enabled);
	}

	@Override
	protected void sampleChoiceChanged(SAMPLE_CHOICE sampleChoice) {
		switch (sampleChoice) {
			case NONE:
				this.text.setText("");
			break;
			default:
				SampleConfigurationsLoader sampleConfig = SampleConfigurationsLoader.getInstance();
				
				TextConfig textConfig = (TextConfig) sampleConfig.getSampleContent(this.getConfiguration("sampleContentId"));
				
				if (textConfig != null) {
					this.text.setText(textConfig.getValue());
				}
			break;
		}
	}
	
	
}
