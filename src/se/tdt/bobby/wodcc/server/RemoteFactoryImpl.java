package se.tdt.bobby.wodcc.server;

import se.tdt.bobby.wodcc.proxy.interfaces.TemplateDB;
import se.tdt.bobby.wodcc.remote.RemoteServer;
import se.tdt.bobby.wodcc.remote.db.*;
import se.tdt.bobby.wodcc.server.db.*;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 22:02:53
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class RemoteFactoryImpl implements RemoteServer {
    private static final boolean DEBUG = false;

    public RemoteRetrievalDB getRetrievalDB() throws RemoteException {
        return new RetrievalDBImpl();
    }

    public RemoteManipulationDB getManipulationDB() throws RemoteException {
        return new ManipulationDBImpl();
    }

    public RemoteInfluenceDB getInfluenceDB() throws RemoteException {
        return new InfluenceDBImpl();
    }

    public RemoteGroupsDB getGroupsDB() throws RemoteException {
        return new GroupsDBImpl();
    }

    public RemoteCreateRules getCreateRules() throws RemoteException {
        return new CreateRulesImpl();
    }

    public RemoteBankingDB getBankingDB() throws RemoteException {
        return new BankingDBImpl();
    }

    public RemoteAdminDB getAdminDB() throws RemoteException {
        return new AdminDBImpl();
    }

    public TemplateDB getTemplateDB() throws RemoteException {
        return new TemplateDBImpl();
    }

    public RemoteStatementsDB getStatementsDB() throws RemoteException {
        return new StatementsDBImpl();
    }

    protected boolean testDatabaseConnection() throws FileNotFoundException {
        return BasicDBImpl.testConnection();
    }

    public static void main(String[] args) throws RemoteException {
        RemoteFactoryImpl factory = new RemoteFactoryImpl();
        boolean dbConnOK = true;
        try {
            dbConnOK = factory.testDatabaseConnection();
        }
        catch (FileNotFoundException e) {
            if (DEBUG) e.printStackTrace();
            dbConnOK = false;
            System.err.println(e.getMessage());
        }
        if (dbConnOK) {
            //Try to create a registry listening on the default RMI port
            //If it throws an ExportException a registry is probably running
            //on the port, in which case we do nothing and simply try to
            //use the existing registry.
            try{
                LocateRegistry.createRegistry(1099);
            }catch(ExportException ex){if(DEBUG) ex.printStackTrace();}

            RemoteServer stub = (RemoteServer) UnicastRemoteObject.exportObject(factory, 0);
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(RemoteServer.BIND_NAME, stub);
            System.err.println("Server Factory ready");
        }
        else {
            System.err.println("Database Connection failed! Not bound!");
        }
    }
}
