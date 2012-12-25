package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;
import se.tdt.bobby.wodcc.data.mgm.User;
import se.tdt.bobby.wodcc.data.mgm.UserRights;
import se.tdt.bobby.wodcc.remote.db.RemoteAdminDB;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-14 16:32:40
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class AdminDBImpl extends BasicDBImpl implements RemoteAdminDB {

    public AdminDBImpl() throws RemoteException {
        super();
    }

    private static final String UPDATE_USER = "UPDATE users SET username='{0}', password='{1}', domain={2}, " +
            "rights='{3}', fullName='{4}', description='{5}', otherDomainIsAll={6} " +
            "WHERE id={7}";
    private static final String REMOVE_USER_DOMAINS = "DELETE FROM usersOtherDomain WHERE user_id=";
    public static final String ADD_USER_OTHER_DOMAIN = "INSERT INTO usersOtherDomain (user_id, domain_id) VALUES({0}, {1})";

    public void updateUser(User pUser) throws SQLException {
        Domain currentDomain = getCurrentDomainForUser(pUser);
        if (mLoginUser.getDomain().equals(currentDomain)) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_USERS)) {
                throw new OperationDeniedException("You do not have permission to update Users in Domain " + currentDomain);
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_USERS_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to update Users in Domain " + currentDomain);
            }
            else if (!mLoginUser.hasDomain(currentDomain)) {
                throw new OperationDeniedException("You do not have permission to update Users in Domain " + currentDomain);
            }
        }
        /*if (!mLoginUser.getUserRights().get(UserRights.GIVE_USER_ACCESS_TO_OTHER_DOMAINS)) {
            if (pUser.getUserRights().canInflictOnOtherDomains()) {
                throw new OperationDeniedException("You do not have permission to give Users access to other Domains!");
            }
        }*/
        if (!mLoginUser.isOtherDomainIsAll()) {
            if (pUser.isOtherDomainIsAll()) {
                throw new OperationDeniedException("You do not have permission to give users access to All Domains");
            }
            else {
                for (int i = 0; i < pUser.getOtherDomains().size(); i++) {
                    Domain domain = pUser.getOtherDomains().elementAt(i);
                    if(!mLoginUser.hasDomain(domain)) {
                        throw  new OperationDeniedException("You do not have permission to give users access to Domain " + domain.getName());
                    }
                }
            }
        }
        connect();
        ArrayList params = new ArrayList(7);
        params.add(pUser.getUserName());
        params.add(pUser.getPassword());
        params.add(pUser.getDomain().getId() + "");
        params.add(pUser.getUserRights().toString());
        params.add(pUser.getFullName());
        params.add(pUser.getDescription());
        params.add(pUser.isOtherDomainIsAll() + "");
        params.add(pUser.getId() + "");
        //int result = update(replaceInString(UPDATE_USER, params));
        //if (DEBUG) System.out.println("AdminDBImpl.updateUser(38) result: " + result);
        mStatement.addBatch(replaceInString(UPDATE_USER, params));
        mStatement.addBatch(REMOVE_USER_DOMAINS + pUser.getId());
        List<Domain> domains = pUser.getOtherDomains();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            params = new ArrayList();
            params.add(pUser.getId() + "");
            params.add(domain.getId() + "");
            mStatement.addBatch(replaceInString(ADD_USER_OTHER_DOMAIN, params));
        }
        mStatement.executeBatch();
        disconnect();
    }

    private Domain getCurrentDomainForUser(User pUser) throws SQLException {
        Domain d = null;
        connect();
        ResultSet rs = query("SELECT domains.id, domains.name FROM users, domains " +
                             "WHERE users.domain = domains.id AND users.id = " + pUser.getId());
        if (rs.next()) {
            d = new Domain(rs.getInt(1), rs.getString(2));
        }
        disconnect();
        return d;
    }

    private static final String ADD_USER = "INSERT INTO users(username, password, domain, rights, fullName, description, otherDomainIsAll) " +
            "VALUES('{0}', '{1}', {2}, '{3}', '{4}', '{5}', {6})";

    public User addUser(User pUser) throws SQLException {
        if (mLoginUser.getDomain().equals(pUser.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_USERS)) {
                throw new OperationDeniedException("You do not have permission to add Users to Domain " + pUser.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_USERS_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to add Users to Domain " + pUser.getDomain());
            }
            else if (!mLoginUser.hasDomain(pUser.getDomain())) {
                throw new OperationDeniedException("You do not have permission to add Users to Domain " + pUser.getDomain());
            }
        }
        /*if (!mLoginUser.getUserRights().get(UserRights.GIVE_USER_ACCESS_TO_OTHER_DOMAINS)) {
            if (pUser.getUserRights().canInflictOnOtherDomains()) {
                throw new OperationDeniedException("You do not have permission to give Users access to other Domains!");
            }
        }*/
        if (!mLoginUser.isOtherDomainIsAll()) {
            if (pUser.isOtherDomainIsAll()) {
                throw new OperationDeniedException("You do not have permission to give users access to All Domains");
            }
            else {
                for (int i = 0; i < pUser.getOtherDomains().size(); i++) {
                    Domain domain = pUser.getOtherDomains().elementAt(i);
                    if(!mLoginUser.hasDomain(domain)) {
                        throw  new OperationDeniedException("You do not have permission to give users access to Domain " + domain.getName());
                    }
                }
            }
        }
        User user = null;
        connect();
        ArrayList params = new ArrayList(7);
        params.add(pUser.getUserName());
        params.add(pUser.getPassword());
        params.add(pUser.getDomain().getId() + "");
        params.add(pUser.getUserRights().toString());
        params.add(pUser.getFullName());
        params.add(pUser.getDescription());
        params.add(String.valueOf(pUser.isOtherDomainIsAll()));
        int result = update(replaceInString(ADD_USER, params));
        if (DEBUG) System.out.println("AdminDBImpl.addUser(56) result: " + result);
        if (result > 0) {
            ResultSet rs = query("SELECT MAX(id) FROM users");
            if (rs.next()) {
                int id = rs.getInt(1);
                user = new User(id, pUser.getUserName(), pUser.getPassword(), pUser.getDomain(), pUser.getUserRights(), pUser.getFullName(), pUser.getDescription(), pUser.getLastLogin(), pUser.isOtherDomainIsAll(), pUser.getOtherDomains());
                //user.setId(id);
                rs.close();
                List<Domain> domains = pUser.getOtherDomains();
                for (int i = 0; i < domains.size(); i++) {
                    Domain domain = domains.get(i);
                    params = new ArrayList();
                    params.add(pUser.getId() + "");
                    params.add(domain.getId() + "");
                    mStatement.addBatch(replaceInString(ADD_USER_OTHER_DOMAIN, params));
                }
                mStatement.executeBatch();
            }
        }
        disconnect();
        return user;
    }

    public User getUser(int pUserId) throws SQLException, RemoteException {
        if (!mLoginUser.getUserRights().get(UserRights.LIST_USERS)) {
            throw new OperationDeniedException("You do not have permission to list Users or Read");
        }
        return null;
    }

    public List<User> getFullUsersListByDomain() throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.LIST_USERS)) {
            throw new OperationDeniedException("You do not have permission to list Users");
        }
        List<User> users = new ArrayList<User>();
        HashMap<Integer, User> usersMap = new HashMap<Integer, User>();
        String q = "SELECT users.id, username, password, domains.id, domains.name, domains.history, rights, fullName, description, lastLogin, otherDomainIsAll " +
                "FROM users, domains " +
                "WHERE users.domain = domains.id " +
                "ORDER BY domains.name, users.username";
        connect();
        ResultSet rs = query(q);
        //DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Calendar cal = Calendar.getInstance();
        while (rs.next()) {
            int id = rs.getInt(1);
            String username = rs.getString(2);
            String password = rs.getString(3);
            int dId = rs.getInt(4);
            String dName = rs.getString(5);
            String dHistory = rs.getString(6);
            String rights = rs.getString(7);
            String fullName = rs.getString(8);
            String description = rs.getString(9);
            Date lastLogin = rs.getDate(10, cal);
            boolean otherDomainIsAll = rs.getBoolean(11);
            if (DEBUG) System.out.println("AdminDBImpl.getFullUsersListByDomain(138) " + lastLogin);
            Domain dom = new Domain(dId, dName, dHistory);
            User user = new User(id, username, password, dom, rights, fullName, description);
            user.setLastLogin(lastLogin);
            user.setOtherDomainIsAll(otherDomainIsAll);
            users.add(user);
            usersMap.put(new Integer(user.getId()), user);
        }
        rs.close();
        q = "SELECT user_id, domain_id, domains.name FROM usersOtherDomain, domains WHERE domain_id = domains.id";
        rs = query(q);
        while (rs.next()) {
            int userId = rs.getInt(1);
            int domainId = rs.getInt(2);
            String name = rs.getString(3);
            User user = usersMap.get(new Integer(userId));
            if (user != null) {
                if (user.getOtherDomains().size() <= 0) {
                    user.setOtherDomains(new Vector<Domain>());
                }
                Domain dom = new Domain(domainId, name);
                user.getOtherDomains().add(dom);
            }
        }
        disconnect();
        return users;
    }

    public void addDomain(Domain pDomain) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_DOMAIN)) {
            throw new OperationDeniedException("You do not have permission to update Domain " + pDomain.getName());
        }
        String q = "INSERT INTO domains(name, history) VALUES('" +
                quoteString(pDomain.getName()) + "', '" +
                quoteString(pDomain.getHistory()) + "')";
        connect();
        int result = update(q);
        if (result > 0) {
            ResultSet rs = query("SELECT MAX(id) FROM domains");
            if (rs.next()) {
                int id = rs.getInt(1);
                rs.close();
                ArrayList params = new ArrayList(7);
                params.add(pDomain.getName().toLowerCase());
                params.add(pDomain.getName().toLowerCase());
                params.add(id + "");
                UserRights rights = new UserRights("");
                rights.set(UserRights.LIST_USERS, true);
                rights.set(UserRights.ADD_USERS, true);
                rights.set(UserRights.UPDATE_USERS, true);
                rights.set(UserRights.GIVE_USER_ACCESS_TO_OTHER_DOMAINS, false);
                params.add(rights.toString());
                params.add(pDomain.getName() + " User Administrator");
                params.add("Default Useradministrator for Domain " + pDomain.getName() + ".\nDo not forget to change the password");
                result = update(replaceInString(ADD_USER, params));
            }
        }
        disconnect();
    }

    public void updateDomain(Domain pDomain) throws SQLException {
        if (mLoginUser.getDomain().equals(pDomain)) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to update Domain " + pDomain.getName());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to update Domain " + pDomain.getName());
            }
        }
        String q = "UPDATE domains SET name = '" + quoteString(pDomain.getName()) + "', " +
                "history = '" + quoteString(pDomain.getHistory()) + "' " +
                "WHERE id = " + pDomain.getId();
        connect();
        int result = update(q);
        if (DEBUG) System.out.println("AdminDBImpl.updateDomain(187) result: " + result);
        disconnect();
    }

    public boolean changePassword(String pCurrentPassword, String pNewPassword) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.CHANGE_PASSWORD)) {
            throw new OperationDeniedException("You do not have permission to change your password!");
        }
        if (!mLoginUser.getPassword().equals(pCurrentPassword)) {
            throw new SQLException("Current Password Incorrect!");
        }
        connect();
        int result = update("UPDATE users SET password='" + quoteString(pNewPassword) + "' " +
                            "WHERE username='" + quoteString(mLoginUser.getUserName()) + "' " +
                            "AND password='" + quoteString(pCurrentPassword) + "'");
        if (DEBUG) System.out.println("AdminDBImpl.changePassword(212) result = " + result);
        disconnect();
        return (result >= 1);
    }
}
