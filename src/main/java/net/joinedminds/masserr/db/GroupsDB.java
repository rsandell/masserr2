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

import net.joinedminds.masserr.model.RolesGroup;
import net.joinedminds.masserr.security.OperationDeniedException;

import java.util.*;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:42:53
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface GroupsDB extends BasicDB {
    Vector getGroups() throws SQLException, RemoteException;

    Vector getRootGroups() throws SQLException, RemoteException;

    List getGroupsInGroup(int pGroupId) throws SQLException, RemoteException;

    List getRolesInGroup(int pId) throws SQLException, RemoteException;

    void addRolesToGroup(Object[] pRoles, RolesGroup pGroup) throws SQLException, ClassCastException, RemoteException;

    Vector getGroupTypes() throws SQLException, RemoteException;

    RolesGroup createGroup(String pName, String pType, String pDescription, Date pDate, Integer pParent) throws SQLException, RemoteException;

    void removeRolesFromTheirGroup(HashMap pRolesToRemove) throws SQLException, RemoteException;

    void deleteGroups(List pGroupsToRemove) throws SQLException, RemoteException;

    void renameGroup(RolesGroup pGroup, String pNewName) throws SQLException, RemoteException;

    void updateGroup(RolesGroup pGroup) throws SQLException, RemoteException;

    void storeGroupTypeIcon(String pFileName, byte[] pBytes) throws IOException, RemoteException, OperationDeniedException;

    HashMap<String,byte[]> getGroupTypeIconFiles(ArrayList<String> pDoNotInclude) throws IOException, FileNotFoundException;
}
