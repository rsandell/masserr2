package net.joinedminds.masserr.api.admin;

import com.github.jmkgreen.morphia.Datastore;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
public class AdminApi {

    @Inject
    private Provider<Datastore> db;


    public AbilitiesApi getAbilities() {
        return new AbilitiesApi(db);
    }
}
