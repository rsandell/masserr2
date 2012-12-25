package se.tdt.bobby.wodcc.admin.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.mgm.UserRights;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-14 17:01:29
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class UserRightsTableModel extends AbstractTableModel {
    private IntWithString[] mDescriptionArray;
    private UserRights mUserRights;

    public UserRightsTableModel(UserRights pUserRights) {
        mUserRights = pUserRights;
        List<IntWithString> descriptionList = new ArrayList<IntWithString>(30);
        for (int i = 0; i < UserRights.sDescriptions.length; i++) {
            String sdescription = UserRights.sDescriptions[i];
            descriptionList.add(new IntWithString(i + 1, sdescription));
        }
        Comparator cmp = new Comparator() {
            public int compare(Object o, Object o1) {
                IntWithString intr = (IntWithString) o;
                IntWithString intr1 = (IntWithString) o1;
                return intr.getString().compareTo(intr1.getString());
            }
        };
        mDescriptionArray = descriptionList.toArray(new IntWithString[descriptionList.size()]);
        Arrays.sort(mDescriptionArray, cmp);
    }

    public UserRightsTableModel() {
        this(new UserRights(""));
    }

    public int getRowCount() {
        return mDescriptionArray.length;
    }

    public int getColumnCount() {
        return 2;
    }

    private static final String[] sColumnNames = {"Right", "Permit"};

    public String getColumnName(int column) {
        return sColumnNames[column];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 1);
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return mDescriptionArray[rowIndex].getString();
        }
        else if (columnIndex == 1) {
            return new Boolean(mUserRights.get(mDescriptionArray[rowIndex].getNumber()));
        }
        else {
            return null;
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            if (aValue instanceof Boolean) {
                Boolean bol = (Boolean) aValue;
                IntWithString row = mDescriptionArray[rowIndex];
                mUserRights.set(row.getNumber(), bol.booleanValue());
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
    }

    public UserRights getUserRights() {
        return mUserRights;
    }
}
