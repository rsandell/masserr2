package se.tdt.bobby.wodcc.proxy.interfaces;

import net.joinedminds.masserr.model.*;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:43:34
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
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
