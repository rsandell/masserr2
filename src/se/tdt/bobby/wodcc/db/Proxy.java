package se.tdt.bobby.wodcc.db;

import se.tdt.bobby.wodcc.data.mgm.User;
import se.tdt.bobby.wodcc.remote.RemoteServer;
import se.tdt.bobby.wodcc.server.db.*;
import se.tdt.bobby.wodcc.ui.LoginDialog;
import se.tdt.bobby.wodcc.ui.MainFrame;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:54:45
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Proxy {
    private static final boolean DEBUG = false;
    private static RemoteServer sRemoteServer = null;
    private static User sLoggedInUser = null;

    public static se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB getRetrievalDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getRetrievalDB();
        }
        else {
            db = new RetrievalDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB getManipulationDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getManipulationDB();
        }
        else {
            db = new ManipulationDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.InfluenceDB getInfluenceDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.InfluenceDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getInfluenceDB();
        }
        else {
            db = new InfluenceDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.GroupsDB getGroupsDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.GroupsDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getGroupsDB();
        }
        else {
            db = new GroupsDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.CreateRules getCreateRules() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.CreateRules db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getCreateRules();
        }
        else {
            db = new CreateRulesImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.BankingDB getBankingDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.BankingDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getBankingDB();
        }
        else {
            db = new BankingDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.AdminDB getAdminDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.AdminDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getAdminDB();
        }
        else {
            db = new AdminDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.TemplateDB getTemplateDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.TemplateDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getTemplateDB();
        }
        else {
            db = new TemplateDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    public static se.tdt.bobby.wodcc.proxy.interfaces.StatementsDB getStatementsDB() throws RemoteException, SQLException {
        se.tdt.bobby.wodcc.proxy.interfaces.StatementsDB db;
        if (AppPreferences.isGoOnline()) {
            if (sRemoteServer == null) {
                return null;
            }
            db = sRemoteServer.getStatementsDB();
        }
        else {
            db = new StatementsDBImpl();
        }
        if (login(db)) {
            return db;
        }
        else {
            return null;
        }
    }

    protected static RemoteServer getRemoteServer() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(AppPreferences.getServerHostName());
        RemoteServer stub = (RemoteServer) registry.lookup(RemoteServer.BIND_NAME);
        return stub;
    }

    private static boolean loginWithPanel(se.tdt.bobby.wodcc.proxy.interfaces.BasicDB pDB) throws SQLException, RemoteException {
        /*GridBagPanel panel = new GridBagPanel();
        JTextField userNameField = new JTextField(35);
        JPasswordField passwordField = new JPasswordField(35);
        panel.addLine("Username: ", userNameField);
        panel.addLine("Password: ", passwordField);
        userNameField.requestFocus();*/
        LoginDialog loginFrame = new LoginDialog(MainFrame.sMainFrame);
        loginFrame.setVisible(true);
        while (loginFrame.isOKPerformed()) {
            String userName = loginFrame.getUserName();
            String password = loginFrame.getPassword();
            User user = pDB.login(userName, password);
            if (user != null) {
                sLoggedInUser = user;
                sLoggedInUser.setUserName(userName);
                sLoggedInUser.setPassword(password);
                break;
            }
            else {
                sLoggedInUser = null;
                JOptionPane.showMessageDialog(null, "Wrong username or password", "Login", JOptionPane.ERROR_MESSAGE);
                loginFrame.setVisible(true);
            }
        }
        if(sLoggedInUser == null) {
            throw new NotLoggedInException();
        } else {
            AppPreferences.setPreferredDomain(sLoggedInUser.getDomain());
            return true;
        }
        //return sLoggedInUser != null;
    }

    private static boolean login(se.tdt.bobby.wodcc.proxy.interfaces.BasicDB pDB) throws SQLException, RemoteException {
        if (sLoggedInUser != null) {
            User user = pDB.login(sLoggedInUser.getUserName(), sLoggedInUser.getPassword());
            if (user != null) {
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong username or password", "Login", JOptionPane.ERROR_MESSAGE);
                return loginWithPanel(pDB);
            }
        }
        else {
            return loginWithPanel(pDB);
        }
    }

    public static boolean testLocalConnection() throws FileNotFoundException {
        return se.tdt.bobby.wodcc.server.db.BasicDBImpl.testConnection();
    }

    public static boolean testRemoteConnection() throws NotBoundException, RemoteException {
        RemoteServer server = getRemoteServer();
        sRemoteServer = server;
        if (server != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean testConnection() throws NotBoundException, RemoteException, FileNotFoundException {
        if (AppPreferences.isGoOnline()) {
            return testRemoteConnection();
        }
        else {
            return testLocalConnection();
        }
    }

    public static User getLoggedInUser() {
        return sLoggedInUser;
    }
}
