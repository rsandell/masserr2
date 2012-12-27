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


import javax.persistence.Id;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

import static java.util.Collections.emptyList;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-15 23:02:20
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class Role implements Serializable {
    public static final int GHOUL_GENERATION = 17;

    @Id
    private String id;
    private String name;
    private String playerName;
    private Generation generation;
    private IntWithString sire;
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
    private List<Discipline> disciplines;
    private List<Path> thaumaturgicalPaths;
    private List<Path> necromancyPaths;
    //private List mNecromanticRituals;
    private List<Ability> physicalAbilities;
    private List<Ability> socialAbilities;
    private List<Ability> mentalAbilities;
    private List<MeritOrFlaw> merits;
    private List<MeritOrFlaw> flaws;
    private List derangements;
    private List otherTraits;
    private boolean ghoul = false;
    private int extraMonthlyIncome;
    private List<Profession> professions;
    private List<Resource> resources;
    private List<BankAccount> bankAccounts;
    private List<Influence> influences;
    private int experience;
    private int vitals = 1;
    private Domain domain;
    private List<Ritual> rituals;
    private String selfControlOrInstinct = "Self-Control";
    private String conscienceOrConviction = "Conciense";
    private String thaumaType = "";
    private String necromancyType = "";
    private List<Plot> plots;
    private String quote;
    private boolean mSLP = false;
    private List<String> beastTraits;
    private FightOrFlight fightForm;
    private FightOrFlight flightForm;
    private Player player = null;

    public Role() {
    }

    public Role(String pId, String pName, Clan pClan) {
        id = pId;
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
        rituals = new LinkedList<Ritual>();
    }

    public Role(String pId, String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury) {
        id = pId;
        name = pName;
        playerName = pPlayerName;
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
        rituals = new LinkedList<Ritual>();
    }

    public Role(String pId, String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, boolean pGhoul) {
        id = pId;
        name = pName;
        playerName = pPlayerName;
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
        rituals = new LinkedList<Ritual>();
    }

    public Role(String pId, String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, boolean pGhoul, int pExtraMonthlyIncome, String pConcienseORconviction, String pSelfControlORinstinct) {
        id = pId;
        name = pName;
        playerName = pPlayerName;
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
        rituals = new LinkedList<Ritual>();
    }

    public Role(String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, String pConcienseORconviction, String pSelfControlORinstinct) {
        name = pName;
        playerName = pPlayerName;
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
        rituals = new LinkedList<Ritual>();
    }

    public List getResources() {
        return resources;
    }

    public void setResources(List pResources) {
        resources = pResources;
    }

    public String getId() {
        return id;
    }

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

    public IntWithString getSire() {
        return sire;
    }

    public void setSire(IntWithString pSire) {
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

    public void setDisciplines(List pDisciplines) {
        disciplines = pDisciplines;
    }

    public List getThaumaturgicalPaths() {
        return thaumaturgicalPaths;
    }

    public void setThaumaturgicalPaths(List pThaumaturgicalPaths) {
        thaumaturgicalPaths = pThaumaturgicalPaths;
    }

    /*public List getThaumaturgicalRituals() {
        return mThaumaturgicalRituals;
    }

    public void setThaumaturgicalRituals(List pThaumaturgicalRituals) {
        mThaumaturgicalRituals = pThaumaturgicalRituals;
    }*/

    public List getNecromancyPaths() {
        return necromancyPaths;
    }

    public void setNecromancyPaths(List pNecromancyPaths) {
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

    public void setPhysicalAbilities(List pAbilities) {
        physicalAbilities = pAbilities;
    }

    public List getPhysicalAbilities() {
        return physicalAbilities;
    }

    public List getSocialAbilities() {
        return socialAbilities;
    }

    public void setSocialAbilities(List pSocialAbilities) {
        socialAbilities = pSocialAbilities;
    }

    public List getMentalAbilities() {
        return mentalAbilities;
    }

    public void setMentalAbilities(List pMentalAbilities) {
        mentalAbilities = pMentalAbilities;
    }

    public List getMerits() {
        return merits;
    }

    public void setMerits(List pMerits) {
        merits = pMerits;
    }

    public List getFlaws() {
        return flaws;
    }

    public void setFlaws(List pFlaws) {
        flaws = pFlaws;
    }

    public List getDerangements() {
        return derangements;
    }

    public void setDerangements(List pDerangements) {
        derangements = pDerangements;
    }

    public List getOtherTraits() {
        return otherTraits;
    }

    public void setOtherTraits(List pOtherTraits) {
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
        if (rituals != null) {
            return rituals.size() > 0;
        }
        return false;
    }

    public List<RitualType> getRitualTypes() {
        ArrayList<RitualType> list = new ArrayList<RitualType>();
        RitualType lastType = null;
        for (int i = 0; i < rituals.size(); i++) {
            Ritual ritual = rituals.get(i);
            if (lastType == null) {
                lastType = ritual.getRitualType();
                list.add(lastType);
            }
            else if (lastType.getId() != ritual.getRitualType().getId()) {
                lastType = ritual.getRitualType();
                list.add(lastType);
            }
        }
        return list;
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

    public static int calculateBaseMonthlyIncome(List pAbilities, List pProfessions) {

        int baseIncome = 0;
        for (int i = 0; i < pAbilities.size(); i++) {
            Ability ability = (Ability) pAbilities.get(i);
            baseIncome += (ability.getBaseMonthlyIncome() * ability.getDots());
        }
        for (int i = 0; i < pProfessions.size(); i++) {
            Profession profession = (Profession) pProfessions.get(i);
            if (profession.isMask()) {
                baseIncome += (profession.getMonthlyIncome() * Profession.MASK_INCOME_FACTOR);
            }
            else {
                baseIncome += profession.getMonthlyIncome();
            }
        }
        return baseIncome;
    }

    public static long calculateTotalMonthlyIncome(List pAbilities, List pProfessions, List pResources) {
        long baseIncome = calculateBaseMonthlyIncome(pAbilities, pProfessions);
        long incomeResources = 0;
        for (int i = 0; i < pResources.size(); i++) {
            Resource resource = (Resource) pResources.get(i);
            incomeResources += (resource.getIncome() * resource.getPercent());
        }
        long totalIncome = baseIncome + incomeResources;
        return totalIncome;
    }

    public List getProfessions() {
        return professions;
    }

    public void setProfessions(List pProfessions) {
        professions = pProfessions;
    }

    public List compileInfluences() {
        ArrayList list = new ArrayList();
        HashMap map = new HashMap();
        if (resources != null) {
            for (int i = 0; i < resources.size(); i++) {
                Resource resource = (Resource) resources.get(i);
                List influences = resource.getInfluences();
                for (int j = 0; j < influences.size(); j++) {
                    Influence influence = (Influence) influences.get(j);
                    Influence stored = (Influence) map.get(influence.getId() + "");
                    if (stored == null) {
                        stored = new Influence(influence.getId(), influence.getName(), influence.getDots(), influence.getNotes());
                        map.put(influence.getId() + "", stored);
                        list.add(stored);
                    }
                    else {
                        stored.setDots(stored.getDots() + influence.getDots());
                    }
                }
            }
        }
        if (influences != null) {
            for (int i = 0; i < influences.size(); i++) {
                Influence influence = (Influence) influences.get(i);
                Influence stored = (Influence) map.get(influence.getId() + "");
                if (stored == null) {
                    stored = new Influence(influence.getId(), influence.getName(), influence.getDots(), influence.getNotes());
                    map.put(influence.getId() + "", stored);
                    list.add(stored);
                }
                else {
                    stored.setDots(stored.getDots() + influence.getDots());
                }
            }
        }
        return list;
    }

    public List getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List pBankAccounts) {
        bankAccounts = pBankAccounts;
    }

    public int getExtraMonthlyIncome() {
        return extraMonthlyIncome;
    }

    public void setExtraMonthlyIncome(int pExtraMonthlyIncome) {
        extraMonthlyIncome = pExtraMonthlyIncome;
    }

    public List<Influence> getInfluences() {
        return influences;
    }

    public void setInfluences(List<Influence> pInfluences) {
        influences = pInfluences;
    }

    public void addInfluence(Influence pInf) {
        if (influences == null) {
            influences = new Vector();
        }
        influences.add(pInf);
    }

    public int baseMonthlyIncome() {
        List abilities = new ArrayList();
        for (int i = 0; i < physicalAbilities.size(); i++) {
            Ability ability = (Ability) physicalAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < socialAbilities.size(); i++) {
            Ability ability = (Ability) socialAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < mentalAbilities.size(); i++) {
            Ability ability = (Ability) mentalAbilities.get(i);
            abilities.add(ability);
        }
        int base = calculateBaseMonthlyIncome(abilities, professions);
        return base;
    }

    public long totalMonthlyIncome() {
        List abilities = new ArrayList();
        for (int i = 0; i < physicalAbilities.size(); i++) {
            Ability ability = (Ability) physicalAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < socialAbilities.size(); i++) {
            Ability ability = (Ability) socialAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < mentalAbilities.size(); i++) {
            Ability ability = (Ability) mentalAbilities.get(i);
            abilities.add(ability);
        }
        long base = calculateTotalMonthlyIncome(abilities, professions, resources);
        return base + extraMonthlyIncome;
    }

    public BankAccount getIncomeBankAccount() {
        if (bankAccounts != null && bankAccounts.size() > 0) {
            for (int i = 0; i < bankAccounts.size(); i++) {
                BankAccount bankAccount = (BankAccount) bankAccounts.get(i);
                if (bankAccount.isIncome()) {
                    return bankAccount;
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
     * @see AppPreferences#getReadXPFrom()
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
     * @param pExperience
     */
    public void setExperience(int pExperience) {
        experience = pExperience;
    }

    public Vitals getVitals() {
        return Vitals.getVital(vitals);
    }

    public int getVitalsId() {
        return vitals;
    }

    public void setVitals(int pVitals) {
        vitals = pVitals;
    }

    public void setVitals(Vitals pVitals) {
        vitals = pVitals.getId();
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

    public boolean isSLP() {
        return mSLP;
    }

    public void setSLP(boolean pSLP) {
        mSLP = pSLP;
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
