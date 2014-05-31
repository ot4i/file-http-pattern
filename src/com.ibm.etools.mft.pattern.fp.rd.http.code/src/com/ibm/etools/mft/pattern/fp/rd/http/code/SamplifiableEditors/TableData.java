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


import java.util.ArrayList;

public class TableData {
	public ArrayList<TableRow> rows;
	
	public TableData() {
		this.rows = new ArrayList<TableRow>();
	}
	
	public void addRow(TableRow row) {
		this.rows.add(row);
	}
	
	public ArrayList<TableRow> getRows() {
		return this.rows;
	}
	
}