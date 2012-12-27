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
import java.util.List;

/**
 * Created: 2006-jul-24 21:29:54
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ListStatementResult implements Serializable {

    private List<ListStatementResultColumns> headers;
    private List<List<Object>> rows;
    private ListStatement statement;

    public ListStatementResult(List<ListStatementResultColumns> pHeaders, List<List<Object>> pRows, ListStatement pStatement) {
        headers = pHeaders;
        rows = pRows;
        statement = pStatement;
    }

    public ListStatementResult(List<ListStatementResultColumns> pHeaders, List<List<Object>> pRows) {
        headers = pHeaders;
        rows = pRows;
    }

    public ListStatementResult(ListStatement pStatement) {
        statement = pStatement;
    }

    public ListStatementResult() {
    }

    public List<ListStatementResultColumns> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ListStatementResultColumns> pHeaders) {
        headers = pHeaders;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public void setRows(List<List<Object>> pRows) {
        rows = pRows;
    }

    public ListStatement getStatement() {
        return statement;
    }

    public void setStatement(ListStatement pStatement) {
        statement = pStatement;
    }
}
