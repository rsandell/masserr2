package se.tdt.bobby.wodcc.remote;



import se.tdt.bobby.wodcc.remote.db.*;
import se.tdt.bobby.wodcc.proxy.interfaces.AdminDB;
import se.tdt.bobby.wodcc.proxy.interfaces.TemplateDB;

import java.rmi.RemoteException;
import java.rmi.Remote;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-14 22:07:54
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface RemoteServer extends Remote {
    public static final String BIND_NAME = "WODCC_Factory";
    RemoteRetrievalDB getRetrievalDB() throws RemoteException;

    RemoteManipulationDB getManipulationDB() throws RemoteException;

    RemoteInfluenceDB getInfluenceDB() throws RemoteException;

    RemoteGroupsDB getGroupsDB() throws RemoteException;

    RemoteCreateRules getCreateRules() throws RemoteException;

    RemoteBankingDB getBankingDB() throws RemoteException;

    RemoteAdminDB getAdminDB()  throws RemoteException;

    TemplateDB getTemplateDB()   throws RemoteException;

    RemoteStatementsDB getStatementsDB() throws RemoteException;
}
