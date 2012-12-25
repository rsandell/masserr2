package se.tdt.bobby.wodcc.proxy.interfaces;

import se.tdt.bobby.wodcc.data.*;

import javax.swing.*;
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
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
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

    Vector<MeritORflaw> getMerits() throws SQLException, RemoteException;

    Vector<MeritORflaw> getFlaws() throws SQLException, RemoteException;

    Role getRole(int pId) throws SQLException, ParseException, RemoteException;

    int getBaseMoneyForAge(int pAgeYears) throws SQLException, RemoteException;

    List<TreeNode> getFamelyTrees() throws SQLException, RemoteException;

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

    List<TreeNode> getFamelyTrees(Clan pClan) throws SQLException, RemoteException;

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
