package se.tdt.bobby.wodcc.server;

import se.tdt.bobby.wodcc.remote.db.RemoteRetrievalDB;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.remote.RemoteServer;
import se.tdt.bobby.wodcc.remote.db.RemoteRetrievalDB;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.sql.SQLException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 22:49:06
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Test {
    private static final boolean DEBUG = false;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost");
        RemoteServer stub = (RemoteServer) registry.lookup("WODCC_Factory");

        stub.getManipulationDB();
        stub.getInfluenceDB();
        stub.getGroupsDB();
        stub.getCreateRules();
        stub.getBankingDB();
        RemoteRetrievalDB db = stub.getRetrievalDB();
        try {
            Vector v = db.getMinRoleRoleInfo();
            for (int i = 0; i < v.size(); i++) {
                Role role = (Role) v.elementAt(i);
                System.out.println(role.toString());
            }
        } catch (SQLException e) {
            if (DEBUG) e.printStackTrace();
        }
        //System.out.println("response: " + response);

    }
}
