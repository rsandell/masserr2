package net.joinedminds.masserr.model;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import net.joinedminds.masserr.Functions;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Description
 * <p/>
 * Created: 2004-feb-17 21:10:37
 *
 * @author <a href="mailto:sandell.robert@gmail.com">Robert "Bobby" Sandell</a>
 */
@Entity
public class RolesGroup implements NamedIdentifiable {

    @Id
    private ObjectId objectId;
    @Indexed
    private String name;
    private Date date;
    private String description;
    @Indexed
    private String type;

    /**
     * For serialization
     */
    public RolesGroup() {
    }

    public RolesGroup(String pName, Date pDate, String pDescription, String pType) {
        name = pName;
        date = pDate;
        description = pDescription;
        type = pType;
    }

    @Override
    public String getId() {
        return Functions.toString(objectId);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date pDate) {
        date = pDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }

    public String toString() {
        return name;
    }
}
