package net.joinedminds.masserr.model;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Reference;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Central configuration.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Entity
public class Config {

    @Id
    private ObjectId objectId;
    private String appName;
    @Reference
    private Morality defaultMorality;
    @Embedded
    private OAuthKeysConfig yahooKeys;
    @Embedded
    private OAuthKeysConfig googleKeys;
    private String applicationUrl;
    private OAuthKeysConfig facebookKeys;

    public Config() {
        yahooKeys = new OAuthKeysConfig(false, "", "");
        googleKeys = new OAuthKeysConfig(false, "", "");
        facebookKeys = new OAuthKeysConfig(false, "", "");
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Morality getDefaultMorality() {
        return defaultMorality;
    }

    public void setDefaultMorality(Morality defaultMorality) {
        this.defaultMorality = defaultMorality;
    }

    public OAuthKeysConfig getYahooKeys() {
        return yahooKeys;
    }

    public OAuthKeysConfig getGoogleKeys() {
        return googleKeys;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setYahooKeys(OAuthKeysConfig yahooKeys) {
        this.yahooKeys = yahooKeys;
    }

    public void setGoogleKeys(OAuthKeysConfig googleKeys) {
        this.googleKeys = googleKeys;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public OAuthKeysConfig getFacebookKeys() {
        return facebookKeys;
    }

    public void setFacebookKeys(OAuthKeysConfig facebookKeys) {
        this.facebookKeys = facebookKeys;
    }

    @Embedded
    public static class OAuthKeysConfig {
        private boolean enabled = false;
        private String apiKey;
        private String apiSecret;

        @DataBoundConstructor
        public OAuthKeysConfig(boolean enabled, String apiKey, String apiSecret) {
            this.enabled = enabled;
            this.apiKey = apiKey;
            this.apiSecret = apiSecret;
        }

        public OAuthKeysConfig() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getApiSecret() {
            return apiSecret;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public void setApiSecret(String apiSecret) {
            this.apiSecret = apiSecret;
        }
    }
}
