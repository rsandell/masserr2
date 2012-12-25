package se.tdt.bobby.wodcc.ui.components.models;


import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.MainFrame;
import se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering.FilterEngine;
import se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering.Rules;

import javax.swing.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-18 20:04:06
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class RoleNamesListModel extends AbstractListModel {
    private RetrievalDB mDB;
    private Vector mVector;
    private Rules mFilterRules;
    private static final boolean DEBUG = false;
    //private boolean mFolowAppPreferences = false;

    public RoleNamesListModel() throws SQLException, RemoteException {
        mDB = Proxy.getRetrievalDB();
        mFilterRules = null;
        update();
    }

    public RoleNamesListModel(Rules pFilterRules) throws SQLException, RemoteException {
        mFilterRules = pFilterRules;
        mDB = Proxy.getRetrievalDB();
        update();
    }

    public void update() {
        try {
            if (mFilterRules == null) {
                mVector = mDB.getMinRoleRoleInfo();
                fireContentsChanged(this, 0, mVector.size());
            }
            else {
                mVector = new Vector();
                fireContentsChanged(this, 0, mVector.size());
                Vector allRoles = mDB.getMinRoleRoleInfo();
                for (int i = 0; i < allRoles.size(); i++) {
                    Role role = (Role) allRoles.get(i);
                    if (FilterEngine.check(role, mFilterRules)) {
                        mVector.add(role);
                        //fireIntervalAdded(this, mVector.size() - 1, mVector.size() - 1);
                    }
                }
                fireContentsChanged(this, 0, mVector.size());
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            MainFrame.sMainFrame.report(e);
        }
    }

    public void search(String pText, boolean pSearchName, boolean pSearchPlayerName, boolean pSearchEmbraced, boolean pSearchNature, boolean pSearchDemeanor, boolean pSearchPath, boolean pSearchDerangement) throws SQLException, RemoteException {
        mVector = mDB.search(pText, pSearchName, pSearchPlayerName, pSearchEmbraced, pSearchNature, pSearchDemeanor, pSearchPath, pSearchDerangement);
        fireContentsChanged(this, 0, mVector.size());
    }

    public int getSize() {
        if (mVector == null) {
            return 0;
        }
        else {
            return mVector.size();
        }
    }

    public Object getElementAt(int index) {
        if (index < 0 || index >= mVector.size()) {
            return null;
        }
        else {
            return mVector.get(index);
        }
    }
}
