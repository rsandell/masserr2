package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.Ritual;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-11 21:06:47
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class RitualsTableModel extends AbstractTableModel {

    private ArrayList mRituals;

    public RitualsTableModel(int pInitialNumber) {
        mRituals = new ArrayList(pInitialNumber);
        for (int i = 0; i < pInitialNumber; i++) {
            mRituals.add(Void.class);
        }
    }

    public RitualsTableModel(int pInitialNumber, List pInitList) {
        mRituals = new ArrayList(pInitialNumber);
        for (int i = 0; i < pInitList.size(); i++) {
            Ritual rit = (Ritual) pInitList.get(i);
            mRituals.add(rit);
        }
        for (int i = 0; i < pInitialNumber - pInitList.size() + 1; i++) {
            mRituals.add(Void.class);
        }
        if (pInitialNumber <= pInitList.size()) {
            mRituals.add(Void.class);
        }
    }

    public int getRowCount() {
        return mRituals.size();
    }

    public int getColumnCount() {
        return 2;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o = mRituals.get(rowIndex);
        if (o == Void.class) {
            return null;
        }
        else {
            Ritual ritual = (Ritual) o;
            if (columnIndex == 0) {
                return ritual.getRitualType().getName();
            }
            else {
                return ritual.getName();
            }
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Ritual ritual = (Ritual) aValue;
        mRituals.remove(rowIndex);
        if (ritual.getId() >= 0) {
            mRituals.add(rowIndex, ritual);
        }
        else {
            mRituals.add(rowIndex, Void.class);
        }
        if (rowIndex == mRituals.size() - 1) {
            mRituals.add(Void.class);
            fireTableRowsUpdated(rowIndex, mRituals.size() - 1);
        }
        else {
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }
    private final String[] sColumnNames = {"Type", "Name"};
    public String getColumnName(int column) {
        return sColumnNames[column];
    }

    public List<Ritual> getList() {
        ArrayList<Ritual> li = new ArrayList<Ritual>();
        for (int i = 0; i < mRituals.size(); i++) {
            Object o = mRituals.get(i);
            if (o instanceof Ritual) {
                li.add((Ritual) o);
            }
        }
        return li;
    }

}
