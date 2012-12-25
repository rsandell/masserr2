package se.tdt.bobby.wodcc.logic.sqllist;

import se.tdt.bobby.wodcc.data.sqllists.ListStatement;
import se.tdt.bobby.wodcc.data.sqllists.ListStatementResult;
import se.tdt.bobby.wodcc.data.sqllists.ListStatementResultColumns;
import se.tdt.bobby.wodcc.data.sqllists.Parameter;
import se.tdt.bobby.wodcc.ui.sqllist.components.models.RowsComboBoxData;

import java.util.List;
import java.util.ArrayList;

/**
 * Created: 2006-jul-24 22:58:25
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class SqlListUtil {

    public static int countParameters(String pStatement) {
        int count = 0;
        for (int i = 0; i < pStatement.length(); i++) {
            char c = pStatement.charAt(i);
            if (c == '?') {
                count++;
            }
        }
        return count;
    }

    public static boolean containsVariables(ListStatement pStatement) {
        List<Parameter> list = pStatement.getParameters();
        for (Parameter parameter : list) {
            if (parameter.isVariable()) {
                return true;
            }
        }
        return false;
    }

    public static List<RowsComboBoxData> makeComboBoxData(List<List<Object>> pRows, short pValueColumn, short pLabelColumn) {
        List<RowsComboBoxData> list = new ArrayList<RowsComboBoxData>();
        for (List<Object> row : pRows) {
            RowsComboBoxData data = new RowsComboBoxData(row.get(pLabelColumn-1), row.get(pValueColumn-1));
            list.add(data);
        }
        return list;
    }

    public static String generateHtmlTable(ListStatementResult pResult) {
        StringBuffer str = new StringBuffer("<html>\n" +
                "<head>\n" +
                "<title>");
        str.append(toHtml(pResult.getStatement().getName()));

        str.append("</title>\n" +
                "</head>\n" +
                "<body>" +
                "<table border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n" +
                "<THEAD>\n" +
                "<tr>");
        List<ListStatementResultColumns> headers = pResult.getHeaders();
        for (ListStatementResultColumns column : headers) {
            str.append("\t<th>");
            str.append(toHtml(column.getName()));
            str.append("</th>\n");
        }
        str.append("</tr>\n" +
                "<THEAD>\n" +
                "<TBODY>\n");
        List<List<Object>> rows = pResult.getRows();
        for (List<Object> row : rows) {
            str.append("<tr>\n");
            for (Object o : row) {
                if(o instanceof Number) {
                    str.append("\t<td align=\"right\">");
                }
                else if(o instanceof Boolean) {
                    str.append("\t<td align=\"center\">");
                }
                else {
                    str.append("\t<td align=\"left\">");
                }
                str.append(o != null ? toHtml(o.toString()) : "<i>NULL</i>");
                str.append("</td>\n");
            }
            str.append("</tr>\n");
        }
        str.append("</TBODY>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>");
        return str.toString();
    }

    protected static String toHtml(String pStr) {
        if (pStr == null) return "";
        pStr = pStr.replace("&", "&amp;");
        pStr = pStr.replace("<", "&lt;");
        pStr = pStr.replace(">", "&gt;");
        return pStr.replace("\"", "&quot;");
    }
}
