package se.tdt.bobby.wodcc.proxy.interfaces;

import se.tdt.bobby.wodcc.data.RolesGroup;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;

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
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
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
