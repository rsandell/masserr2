package se.tdt.bobby.wodcc.proxy.interfaces;

import net.joinedminds.masserr.model.Domain;
import net.joinedminds.masserr.model.mgm.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-14 16:31:08
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface AdminDB extends BasicDB {
    void updateUser(User pUser) throws SQLException, RemoteException;

    User addUser(User pUser) throws SQLException, RemoteException;

    User getUser(int pUserId) throws SQLException, RemoteException;

    List<User> getFullUsersListByDomain() throws SQLException, RemoteException;

    void addDomain(Domain pDomain) throws SQLException, RemoteException;

    void updateDomain(Domain pDomain) throws SQLException, RemoteException;

    boolean changePassword(String pCurrentPassword, String pNewPassword) throws SQLException, RemoteException;
}
