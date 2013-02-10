/*
 * The MIT License
 *
 * Copyright (c) 2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr.db.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import net.joinedminds.masserr.db.InfluenceDB;
import net.joinedminds.masserr.model.*;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class InfluenceDbImpl implements InfluenceDB {

    private Provider<OObjectDatabaseTx> db;

    @Inject
    public InfluenceDbImpl(Provider<OObjectDatabaseTx> db) {
        this.db = db;
    }

    @Override
    public Influence newInfluence() {
        return db.get().newInstance(Influence.class);
    }

    @Override
    public Influence saveInfluence(Influence influence) {
        return db.get().save(influence);
    }

    @Override
    public void updateRoleProfessions(Role pRole, List pProfessions) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeResourcesFromRole(Role pRole, Resource[] pResources) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addInfluenceToRole(Role pRole, Influence pInf) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeInfluencesFromRole(Role pRole, Influence[] pInfluences) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vector<Plot> getPlotTitles() throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vector<Plot> getPlotTitles(Domain pDomain) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Plot getPlot(Plot pPlot) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addPlot(Plot pPlot) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updatePlot(Plot pSelectedPlot) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vector<Plot> getAvailablePlotsForRole(Role pRole, Domain pDomain) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vector<Plot> getAssignedPlotsForRole(Role pRole) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void assignPlots(Object[] pPlots, Role pRole) throws SQLException, RemoteException, ClassCastException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void assignPlots(int[] pPlotIds, Role pRole) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unAssignPlots(Object[] pSelectedValues, Role pRole) throws SQLException, RemoteException, ClassCastException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unAssignPlots(int[] pPlotIds, Role pRole) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Plot> getPlots(Domain pDomain, boolean pListDone) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HashMap<Integer, List<IntWithString>> getAssignedRolesForPlots(Domain pDomain, boolean pListDone) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
