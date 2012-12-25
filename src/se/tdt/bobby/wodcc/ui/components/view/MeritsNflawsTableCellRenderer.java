package se.tdt.bobby.wodcc.ui.components.view;

import se.tdt.bobby.help.ContentParser;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-07 21:25:04
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class MeritsNflawsTableCellRenderer extends DefaultTableCellRenderer {
    private ContentParser mContentParser;
    private static final boolean DEBUG = false;

    public MeritsNflawsTableCellRenderer() throws IOException {
        mContentParser = new ContentParser();
        ToolTipManager.sharedInstance().setDismissDelay(20000);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label =  (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String description = mContentParser.get(label.getText());
        if(description != null) {
            label.setToolTipText("<html><div width=\"250\">" + description + "</div></html>");

        } else {
            label.setToolTipText(null);
        }
        return label;
    }
}
