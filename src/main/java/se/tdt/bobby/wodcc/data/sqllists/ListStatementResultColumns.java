package se.tdt.bobby.wodcc.data.sqllists;

import java.io.Serializable;

/**
 * Created: 2006-jul-24 21:31:44
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ListStatementResultColumns implements Serializable {
    private String mName;
    private int mType;
    private String mColumnClassName;
    private boolean mNullable;
    private Class mColumnClass = null;

    public ListStatementResultColumns(String pName, int pType, String pColumnClassName, boolean pNullable) {
        mName = pName;
        mType = pType;
        mColumnClassName = pColumnClassName;
        mNullable = pNullable;
    }

    public ListStatementResultColumns(String pName, int pType, String pColumnClassName) {
        mName = pName;
        mType = pType;
        mColumnClassName = pColumnClassName;
    }

    public ListStatementResultColumns(String pName, int pType, boolean pNullable) {
        mName = pName;
        mType = pType;
        mNullable = pNullable;
    }

    public ListStatementResultColumns(String pName, int pType) {
        mName = pName;
        mType = pType;
    }

    public ListStatementResultColumns() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public int getType() {
        return mType;
    }

    public void setType(int pType) {
        mType = pType;
    }

    public boolean isNullable() {
        return mNullable;
    }

    public void setNullable(boolean pNullable) {
        mNullable = pNullable;
    }

    public String getColumnClassName() {
        return mColumnClassName;
    }

    public void setColumnClassName(String pColumnClassName) {
        mColumnClassName = pColumnClassName;
    }

    public Class getColumnClass() throws ClassNotFoundException {
        if(mColumnClass == null) {
            mColumnClass = Class.forName(mColumnClassName);
        }
        return mColumnClass; 
    }

    public Object getColumnObjectOfClass() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class c = getColumnClass();
        return c.newInstance();
    }
}
