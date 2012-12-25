package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.data.IntWithString;

import javax.swing.*;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-25 22:22:33
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class NecromancyPathsEditorTableModel extends PathsEditorTableModel {

    public NecromancyPathsEditorTableModel(RetrievalDB pRetrievalDB, ManipulationDB pManipulationDB, JComponent pErrorMessageParent) throws SQLException, RemoteException {
        super(pRetrievalDB, pManipulationDB, pErrorMessageParent);
    }

    protected Vector<IntWithString> getPathNames() throws SQLException, RemoteException {
        return mRetrievalDB.getNectromancyPathNames();
    }

    protected void updatePath(IntWithString pPath) throws SQLException, RemoteException {
        mManipulationDB.updateNecromancyPath(pPath);
    }

    protected IntWithString addPath(String pName) throws SQLException, RemoteException {
        return mManipulationDB.addNecromancyPath(pName);
    }
}
