package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.MeritORflaw;
import se.tdt.bobby.wodcc.data.IntWithString;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 *
 * Created: 2004-jan-13 20:51:33
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class MeritsNflawsTableModel extends AbstractTableModel {

	private ArrayList mMerits;
	private String mFirstColumnName;

	public MeritsNflawsTableModel(int pInitLength, String pFirstColumnName, List pInitList) {
		mFirstColumnName = pFirstColumnName;
		mMerits = new ArrayList(pInitLength);
		for (int i = 0; i < pInitList.size(); i++) {
			se.tdt.bobby.wodcc.data.MeritORflaw meritORflaw = (se.tdt.bobby.wodcc.data.MeritORflaw) pInitList.get(i);
			mMerits.add(meritORflaw);
		}
        int stop = Math.max(pInitLength - mMerits.size(), pInitList.size() + 1 - mMerits.size());
        stop = Math.max(stop, 1);
        for (int i = 0; i < stop; i++) {
            mMerits.add(Void.class);
        }
		/*for (int i = 0; i < pInitLength - pInitList.size() + 1; i++) {
			mMerits.add(Void.class);
		}*/
	}

	public MeritsNflawsTableModel(int pInitLength, String pFirstColumnName) {
		mFirstColumnName = pFirstColumnName;
		mMerits = new ArrayList(pInitLength);
		for (int i = 0; i < pInitLength; i++) {
			mMerits.add(Void.class);
		}
	}


	public int getRowCount() {
		return mMerits.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = mMerits.get(rowIndex);
		if (o == Void.class) {
			return null;
		}
		else {
			se.tdt.bobby.wodcc.data.MeritORflaw merit = (se.tdt.bobby.wodcc.data.MeritORflaw) o;
			switch (columnIndex) {
				case 0:
					return merit.getName();
				case 1:
					return merit.getNote();
			}
		}
		return null;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	private static String[] sColumnNames = {"Name", "Notes"};

	public String getColumnName(int column) {
		if (column == 0) {
			return mFirstColumnName;
		}
		return sColumnNames[column];
	}

	public int getSum() {
		int sum = 0;
		for (int i = 0; i < mMerits.size(); i++) {
			Object o = mMerits.get(i);
			if (o instanceof se.tdt.bobby.wodcc.data.MeritORflaw) {
				sum += ((se.tdt.bobby.wodcc.data.MeritORflaw) o).getPoints();
			}
		}
		return sum;
	}

	public List getSelectedMeritsORflaws() {
		ArrayList list = new ArrayList();
		for (int i = 0; i < mMerits.size(); i++) {
			Object o = mMerits.get(i);
			if (o instanceof se.tdt.bobby.wodcc.data.MeritORflaw) {
				list.add(o);
			}
		}
		return list;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			if (aValue instanceof se.tdt.bobby.wodcc.data.MeritORflaw) {
				se.tdt.bobby.wodcc.data.MeritORflaw merit = (se.tdt.bobby.wodcc.data.MeritORflaw) ((se.tdt.bobby.wodcc.data.MeritORflaw) aValue).clone();
				mMerits.remove(rowIndex);
				mMerits.add(rowIndex, merit);
			}
			else if (aValue instanceof se.tdt.bobby.wodcc.data.IntWithString) {
				mMerits.remove(rowIndex);
				mMerits.add(rowIndex, Void.class);
			}
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
		else {
			Object o = mMerits.get(rowIndex);
			if (o instanceof se.tdt.bobby.wodcc.data.MeritORflaw) {
				se.tdt.bobby.wodcc.data.MeritORflaw merit = (se.tdt.bobby.wodcc.data.MeritORflaw) o;
				merit.setNote((String) aValue);
			}
		}
		if (rowIndex >= mMerits.size() - 1 && columnIndex <= 0) {
			mMerits.add(Void.class);
			fireTableRowsInserted(mMerits.size() - 1, mMerits.size() - 1);
		}
	}
}
