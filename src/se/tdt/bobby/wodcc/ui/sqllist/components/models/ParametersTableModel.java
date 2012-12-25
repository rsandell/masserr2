package se.tdt.bobby.wodcc.ui.sqllist.components.models;

import se.tdt.bobby.wodcc.data.sqllists.ParamType;
import se.tdt.bobby.wodcc.data.sqllists.ParamTypes;
import se.tdt.bobby.wodcc.data.sqllists.Parameter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created: 2006-jul-24 23:02:21
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ParametersTableModel extends AbstractTableModel {
    private List<Parameter> mParameters;

    public ParametersTableModel() {
        reset(1);
    }

    public void reset(int pParamCount) {
        mParameters = new ArrayList<Parameter>();
        for (int i = 0; i < pParamCount; i++) {
            mParameters.add(new Parameter(-1, "", ParamTypes.STRING, false, i + 1));
        }
        fireTableDataChanged();
    }

    public void reset(List<Parameter> pParameters, int pParamCount) {
        if (pParamCount == pParameters.size()) {
            mParameters = pParameters;
        }
        else {
            mParameters = new ArrayList<Parameter>();
            for (int i = 0; i < pParamCount; i++) {
                if (pParameters.size() > i) {
                    Parameter par = pParameters.get(i);
                    par.setIndex(i + 1);
                    mParameters.add(par);
                }
                else {
                    mParameters.add(new Parameter(-1, "", ParamTypes.STRING, false, i + 1));
                }
            }
        }
        fireTableDataChanged();
    }

    public int getRowCount() {
        return mParameters.size();
    }

    private static final String[] COLUMNNAMES = {"Index", "Value", "Type", "Variable", "Input", "Lookup", "ValueColumn", "LabelColumn"};

    public int getColumnCount() {
        return COLUMNNAMES.length;
    }

    public String getColumnName(int column) {
        return COLUMNNAMES[column];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Parameter parameter = mParameters.get(rowIndex);
        if (parameter.isVariable()) {
            return columnIndex != 0;
        }
        else {
            return columnIndex != 0 && columnIndex <= 3;
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return ParamType.class;
            case 3:
                return Boolean.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return Short.class;
            case 7:
                return Short.class;
            default:
                return null;
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Parameter parameter = mParameters.get(rowIndex);
        switch (columnIndex) {
            case 0:
                parameter.setIndex((Integer) aValue);
            case 1:
                parameter.setValue(aValue.toString());
                return;
            case 2:
                parameter.setType((ParamType) aValue);
                return;
            case 3:
                parameter.setVariable((Boolean) aValue);
                return;
            case 4:
                parameter.setVariableInput((String) aValue);
                fireTableRowsUpdated(rowIndex, rowIndex);
                return;
            case 5:
                parameter.setVariableLookup((String) aValue);
                return;
            case 6:
                parameter.setVariableLookupValuecolumn((Short)aValue);
                return;
            case 7:
                parameter.setVariableLookupLabelcolumn((Short)aValue);
                return;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Parameter parameter = mParameters.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return parameter.getIndex();
            case 1:
                return parameter.getValue();
            case 2:
                return parameter.getType();
            case 3:
                return parameter.isVariable();
            case 4:
                return parameter.getVariableInput();
            case 5:
                return parameter.getVariableLookup();
            case 6:
                return parameter.getVariableLookupValuecolumn();
            case 7:
                return parameter.getVariableLookupLabelcolumn();

            default:
                return null;
        }
    }

    public List<Parameter> getParameters() {
        return mParameters;
    }
}
