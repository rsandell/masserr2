package se.tdt.bobby.wodcc.remote.db;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.proxy.interfaces.BasicDB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * 
 * User: bobby
 * Date: 2004-mar-14
 * Time: 21:37:22
 */
public interface RemoteBasicDB extends BasicDB, Remote {

}
