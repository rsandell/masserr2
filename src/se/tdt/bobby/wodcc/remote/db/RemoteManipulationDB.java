package se.tdt.bobby.wodcc.remote.db;

import se.tdt.bobby.wodcc.data.Resource;
import se.tdt.bobby.wodcc.data.Ritual;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.data.RolesGroup;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 21:53:09
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface RemoteManipulationDB extends ManipulationDB, Remote {
    
}
