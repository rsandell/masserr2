package se.tdt.bobby.wodcc.data;

import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.proxy.interfaces.TemplateDB;
import se.tdt.bobby.wodcc.db.Proxy;

import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 * Description.
 * This class acts in the same way as {@link MinimumToFullRolesIterator} except it retrieves Templates instead of roles.
 * Created: 2004-maj-17 20:29:50
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 * @see MinimumToFullRolesIterator
 * @see se.tdt.bobby.wodcc.logic.OutputCreator#makeTemplates(java.util.List<se.tdt.bobby.wodcc.data.Role>)
 */
public class MinimumToFullTemplatesIterator implements Iterator<Role> {
    private List<Role> mMinimumRoles;
    private Iterator<Role> mIterator;
    private static final boolean DEBUG = false;
    private Exception mLastException;
    private TemplateDB mTemplateDb;

    public MinimumToFullTemplatesIterator(List<Role> pMinimumRoles) throws SQLException, RemoteException {
        mMinimumRoles = pMinimumRoles;
        mTemplateDb = Proxy.getTemplateDB();
        mIterator = mMinimumRoles.iterator();
    }

    public boolean hasNext() {
        return mIterator.hasNext();
    }

    public Role next() {
        Role role = mIterator.next();
        try {
            return mTemplateDb.getTemplate(role.getId());
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            mLastException = e;
            return null;
        }
    }

    public void remove() {
        mIterator.remove();
    }

    public Exception getLastException() {
        return mLastException;
    }
}
