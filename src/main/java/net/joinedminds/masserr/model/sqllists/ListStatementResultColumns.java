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

import java.io.Serializable;

/**
 * Created: 2006-jul-24 21:31:44
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ListStatementResultColumns implements Serializable {
    private String name;
    private int type;
    private String columnClassName;
    private boolean nullable;
    private Class columnClass = null;

    public ListStatementResultColumns(String pName, int pType, String pColumnClassName, boolean pNullable) {
        name = pName;
        type = pType;
        columnClassName = pColumnClassName;
        nullable = pNullable;
    }

    public ListStatementResultColumns(String pName, int pType, String pColumnClassName) {
        name = pName;
        type = pType;
        columnClassName = pColumnClassName;
    }

    public ListStatementResultColumns(String pName, int pType, boolean pNullable) {
        name = pName;
        type = pType;
        nullable = pNullable;
    }

    public ListStatementResultColumns(String pName, int pType) {
        name = pName;
        type = pType;
    }

    public ListStatementResultColumns() {
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public int getType() {
        return type;
    }

    public void setType(int pType) {
        type = pType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean pNullable) {
        nullable = pNullable;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

    public void setColumnClassName(String pColumnClassName) {
        columnClassName = pColumnClassName;
    }

    public Class getColumnClass() throws ClassNotFoundException {
        if(columnClass == null) {
            columnClass = Class.forName(columnClassName);
        }
        return columnClass;
    }

    public Object getColumnObjectOfClass() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class c = getColumnClass();
        return c.newInstance();
    }
}
