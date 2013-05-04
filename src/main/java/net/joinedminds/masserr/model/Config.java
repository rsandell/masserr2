package net.joinedminds.masserr.model;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Reference;
import org.bson.types.ObjectId;

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

    @Embedded
    public static class OAuthKeysConfig {
        private String apiKey;
        private String apiSecret;

        public OAuthKeysConfig(String apiKey, String apiSecret) {
            this.apiKey = apiKey;
            this.apiSecret = apiSecret;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getApiSecret() {
            return apiSecret;
        }
    }
}
