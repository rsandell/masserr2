package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.Profession;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

/**
 * Description
 *
 *
 * Created: 2004-feb-02 22:42:14
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class ProfessionsTableModel extends AbstractTableModel {

	private List mProfessions;
	private boolean mShowMaskColumn;

	public ProfessionsTableModel(int pInitLength, boolean pShowMaskColumn) {
		mProfessions = new ArrayList(pInitLength);
		for (int i = 0; i < pInitLength; i++) {
			mProfessions.add(Void.class);
		}
		mShowMaskColumn = pShowMaskColumn;
	}

	public ProfessionsTableModel(int pInitLength) {
		this(pInitLength, true);
	}

	public int getRowCount() {
		return mProfessions.size();
	}

	public int getColumnCount() {
		if (mShowMaskColumn) {
			return 2;
		}
		else {
			return 1;
		}
	}

	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return se.tdt.bobby.wodcc.data.Profession.class;
			case 1:
				return Boolean.class;
		}
		return Object.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	private String[] sColumnNames = {"Name", "mask"};

	public String getColumnName(int column) {
		return sColumnNames[column];
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue instanceof se.tdt.bobby.wodcc.data.Profession && columnIndex == 0) {
			mProfessions.remove(rowIndex);
			mProfessions.add(rowIndex, aValue);
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
		else if (columnIndex == 1 && aValue instanceof Boolean) {
			Object o = mProfessions.get(rowIndex);
			if (o != null && o instanceof se.tdt.bobby.wodcc.data.Profession) {
				se.tdt.bobby.wodcc.data.Profession p = (se.tdt.bobby.wodcc.data.Profession) o;
				p.setMask(((Boolean) aValue).booleanValue());
				fireTableCellUpdated(rowIndex, columnIndex);
			}
		} else if(columnIndex == 0) {
			mProfessions.remove(rowIndex);
			mProfessions.add(rowIndex, Void.class);
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
		if (rowIndex >= mProfessions.size() - 1 && columnIndex <= 0) {
			mProfessions.add(Void.class);
			fireTableRowsInserted(mProfessions.size() - 1, mProfessions.size() - 1);
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = mProfessions.get(rowIndex);
		if (o == Void.class) {
			return null;
		}
		else {
			if (o != null && o instanceof se.tdt.bobby.wodcc.data.Profession) {
				se.tdt.bobby.wodcc.data.Profession p = (se.tdt.bobby.wodcc.data.Profession) o;
				switch (columnIndex) {
					case 0:
						return p.getName();
					case 1:
						return new Boolean(p.isMask());
				}
			}
		}
		return null;
	}

	public List getProfessions() {
		ArrayList list = new ArrayList();
		for (int i = 0; i < mProfessions.size(); i++) {
			Object o = mProfessions.get(i);
			if (o instanceof se.tdt.bobby.wodcc.data.Profession) {
				list.add(o);
			}
		}
		return list;
	}

	public int getIncomeSum() {
		int sum = 0;
		for (int i = 0; i < mProfessions.size(); i++) {
			Object o = mProfessions.get(i);
			if (o instanceof se.tdt.bobby.wodcc.data.Profession) {
				se.tdt.bobby.wodcc.data.Profession p = (se.tdt.bobby.wodcc.data.Profession) o;
				if (p.isMask()) {
					sum += (p.getMonthlyIncome() * se.tdt.bobby.wodcc.data.Profession.MASK_INCOME_FACTOR);
				}
				else {
					sum += p.getMonthlyIncome();
				}
			}
		}
		return sum;
	}

	public void setProfessions(List pProfessions) {
		mProfessions = pProfessions;
		mProfessions.add(Void.class);
		fireTableDataChanged();
	}
}
