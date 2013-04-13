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

package net.joinedminds.masserr.modules;

import com.google.inject.Inject;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.model.*;
import net.joinedminds.masserr.ui.NavItem;
import net.joinedminds.masserr.ui.dto.NameId;
import net.joinedminds.masserr.ui.dto.SubmitResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.bind.JavaScriptMethod;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.joinedminds.masserr.Functions.isEmpty;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class RolesModule implements NavItem {

    private Logger logger = Logger.getLogger(RolesModule.class.getName());

    private ManipulationDB manipulationDB;
    private AdminDB adminDB;
    private AdminModule adminModule;

    @Inject
    public RolesModule(ManipulationDB manipulationDB, AdminDB adminDB, AdminModule adminModule) {
        this.manipulationDB = manipulationDB;
        this.adminDB = adminDB;
        this.adminModule = adminModule;
    }

    @JavaScriptMethod
    public String theTest(String what) {
        return "Hello " + what;
    }

    @JavaScriptMethod
    public SubmitResponse<Role> submitQuickRole(JSONObject jsonRole) {
        try {
            Role role = manipulationDB.newRole();
            role.setDomain(manipulationDB.getDomain(jsonRole.getString("domain")));
            role.setName(jsonRole.getString("name"));
            role.setGeneration(manipulationDB.getGeneration(jsonRole.getInt("generation")));
            Calendar c = calcEmbraced(jsonRole);
            role.setEmbraced(c.getTime());
            role.setClan(manipulationDB.getClan(jsonRole.getString("clan")));
            role.setSire(manipulationDB.getRole(jsonRole.getString("sire")));
            role.setNpc(true);
            logger.info("Saving role");
            role = manipulationDB.saveRole(role);
            return new SubmitResponse<>(role);
        } catch (Exception e) {
            logger.log(Level.WARNING, "When saving quick role", e);
            return new SubmitResponse<>(null, e.getMessage());
        }
    }

    private Calendar calcEmbraced(JSONObject jsonRole) {
        String embr = jsonRole.getString("embraced");
        String[] parts = embr.split("-");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(parts[0]));
        if (parts.length > 1) {
            c.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);
        }
        if (parts.length > 2) {
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));
        }
        return c;
    }

    public List<Role> getRoles() {
        return manipulationDB.getRoles();
    }

    public Role getNewRole() {
        logger.info("New Role!!");
        Role role = new Role();
        Morality morality = adminDB.getConfig().getDefaultMorality();
        if (morality != null) {
            role.setMorality(new DottedType<>(morality, 1));
            role.setVirtues(new Virtues(morality, 1, 1, 1));
        }
        return role;
    }

    @JavaScriptMethod
    public SubmitResponse<String> saveRole(JSONObject formObject) {
        Role role;
        String id = formObject.optString("id");
        if (id == null || id.isEmpty()) {
            role = manipulationDB.newRole();
        } else {
            role = manipulationDB.getRole(id);
        }
        if (role == null) {
            return SubmitResponse.idNotFound(id);
        }
        String message = setRoleBasics(role, formObject);
        if (message != null) {
            return new SubmitResponse<>(null, false, message);
        }
        role = manipulationDB.saveRole(role);
        return new SubmitResponse<>(role.getId());
    }

    private String setRoleBasics(Role role, JSONObject formObject) {
        role.setNpc(formObject.has("npc"));
        role.setDomain(Domain.idRef(formObject.getString("domain")));
        if (isEmpty(formObject.optString("name"))) {
            return Messages.QuickRoles_Msg_NoName_Msg();
        }
        role.setName(formObject.getString("name"));
        role.setPlayer(Player.idRef(formObject.optString("player")));
        role.setGeneration(manipulationDB.getGeneration(formObject.getInt("generation")));
        Calendar c = calcEmbraced(formObject);
        role.setEmbraced(c.getTime());
        role.setClan(Clan.idRef(formObject.getString("clan")));
        role.setSire(Role.idRef(formObject.optString("sire")));
        if (isEmpty(formObject.optString("nature"))) {
            return "Missing Nature";
        }
        if (isEmpty(formObject.optString("demeanor"))) {
            return "Missing Demeanor";
        }
        role.setNature(Archetype.idRef(formObject.getString("nature")));
        role.setDemeanor(Archetype.idRef(formObject.getString("demeanor")));
        JSONObject jsonMorality = formObject.optJSONObject("morality");
        if (jsonMorality != null && jsonMorality.optJSONObject("type") != null &&
                !isEmpty(jsonMorality.getJSONObject("type").optString("id"))) {
            DottedType<Morality> morality = new DottedType<>(
                    Morality.idRef(jsonMorality.getJSONObject("type").getString("id")), jsonMorality.getInt("dots"));
            role.setMorality(morality);
        } else {
            return "Missing Morality";
        }
        JSONObject jsonVirtues = formObject.optJSONObject("virtues");
        if (jsonVirtues != null) {
            Virtues virtues = new Virtues(
                    Virtues.Adherence.valueOf(jsonVirtues.getString("adherence")),
                    jsonVirtues.getInt("adherenceDots"),
                    Virtues.Resistance.valueOf(jsonVirtues.getString("resistance")),
                    jsonVirtues.getInt("resistanceDots"),
                    jsonVirtues.getInt("courageDots"));
            role.setVirtues(virtues);
        } else {
            return "Missing Virtues";
        }
        setDisciplines(role, formObject);
        role.setExtraHealthLevels(formObject.getInt("extraHealthLevels"));
        role.setSufferesOfInjury(formObject.has("suffersOfInjury"));
        role.setFightForm(FightOrFlight.idRef(formObject.optString("fightForm")));
        role.setFlightForm(FightOrFlight.idRef(formObject.optString("flightForm")));
        role.setQuote(Functions.emptyIfNull(formObject.optString("quote")));
        role.setVitals(Vitals.valueOf(formObject.getString("vitals")));
        return null;
    }

    private void setDisciplines(Role role, JSONObject formObject) {
        JSONArray jDisciplines = formObject.getJSONArray("discipline");
        List<DottedType<Discipline>> disciplines = new LinkedList<>();
        for (int i = 0; i < jDisciplines.size(); i++) {
            JSONObject d = jDisciplines.getJSONObject(i);
            if (!isEmpty(d.optString("id")) && d.has("dots") && d.getInt("dots") > 0) {
                disciplines.add(new DottedType<>(
                        Discipline.idRef(d.getString("id")),
                        d.getInt("dots")));
            }
        }
        role.setDisciplines(disciplines);
    }

    public List<Generation> getGenerations() {
        return manipulationDB.getGenerations(false);
    }

    public List<Morality> getMoralityPaths() {
        return manipulationDB.getMoralityPaths();
    }

    @JavaScriptMethod
    public Morality getMoralityPath(String id) {
        return manipulationDB.getMoralityPath(id);
    }

    public List<Clan> getClans() {
        return manipulationDB.getClans();
    }

    public List<Discipline> getDisciplines() {
        return manipulationDB.getDisciplines();
    }

    public List<FightOrFlight> getFightOrFlights() {
        return manipulationDB.getFightOrFlights();
    }

    public List<Domain> getDomains() {
        return manipulationDB.getDomains();
    }

    public List<Path> getPaths(Path.Type type) {
        return manipulationDB.getPaths(type);
    }

    public List<RitualType> getRitualTypes() {
        return manipulationDB.getRitualTypes();
    }

    @JavaScriptMethod
    public List<Ritual> getRituals(String typeId) {
        return manipulationDB.getRituals(typeId);
    }

    @JavaScriptMethod
    public Ritual getRitual(String id) {
        return manipulationDB.getRitual(id);
    }

    public List<Ability> getAbilities(Ability.Type type) {
        return manipulationDB.getAbilities(type);
    }

    @JavaScriptMethod
    public List<Discipline> getClanDisciplines(String clanId) {
        Clan clan = manipulationDB.getClan(clanId);
        if (clan != null) {
            return clan.getClanDisciplines();
        } else {
            return Collections.emptyList();
        }
    }

    @JavaScriptMethod
    public List<NameId> getRolesOfClan(String clanId) {
        List<NameId> list = new LinkedList<>();
        List<Role> roles = manipulationDB.getRolesOfClan(clanId);
        for (Role role : roles) {
            list.add(new NameId(role.getName(), role.getId()));
        }
        return list;
    }

    @Override
    public String getNavDisplay() {
        return Messages.nav_Roles();
    }

    public List<Archetype> getArchetypes() {
        return manipulationDB.getArchetypes();
    }

    public List<OtherTrait> getOtherTraits() {
        return manipulationDB.getOtherTraits();
    }

    public List<Player> getPlayers() {
        //TODO list players in the campaigns that the user has access to.
        return adminDB.getPlayers(adminDB.getCampaigns().get(0));
    }

    @JavaScriptMethod
    public SubmitResponse<Player> savePlayer(JSONObject jsonPlayer) {
        return adminModule.savePlayer(jsonPlayer);
    }

    public List<MeritOrFlaw> getMerits(MeritOrFlaw.Type type) {
        return manipulationDB.getMerits(type);
    }

    public List<MeritOrFlaw> getFlaws(MeritOrFlaw.Type type) {
        return manipulationDB.getFlaws(type);
    }
}
