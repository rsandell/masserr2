package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 *
 * Created: 2004-jan-11 21:06:47
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class IntWithStringTableModel extends AbstractTableModel {

	private ArrayList mIntWithStrings;

	public IntWithStringTableModel(int pInitialNumber) {
		mIntWithStrings = new ArrayList(pInitialNumber);
		for (int i = 0; i < pInitialNumber; i++) {
			mIntWithStrings.add(Void.class);
		}
	}

	public IntWithStringTableModel(int pInitialNumber, List pInitList) {
		mIntWithStrings = new ArrayList(pInitialNumber);
		for (int i = 0; i < pInitList.size(); i++) {
			se.tdt.bobby.wodcc.data.IntWithString intWithString = (se.tdt.bobby.wodcc.data.IntWithString) pInitList.get(i);
			mIntWithStrings.add(intWithString);
		}
		for (int i = 0; i < pInitialNumber - pInitList.size() + 1; i++) {
			mIntWithStrings.add(Void.class);
		}
	}

	public int getRowCount() {
		return mIntWithStrings.size();
	}

	public int getColumnCount() {
		return 1;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = mIntWithStrings.get(rowIndex);
		if (o == Void.class) {
			return null;
		}
		else {
			return o;
		}
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		se.tdt.bobby.wodcc.data.IntWithString intr = (se.tdt.bobby.wodcc.data.IntWithString) aValue;
		mIntWithStrings.remove(rowIndex);
		if (intr.getNumber() >= 0) {
			mIntWithStrings.add(rowIndex, aValue);
		}
		else {
			mIntWithStrings.add(rowIndex, Void.class);
		}
		fireTableRowsUpdated(rowIndex, rowIndex);
		if(rowIndex >= mIntWithStrings.size()-1 && columnIndex <= 0) {
			mIntWithStrings.add(Void.class);
			fireTableRowsInserted(mIntWithStrings.size()-1, mIntWithStrings.size()-1);
		}
	}

	public String getColumnName(int column) {
		return "Name";
	}

	public List getList() {
		ArrayList li = new ArrayList();
		for (int i = 0; i < mIntWithStrings.size(); i++) {
			Object o = mIntWithStrings.get(i);
			if(o instanceof se.tdt.bobby.wodcc.data.IntWithString) {
				li.add(o);
			}
		}
		return li;
	}

}
