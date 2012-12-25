package se.tdt.bobby.wodcc.data.sqllists;

import java.util.List;
import java.io.Serializable;

/**
 * Created: 2006-jul-24 21:07:26
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ListStatement implements Serializable {
    private int mId;
    private String mName;
    private String mStatement;
    private List<Parameter> mParameters;

    public ListStatement(int pId, String pName, String pStatement, List<Parameter> pParameters) {
        mId = pId;
        mName = pName;
        mStatement = pStatement;
        mParameters = pParameters;
    }

    public ListStatement(String pName, String pStatement, List<Parameter> pParameters) {
        mName = pName;
        mStatement = pStatement;
        mParameters = pParameters;
    }

    public ListStatement(int pId, String pName, String pStatement) {
        mId = pId;
        mName = pName;
        mStatement = pStatement;
    }

    public ListStatement() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int pPId) {
        mId = pPId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public String getStatement() {
        return mStatement;
    }

    public void setStatement(String pStatement) {
        mStatement = pStatement;
    }

    public List<Parameter> getParameters() {
        return mParameters;
    }

    public void setParameters(List<Parameter> pParameters) {
        mParameters = pParameters;
    }
}
