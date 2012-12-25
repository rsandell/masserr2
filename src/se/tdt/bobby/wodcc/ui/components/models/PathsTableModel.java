package se.tdt.bobby.wodcc.ui.components.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-11 19:52:54
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class PathsTableModel extends AbstractTableModel {

    private ArrayList mPaths;

    public PathsTableModel(int pInitLength) {
        mPaths = new ArrayList(pInitLength);
        for (int i = 0; i < pInitLength; i++) {
            mPaths.add(Void.class);
        }
    }

    public PathsTableModel(int pInitLength, List pInitList) {
        mPaths = new ArrayList(pInitLength);
        for (int i = 0; i < pInitList.size(); i++) {
            se.tdt.bobby.wodcc.data.Path path = (se.tdt.bobby.wodcc.data.Path) pInitList.get(i);
            mPaths.add(path);
        }
        for (int i = 0; i < pInitLength - pInitList.size() + 2; i++) {
            mPaths.add(Void.class);
        }
    }

    public int getRowCount() {
        return mPaths.size();
    }

    public int getColumnCount() {
        return 2;
    }

    private static String[] sColumnNames = {"Path", "Dots"};

    public String getColumnName(int column) {
        return sColumnNames[column];
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            default:
                return Object.class;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o = mPaths.get(rowIndex);
        if (o == Void.class) {
            if (columnIndex == 0) {
                return null;
            }
            else {
                return new Integer(0);
            }
        }
        else {
            se.tdt.bobby.wodcc.data.Path pa = (se.tdt.bobby.wodcc.data.Path) o;
            if (columnIndex == 0) {
                return pa.getName();
            }
            else {
                return new Integer(pa.getDots());
            }
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue instanceof se.tdt.bobby.wodcc.data.IntWithString) {
            se.tdt.bobby.wodcc.data.IntWithString intr = (se.tdt.bobby.wodcc.data.IntWithString) aValue;
            mPaths.remove(rowIndex);
            if (intr.getNumber() >= 0) {
                mPaths.add(rowIndex, new se.tdt.bobby.wodcc.data.Path(intr.getNumber(), intr.getString()));
            }
            else {
                mPaths.add(rowIndex, Void.class);
            }
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
        else {
            if (mPaths.get(rowIndex) instanceof se.tdt.bobby.wodcc.data.Path) {
                se.tdt.bobby.wodcc.data.Path p = (se.tdt.bobby.wodcc.data.Path) mPaths.get(rowIndex);
                if (columnIndex == 1) {
                    Number n = (Number) aValue;
                    p.setDots(n.intValue());
                    fireTableCellUpdated(rowIndex, 1);
                }
            }
        }
    }

    public int getSum() {
        int sum = 0;
        for (int i = 0; i < mPaths.size(); i++) {
            Object o = mPaths.get(i);
            if (o instanceof se.tdt.bobby.wodcc.data.Path) {
                sum += ((se.tdt.bobby.wodcc.data.Path) o).getDots();
            }
        }
        return sum;
    }

    public List getPaths() {
        ArrayList li = new ArrayList();
        for (int i = 0; i < mPaths.size(); i++) {
            Object o = mPaths.get(i);
            if (o instanceof se.tdt.bobby.wodcc.data.Path) {
                li.add(o);
            }
        }
        return li;
    }
}
