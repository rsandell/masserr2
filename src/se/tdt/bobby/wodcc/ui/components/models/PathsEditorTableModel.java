package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-25 21:47:46
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public abstract class PathsEditorTableModel extends AbstractTableModel {

    protected RetrievalDB mRetrievalDB;
    protected ManipulationDB mManipulationDB;
    private JComponent mErrorMessageParent;
    private Vector<IntWithString> mPaths;
    private static final boolean DEBUG = false;

    protected PathsEditorTableModel(RetrievalDB pRetrievalDB, ManipulationDB pManipulationDB, JComponent pErrorMessageParent) throws SQLException, RemoteException {
        mRetrievalDB = pRetrievalDB;
        mManipulationDB = pManipulationDB;
        mErrorMessageParent = pErrorMessageParent;
        mPaths = getPathNames();
        mPaths.add(new IntWithString(-1, ""));
    }

    protected abstract Vector<IntWithString> getPathNames() throws SQLException, RemoteException;

    protected abstract void updatePath(IntWithString pPath) throws SQLException, RemoteException;

    protected abstract IntWithString addPath(String pName) throws SQLException, RemoteException;

    public int getRowCount() {
        return mPaths.size();
    }

    public int getColumnCount() {
        return 1;
    }

    public void refresh() throws SQLException, RemoteException {
        mPaths = getPathNames();
        mPaths.add(new IntWithString(-1, ""));
        fireTableDataChanged();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return mPaths.get(rowIndex).getString();
    }

    public Class getColumnClass(int columnIndex) {
        return String.class;
    }

    public String getColumnName(int column) {
        return "";
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue instanceof String) {
            IntWithString intWithString = mPaths.get(rowIndex);
            if (intWithString.getNumber() > 0) {
                IntWithString toUpDate = new IntWithString(intWithString.getNumber(), (String) aValue);
                try {
                    updatePath(toUpDate);
                    intWithString.setString(toUpDate.getString());
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
                catch (Exception e) {
                    if (DEBUG) e.printStackTrace();
                    report(e);
                }
            }
            else {
                try {
                    IntWithString updated = addPath((String) aValue);
                    mPaths.add(rowIndex, updated);
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    fireTableRowsInserted(rowIndex + 1, rowIndex + 1);
                }
                catch (Exception e) {
                    if (DEBUG) e.printStackTrace();
                    report(e);
                }
            }
        }
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(mErrorMessageParent, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }
}
