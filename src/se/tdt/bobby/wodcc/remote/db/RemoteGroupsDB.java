package se.tdt.bobby.wodcc.remote.db;

import se.tdt.bobby.wodcc.data.RolesGroup;
import se.tdt.bobby.wodcc.proxy.interfaces.GroupsDB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 21:58:29
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface RemoteGroupsDB extends GroupsDB, Remote {
    
}
