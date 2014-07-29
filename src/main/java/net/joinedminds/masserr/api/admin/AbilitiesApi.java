package net.joinedminds.masserr.api.admin;

import com.github.jmkgreen.morphia.Datastore;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import net.joinedminds.masserr.api.ApiListResponse;
import net.joinedminds.masserr.model.Ability;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.export.ExportedBean;

import java.util.List;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
@ExportedBean
public class AbilitiesApi extends ApiListResponse<Ability> {

    private Provider<Datastore> db;

    public AbilitiesApi(Provider<Datastore> db) {
        this.db = db;
    }

    @Override
    protected List<Ability> createList() {
        return db.get().find(Ability.class).order("type,name").asList();
    }

    @Override
    protected Ability get(String id) {
        return db.get().get(Ability.class, new ObjectId(id));
    }
}
