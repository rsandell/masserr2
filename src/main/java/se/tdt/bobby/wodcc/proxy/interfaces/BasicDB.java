package se.tdt.bobby.wodcc.proxy.interfaces;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.mgm.User;

import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:42:24
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface BasicDB {
    User login(String pUserName, String pPassword) throws SQLException, RemoteException;
    public User getLoginUser() throws RemoteException;
}
