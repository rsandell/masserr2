package se.tdt.bobby.wodcc.proxy.interfaces;

import se.tdt.bobby.wodcc.data.CreateRule;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:42:40
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface CreateRules extends BasicDB {
    CreateRule getRule(int pYear) throws SQLException, RemoteException;

    CreateRule getRule(String pYear) throws SQLException, RemoteException;

    List<CreateRule> getRules()  throws SQLException, RemoteException;
}
