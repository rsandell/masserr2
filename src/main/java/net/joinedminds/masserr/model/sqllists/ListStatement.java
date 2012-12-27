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
import java.util.List;
import java.io.Serializable;

/**
 * Created: 2006-jul-24 21:07:26
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ListStatement implements Serializable {

    @Id
    private String id;
    private String name;
    private String statement;
    private List<Parameter> parameters;

    public ListStatement(String pId, String pName, String pStatement, List<Parameter> pParameters) {
        id = pId;
        name = pName;
        statement = pStatement;
        parameters = pParameters;
    }

    public ListStatement(String pName, String pStatement, List<Parameter> pParameters) {
        name = pName;
        statement = pStatement;
        parameters = pParameters;
    }

    public ListStatement(String pId, String pName, String pStatement) {
        id = pId;
        name = pName;
        statement = pStatement;
    }

    public ListStatement() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String pStatement) {
        statement = pStatement;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> pParameters) {
        parameters = pParameters;
    }
}
