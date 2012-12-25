package se.tdt.bobby.wodcc.ui.components.view;

import se.tdt.bobby.wodcc.logic.IconFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-19 12:20:02
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class RolesListCellRenderer extends DefaultListCellRenderer {
    private static final boolean DEBUG = false;
    private IconFactory mIconFactory;

    public RolesListCellRenderer() {
        mIconFactory = IconFactory.getIconFactory();
    }

    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof se.tdt.bobby.wodcc.data.Role) {

            se.tdt.bobby.wodcc.data.Role role = (se.tdt.bobby.wodcc.data.Role) value;
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                ImageIcon icon = mIconFactory.getClanIcon(role.getClan().getId());
                if (icon != null) {
                    label.setIcon(icon);
                }
                if(role.isSLP()) {
                    label.setText(label.getText() + " (SLP)");
                }
                if (role.isGhoul()) {
                    label.setText("<html><i>" + label.getText() + "</i></html>");
                }
                if (role.getPlayerName() != null && role.getPlayerName().length() > 0) {
                        label.setToolTipText(role.getPlayerName());
                }
                else if (role.getQuote() != null && role.getQuote().length() > 0) {
                        label.setToolTipText("<html><div width=\"201\">" +
                                             role.getQuote() + "</div></html>");                    
                }
                else {
                    label.setToolTipText(null);
                }
                return label;
            }
        }
        return c;
    }
}
