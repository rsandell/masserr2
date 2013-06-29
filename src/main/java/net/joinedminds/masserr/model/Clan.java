package net.joinedminds.masserr.model;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.Functions;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-10 01:52:34
 *
 * @author <a href="sandell.robert@gmail.com">Robert Sandell</a>
 */
@Entity
public class Clan implements NamedIdentifiable, Documented {

    @Id
    private ObjectId objectId;
    @Indexed
    private String name;
    private int baseIncome;
    private String weaknesses = "";
    private String docUrl;
    @Reference
    private List<Discipline> clanDisciplines;

    public Clan() {
    }

    public Clan(String pName) {
        name = pName;
    }

    public Clan(String pName, int pBaseIncome) {
        name = pName;
        baseIncome = pBaseIncome;
    }

    public Clan(String pName, int pBaseIncome, String pWeaknesses) {
        name = pName;
        baseIncome = pBaseIncome;
        weaknesses = pWeaknesses;
    }

    @DataBoundConstructor
    public Clan(String id, String name, int baseIncome, String weaknesses, String docUrl) {
        this.objectId = Functions.toObjectId(id);
        this.name = name;
        this.baseIncome = baseIncome;
        this.weaknesses = weaknesses;
        this.docUrl = docUrl;
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

    public String toString() {
        return name;
    }

    public int getBaseIncome() {
        return baseIncome;
    }

    public void setBaseIncome(int pBaseIncome) {
        baseIncome = pBaseIncome;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String pWeaknesses) {
        weaknesses = pWeaknesses;
    }

    public List<Discipline> getClanDisciplines() {
        if (clanDisciplines == null) {
            clanDisciplines = new LinkedList<>();
        }
        return clanDisciplines;
    }

    public void setClanDisciplines(List<Discipline> clanDisciplines) {
        this.clanDisciplines = clanDisciplines;
    }

    public static Clan idRef(String id) {
        Clan c = new Clan();
        c.objectId = new ObjectId(id);
        return c;
    }

    public String getLogoPath(String resUrl, String dim) {
        if(!"64".equals(dim) && !"48".equals(dim)) {
            throw new IllegalArgumentException("Illegal dimension " + dim);
        }
        StringBuffer str = new StringBuffer(resUrl);
        str.append("/logo/").append(dim).append("/");
        String n = getName();
        n = n.replaceAll("\\s", "");
        return str.append(n.toLowerCase(Locale.US)).append(".png").toString();
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
}
