package se.tdt.bobby.wodcc.data;

import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import java.util.List;
import java.util.Iterator;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.text.ParseException;

/**
 * Description.
 * <p>This iterator takes a list of Role objects whit there minimum info as MainFrame lists them for eaxample
 * and for each call to next, the complete info for the RoleObject that should be returned is returned instead.</p>
 * <p>In reality it only needs Role Objects that comtains the id, the rest is refetched from the database.</p>
 * Created: 2004-mar-29 15:52:00
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 * @see se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB#getRole(int)
 * @see se.tdt.bobby.wodcc.logic.OutputCreator#makeRoles(java.util.List<Role>, boolean, boolean)
 */
public class MinimumToFullRolesIterator implements Iterator<Role> {

    private List<Role> mMinimumRoles;
    private RetrievalDB mRetrievalDb;
    private Iterator<Role> mIterator;
    private static final boolean DEBUG = false;
    private Exception mLastException;

    public MinimumToFullRolesIterator(List<Role> pMinimumRoles) throws SQLException, RemoteException {
        mMinimumRoles = pMinimumRoles;
        mRetrievalDb = Proxy.getRetrievalDB();
        mIterator = mMinimumRoles.iterator();
    }

    public boolean hasNext() {
        if(DEBUG) System.out.println("MinimumToFullRolesIterator.hasNext(38) " + mIterator.hasNext());
        return mIterator.hasNext();
    }

    public Role next() {
        Role role = mIterator.next();
        if(DEBUG) System.out.println("MinimumToFullRolesIterator.next(44) " + role);
        try {
            if(DEBUG) System.out.println("MinimumToFullRolesIterator.next(46) returning");
            return mRetrievalDb.getRole(role.getId());
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
