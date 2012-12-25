package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.Influence;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 *
 * Created: 2004-feb-03 01:24:19
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class InfluencesTableModel extends AbstractTableModel {

	private ArrayList mInfluences;
	private int mInitSize;

	public InfluencesTableModel(int pInitSize) {
		mInfluences = new ArrayList(pInitSize);
		for (int i = 0; i < pInitSize; i++) {
			mInfluences.add(Void.class);
		}
		mInitSize = pInitSize;
	}

	public InfluencesTableModel(int pInitSize, List pInitList) {
		mInfluences = new ArrayList(pInitSize);
		for (int i = 0; i < pInitList.size(); i++) {
			se.tdt.bobby.wodcc.data.Influence inf = (se.tdt.bobby.wodcc.data.Influence) pInitList.get(i);
			mInfluences.add(inf);
		}
		for (int i = 0; i < pInitSize - pInitList.size() + 1; i++) {
			mInfluences.add(Void.class);
		}
		mInitSize = pInitSize;
	}

	public int getRowCount() {
		return mInfluences.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return se.tdt.bobby.wodcc.data.Influence.class;
			case 1:
				return Integer.class;
		}
		return Object.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = mInfluences.get(rowIndex);
		if (o instanceof se.tdt.bobby.wodcc.data.Influence) {
			se.tdt.bobby.wodcc.data.Influence influence = (se.tdt.bobby.wodcc.data.Influence) o;
			switch (columnIndex) {
				case 0:
					return influence.getName();
				case 1:
					return new Integer(influence.getDots());
			}
		}
		if (columnIndex == 0) {
			return null;
		}
		else {
			return new Integer(0);
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue instanceof se.tdt.bobby.wodcc.data.IntWithString) {
			se.tdt.bobby.wodcc.data.IntWithString intr = (se.tdt.bobby.wodcc.data.IntWithString) aValue;
			mInfluences.remove(rowIndex);
			if (intr.getNumber() >= 0) {
				mInfluences.add(rowIndex, new se.tdt.bobby.wodcc.data.Influence(intr.getNumber(), intr.getString()));
			}
			else {
				mInfluences.add(rowIndex, Void.class);
			}
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
		else {
			Object o = mInfluences.get(rowIndex);
			if (o instanceof se.tdt.bobby.wodcc.data.Influence && columnIndex == 1) {
				se.tdt.bobby.wodcc.data.Influence tr = (se.tdt.bobby.wodcc.data.Influence) o;
				Number num = (Number) aValue;
				tr.setDots(num.intValue());
			}
		}
		if (rowIndex >= mInfluences.size() - 1 && columnIndex <= 0) {
			mInfluences.add(Void.class);
			fireTableRowsInserted(mInfluences.size() - 1, mInfluences.size() - 1);
		}
	}

	private static String[] sColumnNames = {"Influences", "Dots"};

	public String getColumnName(int column) {
		return sColumnNames[column];
	}

	public List getInfluences() {
		ArrayList li = new ArrayList();
		for (int i = 0; i < mInfluences.size(); i++) {
			Object o = mInfluences.get(i);
			if (o instanceof se.tdt.bobby.wodcc.data.Influence) {
				li.add(o);
			}
		}
		return li;
	}

	public void setInfluences(List pInfluences) {
		mInfluences = new ArrayList(pInfluences.size() + 1);
		for (int i = 0; i < pInfluences.size(); i++) {
			se.tdt.bobby.wodcc.data.Influence influence = (se.tdt.bobby.wodcc.data.Influence) pInfluences.get(i);
			mInfluences.add(new se.tdt.bobby.wodcc.data.Influence(influence.getId(), influence.getName(), influence.getDots()));
		}
		mInfluences.add(Void.class);
		fireTableDataChanged();
	}

	public void clear() {
		mInfluences = new ArrayList(mInitSize);
		for (int i = 0; i < mInitSize; i++) {
			mInfluences.add(Void.class);
		}
		fireTableDataChanged();
	}
}
