package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.sqllists.*;
import se.tdt.bobby.wodcc.remote.db.RemoteStatementsDB;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

/**
 * Created: 2006-jul-24 20:22:34
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class StatementsDBImpl extends BasicDBImpl implements RemoteStatementsDB {

    public StatementsDBImpl() throws RemoteException {
        super();
    }

    public int addStatement(String pName, String pStatement, List<Parameter> pParameters) throws SQLException {
        PreparedStatement statement = connect("INSERT INTO statements(name, statement) VALUES(?, ?)");
        statement.setString(1, pName);
        statement.setString(2, pStatement);
        int res = statement.executeUpdate();
        statement.close();
        statement = mConnection.prepareStatement("SELECT MAX(id) FROM statements");
        ResultSet rs = statement.executeQuery();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
            statement.close();
            /*String sql = "INSERT INTO statementparameters(statement_id, param_value, param_type, is_variable, param_index) VALUES(" +
                    "?, ?, ?, ?, ?" +
                    ")";
            statement = mConnection.prepareStatement(sql);
            for (Parameter parameter : pParameters) {
                statement.setInt(1, id);
                statement.setString(2, parameter.getValue());
                statement.setString(3, parameter.getType().getName());
                statement.setBoolean(4, parameter.isVariable());
                statement.setInt(5, parameter.getIndex());
                statement.executeUpdate();
            } */
            String sql = "INSERT INTO statementparameters(statement_id, param_value, param_type, is_variable, param_index, variable_input, variable_lookup, variable_lookup_valuecolumn, variable_lookup_labelcolumn) VALUES(" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?" +
                    ")";
            statement = mConnection.prepareStatement(sql);

            //List<Parameter> parameters = pParameters;
            for (Parameter parameter : pParameters) {
                statement.setInt(1, id);
                statement.setString(2, parameter.getValue());
                statement.setString(3, parameter.getType().getName());
                statement.setBoolean(4, parameter.isVariable());
                statement.setInt(5, parameter.getIndex());
                statement.setString(6, parameter.getVariableInput());
                statement.setString(7, parameter.getVariableLookup());
                if (parameter.getVariableLookupValuecolumn() != null) {
                    statement.setShort(8, parameter.getVariableLookupValuecolumn());
                }
                else {
                    statement.setNull(8, Types.SMALLINT);
                }
                if (parameter.getVariableLookupLabelcolumn() != null) {
                    statement.setShort(9, parameter.getVariableLookupLabelcolumn());
                }
                else {
                    statement.setNull(9, Types.SMALLINT);
                }
                statement.executeUpdate();
            }
        }
        disconnect();
        return id;
    }

    public void updateStatement(ListStatement pStatement) throws SQLException {
        PreparedStatement statement = connect("DELETE FROM statementparameters WHERE statement_id=?");
        statement.setInt(1, pStatement.getId());
        statement.executeUpdate();
        statement.close();
        statement = mConnection.prepareStatement("UPDATE statements SET name=?, statement=? WHERE id=?");
        statement.setString(1, pStatement.getName());
        statement.setString(2, pStatement.getStatement());
        statement.setInt(3, pStatement.getId());
        statement.executeUpdate();
        statement.close();
        String sql = "INSERT INTO statementparameters(statement_id, param_value, param_type, is_variable, param_index, variable_input, variable_lookup, variable_lookup_valuecolumn, variable_lookup_labelcolumn) VALUES(" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ")";
        statement = mConnection.prepareStatement(sql);

        List<Parameter> parameters = pStatement.getParameters();
        for (Parameter parameter : parameters) {
            statement.setInt(1, pStatement.getId());
            statement.setString(2, parameter.getValue());
            statement.setString(3, parameter.getType().getName());
            statement.setBoolean(4, parameter.isVariable());
            statement.setInt(5, parameter.getIndex());
            statement.setString(6, parameter.getVariableInput());
            statement.setString(7, parameter.getVariableLookup());
            if (parameter.getVariableLookupValuecolumn() != null) {
                statement.setShort(8, parameter.getVariableLookupValuecolumn());
            }
            else {
                statement.setNull(8, Types.SMALLINT);
            }
            if (parameter.getVariableLookupLabelcolumn() != null) {
                statement.setShort(9, parameter.getVariableLookupLabelcolumn());
            }
            else {
                statement.setNull(9, Types.SMALLINT);
            }
            statement.executeUpdate();
        }
        disconnect();
    }

    public ListStatement getStatement(int pId, boolean pConnect) throws SQLException {
        PreparedStatement statement;
        String sql = "SELECT id, name, statement FROM statements WHERE id=?";
        if (pConnect) {
            statement = connect(sql);
        }
        else {
            statement = mConnection.prepareStatement(sql);
        }
        statement.setInt(1, pId);
        ResultSet rs = statement.executeQuery();
        ListStatement ls = null;
        if (rs.next()) {
            ls = new ListStatement(rs.getInt(1), rs.getString(2), rs.getString(3));
            rs.close();
            statement.close();
            statement = mConnection.prepareStatement("SELECT id, param_value, param_type, is_variable, param_index, variable_input, variable_lookup, variable_lookup_valuecolumn, variable_lookup_labelcolumn FROM statementparameters WHERE statement_id=?");
            statement.setInt(1, pId);
            rs = statement.executeQuery();
            List<Parameter> parameters = new LinkedList<Parameter>();
            while (rs.next()) {
                Parameter p = new Parameter(rs.getInt(1), rs.getString(2), ParamTypes.find(rs.getString(3)), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getString(7), null, null);
                short b = rs.getShort(8);
                if (rs.wasNull()) {
                    p.setVariableLookupValuecolumn(null);
                }
                else {
                    p.setVariableLookupValuecolumn(b);
                }
                b = rs.getShort(9);
                if (rs.wasNull()) {
                    p.setVariableLookupLabelcolumn(null);
                }
                else {
                    p.setVariableLookupLabelcolumn(b);
                }
                parameters.add(p);
            }
            ls.setParameters(parameters);
        }
        if (pConnect) {
            disconnect();
        }
        return ls;
    }

    public ListStatement getStatement(int pId) throws SQLException {
        return getStatement(pId, true);
    }

    public ListStatementResult executeStatement(ListStatement pStatement, boolean pConnect) throws SQLException {
        PreparedStatement statement;
        if(DEBUG) System.out.println("[StatementsDBImpl.executeStatement(176)] Statement: " + pStatement.getStatement());
        if(DEBUG) System.out.println("[StatementsDBImpl.executeStatement(177)] Parameters: " + pStatement.getParameters().size());
        if (pConnect) {
            statement = connect(pStatement.getStatement());
        }
        else {
            statement = mConnection.prepareStatement(pStatement.getStatement());
        }
        List<Parameter> parameters = pStatement.getParameters();
        for (Parameter parameter : parameters) {
            if(DEBUG) System.out.println("[StatementsDBImpl.executeStatement(186)] Parameter " + parameter.getIndex() + ": " + parameter.getValue());
            parameter.getType().setStatementValue(statement, parameter.getValue(), parameter.getIndex());
        }
        ResultSet rs = statement.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        List<ListStatementResultColumns> headers = new LinkedList<ListStatementResultColumns>();
        int cols = metaData.getColumnCount();
        for (int i = 0; i < cols; i++) {
            int index = i + 1;
            ListStatementResultColumns header = new ListStatementResultColumns(
                    metaData.getColumnLabel(index),
                    metaData.getColumnType(index),
                    metaData.getColumnClassName(index),
                    metaData.isNullable(index) == ResultSetMetaData.columnNullable);
            headers.add(header);
        }
        List<List<Object>> rows = new LinkedList<List<Object>>();
        while (rs.next()) {
            List<Object> row = new LinkedList<Object>();
            for (int i = 0; i < cols; i++) {
                int index = i + 1;
                row.add(rs.getObject(index));
            }
            rows.add(row);
        }
        if (pConnect) {
            disconnect();
        }
        return new ListStatementResult(headers, rows, pStatement);
    }

    public ListStatementResult executeStatement(ListStatement pStatement) throws SQLException {
        return executeStatement(pStatement, true);
    }

    public ListStatementResult executeStatement(int pStatementId) throws SQLException {
        connect();
        ListStatement statement = getStatement(pStatementId, false);
        ListStatementResult res = executeStatement(statement, false);
        disconnect();
        return res;
    }

    public Vector<IntWithString> listStatements() throws SQLException, RemoteException {
        PreparedStatement statement = connect("SELECT id, name FROM statements ORDER BY name");
        ResultSet rs = statement.executeQuery();
        Vector<IntWithString> list = new Vector<IntWithString>();
        while (rs.next()) {
            list.add(new IntWithString(rs.getInt(1), rs.getString(2)));
        }
        disconnect();
        return list;
    }

    public int addStatement(ListStatement pStatement) throws SQLException {
        return addStatement(pStatement.getName(), pStatement.getStatement(), pStatement.getParameters());
    }

    public void removeStatement(int pStatementId) throws SQLException, RemoteException {
        PreparedStatement statement = connect("DELETE FROM statements WHERE id=?");
        statement.setInt(1, pStatementId);
        int res = statement.executeUpdate();
        if (DEBUG) System.out.println("[StatementsDBImpl.removeStatement(189)] Res: " + res);
        statement.close();
        statement = mConnection.prepareStatement("DELETE FROM statementparameters WHERE statement_id=?");
        statement.setInt(1, pStatementId);
        res = statement.executeUpdate();
        if (DEBUG) System.out.println("[StatementsDBImpl.removeStatement(194)] res: " + res);
        disconnect();
    }

    public List<List<Object>> getRows(String pSql) throws SQLException, RemoteException {
        if(DEBUG) System.out.println("[StatementsDBImpl.getRows(255)] SQL: " + pSql);
        PreparedStatement statement = connect(pSql);
        ResultSet rs = statement.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<List<Object>> rows = new LinkedList<List<Object>>();
        while(rs.next()) {
            List<Object> row = new ArrayList<Object>();
            for(int i = 0; i < columnCount; i++) {
                row.add(rs.getObject(i+1));
            }
            rows.add(row);
        }
        disconnect();
        return rows;
    }
}
