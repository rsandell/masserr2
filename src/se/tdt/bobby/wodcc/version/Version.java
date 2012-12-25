package se.tdt.bobby.wodcc.version;

import java.io.*;
import java.util.Properties;
import java.util.Date;
import java.text.DateFormat;
import java.net.URL;

/**
 * Description
 * <p/>
 * Created: 2009-jan-16 22:47:13
 *
 * @author <a href="mailto:sandell.robert@gmail.com">Robert Sandell</a>
 */
public class Version {

	public static final String UNKNOWN = "UNKNOWN";

	private static Version sInstance;
	private String mVersion;
	private String mBuildDate;
	private int mBuildNumber;
	private static final String VERSION_DAT = "version.dat";
	private static final String KEY_VERSION = "version";
	private static final String KEY_BUILD_DATE = "buildDate";
	private static final String KEY_BUILD_NUMBER = "buildNumber";

	private Version() {

		InputStream in = getClass().getResourceAsStream(VERSION_DAT);

		if( in != null ) {
			Properties prop = new Properties();
			try {
				prop.load(in);
				mVersion = prop.getProperty(KEY_VERSION, UNKNOWN);
				mBuildDate = prop.getProperty(KEY_BUILD_DATE, UNKNOWN);
				try {
					mBuildNumber = Integer.parseInt(prop.getProperty(KEY_BUILD_NUMBER, "0"));
				}
				catch(NumberFormatException e) {
					mBuildNumber = 0;
				}
			}
			catch(Exception ex) {
				setDefaults();
			}
		}
		else {
			setDefaults();
		}
	}

	@Override
	public String toString() {
		return mVersion + " | " + mBuildDate + "," + mBuildNumber;
	}

	public static Version getInstance() {
		if( sInstance == null ) {
			sInstance = new Version();
		}
		return sInstance;
	}

	public String getVersion() {
		return mVersion;
	}

	public String getBuildDate() {
		return mBuildDate;
	}

	public int getBuildNumber() {
		return mBuildNumber;
	}

	private void setDefaults() {
		mVersion = UNKNOWN;
		mBuildDate = UNKNOWN;
		mBuildNumber = 0;
	}

	public static void main(String[] args) throws IOException {
		if( args.length > 0 && "-create".equalsIgnoreCase(args[0]) ) {
			String version = UNKNOWN;
			String buildDate = UNKNOWN;
			int buildNumber = 0;
			for(int i = 1; i < args.length; i++) {
				if( args[i].indexOf('=') > 0 ) {
					String[] ar = args[i].split("=");
					if( ar.length > 1 ) {
						if( "-version".equalsIgnoreCase(ar[0]) ) {
							version = ar[1];
						}
						else if("-buildDate".equalsIgnoreCase(ar[0])) {
							buildDate = ar[1];
						}
						else if("-buildNumber".equalsIgnoreCase(ar[0])) {
							buildNumber = Integer.parseInt(ar[1]);
						}
					}
				}
			}
			if(version == UNKNOWN) {
				System.err.println("You need to provide a version string (-version=XX)");
				System.exit(1);
			}
			else {
				if(buildDate == UNKNOWN) {
					DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
					buildDate = format.format(new Date());
				}
				if(buildNumber == 0) {
					if(Version.getInstance().getBuildDate().equals(buildDate) && Version.getInstance().getVersion().equalsIgnoreCase(version)) {
						buildNumber = Version.getInstance().getBuildNumber() + 1;
					}
					else {
						buildNumber = 1;
					}
				}
				Properties prop = new Properties();
				prop.setProperty(KEY_VERSION, version);
				prop.setProperty(KEY_BUILD_DATE, buildDate);
				prop.setProperty(KEY_BUILD_NUMBER, String.valueOf(buildNumber));

				URL url = Version.getInstance().getClass().getResource("Version.class");
				if("file".equals(url.getProtocol())) {
					File f = new File(url.getFile());
					f = new File(f.getParentFile(), VERSION_DAT);
					prop.store(new FileWriter(f), "Masserr Version Info");
					System.out.println(VERSION_DAT + " created @ " + f.getAbsolutePath());
					prop.list(System.out);
				}
				else {
					System.err.println("At current version.dat can only be created when it is on a file classpath not " + url.getProtocol());
					System.exit(2);
				}

			}
		}
	}
}
