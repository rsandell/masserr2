package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.remote.db.RemoteTemplateDB;
import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.data.mgm.UserRights;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.DateFormat;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-03 16:46:49
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class TemplateDBImpl extends BasicDBImpl implements RemoteTemplateDB {

    public TemplateDBImpl() throws RemoteException {
        super();
    }

    public Role getTemplate(int pId) throws SQLException, ParseException {
        Role role = null;
        connect();
        String q = "SELECT role_templates.id, role_templates.name AS roles_name, role_templates.generation, " +
                "role_templates.embraced, role_templates.clan, role_templates.nature, role_templates.demeanor, role_templates.courage, role_templates.concience, " +
                "role_templates.[self-control], role_templates.willpower, role_templates.path_dots, role_templates.path, role_templates.physical, " +
                "role_templates.social, role_templates.mental, role_templates.extra_healthlevels, role_templates.sufferes_of_injury, " +
                "generations.bloodpool, generations.bloodpool_spend, generations.abilities_max, " +
                "generations.disciplines_max, generations.traits_max, generations.willpower_start, " +
                "generations.willpower_max, clans.name AS clans_name, goul, clans.base_income, " +
                "role_templates.concienceORconviction, role_templates.selfcontrolORinstinct, " +
                "thauma_type, necromancy_type, generations.bloodpool_human, clans.weaknesses, role_templates.quote " +
                "FROM generations INNER JOIN (clans INNER JOIN role_templates ON clans.id = role_templates.clan) ON generations.generation = role_templates.generation " +
                "WHERE role_templates.id = " + pId;
        ResultSet rs = query(q);
        if (rs.next()) {
            int id = rs.getInt(1);
            String role_name = rs.getString(2);
            //String player_name = rs.getString(3);
            int generation = rs.getInt(3);
            //int sire_id = rs.getInt(5);
            String embraced = rs.getString(4);
            //if (DEBUG) System.out.println("[RetrievalDB][getRole][339] embraced: " + embraced);
            int clan_id = rs.getInt(5);
            String nature = rs.getString(6);
            String demeanor = rs.getString(7);
            int courage = rs.getInt(8);
            int concience = rs.getInt(9);
            int selfControl = rs.getInt(10);
            int willpower = rs.getInt(11);
            int pathDots = rs.getInt(12);
            String path = rs.getString(13);
            int physical = rs.getInt(14);
            int social = rs.getInt(15);
            int mental = rs.getInt(16);
            int extraHealth = rs.getInt(17);
            boolean suffers = rs.getBoolean(18);
            int genBloodPool = rs.getInt(19);
            int genBloodPoolSpend = rs.getInt(20);
            int genAbilitiesMax = rs.getInt(21);
            int genDisciplinesMax = rs.getInt(22);
            int genTraitsMax = rs.getInt(23);
            int genWillpowerStart = rs.getInt(24);
            int genWillPowerMax = rs.getInt(25);
            String clanName = rs.getString(26);
            boolean goul = rs.getBoolean(27);
            int clanBaseIncome = rs.getInt(28);
            //int extraMonthlyIncome = rs.getInt(31);
            //int experience = rs.getInt(32);
            //int vitals = rs.getInt(33);
            //int domainId = rs.getInt(34);
            //String domainName = rs.getString(35);
            //Domain domain = new Domain(domainId, domainName);
            String concienseORconviction = rs.getString(29);
            String selfcontrolORinstinct = rs.getString(30);
            String thaumaType = rs.getString(31);
            String necroType = rs.getString(32);
            int humanBlood = rs.getInt(33);
            String weaknesses = rs.getString(34);
            String quote = rs.getString(35);
            //String sireName = rs.getString(29);
            Generation gen = new Generation(generation, genBloodPool, genBloodPoolSpend, genAbilitiesMax, genDisciplinesMax, genTraitsMax, genWillpowerStart, genWillPowerMax, humanBlood);
            Clan clan = new Clan(clan_id, clanName, clanBaseIncome, weaknesses);
            //IntWithString sire = new IntWithString(sire_id, "");
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            Date embracedDate = format.parse(embraced);
            role = new Role(id, role_name, "", gen, null, embracedDate, clan, nature, demeanor, courage, concience, selfControl, willpower, pathDots, path, physical, social, mental, extraHealth, suffers, goul, 0, concienseORconviction, selfcontrolORinstinct);
            //role.setExperience(experience);
            //role.setVitals(vitals);
            //role.setDomain(domain);
            role.setThaumaType(thaumaType);
            role.setNecromancyType(necroType);
            role.setQuote(quote);
        }
        rs.close();
        if (role != null) {
            role.setDerangements(getDerangementsForTemplate(pId));
            role.setBeastTraits(getBeastTraitsForTemplate(pId));
            role.setDisciplines(getDisciplinesForTemplate(pId));
            role.setFlaws(getFlawsForTemplate(pId));
            role.setMentalAbilities(getAbilitiesForTemplate(pId, ABILITY_TYPE_MENTAL));
            role.setMerits(getMeritsForTemplate(pId));
            role.setNecromancyPaths(getNecromancyPathsForTemplate(pId));
            //role.setNecromanticRituals(getNecromanticRitualsForRole(pId));
            role.setOtherTraits(getOtherTraitsForTemplate(pId));
            role.setPhysicalAbilities(getAbilitiesForTemplate(pId, ABILITY_TYPE_PHYSICAL));
            role.setSocialAbilities(getAbilitiesForTemplate(pId, ABILITY_TYPE_SOCIAL));
            role.setThaumaturgicalPaths(getThaumaturgicalPathsForTemplate(pId));
            //role.setThaumaturgicalRituals(getThaumaturgicalRitualsForRole(pId));
            role.setRituals(getRitualsForTemplate(pId));
        }
        disconnect();
        return role;
    }

    private List<Ritual> getRitualsForTemplate(int pTemplateId) throws SQLException {
        Vector<Ritual> v = new Vector<Ritual>();
        String q = "SELECT rituals.id, rituals.name, rituals.level, rituals.description, ritual_types.id, ritual_types.name " +
                "FROM rituals, ritual_types, template_rituals " +
                "WHERE rituals.type = ritual_types.id AND rituals.id = template_rituals.ritual_id AND template_rituals.template_id = " + pTemplateId +
                " ORDER BY ritual_types.name, rituals.level, rituals.name";
        //connect();
        ResultSet rs = query(q);
        while (rs.next()) {
            int rId = rs.getInt(1);
            String rName = rs.getString(2);
            int level = rs.getInt(3);
            String rDescription = rs.getString(4);
            int tId = rs.getInt(5);
            String tName = rs.getString(6);
            Ritual r = new Ritual(rId, rName, level, new RitualType(tId, tName), rDescription);
            v.add(r);
        }
        rs.close();
        //disconnect();
        return v;
    }

    private List getThaumaturgicalPathsForTemplate(int pTemplateId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT thama_paths.id, thama_paths.name, templateThamaPaths.dots " +
                "FROM thama_paths INNER JOIN templateThamaPaths ON thama_paths.id = templateThamaPaths.path_id " +
                "WHERE templateThamaPaths.template_id = " + pTemplateId +
                " ORDER BY templateThamaPaths.dots DESC, thama_paths.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int dots = rs.getInt(3);
            Path p = new Path(id, name, dots);
            list.add(p);
        }
        rs.close();
        return list;
    }

    private List getOtherTraitsForTemplate(int pTemplateId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT otherTraits.id, otherTraits.name, templateOtherTraits.dots " +
                "FROM otherTraits INNER JOIN templateOtherTraits ON otherTraits.id = templateOtherTraits.trait_id " +
                "WHERE template_id = " + pTemplateId +
                " ORDER BY otherTraits.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int dots = rs.getInt(3);
            OtherTrait tr = new OtherTrait(id, name, dots);
            list.add(tr);
        }
        rs.close();
        return list;
    }

    private List getNecromancyPathsForTemplate(int pTemplateId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT necromancy_paths.id, necromancy_paths.name, templateNecromancyPaths.dots " +
                "FROM necromancy_paths INNER JOIN templateNecromancyPaths ON necromancy_paths.id = templateNecromancyPaths.path_id " +
                "WHERE templateNecromancyPaths.template_id = " + pTemplateId +
                " ORDER BY templateNecromancyPaths.dots DESC, necromancy_paths.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int dots = rs.getInt(3);
            Path p = new Path(id, name, dots);
            list.add(p);
        }
        rs.close();
        return list;
    }

    private List getMeritsForTemplate(int pTemplateId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT meritsNflaws.id, meritsNflaws.name, meritsNflaws.points, templateMeritsNflaws.note " +
                "FROM meritsNflaws INNER JOIN templateMeritsNflaws ON meritsNflaws.id = templateMeritsNflaws.mf_id " +
                "WHERE points >= 0 AND template_id = " + pTemplateId +
                " ORDER BY meritsNflaws.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int points = rs.getInt(3);
            String note = rs.getString(4);
            MeritORflaw me = new MeritORflaw(id, name, points, note);
            list.add(me);
        }
        rs.close();
        return list;
    }

    private List getAbilitiesForTemplate(int pTemplateId, char pAbilityType) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT abilities.id, abilities.name, abilities.type, templateAbilities.dots, templateAbilities.specialisation, abilities.base_monthlyincome " +
                "FROM abilities INNER JOIN templateAbilities ON abilities.id = templateAbilities.ability_id " +
                "WHERE abilities.type='" + pAbilityType + "' AND template_id = " + pTemplateId;

        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String type = rs.getString(3);
            int dots = rs.getInt(4);
            String spec = rs.getString(5);
            int income = rs.getInt(6);
            Ability ab = new Ability(id, name, type.charAt(0), spec, dots, income);
            list.add(ab);
        }
        rs.close();
        return list;
    }

    private List getFlawsForTemplate(int pTemplateId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT meritsNflaws.id, meritsNflaws.name, meritsNflaws.points, templateMeritsNflaws.note " +
                "FROM meritsNflaws INNER JOIN templateMeritsNflaws ON meritsNflaws.id = templateMeritsNflaws.mf_id " +
                "WHERE points < 0 AND template_id = " + pTemplateId +
                " ORDER BY meritsNflaws.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int points = rs.getInt(3);
            String note = rs.getString(4);
            MeritORflaw me = new MeritORflaw(id, name, points, note);
            list.add(me);
        }
        rs.close();
        return list;
    }

    private List getDisciplinesForTemplate(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT disciplines.id, disciplines.name AS disciplines_name, disciplines.retest_ability, " +
                "abilities.name AS abilities_name, abilities.type, templateDisciplines.dots, templateDisciplines.isOfClan " +
                "FROM (abilities RIGHT OUTER JOIN disciplines ON abilities.id = disciplines.retest_ability) INNER JOIN templateDisciplines ON disciplines.id = templateDisciplines.discipline_id " +
                "WHERE templateDisciplines.template_id = " + pRoleId +
                " ORDER BY templateDisciplines.isOfClan, templateDisciplines.dots DESC, disciplines.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int dId = rs.getInt(1);
            String dName = rs.getString(2);
            int aId = rs.getInt(3);
            String aName = rs.getString(4);
            String type = rs.getString(5);
            int dots = rs.getInt(6);
            boolean isOfClan = rs.getBoolean(7);
            Ability ab = null;
            if (aName != null) {
                ab = new Ability(aId, aName, type.charAt(0));
            }
            Discipline di = new Discipline(dId, dName, isOfClan, dots, ab);
            list.add(di);
        }
        rs.close();
        return list;
    }

    private List getDerangementsForTemplate(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT derangement FROM templateDerangements WHERE template_id = " + pRoleId + " AND isBeastTrait = false ORDER BY derangement";
        ResultSet rs = query(q);
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        rs.close();
        return list;
    }

    private List<String> getBeastTraitsForTemplate(int pRoleId) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        String q = "SELECT derangement FROM templateDerangements WHERE template_id = " + pRoleId + " AND isBeastTrait = true ORDER BY derangement";
        ResultSet rs = query(q);
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        rs.close();
        return list;
    }

    public Vector<Role> getMinTemplateInfo() throws SQLException {
        Vector<Role> v = new Vector<Role>(15);

        connect();
        String q = "SELECT role_templates.id, role_templates.name, clan, clans.name, " +
                "role_templates.goul, clans.weaknesses, role_templates.quote " +
                "FROM role_templates, clans " +
                "WHERE role_templates.clan = clans.id" +
                " ORDER BY clans.name, role_templates.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int clanId = rs.getInt(3);
            String clanName = rs.getString(4);
            boolean goul = rs.getBoolean(5);
            //String playerName = rs.getString(6);
            //int vitals = rs.getInt(7);
            //int domainId = rs.getInt(8);
            //String domainName = rs.getString(9);
            String weaknesses = rs.getString(6);
            String quote = rs.getString(7);
            Clan c = new Clan(clanId, clanName);
            c.setWeaknesses(weaknesses);
            Role role = new Role(id, name, c);
            //role.setPlayerName(playerName);
            role.setGhoul(goul);
            role.setQuote(quote);
            //role.setVitals(vitals);
            //role.setDomain(new Domain(domainId, domainName));
            v.addElement(role);
        }
        disconnect();
        return v;
    }
    private static final String INSERT_TEMPLATE_ABILITY = "INSERT INTO templateAbilities (template_id, ability_id, dots, specialisation) " +
            "VALUES({0}, {1}, {2}, '{3}')";
    private static final String INSERT_TEMPLATE_DISCIPLINE = "INSERT INTO templateDisciplines(template_id, discipline_id, dots, isOfClan) " +
            "VALUES({0}, {1}, {2}, {3})";
    private static final String INSERT_TEMPLATE_THAUMA_PATH = "INSERT INTO templateThamaPaths(template_id, path_id, dots) " +
            "VALUES({0}, {1}, {2})";
    private static final String INSERT_TEMPLATE_NECTROMANCY_PATH = "INSERT INTO templateNecromancyPaths(template_id, path_id, dots) " +
            "VALUES({0}, {1}, {2})";
    private static final String INSERT_TEMPLATE_RITUAL = "INSERT INTO template_rituals(template_id, ritual_id) " +
            "VALUES({0}, {1})";
    private static final String INSERT_TEMPLATE_OTHER_TRAIT = "INSERT INTO templateOtherTraits(template_id, trait_id, dots) " +
            "VALUES({0}, {1}, {2})";
    private static final String INSERT_TEMPLATE_DERANGEMENT = "INSERT INTO templateDerangements(template_id, derangement, isBeastTrait) " +
            "VALUES({0}, '{1}', false)";
    private static final String INSERT_TEMPLATE_BEAST_TRAIT = "INSERT INTO templateDerangements(template_id, derangement, isBeastTrait) " +
            "VALUES({0}, '{1}', true)";
    private static final String INSERT_TEMPLATE_MERIT_OR_FLAW = "INSERT INTO templateMeritsNflaws(template_id, mf_id, [note]) " +
            "VALUES({0}, {1}, '{2}')";

    private DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);

    private static final String UPDATE_TEMPLATE = "UPDATE role_templates SET " +
            "name = '{0}', generation = {1}, embraced = '{2}', clan = {3}, nature = '{4}', " +
            "demeanor = '{5}', courage = {6}, concience = {7}, [self-control] = {8}, willpower = {9}, " +
            "path_dots = {10}, path = '{11}', physical = {12}, social = {13}, mental = {14}, " +
            "extra_healthlevels = {15}, sufferes_of_injury = {16}, goul = {17}, " +
            "concienceORconviction = '{18}', selfcontrolORinstinct = '{19}', " +
            "thauma_type = '{20}', necromancy_type = '{21}', quote='{22}'" +
            " WHERE id = {23}";
    private static final String DELETE_TEMPLATE_ABILITIES = "DELETE FROM templateAbilities WHERE template_id =";
    private static final String DELETE_TEMPLATE_DISCIPLINES = "DELETE FROM templateDisciplines WHERE template_id =";
    private static final String DELETE_TEMPLATE_THAUMA_PATHS = "DELETE FROM templateThamaPaths WHERE template_id =";
    private static final String DELETE_TEMPLATE_RITUALS = "DELETE FROM template_rituals WHERE template_id =";
    private static final String DELETE_TEMPLATE_NECROMANCY_PATHS = "DELETE FROM templateNecromancyPaths WHERE template_id =";
    private static final String DELETE_TEMPLATE_OTHER_TRAITS = "DELETE FROM templateOtherTraits WHERE template_id =";
    private static final String DELETE_TEMPLATE_DERANGEMENTS = "DELETE FROM templateDerangements WHERE template_id =";
    private static final String DELETE_TEMPLATE_MERITS_N_FLAWS = "DELETE FROM templateMeritsNflaws WHERE template_id =";

    public void updateTemplate(Role pRole) throws SQLException {
        if(!mLoginUser.getUserRights().get(UserRights.UPDATE_TEMPLATE)) {
            throw new OperationDeniedException("You do not have permission to update Templates!");
        }
        connect();
        ArrayList list = new ArrayList(20);
        list.add(pRole.getName());
        list.add(pRole.getGeneration().getGeneration() + "");
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
        list.add(pRole.isGhoul() + "");
        list.add(pRole.getConcienseORconviction());
        list.add(pRole.getSelfControlORinstinct());
        list.add(pRole.getThaumaType());
        list.add(pRole.getNecromancyType());
        list.add(pRole.getQuote());
        list.add(pRole.getId() + "");
        String query = replaceInString(UPDATE_TEMPLATE, list);
        int result = update(query);
        if(DEBUG) System.out.println("TemplateDBImpl.updateTemplate(390) result: " + result);
        int updateRoleId = pRole.getId();
        list = new ArrayList(4);
        mStatement.addBatch(DELETE_TEMPLATE_ABILITIES + updateRoleId);
        List abilities = pRole.getPhysicalAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = (Ability) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(ability.getId() + "");
            list.add(ability.getDots() + "");
            list.add(ability.getSpecialisation());
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_ABILITY, list));
        }
        abilities = pRole.getSocialAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = (Ability) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(ability.getId() + "");
            list.add(ability.getDots() + "");
            list.add(ability.getSpecialisation());
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_ABILITY, list));
        }
        abilities = pRole.getMentalAbilities();
        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = (Ability) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(ability.getId() + "");
            list.add(ability.getDots() + "");
            list.add(ability.getSpecialisation());
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_ABILITY, list));
        }
        mStatement.addBatch(DELETE_TEMPLATE_DISCIPLINES + updateRoleId);
        List disciplines = pRole.getDisciplines();
        for (int i = 0; i < disciplines.size(); i++) {
            Discipline discipline = (Discipline) disciplines.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(discipline.getId() + "");
            list.add(discipline.getDots() + "");
            list.add(discipline.isOfClan() + "");
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_DISCIPLINE, list));
        }
        mStatement.addBatch(DELETE_TEMPLATE_THAUMA_PATHS + updateRoleId);
        if (pRole.hasThaumaturgy()) {
            List paths = pRole.getThaumaturgicalPaths();
            for (int i = 0; i < paths.size(); i++) {
                Path path = (Path) paths.get(i);
                list.clear();
                list.add(updateRoleId + "");
                list.add(path.getId() + "");
                list.add(path.getDots() + "");
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_THAUMA_PATH, list));
            }
        }
        mStatement.addBatch(DELETE_TEMPLATE_NECROMANCY_PATHS + updateRoleId);
        if (pRole.hasNecromancy()) {
            List paths = pRole.getNecromancyPaths();
            for (int i = 0; i < paths.size(); i++) {
                Path path = (Path) paths.get(i);
                list.clear();
                list.add(updateRoleId + "");
                list.add(path.getId() + "");
                list.add(path.getDots() + "");
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_NECTROMANCY_PATH, list));
            }
        }
        mStatement.addBatch(DELETE_TEMPLATE_RITUALS + updateRoleId);
        if (pRole.hasRituals()) {
            List<Ritual> rituals = pRole.getRituals();
            for (int i = 0; i < rituals.size(); i++) {
                Ritual ritual = rituals.get(i);
                list.clear();
                list.add(updateRoleId + "");
                list.add(ritual.getId() + "");
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_RITUAL, list));
            }
        }
        mStatement.addBatch(DELETE_TEMPLATE_OTHER_TRAITS + updateRoleId);
        abilities = pRole.getOtherTraits();
        for (int i = 0; i < abilities.size(); i++) {
            OtherTrait otherTrait = (OtherTrait) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(otherTrait.getId() + "");
            list.add(otherTrait.getDots() + "");
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_OTHER_TRAIT, list));
        }
        mStatement.addBatch(DELETE_TEMPLATE_DERANGEMENTS + updateRoleId);
        abilities = pRole.getDerangements();
        for (int i = 0; i < abilities.size(); i++) {
            String s = (String) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(s);
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_DERANGEMENT, list));
        }
        abilities = pRole.getBeastTraits();
        for (int i = 0; i < abilities.size(); i++) {
            String s = (String) abilities.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(s);
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_BEAST_TRAIT, list));
        }
        mStatement.addBatch(DELETE_TEMPLATE_MERITS_N_FLAWS + updateRoleId);
        List mORfs = pRole.getMerits();
        for (int i = 0; i < mORfs.size(); i++) {
            MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(meritORflaw.getId() + "");
            list.add(meritORflaw.getNote());
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_MERIT_OR_FLAW, list));
        }
        mORfs = pRole.getFlaws();
        for (int i = 0; i < mORfs.size(); i++) {
            MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
            list.clear();
            list.add(updateRoleId + "");
            list.add(meritORflaw.getId() + "");
            list.add(meritORflaw.getNote());
            mStatement.addBatch(replaceInString(INSERT_TEMPLATE_MERIT_OR_FLAW, list));
        }
        if(DEBUG) System.out.println("TemplateDBImpl.updateTemplate(508) executing batch");
        mStatement.executeBatch();
    }

    private static final String INSERT_TEMPLATE = "INSERT INTO role_templates (" +
            "name, generation, embraced, clan, nature, demeanor, " +
            "courage, concience, [self-control], willpower, path_dots, " +
            "path, physical, social, mental, extra_healthlevels, sufferes_of_injury, " +
            "goul, concienceORconviction, selfcontrolORinstinct, thauma_type, necromancy_type, quote" +
            ") " +
            "VALUES ('{0}', {1}, '{2}', {3}, '{4}', '{5}', " +
            "{6}, {7}, {8}, {9}, {10}, " +
            "'{11}', {12}, {13}, {14}, {15}, {16}, " +
            "{17}, '{18}', '{19}', '{20}', '{21}', '{22}')";

    public int addTemplate(Role pRole) throws SQLException {
        if(!mLoginUser.getUserRights().get(UserRights.ADD_TEMPLATE)) {
            throw new OperationDeniedException("You do not have permission to add Templates!");
        }
        int addedRoleId = -1;
        //IntWithString[] values = new IntWithString[19];
        ArrayList list = new ArrayList(19);
        list.add(pRole.getName());
        list.add(pRole.getGeneration().getGeneration() + "");
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
        list.add(pRole.isGhoul() + "");
        list.add(pRole.getConcienseORconviction());
        list.add(pRole.getSelfControlORinstinct());
        list.add(pRole.getThaumaType());
        list.add(pRole.getNecromancyType());
        list.add(pRole.getQuote());
        String query = replaceInString(INSERT_TEMPLATE, list);
        connect();
        int result = update(query);
        if(DEBUG) System.out.println("TemplateDBImpl.addTemplate(371) template inserted: " + result);
        if (result > 0) {
            //FIND THE INSWERTED ID
            ResultSet rd = query("SELECT MAX(id) FROM role_templates");
            if (rd.next()) {
                addedRoleId = rd.getInt(1);
            }
            rd.close();
        }
        if(DEBUG) System.out.println("TemplateDBImpl.addTemplate(380) addedRoleId MAX? " + addedRoleId);
        if (addedRoleId >= 0) {
            list = new ArrayList(4);
            List abilities = pRole.getPhysicalAbilities();
            for (int i = 0; i < abilities.size(); i++) {
                Ability ability = (Ability) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(ability.getId() + "");
                list.add(ability.getDots() + "");
                list.add(ability.getSpecialisation());
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_ABILITY, list));
            }

            abilities = pRole.getSocialAbilities();
            for (int i = 0; i < abilities.size(); i++) {
                Ability ability = (Ability) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(ability.getId() + "");
                list.add(ability.getDots() + "");
                list.add(ability.getSpecialisation());
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_ABILITY, list));
            }

            abilities = pRole.getMentalAbilities();
            for (int i = 0; i < abilities.size(); i++) {
                Ability ability = (Ability) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(ability.getId() + "");
                list.add(ability.getDots() + "");
                list.add(ability.getSpecialisation());
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_ABILITY, list));
            }
            List disciplines = pRole.getDisciplines();
            for (int i = 0; i < disciplines.size(); i++) {
                Discipline discipline = (Discipline) disciplines.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(discipline.getId() + "");
                list.add(discipline.getDots() + "");
                list.add(discipline.isOfClan() + "");
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_DISCIPLINE, list));
            }
            if (pRole.hasThaumaturgy()) {
                List paths = pRole.getThaumaturgicalPaths();
                for (int i = 0; i < paths.size(); i++) {
                    Path path = (Path) paths.get(i);
                    list.clear();
                    list.add(addedRoleId + "");
                    list.add(path.getId() + "");
                    list.add(path.getDots() + "");
                    mStatement.addBatch(replaceInString(INSERT_TEMPLATE_THAUMA_PATH, list));
                }
            }
            if (pRole.hasNecromancy()) {
                List paths = pRole.getNecromancyPaths();
                for (int i = 0; i < paths.size(); i++) {
                    Path path = (Path) paths.get(i);
                    list.clear();
                    list.add(addedRoleId + "");
                    list.add(path.getId() + "");
                    list.add(path.getDots() + "");
                    mStatement.addBatch(replaceInString(INSERT_TEMPLATE_NECTROMANCY_PATH, list));
                }
            }
            if (pRole.hasRituals()) {
                List<Ritual> rituals = pRole.getRituals();
                for (int i = 0; i < rituals.size(); i++) {
                    Ritual ritual = rituals.get(i);
                    list.clear();
                    list.add(addedRoleId + "");
                    list.add(ritual.getId() + "");
                    mStatement.addBatch(replaceInString(INSERT_TEMPLATE_RITUAL, list));
                }
            }
            abilities = pRole.getOtherTraits();
            for (int i = 0; i < abilities.size(); i++) {
                OtherTrait otherTrait = (OtherTrait) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(otherTrait.getId() + "");
                list.add(otherTrait.getDots() + "");
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_OTHER_TRAIT, list));
            }
            abilities = pRole.getDerangements();
            for (int i = 0; i < abilities.size(); i++) {
                String s = (String) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(s);
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_DERANGEMENT, list));
            }
            abilities = pRole.getBeastTraits();
            for (int i = 0; i < abilities.size(); i++) {
                String s = (String) abilities.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(s);
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_BEAST_TRAIT, list));
            }
            List mORfs = pRole.getMerits();
            for (int i = 0; i < mORfs.size(); i++) {
                MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(meritORflaw.getId() + "");
                list.add(meritORflaw.getNote());
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_MERIT_OR_FLAW, list));
            }
            mORfs = pRole.getFlaws();
            for (int i = 0; i < mORfs.size(); i++) {
                MeritORflaw meritORflaw = (MeritORflaw) mORfs.get(i);
                list.clear();
                list.add(addedRoleId + "");
                list.add(meritORflaw.getId() + "");
                list.add(meritORflaw.getNote());
                mStatement.addBatch(replaceInString(INSERT_TEMPLATE_MERIT_OR_FLAW, list));
            }
            if(DEBUG) System.out.println("TemplateDBImpl.addTemplate(672) executing batch");
            mStatement.executeBatch();
        }
        if(DEBUG) System.out.println("TemplateDBImpl.addTemplate(675) disconnecting");
        disconnect();
        return addedRoleId;
    }
}
