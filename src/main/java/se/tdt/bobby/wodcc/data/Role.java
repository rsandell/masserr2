package se.tdt.bobby.wodcc.data;

import se.tdt.bobby.wodcc.db.AppPreferences;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-15 23:02:20
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class Role implements Serializable {
    public static int GHOUL_GENERATION = 17;
    private int mId;
    private String mName;
    private String mPlayerName;
    private Generation mGeneration;
    private IntWithString mSire;
    private Date mEmbraced;
    private Clan mClan;
    private String mNature;
    private String mDemeanor;
    private int mCourage;
    private int mConcience;
    private int mSelfControl;
    private int mWillpower;
    private int mPathDots;
    private String mPath;
    private int mPhysical;
    private int mSocial;
    private int mMental;
    private int mExtraHealthLevels;
    private boolean mSufferesOfInjury;
    private List mDisciplines;
    private List mThaumaturgicalPaths;
    //private List mThaumaturgicalRituals;
    private List mNecromancyPaths;
    //private List mNecromanticRituals;
    private List mPhysicalAbilities;
    private List mSocialAbilities;
    private List mMentalAbilities;
    private List mMerits;
    private List mFlaws;
    private List mDerangements;
    private List mOtherTraits;
    private boolean mGhoul = false;
    private int mExtraMonthlyIncome;
    //private int mBaseMoneyForAge = 0;
    private List mProfessions;
    private List mResources;
    private List mBankAccounts;
    private Vector mInfluences;
    private int mExperience;
    private int mVitals = 1;
    private Domain mDomain;
    private List<Ritual> mRituals;
    private String mSelfControlORinstinct = "Self-Control";
    private String mConcienseORconviction = "Conciense";
    private String mThaumaType = "";
    private String mNecromancyType = "";
    private List<Plot> mPlots = new ArrayList<Plot>(0);
    private String mQuote;
    private boolean mSLP = false;
    private List<String> mBeastTraits;
    private FightOrFlight mFightForm;
    private FightOrFlight mFlightForm;
    private Player mPlayer = null;
    private static final boolean DEBUG = false;

    public Role() {
    }

    public Role(int pId, String pName, Clan pClan) {
        mId = pId;
        mName = pName;
        mClan = pClan;
        List empty = new ArrayList();
        mDisciplines = empty;
        mThaumaturgicalPaths = empty;
        //mThaumaturgicalRituals = empty;
        mNecromancyPaths = empty;
        //mNecromanticRituals = empty;
        mPhysicalAbilities = empty;
        mSocialAbilities = empty;
        mMentalAbilities = empty;
        mMerits = empty;
        mFlaws = empty;
        mDerangements = empty;
        mOtherTraits = empty;
        mRituals = new ArrayList<Ritual>();
    }

    public Role(int pId, String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury) {
        mId = pId;
        mName = pName;
        mPlayerName = pPlayerName;
        mGeneration = pGeneration;
        mSire = pSire;
        mEmbraced = pEmbraced;
        mClan = pClan;
        mNature = pNature;
        mDemeanor = pDemeanor;
        mCourage = pCourage;
        mConcience = pConcience;
        mSelfControl = pSelfControl;
        mWillpower = pWillpower;
        mPathDots = pPathDots;
        mPath = pPath;
        mPhysical = pPhysical;
        mSocial = pSocial;
        mMental = pMental;
        mExtraHealthLevels = pExtraHealthLevels;
        mSufferesOfInjury = pSufferesOfInjury;
        List empty = new ArrayList();
        mDisciplines = empty;
        mThaumaturgicalPaths = empty;
        //mThaumaturgicalRituals = empty;
        mNecromancyPaths = empty;
        //mNecromanticRituals = empty;
        mPhysicalAbilities = empty;
        mSocialAbilities = empty;
        mMentalAbilities = empty;
        mMerits = empty;
        mFlaws = empty;
        mDerangements = empty;
        mOtherTraits = empty;
        mRituals = new ArrayList<Ritual>();
    }

    public Role(int pId, String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, boolean pGhoul) {
        mId = pId;
        mName = pName;
        mPlayerName = pPlayerName;
        mGeneration = pGeneration;
        mSire = pSire;
        mEmbraced = pEmbraced;
        mClan = pClan;
        mNature = pNature;
        mDemeanor = pDemeanor;
        mCourage = pCourage;
        mConcience = pConcience;
        mSelfControl = pSelfControl;
        mWillpower = pWillpower;
        mPathDots = pPathDots;
        mPath = pPath;
        mPhysical = pPhysical;
        mSocial = pSocial;
        mMental = pMental;
        mExtraHealthLevels = pExtraHealthLevels;
        mSufferesOfInjury = pSufferesOfInjury;
        mGhoul = pGhoul;
        List empty = new ArrayList();
        mDisciplines = empty;
        mThaumaturgicalPaths = empty;
        //mThaumaturgicalRituals = empty;
        mNecromancyPaths = empty;
        //mNecromanticRituals = empty;
        mPhysicalAbilities = empty;
        mSocialAbilities = empty;
        mMentalAbilities = empty;
        mMerits = empty;
        mFlaws = empty;
        mDerangements = empty;
        mOtherTraits = empty;
        mRituals = new ArrayList<Ritual>();
    }

    public Role(int pId, String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, boolean pGhoul, int pExtraMonthlyIncome, String pConcienseORconviction, String pSelfControlORinstinct) {
        mId = pId;
        mName = pName;
        mPlayerName = pPlayerName;
        mGeneration = pGeneration;
        mSire = pSire;
        mEmbraced = pEmbraced;
        mClan = pClan;
        mNature = pNature;
        mDemeanor = pDemeanor;
        mCourage = pCourage;
        mConcience = pConcience;
        mSelfControl = pSelfControl;
        mWillpower = pWillpower;
        mPathDots = pPathDots;
        mPath = pPath;
        mPhysical = pPhysical;
        mSocial = pSocial;
        mMental = pMental;
        mExtraHealthLevels = pExtraHealthLevels;
        mSufferesOfInjury = pSufferesOfInjury;
        mGhoul = pGhoul;
        mExtraMonthlyIncome = pExtraMonthlyIncome;
        mConcienseORconviction = pConcienseORconviction;
        mSelfControlORinstinct = pSelfControlORinstinct;
        List empty = new ArrayList();
        mDisciplines = empty;
        mThaumaturgicalPaths = empty;
        //mThaumaturgicalRituals = empty;
        mNecromancyPaths = empty;
        //mNecromanticRituals = empty;
        mPhysicalAbilities = empty;
        mSocialAbilities = empty;
        mMentalAbilities = empty;
        mMerits = empty;
        mFlaws = empty;
        mDerangements = empty;
        mOtherTraits = empty;
        mRituals = new ArrayList<Ritual>();
    }

    public Role(String pName, String pPlayerName, Generation pGeneration, IntWithString pSire, Date pEmbraced, Clan pClan, String pNature, String pDemeanor, int pCourage, int pConcience, int pSelfControl, int pWillpower, int pPathDots, String pPath, int pPhysical, int pSocial, int pMental, int pExtraHealthLevels, boolean pSufferesOfInjury, String pConcienseORconviction, String pSelfControlORinstinct) {
        mName = pName;
        mPlayerName = pPlayerName;
        mGeneration = pGeneration;
        mSire = pSire;
        mEmbraced = pEmbraced;
        mClan = pClan;
        mNature = pNature;
        mDemeanor = pDemeanor;
        mCourage = pCourage;
        mConcience = pConcience;
        mSelfControl = pSelfControl;
        mWillpower = pWillpower;
        mPathDots = pPathDots;
        mPath = pPath;
        mPhysical = pPhysical;
        mSocial = pSocial;
        mMental = pMental;
        mExtraHealthLevels = pExtraHealthLevels;
        mSufferesOfInjury = pSufferesOfInjury;
        mConcienseORconviction = pConcienseORconviction;
        mSelfControlORinstinct = pSelfControlORinstinct;
        mId = -1;
        List empty = new ArrayList();
        mDisciplines = empty;
        mThaumaturgicalPaths = empty;
        //mThaumaturgicalRituals = empty;
        mNecromancyPaths = empty;
        //mNecromanticRituals = empty;
        mPhysicalAbilities = empty;
        mSocialAbilities = empty;
        mMentalAbilities = empty;
        mMerits = empty;
        mFlaws = empty;
        mDerangements = empty;
        mOtherTraits = empty;
        mRituals = new ArrayList<Ritual>();
    }

    public List getResources() {
        return mResources;
    }

    public void setResources(List pResources) {
        mResources = pResources;
    }

    public int getId() {
        return mId;
    }

    public void setId(int pId) {
        mId = pId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getPlayerName() {
        if (mPlayer != null) {
            return mPlayer.getName();
        }
        else {
            return "";
        }
    }

    /*public void setPlayerName(String pPlayerName) {
        mPlayerName = pPlayerName;
    }*/

    public Generation getGeneration() {
        return mGeneration;
    }

    public void setGeneration(Generation pGeneration) {
        mGeneration = pGeneration;
    }

    public IntWithString getSire() {
        return mSire;
    }

    public void setSire(IntWithString pSire) {
        mSire = pSire;
    }

    public Date getEmbraced() {
        return mEmbraced;
    }

    public String getEmbracedString() {
        if (mEmbraced != null) {
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            return format.format(mEmbraced);
        }
        else {
            return null;
        }
    }

    public void setEmbraced(Date pEmbraced) {
        mEmbraced = pEmbraced;
    }

    public Clan getClan() {
        return mClan;
    }

    public void setClan(Clan pClan) {
        mClan = pClan;
    }

    public String getNature() {
        return mNature;
    }

    public void setNature(String pNature) {
        mNature = pNature;
    }

    public String getDemeanor() {
        return mDemeanor;
    }

    public void setDemeanor(String pDemeanor) {
        mDemeanor = pDemeanor;
    }

    public int getCourage() {
        return mCourage;
    }

    public void setCourage(int pCourage) {
        mCourage = pCourage;
    }

    public int getConcience() {
        return mConcience;
    }

    public void setConcience(int pConcience) {
        mConcience = pConcience;
    }

    public int getSelfControl() {
        return mSelfControl;
    }

    public void setSelfControl(int pSelfControl) {
        mSelfControl = pSelfControl;
    }

    public int getWillpower() {
        return mWillpower;
    }

    public void setWillpower(int pWillpower) {
        mWillpower = pWillpower;
    }

    public int getPathDots() {
        return mPathDots;
    }

    public void setPathDots(int pPathDots) {
        mPathDots = pPathDots;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String pPath) {
        mPath = pPath;
    }

    public int getPhysical() {
        return mPhysical;
    }

    public void setPhysical(int pPhysical) {
        mPhysical = pPhysical;
    }

    public int getSocial() {
        return mSocial;
    }

    public void setSocial(int pSocial) {
        mSocial = pSocial;
    }

    public int getMental() {
        return mMental;
    }

    public void setMental(int pMental) {
        mMental = pMental;
    }

    public int getExtraHealthLevels() {
        return mExtraHealthLevels;
    }

    public void setExtraHealthLevels(int pExtraHealthLevels) {
        mExtraHealthLevels = pExtraHealthLevels;
    }

    public boolean isSufferesOfInjury() {
        return mSufferesOfInjury;
    }

    public void setSufferesOfInjury(boolean pSufferesOfInjury) {
        mSufferesOfInjury = pSufferesOfInjury;
    }

    public String toString() {
        return mName;
    }

    public List getDisciplines() {
        return mDisciplines;
    }

    public void setDisciplines(List pDisciplines) {
        mDisciplines = pDisciplines;
    }

    public List getThaumaturgicalPaths() {
        return mThaumaturgicalPaths;
    }

    public void setThaumaturgicalPaths(List pThaumaturgicalPaths) {
        mThaumaturgicalPaths = pThaumaturgicalPaths;
    }

    /*public List getThaumaturgicalRituals() {
        return mThaumaturgicalRituals;
    }

    public void setThaumaturgicalRituals(List pThaumaturgicalRituals) {
        mThaumaturgicalRituals = pThaumaturgicalRituals;
    }*/

    public List getNecromancyPaths() {
        return mNecromancyPaths;
    }

    public void setNecromancyPaths(List pNecromancyPaths) {
        mNecromancyPaths = pNecromancyPaths;
    }

    /*public List getNecromanticRituals() {
        return mNecromanticRituals;
    }

    public void setNecromanticRituals(List pNecromanticRituals) {
        mNecromanticRituals = pNecromanticRituals;
    }*/

    public List<Ritual> getRituals() {
        return mRituals;
    }

    public void setRituals(List<Ritual> pRituals) {
        mRituals = pRituals;
    }

    public void setPhysicalAbilities(List pAbilities) {
        mPhysicalAbilities = pAbilities;
    }

    public List getPhysicalAbilities() {
        return mPhysicalAbilities;
    }

    public List getSocialAbilities() {
        return mSocialAbilities;
    }

    public void setSocialAbilities(List pSocialAbilities) {
        mSocialAbilities = pSocialAbilities;
    }

    public List getMentalAbilities() {
        return mMentalAbilities;
    }

    public void setMentalAbilities(List pMentalAbilities) {
        mMentalAbilities = pMentalAbilities;
    }

    public List getMerits() {
        return mMerits;
    }

    public void setMerits(List pMerits) {
        mMerits = pMerits;
    }

    public List getFlaws() {
        return mFlaws;
    }

    public void setFlaws(List pFlaws) {
        mFlaws = pFlaws;
    }

    public List getDerangements() {
        return mDerangements;
    }

    public void setDerangements(List pDerangements) {
        mDerangements = pDerangements;
    }

    public List getOtherTraits() {
        return mOtherTraits;
    }

    public void setOtherTraits(List pOtherTraits) {
        mOtherTraits = pOtherTraits;
    }

    public boolean hasThaumaturgy() {
        if (mThaumaturgicalPaths != null) {
            if (mThaumaturgicalPaths.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasNecromancy() {
        if (mNecromancyPaths != null) {
            if (mNecromancyPaths.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRituals() {
        if (mRituals != null) {
            return mRituals.size() > 0;
        }
        return false;
    }

    public List<RitualType> getRitualTypes() {
        ArrayList<RitualType> list = new ArrayList<RitualType>();
        RitualType lastType = null;
        for (int i = 0; i < mRituals.size(); i++) {
            Ritual ritual = mRituals.get(i);
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
        return getAge(mEmbraced);
    }

    public boolean isGhoul() {
        return mGhoul;
    }

    public void setGhoul(boolean pGhoul) {
        mGhoul = pGhoul;
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
        if (DEBUG) System.out.println("Role.calculateBaseMonthlyIncome(611) baseIncome after Abilities: " + baseIncome);
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
        if (DEBUG) System.out.println("Role.calculateTotalMonthlyIncome(627) base: " + baseIncome);
        long incomeResources = 0;
        for (int i = 0; i < pResources.size(); i++) {
            Resource resource = (Resource) pResources.get(i);
            incomeResources += (resource.getIncome() * resource.getPercent());
        }
        if (DEBUG) System.out.println("Role.calculateTotalMonthlyIncome(633) resourceIncome: " + incomeResources);
        long totalIncome = baseIncome + incomeResources;
        if (DEBUG) System.out.println("Role.calculateTotalMonthlyIncome(635) totalIncome = " + totalIncome);
        return totalIncome;
    }

    public List getProfessions() {
        return mProfessions;
    }

    public void setProfessions(List pProfessions) {
        mProfessions = pProfessions;
    }

    public List compileInfluences() {
        ArrayList list = new ArrayList();
        HashMap map = new HashMap();
        if (mResources != null) {
            for (int i = 0; i < mResources.size(); i++) {
                Resource resource = (Resource) mResources.get(i);
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
        if (mInfluences != null) {
            for (int i = 0; i < mInfluences.size(); i++) {
                Influence influence = (Influence) mInfluences.elementAt(i);
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
        return mBankAccounts;
    }

    public void setBankAccounts(List pBankAccounts) {
        mBankAccounts = pBankAccounts;
    }

    public int getExtraMonthlyIncome() {
        return mExtraMonthlyIncome;
    }

    public void setExtraMonthlyIncome(int pExtraMonthlyIncome) {
        mExtraMonthlyIncome = pExtraMonthlyIncome;
    }

    public Vector getInfluences() {
        return mInfluences;
    }

    public void setInfluences(Vector pInfluences) {
        mInfluences = pInfluences;
    }

    public void addInfluence(Influence pInf) {
        if (mInfluences == null) {
            mInfluences = new Vector();
        }
        mInfluences.add(pInf);
    }

    public int baseMonthlyIncome() {
        List abilities = new ArrayList();
        for (int i = 0; i < mPhysicalAbilities.size(); i++) {
            Ability ability = (Ability) mPhysicalAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < mSocialAbilities.size(); i++) {
            Ability ability = (Ability) mSocialAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < mMentalAbilities.size(); i++) {
            Ability ability = (Ability) mMentalAbilities.get(i);
            abilities.add(ability);
        }
        int base = calculateBaseMonthlyIncome(abilities, mProfessions);
        return base;
    }

    public long totalMonthlyIncome() {
        List abilities = new ArrayList();
        for (int i = 0; i < mPhysicalAbilities.size(); i++) {
            Ability ability = (Ability) mPhysicalAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < mSocialAbilities.size(); i++) {
            Ability ability = (Ability) mSocialAbilities.get(i);
            abilities.add(ability);
        }
        for (int i = 0; i < mMentalAbilities.size(); i++) {
            Ability ability = (Ability) mMentalAbilities.get(i);
            abilities.add(ability);
        }
        long base = calculateTotalMonthlyIncome(abilities, mProfessions, mResources);
        if (DEBUG) System.out.println("Role.totalMonthlyIncome(749) base= " + base + " extra= " + mExtraMonthlyIncome);
        return base + mExtraMonthlyIncome;
    }

    public BankAccount getIncomeBankAccount() {
        if (mBankAccounts != null && mBankAccounts.size() > 0) {
            for (int i = 0; i < mBankAccounts.size(); i++) {
                BankAccount bankAccount = (BankAccount) mBankAccounts.get(i);
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
        if (AppPreferences.getReadXPFrom() == AppPreferences.XP_ROLE) {
            return mExperience;
        }
        else if (mPlayer != null) {
            return mPlayer.getXP();
        }
        else {
            return 0;
        }
    }

    /**
     * Sets this Roles experience.
     * @param pExperience
     */
    public void setExperience(int pExperience) {
        mExperience = pExperience;
    }

    public Vitals getVitals() {
        return Vitals.getVital(mVitals);
    }

    public int getVitalsId() {
        return mVitals;
    }

    public void setVitals(int pVitals) {
        mVitals = pVitals;
    }

    public void setVitals(Vitals pVitals) {
        mVitals = pVitals.getId();
    }

    public Domain getDomain() {
        return mDomain;
    }

    public void setDomain(Domain pDomain) {
        mDomain = pDomain;
    }

    public String getSelfControlORinstinct() {
        return mSelfControlORinstinct;
    }

    public void setSelfControlORinstinct(String pSelfControlORinstinct) {
        mSelfControlORinstinct = pSelfControlORinstinct;
    }

    public String getConcienseORconviction() {
        return mConcienseORconviction;
    }

    public void setConcienseORconviction(String pConcienseORconviction) {
        mConcienseORconviction = pConcienseORconviction;
    }

    public String getThaumaType() {
        return mThaumaType;
    }

    public void setThaumaType(String pThaumaType) {
        mThaumaType = pThaumaType;
    }

    public String getNecromancyType() {
        return mNecromancyType;
    }

    public void setNecromancyType(String pNecromancyType) {
        mNecromancyType = pNecromancyType;
    }

    public List<Plot> getPlots() {
        return mPlots;
    }

    public void setPlots(List<Plot> pPlots) {
        mPlots = pPlots;
    }

    public String getQuote() {
        return mQuote;
    }

    public void setQuote(String pQuote) {
        mQuote = pQuote;
    }

    public boolean isSLP() {
        return mSLP;
    }

    public void setSLP(boolean pSLP) {
        mSLP = pSLP;
    }

    public List<String> getBeastTraits() {
        return mBeastTraits;
    }

    public void setBeastTraits(List<String> pBeastTraits) {
        mBeastTraits = pBeastTraits;
    }

    public FightOrFlight getFightForm() {
        return mFightForm;
    }

    public void setFightForm(FightOrFlight pFightForm) {
        mFightForm = pFightForm;
    }

    public FightOrFlight getFlightForm() {
        return mFlightForm;
    }

    public void setFlightForm(FightOrFlight pFlightForm) {
        mFlightForm = pFlightForm;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Player pPlayer) {
        mPlayer = pPlayer;
    }
}
