/*
 * The MIT License
 *
 * Copyright (c) 2004,2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.db;

import net.joinedminds.masserr.model.*;

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
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface InfluenceDB extends BasicDB {

    Influence newInfluence();

    Influence saveInfluence(Influence influence);

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
