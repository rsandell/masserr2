package se.tdt.bobby.wodcc.remote.db;

import se.tdt.bobby.wodcc.data.Influence;
import se.tdt.bobby.wodcc.data.Resource;
import se.tdt.bobby.wodcc.proxy.interfaces.InfluenceDB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 21:56:13
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface RemoteInfluenceDB extends InfluenceDB, Remote {
    
}
