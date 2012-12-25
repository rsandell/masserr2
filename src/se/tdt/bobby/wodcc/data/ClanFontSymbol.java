package se.tdt.bobby.wodcc.data;

import java.io.Serializable;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-27 01:32:29
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ClanFontSymbol implements Serializable, Cloneable {
    private int mClanId;
    private String mFontName;
    private char mCharacter;

    public ClanFontSymbol(int pClanId, String pFontName, char pCharacter) {
        mClanId = pClanId;
        mFontName = pFontName;
        mCharacter = pCharacter;
    }

    /**
     * For serialization
     */
    public ClanFontSymbol() {
    }

    public Object clone() {
        return new ClanFontSymbol(mClanId, mFontName, mCharacter);
    }

    public int getClanId() {
        return mClanId;
    }

    public void setClanId(int pClanId) {
        mClanId = pClanId;
    }

    public String getFontName() {
        return mFontName;
    }

    public void setFontName(String pFontName) {
        mFontName = pFontName;
    }

    public char getCharacter() {
        return mCharacter;
    }

    public void setCharacter(char pCharacter) {
        mCharacter = pCharacter;
    }

    public String toString() {
        return "<font face=\"" + mFontName + "\">" + mCharacter + "</font>";
    }
}
