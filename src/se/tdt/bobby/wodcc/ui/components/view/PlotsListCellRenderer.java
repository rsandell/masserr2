package se.tdt.bobby.wodcc.ui.components.view;

import se.tdt.bobby.wodcc.data.Plot;

import javax.swing.*;
import java.awt.*;

/**
 * Description.
 * <p/>
 * Created: 2004-jun-17 16:41:49
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class PlotsListCellRenderer extends DefaultListCellRenderer {
    private static final boolean DEBUG = false;

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Plot) {
            Plot p = (Plot) value;
            if (p.getId() < 10) {
                label.setText(p.getId() + ":  ");
            }
            else {
                label.setText(p.getId() + ": ");
            }
            label.setText(label.getText() + p.getTitle());
            if (p.getDescription() != null) {
                label.setToolTipText("<html><div width=\"250\">" + p.getDescription() + "</div></html>");
            }
            else {
                label.setToolTipText(null);
            }
        }
        return label;
    }
}
