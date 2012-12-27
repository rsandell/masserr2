package net.joinedminds.masserr.model;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

/**
 * Description
 *
 * Created: 2004-feb-17 21:10:37
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class RolesGroup implements Serializable {

    @Id
    private String id;
    private String name;
    private Date date;
    private String description;
    private String type;

    /**
     * For serialization
     */
    public RolesGroup() {
    }

    public RolesGroup(String pId, String pName, Date pDate, String pDescription, String pType) {
        id = pId;
        name = pName;
        date = pDate;
        description = pDescription;
        type = pType;
    }

    public RolesGroup(String pName, Date pDate, String pDescription, String pType) {
        name = pName;
        date = pDate;
        description = pDescription;
        type = pType;
    }

    public String getId() {
        return id;
    }

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
