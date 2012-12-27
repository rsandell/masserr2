package se.tdt.bobby.wodcc.proxy.interfaces;

import net.joinedminds.masserr.model.*;
import se.tdt.bobby.wodcc.data.*;

import javax.swing.*;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:43:24
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface InfluenceDB extends BasicDB {
    void updateRoleProfessions(Role pRole, List pProfessions) throws SQLException, RemoteException;

    void removeResourcesFromRole(Role pRole, Resource[] pResources) throws SQLException, RemoteException;

    void addInfluenceToRole(Role pRole, Influence pInf) throws SQLException, RemoteException;

    void removeInfluencesFromRole(Role pRole, Influence[] pInfluences) throws SQLException, RemoteException;

    Vector<Plot> getPlotTitles() throws SQLException, RemoteException;

    Vector<Plot> getPlotTitles(Domain pDomain) throws SQLException, RemoteException;

    Plot getPlot(Plot pPlot) throws SQLException, RemoteException;

    void addPlot(Plot pPlot) throws SQLException, RemoteException;

    void updatePlot(Plot pSelectedPlot) throws SQLException, RemoteException;

    Vector<Plot> getAvailablePlotsForRole(Role pRole, Domain pDomain) throws SQLException, RemoteException;

    Vector<Plot> getAssignedPlotsForRole(Role pRole) throws SQLException, RemoteException;

    void assignPlots(Object[] pPlots, Role pRole) throws SQLException, RemoteException, ClassCastException;

    void assignPlots(int[] pPlotIds, Role pRole) throws SQLException, RemoteException;

    void unAssignPlots(Object[] pSelectedValues, Role pRole) throws SQLException, RemoteException, ClassCastException;

    void unAssignPlots(int[] pPlotIds, Role pRole) throws SQLException, RemoteException;

    List<Plot> getPlots(Domain pDomain, boolean pListDone) throws SQLException, RemoteException;

    HashMap<Integer,List<IntWithString>> getAssignedRolesForPlots(Domain pDomain, boolean pListDone) throws SQLException, RemoteException;
}
