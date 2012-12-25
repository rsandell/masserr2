package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;
import se.tdt.bobby.wodcc.data.mgm.UserRights;
import se.tdt.bobby.wodcc.remote.db.RemoteGroupsDB;
import se.tdt.bobby.wodcc.server.ServerPreferences;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

/**
 * Description
 * <p/>
 * Created: 2004-feb-17 21:22:35
 *
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class GroupsDBImpl extends BasicDBImpl implements RemoteGroupsDB {

    public GroupsDBImpl() throws RemoteException {
        super();
    }

    public Vector getGroups() throws SQLException {
        Vector groups = new Vector();
        connect();
        String q = "SELECT id, name, date, description, type " +
                "FROM groups " +
                "ORDER BY date, name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Date date = rs.getDate(3);
            String descS = rs.getString(4);
            String type = rs.getString(5);
            RolesGroup group = new RolesGroup(id, name, date, descS, type);
            groups.add(group);
        }
        disconnect();
        return groups;
    }

    public Vector getRootGroups() throws SQLException {
        Vector groups = new Vector();
        connect();
        String q = "SELECT id, name, date, description, type " +
                "FROM groups " +
                "WHERE parent IS NULL" +
                " ORDER BY date, name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Date date = rs.getDate(3);
            String descS = rs.getString(4);
            String type = rs.getString(5);
            RolesGroup group = new RolesGroup(id, name, date, descS, type);
            groups.add(group);
        }
        disconnect();
        return groups;
    }

    public List getGroupsInGroup(int pGroupId) throws SQLException {
        Vector groups = new Vector();
        connect();
        String q = "SELECT id, name, date, description, type " +
                "FROM groups " +
                "WHERE parent=" + pGroupId +
                " ORDER BY date, name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Date date = rs.getDate(3);
            String descS = rs.getString(4);
            String type = rs.getString(5);
            RolesGroup group = new RolesGroup(id, name, date, descS, type);
            groups.add(group);
        }
        disconnect();
        return groups;
    }

    public List getRolesInGroup(int pId) throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT roles.id, roles.name, roles.clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses, roles.slp, roles.player_id, players.name\n" +
                "FROM players RIGHT JOIN (groupRoles INNER JOIN (domains INNER JOIN (roles INNER JOIN clans ON roles.clan = clans.id) ON roles.domain = domains.id) ON groupRoles.role_id = roles.id) ON players.id = roles.player_id\n" +
                "WHERE group_id = " + pId + "\n" +
                "ORDER BY clans.name, roles.name";
//        String q = "SELECT roles.id, roles.name, clan, clans.name, roles.goul, roles.player_name " +
//                "FROM roles, clans, groupRoles " +
//                "WHERE roles.clan = clans.id AND groupRoles.role_id = roles.id AND groupRoles.group_id = " + pId +
//                " ORDER BY clans.name, roles.name";
        ResultSet rs = query(q);
        while (rs.next()) {
//            int id = rs.getInt(1);
//            String name = rs.getString(2);
//            int clanId = rs.getInt(3);
//            String clanName = rs.getString(4);
//            boolean goul = rs.getBoolean(5);
//            String playerName = rs.getString(6);
//            Clan c = new Clan(clanId, clanName);
//            Role role = new Role(id, name, c);
//            role.setPlayerName(playerName);
//            role.setGhoul(goul);
//            v.addElement(role);
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int clanId = rs.getInt(3);
            String clanName = rs.getString(4);
            boolean goul = rs.getBoolean(5);
            //String playerName = rs.getString(6);
            int vitals = rs.getInt(7);
            int domainId = rs.getInt(8);
            String domainName = rs.getString(9);
            String weaknesses = rs.getString(10);
            boolean slp = rs.getBoolean(11);
            int playerId = rs.getInt(12);
            String player_Name = rs.getString(13);
            Player player = null;
            if (player_Name != null) {
                player = new Player(playerId, player_Name);
            }
            Clan c = new Clan(clanId, clanName);
            c.setWeaknesses(weaknesses);
            Role role = new Role(id, name, c);
            //role.setPlayerName(playerName);
            role.setPlayer(player);
            role.setGhoul(goul);
            role.setVitals(vitals);
            role.setDomain(new Domain(domainId, domainName));
            role.setSLP(slp);
            v.addElement(role);
        }
        disconnect();
        return v;
    }


    public void addRolesToGroup(Object[] pRoles, RolesGroup pGroup) throws SQLException, ClassCastException {
        checkRolesToGroupPermission(pRoles, "You do not have permission to add Roles from Domain {0} to a Group", UserRights.ADD_ROLE_TO_GROUP, UserRights.ADD_ROLE_TO_GROUP_OTHER_DOMAIN);
        connect();
        for (int i = 0; i < pRoles.length; i++) {
            Role role = (Role) pRoles[i];
            String q = "INSERT INTO groupRoles(role_id, group_id) VALUES(" +
                    role.getId() + ", " + pGroup.getId() + ")";
            mStatement.addBatch(q);
        }
        mStatement.executeBatch();
        disconnect();
    }

    private void checkRolesToGroupPermission(Object[] pRoles, String pErrorMsg, int pSameDomainRight, int pOtherDomainRight) throws ClassCastException, OperationDeniedException {
        HashMap<Integer, Domain> invalidDomains = new HashMap<Integer, Domain>();
        for (int i = 0; i < pRoles.length; i++) {
            Role role = (Role) pRoles[i];
            if (mLoginUser.getDomain().equals(role.getDomain())) {
                if (!mLoginUser.getUserRights().get(pSameDomainRight)) {
                    invalidDomains.put(new Integer(role.getDomain().getId()), role.getDomain());
                }
            }
            else {
                if (!mLoginUser.getUserRights().get(pOtherDomainRight)) {
                    invalidDomains.put(new Integer(role.getDomain().getId()), role.getDomain());
                }
            }
        }
        if (invalidDomains.size() > 0) {
            Collection<Domain> domains = invalidDomains.values();
            Iterator<Domain> it = domains.iterator();
            int i = 0;
            String invDomains = "";
            while (it.hasNext()) {
                Domain domain = it.next();
                if (i != 0 && i < invalidDomains.size() - 1) {
                    invDomains += ", ";
                }
                else if (1 != 0 && i == invalidDomains.size() - 1) {
                    invDomains += " and ";
                }
                invDomains += domain.getName();
                i++;
            }
            throw new OperationDeniedException(pErrorMsg.replace("{0}", invDomains));
        }
    }

    public Vector getGroupTypes() throws SQLException {
        Vector v = new Vector();
        connect();
        ResultSet rs = query("SELECT DISTINCT type FROM groups ORDER BY type");
        while (rs.next()) {
            v.add(rs.getString(1));
        }
        disconnect();
        return v;
    }

    public RolesGroup createGroup(String pName, String pType, String pDescription, Date pDate, Integer pParent) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.CREATE_GROUP)) {
            throw new OperationDeniedException("You do not have permission to create a Group");
        }
        RolesGroup group = null;
        connect();
        String q = "INSERT INTO groups(name, type, description, [date], parent) VALUES ('" +
                quoteString(pName) + "', '" + quoteString(pType) + "', '" + quoteString(pDescription) + "', '" +
                DateFormat.getDateInstance(DateFormat.SHORT).format(pDate) + "', " + (pParent != null ? pParent.toString() : "NULL") + ")";
        if (DEBUG) System.out.println("[GroupsDB][createGroup][141] " + q);
        int result = update(q);
        if (result > 0) {
            q = "SELECT MAX(id) FROM groups";
            ResultSet rs = query(q);
            if (rs.next()) {
                int id = rs.getInt(1);
                group = new RolesGroup(id, pName, pDate, pDescription, pType);
            }
        }
        else {
            if (DEBUG) System.out.println("[GroupsDB][createGroup][145] Nothig created!");
        }
        disconnect();
        return group;
    }

    public void removeRolesFromTheirGroup(HashMap pRolesToRemove) throws SQLException {
        Set roles = pRolesToRemove.keySet();
        if (!roles.isEmpty()) {
            checkRolesToGroupPermission(roles.toArray(), "You do not have permission to remove Roles in Domain {0} from a Group", UserRights.REMOVE_ROLE_FROM_GROUP, UserRights.REMOVE_ROLE_FROM_GROUP_OTHER_DOMAIN);
            String q;
            connect();
            Iterator it = roles.iterator();
            while (it.hasNext()) {
                Role role = (Role) it.next();
                RolesGroup group = (RolesGroup) pRolesToRemove.get(role);
                q = "DELETE FROM groupRoles WHERE group_id = " + group.getId() + " AND role_id = " + role.getId();
                mStatement.addBatch(q);
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    public void deleteGroups(List pGroupsToRemove) throws SQLException {
        if (pGroupsToRemove.size() > 0) {
            checkDeleteGroupPermission(pGroupsToRemove);
            connect();
            String q;
            for (int i = 0; i < pGroupsToRemove.size(); i++) {
                RolesGroup rolesGroup = (RolesGroup) pGroupsToRemove.get(i);
                q = "DELETE from groups WHERE id = " + rolesGroup.getId();
                mStatement.addBatch(q);
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    private void checkDeleteGroupPermission(List pGroupsToRemove) throws SQLException {
        String groupIds = "";
        for (int i = 0; i < pGroupsToRemove.size(); i++) {
            RolesGroup rolesGroup = (RolesGroup) pGroupsToRemove.get(i);
            if (i != 0) {
                groupIds += " OR ";
            }
            groupIds += "group_id = " + rolesGroup.getId();
        }
        String q = "SELECT DISTINCT domains.id, domains.name " +
                "FROM groupRoles, roles, domains " +
                "WHERE groupRoles.role_id = roles.id AND roles.domain = domains.id AND (" +
                groupIds + ")";
        connect();
        List<Domain> domains = new ArrayList<Domain>();
        ResultSet rs = query(q);
        while (rs.next()) {
            Domain dom = new Domain(rs.getInt(1), rs.getString(2));
            domains.add(dom);
        }
        disconnect();
        List<Domain> illegalDomains = new ArrayList<Domain>();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            if (mLoginUser.getDomain().equals(domain)) {
                if (!mLoginUser.getUserRights().get(UserRights.DELETE_GROUP)) {
                    illegalDomains.add(domain);
                }
            }
            else {
                if (!mLoginUser.getUserRights().get(UserRights.DELETE_GROUP_OTHER_DOMAIN)) {
                    illegalDomains.add(domain);
                }
                else if (!mLoginUser.hasDomain(domain)) {
                    illegalDomains.add(domain);
                }
            }
        }
        if (illegalDomains.size() > 0) {
            String domainsStr = "";
            for (int i = 0; i < illegalDomains.size(); i++) {
                Domain domain = illegalDomains.get(i);
                if (i != 0 && i < illegalDomains.size() - 1) {
                    domainsStr += ", ";
                }
                else if (i != 0 && i == illegalDomains.size() - 1) {
                    domainsStr += " and ";
                }
                domainsStr += domain.getName();
            }
            throw new OperationDeniedException("You do not have permission to delete Groups with Roles from Domain " + domainsStr);
        }
        else {
            groupIds = "";
            for (int i = 0; i < pGroupsToRemove.size(); i++) {
                RolesGroup rolesGroup = (RolesGroup) pGroupsToRemove.get(i);
                if (i != 0) {
                    groupIds += " OR ";
                }
                groupIds += "parent = " + rolesGroup.getId();
            }
            q = "SELECT DISTINCT id FROM groups WHERE " + groupIds;
            pGroupsToRemove = new ArrayList();
            connect();
            rs = query(q);
            while (rs.next()) {
                RolesGroup group = new RolesGroup();
                group.setId(rs.getInt(1));
                pGroupsToRemove.add(group);
            }
            disconnect();
            if (pGroupsToRemove.size() > 0) {
                checkDeleteGroupPermission(pGroupsToRemove);
            }
        }
    }

    public void renameGroup(RolesGroup pGroup, String pNewName) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.UPDATE_GROUP)) {
            throw new OperationDeniedException("You do not have permission to Update Groups!");
        }
        connect();
        int rows = update("UPDATE groups SET name = '" + quoteString(pNewName) + "' WHERE id = " + pGroup.getId());
        if (DEBUG) System.out.println("[GroupsDB][renameGroup][190] rows: " + rows);
        disconnect();
    }

    public void updateGroup(RolesGroup pGroup) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.UPDATE_GROUP)) {
            throw new OperationDeniedException("You do not have permission to Update Groups!");
        }
        connect();
        int rows = update("UPDATE groups SET name = '" + quoteString(pGroup.getName()) + "'," +
                          "type = '" + quoteString(pGroup.getType()) + "'," +
                          "[date] = '" + DateFormat.getDateInstance(DateFormat.SHORT).format(pGroup.getDate()) + "'," +
                          "description = '" + quoteString(pGroup.getDescription()) + "' " +
                          "WHERE id = " + pGroup.getId());
        if (DEBUG) System.out.println("[GroupsDB][updateGroup][201] rows: " + rows);
        disconnect();
    }

    public void storeGroupTypeIcon(String pFileName, byte[] pBytes) throws IOException, RemoteException, OperationDeniedException {
        if (BasicDBImpl.isRemoteActor()) {
            if (!mLoginUser.getUserRights().get(UserRights.CREATE_GROUP) || !mLoginUser.getUserRights().get(UserRights.UPDATE_GROUP)) {
                throw new OperationDeniedException("You do not have permission to create or update Groups therefore you cannot store groupTypeIcons");
            }
            RandomAccessFile file;
            if (ServerPreferences.getBaseDir() != null) {
                String baseDir = ServerPreferences.getBaseDir();
                File typesDir = new File(baseDir, "img/groupTypes");
                file = new RandomAccessFile(new File(typesDir, pFileName), "rw");
            }
            else {
                file = new RandomAccessFile(new File("img/groupTypes", pFileName), "rw");
            }
            file.write(pBytes);
            file.close();
        }
    }

    public HashMap<String, byte[]> getGroupTypeIconFiles(ArrayList<String> pDoNotInclude) throws IOException {
        HashMap<String, byte[]> theOnes = new HashMap<String, byte[]>();
        File dir;
        if (ServerPreferences.getBaseDir() != null) {
            String baseDir = ServerPreferences.getBaseDir();
            dir = new File(baseDir, "img/groupTypes");
        }
        else {
            dir = new File("img/groupTypes");
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    boolean isNew = true;
                    for (int j = 0; j < pDoNotInclude.size(); j++) {
                        String s = pDoNotInclude.get(j);
                        if (file.getName().equalsIgnoreCase(s)) {
                            isNew = false;
                            break;
                        }
                    }
                    if (isNew) {
                        RandomAccessFile readFile = new RandomAccessFile(file, "r");
                        byte[] bytes = new byte[(int) readFile.length()];
                        readFile.readFully(bytes);
                        readFile.close();
                        theOnes.put(file.getName(), bytes);
                    }
                }
            }
        }
        return theOnes;
    }
}
