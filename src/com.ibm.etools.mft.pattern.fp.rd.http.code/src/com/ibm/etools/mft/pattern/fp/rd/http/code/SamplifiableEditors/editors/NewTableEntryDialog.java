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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ibm.etools.mft.pattern.fp.rd.http.code.SamplifiableEditors.editors.NewTableEntryDialog.TableEntryDialogType;

public class NewTableEntryDialog extends Dialog {
	
	private ArrayList<String[]> columns;
	private ArrayList<Text> textControls;
	private  ArrayList<String> results;
	protected boolean result;
	private TableEntryDialogType dialogType;
	
	public enum TableEntryDialogType {
		TEDT_ADD,
		TEDT_EDIT
	};

	public NewTableEntryDialog(Shell parent, TableEntryDialogType type) {
		super(parent);
		this.columns = new ArrayList<String[]>();
		this.results = new ArrayList<String>();
		this.textControls = new ArrayList<Text>();
		
		this.dialogType = type;
		
		if (type == TableEntryDialogType.TEDT_ADD) {
			this.setText("Add Entry");
		} else if (type == TableEntryDialogType.TEDT_EDIT) {
			this.setText("Edit Entry");
		}
		
	}
	
	public void addColumn(String label, String value) {
		this.columns.add(new String[] { label, value });
	}
	
	public ArrayList<String> getValues() {
		return this.results;
	}
	
	public boolean open() {
		Display display = getParent().getDisplay();
		final Shell shell = new Shell(this.getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		
		
		shell.setSize(520, 200);
		shell.setLayout(new GridLayout(1, true));
		shell.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		//Move to centre of parent
		Rectangle displayRect = display.getPrimaryMonitor().getBounds();
		Rectangle shellRect = shell.getBounds();
		shell.setLocation((displayRect.width - shellRect.width) / 2, (displayRect.height - shellRect.height) / 2);
	
		Composite container = new Composite(shell, SWT.FILL);
		GridLayout layout = new GridLayout(2, false);
		GridData containerGridData  = new GridData(SWT.FILL, SWT.FILL, true, true);
		containerGridData.widthHint = 400;
		container.setLayoutData(containerGridData);
		container.setLayout(layout);

		for (String[] column : this.columns) {
			Label editorLabel  = new Label(container, SWT.NONE);
			editorLabel.setText(column[0]);
			
			if (!column[1].isEmpty()) {
				
			}
		
			Text textEditor = new Text(container, SWT.BORDER);
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			textEditor.setLayoutData(gridData);
			textEditor.setSize(500, 20);
			
			if (!column[1].isEmpty()) {
				textEditor.setText(column[1]);	
			}
			
			textControls.add(textEditor);
		}
		
		//Buttons 
		Button addBtn = new Button(container, SWT.NONE);
		if (this.dialogType == TableEntryDialogType.TEDT_ADD) {
			addBtn.setText("Add");
		} else if (this.dialogType == TableEntryDialogType.TEDT_EDIT) {
			addBtn.setText("Edit");
		}
		addBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				for (Text textControl : textControls) {
					results.add(textControl.getText());
				}
				
				shell.dispose();
				result = true;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button cancelBtn = new Button(container, SWT.NONE);
		cancelBtn.setText("Cancel");
		cancelBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
				result = false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
		container.pack();

		shell.pack();
		shell.open();
		shell.setText(this.getText());
		
		
		
		while (!shell.isDisposed())
	    {
	        if (!display.readAndDispatch())
	        	display.sleep();
	    }
	
		
		return result;
		
	}


}
