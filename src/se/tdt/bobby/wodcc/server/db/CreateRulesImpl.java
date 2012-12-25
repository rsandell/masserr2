package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.CreateRule;
import se.tdt.bobby.wodcc.remote.db.RemoteCreateRules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

/**
 * Description
 *
 *
 * Created: 2004-feb-02 10:04:18
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class CreateRulesImpl extends BasicDBImpl implements RemoteCreateRules {

    public CreateRulesImpl() throws RemoteException {
        super();
    }

	public CreateRule getRule(int pYear) throws SQLException {
		CreateRule rule = null;
		connect();
		String query = "SELECT id, year_min, year_max, disciplines, attributes, abilities " +
				"FROM age_createRules " +
				"WHERE year_min <=" + pYear + " AND year_max >=" + pYear;
		ResultSet rs = query(query);
		if (rs.next()) {
			rule = new CreateRule(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
		}
		disconnect();
		return rule;
	}

	public CreateRule getRule(String pYear) throws SQLException {
		try {
			int year = Integer.parseInt(pYear);
			return getRule(year);
		}
		catch (NumberFormatException e) {
			if (DEBUG) e.printStackTrace();
			return null;
		}
	}

    public List<CreateRule> getRules() throws SQLException {
        ArrayList<CreateRule> rules = new ArrayList<CreateRule>();
		connect();
		String query = "SELECT id, year_min, year_max, disciplines, attributes, abilities " +
				"FROM age_createRules " +
				"ORDER BY year_min DESC";
		ResultSet rs = query(query);
		while (rs.next()) {
			rules.add(new CreateRule(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            if(DEBUG) System.out.println("CreateRulesImpl.getRules(59) rules.size: " + rules.size());
		}
		disconnect();
		return rules;
    }
}
