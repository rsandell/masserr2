package se.tdt.bobby.wodcc.data;

import se.tdt.bobby.wodcc.db.AppPreferences;

import java.util.*;
import java.text.DateFormat;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-23 19:45:12
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class EmptyRole {
    private Clan mClan;
    private List<Discipline> mDisciplines;
    private ArrayList mThaumaturgicalPaths;
    private ArrayList mNecromancyPaths;
    private List mPhysicalAbilities;
    private List mSocialAbilities;
    private List mMentalAbilities;
    private ArrayList mMerits;
    private ArrayList mFlaws;
    private ArrayList mDerangements;
    private boolean mGhoul;
    private ArrayList mOtherTraits;
    private List mCompiledInfluences;

    public EmptyRole(Clan pClan, List pPhysicalAbilities, List pSocialAbilities, List pMentalAbilities, List<Discipline> pDisciplines, boolean pGhoul) {
        mClan = pClan;
        mPhysicalAbilities = pPhysicalAbilities;
        mSocialAbilities = pSocialAbilities;
        mMentalAbilities = pMentalAbilities;
        mDisciplines = pDisciplines;
        mGhoul = pGhoul;        
        mThaumaturgicalPaths = new ArrayList(7);
        for(int i = 0; i < 6; i++) {
            mThaumaturgicalPaths.add("&nbsp;");
        }
        mNecromancyPaths = new ArrayList(7);
        for(int i = 0; i < 6; i++) {
            mNecromancyPaths.add("&nbsp;");
        }
        mMerits = new ArrayList(5);
        for (int i = 0; i < 5; i++) {
            mMerits.add(new MeritORflaw(-1, "&nbsp;", 1));
        }
        mFlaws = new ArrayList(5);
        for (int i = 0; i < 5; i++) {
            mFlaws.add(new MeritORflaw(-1, "&nbsp;", -1));
        }
        mDerangements = new ArrayList(4);
        for (int i = 0; i < 3; i++) {
            mDerangements.add("&nbsp;");
        }
        mOtherTraits = new ArrayList(4);
        for (int i = 0; i < 3; i++) {
            mOtherTraits.add("&nbsp;");
        }
        mCompiledInfluences = new ArrayList(0);
    }

    public int getId() {
        return -1;
    }
    public String getName() {
        return "Name: ";
    }
    public String getPlayerName() {
        return "&nbsp;";
    }
    public String getGeneration() {
        return "&nbsp;";
    }
    public String getSire() {
        return "&nbsp;";
    }
    public String getEmbraced() {
        return "&nbsp;";
    }
    public String getEmbracedString() {
        return "&nbsp;";
    }
    public Clan getClan() {
        return mClan;
    }
    public String getNature() {
        return "&nbsp;";
    }
    public String getDemeanor() {
        return "&nbsp;";
    }
    public String getCourage() {
        return "&nbsp;";
    }
    public String getConcience() {
        return "&nbsp;";
    }
    public String getSelfControl() {
        return "&nbsp;";
    }
    public String getWillpower() {
        return "&nbsp;";
    }
    public String getPathDots() {
        return "&nbsp;";
    }
    public String getPath() {
        return "Path of ...";
    }
    public String getPhysical() {
        return "&nbsp;";
    }
    public String getSocial() {
        return "&nbsp;";
    }
    public String getMental() {
        return "&nbsp;";
    }
    public int getExtraHealthLevels() {
        return 0;
    }
    public boolean isSufferesOfInjury() {
        return true;
    }
    public List getDisciplines() {
        return mDisciplines;
    }
    public List getThaumaturgicalPaths() {
        return mThaumaturgicalPaths;
    }
    public List getNecromancyPaths() {
        return mNecromancyPaths;
    }
    public List getPhysicalAbilities() {
        return mPhysicalAbilities;
    }
    public List getSocialAbilities() {
        return mSocialAbilities;
    }
    public List getMentalAbilities() {
        return mMentalAbilities;
    }
    public List getMerits() {
        return mMerits;
    }
    public List getFlaws() {
        return mFlaws;
    }
    public List getDerangements() {
        return mDerangements;
    }
    public List getOtherTraits() {
        return mOtherTraits;
    }
    public boolean hasThaumaturgy() {
        return true;
    }

    public boolean hasNecromancy() {
        return true;
    }

    public boolean hasRituals() {
        return false;
    }
    public List<RitualType> getRitualTypes() {
        ArrayList<RitualType> list = new ArrayList<RitualType>();
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

    public String getAge() {
        return "&nbsp;";
    }
    public boolean isGhoul() {
        return mGhoul;
    }

    public Domain getDomain() {
        return AppPreferences.getPreferredDomain();
    }

    public String getSelfControlORinstinct() {
        return "Self-Control/Instinct";
    }

    public String getConcienseORconviction() {
        return "Conciense/Conviction";
    }

    public List compileInfluences() {
        return mCompiledInfluences;
    }
}
