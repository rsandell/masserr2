package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.Ability;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

/**
 * Description
 *
 *
 * Created: 2004-jan-10 14:13:00
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class EditAbilitiesTableModel extends AbstractTableModel {

	private List mAbilities;

	public EditAbilitiesTableModel(List pAbilities) {
		mAbilities = pAbilities;
	}

	public EditAbilitiesTableModel(List pAbilities, List pPreSetAbilities) {
		mAbilities = pAbilities;
		for (int i = 0; i < pPreSetAbilities.size(); i++) {
			se.tdt.bobby.wodcc.data.Ability ability = (se.tdt.bobby.wodcc.data.Ability) pPreSetAbilities.get(i);
			for (int j = 0; j < mAbilities.size(); j++) {
				se.tdt.bobby.wodcc.data.Ability setAbility = (se.tdt.bobby.wodcc.data.Ability) mAbilities.get(j);
				if (setAbility.getId() == ability.getId()) {
					setAbility.setDots(ability.getDots());
					setAbility.setSpecialisation(ability.getSpecialisation());
				}
			}
		}
	}

	/**
	 *  Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
	 *
	 *  @param columnIndex  the column being queried
	 *  @return the Object.class
	 */
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return Integer.class;
		}
		return Object.class;
	}

	private static String[] sColumnNames = {"", "Specialisation", "Dots"};

	/**
	 *
	 * @param column  the column being queried
	 * @return a string containing the default name of <code>column</code>
	 */
	public String getColumnName(int column) {
		return sColumnNames[column];
	}

	/**
	 *  @param  rowIndex  the row being queried
	 *  @param  columnIndex the column being queried
	 *  @return (columnIndex == 1 || columnIndex == 2)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (rowIndex == 0) {
			return false;
		}
		return (columnIndex == 1 || columnIndex == 2);
	}

	/**
	 * Returns the number of rows in the model. A
	 * <code>JTable</code> uses this method to determine how many rows it
	 * should display.  This method should be quick, as it
	 * is called frequently during rendering.
	 *
	 * @return the number of rows in the model
	 * @see #getColumnCount
	 */
	public int getRowCount() {
		return mAbilities.size() + 1;
	}

	/**
	 * Returns the number of columns in the model. A
	 * <code>JTable</code> uses this method to determine how many columns it
	 * should create and display by default.
	 *
	 * @return the number of columns in the model
	 * @see #getRowCount
	 */
	public int getColumnCount() {
		return 3;
	}

	/**
	 * Returns the value for the cell at <code>columnIndex</code> and
	 * <code>rowIndex</code>.
	 *
	 * @param	rowIndex	the row whose value is to be queried
	 * @param	columnIndex 	the column whose value is to be queried
	 * @return	the value Object at the specified cell
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex == 0) {
			return sColumnNames[columnIndex];
		}
		else {
			se.tdt.bobby.wodcc.data.Ability ability = (se.tdt.bobby.wodcc.data.Ability) mAbilities.get(rowIndex - 1);
			switch (columnIndex) {
				case 0:
					return ability.getName();
				case 1:
					return ability.getSpecialisation();
				case 2:
					return new Integer(ability.getDots());
			}
		}
		return null;
	}

	/**
	 *  This empty implementation is provided so users don't have to implement
	 *  this method if their data model is not editable.
	 *
	 *  @param  aValue   value to assign to cell
	 *  @param  rowIndex   row of cell
	 *  @param  columnIndex  column of cell
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		se.tdt.bobby.wodcc.data.Ability ability = (se.tdt.bobby.wodcc.data.Ability) mAbilities.get(rowIndex - 1);
		if (ability != null) {
			switch (columnIndex) {
				case 0:
					ability.setName((String) aValue);
					fireTableCellUpdated(rowIndex, 0);
					break;
				case 1:
					ability.setSpecialisation((String) aValue);
					fireTableCellUpdated(rowIndex, 1);
					break;
				case 2:
					Number i = (Number) aValue;
					ability.setDots(i.intValue());
					fireTableCellUpdated(rowIndex, 2);
					break;
			}
		}
	}

	public List getAbilities() {
		ArrayList li = new ArrayList();
		for (int i = 0; i < mAbilities.size(); i++) {
			se.tdt.bobby.wodcc.data.Ability ability = (se.tdt.bobby.wodcc.data.Ability) mAbilities.get(i);
			if (ability.getDots() > 0) {
				li.add(ability);
			}
		}
		return li;
	}

	public int getSum() {
		int sum = 0;
		for (int i = 0; i < mAbilities.size(); i++) {
			se.tdt.bobby.wodcc.data.Ability ability = (se.tdt.bobby.wodcc.data.Ability) mAbilities.get(i);
			sum += ability.getDots();
		}
		return sum;
	}
}
