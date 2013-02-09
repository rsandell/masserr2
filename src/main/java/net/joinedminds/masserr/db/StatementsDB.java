/*
 * The MIT License
 *
 * Copyright (c) 2004,2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.db;

import net.joinedminds.masserr.model.IntWithString;
import net.joinedminds.masserr.model.sqllists.ListStatement;
import net.joinedminds.masserr.model.sqllists.ListStatementResult;
import net.joinedminds.masserr.model.sqllists.Parameter;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * Created: 2006-jul-24 21:58:21
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public interface StatementsDB extends BasicDB {
    int addStatement(String pName, String pStatement, List<Parameter> pParameters) throws SQLException, RemoteException;

    void updateStatement(ListStatement pStatement) throws SQLException, RemoteException;

    ListStatement getStatement(int pId, boolean pConnect) throws SQLException, RemoteException;

    ListStatement getStatement(int pId) throws SQLException, RemoteException;

    ListStatementResult executeStatement(ListStatement pStatement, boolean pConnect) throws SQLException, RemoteException;

    ListStatementResult executeStatement(ListStatement pStatement) throws SQLException, RemoteException;

    ListStatementResult executeStatement(int pStatementId) throws SQLException, RemoteException;

    Vector<IntWithString> listStatements() throws SQLException, RemoteException;

    int addStatement(ListStatement pStatement) throws SQLException, RemoteException;

    void removeStatement(int pStatementId) throws SQLException, RemoteException;

    List<List<Object>> getRows(String pSql) throws SQLException, RemoteException;
}
