package se.tdt.bobby.help;

import java.io.Serializable;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-12 17:56:35
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ParsedMerit implements Serializable, Cloneable {
    private String mHead;
    private String mContent;
    private static final boolean DEBUG = false;

    public ParsedMerit(String pHead, String pContent) {
        if(DEBUG) System.out.println("ParsedMerit.ParsedMerit(17) head: " + pHead + "\nContent: " + pContent);
        mHead = pHead;
        mContent = pContent;
    }

    public ParsedMerit() {

    }

    public String getHead() {
        return mHead;
    }

    public String getEscapedHead() {
        return mHead.replace("'", "\\'").replace("\n", " ").trim();
    }

    public void setHead(String pHead) {
        mHead = pHead;
    }

    public String getContent() {
        return mContent;
    }

    public String getEscapedContent() {
        return mContent.replace("'", "\\'").replace("\n", " ").trim();
    }

    public void setContent(String pContent) {
        mContent = pContent;
    }

    public String toString() {
        return "<h3>" + mHead + "</h3>\n" +
                "<p class=\"default\">" + mContent + "</p>";
    }

    public Object clone() {
        return new ParsedMerit(mHead, mContent);
    }
}
