package net.joinedminds.masserr.api.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class ObjectIdTypeAdapter extends TypeAdapter<ObjectId> {
    @Override
    public void write(JsonWriter out, ObjectId value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public ObjectId read(JsonReader in) throws IOException {
        return new ObjectId(in.nextString());
    }
}
