package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-26 00:04:44
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class OtherTraitsEditorTableModel extends AbstractTableModel {

    private RetrievalDB mRetrievalDB;
    private ManipulationDB mManipulationDB;
    private Component mErrorMessageParent;
    private Vector<IntWithString> mOtherTraits;
    private static final boolean DEBUG = false;
    private HashMap<Integer, Integer> mOtherTraitsStat;
    private int mNumberOfPlayedRoles;

    public OtherTraitsEditorTableModel(RetrievalDB pRetrievalDB, ManipulationDB pManipulationDB, Component pErrorMessageParent) throws SQLException, RemoteException {
        mRetrievalDB = pRetrievalDB;
        mManipulationDB = pManipulationDB;
        mErrorMessageParent = pErrorMessageParent;
        refresh();
    }

    public void refresh() throws SQLException, RemoteException {
        mOtherTraits = mRetrievalDB.getOtherTraitNames();
        mOtherTraits.add(new IntWithString(-1, ""));
        mOtherTraitsStat = mRetrievalDB.getOtherTraitsUsage();
        mNumberOfPlayedRoles = mRetrievalDB.getNumberOfPlayedRoles();
    }

    public int getNumberOfRolesUsing(int pRow) {
        IntWithString other = mOtherTraits.get(pRow);
        if (other != null) {
            return getNumberOfRolesUsing(other);
        }
        else {
            return 0;
        }
    }

    public int getNumberOfRolesUsing(IntWithString pOtherTrait) {
        Integer number = mOtherTraitsStat.get(new Integer(pOtherTrait.getNumber()));
        if (number != null) {
            return number.intValue();
        }
        else {
            return 0;
        }
    }

    public float getUsage(IntWithString pOtherTrait) {
        float usage = 0f;
        Integer number = mOtherTraitsStat.get(new Integer(pOtherTrait.getNumber()));
        if (number != null) {
            usage = number.floatValue() / mNumberOfPlayedRoles;
        }
        return usage;
    }

    public float getUsage(int pRow) {
        IntWithString otherTrait = mOtherTraits.get(pRow);
        if (otherTrait != null) {
            return getUsage(otherTrait);
        }
        else {
            return 0f;
        }
    }

    public int getRowCount() {
        return mOtherTraits.size();
    }

    public String getColumnName(int column) {
        return "Name";
    }

    public int getColumnCount() {
        return 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return mOtherTraits.get(rowIndex).getString();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        IntWithString trait = mOtherTraits.get(rowIndex);
        try {
            if (aValue instanceof String) {
                if (trait.getNumber() > 0) {
                    IntWithString toUpdate = new IntWithString(trait.getNumber(), (String) aValue);
                    mManipulationDB.updateOtherTrait(toUpdate);
                    trait.setString((String) aValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
                else {
                    IntWithString added = mManipulationDB.addOtherTrait((String) aValue);
                    mOtherTraits.add(rowIndex, added);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    fireTableRowsInserted(rowIndex + 1, rowIndex + 1);
                }
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(mErrorMessageParent, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }


}
