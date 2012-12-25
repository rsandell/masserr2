package se.tdt.bobby.wodcc.logic;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-26 23:40:10
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class HTMLUtil {
    public static String toHTMLformat(String pPreFormatedText) {
        if(pPreFormatedText == null) {
            return null;
        }
        String str = pPreFormatedText.replace(">", "&gt;");
        str = str.replace("<", "&lt;");
        str = str.replace("&", "&amp;");
        str = str.replace("\n", "<br/>");
        str = str.replace("[b]", "<b>");
        str = str.replace("[/b]", "</b>");
        str = str.replace("[i]", "<i>");
        str = str.replace("[/i]", "</i>");
        str = str.replace("[u]", "<u>");
        str = str.replace("[/u]", "</u>");
        str = str.replace("\"", "&quot;");
        str = str.replace("å", "&aring;");
        str = str.replace("ä", "&auml;");
        str = str.replace("ö", "&ouml;");
        return str;
    }

    public static String toHTMLformatNoBR(String pPreFormatedText) {
        if(pPreFormatedText == null) {
            return null;
        }
        String str = pPreFormatedText.replace(">", "&gt;");
        str = str.replace("<", "&lt;");
        str = str.replace("&", "&amp;");
        //str = str.replace("\n", "<br/>");
        str = str.replace("[b]", "<b>");
        str = str.replace("[/b]", "</b>");
        str = str.replace("[i]", "<i>");
        str = str.replace("[/i]", "</i>");
        str = str.replace("[u]", "<u>");
        str = str.replace("[/u]", "</u>");
        str = str.replace("\"", "&quot;");
        str = str.replace("å", "&aring;");
        str = str.replace("ä", "&auml;");
        str = str.replace("ö", "&ouml;");
        return str;
    }

    public String toHTML(String pPreFormatedText) {
        return toHTMLformat(pPreFormatedText);
    }

    public String toHTMLnoBR(String pPreFormatedText) {
        return toHTMLformatNoBR(pPreFormatedText);
    }
}
