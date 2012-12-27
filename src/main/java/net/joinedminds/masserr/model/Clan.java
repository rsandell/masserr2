package net.joinedminds.masserr.model;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 01:52:34
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Clan implements NamedIdentifiable  {

    @Id
    private String id;
	private String name;
	private int baseIncome;
    private String weaknesses = "";

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
}
