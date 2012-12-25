package se.tdt.bobby.wodcc.ui.sqllist.components.models;

import se.tdt.bobby.wodcc.data.sqllists.ListStatementResult;

import javax.swing.table.AbstractTableModel;

/**
 * Created: 2006-jul-24 23:18:52
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ListStatementResultTableModel extends AbstractTableModel {
    private ListStatementResult mResult;
    private static final boolean DEBUG = false;

    public ListStatementResultTableModel(ListStatementResult pResult) {
        mResult = pResult;
    }

    public ListStatementResultTableModel() {
    }

    public int getRowCount() {
        if (mResult != null) {
            return mResult.getRows().size();
        }
        else {
            return 0;
        }
    }

    public int getColumnCount() {
        if (mResult != null) {
            return mResult.getHeaders().size();
        }
        else {
            return 0;
        }
    }

    public String getColumnName(int column) {
        if (mResult != null) {
            return mResult.getHeaders().get(column).getName();
        }
        else {
            return "";
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        if(mResult == null) return Object.class;
        try {
            return mResult.getHeaders().get(columnIndex).getColumnClass();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            return Object.class;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return mResult.getRows().get(rowIndex).get(columnIndex);
    }

    public ListStatementResult getResult() {
        return mResult;
    }

    public void setResult(ListStatementResult pResult) {
        mResult = pResult;
        fireTableStructureChanged();
        //fireTableDataChanged();
    }
}
