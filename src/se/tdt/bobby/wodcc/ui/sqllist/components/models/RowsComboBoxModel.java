package se.tdt.bobby.wodcc.ui.sqllist.components.models;

import se.tdt.bobby.wodcc.logic.sqllist.SqlListUtil;

import javax.swing.*;
import java.util.List;

/**
 * Created: 2006-jul-25 13:55:56
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class RowsComboBoxModel extends AbstractListModel implements ComboBoxModel {
    private List<RowsComboBoxData> mData;
    private Object mSelectedItem;

    public RowsComboBoxModel(List<List<Object>> pRows, short pValueColumn, short pLabelColumn) {
        mData = SqlListUtil.makeComboBoxData(pRows, pValueColumn, pLabelColumn);
    }

    public int getSize() {
        return mData.size();
    }

    public Object getElementAt(int index) {
        return mData.get(index);
    }

    public void setSelectedItem(Object anItem) {
        mSelectedItem = anItem;
    }

    public Object getSelectedItem() {
        return mSelectedItem;
    }
}
