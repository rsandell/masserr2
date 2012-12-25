package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.OtherTrait;
import se.tdt.bobby.wodcc.data.IntWithString;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 *
 * Created: 2004-jan-14 12:27:33
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class OtherTraitsTableModel extends AbstractTableModel {

	private ArrayList mTraits;

	public OtherTraitsTableModel(int pInitSize) {
		mTraits = new ArrayList(pInitSize);
		for (int i = 0; i < pInitSize; i++) {
			mTraits.add(Void.class);
		}
	}

	public OtherTraitsTableModel(int pInitSize, List pInitList) {
		mTraits = new ArrayList(pInitSize);
		for (int i = 0; i < pInitList.size(); i++) {
			se.tdt.bobby.wodcc.data.OtherTrait otherTrait = (se.tdt.bobby.wodcc.data.OtherTrait) pInitList.get(i);
			mTraits.add(otherTrait);
		}
        int stop = Math.max(pInitSize - mTraits.size(), pInitList.size() + 1 - mTraits.size());
        stop = Math.max(stop, 1);
		for (int i = 0; i < stop; i++) {
			mTraits.add(Void.class);
		}
	}

	public int getRowCount() {
		return mTraits.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Integer.class;
		}
		return Object.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = mTraits.get(rowIndex);
		if (o instanceof se.tdt.bobby.wodcc.data.OtherTrait) {
			se.tdt.bobby.wodcc.data.OtherTrait trait = (se.tdt.bobby.wodcc.data.OtherTrait) o;
			switch (columnIndex) {
				case 0:
					return trait.getName();
				case 1:
					return new Integer(trait.getDots());
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
			mTraits.remove(rowIndex);
			if (intr.getNumber() >= 0) {
				mTraits.add(rowIndex, new se.tdt.bobby.wodcc.data.OtherTrait(intr.getNumber(), intr.getString()));
			}
			else {
				mTraits.add(rowIndex, Void.class);
			}
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
		else {
			Object o = mTraits.get(rowIndex);
			if (o instanceof se.tdt.bobby.wodcc.data.OtherTrait && columnIndex == 1) {
				se.tdt.bobby.wodcc.data.OtherTrait tr = (se.tdt.bobby.wodcc.data.OtherTrait) o;
				Number num = (Number) aValue;
				tr.setDots(num.intValue());
			}
		}
		if (rowIndex >= mTraits.size() - 1 && columnIndex <= 0) {
			mTraits.add(Void.class);
			fireTableRowsInserted(mTraits.size() - 1, mTraits.size() - 1);
		}
	}

	private static String[] sColumnNames = {"Other Traits", "Dots"};

	public String getColumnName(int column) {
		return sColumnNames[column];
	}

	public List getOtherTraits() {
		ArrayList li = new ArrayList();
		for (int i = 0; i < mTraits.size(); i++) {
			Object o = mTraits.get(i);
			if (o instanceof se.tdt.bobby.wodcc.data.OtherTrait) {
				li.add(o);
			}
		}
		return li;
	}
}
