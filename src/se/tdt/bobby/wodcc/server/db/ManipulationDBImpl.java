package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;
import se.tdt.bobby.wodcc.data.mgm.UserRights;
import se.tdt.bobby.wodcc.remote.db.RemoteManipulationDB;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-16 13:21:56
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class ManipulationDBImpl extends BasicDBImpl implements RemoteManipulationDB {

    public ManipulationDBImpl() throws RemoteException {
        super();
    }

    private DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);

    private static final String INSERT_ROLE = "INSERT INTO roles (" +
            "name, generation, sire, embraced, clan, nature, demeanor, " +
            "courage, concience, [self-control], willpower, path_dots, " +
            "path, physical, social, mental, extra_healthlevels, sufferes_of_injury, " +
            "player_name, goul, vitals, domain, concienceORconviction, selfcontrolORinstinct, " +
            "thauma_type, necromancy_type, quote, slp, fightForm, flightForm, player_id" +
            ") " +
            "VALUES ('{0}', {1}, {2}, '{3}',{4}, '{5}', '{6}', " +
            "{7}, {8}, {9}, {10}, {11}, " +
            "'{12}', {13}, {14}, {15}, {16}, {17}, " +
            "'{18}', {19}, {20}, {21}, '{22}', '{23}', " +
            "'{24}', '{25}', '{26}', {27}, {28}, {29}, {30})";
    private static final String INSERT_ROLE_ABILITY = "INSERT INTO roleAbilities (role_id, ability_id, dots, specialisation) " +
            "VALUES({0}, {1}, {2}, '{3}')";
    private static final String INSERT_ROLE_DISCIPLINE = "INSERT INTO roleDisciplines(role_id, discipline_id, dots, isOfClan) " +
            "VALUES({0}, {1}, {2}, {3})";
    private static final String INSERT_ROLE_THAUMA_PATH = "INSERT INTO roleThamaPaths(role_id, path_id, dots) " +
            "VALUES({0}, {1}, {2})";
    private static final String INSERT_ROLE_NECTROMANCY_PATH = "INSERT INTO roleNecromancyPaths(role_id, path_id, dots) " +
            "VALUES({0}, {1}, {2})";
    //private static final String INSERT_ROLE_THAUMA_RITUAL = "INSERT INTO roleThamaRituals(role_id, ritual_id) " +
    //        "VALUES({0}, {1})";
    private static final String INSERT_ROLE_RITUAL = "INSERT INTO role_rituals(role_id, ritual_id) " +
            "VALUES({0}, {1})";
    //private static final String INSERT_ROLE_NECROMANTIC_RITUAL = "INSERT INTO roleNecromanticRituals(role_id, ritual_id) " +
    //        "VALUES({0}, {1})";
    private static final String INSERT_ROLE_OTHER_TRAIT = "INSERT INTO roleOtherTraits(role_id, trait_id, dots) " +
            "VALUES({0}, {1}, {2})";
    private static final String INSERT_ROLE_DERANGEMENT = "INSERT INTO roleDerangements(role_id, derangement, isBeastTrait) " +
            "VALUES({0}, '{1}', false)";
    private static final String INSERT_ROLE_BEAST_TRAIT = "INSERT INTO roleDerangements(role_id, derangement, isBeastTrait) " +
            "VALUES({0}, '{1}', true)";
    private static final String INSERT_ROLE_MERIT_OR_FLAW = "INSERT INTO roleMeritsNflaws(role_id, mf_id, [note]) " +
            "VALUES({0}, {1}, '{2}')";
    //private static final String INSERT_THAUMA_RITUAL = "INSERT INTO thama_rituals (name, [level], description) " +
    //        "VALUES('{0}', {1}, '{2}')";
    //private static final String INSERT_NECROMANTIC_RITUAL = "INSERT INTO necromantic_rituals (name, [level], description) " +
    //        "VALUES('{0}', {1}, '{2}')";
    //private static final String UPDATE_THAUMA_RITUAL = "UPDATE thama_rituals SET name='{0}', " +
    //        "[level]={1}, description='{2}' WHERE id={3}";
    //private static final String UPDATE_NECROMANTIC_RITUAL = "UPDATE necromantic_rituals SET name='{0}', " +
    //        "[level]={1}, description='{2}' WHERE id={3}";
    private static final String UPDATE_ROLE = "UPDATE roles SET " +
            "name = '{0}', generation = {1}, sire = {2}, embraced = '{3}', clan = {4}, nature = '{5}', " +
            "demeanor = '{6}', courage = {7}, concience = {8}, [self-control] = {9}, willpower = {10}, " +
            "path_dots = {11}, path = '{12}', physical = {13}, social = {14}, mental = {15}, " +
            "extra_healthlevels = {16}, sufferes_of_injury = {17}, player_name = '{18}', goul = {19}, " +
            "vitals = {20}, domain = {21}, concienceORconviction = '{22}', selfcontrolORinstinct = '{23}', " +
            "thauma_type = '{24}', necromancy_type = '{25}', quote='{26}', slp={27}, " +
            "fightForm = {28}, flightForm = {29}, player_id = {30}" +
            " WHERE id = {31}";
    private static final String DELETE_ROLE_ABILITIES = "DELETE FROM roleAbilities WHERE role_id =";
    private static final String DELETE_ROLE_DISCIPLINES = "DELETE FROM roleDisciplines WHERE role_id =";
    private static final String DELETE_ROLE_THAUMA_PATHS = "DELETE FROM roleThamaPaths WHERE role_id =";
    //private static final String DELETE_ROLE_THAUMA_RITUALS = "DELETE FROM roleThamaRituals WHERE role_id =";
    private static final String DELETE_ROLE_RITUALS = "DELETE FROM role_rituals WHERE role_id =";
    private static final String DELETE_ROLE_NECROMANCY_PATHS = "DELETE FROM roleNecromancyPaths WHERE role_id =";
    //private static final String DELETE_ROLE_NECROMANTIC_RITUALS = "DELETE FROM roleNecromanticRituals WHERE role_id =";
    private static final String DELETE_ROLE_OTHER_TRAITS = "DELETE FROM roleOtherTraits WHERE role_id =";
    private static final String DELETE_ROLE_DERANGEMENTS = "DELETE FROM roleDerangements WHERE role_id =";
    private static final String DELETE_ROLE_MERITS_N_FLAWS = "DELETE FROM roleMeritsNflaws WHERE role_id =";

    public void updateRole(Role pRole) throws SQLException {
        Domain currentDomain = getCurrentDomainForRole(pRole);
        if (mLoginUser.getDomain().equals(currentDomain)) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_ROLE_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to update roles in Domain " + currentDomain.toString());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_ROLE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to update roles in Domain " + currentDomain.toString());
            }
            else if (!mLoginUser.hasDomain(currentDomain)) {
                throw new OperationDeniedException("You do not have priveleges to update roles in Domain " + currentDomain.toString());
            }
        }
        if (currentDomain.getId() != pRole.getDomain().getId()) {
            if (mLoginUser.getUserRights().get(UserRights.UPDATE_ROLE_OTHER_DOMAIN)) {
                if (!mLoginUser.hasDomain(pRole.getDomain())) {
                    throw new OperationDeniedException("You do not have priveleges to move roles to Domain " + pRole.getDomain());
                }
            }
            else {
                throw new OperationDeniedException("You do not have priveleges to move roles to Domain " + pRole.getDomain());
            }
        }
        connect();
        ArrayList list = new ArrayList(20);
        if (DEBUG) System.out.println("[ManipulationDB][addRole][56] adding role parameters");
        list.add(pRole.getName());
        list.add(pRole.getGeneration().getGeneration() + "");
        if (pRole.getSire().getNumber() < 0) {
            list.add("NULL");
        }
        else {
            list.add(pRole.getSire().getNumber() + "");
        }
        list.add(format.format(pRole.getEmbraced()));
        list.add(pRole.getClan().getId() + "");
        list.add(pRole.getNature());
        list.add(pRole.getDemeanor());
        list.add(pRole.getCourage() + "");
        list.add(pRole.getConcience() + "");
        list.add(pRole.getSelfControl() + "");
        list.add(pRole.getWillpower() + "");
        list.add(pRole.getPathDots() + "");
        list.add(pRole.getPath());
        list.add(pRole.getPhysical() + "");
        list.add(pRole.getSocial() + "");
        list.add(pRole.getMental() + "");
        list.add(pRole.getExtraHealthLevels() + "");
        list.add(pRole.isSufferesOfInjury() + "");
        list.add(pRole.getPlayerName());
        list.add(pRole.isGhoul() + "");
        list.add(pRole.getVitalsId() + "");
        list.add(pRole.getDomain().getId() + "");
        list.add(pRole.getConcienseORconviction());
        list.add(pRole.getSelfControlORinstinct());
        list.add(pRole.getThaumaType());
        list.add(pRole.getNecromancyType());
        list.add(pRole.getQuote());
        list.add(pRole.isSLP() + "");
        if (pRole.getFightForm() != null) {
            list.add(pRole.getFightForm().getId() + "");
        }
        else {
            list.add("NULL");
        }
        if (pRole.getFlightForm() != null) {
            list.add(pRole.getFlightForm().getId() + "");
        }
        else {
            list.add("NULL");
        }
        if (pRole.getPlayer() != null) {
            list.add(pRole.getPlayer().getId() + "");
        }
        else {
            list.add("NULL");
        }
        list.add(pRole.getId() + "");
        String query = replaceInString(UPDATE_ROLE, list);
        int result = update(query);
        if (DEBUG) System.out.println("ManipulationDBImpl.updateRole(135) result: " + result);
        int updateRoleId = pRole.getId();
        list = new ArrayList(4);
        mStatement.addBatch(DELETE_ROLE_ABILITIES + updateRoleId);
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][99] physical abilities");
        List abilities = pRole.getPhysicalAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = (Ability) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(ability.getId() + "");
            list.add(ability.getDots() + "");
            list.add(ability.getSpecialisation());
            mStatement.addBatch(replaceInString(INSERT_ROLE_ABILITY, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][111] social abilities");
        abilities = pRole.getSocialAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = (Ability) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(ability.getId() + "");
            list.add(ability.getDots() + "");
            list.add(ability.getSpecialisation());
            mStatement.addBatch(replaceInString(INSERT_ROLE_ABILITY, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][122] mental abilities");
        abilities = pRole.getMentalAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = (Ability) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(ability.getId() + "");
            list.add(ability.getDots() + "");
            list.add(ability.getSpecialisation());
            mStatement.addBatch(replaceInString(INSERT_ROLE_ABILITY, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][134] disciplines");
        mStatement.addBatch(DELETE_ROLE_DISCIPLINES + updateRoleId);
        List disciplines = pRole.getDisciplines();
        for (int i = 0; i < disciplines.size(); i++) {
            Discipline discipline = (Discipline) disciplines.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(discipline.getId() + "");
            list.add(discipline.getDots() + "");
            list.add(discipline.isOfClan() + "");
            mStatement.addBatch(replaceInString(INSERT_ROLE_DISCIPLINE, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][150] deleting thauma");
        mStatement.addBatch(DELETE_ROLE_THAUMA_PATHS + updateRoleId);
        if (pRole.hasThaumaturgy()) {
            if (DEBUG) System.out.println("[ManipulationDB][updateRole][151] thauma paths");
            List paths = pRole.getThaumaturgicalPaths();
            for (int i = 0; i < paths.size(); i++) {
                Path path = (Path) paths.get(i);
                list.clear();
                list.add(updateRoleId + "");
                list.add(path.getId() + "");
                list.add(path.getDots() + "");
                mStatement.addBatch(replaceInString(INSERT_ROLE_THAUMA_PATH, list));
            }
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][174] deleting necromancy");
        mStatement.addBatch(DELETE_ROLE_NECROMANCY_PATHS + updateRoleId);
        if (pRole.hasNecromancy()) {
            if (DEBUG) System.out.println("[ManipulationDB][updateRole][178] necromancy paths");
            List paths = pRole.getNecromancyPaths();
            for (int i = 0; i < paths.size(); i++) {
                Path path = (Path) paths.get(i);
                list.clear();
                list.add(updateRoleId + "");
                list.add(path.getId() + "");
                list.add(path.getDots() + "");
                mStatement.addBatch(replaceInString(INSERT_ROLE_NECTROMANCY_PATH, list));
            }
        }
        mStatement.addBatch(DELETE_ROLE_RITUALS + updateRoleId);
        if (pRole.hasRituals()) {
            List<Ritual> rituals = pRole.getRituals();
            for (int i = 0; i < rituals.size(); i++) {
                Ritual ritual = rituals.get(i);
                list.clear();
                list.add(updateRoleId + "");
                list.add(ritual.getId() + "");
                mStatement.addBatch(replaceInString(INSERT_ROLE_RITUAL, list));
            }
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][200] other traits");
        mStatement.addBatch(DELETE_ROLE_OTHER_TRAITS + updateRoleId);
        abilities = pRole.getOtherTraits();
        for (int i = 0; i < abilities.size(); i++) {
            OtherTrait otherTrait = (OtherTrait) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(otherTrait.getId() + "");
            list.add(otherTrait.getDots() + "");
            mStatement.addBatch(replaceInString(INSERT_ROLE_OTHER_TRAIT, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][210] derangements");
        mStatement.addBatch(DELETE_ROLE_DERANGEMENTS + updateRoleId);
        abilities = pRole.getDerangements();
        for (int i = 0; i < abilities.size(); i++) {
            String s = (String) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(s);
            mStatement.addBatch(replaceInString(INSERT_ROLE_DERANGEMENT, list));
        }
        abilities = pRole.getBeastTraits();
        for (int i = 0; i < abilities.size(); i++) {
            String s = (String) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(s);
            mStatement.addBatch(replaceInString(INSERT_ROLE_BEAST_TRAIT, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][222] deleting merits n flaws");
        mStatement.addBatch(DELETE_ROLE_MERITS_N_FLAWS + updateRoleId);
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][224] merits");
        List mORfs = pRole.getMerits();
        for (int i = 0; i < mORfs.size(); i++) {
            MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(meritORflaw.getId() + "");
            list.add(meritORflaw.getNote());
            mStatement.addBatch(replaceInString(INSERT_ROLE_MERIT_OR_FLAW, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][updateRole][234] flaws");
        mORfs = pRole.getFlaws();
        for (int i = 0; i < mORfs.size(); i++) {
            MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(meritORflaw.getId() + "");
            list.add(meritORflaw.getNote());
            mStatement.addBatch(replaceInString(INSERT_ROLE_MERIT_OR_FLAW, list));
        }
        if (DEBUG) System.out.println("[ManipulationDB][addRole][227] executing batch");
        mStatement.executeBatch();
    }

    private Domain getCurrentDomainForRole(Role pRole) throws SQLException {
        Domain domain = null;
        connect();
        ResultSet rs = query("SELECT domains.id, domains.name " +
                             "FROM roles, domains " +
                             "WHERE roles.domain = domains.id AND roles.id = " + pRole.getId());
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            domain = new Domain(id, name);
        }
        disconnect();
        return domain;
    }

    private static final String INSERT_RITUAL = "INSERT INTO rituals (name, [level], description, type) " +
            "VALUES('{0}', {1}, '{2}', {3})";

    public void addRitual(Ritual pRitual) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_RITUAL)) {
            throw new OperationDeniedException("You do not have priveleges to add rituals");
        }
        connect();
        ArrayList params = new ArrayList(3);
        params.add(pRitual.getName());
        params.add(pRitual.getLevel() + "");
        params.add(pRitual.getDescription());
        params.add(pRitual.getRitualType().getId() + "");
        String query = replaceInString(INSERT_RITUAL, params);
        update(query);
        disconnect();
    }

    private static final String UPDATE_RITUAL = "UPDATE rituals SET name = '{0}', [level] = {1}, " +
            "description = '{2}', type = {3} WHERE id = {4}";

    public void updateRitual(Ritual pRitual) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.UPDATE_RITUAL)) {
            throw new OperationDeniedException("You do not have priveleges to update rituals");
        }
        connect();
        ArrayList params = new ArrayList(3);
        params.add(pRitual.getName());
        params.add(pRitual.getLevel() + "");
        params.add(pRitual.getDescription());
        params.add(pRitual.getRitualType().getId() + "");
        params.add(pRitual.getId() + "");
        String query = replaceInString(UPDATE_RITUAL, params);
        update(query);
        disconnect();
    }

    /*public void addNecromanticRitual(Ritual pRitual) throws SQLException {
        connect();
        ArrayList params = new ArrayList(3);
        params.add(pRitual.getName());
        params.add(pRitual.getLevel() + "");
        params.add(pRitual.getDescription());
        String query = replaceInString(INSERT_NECROMANTIC_RITUAL, params);
        update(query);
        disconnect();
    }

    public void updateNecromanticRitual(Ritual pRitual) throws SQLException {
        connect();
        ArrayList params = new ArrayList(3);
        params.add(pRitual.getName());
        params.add(pRitual.getLevel() + "");
        params.add(pRitual.getDescription());
        params.add(pRitual.getId() + "");
        String query = replaceInString(UPDATE_NECROMANTIC_RITUAL, params);
        update(query);
        disconnect();
    }*/

    public int addRole(Role pRole) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_ROLE_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to add roles to Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_ROLE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to add roles to Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have priveleges to add roles to Domain " + pRole.getDomain());
            }
        }
        connect();
        int addedRoleId = -1;
        //IntWithString[] values = new IntWithString[19];
        ArrayList list = new ArrayList(19);
        if (DEBUG) System.out.println("[ManipulationDB][addRole][56] adding role parameters");
        list.add(pRole.getName());
        list.add(pRole.getGeneration().getGeneration() + "");
        if (pRole.getSire() == null) {
            list.add("NULL");
        }
        else if (pRole.getSire().getNumber() < 0) {
            list.add("NULL");
        }
        else {
            list.add(pRole.getSire().getNumber() + "");
        }
        list.add(format.format(pRole.getEmbraced()));
        list.add(pRole.getClan().getId() + "");
        list.add(pRole.getNature());
        list.add(pRole.getDemeanor());
        list.add(pRole.getCourage() + "");
        list.add(pRole.getConcience() + "");
        list.add(pRole.getSelfControl() + "");
        list.add(pRole.getWillpower() + "");
        list.add(pRole.getPathDots() + "");
        list.add(pRole.getPath());
        list.add(pRole.getPhysical() + "");
        list.add(pRole.getSocial() + "");
        list.add(pRole.getMental() + "");
        list.add(pRole.getExtraHealthLevels() + "");
        list.add(pRole.isSufferesOfInjury() + "");
        list.add(pRole.getPlayerName());
        list.add(pRole.isGhoul() + "");
        list.add(pRole.getVitalsId() + "");
        list.add(pRole.getDomain().getId() + "");
        list.add(pRole.getConcienseORconviction());
        list.add(pRole.getSelfControlORinstinct());
        list.add(pRole.getThaumaType());
        list.add(pRole.getNecromancyType());
        list.add(pRole.getQuote());
        list.add(pRole.isSLP() + "");
        if (pRole.getFightForm() != null) {
            list.add(pRole.getFightForm().getId() + "");
        }
        else {
            list.add("NULL");
        }
        if (pRole.getFlightForm() != null) {
            list.add(pRole.getFlightForm().getId() + "");
        }
        else {
            list.add("NULL");
        }
        if (pRole.getPlayer() != null) {
            list.add(pRole.getPlayer().getId() + "");
        }
        else {
            list.add("NULL");
        }
        String query = replaceInString(INSERT_ROLE, list);
        if (DEBUG) System.out.println("[ManipulationDB][addRole][39] query: \n" + query);
        int result = update(query);
        if (DEBUG) System.out.println("[ManipulationDB][addRole][84] role inserted");
        /*ResultSet rs = mStatement.getGeneratedKeys();
        while (rs.next()) {
            if (DEBUG) System.out.println("[ManipulationDB][addRole][64] rs keys: " + rs.getString(1));
        }
        rs.close();*/
        if (result > 0) {
            //FIND THE INSWERTED ID
            ResultSet rd = query("SELECT MAX(id) FROM roles");
            if (rd.next()) {
                addedRoleId = rd.getInt(1);
            }
            rd.close();
        }
        if (DEBUG) System.out.println("[ManipulationDB][addRole][70] addedRoleId MAX? " + addedRoleId);

        if (addedRoleId >= 0) {
            list = new ArrayList(4);
            if (DEBUG) System.out.println("[ManipulationDB][addRole][102] physical abilities");
            List abilities = pRole.getPhysicalAbilities();
            for (int i = 0; i < abilities.size(); i++) {
                Ability ability = (Ability) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(ability.getId() + "");
                list.add(ability.getDots() + "");
                list.add(ability.getSpecialisation());
                mStatement.addBatch(replaceInString(INSERT_ROLE_ABILITY, list));
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][113] social abilities");
            abilities = pRole.getSocialAbilities();
            for (int i = 0; i < abilities.size(); i++) {
                Ability ability = (Ability) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(ability.getId() + "");
                list.add(ability.getDots() + "");
                list.add(ability.getSpecialisation());
                mStatement.addBatch(replaceInString(INSERT_ROLE_ABILITY, list));
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][124] mental abilities");
            abilities = pRole.getMentalAbilities();
            for (int i = 0; i < abilities.size(); i++) {
                Ability ability = (Ability) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(ability.getId() + "");
                list.add(ability.getDots() + "");
                list.add(ability.getSpecialisation());
                mStatement.addBatch(replaceInString(INSERT_ROLE_ABILITY, list));
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][135] disciplines");
            List disciplines = pRole.getDisciplines();
            for (int i = 0; i < disciplines.size(); i++) {
                Discipline discipline = (Discipline) disciplines.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(discipline.getId() + "");
                list.add(discipline.getDots() + "");
                list.add(discipline.isOfClan() + "");
                mStatement.addBatch(replaceInString(INSERT_ROLE_DISCIPLINE, list));
            }
            if (pRole.hasThaumaturgy()) {
                if (DEBUG) System.out.println("[ManipulationDB][addRole][147] thauma paths");
                List paths = pRole.getThaumaturgicalPaths();
                for (int i = 0; i < paths.size(); i++) {
                    Path path = (Path) paths.get(i);
                    list.clear();
                    list.add(addedRoleId + "");
                    list.add(path.getId() + "");
                    list.add(path.getDots() + "");
                    mStatement.addBatch(replaceInString(INSERT_ROLE_THAUMA_PATH, list));
                }
            }
            if (pRole.hasNecromancy()) {
                if (DEBUG) System.out.println("[ManipulationDB][addRole][168] necromancy paths");
                List paths = pRole.getNecromancyPaths();
                for (int i = 0; i < paths.size(); i++) {
                    Path path = (Path) paths.get(i);
                    list.clear();
                    list.add(addedRoleId + "");
                    list.add(path.getId() + "");
                    list.add(path.getDots() + "");
                    mStatement.addBatch(replaceInString(INSERT_ROLE_NECTROMANCY_PATH, list));
                }
            }
            if (pRole.hasRituals()) {
                List<Ritual> rituals = pRole.getRituals();
                for (int i = 0; i < rituals.size(); i++) {
                    Ritual ritual = rituals.get(i);
                    list.clear();
                    list.add(addedRoleId + "");
                    list.add(ritual.getId() + "");
                    mStatement.addBatch(replaceInString(INSERT_ROLE_RITUAL, list));
                }
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][188] other traits");
            abilities = pRole.getOtherTraits();
            for (int i = 0; i < abilities.size(); i++) {
                OtherTrait otherTrait = (OtherTrait) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(otherTrait.getId() + "");
                list.add(otherTrait.getDots() + "");
                mStatement.addBatch(replaceInString(INSERT_ROLE_OTHER_TRAIT, list));
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][198] derangements");
            abilities = pRole.getDerangements();
            for (int i = 0; i < abilities.size(); i++) {
                String s = (String) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(s);
                mStatement.addBatch(replaceInString(INSERT_ROLE_DERANGEMENT, list));
            }
            abilities = pRole.getBeastTraits();
            for (int i = 0; i < abilities.size(); i++) {
                String s = (String) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(s);
                mStatement.addBatch(replaceInString(INSERT_ROLE_BEAST_TRAIT, list));
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][207] merits");
            List mORfs = pRole.getMerits();
            for (int i = 0; i < mORfs.size(); i++) {
                MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(meritORflaw.getId() + "");
                list.add(meritORflaw.getNote());
                mStatement.addBatch(replaceInString(INSERT_ROLE_MERIT_OR_FLAW, list));
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][217] flaws");
            mORfs = pRole.getFlaws();
            for (int i = 0; i < mORfs.size(); i++) {
                MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(meritORflaw.getId() + "");
                list.add(meritORflaw.getNote());
                mStatement.addBatch(replaceInString(INSERT_ROLE_MERIT_OR_FLAW, list));
            }
            if (DEBUG) System.out.println("[ManipulationDB][addRole][227] executing batch");
            mStatement.executeBatch();
        }
        if (DEBUG) System.out.println("[ManipulationDB][addRole][230] disconnecting");
        disconnect();
        return addedRoleId;
    }

    /*private String replaceInString(String pString, IntWithString[] pValues) {
        String cp = pString;
        for (int i = 0; i < pValues.length; i++) {
            IntWithString value = pValues[i];
            cp = cp.replaceAll("\\{" + value.getNumber() + "\\}", value.getString());
        }
        return cp;
    }*/



    private static final String INSERT_RESOURCE = "INSERT INTO resources (name, description, income, cost, domain, type) " +
            "VALUES ('{0}', '{1}', {2}, {3}, {4}, '{5}')";
    private static final String INSERT_RESOURCE_INFLUENCE = "INSERT INTO resourceInfluences (resource_id, influence_id, dots) " +
            "VALUES ({0}, {1}, {2})";
    private static final String UPDATE_RESOURCE = "UPDATE resources SET name = '{0}', " +
            "description = '{1}', income = {2}, cost = {3}, domain = {4}, type = '{5}' " +
            "WHERE id = {6}";
    private static final String DELETE_RESOURCE_INFLUENCES = "DELETE FROM resourceInfluences " +
            "WHERE resource_id = {0}";

    public void addResource(Resource pResource) throws SQLException {
        if (mLoginUser.getDomain().equals(pResource.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_RESOURCE)) {
                throw new OperationDeniedException("You do not have priveleges to add resources to Domain " + pResource.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_RESOURCE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to add resources to Domain " + pResource.getDomain());
            }
            else if (!mLoginUser.hasDomain(pResource.getDomain())) {
                throw new OperationDeniedException("You do not have priveleges to add resources to Domain " + pResource.getDomain());
            }
        }
        connect();
        ArrayList li = new ArrayList(4);
        li.add(pResource.getName());
        li.add(pResource.getDescription());
        li.add(pResource.getIncome() + "");
        li.add(pResource.getCost() + "");
        li.add(pResource.getDomain().getId() + "");
        li.add(pResource.getType());
        int result = update(replaceInString(INSERT_RESOURCE, li));
        if (result > 0) {
            int id = -1;
            ResultSet rs = query("SELECT MAX(id) FROM resources");
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            if (id >= 0) {
                List influences = pResource.getInfluences();
                for (int i = 0; i < influences.size(); i++) {
                    Influence influence = (Influence) influences.get(i);
                    li.clear();
                    li.add(id + "");
                    li.add(influence.getId() + "");
                    li.add(influence.getDots() + "");
                    mStatement.addBatch(replaceInString(INSERT_RESOURCE_INFLUENCE, li));
                }
                mStatement.executeBatch();
            }
        }
        disconnect();
    }

    public void updateResource(Resource pResource) throws SQLException {
        Domain currentDomain = getCurrentDomainForResource(pResource);
        if (mLoginUser.getDomain().equals(currentDomain)) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_RESOURCE)) {
                throw new OperationDeniedException("You do not have priveleges to update resources in Domain " + currentDomain);
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_RESOURCE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to update resources in Domain " + currentDomain);
            }
            else if (!mLoginUser.hasDomain(currentDomain)) {
                throw new OperationDeniedException("You do not have priveleges to update resources in Domain " + currentDomain);
            }
        }
        if (currentDomain.getId() != pResource.getDomain().getId()) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_RESOURCE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to move resources to Domain " + pResource.getDomain());
            }
            else if (!mLoginUser.hasDomain(pResource.getDomain())) {
                throw new OperationDeniedException("You do not have priveleges to move resources to Domain " + pResource.getDomain());
            }
        }
        connect();
        ArrayList li = new ArrayList(4);
        li.add(pResource.getName());
        li.add(pResource.getDescription());
        li.add(pResource.getIncome() + "");
        li.add(pResource.getCost() + "");
        li.add(pResource.getDomain().getId() + "");
        li.add(pResource.getType());
        li.add(pResource.getId() + "");
        mStatement.addBatch(replaceInString(UPDATE_RESOURCE, li));
        li.clear();
        li.add(pResource.getId() + "");
        mStatement.addBatch(replaceInString(DELETE_RESOURCE_INFLUENCES, li));
        List influences = pResource.getInfluences();
        for (int i = 0; i < influences.size(); i++) {
            Influence influence = (Influence) influences.get(i);
            li.clear();
            li.add(pResource.getId() + "");
            li.add(influence.getId() + "");
            li.add(influence.getDots() + "");
            mStatement.addBatch(replaceInString(INSERT_RESOURCE_INFLUENCE, li));
        }
        mStatement.executeBatch();
        disconnect();
    }

    private Domain getCurrentDomainForResource(Resource pResource) throws SQLException {
        Domain domain = null;
        connect();
        ResultSet rs = query("SELECT domains.id, domains.name " +
                             "FROM resources, domains " +
                             "WHERE resources.domain = domains.id AND resources.id = " + pResource.getId());
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            domain = new Domain(id, name);
        }
        disconnect();
        return domain;
    }

    public void insertExperience(Role pRole, int pAmmount, String pReason) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.INSERT_EXPERIENCE_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to update experience for roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.INSERT_EXPERIENCE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to update experience for roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have priveleges to update experience for roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        String q = "INSERT INTO experience(role_id, ammount, reason, user_id, atTimeUserFullName) VALUES(" +
                pRole.getId() + ", " + pAmmount + ", '" + quoteString(pReason) + "', " + mLoginUser.getId() + ", '" + quoteString(mLoginUser.getFullName()) + "')";
        int rows = update(q);
        if (DEBUG) System.out.println("[ManipulationDB][insertExperience][564] rows: " + rows);
        disconnect();
    }

    public static final String INSERT_GROUP_EXPERIENCE =
            "INSERT INTO experience\n" +
            "                      (role_id, ammount, reason, user_id, atTimeUserFullName)\n" +
            "SELECT     role_id, {0} AS Expr1, '{1}' AS Expr2, {2} AS Expr3, '{3}' AS Expr4\n" +
            "FROM         groupRoles\n" +
            "WHERE     (group_id = {4})";
    public static final String INSERT_EXPERIENCE =
            "INSERT INTO experience (ammount, role_id, reason, user_id, atTimeUserFullName)\n" +
            "VALUES ({0}, {1}, '{2}', {3}, '{4}')";

    private void insertGroupExperience(int pGroupId, int pAmmount, String pReason) throws SQLException {
        ArrayList params = new ArrayList();
        params.add(pAmmount + "");
        params.add(pReason);
        params.add(String.valueOf(mLoginUser.getId()));
        params.add(mLoginUser.getFullName());
        params.add(pGroupId + "");
        update(replaceInString(INSERT_GROUP_EXPERIENCE, params));
        params = new ArrayList();
        ArrayList<Integer> children = new ArrayList<Integer>();
        ResultSet rs = query("SELECT id FROM groups WHERE parent = " + pGroupId);
        while (rs.next()) {
            children.add(new Integer(rs.getInt(1)));
        }
        rs.close();
        for (int i = 0; i < children.size(); i++) {
            Integer integer = children.get(i);
            insertGroupExperience(integer.intValue(), pAmmount, pReason);
        }
    }

    public void insertExperience(List<RolesGroup> pGroupsList, List<Role> pRolesList, int pAmmount, String pReason) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.INSERT_GROUP_EXPERIENCE)) {
            throw new OperationDeniedException("You do not have priveleges to update experience for an entire group!");
        }
        if (pGroupsList.size() > 0 || pRolesList.size() > 0) {
            connect();
            for (int i = 0; i < pGroupsList.size(); i++) {
                RolesGroup rolesGroup = pGroupsList.get(i);
                insertGroupExperience(rolesGroup.getId(), pAmmount, pReason);
            }
            disconnect();
            connect();
            ArrayList params = new ArrayList();
            for (int i = 0; i < pRolesList.size(); i++) {
                Role role = pRolesList.get(i);
                params.add(pAmmount + "");
                params.add(role.getId() + "");
                params.add(pReason);
                params.add(String.valueOf(mLoginUser.getId()));
                params.add(mLoginUser.getFullName());
                update(replaceInString(INSERT_EXPERIENCE, params));
                params = new ArrayList();
            }
            disconnect();
        }
    }

    public void updateBackgroundAndWill(Role pRole, String pBackground, String pWill) throws SQLException, RemoteException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_BACKGROUND_AND_WILL_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to update background and will for roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_BACKGROUND_AND_WILL_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have priveleges to update background and will for roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have priveleges to update background and will for roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        int rows = update("UPDATE roles SET background = '" + quoteString(pBackground) + "', will = '" + quoteString(pWill) + "' " +
                          "WHERE id = " + pRole.getId());
        if (DEBUG) System.out.println("ManipulationDBImpl.updateBackgroundAndWill(634) rows: " + rows);
        disconnect();
    }

    public void addClanDisciplines(Clan pClan, Discipline[] pDisciplines) throws SQLException, RemoteException {
        if (pDisciplines.length > 0) {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_REMOVE_CLAN_DISCIPLINES)) {
                throw new OperationDeniedException("You do not have priveleges to add clan disciplines");
            }
            connect();
            for (int i = 0; i < pDisciplines.length; i++) {
                Discipline discipline = pDisciplines[i];
                mStatement.addBatch("INSERT INTO clan_disciplines(clan_id, discipline_id) VALUES(" + pClan.getId() + "," + discipline.getId() + ")");
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    public void removeClanDisciplines(Clan pClan, Discipline[] pDisciplines) throws SQLException, RemoteException {
        if (pDisciplines.length > 0) {
            if (!mLoginUser.getUserRights().get(UserRights.ADD_REMOVE_CLAN_DISCIPLINES)) {
                throw new OperationDeniedException("You do not have priveleges to remove clan disciplines");
            }
            connect();
            for (int i = 0; i < pDisciplines.length; i++) {
                Discipline discipline = pDisciplines[i];
                mStatement.addBatch("DELETE FROM clan_disciplines WHERE clan_id = " + pClan.getId() + " AND discipline_id = " + discipline.getId());
            }
            mStatement.executeBatch();
            disconnect();
        }
    }

    public void updateDiscipline(Discipline pDiscipline) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_DISCIPLINE)) {
            throw new OperationDeniedException("You do not have priveleges to update disciplines");
        }
        connect();
        String retest = "NULL";
        if (pDiscipline.getRetestAbility() != null) {
            retest = pDiscipline.getRetestAbility().getId() + "";
        }
        update("UPDATE disciplines SET name = '" + quoteString(pDiscipline.getName()) + "', " +
               "retest_ability = " + retest + " WHERE id = " + pDiscipline.getId());
        disconnect();
    }

    public void addDiscipline(Discipline pDiscipline) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_DISCIPLINE)) {
            throw new OperationDeniedException("You do not have priveleges to add disciplines");
        }
        connect();
        String retest = "NULL";
        if (pDiscipline.getRetestAbility() != null) {
            retest = pDiscipline.getRetestAbility().getId() + "";
        }
        update("INSERT INTO disciplines(name, retest_ability) VALUES('" + quoteString(pDiscipline.getName()) + "', " +
               retest + ")");
        disconnect();
    }

    public void updateThaumaturgicalPath(IntWithString pPath) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_DISCIPLINE)) {
            throw new OperationDeniedException("You do not have priveleges to update Thaumaturgical paths");
        }
        connect();
        update("UPDATE thama_paths SET name = '" + quoteString(pPath.getString()) + "' WHERE id = " + pPath.getNumber());
        disconnect();
    }

    public IntWithString addThaumaturgicalPath(String pName) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_DISCIPLINE)) {
            throw new OperationDeniedException("You do not have priveleges to add Thaumaturgical paths");
        }
        IntWithString toReturn = null;
        connect();
        int result = update("INSERT INTO thama_paths(name) VALUES('" + quoteString(pName) + "')");
        if (result > 0) {
            ResultSet rs = query("SELECT id, name FROM thama_paths WHERE id = (SELECT MAX(id) FROM thama_paths)");
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                toReturn = new IntWithString(id, name);
            }
        }
        disconnect();
        return toReturn;
    }

    public void updateNecromancyPath(IntWithString pPath) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_DISCIPLINE)) {
            throw new OperationDeniedException("You do not have priveleges to update Necromancy paths");
        }
        connect();
        update("UPDATE necromancy_paths SET name = '" + quoteString(pPath.getString()) + "' WHERE id = " + pPath.getNumber());
        disconnect();
    }

    public IntWithString addNecromancyPath(String pName) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_DISCIPLINE)) {
            throw new OperationDeniedException("You do not have priveleges to add Necromancy paths");
        }
        IntWithString toReturn = null;
        connect();
        update("INSERT INTO necromancy_paths(name) VALUES('" + quoteString(pName) + "')");
        ResultSet rs = query("SELECT id, name FROM necromancy_paths WHERE id = (SELECT MAX(id) FROM necromancy_paths)");
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            toReturn = new IntWithString(id, name);
        }
        disconnect();
        return toReturn;
    }

    public void updateMeritORflaw(MeritORflaw pToUpdate) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_MERIT_OR_FLAW)) {
            throw new OperationDeniedException("You do not have priveleges to update Merits or Flaws");
        }
        connect();
        update("UPDATE meritsNflaws SET name='" + quoteString(pToUpdate.getName()) + "', " +
               "points=" + pToUpdate.getPoints() + " " +
               "WHERE id=" + pToUpdate.getId());
        disconnect();
    }

    public MeritORflaw addMeritORflaw(String pName) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_MERIT_OR_FLAW)) {
            throw new OperationDeniedException("You do not have priveleges to add Merits or Flaws");
        }
        MeritORflaw toReturn = null;
        connect();
        int result = update("INSERT INTO meritsNflaws(name, points) VALUES('" + quoteString(pName) + "', 0)");
        if (result > 0) {
            ResultSet rs = query("SELECT id, name FROM meritsNflaws WHERE id = (SELECT MAX(id) FROM meritsNflaws)");
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                toReturn = new MeritORflaw(id, name, 0);
            }
        }
        disconnect();
        return toReturn;
    }

    public void updateOtherTrait(IntWithString pToUpdate) throws SQLException, RemoteException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_OTHER_TRAITS)) {
            throw new OperationDeniedException("You do not have priveleges to update Other Traits");
        }
        connect();
        update("UPDATE otherTraits SET name='" + quoteString(pToUpdate.getString()) + "' WHERE id=" + pToUpdate.getNumber());
        disconnect();
    }

    public IntWithString addOtherTrait(String pName) throws SQLException, RemoteException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_OTHER_TRAITS)) {
            throw new OperationDeniedException("You do not have priveleges to add Other Traits");
        }
        IntWithString toReturn = null;
        connect();
        int result = update("INSERT INTO otherTraits(name) VALUES('" + quoteString(pName) + "')");
        if (result > 0) {
            ResultSet rs = query("SELECT id, name FROM otherTraits WHERE id=(SELECT MAX(id) FROM otherTraits)");
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                toReturn = new IntWithString(id, name);
            }
        }
        disconnect();
        return toReturn;
    }

    public void updatePlayer(Player pPlayer) throws SQLException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_PLAYERS)) {
            throw new OperationDeniedException("You do not have permission to update players");
        }
        String q = "UPDATE players SET name='" + quoteString(pPlayer.getName()) + "', " +
                "address='" + quoteString(pPlayer.getAddress()) + "', " +
                "phone='" + quoteString(pPlayer.getPhone()) + "', " +
                "email='" + quoteString(pPlayer.getEmail()) + "' " +
                "WHERE id=" + pPlayer.getId();
        connect();
        int rows = update(q);
        if (DEBUG) System.out.println("ManipulationDBImpl.updatePlayer(1026) rows: " + rows);
        disconnect();
    }

    public void addPlayer(Player pPlayer) throws SQLException, RemoteException {
        if (!mLoginUser.getUserRights().get(UserRights.ADD_UPDATE_PLAYERS)) {
            throw new OperationDeniedException("You do not have permission to add players");
        }
        String q = "INSERT INTO players(name, address, phone, email) VALUES(" +
                "'" + quoteString(pPlayer.getName()) + "', '" + quoteString(pPlayer.getAddress()) + "', '" +
                quoteString(pPlayer.getPhone()) + "', '" + quoteString(pPlayer.getEmail()) + "')";
        connect();
        int rows = update(q);
        if (DEBUG) System.out.println("ManipulationDBImpl.addPlayer(1042) rows: " + rows);
        disconnect();
    }

    public void insertPlayersExperience(List<Player> pPlayers, int pAmmount, String pReason) throws SQLException, RemoteException {
        connect();
        for (int i = 0; i < pPlayers.size(); i++) {
            Player player = pPlayers.get(i);
            mStatement.addBatch("INSERT INTO playersExperience(player_id, ammount, reason, [date], user_id, atTimeUserFullName) VALUES(" +
                                player.getId() + ", " + pAmmount + ", '" + quoteString(pReason) + "', Now(), " + mLoginUser.getId() + ", '" + quoteString(mLoginUser.getFullName()) + "')");
        }
        mStatement.executeBatch();
        disconnect();
    }
}
