package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.MeritORflaw;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-25 23:27:39
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class MeritsNflawsEditorTableModel extends AbstractTableModel {

    private RetrievalDB mRetrievalDB;
    private ManipulationDB mManipulationDB;
    private Component mErrorMessageParent;
    private ArrayList<MeritORflaw> mList;
    private static final boolean DEBUG = false;
    private HashMap<Integer, Integer> mUsage;
    private int mNumberOfPlayedRoles;

    public MeritsNflawsEditorTableModel(RetrievalDB pRetrievalDB, ManipulationDB pManipulationDB, Component pErrorMessageParent) throws SQLException, RemoteException {
        mRetrievalDB = pRetrievalDB;
        mManipulationDB = pManipulationDB;
        mErrorMessageParent = pErrorMessageParent;
        refresh();
    }

    public void refresh() throws SQLException, RemoteException {
        Vector<MeritORflaw> merits = mRetrievalDB.getMerits();
        Vector<MeritORflaw> flaws = mRetrievalDB.getFlaws();
        mList = new ArrayList<MeritORflaw>(merits.size() + flaws.size() + 1);
        mList.addAll(merits);
        mList.addAll(flaws);
        mList.add(new MeritORflaw(-1, "", 0));
        mUsage = mRetrievalDB.getMeritsNflawsUsage();
        mNumberOfPlayedRoles = mRetrievalDB.getNumberOfPlayedRoles();
    }

    public float getUsage(MeritORflaw pMeritORflaw) {
        Integer number = mUsage.get(new Integer(pMeritORflaw.getId()));
        if (number != null) {
            return number.floatValue() / mNumberOfPlayedRoles;
        }
        else {
            return 0f;
        }
    }

    public float getUsage(int pRow) {
        MeritORflaw meritORflaw = mList.get(pRow);
        if(meritORflaw != null) {
            return getUsage(meritORflaw);
        } else {
            return 0f;
        }
    }

    public int getNumberOfRolesUsing(MeritORflaw pMeritORflaw) {
        Integer number = mUsage.get(new Integer(pMeritORflaw.getId()));
        if (number != null) {
            return number.intValue();
        }
        else {
            return 0;
        }
    }

    public int getNumberOfRolesUsing(int pRow) {
        MeritORflaw meritORflaw = mList.get(pRow);            
        if (meritORflaw != null) {
            return getNumberOfRolesUsing(meritORflaw);
        }
        else {
            return 0;
        }
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(mErrorMessageParent, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    public int getRowCount() {
        return mList.size();
    }

    public int getColumnCount() {
        return 2;
    }

    private static final String[] sColumnNames = {"Name", "Points"};

    public String getColumnName(int column) {
        return sColumnNames[column];
    }

    public Class getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return String.class;
        }
        else if (columnIndex == 1) {
            return Integer.class;
        }
        return Object.class;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        MeritORflaw meritORflaw = mList.get(rowIndex);
        try {
            if (meritORflaw.getId() > 0) {
                if (columnIndex == 0) {
                    if (aValue instanceof String) {
                        MeritORflaw toUpdate = new MeritORflaw(meritORflaw.getId(), (String) aValue, meritORflaw.getPoints());
                        mManipulationDB.updateMeritORflaw(toUpdate);
                        meritORflaw.setName((String) aValue);
                        fireTableCellUpdated(rowIndex, columnIndex);
                    }
                }
                else if (columnIndex == 1) {
                    if (aValue instanceof Number) {
                        Number num = (Number) aValue;
                        if (meritORflaw.getPoints() != num.intValue()) {
                            MeritORflaw toUpdate = new MeritORflaw(meritORflaw.getId(), meritORflaw.getName(), num.intValue());
                            mManipulationDB.updateMeritORflaw(toUpdate);
                            meritORflaw.setPoints(num.intValue());
                            fireTableCellUpdated(rowIndex, columnIndex);
                        }
                    }
                }
            }
            else {
                if (columnIndex == 0 && aValue instanceof String) {
                    MeritORflaw toAdd = mManipulationDB.addMeritORflaw((String) aValue);
                    if (toAdd != null) {
                        mList.add(rowIndex, toAdd);
                        fireTableRowsUpdated(rowIndex, rowIndex);
                        fireTableRowsInserted(rowIndex + 1, rowIndex + 1);
                    }
                }
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        MeritORflaw meritORflaw = mList.get(rowIndex);
        if (columnIndex == 0) {
            return meritORflaw.getName();
        }
        else if (columnIndex == 1) {
            return new Integer(meritORflaw.getPoints());
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
