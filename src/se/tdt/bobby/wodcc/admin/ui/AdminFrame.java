package se.tdt.bobby.wodcc.admin.ui;

import se.tdt.bobby.wodcc.admin.ui.components.UsersInfoPanel;
import se.tdt.bobby.wodcc.admin.ui.components.views.UsersTreeCellRenderer;
import se.tdt.bobby.wodcc.admin.ui.dialogs.DomainDialog;
import se.tdt.bobby.wodcc.admin.ui.dialogs.UserDialog;
import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.mgm.User;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.proxy.interfaces.AdminDB;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.dialogs.PreferencesDialog;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-14 19:07:07
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class AdminFrame extends JFrame implements TreeSelectionListener {
    private AdminDB mAdminDB;
    private JTree mUsersTree;
    private static final boolean DEBUG = false;
    private MutableAction mAddUserAction;
    public static AdminFrame sAdminFrame;
    private MutableAction mEditUserAction;
    private MutableAction mExitAction;
    private MutableAction mPreferencesAction;
    private PreferencesDialog mPreferencesDialog;
    private UsersInfoPanel mUsersInfoPanel;
    private MutableAction mEditDomainAction;
    private MutableAction mAddDomainAction;

    public void valueChanged(TreeSelectionEvent e) {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    User selectedUser = getSelectedUser();
                    if (selectedUser != null) {
                        mUsersInfoPanel.showUser(selectedUser);
                    }
                    else {
                        Domain domain = getSelectedDomain();
                        if (domain != null) {
                            mUsersInfoPanel.showDomain(domain);
                        }
                        else {
                            mUsersInfoPanel.showBlankPage();
                        }
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    public AdminFrame() throws Exception, RemoteException {
        super("Masserr Admin");
        sAdminFrame = this;
        mAdminDB = Proxy.getAdminDB();
        mPreferencesDialog = new PreferencesDialog(this);
        createActions();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(mPreferencesAction);
        menu.addSeparator();
        menu.add(mExitAction);
        menuBar.add(menu);
        menu = new JMenu("Users");
        menu.add(mAddUserAction);
        menu.add(mEditUserAction);
        menuBar.add(menu);
        menu = new JMenu("Domains");
        menu.add(mAddDomainAction);
        menu.add(mEditDomainAction);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        JToolBar toolBar = new JToolBar();
        toolBar.setRollover(true);
        toolBar.add(mAddUserAction);
        toolBar.add(mEditUserAction);
        toolBar.addSeparator();
        toolBar.add(mAddDomainAction);
        toolBar.add(mEditDomainAction);
        toolBar.setFloatable(false);
        getContentPane().add(toolBar, BorderLayout.NORTH);
        DefaultMutableTreeNode root = createUsersTree();
        mUsersTree = new JTree(root);
        mUsersTree.setRootVisible(false);
        mUsersTree.setScrollsOnExpand(true);
        mUsersTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        mUsersTree.setCellRenderer(new UsersTreeCellRenderer());
        mUsersTree.addTreeSelectionListener(this);
        mUsersInfoPanel = new UsersInfoPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(mUsersTree), mUsersInfoPanel);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateTree() {
        try {
            int row = mUsersTree.getLeadSelectionRow();
            DefaultTreeModel model = (DefaultTreeModel) mUsersTree.getModel();
            Enumeration enumerator = mUsersTree.getExpandedDescendants(new TreePath(model.getRoot()));
            model.setRoot(createUsersTree());
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            if (enumerator != null) {
                while (enumerator.hasMoreElements()) {
                    TreePath treePath = (TreePath) enumerator.nextElement();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    if(node.getUserObject() instanceof Domain) {
                        Enumeration children = root.children();
                        while (children.hasMoreElements()) {
                            DefaultMutableTreeNode domainNode = (DefaultMutableTreeNode) children.nextElement();
                            if(domainNode.getUserObject().equals(node.getUserObject())) {
                                mUsersTree.expandPath(new TreePath(domainNode.getPath()));
                                break;
                            }
                        }
                    }
                }
            }
            if(row >= 0) {
                mUsersTree.setSelectionRow(row);
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private void createActions() {
        mAddUserAction = new MutableAction("New User..", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK), IconFactory.getIconFactory().getIcon("AddUser24.gif")) {
            public void actionPerformed(ActionEvent e) {
                try {
                    UserDialog dialog = new UserDialog(sAdminFrame, "Add User");
                    dialog.setVisible(true);
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }

            }
        };
        mAddUserAction.setToolTipText("Create a new User");
        mEditUserAction = new MutableAction("Edit User...", KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK), IconFactory.getIconFactory().getIcon("User24.gif")) {
            public void actionPerformed(ActionEvent e) {
                User user = getSelectedUser();
                if (user != null) {
                    try {
                        UserDialog dialog = new UserDialog(sAdminFrame, "Edit User " + user.getUserName(), user);
                        dialog.setVisible(true);
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            }
        };
        mEditUserAction.setToolTipText("Edit the selected User");
        mExitAction = new MutableAction("Exit", KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };
        mExitAction.setToolTipText("Exit the application");
        mPreferencesAction = new MutableAction("Preferences", 'P',
                                               IconFactory.getIconFactory().getIcon("Preferences24.gif")) {
            public void actionPerformed(ActionEvent e) {
                mPreferencesDialog.setVisible(true);
            }
        };
        mEditDomainAction = new MutableAction("Edit Domain...", KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK), IconFactory.getIconFactory().getIcon("Domain24.gif")) {
            public void actionPerformed(ActionEvent e) {
                try {
                    Domain domain = getSelectedDomain();
                    if (domain != null) {
                        if (DomainDialog.showUpdateDialog(sAdminFrame, domain)) {
                            updateTree();
                        }
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mEditDomainAction.setToolTipText("Edit the selected domain");
        mAddDomainAction = new MutableAction("Add Domain...", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK), IconFactory.getIconFactory().getIcon("DomainAdd24.gif")) {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (DomainDialog.showAddDialog(sAdminFrame)) {
                        updateTree();
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mAddDomainAction.setToolTipText("Add a domain");
    }

    private void exit() {
        System.exit(0);
    }

    private User getSelectedUser() {
        TreePath treePath = mUsersTree.getSelectionPath();
        if (treePath != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            Object obj = node.getUserObject();
            if (obj instanceof User) {
                return (User) obj;
            }
        }
        return null;
    }

    private Domain getSelectedDomain() {
        TreePath treePath = mUsersTree.getSelectionPath();
        if (treePath != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            Object obj = node.getUserObject();
            if (obj instanceof Domain) {
                return (Domain) obj;
            }
        }
        return null;
    }

    private void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);

    }

    private DefaultMutableTreeNode createUsersTree() throws SQLException, RemoteException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        List<User> users = mAdminDB.getFullUsersListByDomain();
        Domain lastDomain = new Domain(-1, "");
        DefaultMutableTreeNode lastNode = null;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (!lastDomain.equals(user.getDomain())) {
                lastDomain = user.getDomain();
                lastNode = new DefaultMutableTreeNode(lastDomain);
                root.add(lastNode);
            }
            lastNode.add(new DefaultMutableTreeNode(user));
        }
        return root;
    }

    public static void main(String[] args) throws Throwable, RemoteException {
        try {
            AppPreferences.testFiles();

            if (Proxy.testConnection()) {
                new AdminFrame();
            }
            else {
                JOptionPane.showMessageDialog(null, "Unable to connect to the database or remote Server.\nCheck the preferences.",
                                              "Database Connection error", JOptionPane.ERROR_MESSAGE);
                preferences();
            }
        }
        catch (java.io.FileNotFoundException e) {
            if (DEBUG) e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            JOptionPane.showMessageDialog(null, e.getMessage(), "Check Preferences.", JOptionPane.ERROR_MESSAGE);
            preferences();
            throw e;
        }
        catch (NotBoundException e) {
            if (DEBUG) e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage() + "\nThere is no Service bound to the given host.\nCheck Preferences or contact the System Administrator", "Service Not Bound", JOptionPane.ERROR_MESSAGE);
            preferences();
            throw e;
        }
        catch (RemoteException e) {
            if (DEBUG) e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to Contact the Server:\n" + e.getMessage() + "\nCheck Preferences or contact the System Administrator ", "Connection Error", JOptionPane.ERROR_MESSAGE);
            preferences();
            throw e;
        }
        catch (Throwable t) {
            JOptionPane.showMessageDialog(null, t.getMessage(), t.getClass().getName(), JOptionPane.ERROR_MESSAGE);
            throw t;
        }
    }

    private static void preferences() {
        PreferencesDialog diag = new PreferencesDialog((Frame) null);
        diag.setVisible(true);
    }
}
