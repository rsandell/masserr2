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
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.model.*;
import net.joinedminds.masserr.ui.NavItem;
import net.joinedminds.masserr.ui.dto.NameId;
import net.joinedminds.masserr.ui.dto.SubmitResponse;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.bind.JavaScriptMethod;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            String embr = jsonRole.getString("embraced");
            String[] parts = embr.split("-");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(parts[0]));
            if(parts.length > 1) {
                c.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);
            }
            if(parts.length > 2) {
                c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));
            }
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

    public List<Role> getRoles() {
        return manipulationDB.getRoles();
    }

    public Role getNewRole() {
        logger.info("New Role!!");
        Role role = new Role();
        Morality morality = adminDB.getConfig().getDefaultMorality();
        if(morality != null) {
            role.setMorality(new DottedType<>(morality, 1));
            role.setVirtues(new Virtues(morality, 1, 1, 1));
        }
        return role;
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

    public List<Player> getPlayers() {
        //TODO list players in the campaigns that the user has access to.
        return adminDB.getPlayers(adminDB.getCampaigns().get(0));
    }

    @JavaScriptMethod
    public SubmitResponse<Player> savePlayer(JSONObject jsonPlayer) {
        return adminModule.savePlayer(jsonPlayer);
    }
}
