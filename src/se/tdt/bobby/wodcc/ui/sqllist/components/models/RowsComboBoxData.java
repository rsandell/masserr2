package se.tdt.bobby.wodcc.ui.sqllist.components.models;

import java.io.Serializable;

/**
 * Created: 2006-jul-25 13:58:31
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class RowsComboBoxData implements Serializable {
    private Object mLabel;
    private Object mValue;

    public RowsComboBoxData(Object pLabel, Object pValue) {
        mLabel = pLabel;
        mValue = pValue;
    }

    public RowsComboBoxData() {
    }

    public Object getLabel() {
        return mLabel;
    }

    public void setLabel(Object pLabel) {
        mLabel = pLabel;
    }

    public Object getValue() {
        return mValue;
    }

    public void setValue(Object pValue) {
        mValue = pValue;
    }

    public String toString() {
        return String.valueOf(mLabel);
    }
}
