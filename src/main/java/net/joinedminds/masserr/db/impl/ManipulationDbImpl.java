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

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.WriteResult;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.model.*;
import net.joinedminds.masserr.util.RoleClanComparator;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Database for manipulating base elements like Roles, Players etc.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class ManipulationDbImpl extends BasicDbImpl implements ManipulationDB {

    public static final RoleClanComparator ROLE_CLAN_COMPARATOR = new RoleClanComparator();
    private LoadingCache<Integer, Generation> generationLoadingCache;

    @Inject
    public ManipulationDbImpl(Provider<Datastore> db) {
        super(db);
        generationLoadingCache = CacheBuilder.newBuilder().refreshAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<Integer, Generation>() {
            @Override
            public Generation load(Integer id) throws Exception {
                return _getGeneration(id);
            }
        });
    }

    @Override
    public Ability newAbility() {
        return new Ability();
    }

    @Override
    public Ability saveAbility(Ability ability) {
        return save(ability);
    }

    @Override
    public Ability getAbility(String id) {
        return get(Ability.class, id);
    }

    @Override
    public List<Ability> getAbilities() {
        return db.get().find(Ability.class).order("type,name").asList();
    }

    @Override
    public List<OtherTrait> getOtherTraits() {
        return db.get().find(OtherTrait.class).order("name").asList();
    }

    @Override
    public OtherTrait getOtherTrait(String id) {
        return get(OtherTrait.class, id);
    }

    @Override
    public OtherTrait newOtherTrait() {
        return new OtherTrait();
    }

    @Override
    public OtherTrait saveOtherTrait(OtherTrait otherTrait) {
        return save(otherTrait);
    }

    @Override
    public List<Discipline> getDisciplines() {
        return db.get().find(Discipline.class).order("name").asList();
    }

    @Override
    public Discipline getDiscipline(String id) {
        return get(Discipline.class, id);
    }

    @Override
    public Discipline newDiscipline() {
        return new Discipline();
    }

    @Override
    public Discipline saveDiscipline(Discipline discipline) {
        return save(discipline);
    }

    @Override
    public List<Path> getPaths() {
        return db.get().find(Path.class).order("type,name").asList();
    }

    @Override
    public List<Path> getPaths(Path.Type type) {
        return db.get().find(Path.class, "type", type).order("name").asList();
    }

    @Override
    public Path getPath(String id) {
        return get(Path.class, id);
    }

    @Override
    public Path newPath() {
        return new Path();
    }

    @Override
    public Path savePath(Path path) {
        return save(path);
    }

    @Override
    public List<Generation> getGenerations() {
        return db.get().find(Generation.class).order("-generation").asList();
    }

    @Override
    public List<Generation> getGenerations(boolean ghoulGenerations) {
        return db.get().find(Generation.class, "ghoulLevel", ghoulGenerations).order("-generation").asList();
    }

    protected Generation _getGeneration(int generation) {
        return db.get().get(Generation.class, generation);
    }

    @Override
    public Generation getGeneration(int generation) {
        try {
            return generationLoadingCache.get(generation);
        } catch (ExecutionException e) {
            return _getGeneration(generation);
        }
    }

    @Override
    public Generation newGeneration() {
        return new Generation();
    }

    @Override
    public Generation saveGeneration(Generation generation) {
        return save(generation);
    }

    @Override
    public List<Clan> getClans() {
        return db.get().find(Clan.class).order("name").asList();
    }

    @Override
    public Clan getClan(String id) {
        if (id != null && !id.isEmpty()) {
            return get(Clan.class, id);
        } else {
            return null;
        }
    }

    @Override
    public Clan newClan() {
        return new Clan();
    }

    @Override
    public Clan saveClan(Clan clan) {
        return save(clan);
    }

    @Override
    public List<Domain> getDomains() {
        return db.get().find(Domain.class).order("name").asList();
    }

    @Override
    public Domain newDomain() {
        return new Domain();
    }

    @Override
    public Domain saveDomain(Domain d) {
        return save(d);
    }

    @Override
    public Domain getDomain(String id) {
        return get(Domain.class, id);
    }

    @Override
    public List<RitualType> getRitualTypes() {
        return db.get().find(RitualType.class).order("name").asList();
    }

    @Override
    public RitualType newRitualType() {
        return new RitualType();
    }

    @Override
    public RitualType saveRitualType(RitualType type) {
        return save(type);
    }

    @Override
    public List<Ritual> getRituals(String typeId) {
        //DBRef ref = db.get().getMapper().keyToRef(new Key<RitualType>(RitualType.class, new ObjectId(typeId)));
        return db.get().find(Ritual.class, "ritualType", RitualType.idRef(typeId)).order("level").order("name").asList();
    }

    @Override
    public Ritual newRitual() {
        return new Ritual();
    }

    @Override
    public Ritual saveRitual(Ritual ritual) {
        return save(ritual);
    }

    @Override
    public List<Ability> getAbilities(Ability.Type type) {
        return db.get().find(Ability.class, "type", type).order("name").asList();
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new LinkedList<>();
        for (Role role : db.get().find(Role.class).order("name")) {
            roles.add(role);
        }
        Collections.sort(roles, ROLE_CLAN_COMPARATOR);
        return roles;
    }

    @Override
    public List<Role> getRolesOfClan(String clanId) {
        return db.get().find(Role.class, "clan", Clan.idRef(clanId)).order("name").asList();
    }

    @Override
    public boolean deleteDiscipline(String id) {
        WriteResult result = db.get().delete(Discipline.class, new ObjectId(id));
        return result.getN() == 1;
    }

    @Override
    public List<MeritOrFlaw> getMeritOrFlaws() {
        return db.get().find(MeritOrFlaw.class).order("type,points,name").asList();
    }

    @Override
    public MeritOrFlaw getMeritOrFlaw(String id) {
        return get(MeritOrFlaw.class, id);
    }

    @Override
    public List<MeritOrFlaw> getMerits(MeritOrFlaw.Type type) {
        Query<MeritOrFlaw> q = db.get().find(MeritOrFlaw.class);
        q.criteria("type").equal(type).and().criteria("points").greaterThanOrEq(0);
        return q.order("name,points").asList();
    }

    @Override
    public List<MeritOrFlaw> getFlaws(MeritOrFlaw.Type type) {
        Query<MeritOrFlaw> q = db.get().find(MeritOrFlaw.class);
        q.criteria("type").equal(type).and().criteria("points").lessThan(0);
        return q.order("name,-points").asList();
    }

    @Override
    public Archetype newArchetype() {
        return new Archetype();
    }

    @Override
    public Archetype saveArchetype(Archetype archetype) {
        return save(archetype);
    }

    @Override
    public List<Archetype> getArchetypes() {
        return db.get().find(Archetype.class).order("name").asList();
    }

    @Override
    public Morality newMorality() {
        return new Morality();
    }

    @Override
    public Morality saveMorality(Morality morality) {
        return save(morality);
    }

    @Override
    public List<Morality> getMoralityPaths() {
        return db.get().find(Morality.class).order("name").asList();
    }

    @Override
    public Morality getMoralityPath(String id) {
        if (id == null) {
            return null;
        }
        return db.get().get(Morality.class, new ObjectId(id));
    }

    @Override
    public Ritual getRitual(String id) {
        return get(Ritual.class, id);
    }

    @Override
    public List<Discipline> getDisciplinesWithRetestAbility(String[] disciplineIds) {
        Set<ObjectId> ids = new TreeSet<>();
        for (String disciplineId : disciplineIds) {
            ObjectId objectId = Functions.toObjectId(disciplineId);
            if (objectId != null) {
                ids.add(objectId);
            }
        }
        if (ids.size() > 0) {
            Query<Discipline> query = db.get().createQuery(Discipline.class);
            query.field("objectId").in(ids);
            query.field("retestAbility").exists();
            return query.asList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Discipline> getDisciplines(String[] disciplineIds) {
        Set<ObjectId> ids = new TreeSet<>();
        for (String disciplineId : disciplineIds) {
            ObjectId objectId = Functions.toObjectId(disciplineId);
            if (objectId != null) {
                ids.add(objectId);
            }
        }
        if (ids.size() > 0) {
            Query<Discipline> query = db.get().createQuery(Discipline.class);
            query.field("objectId").in(ids);
            return query.asList();
        }
        return Collections.emptyList();
    }

    @Override
    public Role getRole(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return get(Role.class, id);
    }

    @Override
    public Role newRole() {
        return new Role();
    }

    @Override
    public Role saveRole(Role role) {
        return save(role);
    }

    @Override
    public Resource newResource() {
        return new Resource();
    }

    @Override
    public Resource saveResource(Resource resource) {
        return save(resource);
    }

    @Override
    public FightOrFlight newFightOrFlight() {
        return new FightOrFlight();
    }

    @Override
    public FightOrFlight saveFightOrFlight(FightOrFlight fightOrFlight) {
        return save(fightOrFlight);
    }

    @Override
    public List<FightOrFlight> getFightOrFlights() {
        return db.get().find(FightOrFlight.class).order("name").asList();
    }

    @Override
    public MeritOrFlaw newMeritOrFlaw() {
        return new MeritOrFlaw();
    }

    @Override
    public MeritOrFlaw saveMeritOrFlaw(MeritOrFlaw meritOrFlaw) {
        return save(meritOrFlaw);
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
        return db.get().find(Ability.class).countAll() <= 0;
    }
}
