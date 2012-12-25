package se.tdt.bobby.wodcc.logic;

import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.data.Experience;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-18 16:21:13
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ExperienceListFetcher {
    private RetrievalDB mRetrievalDB;

    public ExperienceListFetcher() throws SQLException, RemoteException {
        mRetrievalDB = Proxy.getRetrievalDB();
    }

    public List<Experience> getDetailedExperienceListForRole(int pRoleId) throws SQLException, RemoteException {
        return mRetrievalDB.getDetailedExperienceListForRole(pRoleId);
    }
}
