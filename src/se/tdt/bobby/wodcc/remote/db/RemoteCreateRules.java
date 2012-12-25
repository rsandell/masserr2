package se.tdt.bobby.wodcc.remote.db;

import se.tdt.bobby.wodcc.data.CreateRule;
import se.tdt.bobby.wodcc.proxy.interfaces.CreateRules;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 22:00:48
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface RemoteCreateRules extends CreateRules, Remote {
    
}
