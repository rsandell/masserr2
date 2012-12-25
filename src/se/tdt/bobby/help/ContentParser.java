package se.tdt.bobby.help;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-07 19:27:24
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ContentParser {
    private static final boolean DEBUG = false;
    private HashMap<String, String> mMeritsNflawsMap;

    public ContentParser() throws IOException {
        parse();
    }

    public void parse() throws IOException {
        String file = readFile("help/Merits&Flaws.htm");
        mMeritsNflawsMap = new HashMap<String, String>();
        boolean run = true;
        int start = 0;
        int end = file.indexOf("<h3>");
        int testEnd1 = -1;
        int testEnd2 = -1;
        //String section = file.substring(start, end);
        //list.add(section.trim());
        while (run) {
            start = end;
            end = file.indexOf("<h3>", start + 4);
            testEnd1 = file.indexOf("<h1>", start + 4);
            if(testEnd1 > 0 && testEnd1 < end) {
                end = testEnd1;
            }
            testEnd2 = file.indexOf("<h2>", start + 4);
            if(testEnd2 > 0 && testEnd2 < end) {
                end = testEnd2;
            }
            if (end < 0) {
                end = file.indexOf("</body>", start + 4);
                run = false;
            }
            String section = file.substring(start, end);
            int startname = section.indexOf("<a name=\"");
            int endName = section.indexOf("\"", startname + 9);
            String name = section.substring(startname + 9, endName);
            mMeritsNflawsMap.put(name.toLowerCase(), section.trim());

        }
    }

    public String get(String pName) {
        String content = mMeritsNflawsMap.get(pName.toLowerCase());
        if (content == null) {
            int end = pName.length() -1;
            pName = pName.toLowerCase();
            while(end > 0) {
                String name = pName.substring(0, end);
                content = mMeritsNflawsMap.get(name);
                if(content != null) {
                    return content;
                }
                end--;
            }
            return null;
        }
        else {
            return content;
        }
    }

    public String getEscapeSingleQuotes(String pName) {
        String re = get(pName);
        if(re == null) {
            return null;
        } else {
            re = re.replace("'", "\\'");
            return re.replace("\"", "\\\"");
        }
    }

    public ParsedMerit getInfo(String pName) {
        String string = get(pName);
        if(string == null) {
            return null;
        } else {
            int beginStrip = string.indexOf("<a name=\"");
            string = string.substring(beginStrip +9);
            beginStrip = string.indexOf("\">");
            string = string.substring(beginStrip + 2);
            int endStrip = string.indexOf("</h3>");
            String header = string.substring(0, endStrip);
            header = header.replace("</a>", "");
            beginStrip = string.indexOf(">", endStrip + 5);
            string = string.substring(beginStrip + 1);
            string = string.replace("</p>", "\n");
            string = string.replace("<p class=default>", "");
            string = string.replace("<i>", "");
            string = string.replace("</i>", "");
            string = string.replace("<b>", "");
            string = string.replace("</b>", "");
            string = string.replace("<u>", "");
            string = string.replace("</u>", "");
            return new ParsedMerit(header, string);
        }
    }

    private String readFile(String fileName) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        byte[] bytes = new byte[(int) file.length()];
        file.readFully(bytes);
        return new String(bytes);
    }
}
