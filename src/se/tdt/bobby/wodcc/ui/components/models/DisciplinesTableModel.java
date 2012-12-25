package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.Discipline;
import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import javax.swing.table.AbstractTableModel;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-11 14:50:43
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class DisciplinesTableModel extends AbstractTableModel {

    private ArrayList mDisciplines;
    private RetrievalDB mDb;
    private static final boolean DEBUG = false;

    public DisciplinesTableModel(int pNumberOfDisciplines) throws RemoteException, SQLException {
        mDisciplines = new ArrayList(pNumberOfDisciplines);
        for (int i = 0; i < pNumberOfDisciplines; i++) {
            mDisciplines.add(Void.class);
        }
        mDb = Proxy.getRetrievalDB();
    }

    public DisciplinesTableModel(int pNumberOfDisciplines, List pSelected) throws RemoteException, SQLException {
        mDisciplines = new ArrayList(pNumberOfDisciplines);
        for (int i = 0; i < pSelected.size(); i++) {
            Discipline discipline = (Discipline) pSelected.get(i);
            mDisciplines.add(discipline);
        }
        int stop = Math.max(pNumberOfDisciplines - mDisciplines.size(), pSelected.size() + 1 - mDisciplines.size());
        stop = Math.max(stop, 1);
        for (int i = 0; i < stop; i++) {
            mDisciplines.add(Void.class);
        }
        mDb = Proxy.getRetrievalDB();
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
        return mDisciplines.size();
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
     * Returns false.  This is the default implementation for all cells.
     *
     * @param rowIndex    the row being queried
     * @param columnIndex the column being queried
     * @return false
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex the column whose value is to be queried
     * @return	the value Object at the specified cell
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o = mDisciplines.get(rowIndex);
        if (o == Void.class) {
            switch (columnIndex) {
                case 0:
                    return null;
                case 1:
                    return Boolean.FALSE;
                case 2:
                    return new Integer(0);
            }
            return null;
        }
        else if (o instanceof Discipline) {
            Discipline di = (Discipline) o;
            switch (columnIndex) {
                case 0:
                    return di.getName();
                case 1:
                    return new Boolean(di.isOfClan());
                case 2:
                    return new Integer(di.getDots());
            }
        }
        return null;
    }

    private String[] sColumnNames = {"Discipline", "Is Of Clan", "Dots"};

    /**
     * @param column the column being queried
     * @return a string containing the default name of <code>column</code>
     */
    public String getColumnName(int column) {
        return sColumnNames[column];
    }

    /**
     * Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
     *
     * @param columnIndex the column being queried
     * @return the Object.class
     */
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Boolean.class;
            case 2:
                return Integer.class;
        }
        return Object.class;
    }

    protected boolean containsDiscipline(int pDisciplineId) {
        for (int i = 0; i < mDisciplines.size(); i++) {
            Object o = mDisciplines.get(i);
            if (o instanceof Discipline) {
                Discipline di = (Discipline) o;
                if (di.getId() == pDisciplineId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param aValue      value to assign to cell
     * @param rowIndex    row of cell
     * @param columnIndex column of cell
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (DEBUG) System.out.println("DisciplinesTableModel.setValueAt(169) " + aValue.getClass().getName());
        if (aValue instanceof IntWithString && columnIndex == 0) {
            if (DEBUG) System.out.println("DisciplinesTableModel.setValueAt(171) 1");
            IntWithString in = (IntWithString) aValue;
            if (DEBUG) System.out.println("DisciplinesTableModel.setValueAt(173) value: " + in.getNumber());
            if (in.getNumber() >= 0 && !containsDiscipline(in.getNumber())) {
                if (DEBUG) System.out.println("DisciplinesTableModel.setValueAt(175) 1.1");
                mDisciplines.remove(rowIndex);
                if (in.getNumber() >= 0) {
                    try {
                        Discipline d = mDb.getDiscipline(in.getNumber());
                        if (d != null) {
                            mDisciplines.add(rowIndex, d);
                        }
                        else {
                            mDisciplines.add(rowIndex, new Discipline(in.getNumber(), in.getString()));
                            if (DEBUG) System.out.println("[DisciplinesTableModel][setValueAt][150] fetched discipline was null");
                        }
                    }
                    catch (Exception e) {
                        mDisciplines.add(rowIndex, new Discipline(in.getNumber(), in.getString()));
                        if (DEBUG) e.printStackTrace();
                    }
                }
                else {
                    if (DEBUG) System.out.println("DisciplinesTableModel.setValueAt(194) 1.2");
                    mDisciplines.add(rowIndex, Void.class);
                }
            }
            else {
                if (DEBUG) System.out.println("DisciplinesTableModel.setValueAt(198) 1.3");
                mDisciplines.remove(rowIndex);
                mDisciplines.add(rowIndex, Void.class);
            }
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
        else {
            if (DEBUG) System.out.println("DisciplinesTableModel.setValueAt(198) 2");
            Object o = mDisciplines.get(rowIndex);
            if (o != null) {
                if (o instanceof Discipline) {
                    Discipline di = (Discipline) o;
                    switch (columnIndex) {
                        case 1:
                            Boolean b = (Boolean) aValue;
                            di.setOfClan(b.booleanValue());
                            fireTableCellUpdated(rowIndex, 1);
                            break;
                        case 2:
                            Number no = (Number) aValue;
                            di.setDots(no.intValue());
                            fireTableCellUpdated(rowIndex, 2);
                            break;
                    }
                }
            }
        }
        if (rowIndex >= mDisciplines.size() - 1 && columnIndex == 0) {
            mDisciplines.add(Void.class);
            fireTableRowsInserted(mDisciplines.size() - 1, mDisciplines.size() - 1);
        }
    }

    public List getSelectedDiscsiplines() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < mDisciplines.size(); i++) {
            Object discipline = mDisciplines.get(i);
            if (discipline instanceof Discipline) {
                list.add(discipline);
            }
        }
        return list;
    }

    public List getDisciplines() {
        ArrayList li = new ArrayList();
        for (int i = 0; i < mDisciplines.size(); i++) {
            Object o = mDisciplines.get(i);
            if (o instanceof Discipline) {
                li.add(o);
            }
        }
        return li;
    }

    public int getSum() {
        int sum = 0;
        for (int i = 0; i < mDisciplines.size(); i++) {
            Object o = mDisciplines.get(i);
            if (o instanceof Discipline) {
                sum += ((Discipline) o).getDots();
            }
        }
        return sum;
    }

    public void replaceClanDisciplines(List<Discipline> pClanDisciplines) {
        ArrayList<Object> toDelete = new ArrayList<Object>();
        for (int i = 0; i < mDisciplines.size(); i++) {
            Object o = mDisciplines.get(i);
            if (o instanceof Discipline) {
                Discipline d = (Discipline) o;
                if (d.isOfClan()) {
                    toDelete.add(d);
                }
            }
        }
        mDisciplines.removeAll(toDelete);
        for (int i = 0; i < pClanDisciplines.size(); i++) {
            Discipline discipline = pClanDisciplines.get(i);
            mDisciplines.add(0, discipline);
        }
        fireTableDataChanged();
    }
}
