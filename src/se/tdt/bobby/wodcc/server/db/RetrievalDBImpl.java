package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.data.mgm.User;
import se.tdt.bobby.wodcc.remote.db.RemoteRetrievalDB;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-10 00:11:41
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class RetrievalDBImpl extends BasicDBImpl implements RemoteRetrievalDB {
    private static final String MINIMUM_ROLE_INFO_QUERY = "SELECT roles.id, roles.name, roles.clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses,  roles.slp, roles.player_id, players.name\n" +
            "FROM players RIGHT JOIN (domains INNER JOIN (clans INNER JOIN roles ON clans.id = roles.clan) ON domains.id = roles.domain) ON players.id = roles.player_id " +
            "ORDER BY clans.name, roles.name";

    public RetrievalDBImpl() throws RemoteException {
        super();
    }

    public Vector getAbilities() throws SQLException {
        connect();
        Vector list = null;
        String query = "SELECT id, name, type, base_monthlyincome FROM abilities ORDER BY type, name";
        ResultSet rs = query(query);
        list = new Vector();
        while (rs.next()) {
            list.add(new Ability(rs.getInt(1), rs.getString(2), rs.getString(3).charAt(0), rs.getInt(4)));
        }
        disconnect();
        return list;
    }

    public List getAbilities(char pAbilityType) throws SQLException {
        if (pAbilityType != ABILITY_TYPE_MENTAL && pAbilityType != ABILITY_TYPE_PHYSICAL && pAbilityType != ABILITY_TYPE_SOCIAL)
            throw new IllegalArgumentException(pAbilityType + "");
        connect();
        ArrayList list = null;
        String query = "SELECT id, name, type, base_monthlyincome FROM abilities WHERE type = '" + pAbilityType + "' ORDER BY type, name";
        ResultSet rs = query(query);
        list = new ArrayList();
        while (rs.next()) {
            list.add(new Ability(rs.getInt(1), rs.getString(2), rs.getString(3).charAt(0), rs.getInt(4)));
        }
        disconnect();
        return list;
    }

    public Vector getGenerations() throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT generation, bloodpool, bloodpool_spend, abilities_max, disciplines_max, traits_max, willpower_start, willpower_max,bloodpool_human " +
                "FROM generations " +
                "ORDER BY generation DESC";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new Generation(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
        }
        disconnect();
        return v;
    }

    public Vector getClans() throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT id, name, base_income, weaknesses " +
                "FROM clans " +
                "ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new Clan(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
        }
        disconnect();
        return v;
    }

    public Vector getRoleNames(int pGeneration, int pClan) throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT id, name " +
                "FROM roles " +
                "WHERE generation <= " + pGeneration + " AND clan = " + pClan +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    public Vector getRoleNames(int pClan) throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT id, name " +
                "FROM roles " +
                "WHERE clan = " + pClan +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    public Vector getRoleNames() throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT id, name " +
                "FROM roles " +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    public Vector getMinRoleRoleInfo() throws SQLException {
        if (DEBUG) System.out.println("RetrievalDBImpl.getMinRoleRoleInfo (131) running");
        Vector v = new Vector(15);

        connect();
//        String q = "SELECT roles.id, roles.name, clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses, roles.slp " +
//                "FROM roles, clans, domains " +
//                "WHERE roles.clan = clans.id AND roles.domain = domains.id" +
//                " ORDER BY clans.name, roles.name";
        ResultSet rs = query(MINIMUM_ROLE_INFO_QUERY);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int clanId = rs.getInt(3);
            String clanName = rs.getString(4);
            boolean goul = rs.getBoolean(5);
            //String playerName = rs.getString(6);
            int vitals = rs.getInt(7);
            int domainId = rs.getInt(8);
            String domainName = rs.getString(9);
            String weaknesses = rs.getString(10);
            boolean slp = rs.getBoolean(11);
            int playerId = rs.getInt(12);
            String player_Name = rs.getString(13);
            Player player = null;
            if (player_Name != null) {
                player = new Player(playerId, player_Name);
            }
            Clan c = new Clan(clanId, clanName);
            c.setWeaknesses(weaknesses);
            Role role = new Role(id, name, c);
            //role.setPlayerName(playerName);
            role.setPlayer(player);
            role.setGhoul(goul);
            role.setVitals(vitals);
            role.setDomain(new Domain(domainId, domainName));
            role.setSLP(slp);
            v.addElement(role);
        }
        disconnect();
        return v;
    }

    public Vector getDisciplineNames() throws SQLException {
        Vector v = new Vector(35);

        connect();
        String q = "SELECT id, name " +
                "FROM disciplines " +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    public Vector<IntWithString> getThaumaturgicalPathNames() throws SQLException {
        Vector<IntWithString> v = new Vector<IntWithString>(15);

        connect();
        String q = "SELECT id, name " +
                "FROM thama_paths " +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    /*public Vector getThaumaturgicalRitualNames() throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT id, name, thama_rituals.level " +
                "FROM thama_rituals " +
                " ORDER BY thama_rituals.level, name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2) + " (" + rs.getInt(3) + ")"));
        }
        disconnect();
        return v;
    }*/

    /*public Vector getThaumaturgicalRituals() throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT id, name, thama_rituals.level, description " +
                "FROM thama_rituals " +
                " ORDER BY thama_rituals.level, name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int level = rs.getInt(3);
            String description = rs.getString(4);
            v.addElement(new Ritual(id, name, level, description));
        }
        disconnect();
        return v;
    }*/

    public Vector<IntWithString> getNectromancyPathNames() throws SQLException {
        Vector<IntWithString> v = new Vector<IntWithString>(15);

        connect();
        String q = "SELECT id, name " +
                "FROM necromancy_paths " +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    public Vector<IntWithString> getOtherTraitNames() throws SQLException {
        Vector<IntWithString> v = new Vector<IntWithString>(15);

        connect();
        String q = "SELECT id, name " +
                "FROM otherTraits " +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    public Vector<Ritual> getRituals() throws SQLException, RemoteException {
        Vector<Ritual> v = new Vector<Ritual>();
        String q = "SELECT rituals.id, rituals.name, rituals.level, rituals.description, ritual_types.id, ritual_types.name " +
                "FROM rituals, ritual_types " +
                "WHERE rituals.type = ritual_types.id " +
                "ORDER BY ritual_types.name, rituals.level, rituals.name";
        connect();
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
        disconnect();
        return v;
    }

    public Vector<RitualType> getRitualTypes() throws SQLException {
        Vector<RitualType> v = new Vector<RitualType>();
        connect();
        ResultSet rs = query("SELECT id, name FROM ritual_types ORDER BY name");
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            v.add(new RitualType(id, name));
        }
        disconnect();
        return v;
    }

    public Vector<Ritual> getRituals(RitualType pRitualType) throws SQLException, RemoteException {
        Vector<Ritual> v = new Vector<Ritual>();
        String q = "SELECT rituals.id, rituals.name, rituals.level, rituals.description, ritual_types.id, ritual_types.name " +
                "FROM rituals, ritual_types " +
                "WHERE rituals.type = ritual_types.id AND rituals.type = " + pRitualType.getId() +
                " ORDER BY rituals.level, rituals.name";
        connect();
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
        disconnect();
        return v;
    }

    public Vector<Discipline> getClanDisciplines(Clan pClan) throws SQLException, RemoteException {
        Vector<Discipline> list = new Vector<Discipline>();
        connect();
        String q = "SELECT disciplines.id AS disciplines_id, disciplines.name AS disciplines_name, abilities.id AS abilities_id, abilities.name AS abilities_name, abilities.type, clan_disciplines.clan_id " +
                "FROM (abilities RIGHT OUTER JOIN disciplines ON abilities.id = disciplines.retest_ability) INNER JOIN clan_disciplines ON disciplines.id = clan_disciplines.discipline_id " +
                "WHERE clan_disciplines.clan_id = " + pClan.getId();
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int abilityId = rs.getInt(3);
            Ability ability = null;
            if (abilityId > 0) {
                ability = new Ability(abilityId, rs.getString(4), rs.getString(5).charAt(0));
            }
            Discipline discipline = new Discipline(id, name, true, 0, ability);
            list.add(discipline);
        }
        disconnect();
        return list;
    }

    public Vector<Discipline> getDisciplines() throws SQLException, RemoteException {
        Vector<Discipline> v = new Vector<Discipline>(35);

        connect();
        String q = "SELECT disciplines.id AS disciplines_id, disciplines.name AS disciplines_name, abilities.id AS abilities_id, abilities.name AS abilities_name, abilities.type " +
                "FROM abilities RIGHT OUTER JOIN disciplines ON abilities.id = disciplines.retest_ability" +
                " ORDER BY disciplines.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Ability ability = null;
            int abilityId = rs.getInt(3);
            if (abilityId > 0) {
                ability = new Ability(abilityId, rs.getString(4), rs.getString(5).charAt(0));
            }
            v.addElement(new Discipline(id, name, ability));
        }
        disconnect();
        return v;
    }

    public List<ClanFontSymbol> getClanFontSymbols() throws SQLException {
        List<ClanFontSymbol> list = new ArrayList<ClanFontSymbol>();
        connect();
        ResultSet rs = query("SELECT id, symbol_font, symbol_char FROM clans WHERE symbol_font IS NOT NULL AND symbol_char IS NOT NULL");
        while (rs.next()) {
            int id = rs.getInt(1);
            String font = rs.getString(2);
            String symbol = rs.getString(3);
            list.add(new ClanFontSymbol(id, font, symbol.charAt(0)));
        }
        disconnect();
        return list;
    }
    /*public Vector getNectromancyRitualNames() throws SQLException {
		Vector v = new Vector(15);

		connect();
		String q = "SELECT id, name, necromantic_rituals.level " +
				"FROM necromantic_rituals " +
				" ORDER BY necromantic_rituals.level, name";
		ResultSet rs = query(q);
		while (rs.next()) {
			v.addElement(new IntWithString(rs.getInt(1), rs.getString(2) + " (" + rs.getInt(3) + ")"));
		}
		disconnect();
		return v;
	}*/

    /*public Vector getNectromancyRituals() throws SQLException {
        Vector v = new Vector(15);

        connect();
        String q = "SELECT id, name, necromantic_rituals.level, description " +
                "FROM necromantic_rituals " +
                " ORDER BY necromantic_rituals.level, name";
        ResultSet rs = query(q);
        while (rs.next()) {
            Ritual r = new Ritual(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            v.addElement(r);
        }
        disconnect();
        return v;
    }*/

    public Discipline getDiscipline(int pNumber) throws SQLException {
        connect();
        String q = "SELECT disciplines.id, disciplines.name, disciplines.retest_ability, abilities.name, abilities.type " +
                "FROM abilities INNER JOIN disciplines ON abilities.id = disciplines.retest_ability " +
                "WHERE disciplines.id = " + pNumber;
        ResultSet rs = query(q);
        Discipline di = null;
        if (rs.next()) {
            di = new Discipline(rs.getInt(1), rs.getString(2));
            int abId = rs.getInt(3);
            String abName = rs.getString(4);
            String abType = rs.getString(5);
            if (abId != 0 && abName != null) {
                Ability ab = new Ability(abId, abName, abType.charAt(0));
                di.setRetestAbility(ab);
            }
        }
        disconnect();
        return di;
    }

    public Vector<MeritORflaw> getMerits() throws SQLException {
        Vector<MeritORflaw> v = new Vector<MeritORflaw>(15);

        connect();
        String q = "SELECT id, name, points " +
                "FROM meritsNflaws " +
                "WHERE points >= 0" +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new MeritORflaw(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        disconnect();
        return v;
    }

    public Vector<MeritORflaw> getFlaws() throws SQLException {
        Vector<MeritORflaw> v = new Vector<MeritORflaw>(15);

        connect();
        String q = "SELECT id, name, points " +
                "FROM meritsNflaws " +
                "WHERE points < 0" +
                " ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.addElement(new MeritORflaw(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        disconnect();
        return v;
    }

    public Role getRole(int pId) throws SQLException, ParseException {
        Role role = null;
        connect();
        String q = "SELECT roles.id, roles.name, roles.player_name, roles.generation, roles.sire, \n" +
                "roles.embraced, roles.clan, roles.nature, roles.demeanor, roles.courage, roles.concience, \n" +
                "roles.[self-control], roles.willpower, roles.path_dots, roles.path, roles.physical, \n" +
                "roles.social, roles.mental, roles.extra_healthlevels, roles.sufferes_of_injury, \n" +
                "generations.bloodpool, generations.bloodpool_spend, generations.abilities_max,\n" +
                "generations.disciplines_max, generations.traits_max, generations.willpower_start,\n" +
                "generations.willpower_max, clans.name, roles.goul, clans.base_income, roles.extra_monthly_income,\n" +
                "(SELECT SUM(ammount) FROM experience WHERE role_id = roles.id) AS experience, roles.vitals, roles.domain, domains.name,\n" +
                "roles.concienceORconviction, roles.selfcontrolORinstinct, roles.thauma_type, roles.necromancy_type,\n" +
                "generations.bloodpool_human, clans.weaknesses, roles.quote, roles.slp,\n" +
                "roles.player_id, players.name\n" +
                "FROM players \n" +
                "  RIGHT JOIN (generations \n" +
                "    INNER JOIN (domains \n" +
                "      INNER JOIN (clans \n" +
                "        INNER JOIN roles ON clans.id = roles.clan) ON domains.id = roles.domain) ON generations.generation = roles.generation) ON players.id = roles.player_id " +
                "WHERE roles.id=" + pId;
//        String q = "SELECT roles.id, roles.name AS roles_name, roles.player_name, roles.generation, roles.sire, " +
//                "roles.embraced, roles.clan, roles.nature, roles.demeanor, roles.courage, roles.concience, " +
//                "roles.[self-control], roles.willpower, roles.path_dots, roles.path, roles.physical, " +
//                "roles.social, roles.mental, roles.extra_healthlevels, roles.sufferes_of_injury, " +
//                "generations.bloodpool, generations.bloodpool_spend, generations.abilities_max, " +
//                "generations.disciplines_max, generations.traits_max, generations.willpower_start, " +
//                "generations.willpower_max, clans.name AS clans_name, goul, clans.base_income, extra_monthly_income, " +
//                "(SELECT SUM(ammount) FROM experience WHERE role_id = roles.id) AS experience, roles.vitals, roles.domain, domains.name, " +
//                "roles.concienceORconviction, roles.selfcontrolORinstinct, thauma_type, necromancy_type, " +
//                "generations.bloodpool_human, clans.weaknesses, roles.quote, roles.slp " +
//                "FROM generations INNER JOIN (clans INNER JOIN roles ON clans.id = roles.clan) ON generations.generation = roles.generation, domains " +
//                "WHERE roles.domain = domains.id AND roles.id = " + pId;
        ResultSet rs = query(q);
        if (rs.next()) {
            int id = rs.getInt(1);
            String role_name = rs.getString(2);
            String player_name = rs.getString(3);
            int generation = rs.getInt(4);
            int sire_id = rs.getInt(5);
            String embraced = rs.getString(6);
            //if (DEBUG) System.out.println("[RetrievalDB][getRole][339] embraced: " + embraced);
            int clan_id = rs.getInt(7);
            String nature = rs.getString(8);
            String demeanor = rs.getString(9);
            int courage = rs.getInt(10);
            int concience = rs.getInt(11);
            int selfControl = rs.getInt(12);
            int willpower = rs.getInt(13);
            int pathDots = rs.getInt(14);
            String path = rs.getString(15);
            int physical = rs.getInt(16);
            int social = rs.getInt(17);
            int mental = rs.getInt(18);
            int extraHealth = rs.getInt(19);
            boolean suffers = rs.getBoolean(20);
            int genBloodPool = rs.getInt(21);
            int genBloodPoolSpend = rs.getInt(22);
            int genAbilitiesMax = rs.getInt(23);
            int genDisciplinesMax = rs.getInt(24);
            int genTraitsMax = rs.getInt(25);
            int genWillpowerStart = rs.getInt(26);
            int genWillPowerMax = rs.getInt(27);
            String clanName = rs.getString(28);
            boolean goul = rs.getBoolean(29);
            int clanBaseIncome = rs.getInt(30);
            int extraMonthlyIncome = rs.getInt(31);
            int experience = rs.getInt(32);
            int vitals = rs.getInt(33);
            int domainId = rs.getInt(34);
            String domainName = rs.getString(35);
            Domain domain = new Domain(domainId, domainName);
            String concienseORconviction = rs.getString(36);
            String selfcontrolORinstinct = rs.getString(37);
            String thaumaType = rs.getString(38);
            String necroType = rs.getString(39);
            int humanBlood = rs.getInt(40);
            String weaknesses = rs.getString(41);
            String quote = rs.getString(42);
            boolean slp = rs.getBoolean(43);
            int playerId = rs.getInt(44);
            String playerName = rs.getString(45);
            Generation gen = new Generation(generation, genBloodPool, genBloodPoolSpend, genAbilitiesMax, genDisciplinesMax, genTraitsMax, genWillpowerStart, genWillPowerMax, humanBlood);
            Clan clan = new Clan(clan_id, clanName, clanBaseIncome, weaknesses);
            IntWithString sire = new IntWithString(sire_id, "");
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            Date embracedDate = format.parse(embraced);
            role = new Role(id, role_name, player_name, gen, sire, embracedDate, clan, nature, demeanor, courage, concience, selfControl, willpower, pathDots, path, physical, social, mental, extraHealth, suffers, goul, extraMonthlyIncome, concienseORconviction, selfcontrolORinstinct);
            if (playerName != null && playerId != 0) {
                role.setPlayer(new Player(playerId, playerName));
            }
            role.setExperience(experience);
            role.setVitals(vitals);
            role.setDomain(domain);
            role.setThaumaType(thaumaType);
            role.setNecromancyType(necroType);
            role.setQuote(quote);
            role.setSLP(slp);
        }
        rs.close();
        if (role != null) {
            if (role.getSire().getNumber() > 0) {
                IntWithString sire = role.getSire();
                sire.setString(getRoleNameNoConnect(sire.getNumber()));
                role.setSire(sire);
                //if (DEBUG) System.out.println("[RetrievalDB][getRole][378] set the name " + role.getSire() + " to " + role.getName());
            }
            else {
                //if (DEBUG) System.out.println("[RetrievalDB][getRole][380] Sire is set to null on " + role.getName());
                role.setSire(null);
            }
            if (role.getPlayer() != null) {
                role.setPlayer(getPlayer(role.getPlayer().getId(), false, true));
            }
            role.setDerangements(getDerangementsForRole(pId));
            role.setBeastTraits(getBeastTraitsForRole(pId));
            role.setDisciplines(getDisciplinesForRole(pId));
            role.setFlaws(getFlawsForRole(pId));
            role.setMentalAbilities(getAbilitiesForRole(pId, ABILITY_TYPE_MENTAL));
            role.setMerits(getMeritsForRole(pId));
            role.setNecromancyPaths(getNecromancyPathsForRole(pId));
            //role.setNecromanticRituals(getNecromanticRitualsForRole(pId));
            role.setOtherTraits(getOtherTraitsForRole(pId));
            role.setPhysicalAbilities(getAbilitiesForRole(pId, ABILITY_TYPE_PHYSICAL));
            role.setSocialAbilities(getAbilitiesForRole(pId, ABILITY_TYPE_SOCIAL));
            role.setThaumaturgicalPaths(getThaumaturgicalPathsForRole(pId));
            //role.setThaumaturgicalRituals(getThaumaturgicalRitualsForRole(pId));
            role.setRituals(getRitualsForRole(pId));
            role.setFightForm(getFightFormForRole(pId));
            role.setFlightForm(getFlightFormForRole(pId));
            role.setProfessions(getProfessionsForRole(pId));
            role.setResources(getResourcesForRole(pId));
            //role.setBaseMoneyForAge(getBaseMoneyForAge(role.getAge()));
            role.setBankAccounts(getBankAccountsForRole(pId));
            role.setInfluences(getInfluencesForRole(pId));
            role.setPlots(getActivePlotsForRole(pId));
        }
        disconnect();
        return role;
    }

    private FightOrFlight getFlightFormForRole(int pRoleId) throws SQLException {
        FightOrFlight fightOrFlight = null;

        ResultSet rs = query("SELECT fightNflight.id, fightNflight.name, fightNflight.description " +
                             "FROM roles, fightNFlight " +
                             "WHERE fightNflight.id = roles.flightForm AND roles.id = " + pRoleId);
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            fightOrFlight = new FightOrFlight(id, name, description);
        }
        rs.close();

        return fightOrFlight;
    }

    private FightOrFlight getFightFormForRole(int pRoleId) throws SQLException {
        FightOrFlight fightOrFlight = null;

        ResultSet rs = query("SELECT fightNflight.id, fightNflight.name, fightNflight.description " +
                             "FROM roles, fightNFlight " +
                             "WHERE fightNflight.id = roles.fightForm AND roles.id = " + pRoleId);
        if (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            fightOrFlight = new FightOrFlight(id, name, description);
        }
        rs.close();

        return fightOrFlight;
    }

    private List<Plot> getActivePlotsForRole(int pRoleId) throws SQLException {
        List<Plot> list = new ArrayList<Plot>();
        String q = "SELECT plots.id, title, domains.id, domains.name, description, positive, negative, SLdescription, done, created " +
                "FROM plots, role_plots, domains " +
                "WHERE plots.domain = domains.id " +
                "AND plots.id = role_plots.plot_id " +
                "AND role_plots.role_id = " + pRoleId;
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            int dId = rs.getInt(3);
            String dName = rs.getString(4);
            String description = rs.getString(5);
            String positive = rs.getString(6);
            String negative = rs.getString(7);
            String sl = rs.getString(8);
            boolean done = rs.getBoolean(9);
            java.sql.Date created = rs.getDate(10);
            Plot p = new Plot(id, title, description, created, positive, negative, done, new Domain(dId, dName), sl);
            list.add(p);
        }
        rs.close();
        return list;
    }

    private Vector<Ritual> getRitualsForRole(int pRoleId) throws SQLException {
        Vector<Ritual> v = new Vector<Ritual>();
        String q = "SELECT rituals.id, rituals.name, rituals.level, rituals.description, ritual_types.id, ritual_types.name " +
                "FROM rituals, ritual_types, role_rituals " +
                "WHERE rituals.type = ritual_types.id AND rituals.id = role_rituals.ritual_id AND role_rituals.role_id = " + pRoleId +
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

    private Vector getInfluencesForRole(int pRoleId) throws SQLException {
        Vector li = new Vector();
        String q = "SELECT influences.id, influences.name, roleInfluences.dots, roleInfluences.notes " +
                "FROM influences INNER JOIN roleInfluences ON influences.id = roleInfluences.influence_id " +
                "WHERE roleInfluences.role_id =" + pRoleId +
                " ORDER BY influences.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            Influence inf = new Influence(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            li.add(inf);
        }
        rs.close();
        return li;
    }

    private List getBankAccountsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT id, owner_name, ammount, income, active " +
                "FROM bank_accounts " +
                "WHERE owner = " + pRoleId + " AND active = true" +
                " ORDER BY income, owner_name";
        ResultSet rs = query(q);
        while (rs.next()) {
            BankAccount ba = new BankAccount(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getBoolean(4), rs.getBoolean(5));
            list.add(ba);
        }
        return list;
    }

    private List getResourcesForRole(int pRoleId) throws SQLException {
        ArrayList li = new ArrayList();
        String q = "SELECT resources.id, resources.name, resources.description, resources.income, roleResources.percent, resources.cost, resources.domain, domains.name, resources.type " +
                "FROM resources INNER JOIN roleResources ON resources.id = roleResources.resource_id, domains " +
                "WHERE resources.domain = domains.id AND roleResources.role_id = " + pRoleId +
                " ORDER BY resources.type, resources.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            Resource res = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getFloat(6), rs.getString(9));
            int dId = rs.getInt(7);
            String dName = rs.getString(8);
            res.setDomain(new Domain(dId, dName));
            li.add(res);
        }
        rs.close();
        for (int i = 0; i < li.size(); i++) {
            Resource resource = (Resource) li.get(i);
            resource.setInfluences(getInfluencesForResource(resource, false));
        }
        return li;
    }

    private List getProfessionsForRole(int pId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT professions.id, professions.name, professions.monthly_income, professions.mortal, roleProfessions.mask " +
                "FROM professions INNER JOIN roleProfessions ON professions.id = roleProfessions.profession_id " +
                "WHERE roleProfessions.role_id = " + pId +
                " ORDER BY roleProfessions.mask, professions.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            Profession p = new Profession(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4), rs.getBoolean(5));
            list.add(p);
        }
        rs.close();
        return list;
    }

    private String getRoleNameNoConnect(int pRoleId) throws SQLException {
        String s = null;
        ResultSet rs = query("SELECT name FROM roles WHERE id = " + pRoleId);
        if (rs.next()) {
            s = rs.getString(1);
        }
        return s;
    }

    public int getBaseMoneyForAge(int pAgeYears) throws SQLException {
        int money = 0;
        connect();
        String q = "SELECT base_money " +
                "FROM roleAgeSpanMoney " +
                "WHERE min_age <= " + pAgeYears + " AND max_age >= " + pAgeYears;
        ResultSet rs = query(q);
        if (rs.next()) {
            money = rs.getInt(1);
        }
        rs.close();
        disconnect();
        return money;
    }

    /*public int getBaseMoneyForAgeConnect(int pAgeYears) throws SQLException {
        int money = 0;
        connect();
        money = getBaseMoneyForAge(pAgeYears);
        disconnect();
        return money;
    }*/

    /*private List getThaumaturgicalRitualsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT thama_rituals.id, thama_rituals.name, thama_rituals.level, thama_rituals.description " +
                "FROM thama_rituals INNER JOIN roleThamaRituals ON thama_rituals.id = roleThamaRituals.ritual_id " +
                "WHERE role_id = " + pRoleId + " " +
                "ORDER BY thama_rituals.level, thama_rituals.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int level = rs.getInt(3);
String description = rs.getString(4);
            Ritual intr = new Ritual(id, name, level, description);
            list.add(intr);
        }
        rs.close();
        return list;
    }*/

    private List getThaumaturgicalPathsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT thama_paths.id, thama_paths.name, roleThamaPaths.dots " +
                "FROM thama_paths INNER JOIN roleThamaPaths ON thama_paths.id = roleThamaPaths.path_id " +
                "WHERE roleThamaPaths.role_id = " + pRoleId +
                " ORDER BY roleThamaPaths.dots DESC, thama_paths.name";
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

    private List getOtherTraitsForRole(int pId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT otherTraits.id, otherTraits.name, roleOtherTraits.dots " +
                "FROM otherTraits INNER JOIN roleOtherTraits ON otherTraits.id = roleOtherTraits.trait_id " +
                "WHERE role_id = " + pId +
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

    /*private List getNecromanticRitualsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT necromantic_rituals.id, necromantic_rituals.name, necromantic_rituals.level, necromantic_rituals.description " +
                "FROM necromantic_rituals INNER JOIN roleNecromanticRituals ON necromantic_rituals.id = roleNecromanticRituals.ritual_id " +
                "WHERE role_id = " + pRoleId + " " +
                "ORDER BY necromantic_rituals.level, necromantic_rituals.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int level = rs.getInt(3);
String description = rs.getString(4);
            Ritual intr = new Ritual(id, name, level, description);
            list.add(intr);
        }
        rs.close();
        return list;
    }*/

    private List getNecromancyPathsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT necromancy_paths.id, necromancy_paths.name, roleNecromancyPaths.dots " +
                "FROM necromancy_paths INNER JOIN roleNecromancyPaths ON necromancy_paths.id = roleNecromancyPaths.path_id " +
                "WHERE roleNecromancyPaths.role_id = " + pRoleId +
                " ORDER BY roleNecromancyPaths.dots DESC, necromancy_paths.name";
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

    private List getAbilitiesForRole(int pRoleId, char pAbilityType) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT abilities.id, abilities.name, abilities.type, roleAbilities.dots, roleAbilities.specialisation, abilities.base_monthlyincome " +
                "FROM abilities INNER JOIN roleAbilities ON abilities.id = roleAbilities.ability_id " +
                "WHERE abilities.type='" + pAbilityType + "' AND role_id = " + pRoleId;

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

    private List getFlawsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT meritsNflaws.id, meritsNflaws.name, meritsNflaws.points, roleMeritsNflaws.note " +
                "FROM meritsNflaws INNER JOIN roleMeritsNflaws ON meritsNflaws.id = roleMeritsNflaws.mf_id " +
                "WHERE points < 0 AND role_id = " + pRoleId +
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

    private List getMeritsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT meritsNflaws.id, meritsNflaws.name, meritsNflaws.points, roleMeritsNflaws.note " +
                "FROM meritsNflaws INNER JOIN roleMeritsNflaws ON meritsNflaws.id = roleMeritsNflaws.mf_id " +
                "WHERE points >= 0 AND role_id = " + pRoleId +
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

    private List getDisciplinesForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT disciplines.id, disciplines.name AS disciplines_name, disciplines.retest_ability, " +
                "abilities.name AS abilities_name, abilities.type, roleDisciplines.dots, roleDisciplines.isOfClan " +
                "FROM (abilities RIGHT OUTER JOIN disciplines ON abilities.id = disciplines.retest_ability) INNER JOIN roleDisciplines ON disciplines.id = roleDisciplines.discipline_id " +
                "WHERE roleDisciplines.role_id = " + pRoleId +
                " ORDER BY roleDisciplines.isOfClan, roleDisciplines.dots DESC, disciplines.name";
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

    private List getDerangementsForRole(int pId) throws SQLException {
        ArrayList list = new ArrayList();
        String q = "SELECT derangement FROM roleDerangements WHERE role_id = " + pId + " AND isBeastTrait = false ORDER BY derangement";
        ResultSet rs = query(q);
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        rs.close();
        return list;
    }

    private List<String> getBeastTraitsForRole(int pId) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        String q = "SELECT derangement FROM roleDerangements WHERE role_id = " + pId + " AND isBeastTrait = true ORDER BY derangement";
        ResultSet rs = query(q);
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        rs.close();
        return list;
    }

    public List<TreeNode> getFamelyTrees(Clan pClan) throws SQLException, RemoteException {
        List<TreeNode> nodes = getFamelyTrees();
        if (DEBUG) System.out.println("RetrievalDBImpl.getFamelyTrees(877) all nodes = " + nodes.size());
        List<TreeNode> toReturn = new ArrayList<TreeNode>();
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode treeNode = nodes.get(i);
            if (treeNode.getData().getClan().getId() == pClan.getId()) {
                if (DEBUG) System.out.println("RetrievalDBImpl.getFamelyTrees(882) adding " + treeNode.getData());
                toReturn.add(treeNode);
            }
        }
        return toReturn;
    }

    public List<Clan> getRepresentedClans() throws SQLException {
        List<Clan> list = new ArrayList<Clan>();
        connect();
        ResultSet rs = query("SELECT DISTINCT roles.clan, clans.name, base_income, weaknesses " +
                             "FROM roles, clans " +
                             "WHERE roles.clan = clans.id " +
                             "ORDER BY clans.name");
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int baseIncome = rs.getInt(3);
            String weakness = rs.getString(4);
            Clan c = new Clan(id, name, baseIncome, weakness);
            list.add(c);
        }
        disconnect();
        return list;
    }

    public Vector search(String pText, boolean pSearchName, boolean pSearchPlayerName, boolean pSearchEmbraced,
                         boolean pSearchNature, boolean pSearchDemeanor, boolean pSearchPath, boolean pSearchDerangement) throws SQLException, RemoteException {
        if (pText.indexOf('%') >= 0) {
            throw new SQLException("Search-String may not contain '%'");
        }
        Vector result = new Vector();
        connect();
        String searchString = "";
        pText = quoteString(pText);
        if (pSearchName) {
            searchString = "roles.name LIKE '%" + pText + "%'";
        }
        if (pSearchPlayerName) {
            if (searchString.length() > 0) {
                searchString += " OR ";
            }
            searchString += "player_name LIKE '%" + pText + "%' OR players.name LIKE '%" + pText + "%'";
        }
        if (pSearchEmbraced) {
            if (searchString.length() > 0) {
                searchString += " OR ";
            }
            searchString += "embraced LIKE '%" + pText + "%'";
        }
        if (pSearchNature) {
            if (searchString.length() > 0) {
                searchString += " OR ";
            }
            searchString += "nature LIKE '%" + pText + "%'";
        }
        if (pSearchDemeanor) {
            if (searchString.length() > 0) {
                searchString += " OR ";
            }
            searchString += "demeanor LIKE '%" + pText + "%'";
        }
        if (pSearchPath) {
            if (searchString.length() > 0) {
                searchString += " OR ";
            }
            searchString += "path LIKE '%" + pText + "%'";
        }
        String q = "";
        ResultSet rs = null;
        if (searchString.length() > 0) {
            q = "SELECT roles.id, roles.name, roles.clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses,  roles.slp, roles.player_id, players.name\n" +
                    "FROM players RIGHT JOIN (domains INNER JOIN (clans INNER JOIN roles ON clans.id = roles.clan) ON domains.id = roles.domain) ON players.id = roles.player_id " +
                    "WHERE " + searchString +
                    " ORDER BY clans.name, roles.name";
//            q = "SELECT roles.id, roles.name, clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses, roles.slp " +
//                    "FROM roles, clans, domains " +
//                    "WHERE roles.clan = clans.id AND roles.domain = domains.id AND (" +
//                    searchString +
//                    ") ORDER BY clans.name, roles.name";
            rs = query(q);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int clanId = rs.getInt(3);
                String clanName = rs.getString(4);
                boolean goul = rs.getBoolean(5);
                //String playerName = rs.getString(6);
                int vitals = rs.getInt(7);
                int domainId = rs.getInt(8);
                String domainName = rs.getString(9);
                String weaknesses = rs.getString(10);
                boolean slp = rs.getBoolean(11);
                int player_id = rs.getInt(12);
                String player_name = rs.getString(13);
                Player player = null;
                if (player_name != null) {
                    player = new Player(player_id, player_name);
                }
                Clan c = new Clan(clanId, clanName);
                c.setWeaknesses(weaknesses);
                Role role = new Role(id, name, c);
                //role.setPlayerName(playerName);
                role.setPlayer(player);
                role.setGhoul(goul);
                role.setVitals(vitals);
                role.setSLP(slp);
                role.setDomain(new Domain(domainId, domainName));
                result.addElement(role);
            }
        }
        if (pSearchDerangement) {
            if (rs != null) {
                rs.close();
            }
            q = "SELECT roles.id, roles.name, roles.clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses,  roles.slp, roles.player_id, players.name\n" +
                    "FROM players RIGHT JOIN (domains INNER JOIN (clans INNER JOIN roles ON clans.id = roles.clan) ON domains.id = roles.domain) ON players.id = roles.player_id " +
                    "WHERE roles.id IN (SELECT role_id FROM roleDerangements WHERE derangement LIKE '%" + pText + "%')" +
                    " ORDER BY clans.name, roles.name";
//            q = "SELECT roles.id, roles.name, clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses " +
//                    "FROM roles, clans, domains " +
//                    "WHERE roles.clan = clans.id AND roles.domain = domains.id AND " +
//                    "roles.id IN (SELECT role_id FROM roleDerangements WHERE derangement LIKE '%" + pText + "%')" +
//                    " ORDER BY clans.name, roles.name";
            rs = query(q);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int clanId = rs.getInt(3);
                String clanName = rs.getString(4);
                boolean goul = rs.getBoolean(5);
                //String playerName = rs.getString(6);
                int vitals = rs.getInt(7);
                int domainId = rs.getInt(8);
                String domainName = rs.getString(9);
                String weaknesses = rs.getString(10);
                boolean slp = rs.getBoolean(11);
                int player_id = rs.getInt(12);
                String player_name = rs.getString(13);
                Player player = null;
                if (player_name != null) {
                    player = new Player(player_id, player_name);
                }
                Clan c = new Clan(clanId, clanName);
                c.setWeaknesses(weaknesses);
                Role role = new Role(id, name, c);
                //role.setPlayerName(playerName);
                role.setPlayer(player);
                role.setSLP(slp);
                role.setGhoul(goul);
                role.setVitals(vitals);
                role.setDomain(new Domain(domainId, domainName));
                result.addElement(role);
            }
        }
        disconnect();
        return result;
    }

    public Vector<String> getResourceTypes() throws SQLException {
        Vector<String> vector = new Vector<String>();
        connect();
        ResultSet rs = query("SELECT DISTINCT type FROM resources ORDER BY type");
        while (rs.next()) {
            vector.add(rs.getString(1));
        }
        disconnect();
        return vector;
    }

    public ArrayList<Vector<Resource>> getResourceByDomain(int pResourceId) throws SQLException, RemoteException {
        ArrayList<Vector<Resource>> vectors = new ArrayList<Vector<Resource>>();
        connect();
        ResultSet rs = query("SELECT resources.id, resources.name, description, income, " +
                             "(" +
                             "	SELECT SUM(percent) " +
                             "	FROM roleResources " +
                             "	WHERE resource_id = resources.id" +
                             ") AS percent_taken," +
                             "cost, domain, domains.name, resources.type " +
                             "FROM resources, domains " +
                             "WHERE resources.domain = domains.id AND resources.id=" + pResourceId +
                             " ORDER BY domains.id, resources.type, resources.name");
        Domain lastDomain = new Domain(-1, "");
        Vector<Resource> currentVector = null;
        while (rs.next()) {
            Resource res = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getFloat(6), rs.getString(9));
            int dId = rs.getInt(7);
            String dNS = rs.getString(8);
            Domain dom = new Domain(dId, dNS);
            res.setDomain(dom);
            if (dom.getId() != lastDomain.getId()) {
                currentVector = new Vector<Resource>();
                vectors.add(currentVector);
                lastDomain = dom;
            }
            currentVector.add(res);
        }
        disconnect();
        return vectors;
    }

    public Resource getResource(int pResourceId) throws SQLException, RemoteException {
        Resource r = null;
        connect();
        ResultSet rs = query("SELECT resources.id, resources.name, description, income, " +
                             "(" +
                             "	SELECT SUM(percent) " +
                             "	FROM roleResources " +
                             "	WHERE resource_id = resources.id" +
                             ") AS percent_taken," +
                             "cost, domain, domains.name, resources.type " +
                             "FROM resources, domains " +
                             "WHERE resources.domain = domains.id AND resources.id=" + pResourceId);


        if (rs.next()) {
            Resource res = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getFloat(6), rs.getString(9));
            int dId = rs.getInt(7);
            String dNS = rs.getString(8);
            Domain dom = new Domain(dId, dNS);
            res.setDomain(dom);
            r = res;
        }
        disconnect();
        return r;
    }

    public Vector<FightOrFlight> getFightOrFlights() throws SQLException {
        Vector<FightOrFlight> vector = new Vector<FightOrFlight>();
        connect();
        ResultSet rs = query("SELECT id, name, description " +
                             "FROM fightNflight " +
                             "ORDER BY name");
        while (rs.next()) {
            vector.add(new FightOrFlight(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        disconnect();
        return vector;
    }

    public HashMap<Integer, Integer> getOtherTraitsUsage() throws SQLException {
        HashMap<Integer, Integer> stats = new HashMap<Integer, Integer>();
        connect();
        String q = "SELECT Count(roleOtherTraits.role_id) AS UsedCount, roleOtherTraits.trait_id\n" +
                "FROM roles INNER JOIN roleOtherTraits ON roles.id = roleOtherTraits.role_id\n" +
                "GROUP BY roleOtherTraits.trait_id;";
        ResultSet rs = query(q);
        while (rs.next()) {
            int number = rs.getInt(1);
            int id = rs.getInt(2);
            stats.put(new Integer(id), new Integer(number));
        }
        disconnect();
        return stats;
    }

    public int getNumberOfPlayedRoles() throws SQLException {
        int number = 1;
//        String q = "SELECT Count([id]) AS Expr1\n" +
//                "FROM roles\n" +
//                "WHERE ((Len(player_name)>0))";
        String q = "SELECT Count([id]) AS Expr1\n" +
                "FROM roles\n" +
                "WHERE player_id IS NOT NULL";
        connect();
        ResultSet rs = query(q);
        if (rs.next()) {
            number = rs.getInt(1);
        }
        disconnect();
        return number;
    }

    public HashMap<Integer, Integer> getMeritsNflawsUsage() throws SQLException, RemoteException {
        HashMap<Integer, Integer> stats = new HashMap<Integer, Integer>();
        connect();
        String q = "SELECT Count(roleMeritsNflaws.role_id) AS UsedCount, roleMeritsNflaws.mf_id\n" +
                "FROM roles INNER JOIN roleMeritsNflaws ON roles.id = roleMeritsNflaws.role_id\n" +
                "GROUP BY roleMeritsNflaws.mf_id;";
        ResultSet rs = query(q);
        while (rs.next()) {
            int number = rs.getInt(1);
            int id = rs.getInt(2);
            stats.put(new Integer(id), new Integer(number));
        }
        disconnect();
        return stats;
    }

    public List<Experience> getDetailedExperienceListForRole(int pRoleId) throws SQLException {
        List<Experience> experiences = new ArrayList<Experience>();
        connect();
        ResultSet rs = query("SELECT ammount, date, reason, user_id, users.username, atTimeUserFullName " +
                             "FROM users RIGHT JOIN experience ON experience.user_id = users.id " +
                             "WHERE role_id = " + pRoleId + " ORDER BY date, ammount DESC");
        while (rs.next()) {
            int ammount = rs.getInt(1);
            Date date = rs.getDate(2);
            String reason = rs.getString(3);
            int uId = rs.getInt(4);
            String userName = rs.getString(5);
            String fullName = rs.getString(6);
            User user = new User();
            user.setId(uId);
            user.setUserName(userName);
            user.setFullName(fullName);
            experiences.add(new Experience(ammount, date, reason, user));
        }
        disconnect();
        return experiences;
    }

    public Vector<Player> getPlayingMembersList(Domain pDomain) throws SQLException, RemoteException {
        //HashMap<String, List<String>> members = new HashMap<String, List<String>>();
        Vector<Player> members = new Vector<Player>();
        String q = "SELECT players.id, players.name, players.address, players.phone, " +
                "players.email, roles.id, roles.name, roles.clan, clans.name, roles.goul, " +
                "roles.vitals, roles.domain, domains.name, " +
                "roles.slp, " +
                "(SELECT SUM(ammount) FROM playersExperience WHERE playersExperience.player_id = players.id) AS experience\n" +
                "FROM players RIGHT JOIN (domains INNER JOIN (clans INNER JOIN roles ON clans.id = roles.clan) ON domains.id = roles.domain) ON players.id = roles.player_id " +
                "WHERE roles.vitals = " + Vitals.NORMAL.getId() + " AND roles.player_id IS NOT NULL " +
                " ORDER BY players.name, players.id, clans.name, roles.name";
//        String q = "SELECT roles.player_name, roles.name\n" +
//                "FROM roles\n" +
//                "WHERE Len([player_name]) > 0 AND roles.vitals = " + Vitals.NORMAL.getId() +
//                " AND roles.domain = " + pDomain.getId() + "\n" +
//                "ORDER BY roles.player_name, roles.name;";
        connect();
        ResultSet rs = query(q);
        Player lastPlayer = new Player(-1, "");
        while (rs.next()) {
            int pId = rs.getInt(1);
            String pName = rs.getString(2);
            String address = rs.getString(3);
            String phone = rs.getString(4);
            String email = rs.getString(5);
            int roleId = rs.getInt(6);
            String roleName = rs.getString(7);
            int clanId = rs.getInt(8);
            String clanName = rs.getString(9);
            boolean ghoul = rs.getBoolean(10);
            Vitals vitals = Vitals.getVital(rs.getInt(11));
            int domainId = rs.getInt(12);
            String domainName = rs.getString(13);
            boolean slp = rs.getBoolean(14);
            int experience = rs.getInt(15);
            if (pId != lastPlayer.getId()) {
                lastPlayer = new Player(pId, pName, address, phone, email);
                lastPlayer.setRoles(new Vector<Role>());
                lastPlayer.setXP(experience);
                members.add(lastPlayer);
            }
            Role role = new Role(roleId, roleName, new Clan(clanId, clanName));
            role.setGhoul(ghoul);
            role.setVitals(vitals);
            role.setDomain(new Domain(domainId, domainName));
            role.setSLP(slp);
            lastPlayer.getRoles().add(role);
        }
        disconnect();
        return members;
    }

    public String getRoleName(int pRoleId) throws SQLException, RemoteException {
        String roleName = null;
        connect();
        roleName = getRoleNameNoConnect(pRoleId);
        disconnect();
        return roleName;
    }

    public Vector getPlayers() throws SQLException, RemoteException {
        Vector players = new Vector();
        String q = "SELECT id, name, address, phone, email " +
                "FROM players " +
                "ORDER BY name, id";
        connect();
        ResultSet rs = query(q);
        while (rs.next()) {
            Player player = new Player(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            players.add(player);
        }
        disconnect();
        return players;
    }

    public Player getPlayer(int pPlayerId) throws SQLException {
        connect();
        Player player = getPlayer(pPlayerId, true, true);
        disconnect();
        return player;
    }

    public Player getPlayer(int pPlayerId, boolean pGetPlayingRoles, boolean pGetExperienceList) throws SQLException {
        Player player = null;
        String q = "SELECT id, name, address, phone, email, (SELECT SUM(ammount) FROM playersExperience WHERE player_id= players.id) AS xp " +
                "FROM players " +
                "WHERE id = " + pPlayerId;
        ResultSet rs = query(q);
        if (rs.next()) {
            player = new Player(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            int xp = rs.getInt(6);
            player.setXP(xp);
        }
        rs.close();
        if (player != null) {
            //Gets the roles that this player is playing
            if (pGetPlayingRoles) {
                Vector<Role> roles = new Vector<Role>();
                q = "SELECT roles.id, roles.name, roles.clan, clans.name, roles.goul, roles.player_name, roles.vitals, roles.domain, domains.name, clans.weaknesses,  roles.slp, roles.player_id, players.name\n" +
                        "FROM players RIGHT JOIN (domains INNER JOIN (clans INNER JOIN roles ON clans.id = roles.clan) ON domains.id = roles.domain) ON players.id = roles.player_id " +
                        "WHERE player_id = " + pPlayerId +
                        " ORDER BY clans.name, roles.name";
                rs = query(q);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int clanId = rs.getInt(3);
                    String clanName = rs.getString(4);
                    boolean goul = rs.getBoolean(5);
                    //String playerName = rs.getString(6);
                    int vitals = rs.getInt(7);
                    int domainId = rs.getInt(8);
                    String domainName = rs.getString(9);
                    String weaknesses = rs.getString(10);
                    boolean slp = rs.getBoolean(11);
                    int playerId = rs.getInt(12);
                    String player_Name = rs.getString(13);
                    Player rPlayer = null;
                    if (player_Name != null) {
                        rPlayer = new Player(playerId, player_Name);
                    }
                    Clan c = new Clan(clanId, clanName);
                    c.setWeaknesses(weaknesses);
                    Role role = new Role(id, name, c);
                    //role.setPlayerName(playerName);
                    role.setPlayer(rPlayer);
                    role.setGhoul(goul);
                    role.setVitals(vitals);
                    role.setDomain(new Domain(domainId, domainName));
                    role.setSLP(slp);
                    roles.addElement(role);
                }
                rs.close();
                player.setRoles(roles);
            }

            if (pGetExperienceList) {
                //Get the full XP list
                q = "SELECT ammount, date, reason, user_id, users.username, atTimeUserFullName " +
                        "FROM users RIGHT JOIN playersExperience ON playersExperience.user_id = users.id " +
                        "WHERE player_id = " + pPlayerId +
                        " ORDER BY date";
                Vector<Experience> experiences = new Vector<Experience>();
                rs = query(q);
                while (rs.next()) {
                    int ammount = rs.getInt(1);
                    Date date = rs.getDate(2);
                    String reason = rs.getString(3);
                    int uId = rs.getInt(4);
                    String userName = rs.getString(5);
                    String fullName = rs.getString(6);
                    User user = new User();
                    user.setId(uId);
                    user.setUserName(userName);
                    user.setFullName(fullName);
                    experiences.add(new Experience(ammount, date, reason, user));
                }
                rs.close();
                player.setExperienceList(experiences);
            }
        }
        return player;
    }

    /**
     * Makes a tree of all the roleNames in the database and returns a list of TreeNode objects.
     * Where the TreeNodes in the list has no known sire.
     *
     * @return a List of TreeNode's
     * @throws java.sql.SQLException
     * @see TreeNode
     */
    public List<TreeNode> getFamelyTrees() throws SQLException {
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        connect();
        String q = "SELECT roles.id, roles.name, roles.sire, roles.goul, roles.vitals, roles.clan, clans.name " +
                "FROM roles, clans " +
                "WHERE roles.clan = clans.id " +
                "ORDER BY clans.name, roles.sire, roles.name";
        ResultSet rs = query(q);
        HashMap map = new HashMap();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int sire = rs.getInt(3);
            boolean goul = rs.getBoolean(4);
            int vital = rs.getInt(5);
            int clanId = rs.getInt(6);
            String clanName = rs.getString(7);
            //TreeNode sireNode = (TreeNode) map.get("" + sire);
            Role role = new Role(id, name, new Clan(clanId, clanName));
            role.setSire(new IntWithString(sire, sire + ""));
            //IntWithString intr = new IntWithString(sire, name);
            role.setGhoul(goul);
            Vitals vitals = Vitals.getVital(vital);
            role.setVitals(vitals);
            TreeNode node = new TreeNode(role);
            map.put("" + id, node);
        }
        disconnect();
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            TreeNode node = (TreeNode) it.next();
            TreeNode sire = (TreeNode) map.get(node.getData().getSire().toString());
            if (sire != null) {
                if (DEBUG) System.out.println("[RetrievalDB][getFamelyTrees][628] adding " + node + " to sire " + sire);
                sire.addChild(node);
            }
            else {
                if (DEBUG) System.out.println("[RetrievalDB][getFamelyTrees][631] adding " + node + " on top");
                list.add(node);
            }
        }
        return list;
    }

    public List getGroups() throws SQLException {
        ArrayList list = new ArrayList();
        connect();
        String q = "SELECT groups.id AS groups_id, groups.name AS groups_name, groups.date, groups.description, groups.type, roles.id AS roles_id, roles.name AS roles_name, roles.goul, clans.id AS clans_id, clans.name AS clans_name " +
                "FROM (clans INNER JOIN roles ON clans.id = roles.clan) INNER JOIN (groups INNER JOIN groupRoles ON groups.id = groupRoles.group_id) ON roles.id = groupRoles.role_id " +
                "ORDER BY groups.date, groups.name, roles.name";
        ResultSet rs = query(q);
        while (rs.next()) {

        }
        disconnect();
        return list;
    }

    public static void main(String[] args) throws SQLException, RemoteException {
        RetrievalDBImpl db = new RetrievalDBImpl();
        List nodes = db.getFamelyTrees();
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode treeNode = (TreeNode) nodes.get(i);
            printNode(treeNode, "");
        }
    }

    private static void printNode(TreeNode pNode, String pTabs) {
        System.out.println(pTabs + pNode);
        List children = pNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            TreeNode treeNode = (TreeNode) children.get(i);
            printNode(treeNode, pTabs + "\t");
        }
    }

    public Vector getNonMortalProfessions() throws SQLException {
        Vector v = new Vector();
        connect();
        String q = "SELECT id, name, monthly_income FROM professions WHERE mortal = false ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.add(new Profession(rs.getInt(1), rs.getString(2), rs.getInt(3), false));
        }
        disconnect();
        return v;
    }

    public Vector getMortalProfessions() throws SQLException {
        Vector v = new Vector();
        connect();
        String q = "SELECT id, name, monthly_income FROM professions WHERE mortal = true ORDER BY name";
        ResultSet rs = query(q);
        while (rs.next()) {
            v.add(new Profession(rs.getInt(1), rs.getString(2), rs.getInt(3), true));
        }
        disconnect();
        return v;
    }

    public Vector getResources() throws SQLException {
        Vector v = new Vector();
        connect();
        ResultSet rs = query("SELECT resources.id, resources.name, description, income, " +
                             "(" +
                             "	SELECT SUM(percent) " +
                             "	FROM roleResources " +
                             "	WHERE resource_id = resources.id" +
                             ") AS percent_taken," +
                             "cost, domain, domains.name, resources.type " +
                             "FROM resources, domains " +
                             "WHERE resources.domain = domains.id " +
                             "ORDER BY resources.type, resources.name");
        while (rs.next()) {
            Resource res = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getFloat(6), rs.getString(9));
            if (DEBUG) System.out.println("[RetrievalDB][getResources][739] " + res + " percent: " + res.getPercent());
            int dId = rs.getInt(7);
            String dNS = rs.getString(8);
            res.setDomain(new Domain(dId, dNS));
            v.addElement(res);
        }
        disconnect();
        return v;
    }

    public List getInfluencesForResource(Resource pResource) throws SQLException {
        return getInfluencesForResource(pResource, true);
    }

    public List getInfluencesForResource(Resource pResource, boolean pConnect) throws SQLException {
        List li = new ArrayList();
        if (pConnect)
            connect();
        String q = "SELECT influences.id, influences.name, resourceInfluences.dots " +
                "FROM influences INNER JOIN resourceInfluences ON influences.id = resourceInfluences.influence_id " +
                "WHERE resourceInfluences.resource_id =" + pResource.getId() +
                " ORDER BY influences.name";
        ResultSet rs = query(q);
        while (rs.next()) {
            Influence inf = new Influence(rs.getInt(1), rs.getString(2), rs.getInt(3));
            li.add(inf);
        }
        rs.close();
        if (pConnect)
            disconnect();
        return li;
    }

    public Vector getInfluenceNames() throws SQLException {
        Vector v = new Vector();
        connect();
        ResultSet rs = query("SELECT id, name FROM influences ORDER BY name");
        while (rs.next()) {
            v.add(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return v;
    }

    public Vector getDomains() throws SQLException {
        Vector domains = new Vector();
        connect();
        ResultSet rs = query("SELECT id, name, history FROM domains ORDER BY name");
        while (rs.next()) {
            domains.add(new Domain(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        disconnect();
        return domains;
    }

    public Vector<Resource> getResources(int pDomainId, String pType) throws SQLException {
        Vector<Resource> v = new Vector<Resource>();
        connect();
        ResultSet rs = query("SELECT resources.id, resources.name, description, income, " +
                             "(" +
                             "	SELECT SUM(percent) " +
                             "	FROM roleResources " +
                             "	WHERE resource_id = resources.id" +
                             ") AS percent_taken," +
                             "cost, domain, domains.name, resources.type " +
                             "FROM resources, domains " +
                             "WHERE resources.domain = domains.id AND resources.domain = " + pDomainId +
                             " AND resources.type = '" + quoteString(pType) + "'" +
                             " ORDER BY resources.name");
        while (rs.next()) {
            Resource res = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getFloat(6), rs.getString(9));
            if (DEBUG) System.out.println("[RetrievalDB][getResources][739] " + res + " percent: " + res.getPercent());
            int dId = rs.getInt(7);
            String dNS = rs.getString(8);
            res.setDomain(new Domain(dId, dNS));
            v.addElement(res);
        }
        disconnect();
        return v;
    }

    public ArrayList<Vector<Resource>> getResourcesByDomain() throws SQLException {
        ArrayList<Vector<Resource>> vectors = new ArrayList<Vector<Resource>>();
        connect();
        ResultSet rs = query("SELECT resources.id, resources.name, description, income, " +
                             "(" +
                             "	SELECT SUM(percent) " +
                             "	FROM roleResources " +
                             "	WHERE resource_id = resources.id" +
                             ") AS percent_taken," +
                             "cost, domain, domains.name, resources.type " +
                             "FROM resources, domains " +
                             "WHERE resources.domain = domains.id " +
                             "ORDER BY domains.id, resources.type, resources.name");
        Domain lastDomain = new Domain(-1, "");
        Vector<Resource> currentVector = null;
        while (rs.next()) {
            Resource res = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getFloat(6), rs.getString(9));
            int dId = rs.getInt(7);
            String dNS = rs.getString(8);
            Domain dom = new Domain(dId, dNS);
            res.setDomain(dom);
            if (dom.getId() != lastDomain.getId()) {
                currentVector = new Vector<Resource>();
                vectors.add(currentVector);
                lastDomain = dom;
            }
            currentVector.add(res);
        }
        disconnect();
        return vectors;
    }

    public String[] getBackgroundAndWill(int pRoleId) throws SQLException {
        String back = "";
        String will = "";
        connect();
        ResultSet rs = query("SELECT background, will FROM roles WHERE id = " + pRoleId);
        if (rs.next()) {
            back = rs.getString(1);
            will = rs.getString(2);
        }
        disconnect();
        return new String[]{back, will};
    }
}
