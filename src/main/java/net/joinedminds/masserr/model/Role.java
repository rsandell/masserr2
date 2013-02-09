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


import com.google.common.collect.ImmutableList;

import javax.persistence.Id;
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
public class Role implements NamedIdentifiable {
    public static final int GHOUL_GENERATION = 17;

    @Id
    private String id;
    private String name;
    private Generation generation;
    private Role sire;
    private Date embraced;
    private Clan clan;
    private String nature;
    private String demeanor;
    private int courage;
    private int conscience;
    private int selfControl;
    private int willpower;
    private int pathDots;
    private String path;
    private int physical;
    private int social;
    private int mental;
    private int extraHealthLevels;
    private boolean sufferesOfInjury;
    private List<DottedType<Discipline>> disciplines;
    private List<DottedType<Path>> thaumaturgicalPaths;
    private List<DottedType<Path>> necromancyPaths;
    //private List mNecromanticRituals;
    private List<DottedType<Ability>> physicalAbilities;
    private List<DottedType<Ability>> socialAbilities;
    private List<DottedType<Ability>> mentalAbilities;
    private List<MeritOrFlaw> merits;
    private List<MeritOrFlaw> flaws;
    private List<String> derangements;
    private List<DottedType<OtherTrait>> otherTraits;
    private boolean ghoul = false;
    private int extraMonthlyIncome;
    private List<Profession> professions;
    private List<Resource> resources;
    private List<BankAccount> bankAccounts;
    private List<DottedType<Influence>> influences;
    private int experience;
    private Vitals vitals;
    private Domain domain;
    private List<Ritual> rituals;
    private String selfControlOrInstinct = "Self-Control";
    private String conscienceOrConviction = "Conciense";
    private String thaumaType = "";
    private String necromancyType = "";
    private List<Plot> plots;
    private String quote;
    private boolean npc = false;
    private List<String> beastTraits;
    private FightOrFlight fightForm;
    private FightOrFlight flightForm;
    private Player player = null;

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
    }

    public Role(String pName, Generation pGeneration, Role pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury) {
        name = pName;
        generation = pGeneration;
        sire = pSire;
        embraced = pEmbraced;
        clan = pClan;
        nature = pNature;
        demeanor = pDemeanor;
        courage = pCourage;
        conscience = pConcience;
        selfControl = pSelfControl;
        willpower = pWillpower;
        pathDots = pPathDots;
        path = pPath;
        physical = pPhysical;
        social = pSocial;
        mental = pMental;
        extraHealthLevels = pExtraHealthLevels;
        sufferesOfInjury = pSufferesOfInjury;
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
    }

    public Role(String pName, Generation pGeneration, Role pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, boolean pGhoul) {
        name = pName;
        generation = pGeneration;
        sire = pSire;
        embraced = pEmbraced;
        clan = pClan;
        nature = pNature;
        demeanor = pDemeanor;
        courage = pCourage;
        conscience = pConcience;
        selfControl = pSelfControl;
        willpower = pWillpower;
        pathDots = pPathDots;
        path = pPath;
        physical = pPhysical;
        social = pSocial;
        mental = pMental;
        extraHealthLevels = pExtraHealthLevels;
        sufferesOfInjury = pSufferesOfInjury;
        ghoul = pGhoul;
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
    }

    public Role(String pName, Generation pGeneration, Role pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, boolean pGhoul, int pExtraMonthlyIncome, String pConcienseORconviction, String pSelfControlORinstinct) {
        name = pName;
        generation = pGeneration;
        sire = pSire;
        embraced = pEmbraced;
        clan = pClan;
        nature = pNature;
        demeanor = pDemeanor;
        courage = pCourage;
        conscience = pConcience;
        selfControl = pSelfControl;
        willpower = pWillpower;
        pathDots = pPathDots;
        path = pPath;
        physical = pPhysical;
        social = pSocial;
        mental = pMental;
        extraHealthLevels = pExtraHealthLevels;
        sufferesOfInjury = pSufferesOfInjury;
        ghoul = pGhoul;
        extraMonthlyIncome = pExtraMonthlyIncome;
        conscienceOrConviction = pConcienseORconviction;
        selfControlOrInstinct = pSelfControlORinstinct;
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
    }

    public Role(String pName, Generation pGeneration, Role pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, String pConcienseORconviction, String pSelfControlORinstinct) {
        name = pName;
        generation = pGeneration;
        sire = pSire;
        embraced = pEmbraced;
        clan = pClan;
        nature = pNature;
        demeanor = pDemeanor;
        courage = pCourage;
        conscience = pConcience;
        selfControl = pSelfControl;
        willpower = pWillpower;
        pathDots = pPathDots;
        path = pPath;
        physical = pPhysical;
        social = pSocial;
        mental = pMental;
        extraHealthLevels = pExtraHealthLevels;
        sufferesOfInjury = pSufferesOfInjury;
        conscienceOrConviction = pConcienseORconviction;
        selfControlOrInstinct = pSelfControlORinstinct;
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
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> pResources) {
        resources = pResources;
    }

    @Override
    public String getId() {
        return id;
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
        }
        else {
            return "";
        }
    }

    /*public void setPlayerName(String pPlayerName) {
        playerName = pPlayerName;
    }*/

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

    public String getEmbracedString() {
        if (embraced != null) {
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            return format.format(embraced);
        }
        else {
            return null;
        }
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

    public String getNature() {
        return nature;
    }

    public void setNature(String pNature) {
        nature = pNature;
    }

    public String getDemeanor() {
        return demeanor;
    }

    public void setDemeanor(String pDemeanor) {
        demeanor = pDemeanor;
    }

    public int getCourage() {
        return courage;
    }

    public void setCourage(int pCourage) {
        courage = pCourage;
    }

    public int getConscience() {
        return conscience;
    }

    public void setConscience(int pConcience) {
        conscience = pConcience;
    }

    public int getSelfControl() {
        return selfControl;
    }

    public void setSelfControl(int pSelfControl) {
        selfControl = pSelfControl;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int pWillpower) {
        willpower = pWillpower;
    }

    public int getPathDots() {
        return pathDots;
    }

    public void setPathDots(int pPathDots) {
        pathDots = pPathDots;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String pPath) {
        path = pPath;
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

    /*public List getThaumaturgicalRituals() {
        return mThaumaturgicalRituals;
    }

    public void setThaumaturgicalRituals(List pThaumaturgicalRituals) {
        mThaumaturgicalRituals = pThaumaturgicalRituals;
    }*/

    public List<DottedType<Path>> getNecromancyPaths() {
        return necromancyPaths;
    }

    public void setNecromancyPaths(List<DottedType<Path>> pNecromancyPaths) {
        necromancyPaths = pNecromancyPaths;
    }

    /*public List getNecromanticRituals() {
        return mNecromanticRituals;
    }

    public void setNecromanticRituals(List pNecromanticRituals) {
        mNecromanticRituals = pNecromanticRituals;
    }*/

    public List<Ritual> getRituals() {
        return rituals;
    }

    public void setRituals(List<Ritual> pRituals) {
        rituals = pRituals;
    }

    public void setPhysicalAbilities(List<DottedType<Ability>> pAbilities) {
        physicalAbilities = pAbilities;
    }

    public List<DottedType<Ability>> getPhysicalAbilities() {
        return physicalAbilities;
    }

    public List<DottedType<Ability>> getSocialAbilities() {
        return socialAbilities;
    }

    public void setSocialAbilities(List<DottedType<Ability>> pSocialAbilities) {
        socialAbilities = pSocialAbilities;
    }

    public List<DottedType<Ability>> getMentalAbilities() {
        return mentalAbilities;
    }

    public void setMentalAbilities(List<DottedType<Ability>> pMentalAbilities) {
        mentalAbilities = pMentalAbilities;
    }

    public List<MeritOrFlaw> getMerits() {
        return merits;
    }

    public void setMerits(List<MeritOrFlaw> pMerits) {
        merits = pMerits;
    }

    public List<MeritOrFlaw> getFlaws() {
        return flaws;
    }

    public void setFlaws(List<MeritOrFlaw> pFlaws) {
        flaws = pFlaws;
    }

    public List<String> getDerangements() {
        return derangements;
    }

    public void setDerangements(List<String> pDerangements) {
        derangements = pDerangements;
    }

    public List<DottedType<OtherTrait>> getOtherTraits() {
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
        for (Ritual ritual : getRituals()) {
            set.add(ritual.getRitualType());
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
     * AppPreferences#getReadXPFrom()
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

    public String getSelfControlOrInstinct() {
        return selfControlOrInstinct;
    }

    public void setSelfControlOrInstinct(String pSelfControlORinstinct) {
        selfControlOrInstinct = pSelfControlORinstinct;
    }

    public String getConscienceOrConviction() {
        return conscienceOrConviction;
    }

    public void setConscienceOrConviction(String pConcienseORconviction) {
        conscienceOrConviction = pConcienseORconviction;
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
}
