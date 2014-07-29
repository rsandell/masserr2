package net.joinedminds.masserr.api.serialization;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.bson.types.ObjectId;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class NoObjectIdExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.isAssignableFrom(ObjectId.class);
    }
}
