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
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.text.ParseException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:43:55
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface RetrievalDB extends BasicDB {
    char ABILITY_TYPE_MENTAL = 'M';
    char ABILITY_TYPE_PHYSICAL = 'P';
    char ABILITY_TYPE_SOCIAL = 'S';

    Vector getAbilities() throws SQLException, RemoteException;

    List getAbilities(char pAbilityType) throws SQLException, RemoteException;

    Vector getGenerations() throws SQLException, RemoteException;

    Vector getClans() throws SQLException, RemoteException;

    Vector getRoleNames(int pGeneration, int pClan) throws SQLException, RemoteException;

    Vector getRoleNames(int pClan) throws SQLException, RemoteException;

    Vector getRoleNames() throws SQLException, RemoteException;

    Vector getMinRoleRoleInfo() throws SQLException, RemoteException;

    Vector getDisciplineNames() throws SQLException, RemoteException;

    Vector<IntWithString> getThaumaturgicalPathNames() throws SQLException, RemoteException;

    Vector<IntWithString> getNectromancyPathNames() throws SQLException, RemoteException;

    Vector<IntWithString> getOtherTraitNames() throws SQLException, RemoteException;

    Vector<Ritual> getRituals() throws SQLException, RemoteException;

    Discipline getDiscipline(int pNumber) throws SQLException, RemoteException;

    Vector<MeritOrFlaw> getMerits() throws SQLException, RemoteException;

    Vector<MeritOrFlaw> getFlaws() throws SQLException, RemoteException;

    Role getRole(int pId) throws SQLException, ParseException, RemoteException;

    int getBaseMoneyForAge(int pAgeYears) throws SQLException, RemoteException;

    //TODO Better implementation
    //List<TreeNode> getFamelyTrees() throws SQLException, RemoteException;

    List getGroups() throws SQLException, RemoteException;

    Vector getNonMortalProfessions() throws SQLException, RemoteException;

    Vector getMortalProfessions() throws SQLException, RemoteException;

    Vector getResources() throws SQLException, RemoteException;

    List getInfluencesForResource(Resource pResource) throws SQLException, RemoteException;

    List getInfluencesForResource(Resource pResource, boolean pConnect) throws SQLException, RemoteException;

    Vector getInfluenceNames() throws SQLException, RemoteException;

    Vector getDomains() throws SQLException, RemoteException;

    Vector<Resource> getResources(int pDomainId, String pType) throws SQLException, RemoteException;

    ArrayList<Vector<Resource>> getResourcesByDomain() throws SQLException, RemoteException;

    String[] getBackgroundAndWill(int pRoleId) throws SQLException, RemoteException;

    public Vector<RitualType> getRitualTypes() throws SQLException, RemoteException;

    Vector<Ritual> getRituals(RitualType pRitualType) throws SQLException, RemoteException;

    Vector<Discipline> getClanDisciplines(Clan pClan) throws SQLException, RemoteException;

    Vector<Discipline> getDisciplines() throws SQLException, RemoteException;

    List<ClanFontSymbol> getClanFontSymbols() throws SQLException, RemoteException;

    //TODO Better implementation
    //List<TreeNode> getFamelyTrees(Clan pClan) throws SQLException, RemoteException;

    List<Clan> getRepresentedClans() throws SQLException, RemoteException;

    Vector search(String pText, boolean pSearchName, boolean pSearchPlayerName, boolean pSearchEmbraced, boolean pSearchNature, boolean pSearchDemeanor, boolean pSearchPath, boolean pSearchDerangement) throws SQLException, RemoteException;

    Vector<String> getResourceTypes() throws SQLException, RemoteException;

    ArrayList<Vector<Resource>> getResourceByDomain(int pResourceId) throws SQLException, RemoteException;

    Resource getResource(int pResourceId) throws SQLException, RemoteException;

    Vector<FightOrFlight> getFightOrFlights() throws SQLException, RemoteException;

    HashMap<Integer,Integer> getOtherTraitsUsage() throws SQLException, RemoteException;

    int getNumberOfPlayedRoles() throws SQLException, RemoteException;

    HashMap<Integer, Integer> getMeritsNflawsUsage() throws SQLException, RemoteException;

    List<Experience> getDetailedExperienceListForRole(int pRoleId) throws SQLException, RemoteException;

    Vector<Player> getPlayingMembersList(Domain pDomain) throws SQLException, RemoteException;

    String getRoleName(int pRoleId) throws SQLException, RemoteException;

    Vector getPlayers() throws SQLException, RemoteException;

    Player getPlayer(int pPlayerId) throws SQLException, RemoteException;
}
