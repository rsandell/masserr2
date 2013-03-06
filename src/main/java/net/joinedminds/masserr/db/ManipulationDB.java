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

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:43:34
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface ManipulationDB extends BasicDB {

    Ability newAbility();

    Ability saveAbility(Ability ability);

    List<Ability> getAbilities();

    Role newRole();

    Role saveRole(Role pRole);

    Ritual newRitual();

    Ritual saveRitual(Ritual pRitual);

    Resource newResource();

    Resource saveResource(Resource pResource);

    Clan newClan();

    Clan saveClan(Clan clan);

    Discipline newDiscipline();

    Discipline saveDiscipline(Discipline discipline);

    FightOrFlight newFightOrFlight();

    FightOrFlight saveFightOrFlight(FightOrFlight fightOrFlight);

    Generation newGeneration();

    Generation saveGeneration(Generation generation);

    MeritOrFlaw newMeritOrFlaw();

    MeritOrFlaw saveMeritOrFlaw(MeritOrFlaw meritOrFlaw);

    Path newPath();

    Path savePath(Path path);

    OtherTrait newOtherTrait();

    OtherTrait saveOtherTrait(OtherTrait otherTrait);

    RitualType newRitualType();

    RitualType saveRitualType(RitualType type);


    void insertExperience(Role pRole, int pAmmount, String pReason);

    void insertExperience(List<RolesGroup> pGroupsList, List<Role> pRolesList, int pAmount, String pReason);

    void updateBackgroundAndWill(Role pRole, String pBackground, String pWill);

    void addClanDisciplines(Clan pClan, Discipline[] pDisciplines);

    void removeClanDisciplines(Clan pClan, Discipline[] pDisciplines);

    void updateDiscipline(Discipline pDiscipline);

    void addDiscipline(Discipline pDiscipline);

    void updateThaumaturgicalPath(IntWithString pPath);

    IntWithString addThaumaturgicalPath(String pName);

    void updateNecromancyPath(IntWithString pPath);

    IntWithString addNecromancyPath(String pName);

    void updateMeritOrflaw(MeritOrFlaw pToUpdate);

    MeritOrFlaw addMeritOrflaw(String pName);

    void updateOtherTrait(IntWithString pToUpdate);

    IntWithString addOtherTrait(String pName);

    void updatePlayer(Player pPlayer);

    void addPlayer(Player pPlayer);

    void insertPlayersExperience(List<Player> pPlayers, int amount, String pReason);

    boolean isEmpty();

    Ability getAbility(String id);

    List<OtherTrait> getOtherTraits();

    List<Discipline> getDisciplines();

    OtherTrait getOtherTrait(String id);

    Discipline getDiscipline(String id);

    List<Path> getPaths();

    Path getPath(String id);
}
