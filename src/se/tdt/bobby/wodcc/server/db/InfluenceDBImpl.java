package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;
import se.tdt.bobby.wodcc.data.mgm.UserRights;
import se.tdt.bobby.wodcc.remote.db.RemoteInfluenceDB;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-feb-03 23:52:17
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class InfluenceDBImpl extends BasicDBImpl implements RemoteInfluenceDB {

    public InfluenceDBImpl() throws RemoteException {
        super();
    }

    public void updateRoleProfessions(Role pRole, List pProfessions) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_ROLE_PROFESSION)) {
                throw new OperationDeniedException("You do not have permission to update the Professions for Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_ROLE_PROFESSION_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to update the Professions for Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to update the Professions for Roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        String q = "DELETE FROM roleProfessions WHERE role_id = " + pRole.getId();
        mStatement.addBatch(q);
        for (int i = 0; i < pProfessions.size(); i++) {
            Profession profession = (Profession) pProfessions.get(i);
            q = "INSERT INTO roleProfessions (role_id, profession_id, mask) VALUES(" +
                    pRole.getId() + ", " + profession.getId() + ", " + profession.isMask() +
                    ")";
            mStatement.addBatch(q);
        }
        mStatement.executeBatch();
        disconnect();
    }

    private static final String REMOVE_ROLE_RESOURCE = "DELETE FROM roleResources WHERE " +
            "role_id = {0} AND resource_id = {1}";

    public void removeResourcesFromRole(Role pRole, Resource[] pResources) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.REMOVE_RESOURCE_FROM_ROLE)) {
                throw new OperationDeniedException("You do not have permission to remove Resources from Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.REMOVE_RESOURCE_FROM_ROLE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to remove Resources from Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to remove Resources from Roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        ArrayList par = new ArrayList();
        for (int i = 0; i < pResources.length; i++) {
            Resource resource = pResources[i];
            par.add(pRole.getId() + "");
            par.add(resource.getId() + "");
            mStatement.addBatch(replaceInString(REMOVE_ROLE_RESOURCE, par));
            par = new ArrayList();
        }
        mStatement.executeBatch();
        disconnect();
    }

    public void addInfluenceToRole(Role pRole, Influence pInf) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_INFLUENCE_TO_ROLE)) {
                throw new OperationDeniedException("You do not have permission to add Influences to Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_INFLUENCE_TO_ROLE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to add Influences to Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to add Influences to Roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        String q = "INSERT INTO roleInfluences (role_id, influence_id, dots, notes) VALUES(" +
                pRole.getId() + ", " + pInf.getId() + ", " + pInf.getDots() + ", '" + quoteString(pInf.getNotes()) + "')";
        int rows = update(q);
        if (DEBUG) System.out.println("[InfluenceDB][addInfluenceToRole][57] rows: " + rows);
        disconnect();
    }

    public void removeInfluencesFromRole(Role pRole, Influence[] pInfluences) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.REMOVE_INFLUENCE_FROM_ROLE)) {
                throw new OperationDeniedException("You do not have permission to remove Influences from Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.REMOVE_INFLUENCE_FROM_ROLE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to remove Influences from Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to remove Influences from Roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        String q = "";
        for (int i = 0; i < pInfluences.length; i++) {
            Influence influence = pInfluences[i];
            q = "DELETE FROM roleInfluences WHERE role_id = " + pRole.getId() + " AND influence_id = " + influence.getId();
            mStatement.addBatch(q);
        }
        mStatement.executeBatch();
        disconnect();
    }

    public Vector<Plot> getPlotTitles() throws SQLException {
        Vector<Plot> vec = new Vector<Plot>();
        connect();
        ResultSet rs = query("SELECT plots.id, title, domains.id, domains.name " +
                             "FROM plots, domains " +
                             "WHERE plots.domain = domains.id " +
                             "ORDER BY plots.id");
        while (rs.next()) {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            int dId = rs.getInt(3);
            String dName = rs.getString(4);
            Plot p = new Plot(id, title, new Domain(dId, dName));
            vec.add(p);
        }
        disconnect();
        return vec;
    }

    public Vector<Plot> getPlotTitles(Domain pDomain) throws SQLException {
        Vector<Plot> vec = new Vector<Plot>();
        connect();
        ResultSet rs = query("SELECT plots.id, title, domains.id, domains.name, plots.done " +
                             "FROM plots, domains " +
                             "WHERE plots.domain = domains.id AND domain = " + pDomain.getId() +
                             " ORDER BY plots.done DESC, plots.id ASC");
        while (rs.next()) {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            int dId = rs.getInt(3);
            String dName = rs.getString(4);
            boolean done = rs.getBoolean(5);
            Plot p = new Plot(id, title, new Domain(dId, dName));
            p.setDone(done);
            vec.add(p);
        }
        disconnect();
        return vec;
    }

    public Plot getPlot(Plot pPlot) throws SQLException {
        Plot p = null;
        connect();
        ResultSet rs = query("SELECT plots.id, title, description, created, positive, negative, done, domains.id, domains.name, SLdescription " +
                             "FROM plots, domains " +
                             "WHERE plots.domain = domains.id AND plots.id = " + pPlot.getId());
        if (rs.next()) {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            Date created = rs.getDate(4);
            String positive = rs.getString(5);
            String negative = rs.getString(6);
            boolean done = rs.getBoolean(7);
            int dId = rs.getInt(8);
            String dName = rs.getString(9);
            String sl = rs.getString(10);
            p = new Plot(id, title, description, created, positive, negative, done, new Domain(dId, dName), sl);
        }
        disconnect();
        return p;
    }

    private static final String ADD_PLOT = "INSERT INTO plots(title, description, created, positive, negative, done, domain, SLdescription) VALUES(" +
            "'{0}', '{1}', Now(), '{2}', '{3}', {4}, {5}, '{6}')";

    public void addPlot(Plot pPlot) throws SQLException {
        if (mLoginUser.getDomain().equals(pPlot.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_PLOT)) {
                throw new OperationDeniedException("You do not have permission to add plots to Domain " + pPlot.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_PLOT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to add plots to Domain " + pPlot.getDomain());
            }
            else if (!mLoginUser.hasDomain(pPlot.getDomain())) {
                throw new OperationDeniedException("You do not have permission to add plots to Domain " + pPlot.getDomain());
            }
        }
        ArrayList params = new ArrayList();
        params.add(pPlot.getTitle());
        params.add(pPlot.getDescription());
        params.add(pPlot.getPositive());
        params.add(pPlot.getNegative());
        params.add(pPlot.isDone() + "");
        params.add(pPlot.getDomain().getId() + "");
        params.add(pPlot.getSLdescription());
        connect();
        int rows = update(replaceInString(ADD_PLOT, params));
        if (DEBUG) System.out.println("InfluenceDBImpl.addPlot(195) rows: " + rows);
        disconnect();
    }

    public static final String UPDATE_PLOT = "UPDATE plots SET title = '{0}', description = '{1}', " +
            "positive = '{2}', negative = '{3}', done = {4}, domain = {5}, SLdescription = '{6}' " +
            "WHERE id = {7}";

    public void updatePlot(Plot pPlot) throws SQLException {
        Domain currentDomain = getCurrentDomainForPlot(pPlot);
        if (mLoginUser.getDomain().equals(currentDomain)) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_PLOT)) {
                throw new OperationDeniedException("You do not have permission to update plots in Domain " + currentDomain);
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_PLOT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to update plots in Domain " + currentDomain);
            }
            else if (!mLoginUser.hasDomain(currentDomain)) {
                throw new OperationDeniedException("You do not have permission to update plots in Domain " + currentDomain);
            }
        }
        if (currentDomain.getId() != pPlot.getDomain().getId()) {
            if (mLoginUser.getUserRights().get(UserRights.UPDATE_PLOT_OTHER_DOMAIN)) {
                if (!mLoginUser.hasDomain(pPlot.getDomain())) {
                    throw new OperationDeniedException("You do not have permission to move plots to Domain " + pPlot.getDomain());
                }
            }
            else {
                throw new OperationDeniedException("You do not have permission to move plots to Domain " + pPlot.getDomain());
            }
        }
        ArrayList params = new ArrayList();
        params.add(pPlot.getTitle());
        params.add(pPlot.getDescription());
        params.add(pPlot.getPositive());
        params.add(pPlot.getNegative());
        params.add(pPlot.isDone() + "");
        params.add(pPlot.getDomain().getId() + "");
        params.add(pPlot.getSLdescription());
        params.add(pPlot.getId() + "");
        connect();
        int rows = update(replaceInString(UPDATE_PLOT, params));
        if (DEBUG) System.out.println("InfluenceDBImpl.updatePlot(212) rows: " + rows);
        disconnect();
    }

    private Domain getCurrentDomainForPlot(Plot pPlot) throws SQLException {
        Domain dom = null;
        connect();
        ResultSet rs = query("SELECT domains.id, domains.name " +
                             "FROM plots, domains " +
                             "WHERE plots.domain = domains.id AND plots.id = " + pPlot.getId());
        if (rs.next()) {
            dom = new Domain(rs.getInt(1), rs.getString(2));
        }
        disconnect();
        return dom;
    }

    public Vector<Plot> getAvailablePlotsForRole(Role pRole, Domain pDomain) throws SQLException {
        Vector<Plot> plots = new Vector<Plot>();
        String q = "SELECT plots.id, title, domains.id, domains.name, plots.description " +
                "FROM plots, domains " +
                "WHERE plots.domain = domains.id AND " +
                "plots.done = false AND " +
                "plots.id NOT IN (SELECT plot_id FROM role_plots WHERE role_id = " + pRole.getId() + ") " +
                "AND domain = " + pDomain.getId();
        connect();
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            int dId = rs.getInt(3);
            String dName = rs.getString(4);
            String description = rs.getString(5);
            Plot p = new Plot(id, title, new Domain(dId, dName));
            p.setDescription(description);
            p.setDone(false);
            plots.add(p);
        }
        disconnect();
        return plots;
    }

    public Vector<Plot> getAssignedPlotsForRole(Role pRole) throws SQLException {
        Vector<Plot> plots = new Vector<Plot>();
        String q = "SELECT plots.id, title, domains.id, domains.name, plots.done " +
                "FROM plots, role_plots, domains " +
                "WHERE plots.domain = domains.id " +
                "AND plots.id = role_plots.plot_id " +
                "AND role_plots.role_id = " + pRole.getId() +
                " ORDER BY plots.done, plots.id";
        connect();
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            int dId = rs.getInt(3);
            String dName = rs.getString(4);
            boolean done = rs.getBoolean(5);
            Plot p = new Plot(id, title, new Domain(dId, dName));
            p.setDone(done);
            plots.add(p);
        }
        disconnect();
        return plots;
    }

    public void assignPlots(Object[] pPlots, Role pRole) throws SQLException, ClassCastException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT)) {
                throw new OperationDeniedException("You do not have permission to assign plots to Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to assign plots to Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to assign plots to Roles in Domain " + pRole.getDomain());
            }
        }
        if (pPlots.length > 0) {
            connect();
            for (int i = 0; i < pPlots.length; i++) {
                Plot plot = (Plot) pPlots[i];
                String q = "INSERT INTO role_plots(role_id, plot_id) VALUES(" +
                        pRole.getId() + ", " + plot.getId() + ")";
                mStatement.addBatch(q);
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    public void assignPlots(int[] pPlotIds, Role pRole) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT)) {
                throw new OperationDeniedException("You do not have permission to assign plots to Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to assign plots to Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to assign plots to Roles in Domain " + pRole.getDomain());
            }
        }
        if (pPlotIds.length > 0) {
            connect();
            for (int i = 0; i < pPlotIds.length; i++) {
                int plot = pPlotIds[i];
                String q = "INSERT INTO role_plots(role_id, plot_id) VALUES(" +
                        pRole.getId() + ", " + plot + ")";
                mStatement.addBatch(q);
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    public void unAssignPlots(Object[] pPlots, Role pRole) throws SQLException, ClassCastException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT)) {
                throw new OperationDeniedException("You do not have permission to unAssign plots from Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to unAssign plots from Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to unAssign plots from Roles in Domain " + pRole.getDomain());
            }
        }
        if (pPlots.length > 0) {
            connect();
            for (int i = 0; i < pPlots.length; i++) {
                Plot plot = (Plot) pPlots[i];
                String q = "DELETE FROM role_plots WHERE role_id = " + pRole.getId() + " AND " +
                        "plot_id = " + plot.getId();
                mStatement.addBatch(q);
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    public void unAssignPlots(int[] pPlotIds, Role pRole) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT)) {
                throw new OperationDeniedException("You do not have permission to unAssign plots from Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ASSIGN_UNASSIGN_PLOT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to unAssign plots from Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to unAssign plots from Roles in Domain " + pRole.getDomain());
            }
        }
        if (pPlotIds.length > 0) {
            connect();
            for (int i = 0; i < pPlotIds.length; i++) {
                int plot = pPlotIds[i];
                String q = "DELETE FROM role_plots WHERE role_id = " + pRole.getId() + " AND " +
                        "plot_id = " + plot;
                mStatement.addBatch(q);
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    public List<Plot> getPlots(Domain pDomain, boolean pListDone) throws SQLException, RemoteException {
        List<Plot> list = new ArrayList<Plot>();
        connect();
        String q = "SELECT plots.id, title, description, created, positive, negative, done, domains.id, domains.name, SLdescription " +
                "FROM plots, domains " +
                "WHERE plots.domain = domains.id";
        if (pDomain != null) {
            q += " AND plots.domain=" + pDomain.getId() + " ";
        }
        if (!pListDone) {
            q += " AND plots.done = false";
        }
        q += " ORDER BY domains.name, plots.done, plots.id";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            Date created = rs.getDate(4);
            String positive = rs.getString(5);
            String negative = rs.getString(6);
            boolean done = rs.getBoolean(7);
            int dId = rs.getInt(8);
            String dName = rs.getString(9);
            String sl = rs.getString(10);
            Plot p = new Plot(id, title, description, created, positive, negative, done, new Domain(dId, dName), sl);
            list.add(p);
        }
        disconnect();
        return list;
    }

    public HashMap<Integer, List<IntWithString>> getAssignedRolesForPlots(Domain pDomain, boolean pListDone) throws SQLException, RemoteException {
        HashMap<Integer, List<IntWithString>> map = new HashMap<Integer, List<IntWithString>>();
        String q = "SELECT plot_id, role_id, roles.name " +
                "FROM role_plots, roles, plots, domains " +
                "WHERE plots.domain = domains.id AND role_plots.plot_id = plots.id AND role_plots.role_id = roles.id";
        if (pDomain != null) {
            q += " AND plots.domain=" + pDomain.getId() + " ";
        }
        if (!pListDone) {
            q += " AND plots.done = false";
        }
        q += " ORDER BY plots.done, plot_id";
        connect();
        if(DEBUG) System.out.println("InfluenceDBImpl.getAssignedRolesForPlots(487) q: " + q);
        ResultSet rs = query(q);
        while(rs.next()) {
            int plId = rs.getInt(1);
            int rId = rs.getInt(2);
            String rName = rs.getString(3);
            Integer plotId = new Integer(plId);
            if(DEBUG) System.out.println("InfluenceDBImpl.getAssignedRolesForPlots(494) plotId: " + plotId + " role: " + rName);
            List<IntWithString> list = map.get(plotId);
            if(list == null) {
                list = new ArrayList<IntWithString>();
                map.put(plotId, list);
            }
            list.add(new IntWithString(rId, rName));
        }
        disconnect();
        return map;
    }

}
