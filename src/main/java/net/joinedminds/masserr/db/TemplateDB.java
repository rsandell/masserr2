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

import net.joinedminds.masserr.model.Role;

import java.sql.SQLException;
import java.text.ParseException;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-03 16:40:02
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface TemplateDB extends BasicDB {
    char ABILITY_TYPE_MENTAL = 'M';
    char ABILITY_TYPE_PHYSICAL = 'P';
    char ABILITY_TYPE_SOCIAL = 'S';

    Role getTemplate(int pId) throws SQLException, ParseException, RemoteException;

    Vector<Role> getMinTemplateInfo() throws SQLException, RemoteException;

    void updateTemplate(Role pRole) throws SQLException, RemoteException;

    int addTemplate(Role pRole) throws SQLException, RemoteException;
}
