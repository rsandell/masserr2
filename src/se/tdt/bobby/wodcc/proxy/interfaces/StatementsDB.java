package se.tdt.bobby.wodcc.proxy.interfaces;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.sqllists.ListStatement;
import se.tdt.bobby.wodcc.data.sqllists.ListStatementResult;
import se.tdt.bobby.wodcc.data.sqllists.Parameter;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * Created: 2006-jul-24 21:58:21
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public interface StatementsDB extends BasicDB {
    int addStatement(String pName, String pStatement, List<Parameter> pParameters) throws SQLException, RemoteException;

    void updateStatement(ListStatement pStatement) throws SQLException, RemoteException;

    ListStatement getStatement(int pId, boolean pConnect) throws SQLException, RemoteException;

    ListStatement getStatement(int pId) throws SQLException, RemoteException;

    ListStatementResult executeStatement(ListStatement pStatement, boolean pConnect) throws SQLException, RemoteException;

    ListStatementResult executeStatement(ListStatement pStatement) throws SQLException, RemoteException;

    ListStatementResult executeStatement(int pStatementId) throws SQLException, RemoteException;

    Vector<IntWithString> listStatements() throws SQLException, RemoteException;

    int addStatement(ListStatement pStatement) throws SQLException, RemoteException;

    void removeStatement(int pStatementId) throws SQLException, RemoteException;

    List<List<Object>> getRows(String pSql) throws SQLException, RemoteException;
}
