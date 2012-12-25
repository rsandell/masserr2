package se.tdt.bobby.wodcc.remote.db;

import se.tdt.bobby.wodcc.data.BankAccount;
import se.tdt.bobby.wodcc.data.Resource;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.data.Withdrawal;
import se.tdt.bobby.wodcc.proxy.interfaces.BankingDB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 22:02:01
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface RemoteBankingDB extends BankingDB, Remote {
    
}
