package se.tdt.bobby.wodcc.ui.components.controllers;

import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 14:37:30
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class SpinnerTableCellEditor extends AbstractCellEditor implements TableCellEditor {
	private JSpinner mSpinner;


	public SpinnerTableCellEditor(int pMinimum, int pMaximum, int pInitialValue) {
		mSpinner = Utilities.createIntegerJSpinner(pMinimum, pMaximum, 1, pInitialValue);
	}

	public void setSpinnerAttributes(int pMinimum, int pMaximum, int pStepSize) {
		Utilities.changeSpinnerAttributes(mSpinner, pMinimum, pMaximum, pStepSize);
	}

	/**
	 * Returns the value contained in the editor.
	 * @return the value contained in the editor
	 */
	public Object getCellEditorValue() {
		return (Number)mSpinner.getValue();
	}

	/**
	 *  Sets an initial <code>value</code> for the editor.  This will cause
	 *  the editor to <code>stopEditing</code> and lose any partially
	 *  edited value if the editor is editing when this method is called. <p>
	 *
	 *  Returns the component that should be added to the client's
	 *  <code>Component</code> hierarchy.  Once installed in the client's
	 *  hierarchy this component will then be able to draw and receive
	 *  user input.
	 *
	 * @param	table		the <code>JTable</code> that is asking the
	 *				editor to edit; can be <code>null</code>
	 * @param	value		the value of the cell to be edited; it is
	 *				up to the specific editor to interpret
	 *				and draw the value.  For example, if value is
	 *				the string "true", it could be rendered as a
	 *				string or it could be rendered as a check
	 *				box that is checked.  <code>null</code>
	 *				is a valid value
	 * @param	isSelected	true if the cell is to be rendered with
	 *				highlighting
	 * @param	row     	the row of the cell being edited
	 * @param	column  	the column of the cell being edited
	 * @return	the component for editing
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
												 boolean isSelected,
												 int row, int column) {
		mSpinner.setValue(value);
		return mSpinner;
	}

}
