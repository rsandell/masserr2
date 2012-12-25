package se.tdt.bobby.wodcc.data.sqllists;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created: 2006-jul-24 20:25:21
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public abstract class ParamType implements Serializable {
    private String mName;

    protected ParamType(String pName) {
        mName = pName;
        ArrayList l = new ArrayList();
    }

    protected ParamType() {
    }

    public String getName() {
        return mName;
    }

    public String toString() {
        return mName;
    }


    public abstract Object parse(String pValue);

    public abstract void setStatementValue(PreparedStatement pStatement, Object pValue, int pIndex) throws SQLException;
}
