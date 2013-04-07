package net.joinedminds.masserr.model.mgm;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.model.Morality;
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


}
