package se.tdt.bobby.wodcc.server;

import se.tdt.bobby.wodcc.db.AppPreferences;

import java.util.prefs.Preferences;

/**
 * Description.
 * <p/>
 * Created: 2004-jul-12 00:09:47
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ServerPreferences {
    private static Preferences sPreferences;

    static {
        load();
    }

    public static void load() {
        sPreferences = Preferences.systemNodeForPackage(ServerPreferences.class);
    }

    public static String getBaseDir() {
        return sPreferences.get("BaseDir", null);
    }

    public static void setBaseDir(String pBaseDir) {
        sPreferences.put("BaseDir", pBaseDir);
    }

    public static String getDatabaseFile() {
        return sPreferences.get("DatabaseFile", "");
    }

    public static void setDatabaseFile(String pDatabaseFile) {
        sPreferences.put("DatabaseFile", pDatabaseFile);
    }
}
