package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.mgm.User;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.remote.db.RemoteBasicDB;
import se.tdt.bobby.wodcc.server.ServerPreferences;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;
import java.util.Vector;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-10 00:03:34
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public abstract class BasicDBImpl extends UnicastRemoteObject implements RemoteBasicDB {
    protected Connection mConnection;
    protected Statement mStatement;
    protected static final boolean DEBUG = false;
    private String mConnectionUrl;
    protected User mLoginUser = null;
    protected static boolean sRemoteActor = true;

    static {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        }
        catch (ClassNotFoundException cnf) {
            System.out.println(cnf);
        }
    }

    public BasicDBImpl() throws RemoteException {
        super();
        String databaseFile = getDatabaseFile();
        File file = new File(databaseFile);
        //String url = "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=c:\\full\\path\\to\\database.mdb";
        mConnectionUrl = "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=" + file.getAbsolutePath();
    }

    protected static String getDatabaseFile() {
        if (sRemoteActor) {
            return ServerPreferences.getDatabaseFile();
        }
        else {
             return AppPreferences.getDatabaseFile();
        }
    }

    public static boolean isRemoteActor() {
        return sRemoteActor;
    }

    public static void setRemoteActor(boolean pRemoteActor) {
        sRemoteActor = pRemoteActor;
    }

    public static boolean testConnection() throws java.io.FileNotFoundException {
        //String filename = System.getProperty("wodcc.databaseFile");
        String filename = getDatabaseFile();
        if (filename == null) {
            throw new java.io.FileNotFoundException("No path to database entered.");
        }
        File file = new File(filename);
        if (!file.exists()) {
            throw new java.io.FileNotFoundException(file.getPath());
        }

        try {
            BasicDBImpl db = new BasicDBImpl() {
            };
            db.connectNoLogin();
            db.disconnect();
            return true;
        }
        catch (SQLException e) {
            if (DEBUG) e.printStackTrace();
        }
        catch (RemoteException e) {
            if (DEBUG) e.printStackTrace();
        }
        return false;
    }

    protected void connect() throws SQLException {
        //mConnection = DriverManager.getConnection("jdbc:odbc:wodcc");
        if (mLoginUser == null) {
            throw new NotLoggedInException();
        }
        connectNoLogin();
    }

    protected PreparedStatement connect(String pSqlStatement) throws SQLException {
        if (mLoginUser == null) {
            throw new NotLoggedInException();
        }
        mConnection = DriverManager.getConnection(mConnectionUrl);
        return mConnection.prepareStatement(pSqlStatement);
    }

    private void connectNoLogin() throws SQLException {
        mConnection = DriverManager.getConnection(mConnectionUrl);
        mStatement = mConnection.createStatement();
    }

    protected ResultSet query(String pQuery) throws SQLException {
        return mStatement.executeQuery(pQuery);
    }

    protected int update(String pQuery) throws SQLException {
        return mStatement.executeUpdate(pQuery);
    }

    protected void disconnect() throws SQLException {
        if (mStatement != null) {
            try {
                mStatement.close();
            }
            catch (SQLException e) {
                if (DEBUG) e.printStackTrace();
            }
        }
        if (mConnection != null) {
            mConnection.close();
        }
    }

    protected String replaceInString(String pString, List pListOfStrings, boolean pNullIsNull) {
        String cp = pString;
        if (DEBUG) System.out.println("[ManipulationDB][replaceInString][246] cp: " + cp);
        for (int i = 0; i < pListOfStrings.size(); i++) {
            String s = (String) pListOfStrings.get(i);
            if (s == null) {
                if (pNullIsNull) {
                    cp = cp.replaceAll("\\{" + i + "\\}", "NULL");
                }
                else {
                    cp = cp.replaceAll("\\{" + i + "\\}", "");
                }
            }
            else {
                cp = cp.replaceAll("\\{" + i + "\\}", quoteString(s));
            }
        }
        if (DEBUG) System.out.println("[ManipulationDB][replaceInString][262] returning " + cp);
        return cp;
    }

    protected String quoteString(String pS) {
        return pS.replaceAll("'", "''");
    }

    protected String replaceInString(String pString, List pListOfStrings) {
        return replaceInString(pString, pListOfStrings, false);
    }

    public User login(String pUserName, String pPassword) throws SQLException {
        User user = null;
        connectNoLogin();
        String query = "SELECT users.id, users.username, domains.id, domains.name, users.rights, users.fullName, users.otherDomainIsAll " +
                "FROM users, domains " +
                "WHERE users.domain = domains.id AND users.username = '" + quoteString(pUserName) + "' " +
                "AND users.password = '" + quoteString(pPassword) + "'";
        ResultSet rs = query(query);
        if (rs.next()) {
            int id = rs.getInt(1);
            String username = rs.getString(2);
            int dId = rs.getInt(3);
            String dname = rs.getString(4);
            Domain dom = new Domain(dId, dname);
            String rights = rs.getString(5);
            String fullName = rs.getString(6);
            boolean otherDomainIsAll = rs.getBoolean(7);
            if (rights == null) {
                rights = "";
            }
            user = new User(id, username, pPassword, dom, rights, fullName);
            user.setOtherDomainIsAll(otherDomainIsAll);
            rs.close();
            String q = "SELECT domain_id, domains.name FROM usersOtherDomain, domains WHERE domain_id = domains.id AND user_id=" + id;
            rs = query(q);
            while (rs.next()) {
                int domainId = rs.getInt(1);
                String name = rs.getString(2);
                if (user.getOtherDomains().size() <= 0) {
                    user.setOtherDomains(new Vector<Domain>());
                }
                dom = new Domain(domainId, name);
                user.getOtherDomains().add(dom);
            }
            rs.close();
            update("UPDATE users SET lastLogin = Now() WHERE id=" + id);
            user.setLastLogin(new java.util.Date());
        }
        disconnect();
        mLoginUser = user;
        return user;
    }

    public User getLoginUser() {
        return mLoginUser;
    }
}
