package se.tdt.bobby.wodcc.admin.ui.components.views;

import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.data.mgm.User;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.*;
import java.awt.*;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-15 01:02:47
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class UsersTreeCellRenderer extends DefaultTreeCellRenderer {
    private ImageIcon mUserIcon;
    private static final boolean DEBUG = false;

    public UsersTreeCellRenderer() {
        mUserIcon = IconFactory.getIconFactory().getIcon("User24.gif");
        Image smallerImage = mUserIcon.getImage().getScaledInstance(-1, 16, Image.SCALE_SMOOTH);
        mUserIcon = new ImageIcon(smallerImage);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if(node.getUserObject() instanceof User) {
            label.setIcon(mUserIcon);
        }
        return label;
    }
}
