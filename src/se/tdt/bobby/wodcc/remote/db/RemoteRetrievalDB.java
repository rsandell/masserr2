package se.tdt.bobby.wodcc.remote.db;

import se.tdt.bobby.wodcc.data.Discipline;
import se.tdt.bobby.wodcc.data.Resource;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

/**
 * Description.
 *
 * Created: 2004-mar-14 21:45:05
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface RemoteRetrievalDB extends RetrievalDB, Remote {
    
}
