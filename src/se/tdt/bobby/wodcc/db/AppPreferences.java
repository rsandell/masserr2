package se.tdt.bobby.wodcc.db;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.Vitals;
import se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering.Rules;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.prefs.Preferences;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-26 14:19:05
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class AppPreferences {
    private static Preferences sPreferences;
    public static final int XP_ROLE = 1;
    public static final int XP_PLAYER = 2;

    static {
        load();
    }

    public static void load() {
        sPreferences = Preferences.userNodeForPackage(AppPreferences.class);
    }

    public static String getHeadingName() {
        return sPreferences.get("HeadingName", "");
    }

    public static void setHeadingName(String pName) {
        sPreferences.put("HeadingName", pName);
    }

    public static String getDatabaseFile() {
        return sPreferences.get("DatabaseFile", null);
    }

    public static void setDatabaseFile(String pLocation) {
        sPreferences.put("DatabaseFile", pLocation);
    }

    public static String getTempDir() {
        return sPreferences.get("TempDir", null);
    }

    public static void setTempDir(String pTempDir) {
        sPreferences.put("TempDir", pTempDir);
    }

    public static String getExplorer() {
        return sPreferences.get("Explorer", null);
    }

    public static void setExplorer(String pExplorer) {
        sPreferences.put("Explorer", pExplorer);
    }

    public static void testFiles() throws FileNotFoundException {
        String f;
        File file;
        if (!isGoOnline()) {
            f = getDatabaseFile();
            file = null;
            if (f == null) {
                throw new FileNotFoundException("Database File not set.");
            } else {
                file = new File(f);
                if (!file.exists()) {
                    throw new FileNotFoundException(file.getPath());
                }
            }
        }
        f = getTempDir();
        if (f == null) {
            throw new FileNotFoundException("Temp Dir not set.");
        } else {
            file = new File(f);
            if (!file.exists()) {
                throw new FileNotFoundException(file.getPath());
            }
        }
        f = getExplorer();
        if (f == null) {
            throw new FileNotFoundException("Html Browser not set.");
        } else {
            file = new File(f);
            if (!file.exists()) {
                throw new FileNotFoundException(file.getPath());
            }
        }
    }

    public static void setVisibleClan(int pSelectedClanId) {
        sPreferences.putInt("VisibleClan", pSelectedClanId);
    }

    public static int getVisibleClan() {
        return sPreferences.getInt("VisibleClan", -1);
    }

    public static boolean getViewGhouls() {
        return sPreferences.getBoolean("ViewGhouls", true);
    }

    public static void setViewGhouls(boolean pViewGhouls) {
        sPreferences.putBoolean("ViewGhouls", pViewGhouls);
    }

    public static boolean getViewNotPlayedRoles() {
        return sPreferences.getBoolean("ViewNotPlayedRoles", true);
    }

    public static void setViewNotPlayedRoles(boolean pView) {
        sPreferences.putBoolean("ViewNotPlayedRoles", pView);
    }

    public static Domain getPreferredDomain() {
        int id = sPreferences.getInt("PreferredDomainId", 1);
        String name = sPreferences.get("PreferredDomainName", "Carlshamn");
        return new Domain(id, name);
    }

    public static void setPreferredDomain(Domain pDomain) {
        sPreferences.putInt("PreferredDomainId", pDomain.getId());
        sPreferences.put("PreferredDomainName", pDomain.getName());
    }

    public static int getVisibleDomain() {
        return sPreferences.getInt("VisibleDomain", -1);
    }

    public static void setVisibleDomain(int pSelectedDomainId) {
        sPreferences.putInt("VisibleDomain", pSelectedDomainId);
    }

    public static Vitals getVisibleVitals() {
        return Vitals.getVital(sPreferences.getInt("VisibleVitals", -1));
    }

    public static void setVisibleVitals(Vitals pVitals) {
        if (pVitals == null) {
            sPreferences.putInt("VisibleVitals", -1);
        } else {
            sPreferences.putInt("VisibleVitals", pVitals.getId());
        }
    }

    public static boolean isGoOnline() {
        return sPreferences.getBoolean("GoOnline", true);
    }

    public static void setGoOnline(boolean pGo) {
        sPreferences.putBoolean("GoOnline", pGo);
    }

    public static String getServerHostName() {
        return sPreferences.get("ServerHostName", "localhost");
    }

    public static void setServerHostName(String pHostName) {
        sPreferences.put("ServerHostName", pHostName);
    }

    public static void setRolesFilterRules(Rules pRolesFilterRules) {
        setVisibleVitals(pRolesFilterRules.getVisibleVitals());
        setVisibleDomain(pRolesFilterRules.getVisibleDomain());
        setViewNotPlayedRoles(pRolesFilterRules.isViewNotPlayedRoles());
        setViewGhouls(pRolesFilterRules.isViewGhouls());
        setVisibleClan(pRolesFilterRules.getVisibleClan());
        setViewKindred(pRolesFilterRules.isViewKindred());
        setViewSLP(pRolesFilterRules.isViewSLP());
    }

    public static boolean isViewPage2() {
        return sPreferences.getBoolean("ViewPage2", true);
    }

    public static boolean isListPlots() {
        return sPreferences.getBoolean("ListPlots", false);
    }

    public static void setViewPage2(boolean pValue) {
        sPreferences.putBoolean("ViewPage2", pValue);
    }

    public static void setListPlots(boolean pValue) {
        sPreferences.putBoolean("ListPlots", pValue);
    }

    public static boolean getViewKindred() {
        return sPreferences.getBoolean("ViewKindred", true);
    }

    public static void setViewKindred(boolean pViewKindred) {
        sPreferences.putBoolean("ViewKindred", pViewKindred);
    }

    public static boolean getViewSLP() {
        return sPreferences.getBoolean("ViewSLP", true);
    }

    public static void setViewSLP(boolean pViewSLP) {
        sPreferences.putBoolean("ViewSLP", pViewSLP);
    }

    public static boolean isListExperience() {
        return sPreferences.getBoolean("ListExperience", false);
    }

    public static void setListExperience(boolean pListExperience) {
        sPreferences.putBoolean("ListExperience", pListExperience);
    }

    public static int getReadXPFrom() {
        return sPreferences.getInt("ReadXPFrom", XP_PLAYER);
    }

    public static void setReadXPFrom(int pXPRead) {
        if(pXPRead != XP_PLAYER && pXPRead != XP_ROLE) {
            throw new IllegalArgumentException(pXPRead + " != XP_PLAYER AND XP_ROLE");
        }
        sPreferences.putInt("ReadXPFrom", pXPRead);
    }
}
