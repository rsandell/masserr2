package net.joinedminds.masserr.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 01:52:34
 * @author <a href="sandell.robert@gmail.com">Robert Sandell</a>
 */
public class Clan implements NamedIdentifiable  {

    @Id
    private String id;
	private String name;
	private int baseIncome;
    private String weaknesses = "";
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

	@Override
    public String getId() {
		return id;
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
}
