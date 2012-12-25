package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;

import javax.swing.*;
import java.util.Vector;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-25 22:12:13
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ThaumaturgyPathsEditorTableModel extends PathsEditorTableModel{

    public ThaumaturgyPathsEditorTableModel(RetrievalDB pRetrievalDB, ManipulationDB pManipulationDB, JComponent pErrorMessageParent) throws SQLException, RemoteException {
        super(pRetrievalDB, pManipulationDB, pErrorMessageParent);
    }

    protected Vector<IntWithString> getPathNames() throws SQLException, RemoteException {
        return mRetrievalDB.getThaumaturgicalPathNames();
    }

    protected void updatePath(IntWithString pPath) throws SQLException, RemoteException {
        mManipulationDB.updateThaumaturgicalPath(pPath);
    }

    protected IntWithString addPath(String pName) throws SQLException, RemoteException {
        return mManipulationDB.addThaumaturgicalPath(pName);
    }


}
