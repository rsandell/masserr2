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

import net.joinedminds.masserr.model.Domain;
import net.joinedminds.masserr.model.mgm.Config;
import net.joinedminds.masserr.model.mgm.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-14 16:31:08
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface AdminDB extends BasicDB {

    Config getConfig();

    Config saveConfig(Config config);

    void updateUser(User pUser) throws SQLException, RemoteException;

    User addUser(User pUser) throws SQLException, RemoteException;

    User getUser(int pUserId) throws SQLException, RemoteException;

    List<User> getFullUsersListByDomain() throws SQLException, RemoteException;

    void addDomain(Domain pDomain) throws SQLException, RemoteException;

    void updateDomain(Domain pDomain) throws SQLException, RemoteException;

    boolean changePassword(String pCurrentPassword, String pNewPassword) throws SQLException, RemoteException;
}
