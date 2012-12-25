package se.tdt.bobby.wodcc.data.sqllists;

import java.io.Serializable;

/**
 * Created: 2006-jul-24 20:23:51
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class Parameter implements Serializable {
    private int mId;
    private String mValue;
    private ParamType mType;
    private boolean mVariable;
    private int mIndex;
    private String mVariableInput;
    private String mVariableLookup;
    private Short mVariableLookupValuecolumn;
    private Short mVariableLookupLabelcolumn;

    public Parameter(int pId, String pValue, ParamType pType, boolean pVariable, int pIndex, String pVariableInput, String pVariableLookup, Short pVariableLookupValuecolumn, Short pVariableLookupLabelcolumn) {
        mId = pId;
        mValue = pValue;
        mType = pType;
        mVariable = pVariable;
        mIndex = pIndex;
        mVariableInput = pVariableInput;
        mVariableLookup = pVariableLookup;
        mVariableLookupValuecolumn = pVariableLookupValuecolumn;
        mVariableLookupLabelcolumn = pVariableLookupLabelcolumn;
    }

    public Parameter(String pValue, ParamType pType, boolean pVariable, int pIndex, String pVariableInput, String pVariableLookup, Short pVariableLookupValuecolumn, Short pVariableLookupLabelcolumn) {
        mValue = pValue;
        mType = pType;
        mVariable = pVariable;
        mIndex = pIndex;
        mVariableInput = pVariableInput;
        mVariableLookup = pVariableLookup;
        mVariableLookupValuecolumn = pVariableLookupValuecolumn;
        mVariableLookupLabelcolumn = pVariableLookupLabelcolumn;
    }

    public Parameter(int pId, String pValue, ParamType pType, boolean pVariable, int pIndex) {
        mId = pId;
        mValue = pValue;
        mType = pType;
        mVariable = pVariable;
        mIndex = pIndex;
    }

    public Parameter(String pValue, ParamType pType, boolean pVariable, int pIndex) {
        mValue = pValue;
        mType = pType;
        mVariable = pVariable;
        mIndex = pIndex;
    }

    public Parameter() {
    }

    public Object getParsedValue() {
        return mType.parse(mValue);
    }

    public int getId() {
        return mId;
    }

    public void setId(int pId) {
        mId = pId;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String pValue) {
        mValue = pValue;
    }

    public ParamType getType() {
        return mType;
    }

    public void setType(ParamType pType) {
        mType = pType;
    }

    public boolean isVariable() {
        return mVariable;
    }

    public void setVariable(boolean pVariable) {
        mVariable = pVariable;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int pIndex) {
        mIndex = pIndex;
    }

    public String getVariableInput() {
        return mVariableInput;
    }

    public void setVariableInput(String pVariableInput) {
        mVariableInput = pVariableInput;
    }

    public String getVariableLookup() {
        return mVariableLookup;
    }

    public void setVariableLookup(String pVariableLookup) {
        mVariableLookup = pVariableLookup;
    }

    public Short getVariableLookupValuecolumn() {
        return mVariableLookupValuecolumn;
    }

    public void setVariableLookupValuecolumn(Short pVariableLookupValuecolumn) {
        mVariableLookupValuecolumn = pVariableLookupValuecolumn;
    }

    public Short getVariableLookupLabelcolumn() {
        return mVariableLookupLabelcolumn;
    }

    public void setVariableLookupLabelcolumn(Short pVariableLookupLabelcolumn) {
        mVariableLookupLabelcolumn = pVariableLookupLabelcolumn;
    }
}
