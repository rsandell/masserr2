package se.tdt.bobby.wodcc.ui;

import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.data.RolesGroup;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.proxy.interfaces.GroupsDB;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.view.RolesGroupTreeCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.WhiteJPanel;
import se.tdt.bobby.wodcc.ui.dialogs.NewRolesGroupDialog;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * Description
 * <p/>
 * Created: 2004-feb-17 21:17:23
 *
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class GroupsFrame extends JFrame implements TreeExpansionListener, TreeSelectionListener {
    private JTree mTree;
    private GroupsDB mGroupsDB;
    private static final boolean DEBUG = false;
    private MainFrame mMainFrame;
    private JLabel mClanLabel;
    private Box mRoleBox;
    private JLabel mRoleNameLabel;
    private JLabel mPlayerNameLabel;
    private JLabel mGhoulLabel;
    private GridBagPanel mGroupBox;
    private JLabel mGroupName;
    private JLabel mGroupDate;
    private JTextArea mGroupDescriptionArea;
    private JLabel mGroupType;
    private MutableAction mAddChildGroupAction;
    private MutableAction mAddSiblingGroupAction;
    private MutableAction mRemoveAction;
    private JPopupMenu mPopup;
    private MutableAction mAddGroupAction;
    private MutableAction mUpdateAction;
    private MutableAction mEditAction;
    private MutableAction mInsertXPAction;

    public void treeExpanded(final TreeExpansionEvent event) {
        Runnable runnable = new Runnable() {
            public void run() {
                if (DEBUG) System.out.println("[GroupsFrame][run][33] " + event);
                TreePath path = event.getPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object o = node.getUserObject();
                if (o instanceof RolesGroup && node.getChildCount() <= 1) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getFirstChild();
                    if (child.getUserObject() instanceof String) {
                        RolesGroup group = (RolesGroup) o;
                        try {
                            List groupsInGroup = mGroupsDB.getGroupsInGroup(group.getId());
                            List roles = mGroupsDB.getRolesInGroup(group.getId());

                            node = (DefaultMutableTreeNode) setChildrenOnGroupNode(groupsInGroup, roles, node);
                            path = new TreePath(node.getPath());
                            if (!mTree.isExpanded(path)) {
                                mTree.expandPath(path);
                            }
                            mTree.setSelectionPath(path);
                        }
                        catch (Exception e) {
                            if (DEBUG) e.printStackTrace();
                            report(e);
                        }
                    }
                }
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    private MutableTreeNode setChildrenOnGroupNode(List pGroups, List pRoles, DefaultMutableTreeNode pNode) {
        if (DEBUG) System.out.println("[GroupsFrame][setChildrenOnGroupNode][51] size: " + pRoles.size());
        if (pRoles.size() <= 0 && pGroups.size() <= 0) {
            if (DEBUG) System.out.println("[GroupsFrame][setChildrenOnGroupNode][53] adding nothing");
            DefaultMutableTreeNode rNode = (DefaultMutableTreeNode) pNode.getChildAt(0);
            rNode.setUserObject("Nothing in this group");
            mTree.getModel().valueForPathChanged(new TreePath(rNode.getPath()), "Nothing in this group");
        }
        else {
            DefaultTreeModel model = (DefaultTreeModel) mTree.getModel();
            DefaultMutableTreeNode rNode = (DefaultMutableTreeNode) pNode.getChildAt(0);
            /*Enumeration enum = pNode.children();
            while (enum.hasMoreElements()) {
                MutableTreeNode treeNode = (MutableTreeNode) enum.nextElement();
                model.removeNodeFromParent(treeNode);
            }*/
            //pNode.removeAllChildren();
            //model.reload(pNode);
            Object userObj = pNode.getUserObject();
            MutableTreeNode parent = (MutableTreeNode) pNode.getParent();
            int index = model.getIndexOfChild(pNode.getParent(), pNode);
            model.removeNodeFromParent(pNode);
            pNode = new DefaultMutableTreeNode(userObj);
            for (int i = 0; i < pGroups.size(); i++) {
                RolesGroup rolesGroup = (RolesGroup) pGroups.get(i);
                rNode = new DefaultMutableTreeNode(rolesGroup);
                rNode.add(new DefaultMutableTreeNode("Loading..."));
                pNode.add(rNode);
            }
            for (int i = 0; i < pRoles.size(); i++) {
                Object role = pRoles.get(i);
                rNode = new DefaultMutableTreeNode(role);
                if (DEBUG) System.out.println("[GroupsFrame][setChildrenOnGroupNode][65] adding " + rNode);
                pNode.add(rNode);
            }
            model.insertNodeInto(pNode, parent, index);
            //model.reload(pNode);
        }
        return pNode;
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    public void treeCollapsed(TreeExpansionEvent event) {
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        updateViewPane(path);
    }

    private void updateViewPane(TreePath pPath) {
        Object nodeObj = pPath.getLastPathComponent();
        if (nodeObj instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeObj;
            if (node.getUserObject() instanceof Role) {

                mAddChildGroupAction.setEnabled(false);
                mAddSiblingGroupAction.setEnabled(true);
                mEditAction.setEnabled(false);

                Role role = (Role) node.getUserObject();
                mRoleBox.setVisible(true);
                mGroupBox.setVisible(false);
                mClanLabel.setText(role.getClan().getName());
                Icon i = IconFactory.getIconFactory().getClanIcon(role.getClan().getId());
                mClanLabel.setIcon(i);
                mRoleNameLabel.setText("Name: " + role.getName());
                mPlayerNameLabel.setText("Player: " + role.getPlayerName());
                if (role.isGhoul()) {
                    mGhoulLabel.setText("Ghoul");
                }
                else {
                    mGhoulLabel.setText("");
                }
            }
            else if (node.getUserObject() instanceof RolesGroup) {

                mAddChildGroupAction.setEnabled(true);
                mAddSiblingGroupAction.setEnabled(true);
                mEditAction.setEnabled(true);

                RolesGroup group = (RolesGroup) node.getUserObject();
                mRoleBox.setVisible(false);
                mGroupBox.setVisible(true);
                mGroupName.setText("Name: " + group.getName());
                mGroupType.setText("Type: " + group.getType());
                Icon icon = IconFactory.getIconFactory().getRolesGroupTypeIcon(group.getType());
                mGroupType.setIcon(icon);
                DateFormat format = DateFormat.getDateInstance();
                mGroupDate.setText("Date: " + format.format(group.getDate()));
                mGroupDescriptionArea.setText(group.getDescription());
            }
        }
    }

    /*
    * TODO fetch Icons from server
    */

    public GroupsFrame(MainFrame pMainFrame) throws SQLException, RemoteException {
        super("Groups");
        mMainFrame = pMainFrame;
        mGroupsDB = Proxy.getGroupsDB();
        TreeNode rootNode = createRootNode();
        mTree = new JTree(rootNode);
        mTree.setRootVisible(false);
        mTree.addTreeExpansionListener(this);
        mTree.setCellRenderer(new RolesGroupTreeCellRenderer());
        mTree.addTreeSelectionListener(this);
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JScrollPane scroll = new JScrollPane(mTree);
        scroll.setPreferredSize(new Dimension(100, -1));
        pane.add(scroll);
        JPanel panel = new WhiteJPanel();
        mRoleBox = Box.createVerticalBox();
        mClanLabel = new JLabel("Clan: ");
        mRoleBox.add(mClanLabel);
        mRoleNameLabel = new JLabel("Role Name: ");
        mRoleBox.add(mRoleNameLabel);
        mPlayerNameLabel = new JLabel("Player: ");
        mRoleBox.add(mPlayerNameLabel);
        mGhoulLabel = new JLabel();
        mRoleBox.add(mGhoulLabel);
        panel.add(mRoleBox);
        mGroupBox = new GridBagPanel();
        mGroupBox.setBackground(Color.white);
        mGroupName = new JLabel("Name: ");
        mGroupBox.addLine(mGroupName);
        mGroupType = new JLabel("Type: ");
        mGroupBox.addLine(mGroupType);
        mGroupDate = new JLabel("Date: ");
        mGroupBox.addLine(mGroupDate);
        mGroupDescriptionArea = new JTextArea(4, 30);
        mGroupDescriptionArea.setWrapStyleWord(true);
        mGroupDescriptionArea.setLineWrap(true);
        mGroupDescriptionArea.setEditable(false);
        mGroupBox.addLine(new JScrollPane(mGroupDescriptionArea));
        panel.add(mGroupBox);
        mRoleBox.setVisible(false);
        mGroupBox.setVisible(false);
        pane.add(panel);
        getContentPane().add(pane, BorderLayout.CENTER);
        setIconImage(mMainFrame.getIconImage());
        setSize(640, 480);
        Utilities.positionMe(this, mMainFrame);
        createActions();
        mPopup = new JPopupMenu("Selection");
        mPopup.add(mInsertXPAction);
        mPopup.addSeparator();
        mPopup.add(mAddGroupAction);
        mPopup.add(mAddChildGroupAction);
        mPopup.add(mAddSiblingGroupAction);
        mPopup.addSeparator();
        mPopup.add(mUpdateAction);
        mPopup.addSeparator();
        mPopup.add(mEditAction);
        mPopup.addSeparator();
        mPopup.add(mRemoveAction);
        mTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = mTree.getRowForLocation(e.getX(), e.getY());
                    if (row >= 0) {
                        if (mTree.getSelectionRows() != null && mTree.getSelectionRows().length > 0) {
                            if (e.isControlDown()) {
                                mTree.addSelectionRow(row);
                            }
                            else if (mTree.getSelectionRows().length == 1) {
                                mTree.setSelectionRow(row);
                            }
                        }
                        else {
                            mTree.setSelectionRow(row);
                        }
                    }
                    mPopup.show(mTree, e.getX(), e.getY());
                }
            }
        });
        setVisible(true);
    }

    private void createActions() {
        mAddGroupAction = new MutableAction("Add Group...") {
            public void actionPerformed(ActionEvent e) {
                newGroup((DefaultMutableTreeNode) mTree.getModel().getRoot());
            }
        };
        mAddChildGroupAction = new MutableAction("Add Group as Child...") {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) mTree.getSelectionPath().getLastPathComponent();
                if (parent != null) {
                    if (parent.getUserObject() instanceof RolesGroup) {
                        newGroup(parent);
                    }
                }
            }
        };
        mAddChildGroupAction.setEnabled(false);
        mAddSiblingGroupAction = new MutableAction("Add Group as Sibling...") {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) mTree.getSelectionPath().getLastPathComponent();
                if (node != null) {
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                    if (parent != null) {
                        newGroup(parent);
                    }
                }
            }
        };
        mAddSiblingGroupAction.setEnabled(false);
        mRemoveAction = new MutableAction("Remove") {
            public void actionPerformed(ActionEvent e) {
                final TreePath[] paths = mTree.getSelectionPaths();
                Runnable runnable = new Runnable() {
                    public void run() {
                        if (paths != null && paths.length > 0) {
                            int choice = JOptionPane.showConfirmDialog(mTree, "WARNING! This will remove all selected roles from its group\n" +
                                                                              "all roles in a selected group will be removed from it and the groups will then be removed.\n" +
                                                                              "Do you want to continue?", "Remove", JOptionPane.YES_NO_OPTION,
                                                                       JOptionPane.WARNING_MESSAGE);
                            if (choice == JOptionPane.YES_OPTION) {
                                HashMap rolesToRemove = new HashMap();
                                List roleNodesToRemove = new ArrayList();
                                List groupsToRemove = new ArrayList();
                                List groupNodesToRemove = new ArrayList();
                                for (int i = 0; i < paths.length; i++) {
                                    TreePath path = paths[i];
                                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                                    Object o = node.getUserObject();
                                    if (o instanceof Role) {
                                        RolesGroup group = (RolesGroup) ((DefaultMutableTreeNode) node.getParent()).getUserObject();
                                        rolesToRemove.put(o, group);
                                        roleNodesToRemove.add(node);
                                    }
                                    else if (o instanceof RolesGroup) {
                                        groupsToRemove.add(o);
                                        groupNodesToRemove.add(node);
                                    }
                                }
                                DefaultTreeModel model = (DefaultTreeModel) mTree.getModel();
                                try {
                                    mGroupsDB.removeRolesFromTheirGroup(rolesToRemove);
                                    for (int i = 0; i < roleNodesToRemove.size(); i++) {
                                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) roleNodesToRemove.get(i);
                                        model.removeNodeFromParent(node);
                                    }
                                    mGroupsDB.deleteGroups(groupsToRemove);
                                    for (int i = 0; i < groupNodesToRemove.size(); i++) {
                                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) groupNodesToRemove.get(
                                                i);
                                        model.removeNodeFromParent(node);
                                    }
                                }
                                catch (Exception e1) {
                                    if (DEBUG) e1.printStackTrace();
                                    report(e1);
                                }
                            }
                        }
                    }

                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mUpdateAction = new MutableAction("Reload Tree") {
            public void actionPerformed(ActionEvent e) {
                DefaultTreeModel model = (DefaultTreeModel) mTree.getModel();
                try {
                    model.setRoot(createRootNode());
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mEditAction = new MutableAction("Edit Group...") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        editGroup();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mEditAction.setEnabled(false);
        mInsertXPAction = new MutableAction("Insert XP") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        insertXP();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
    }

    private void editGroup() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) mTree.getSelectionPath().getLastPathComponent();
        if (node != null && node.getUserObject() instanceof RolesGroup) {
            try {
                RolesGroup group = (RolesGroup) node.getUserObject();
                RolesGroup edited = new RolesGroup(group.getId(), group.getName(), group.getDate(),
                                                   group.getDescription(), group.getType());
                edited = NewRolesGroupDialog.showEditDialog(this, edited);
                if (edited != null) {
                    node.setUserObject(edited);
                    TreePath path = new TreePath(node.getPath());
                    mTree.getModel().valueForPathChanged(path, edited);
                    updateViewPane(path);
                }
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                report(e);
            }
        }
    }

    private void newGroup(DefaultMutableTreeNode pParent) {
        try {
            Integer parentId = null;
            if (pParent != mTree.getModel().getRoot()) {
                RolesGroup gr = (RolesGroup) pParent.getUserObject();
                parentId = new Integer(gr.getId());
            }
            RolesGroup group = NewRolesGroupDialog.showDialog(this, parentId);
            if (group != null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(group);
                node.add(new DefaultMutableTreeNode("Loading..."));
                DefaultTreeModel model = (DefaultTreeModel) mTree.getModel();
                pParent.insert(node, 0);
                //model.insertNodeInto(node, pParent, 0);
                model.reload(pParent);
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private TreeNode createRootNode() throws SQLException, RemoteException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root", true);
        List v = mGroupsDB.getRootGroups();
        for (int i = 0; i < v.size(); i++) {
            RolesGroup rolesGroup = (RolesGroup) v.get(i);
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(rolesGroup);
            node.add(new DefaultMutableTreeNode("Loading..."));
            root.add(node);
        }
        return root;
    }

    private void insertXP(){
        TreePath[] paths = mTree.getSelectionPaths();
        if(paths == null) {
            JOptionPane.showMessageDialog(mTree, "Nothing selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Check to see if there is any selected descendants to other selections
        boolean exsistsDescendants = false;
        for (int i = 0; i < paths.length; i++) {
            TreePath path = paths[i];
            for (int j = 0; j < paths.length; j++) {
                TreePath treePath = paths[j];
                if (path != treePath) {
                    if (path.isDescendant(treePath)) {
                        exsistsDescendants = true;
                        break;
                    }
                }
            }
            if (exsistsDescendants) {
                break;
            }
        }
        boolean cont = true;
        if (exsistsDescendants) {
            int choice = JOptionPane.showConfirmDialog(mTree, "There are selected groups and/or roles which are children to other selected groups.\n" +
                                                              "If you answer YES those roles will get the XPs as many times as they apear beneeth and in a selection.\n" +
                                                              "Do you want to continue?", "Selection Warning",
                                                       JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                cont = true;
            }
            else {
                cont = false;
            }
        }
        if (cont) {
            List<RolesGroup> groupsList = new ArrayList<RolesGroup>();
            List<Role> rolesList = new ArrayList<Role>();
            for (int i = 0; i < paths.length; i++) {
                TreePath path = paths[i];
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object obj = node.getUserObject();
                if (obj instanceof RolesGroup) {
                    groupsList.add((RolesGroup) obj);
                }
                else if (obj instanceof Role) {
                    rolesList.add((Role) obj);
                }
            }
            if (groupsList.size() > 0 || rolesList.size() > 0) {
                JTextField text = new JTextField(25);
                JSpinner ammount = Utilities.createIntegerJSpinner(-7, 7, 1, 1);
                GridBagPanel panel = new GridBagPanel();
                panel.addLine("Ammount: ", ammount);
                panel.addLine("Message: ", text);
                int choice = JOptionPane.showConfirmDialog(mTree, panel, "Insert XP", JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    if (DEBUG) System.out.println(
                            "[GroupsFrame][insertXP][491] ammount: " + Utilities.getSpinnerInteger(ammount));
                    if (DEBUG) System.out.println("[GroupsFrame][insertXP][492] message: " + text.getText());
                    try {
                        ManipulationDB db = Proxy.getManipulationDB();
                        db.insertExperience(groupsList, rolesList, Utilities.getSpinnerInteger(ammount), text.getText());
                    }
                    catch (Exception e) {
                        if (DEBUG) e.printStackTrace();
                        report(e);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(mTree, "Nothing of use selected");
            }
        }
    }

    protected List<RolesGroup> getSelectedGroups() {
        List<RolesGroup> list = new ArrayList<RolesGroup>();
        TreePath[] paths = mTree.getSelectionPaths();
        for (int i = 0; i < paths.length; i++) {
            TreePath path = paths[i];
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            Object o = node.getUserObject();
            if(o instanceof RolesGroup) {
                list.add((RolesGroup) o);
            }
        }
        return list;
    }

    public void updateGroup(RolesGroup pGroup) {
        try {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) mTree.getModel().getRoot();
            Enumeration enumerate = root.breadthFirstEnumeration();
            DefaultMutableTreeNode groupNode = null;
            while (enumerate.hasMoreElements()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumerate.nextElement();
                Object obj = node.getUserObject();
                if (obj instanceof RolesGroup) {
                    RolesGroup group = (RolesGroup) obj;
                    if (group.getId() == pGroup.getId()) {
                        if (node.getChildCount() >= 1) {
                            groupNode = node;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
                else {
                    if (DEBUG)
                        System.out.println(
                                "[GroupsFrame][updateGroup][115] " + obj + " is a " + obj.getClass().getName());
                }
            }
            if (groupNode != null) {
                RolesGroup group = (RolesGroup) groupNode.getUserObject();
                List groupsInGroup = mGroupsDB.getGroupsInGroup(group.getId());
                List roles = mGroupsDB.getRolesInGroup(group.getId());

                TreePath path = new TreePath(groupNode.getPath());
                boolean wasExpanded = mTree.isExpanded(path);
                groupNode = (DefaultMutableTreeNode) setChildrenOnGroupNode(groupsInGroup, roles, groupNode);
                if (wasExpanded) {
                    path = new TreePath(groupNode.getPath());
                    mTree.expandPath(path);
                }
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }
}
