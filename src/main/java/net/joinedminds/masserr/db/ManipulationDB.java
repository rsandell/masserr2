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

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:43:34
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface ManipulationDB extends BasicDB {
    void updateRole(Role pRole) throws SQLException, RemoteException;

    void addRitual(Ritual pRitual) throws SQLException, RemoteException;

    void updateRitual(Ritual pRitual) throws SQLException, RemoteException;

    int addRole(Role pRole) throws SQLException, RemoteException;

    void addResource(Resource pResource) throws SQLException, RemoteException;

    void updateResource(Resource pResource) throws SQLException, RemoteException;

    void insertExperience(Role pRole, int pAmmount, String pReason) throws SQLException, RemoteException;

    void insertExperience(List<RolesGroup> pGroupsList, List<Role> pRolesList, int pAmmount, String pReason) throws SQLException, RemoteException;

    void updateBackgroundAndWill(Role pRole, String pBackground, String pWill) throws SQLException, RemoteException;

    void addClanDisciplines(Clan pClan, Discipline[] pDisciplines) throws SQLException, RemoteException;

    void removeClanDisciplines(Clan pClan, Discipline[] pDisciplines) throws SQLException, RemoteException;

    void updateDiscipline(Discipline pDiscipline) throws SQLException, RemoteException;

    void addDiscipline(Discipline pDiscipline) throws SQLException, RemoteException;

    void updateThaumaturgicalPath(IntWithString pPath) throws SQLException, RemoteException;

    IntWithString addThaumaturgicalPath(String pName) throws SQLException, RemoteException;

    void updateNecromancyPath(IntWithString pPath) throws SQLException, RemoteException;

    IntWithString addNecromancyPath(String pName) throws SQLException, RemoteException;

    void updateMeritORflaw(MeritOrFlaw pToUpdate) throws SQLException, RemoteException;

    MeritOrFlaw addMeritORflaw(String pName) throws SQLException, RemoteException;

    void updateOtherTrait(IntWithString pToUpdate) throws SQLException, RemoteException;

    IntWithString addOtherTrait(String pName) throws SQLException, RemoteException;

    void updatePlayer(Player pPlayer) throws SQLException, RemoteException;

    void addPlayer(Player pPlayer) throws SQLException, RemoteException;

    void insertPlayersExperience(List<Player> pPlayers, int pAmmount, String pReason) throws SQLException, RemoteException;
}
