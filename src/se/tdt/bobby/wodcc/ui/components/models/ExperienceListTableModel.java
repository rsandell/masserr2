package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.Experience;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-jul-12 02:23:59
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ExperienceListTableModel extends AbstractTableModel {

    private List<Experience> mExperienceList;

    public ExperienceListTableModel(List<Experience> pExperienceList) {
        mExperienceList = pExperienceList;
    }

    public int getRowCount() {
        return mExperienceList.size();
    }

    public int getColumnCount() {
        return sColumnNames.length;
    }

    public static final String[] sColumnNames = {"Date", "Ammount", "Reason", "Added By"};

    public String getColumnName(int column) {
        return sColumnNames[column];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        Experience experience = mExperienceList.get(rowIndex);
        if (experience != null) {
            switch (columnIndex) {
                case 0:
                    return experience.getDate();
                case 1:
                    return new Integer(experience.getAmmount());
                case 2:
                    return experience.getReason();
                case 3:
                    return experience.getSetByUser().getFullName();
            }
        }
        return null;
    }
}
