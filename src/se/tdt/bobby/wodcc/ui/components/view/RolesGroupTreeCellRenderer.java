package se.tdt.bobby.wodcc.ui.components.view;

import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.data.RolesGroup;
import se.tdt.bobby.wodcc.logic.IconFactory;

import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.io.File;

/**
 * Description
 *
 * Created: 2004-feb-18 19:28:46
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class RolesGroupTreeCellRenderer extends DefaultTreeCellRenderer {
    private static final boolean DEBUG = false;
    private HashMap mClanIcons;
    private IconFactory mIconFactory;

    public RolesGroupTreeCellRenderer() {
        mIconFactory = IconFactory.getIconFactory();
    }

    /**
     * Configures the renderer based on the passed in components.
     * The value is set from messaging the tree with
     * <code>convertValueToText</code>, which ultimately invokes
     * <code>toString</code> on <code>value</code>.
     * The foreground color is set based on the selection and the icon
     * is set based on on leaf and expanded.
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel,
                                                                   expanded, leaf,
                                                                   row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object o = node.getUserObject();
            if (o instanceof se.tdt.bobby.wodcc.data.Role) {
                se.tdt.bobby.wodcc.data.Role role = (se.tdt.bobby.wodcc.data.Role) o;
                ImageIcon icon = mIconFactory.getClanIcon16(role.getClan().getId());
                if (icon != null) {
                    label.setIcon(icon);
                }
                if (role.isGhoul()) {
                    label.setText("<html><i>" + label.getText() + "</i></html>");
                }
            }
            else if (o instanceof se.tdt.bobby.wodcc.data.RolesGroup) {
                se.tdt.bobby.wodcc.data.RolesGroup group = (se.tdt.bobby.wodcc.data.RolesGroup) o;
                label.setText("<html><b>" + label.getText() + "</b></html>");
                ImageIcon icon = mIconFactory.getRolesGroupTypeIcon(group.getType());
                if(DEBUG) System.out.println("[RolesGroupTreeCellRenderer][getTreeCellRendererComponent][64] icon is " + icon);
                if(icon != null) {
                    label.setIcon(icon);
                } else {
                    if(expanded) {
                        label.setIcon(this.openIcon);
                    } else {
                        label.setIcon(this.closedIcon);
                    }
                }
            }
        }

        return label;
    }
}
