package se.tdt.bobby.wodcc.data.sqllists;

import java.io.Serializable;
import java.util.List;

/**
 * Created: 2006-jul-24 21:29:54
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ListStatementResult implements Serializable {
    private List<ListStatementResultColumns> mHeaders;
    private List<List<Object>> mRows;
    private ListStatement mStatement;

    public ListStatementResult(List<ListStatementResultColumns> pHeaders, List<List<Object>> pRows, ListStatement pStatement) {
        mHeaders = pHeaders;
        mRows = pRows;
        mStatement = pStatement;
    }

    public ListStatementResult(List<ListStatementResultColumns> pHeaders, List<List<Object>> pRows) {
        mHeaders = pHeaders;
        mRows = pRows;
    }

    public ListStatementResult(ListStatement pStatement) {
        mStatement = pStatement;
    }

    public ListStatementResult() {
    }

    public List<ListStatementResultColumns> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(List<ListStatementResultColumns> pHeaders) {
        mHeaders = pHeaders;
    }

    public List<List<Object>> getRows() {
        return mRows;
    }

    public void setRows(List<List<Object>> pRows) {
        mRows = pRows;
    }

    public ListStatement getStatement() {
        return mStatement;
    }

    public void setStatement(ListStatement pStatement) {
        mStatement = pStatement;
    }
}
