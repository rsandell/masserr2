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

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created: 2006-jul-24 20:36:18
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ParamTypes {
    public static final ParamType STRING = new ParamType("String") {
        public Object parse(String pValue) {
            return pValue;
        }
        public void setStatementValue(PreparedStatement pStatement, Object pValue, int pIndex) throws SQLException {
            if(pValue == null) {
                pStatement.setString(pIndex, null);
            }
            else if(pValue instanceof String) {
                pStatement.setString(pIndex, (String) pValue);
            }
            else {
                pStatement.setString(pIndex, pValue.toString());
            }
        }
    };

    public static final ParamType BOOLEAN = new ParamType("Boolean") {
        public Object parse(String pValue) {
            if(pValue == null) return null;
            return Boolean.valueOf(pValue);
        }
        public void setStatementValue(PreparedStatement pStatement, Object pValue, int pIndex) throws SQLException {
            if(pValue == null) {
                pStatement.setNull(pIndex, Types.BOOLEAN);
            }
            else if(pValue instanceof Boolean) {
                pStatement.setBoolean(pIndex, (Boolean)pValue);
            }
            else {
                pStatement.setBoolean(pIndex, (Boolean)parse(pValue.toString()));
            }
        }
    };

    public static final ParamType INTEGER = new ParamType("Integer") {
        public Object parse(String pValue) {
            if(pValue == null) return null;
            return Integer.parseInt(pValue);
        }

        public void setStatementValue(PreparedStatement pStatement, Object pValue, int pIndex) throws SQLException {
            if(pValue == null) {
                pStatement.setNull(pIndex, Types.INTEGER);
            }
            else if (pValue instanceof Integer){
                pStatement.setInt(pIndex, (Integer)pValue);
            }
            else {
                pStatement.setInt(pIndex, (Integer)parse(pValue.toString()));
            }
        }
    };

    public static final ParamType FLOAT = new ParamType("Float") {
        public Object parse(String pValue) {
            if(pValue == null) return null;
            return Float.parseFloat(pValue);
        }

        public void setStatementValue(PreparedStatement pStatement, Object pValue, int pIndex) throws SQLException {
            if(pValue == null) {
                pStatement.setNull(pIndex, Types.FLOAT);
            }
            else if (pValue instanceof Float){
                pStatement.setFloat(pIndex, (Float)pValue);
            }
            else {
                pStatement.setFloat(pIndex, (Float)parse(pValue.toString()));
            }
        }
    };

    public static final ParamType LONG = new ParamType("Long") {
        public Object parse(String pValue) {
            if(pValue == null) return null;
            return Long.parseLong(pValue);
        }

        public void setStatementValue(PreparedStatement pStatement, Object pValue, int pIndex) throws SQLException {
            if(pValue == null) {
                pStatement.setNull(pIndex, Types.BIGINT);
            }
            else if (pValue instanceof Integer){
                pStatement.setLong(pIndex, (Long)pValue);
            }
            else {
                pStatement.setLong(pIndex, (Long)parse(pValue.toString()));
            }
        }
    };

    public static final ParamType DOUBLE = new ParamType("Double") {
        public Object parse(String pValue) {
            if(pValue == null) return null;
            return Double.parseDouble(pValue);
        }

        public void setStatementValue(PreparedStatement pStatement, Object pValue, int pIndex) throws SQLException {
            if(pValue == null) {
                pStatement.setNull(pIndex, Types.DOUBLE);
            }
            else if (pValue instanceof Integer){
                pStatement.setDouble(pIndex, (Double)pValue);
            }
            else {
                pStatement.setDouble(pIndex, (Double)parse(pValue.toString()));
            }
        }
    };

    public static final ParamType[] ALL = new ParamType[] {STRING, BOOLEAN, INTEGER, LONG, FLOAT, DOUBLE};

    public static ParamType find(String pName) {
        for (ParamType type : ALL) {
            if (type.getName().equalsIgnoreCase(pName)) {
                return type;
            }
        }
        return null;
    }
}
