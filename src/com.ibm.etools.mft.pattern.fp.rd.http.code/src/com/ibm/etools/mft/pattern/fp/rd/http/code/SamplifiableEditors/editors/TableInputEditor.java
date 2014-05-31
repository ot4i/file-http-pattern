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

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


import com.ibm.broker.config.appdev.patterns.ui.*;
import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.SampleConfigurationsLoader;
import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.TableConfig;
import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.TableData;
import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.TableRow;
import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.editors.NewTableEntryDialog.TableEntryDialogType;


public class TableInputEditor extends SamplifiedEditor {

	private Table table;
	private boolean required;
	private Composite composite;
	
	@Override
	public void configureEditor(PatternPropertyEditorSite site,
			boolean required, String configurationValues) {
		// This method is called by the site immediately after the user-defined editor is created.
		// It provides the editor with its site interface and also some basic configuration such
		// as whether the pattern parameter is required (mandatory) or optional. If the pattern
		// author has set up some configuration values then they are also passed in here. 

		// Must call the base class to pass on the property site
		super.configureEditor(site, required, configurationValues);
		
		this.required = required;
	}

	@Override
	public void createControls(Object parent) {
		// This method is called with the parent Composite	
		this.composite = (Composite) parent; 
		
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
	
		
		// Add the controls for this user-defined editor
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);

		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		table.setLayoutData(gridData);

		

		//Create Columns
		if ( this.getConfiguration("columns") != null) {
			String[] configColumns = this.getConfiguration("columns").split(",");
			
			if (configColumns != null) {
				for (String columnTitle : configColumns) {
					org.eclipse.swt.widgets.TableColumn column = new org.eclipse.swt.widgets.TableColumn(table, SWT.NONE);
					column.setText(columnTitle);
					column.pack();
				}
			}
		}
		


		// This is invoked by SWT whenever the value of the text box changes.
		// This is the hint to tell our site that the value of this editor
		// has changed - the base class for this user-defined editor provides
		// the getSite() helper method (passed into configureEditor earlier!).

		table.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				PatternPropertyEditorSite site = getSite();
				site.valueChanged();
			}
		});
		
		Composite actionButtonsComposite = new Composite(composite, SWT.NONE);
		actionButtonsComposite.setLayout(new GridLayout(1, true));
		
		Button addBtn = new Button(actionButtonsComposite, SWT.PUSH);
		addBtn.setText("Add...");
		
		addBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				addNewItem();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		}); 
		
		
		Button editBtn = new Button(actionButtonsComposite, SWT.PUSH);
		editBtn.setText("Edit...");
		
		editBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (table.getSelectionCount() > 0) {
					editItem(table.getSelection()[0]);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		}); 
		
		Button removeBtn = new Button(actionButtonsComposite, SWT.PUSH);
		removeBtn.setText("Delete");
		
		removeBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (table.getSelectionCount() > 0 ) {
					table.remove(table.getSelectionIndex());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		}); 

		actionButtonsComposite.pack();

		
		composite.pack();
	}

	@Override
	public String isValid() {
		if (this.required) {
			return  (this.table.getItemCount() > 0) ? null : "Table is empty";
		} else {
			return null;
		}
		
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
			
			}
		}
	}

	@Override
	public String getValue() {
		// Return the current value of the user-defined editor to the site
		String value = "";

		
		for (TableItem item : this.table.getItems()) {
			
			for (int i = 0; i < this.table.getColumnCount(); i++) {			
				value += item.getText(i)+",";
			}
			
			value += '\n';
		}
		
		if (value.length() > 0) {
			return value;
		}
		
		return null;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// Notification that we should enable or disable the user-defined editor
		table.setEnabled(enabled);
	}

	@Override
	protected void sampleChoiceChanged(SAMPLE_CHOICE sampleChoice) {
		if (sampleChoice == SAMPLE_CHOICE.valueOf(this.getConfiguration("sampleOption"))) {
			this.table.removeAll();
			this.loadTableContents();
		} else {
			this.table.removeAll();
		}
	} 
	
	private void loadTableContents() {
		SampleConfigurationsLoader sampleConfig = SampleConfigurationsLoader.getInstance();
		
		String sampleContent = this.getConfiguration("sampleContentId");
		
		if (!sampleContent.isEmpty()) {
			TableConfig tableConfig = (TableConfig) sampleConfig.getSampleContent(sampleContent);
			
			if (tableConfig != null) {
				TableData tableData = tableConfig.getValue();
				
				for (TableRow row : tableData.getRows()) {
					TableItem item = new TableItem(this.table, SWT.NONE);
					int i = 0;
					for (String cellContent : row.getCells()) {
						item.setText(i, cellContent);
						i++;
					}
				}
			}
		}
		
		
	}
	
	private void addNewItem() {
		NewTableEntryDialog dialog = new NewTableEntryDialog(this.composite.getShell(), TableEntryDialogType.TEDT_ADD);
		
		for (int i = 0; i < this.table.getColumnCount(); i++) {	
			dialog.addColumn(this.table.getColumn(i).getText(), "");
		}
		
		
		if (dialog.open()) {
			
			ArrayList<String> values = dialog.getValues();
			
			int i = 0;
			TableItem item  = new TableItem(table, SWT.NONE);
			for (String value : values) {
				item.setText(i++, value);
			}
		}
		
		
	}
	
	private void editItem(TableItem item) {
		NewTableEntryDialog dialog = new NewTableEntryDialog(this.composite.getShell(),TableEntryDialogType.TEDT_EDIT);
		
		for (int i = 0; i < this.table.getColumnCount(); i++) {	
			dialog.addColumn(this.table.getColumn(i).getText(), item.getText(i));
		}
		
		if (dialog.open()) {
			
			ArrayList<String> values = dialog.getValues();

			for (int i = 0; i < values.size(); i++) {
				item.setText(i, values.get(i));
			}

		}
		
		
	}
	
	
}
