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

package net.joinedminds.masserr.db.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.command.OCommandRequestText;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.query.nativ.ONativeQuery;
import com.orientechnologies.orient.core.query.nativ.ONativeSynchQuery;
import com.orientechnologies.orient.core.sql.query.OSQLQuery;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.model.*;
import net.joinedminds.masserr.model.mgm.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * Database for manipulating base elements like Roles, Players etc.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class ManipulationDbImpl implements ManipulationDB {

    private Provider<OObjectDatabaseTx> db;

    @Inject
    public ManipulationDbImpl(Provider<OObjectDatabaseTx> db) {
        this.db = db;
    }

    @Override
    public Ability newAbility() {
        return db.get().newInstance(Ability.class);
    }

    @Override
    public Ability saveAbility(Ability ability) {
        return db.get().save(ability);
    }

    @Override
    public List<Ability> getAbilities() {
        return db.get().query(new OSQLSynchQuery<Ability>("SELECT * FROM Ability ORDER BY type ASC, name ASC"));
    }

    @Override
    public Role newRole() {
        return db.get().newInstance(Role.class);
    }

    @Override
    public Role saveRole(Role role) {
        return db.get().save(role);
    }

    @Override
    public Ritual newRitual() {
        return db.get().newInstance(Ritual.class);
    }

    @Override
    public Ritual saveRitual(Ritual ritual) {
        return db.get().save(ritual);
    }

    @Override
    public Resource newResource() {
        return db.get().newInstance(Resource.class);
    }

    @Override
    public Resource saveResource(Resource resource) {
        return db.get().save(resource);
    }

    @Override
    public Clan newClan() {
        return db.get().newInstance(Clan.class);
    }

    @Override
    public Clan saveClan(Clan clan) {
        return db.get().save(clan);
    }

    @Override
    public Discipline newDiscipline() {
        return db.get().newInstance(Discipline.class);
    }

    @Override
    public Discipline saveDiscipline(Discipline discipline) {
        return db.get().save(discipline);
    }

    @Override
    public FightOrFlight newFightOrFlight() {
        return db.get().newInstance(FightOrFlight.class);
    }

    @Override
    public FightOrFlight saveFightOrFlight(FightOrFlight fightOrFlight) {
        return db.get().save(fightOrFlight);
    }

    @Override
    public Generation newGeneration() {
        return db.get().newInstance(Generation.class);
    }

    @Override
    public Generation saveGeneration(Generation generation) {
        return db.get().save(generation);
    }

    @Override
    public MeritOrFlaw newMeritOrFlaw() {
        return db.get().newInstance(MeritOrFlaw.class);
    }

    @Override
    public MeritOrFlaw saveMeritOrFlaw(MeritOrFlaw meritOrFlaw) {
        return db.get().save(meritOrFlaw);
    }

    @Override
    public Path newPath() {
        return db.get().newInstance(Path.class);
    }

    @Override
    public Path savePath(Path path) {
        return db.get().save(path);
    }

    @Override
    public OtherTrait newOtherTrait() {
        return db.get().newInstance(OtherTrait.class);
    }

    @Override
    public OtherTrait saveOtherTrait(OtherTrait otherTrait) {
        return db.get().save(otherTrait);
    }

    @Override
    public RitualType newRitualType() {
        return db.get().newInstance(RitualType.class);
    }

    @Override
    public RitualType saveRitualType(RitualType type) {
        return db.get().save(type);
    }

    @Override
    public void insertExperience(Role pRole, int pAmmount, String pReason) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void insertExperience(List<RolesGroup> pGroupsList, List<Role> pRolesList, int pAmount, String pReason) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateBackgroundAndWill(Role pRole, String pBackground, String pWill) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addClanDisciplines(Clan pClan, Discipline[] pDisciplines) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeClanDisciplines(Clan pClan, Discipline[] pDisciplines) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateDiscipline(Discipline pDiscipline) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addDiscipline(Discipline pDiscipline) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateThaumaturgicalPath(IntWithString pPath) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IntWithString addThaumaturgicalPath(String pName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateNecromancyPath(IntWithString pPath) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IntWithString addNecromancyPath(String pName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateMeritOrflaw(MeritOrFlaw pToUpdate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MeritOrFlaw addMeritOrflaw(String pName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateOtherTrait(IntWithString pToUpdate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IntWithString addOtherTrait(String pName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updatePlayer(Player pPlayer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addPlayer(Player pPlayer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void insertPlayersExperience(List<Player> pPlayers, int amount, String pReason) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEmpty() {
        return db.get().countClass(Ability.class) <= 0;
    }

    @Override
    public Ability getAbility(String id) {
        return db.get().load(new ORecordId(id));
    }
}
