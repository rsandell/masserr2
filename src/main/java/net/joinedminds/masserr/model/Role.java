/*
 * The MIT License
 *
 * Copyright (c) 2004-2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.model;


import com.github.jmkgreen.morphia.annotations.*;
import com.google.common.collect.ImmutableList;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.model.security.ACL;
import net.joinedminds.masserr.model.security.Principal;
import net.joinedminds.masserr.security.AccessControlled;
import net.joinedminds.masserr.security.Permission;
import net.joinedminds.masserr.security.PermissionGroup;
import net.joinedminds.masserr.ui.NavItem;
import org.bson.types.ObjectId;

import java.text.DateFormat;
import java.util.*;

import static java.util.Collections.emptyList;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-15 23:02:20
 *
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
@Entity
public class Role implements NamedIdentifiable, AccessControlled<Role>, NavItem {
    public static final int GHOUL_GENERATION = 17;

    @Id
    private ObjectId objectId;
    private String name;
    @Reference
    private Generation generation;
    @Indexed
    @Reference
    private Role sire;
    private Date embraced;
    @Indexed
    @Reference
    private Clan clan;
    @Reference
    private Archetype nature;
    @Reference
    private Archetype demeanor;
    @Embedded
    private Virtues virtues;
    private int willpower;
    @Embedded
    private DottedType<Morality> morality;
    private int physical;
    private int social;
    private int mental;
    private int extraHealthLevels;
    private boolean sufferesOfInjury;
    @Embedded
    private List<DottedType<Discipline>> disciplines;
    @Embedded
    private List<DottedType<Path>> thaumaturgicalPaths;
    @Embedded
    private List<DottedType<Path>> necromancyPaths;
    //private List mNecromanticRituals;
    @Embedded
    private List<DottedNotedType<Ability>> physicalAbilities;
    @Embedded
    private List<DottedNotedType<Ability>> socialAbilities;
    @Embedded
    private List<DottedNotedType<Ability>> mentalAbilities;
    @Embedded
    private List<DottedType<OtherTrait>> otherTraits;
    @Embedded
    private List<NotedType<MeritOrFlaw>> merits;
    @Embedded
    private List<NotedType<MeritOrFlaw>> flaws;
    private List<String> derangements;
    @Indexed
    private boolean ghoul = false;
    private int extraMonthlyIncome;
    @Reference
    private List<Profession> professions;
    @Reference
    private List<Resource> resources;
    @Reference
    private List<BankAccount> bankAccounts;
    @Embedded
    private List<DottedType<Influence>> influences;
    private int experience;
    private Vitals vitals;
    @Indexed
    @Reference
    private Domain domain;
    @Reference
    private List<Ritual> rituals;
    private String thaumaType = "";
    private String necromancyType = "";
    @Reference
    private List<Plot> plots;
    private String quote;
    @Indexed
    private boolean npc = false;
    private List<String> beastTraits;
    @Reference
    private FightOrFlight fightForm;
    @Reference
    private FightOrFlight flightForm;
    @Indexed
    @Reference
    private Player player = null;
    @Embedded
    private ACL<Role> acl;

    public Role() {
    }

    public Role(String pName, Clan pClan) {
        name = pName;
        clan = pClan;
        disciplines = emptyList();
        thaumaturgicalPaths = emptyList();
        necromancyPaths = emptyList();
        physicalAbilities = emptyList();
        socialAbilities = emptyList();
        mentalAbilities = emptyList();
        merits = emptyList();
        flaws = emptyList();
        derangements = emptyList();
        otherTraits = emptyList();
        rituals = new LinkedList<>();
        acl = new ACL<>(this);
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> pResources) {
        resources = pResources;
    }

    @Override
    public String getId() {
        return Functions.toString(objectId);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getPlayerName() {
        if (player != null) {
            return player.getName();
        } else {
            return "";
        }
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation pGeneration) {
        generation = pGeneration;
    }

    public Role getSire() {
        return sire;
    }

    public void setSire(Role pSire) {
        sire = pSire;
    }

    public Date getEmbraced() {
        return embraced;
    }

    public void setEmbraced(Date pEmbraced) {
        embraced = pEmbraced;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan pClan) {
        clan = pClan;
    }

    public Archetype getNature() {
        return nature;
    }

    public void setNature(Archetype pNature) {
        nature = pNature;
    }

    public Archetype getDemeanor() {
        return demeanor;
    }

    public void setDemeanor(Archetype pDemeanor) {
        demeanor = pDemeanor;
    }

    public Virtues getVirtues() {
        return virtues;
    }

    public void setVirtues(Virtues virtues) {
        this.virtues = virtues;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int pWillpower) {
        willpower = pWillpower;
    }

    public DottedType<Morality> getMorality() {
        return morality;
    }

    public void setMorality(DottedType<Morality> morality) {
        this.morality = morality;
    }

    public int getPhysical() {
        return physical;
    }

    public void setPhysical(int pPhysical) {
        physical = pPhysical;
    }

    public int getSocial() {
        return social;
    }

    public void setSocial(int pSocial) {
        social = pSocial;
    }

    public int getMental() {
        return mental;
    }

    public void setMental(int pMental) {
        mental = pMental;
    }

    public int getExtraHealthLevels() {
        return extraHealthLevels;
    }

    public void setExtraHealthLevels(int pExtraHealthLevels) {
        extraHealthLevels = pExtraHealthLevels;
    }

    public boolean isSufferesOfInjury() {
        return sufferesOfInjury;
    }

    public void setSufferesOfInjury(boolean pSufferesOfInjury) {
        sufferesOfInjury = pSufferesOfInjury;
    }

    public String toString() {
        return name;
    }

    public List getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<DottedType<Discipline>> pDisciplines) {
        disciplines = pDisciplines;
    }

    public List<DottedType<Path>> getThaumaturgicalPaths() {
        return thaumaturgicalPaths;
    }

    public void setThaumaturgicalPaths(List<DottedType<Path>> pThaumaturgicalPaths) {
        thaumaturgicalPaths = pThaumaturgicalPaths;
    }

    public List<DottedType<Path>> getNecromancyPaths() {
        return necromancyPaths;
    }

    public void setNecromancyPaths(List<DottedType<Path>> pNecromancyPaths) {
        necromancyPaths = pNecromancyPaths;
    }

    public List<Ritual> getRituals() {
        return rituals;
    }

    public void setRituals(List<Ritual> pRituals) {
        rituals = pRituals;
    }

    public void setPhysicalAbilities(List<DottedNotedType<Ability>> pAbilities) {
        physicalAbilities = pAbilities;
    }

    public List<DottedNotedType<Ability>> getPhysicalAbilities() {
        if (physicalAbilities == null) {
            return Collections.emptyList();
        }
        return physicalAbilities;
    }

    public List<DottedNotedType<Ability>> getSocialAbilities() {
        if (socialAbilities == null) {
            return Collections.emptyList();
        }
        return socialAbilities;
    }

    public void setSocialAbilities(List<DottedNotedType<Ability>> pSocialAbilities) {
        socialAbilities = pSocialAbilities;
    }

    public List<DottedNotedType<Ability>> getMentalAbilities() {
        if (mentalAbilities == null) {
            return Collections.emptyList();
        }
        return mentalAbilities;
    }

    public void setMentalAbilities(List<DottedNotedType<Ability>> pMentalAbilities) {
        mentalAbilities = pMentalAbilities;
    }

    public List<NotedType<MeritOrFlaw>> getMerits() {
        return merits;
    }

    public void setMerits(List<NotedType<MeritOrFlaw>> merits) {
        this.merits = merits;
    }

    public List<NotedType<MeritOrFlaw>> getFlaws() {
        return flaws;
    }

    public void setFlaws(List<NotedType<MeritOrFlaw>> flaws) {
        this.flaws = flaws;
    }

    public List<String> getDerangements() {
        return derangements;
    }

    public void setDerangements(List<String> pDerangements) {
        derangements = pDerangements;
    }

    public List<DottedType<OtherTrait>> getOtherTraits() {
        if (otherTraits == null) {
            return Collections.emptyList();
        }
        return otherTraits;
    }

    public void setOtherTraits(List<DottedType<OtherTrait>> pOtherTraits) {
        otherTraits = pOtherTraits;
    }

    public boolean hasThaumaturgy() {
        if (thaumaturgicalPaths != null) {
            if (thaumaturgicalPaths.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasNecromancy() {
        if (necromancyPaths != null) {
            if (necromancyPaths.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRituals() {
        return rituals != null && rituals.size() > 0;
    }

    public Set<RitualType> getRitualTypes() {
        Set<RitualType> set = new HashSet<>();
        List<Ritual> ritualList = getRituals();
        if (ritualList != null) {
            for (Ritual ritual : ritualList) {
                set.add(ritual.getRitualType());
            }
        }
        return set;
    }

    public static int getAge(Date pEmbraced) {
        Calendar cal = Calendar.getInstance();
        Calendar emCal = Calendar.getInstance();
        emCal.setTime(pEmbraced);
        cal.add(Calendar.YEAR, -emCal.get(Calendar.YEAR));
        cal.add(Calendar.MONTH, -emCal.get(Calendar.MONTH));
        cal.add(Calendar.DAY_OF_MONTH, -emCal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR);
    }

    public int getAge() {
        return getAge(embraced);
    }

    public boolean isGhoul() {
        return ghoul;
    }

    public void setGhoul(boolean pGhoul) {
        ghoul = pGhoul;
    }

    /*public int getBaseMoneyForAge() {
        return mBaseMoneyForAge;
    }

    public void setBaseMoneyForAge(int pBaseMoneyForAge) {
        mBaseMoneyForAge = pBaseMoneyForAge;
    }*/

    public static int calculateBaseMonthlyIncome(List<DottedType<Ability>> pAbilities, List<Profession> pProfessions) {
        int baseIncome = 0;
        for (DottedType<Ability> ability : pAbilities) {
            baseIncome += (ability.getType().getBaseMonthlyIncome() * ability.getDots());
        }
        for (Profession profession : pProfessions) {
            if (profession.isMask()) {
                baseIncome += (profession.getMonthlyIncome() * Profession.MASK_INCOME_FACTOR);
            } else {
                baseIncome += profession.getMonthlyIncome();
            }
        }
        return baseIncome;
    }

    public static long calculateTotalMonthlyIncome(List<DottedType<Ability>> pAbilities, List<Profession> pProfessions, List<Resource> pResources) {
        long baseIncome = calculateBaseMonthlyIncome(pAbilities, pProfessions);
        long incomeResources = 0;
        for (Resource resource : pResources) {
            incomeResources += (resource.getIncome() * resource.getPercent());
        }
        return baseIncome + incomeResources;
    }

    public List<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Profession> pProfessions) {
        professions = pProfessions;
    }

    public List<DottedType<Influence>> compileInfluences() {
        List<DottedType<Influence>> list = new LinkedList<>();
        HashMap<String, DottedType<Influence>> map = new HashMap<>();
        if (resources != null) {
            for (Resource resource : resources) {
                List<DottedType<Influence>> influences = resource.getInfluences();
                for (DottedType<Influence> influence : influences) {
                    DottedType<Influence> stored = map.get(influence.getType().getId());
                    if (stored == null) {
                        stored = new DottedType<Influence>(new Influence(influence.getType().getName(), influence.getType().getNotes()), influence.getDots());
                        map.put(influence.getType().getId(), stored);
                        list.add(stored);
                    } else {
                        stored.setDots(stored.getDots() + influence.getDots());
                    }
                }
            }
        }
        List<DottedType<Influence>> inf = getInfluences();
        if (inf != null) {
            for (DottedType<Influence> influence : inf) {
                DottedType<Influence> stored = map.get(influence.getType().getId());
                if (stored == null) {
                    stored = new DottedType<Influence>(new Influence(influence.getType().getName(), influence.getType().getNotes()), influence.getDots());
                    map.put(influence.getType().getId(), stored);
                    list.add(stored);
                } else {
                    stored.setDots(stored.getDots() + influence.getDots());
                }
            }
        }
        return list;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> pBankAccounts) {
        bankAccounts = pBankAccounts;
    }

    public int getExtraMonthlyIncome() {
        return extraMonthlyIncome;
    }

    public void setExtraMonthlyIncome(int pExtraMonthlyIncome) {
        extraMonthlyIncome = pExtraMonthlyIncome;
    }

    public List<DottedType<Influence>> getInfluences() {
        return influences;
    }

    public void setInfluences(List<DottedType<Influence>> pInfluences) {
        influences = pInfluences;
    }

    public void addInfluence(DottedType<Influence> pInf) {
        if (getInfluences() == null) {
            setInfluences(new LinkedList<DottedType<Influence>>());
        }
        getInfluences().add(pInf);
    }

    public int baseMonthlyIncome() {
        List<DottedType<Ability>> abilities = combinedAbilities();
        return calculateBaseMonthlyIncome(abilities, professions);
    }

    private List<DottedType<Ability>> combinedAbilities() {
        ImmutableList.Builder<DottedType<Ability>> builder = ImmutableList.builder();
        return builder.
                addAll(getPhysicalAbilities()).
                addAll(getSocialAbilities()).
                addAll(getMentalAbilities()).build();
    }

    public long totalMonthlyIncome() {
        List<DottedType<Ability>> abilities = combinedAbilities();
        long base = calculateTotalMonthlyIncome(abilities, getProfessions(), getResources());
        return base + extraMonthlyIncome;
    }

    public BankAccount getIncomeBankAccount() {
        List<BankAccount> accounts = getBankAccounts();
        if (accounts != null && !accounts.isEmpty()) {
            for (BankAccount account : accounts) {
                if (account.isIncome()) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * if AppPrefferences.getReadXPFrom returns AppPrefferences.XP_ROLE this role's Experience is returned
     * else if player != null the players Experience is returned
     * else 0 is returned
     *
     * @return the Experience for the Role or the Player
     *         AppPreferences#getReadXPFrom()
     * @see #getPlayer()
     * @see Player
     */
    public int getExperience() {
        //if (AppPreferences.getReadXPFrom() == AppPreferences.XP_ROLE) {
        return experience;
        //}
        //else if (player != null) {
        //    return player.getXP();
        //}
        //else {
        //    return 0;
        //}
    }

    /**
     * Sets this Roles experience.
     *
     * @param pExperience the total experience.
     */
    public void setExperience(int pExperience) {
        experience = pExperience;
    }

    public Vitals getVitals() {
        return vitals;
    }

    public void setVitals(Vitals pVitals) {
        vitals = pVitals;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain pDomain) {
        domain = pDomain;
    }

    public String getThaumaType() {
        return thaumaType;
    }

    public void setThaumaType(String pThaumaType) {
        thaumaType = pThaumaType;
    }

    public String getNecromancyType() {
        return necromancyType;
    }

    public void setNecromancyType(String pNecromancyType) {
        necromancyType = pNecromancyType;
    }

    public List<Plot> getPlots() {
        return plots;
    }

    public void setPlots(List<Plot> pPlots) {
        plots = pPlots;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String pQuote) {
        quote = pQuote;
    }

    public boolean isNpc() {
        return npc;
    }

    public void setNpc(boolean pNpc) {
        npc = pNpc;
    }

    public List<String> getBeastTraits() {
        return beastTraits;
    }

    public void setBeastTraits(List<String> pBeastTraits) {
        beastTraits = pBeastTraits;
    }

    public FightOrFlight getFightForm() {
        return fightForm;
    }

    public void setFightForm(FightOrFlight pFightForm) {
        fightForm = pFightForm;
    }

    public FightOrFlight getFlightForm() {
        return flightForm;
    }

    public void setFlightForm(FightOrFlight pFlightForm) {
        flightForm = pFlightForm;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player pPlayer) {
        player = pPlayer;
    }

    public static Role idRef(String stringId) {
        ObjectId id = Functions.toObjectId(stringId);
        if (id != null) {
            Role r = new Role();
            r.objectId = id;
            return r;
        } else {
            return null;
        }
    }

    @Override
    public String getNavDisplay() {
        return getName();
    }

    @Override
    public boolean hasPermission(Principal principal, Permission permission) {
        if (getACL().hasPermission(principal, permission)) {
            return true;
        } else {
            Domain d = getDomain();
            return d != null && d.hasPermission(principal, permission);
        }
    }

    @Override
    public synchronized ACL<Role> getACL() {
        if (acl == null) {
            acl = new ACL<>(this);
        }
        return acl;
    }

    public static final PermissionGroup PGROUP_GENERAL = new PermissionGroup(Role.class, Messages._Role_Permission_General());
    public static final Permission ADMINISTER = new Permission(PGROUP_GENERAL, Messages._Role_Permission_General_Administer(), Messages._Role_Permission_General_Administer_Description(), Domain.ADMINISTER);
    public static final Permission EDIT = new Permission(PGROUP_GENERAL, Messages._Role_Permission_General_Edit(), Domain.ROLE_CREATE);
    public static final Permission READ = new Permission(PGROUP_GENERAL, Messages._Role_Permission_General_Read(), EDIT);

}
