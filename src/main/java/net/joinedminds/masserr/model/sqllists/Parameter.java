/*
 * The MIT License
 *
 * Copyright (c) 2006-2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr.model.sqllists;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created: 2006-jul-24 20:23:51
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class Parameter implements Serializable {

    @Id
    private String id;
    private String value;
    private ParamType type;
    private boolean variable;
    private int index;
    private String variableInput;
    private String variableLookup;
    private Short variableLookupValuecolumn;
    private Short variableLookupLabelcolumn;

    public Parameter(String pId, String pValue, ParamType pType, boolean pVariable, int pIndex, String pVariableInput, String pVariableLookup, Short pVariableLookupValuecolumn, Short pVariableLookupLabelcolumn) {
        id = pId;
        value = pValue;
        type = pType;
        variable = pVariable;
        index = pIndex;
        variableInput = pVariableInput;
        variableLookup = pVariableLookup;
        variableLookupValuecolumn = pVariableLookupValuecolumn;
        variableLookupLabelcolumn = pVariableLookupLabelcolumn;
    }

    public Parameter(String pValue, ParamType pType, boolean pVariable, int pIndex, String pVariableInput, String pVariableLookup, Short pVariableLookupValuecolumn, Short pVariableLookupLabelcolumn) {
        value = pValue;
        type = pType;
        variable = pVariable;
        index = pIndex;
        variableInput = pVariableInput;
        variableLookup = pVariableLookup;
        variableLookupValuecolumn = pVariableLookupValuecolumn;
        variableLookupLabelcolumn = pVariableLookupLabelcolumn;
    }

    public Parameter(String pId, String pValue, ParamType pType, boolean pVariable, int pIndex) {
        id = pId;
        value = pValue;
        type = pType;
        variable = pVariable;
        index = pIndex;
    }

    public Parameter(String pValue, ParamType pType, boolean pVariable, int pIndex) {
        value = pValue;
        type = pType;
        variable = pVariable;
        index = pIndex;
    }

    public Parameter() {
    }

    public Object getParsedValue() {
        return type.parse(value);
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String pValue) {
        value = pValue;
    }

    public ParamType getType() {
        return type;
    }

    public void setType(ParamType pType) {
        type = pType;
    }

    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean pVariable) {
        variable = pVariable;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int pIndex) {
        index = pIndex;
    }

    public String getVariableInput() {
        return variableInput;
    }

    public void setVariableInput(String pVariableInput) {
        variableInput = pVariableInput;
    }

    public String getVariableLookup() {
        return variableLookup;
    }

    public void setVariableLookup(String pVariableLookup) {
        variableLookup = pVariableLookup;
    }

    public Short getVariableLookupValuecolumn() {
        return variableLookupValuecolumn;
    }

    public void setVariableLookupValuecolumn(Short pVariableLookupValuecolumn) {
        variableLookupValuecolumn = pVariableLookupValuecolumn;
    }

    public Short getVariableLookupLabelcolumn() {
        return variableLookupLabelcolumn;
    }

    public void setVariableLookupLabelcolumn(Short pVariableLookupLabelcolumn) {
        variableLookupLabelcolumn = pVariableLookupLabelcolumn;
    }
}
