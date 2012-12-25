package se.tdt.bobby.wodcc.ui;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.logic.MonthlyIncomePayer;
import se.tdt.bobby.wodcc.logic.OutputCreator;
import se.tdt.bobby.wodcc.proxy.interfaces.*;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.controllers.EditRoleAction;
import se.tdt.bobby.wodcc.ui.components.controllers.PreferredDomainAction;
import se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering.Rules;
import se.tdt.bobby.wodcc.ui.components.models.RoleNamesListModel;
import se.tdt.bobby.wodcc.ui.components.view.RolesGroupTreeCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.RolesListCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.ShowPage2Panel;
import se.tdt.bobby.wodcc.ui.dialogs.*;
import se.tdt.bobby.wodcc.ui.guide.CreateRoleDialog;
import se.tdt.bobby.wodcc.ui.sqllist.SqlListsFrame;
import se.tdt.bobby.wodcc.version.Version;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-18 19:47:16
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class MainFrame extends JFrame {
    public static MainFrame sMainFrame = null;
    private JList mRolesList;
    private MutableAction mExitAction;
    private MutableAction mNewRoleAction;
    private static final boolean DEBUG = false;
    private RoleNamesListModel mRoleNamesListModel;
    private OutputCreator mOutputCreator;
    private RetrievalDB mDB;
    private MutableAction mShowRolesAction;
    //private MutableAction mShowThaumaRitualsAction;
    //private MutableAction mShowNecromanticRitualsAction;
    private MutableAction mEditRoleAction;
    private PreferencesDialog mPreferencesDialog;
    private MutableAction mPreferencesAction;
    private MutableAction mShowFamelyTreesAction;
    private MutableAction mUpdateRolesAction;
    private MutableAction mCreateGhoulAction;
    private MutableAction mResourcesAction;
    private MutableAction mRolesInfluensNBankingAction;
    private JComboBox mViewClansCombo;
    private MutableAction mShowGroupsAction;
    private GroupsFrame mGroupsFrame;
    private MutableAction mAddToGroupAction;
    private MutableAction mPayMonthlyIncomeAction;
    private MutableAction mShowRoles2Action;
    private ButtonGroup mDomainsButtonGroup;
    private JComboBox mViewDomainsCombo;
    private Rules mRolesFilterRules;
    private JComboBox mViewVitalsCombo;
    private MutableAction mInsertXPAction;
    private ManipulationDB mManipulationDB;
    private MutableAction mBackgroundAction;
    private MutableAction mShowRitualsAction;
    private JLabel mStatusLabel;
    private MutableAction mShowDisciplinesAction;
    private DisciplinesDialog mDisciplinesDialog = null;
    private MutableAction mShowMeritsFlawsAndOtherTraitsAction;
    private MeritsFlawsOtherTraitsDialog mMeritsFlawsAndOtherTraitsDialog;
    private MutableAction mAboutAction;
    private AboutDialog mAboutDialog;
    private MutableAction mShowGeneratorAction;
    private CreateRules mCreateRules;
    private MutableAction mSearchAction;
    private SearchDialog mSearchDialog;
    private MutableAction mPlotsAction;
    private MutableAction mAssignPlotsAction;
    private MutableAction mListPlotsAction;
    private MutableAction mShowTemplatesAction;
    private TemplatesFrame mTemplatesFrame;
    private MutableAction mAddToTemplatesAction;
    private TemplateDB mTemplateDB;
    private MutableAction mChangePasswordAction;
    private MutableAction mMembersListAction;
    private MutableAction mPlayersAction;
    private PlayersFrame mPlayersFrame;
    private SqlListsFrame mSqlListsFrame;
    private MutableAction mSqlListsAction;

    class EmptyRoleFormAction extends MutableAction {
        private Clan mClan;
        private boolean mGhoul;

        public EmptyRoleFormAction(Clan pClan, boolean pGhoul) {
            super(pClan.getName());
            mClan = pClan;
            mGhoul = pGhoul;
        }

        public void actionPerformed(ActionEvent e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        File f = mOutputCreator.makeEmptyRole(mClan, mGhoul);
                        /*Process p = */Utilities.startExplorer(f.getAbsolutePath());
                        f.deleteOnExit();
                        /*if (p != null) {
                            FileQuestionaire quest = new FileQuestionaire(p, f, sMainFrame);
                        }*/
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    class FamelyTreesForClanAction extends MutableAction {
        private Clan mClan;

        public FamelyTreesForClanAction(Clan pClan) {
            super(pClan.getName());
            mClan = pClan;
        }

        public void actionPerformed(ActionEvent e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        File f = mOutputCreator.makeFamelyTrees(mClan);
                        /*Process p = */Utilities.startExplorer(f.getAbsolutePath());
                        f.deleteOnExit();
                        /*if (p != null) {
                            FileQuestionaire quest = new FileQuestionaire(p, f, sMainFrame);
                        }*/
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    public MainFrame() throws Exception, SQLException, RemoteException {
        super("[WoD] Masserr\u2122  v." + Version.getInstance());
        sMainFrame = this;
        mStatusLabel = new JLabel(" Starting...");
        mStatusLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        getContentPane().add(mStatusLabel, BorderLayout.SOUTH);
        ImageIcon icon = new ImageIcon("img/ankh.gif");
        setIconImage(icon.getImage());
        mPreferencesDialog = new PreferencesDialog(this);
        mManipulationDB = Proxy.getManipulationDB();
        mDB = Proxy.getRetrievalDB();
        mCreateRules = Proxy.getCreateRules();
        mOutputCreator = OutputCreator.getInstance();
        mRolesFilterRules = new Rules();
        mRoleNamesListModel = new RoleNamesListModel(mRolesFilterRules);
        mRolesList = new JList(mRoleNamesListModel);

        mRolesList.setCellRenderer(new RolesListCellRenderer());
        mRolesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            int row = mRolesList.locationToIndex(e.getPoint());
                            if (row >= 0) {
                                Role role = (Role) mRoleNamesListModel.getElementAt(row);
                                mRolesList.addSelectionInterval(row, row);
                                viewRole(role.getId());
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            }
        });
        mRolesList.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getModifiers() == KeyEvent.CTRL_MASK) {
                        showRoles(false);
                    } else {
                        Role role = (Role) mRolesList.getSelectedValue();
                        viewRole(role.getId());
                    }
                }
            }
        });
        mRolesList.setVisibleRowCount(-1);
        mRolesList.setLayoutOrientation(JList.VERTICAL_WRAP);
        mRolesList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updateStatusLabel();
            }
        });
        mRoleNamesListModel.addListDataListener(new ListDataListener() {
            public void intervalAdded(ListDataEvent e) {
                updateStatusLabel();
            }

            public void intervalRemoved(ListDataEvent e) {
                updateStatusLabel();
            }

            public void contentsChanged(ListDataEvent e) {
                updateStatusLabel();
            }
        });
        getContentPane().add(new JScrollPane(mRolesList), BorderLayout.CENTER);
        setUpActions();
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menu.add(mSearchAction);
        menu.add(mPlayersAction);
        menu.add(mMembersListAction);
        JMenuItem item = new JMenuItem(mPreferencesAction);
        menu.add(item);

        JMenu prefereredDomainMenu = new JMenu("Preferred Domain");
        Vector domains = mDB.getDomains();
        mDomainsButtonGroup = new ButtonGroup();
        Domain preferred = AppPreferences.getPreferredDomain();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = (Domain) domains.elementAt(i);
            PreferredDomainAction ac = new PreferredDomainAction(domain);
            JRadioButtonMenuItem citem = new JRadioButtonMenuItem(ac);
            mDomainsButtonGroup.add(citem);
            if (domain.getId() == preferred.getId()) {
                citem.setSelected(true);
            }
            prefereredDomainMenu.add(citem);
        }
        menu.add(prefereredDomainMenu);
        menu.addSeparator();
        item = new JMenuItem(mExitAction);
        menu.add(item);
        menuBar.add(menu);

        menu = new JMenu("Roles");
        menu.add(mInsertXPAction);
        menu.add(mBackgroundAction);
        menu.addSeparator();
        menu.add(mNewRoleAction);
        menu.add(mCreateGhoulAction);
        menu.add(mEditRoleAction);
        menu.addSeparator();
        //item = new JMenuItem(mShowRolesAction);
        //item.setToolTipText("Show the selected roles.");
        menu.add(mShowRolesAction);
        menu.add(mShowRoles2Action);
        menu.addSeparator();
        menu.add(mShowTemplatesAction);
        menu.add(mAddToTemplatesAction);
        menu.addSeparator();
        menu.add(mShowGroupsAction);
        menu.add(mAddToGroupAction);
        final JMenu subMenu = new JMenu("Famely Trees");
        subMenu.setIcon(IconFactory.getIconFactory().getIcon("Tree24.gif"));
        subMenu.add(mShowFamelyTreesAction);
        subMenu.addSeparator();
        menu.add(subMenu);
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    List<Clan> replresentedClans = Proxy.getRetrievalDB().getRepresentedClans();
                    for (int i = 0; i < replresentedClans.size(); i++) {
                        Clan clan = replresentedClans.get(i);
                        subMenu.add(new FamelyTreesForClanAction(clan));
                    }
                }
                catch (Exception e) {
                    if (DEBUG) e.printStackTrace();
                    report(e);
                }
            }
        };
        new Thread(runnable).start();
        //menu.add(mShowFamelyTreesAction);
        menu.add(mUpdateRolesAction);
        menuBar.add(menu);

        /*********************** VIEW MENU *******************************/
        ActionListener listen = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (e.getActionCommand().equals("viewGhouls")) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
                            mRolesFilterRules.setViewGhouls(box.isSelected());
                            try {
                                mRoleNamesListModel.update();
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                } else if (e.getActionCommand().equals("viewNotPlayed")) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
                            mRolesFilterRules.setViewNotPlayedRoles(box.isSelected());
                            try {
                                mRoleNamesListModel.update();
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                } else if (e.getActionCommand().equals("viewKindred")) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
                            mRolesFilterRules.setViewKindred(box.isSelected());
                            try {
                                mRoleNamesListModel.update();
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                } else if (e.getActionCommand().equals("viewSLP")) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            JCheckBoxMenuItem box = (JCheckBoxMenuItem) e.getSource();
                            mRolesFilterRules.setViewSLP(box.isSelected());
                            try {
                                mRoleNamesListModel.update();
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            }
        };
        menu = new JMenu("View");
        JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem("Show Ghouls", mRolesFilterRules.isViewGhouls());
        checkBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.ALT_MASK));
        checkBox.setActionCommand("viewGhouls");
        checkBox.addActionListener(listen);
        checkBox.setOpaque(false);
        menu.add(checkBox);

        checkBox = new JCheckBoxMenuItem("Show Kindred", mRolesFilterRules.isViewKindred());
        checkBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, KeyEvent.ALT_MASK));
        checkBox.setActionCommand("viewKindred");
        checkBox.addActionListener(listen);
        checkBox.setOpaque(false);
        menu.add(checkBox);

        checkBox = new JCheckBoxMenuItem("Show Not played", mRolesFilterRules.isViewNotPlayedRoles());
        checkBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.ALT_MASK));
        checkBox.setActionCommand("viewNotPlayed");
        checkBox.addActionListener(listen);
        checkBox.setOpaque(false);
        menu.add(checkBox);

        checkBox = new JCheckBoxMenuItem("Show SLPs", mRolesFilterRules.isViewSLP());
        checkBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_MASK));
        checkBox.setActionCommand("viewSLP");
        checkBox.addActionListener(listen);
        checkBox.setOpaque(false);
        menu.add(checkBox);
        menuBar.add(menu);

        menu = new JMenu("Powers");
        menu.add(mShowRitualsAction);
        menu.add(mShowDisciplinesAction);
        menu.add(mShowMeritsFlawsAndOtherTraitsAction);
        //menu.add(mShowNecromanticRitualsAction);
        menuBar.add(menu);

        menu = new JMenu("Influences");
        menu.add(mResourcesAction);
        menu.add(mRolesInfluensNBankingAction);
        menu.add(mPayMonthlyIncomeAction);
        menu.addSeparator();
        menu.add(mPlotsAction);
        menu.add(mAssignPlotsAction);
        menu.add(mListPlotsAction);
        menuBar.add(menu);

        menu = new JMenu("Empty Forms");
        final JMenu subMenuEmptyKindred = new JMenu("Empty Kindred");
        menu.add(subMenuEmptyKindred);
        final JMenu subMenuEmptyGhouls = new JMenu("Empty Ghoul");
        menu.add(subMenuEmptyGhouls);
        runnable = new Runnable() {
            public void run() {
                try {
                    Vector clans = Proxy.getRetrievalDB().getClans();
                    for (int i = 0; i < clans.size(); i++) {
                        Clan clan = (Clan) clans.elementAt(i);
                        subMenuEmptyKindred.add(new EmptyRoleFormAction(clan, false));
                        subMenuEmptyGhouls.add(new EmptyRoleFormAction(clan, true));
                    }
                }
                catch (Exception e) {
                    if (DEBUG) e.printStackTrace();
                    report(e);
                }
            }
        };
        new Thread(runnable).start();
        menu.add(mShowGeneratorAction);
        menuBar.add(menu);

        menu = new JMenu("SQL");
        menu.add(mSqlListsAction);
        menuBar.add(menu);

        menu = new JMenu("Help");
        JMenu subMenuLotn = new JMenu("Lotn");
        subMenuLotn.add(HelpFrame.sShowLotnAbilitiesHelpAction);
        subMenuLotn.add(HelpFrame.sShowLotnArchetypesHelpAction);
        subMenuLotn.add(HelpFrame.sShowLotnPathsHelpAction);
        subMenuLotn.add(HelpFrame.sShowLotnMeritsNflawsHelpAction);
        menu.add(subMenuLotn);
        menu.addSeparator();
        menu.add(mChangePasswordAction);
        menu.addSeparator();
        menu.add(mAboutAction);
        menuBar.add(menu);

        setJMenuBar(menuBar);
        JToolBar toolBar = createToolBar(domains);
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(true);
        getContentPane().add(toolBar, BorderLayout.NORTH);
        setSize(975, 750);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        setVisible(true);
        updateStatusLabel();
    }

    private void updateStatusLabel() {
        int[] selectionIndices = mRolesList.getSelectedIndices();
        int selectionCount = 0;
        if (selectionIndices != null) {
            selectionCount = selectionIndices.length;
        }
        int totalCount = mRoleNamesListModel.getSize();
        if (selectionCount > 0) {
            mStatusLabel.setText(" Roles: " + totalCount + " Selected: " + selectionCount);
        } else {
            mStatusLabel.setText(" Roles: " + totalCount);
        }
    }

    private JToolBar createToolBar(Vector pDomains) throws SQLException, RemoteException {
        JToolBar toolBar = new JToolBar("Role visibility");
        toolBar.setRollover(true);
        toolBar.add(mNewRoleAction);
        toolBar.add(mEditRoleAction);
        toolBar.add(mShowRolesAction);
        toolBar.add(mShowRoles2Action);
        toolBar.add(mShowGroupsAction);
        toolBar.add(mSearchAction);
        toolBar.addSeparator();
        JPanel flowPanel = new JPanel();
        flowPanel.setOpaque(false);
        Vector clans = mDB.getClans();
        Clan all = new Clan(-1, "All");
        clans.add(0, all);
        mViewClansCombo = new JComboBox(clans);
        int clanid = mRolesFilterRules.getVisibleClan();
        for (int i = 0; i < clans.size(); i++) {
            Clan clan = (Clan) clans.elementAt(i);
            if (clan.getId() == clanid) {
                mViewClansCombo.setSelectedItem(clan);
                break;
            }
        }

        MutableAction action = new MutableAction("View Clan") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Clan c = (Clan) mViewClansCombo.getSelectedItem();
                        mRolesFilterRules.setVisibleClan(c.getId());
                        try {
                            mRoleNamesListModel.update();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mViewClansCombo.setAction(action);
        flowPanel.add(new JLabel("Clans: "));
        flowPanel.add(mViewClansCombo);
        pDomains.add(0, new Domain(-1, "All"));
        mViewDomainsCombo = new JComboBox(pDomains);
        int domainId = mRolesFilterRules.getVisibleDomain();
        for (int i = 0; i < pDomains.size(); i++) {
            Domain domain = (Domain) pDomains.elementAt(i);
            if (domain.getId() == domainId) {
                mViewDomainsCombo.setSelectedItem(domain);
                break;
            }
        }
        action = new MutableAction("View Domain") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Domain d = (Domain) mViewDomainsCombo.getSelectedItem();
                        if (DEBUG) System.out.println("[MainFrame][actionPerformed][227] visible domain: " + d);
                        mRolesFilterRules.setVisibleDomain(d.getId());
                        try {
                            mRoleNamesListModel.update();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mViewDomainsCombo.setAction(action);
        flowPanel.add(new JLabel("  Domains: "));
        flowPanel.add(mViewDomainsCombo);

        DefaultComboBoxModel model = new DefaultComboBoxModel(Vitals.ALL);
        model.insertElementAt("All", 0);
        mViewVitalsCombo = new JComboBox(model);
        if (mRolesFilterRules.getVisibleVitals() != null) {
            mViewVitalsCombo.setSelectedItem(mRolesFilterRules.getVisibleVitals());
        } else {
            mViewVitalsCombo.setSelectedIndex(0);
        }
        action = new MutableAction("View Vitals") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Object o = mViewVitalsCombo.getSelectedItem();
                        if (o instanceof Vitals) {
                            mRolesFilterRules.setVisibleVitals((Vitals) o);
                        } else {
                            mRolesFilterRules.setVisibleVitals(null);
                        }
                        try {
                            mRoleNamesListModel.update();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mViewVitalsCombo.setAction(action);
        flowPanel.add(new JLabel("  Vitals: "));
        flowPanel.add(mViewVitalsCombo);
        toolBar.add(flowPanel);
        toolBar.add(Box.createHorizontalGlue());
        return toolBar;
    }

    protected void viewRole(int pRoleId) {
        try {
            Role role = mDB.getRole(pRoleId);
            File f = mOutputCreator.makeSingleRole(role);
            /*Process p = Utilities.startExplorer(f.getAbsolutePath());
            if (p != null) {
                FileQuestionaire quest = new FileQuestionaire(p, f, this);
            }*/
            Utilities.startExplorer(f.getAbsolutePath());
            f.deleteOnExit();
        }
        catch (SQLException e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private void exit() {
        AppPreferences.setRolesFilterRules(mRolesFilterRules);
        System.exit(0);
    }

    public void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void setUpActions() throws SQLException, RemoteException {
        final MainFrame mainFrame = this;
        mExitAction = new MutableAction("Exit", KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };
        mExitAction.setToolTipText("Exit the application");
        mNewRoleAction = new MutableAction("New Role...", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK),
                IconFactory.getIconFactory().getIcon("New24.gif")) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        mNewRoleAction.setEnabled(false);
                        final CreateRoleDialog dialog = new CreateRoleDialog(mainFrame);
                        dialog.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                if (DEBUG) System.out.println("[MainFrame][windowClosing][157] closed");
                                if (dialog.isFinished()) {
                                    if (DEBUG) System.out.println("[MainFrame][windowClosing][159] finished");
                                    try {
                                        mRoleNamesListModel.update();
                                    }
                                    catch (Exception e1) {
                                        if (DEBUG) e1.printStackTrace();
                                        report(e1);
                                    }
                                }
                            }
                        });
                        dialog.setVisible(true);
                        mNewRoleAction.setEnabled(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mNewRoleAction.setToolTipText("Create a new Role");
        mShowRolesAction = new MutableAction("Show", KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK),
                IconFactory.getIconFactory().getIcon("PrintPreview24.gif")) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        showRoles(false);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowRolesAction.setToolTipText("Show the character sheets for the selected roles.");
        mShowRoles2Action = new MutableAction("Show 1-2",
                KeyStroke.getKeyStroke(KeyEvent.VK_O,
                        KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK),
                IconFactory.getIconFactory().getIcon("PrintPreview224.gif")) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        showRoles(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowRoles2Action.setToolTipText("Show the character sheets and the \"Second Page\" for the selected roles.");
        mShowFamelyTreesAction = new MutableAction("All") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            File f = mOutputCreator.makeFamelyTrees();
                            //Utilities.startExplorer(f.getAbsolutePath());
                            /*Process p = */Utilities.startExplorer(f.getAbsolutePath());
                            /*if (p != null) {
                                FileQuestionaire quest = new FileQuestionaire(p, f, mainFrame);
                            }*/
                            f.deleteOnExit();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowFamelyTreesAction.setToolTipText("Show the roles' Famelytrees ");
        mShowRitualsAction = new MutableAction("Rituals", 'r') {
            public void actionPerformed(ActionEvent e) {
                try {
                    new RitualsDialog(mainFrame);
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mPreferencesAction = new MutableAction("Preferences", 'P',
                IconFactory.getIconFactory().getIcon("Preferences24.gif")) {
            public void actionPerformed(ActionEvent e) {
                mPreferencesDialog.setVisible(true);
            }
        };
        mEditRoleAction = new EditRoleAction(mRolesList, sMainFrame);
        /*mEditRoleAction = new MutableAction("Edit Role...", KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK),
                                            IconFactory.getIconFactory().getIcon("Edit24.gif")) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        mEditRoleAction.setEnabled(false);
                        Object o = mRolesList.getSelectedValue();
                        if (o != null && o instanceof Role) {
                            int id = ((Role) o).getId();
                            try {
                                Role role = mDB.getRole(id);
                                CreateRoleDialog dialog = CreateRoleDialog.getEditDialog(mainFrame, role);
                                dialog.addWindowListener(new WindowAdapter() {
                                    public void windowClosing(WindowEvent e) {
                                        try {
                                            mRoleNamesListModel.update();
                                        }
                                        catch (Exception e1) {
                                            if (DEBUG) e1.printStackTrace();
                                        }
                                    }
                                });
                                dialog.setVisible(true);
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                        mEditRoleAction.setEnabled(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mEditRoleAction.setToolTipText("Edit the first selected role.");*/
        mCreateGhoulAction = new MutableAction("Create Ghoul...",
                KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_MASK),
                IconFactory.getIconFactory().getIcon("NewG24.gif")) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        mCreateGhoulAction.setEnabled(false);
                        try {
                            CreateRoleDialog dialog = CreateRoleDialog.getGhoulDialog(mainFrame);
                            dialog.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent e) {
                                    try {
                                        mRoleNamesListModel.update();
                                    }
                                    catch (Exception e1) {
                                        if (DEBUG) e1.printStackTrace();
                                    }
                                }
                            });
                            dialog.setVisible(true);
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                        mCreateGhoulAction.setEnabled(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mCreateGhoulAction.setToolTipText("Create a new Ghoul.");
        mUpdateRolesAction = new MutableAction("Update Roles", KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
                IconFactory.getIconFactory().getIcon("Refresh24.gif")) {
            public void actionPerformed(ActionEvent e) {
                try {
                    mRoleNamesListModel.update();
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mUpdateRolesAction.setToolTipText("Update the list of roles.");
        mResourcesAction = new MutableAction("Resources...", 'R') {
            public void actionPerformed(ActionEvent e) {
                try {
                    new ResourcesDialog(mainFrame);
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mRolesInfluensNBankingAction = new MutableAction("Roles Influences & Banking") {
            public void actionPerformed(ActionEvent e) {
                try {
                    new RolesInfluenceDialog(mainFrame);
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mShowGroupsAction = new MutableAction("Groups...", KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0),
                IconFactory.getIconFactory().getIcon("Groups24.gif")) {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (mGroupsFrame != null) {
                        mGroupsFrame.requestFocus();
                    } else {
                        mGroupsFrame = new GroupsFrame(mainFrame);
                        mGroupsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        mGroupsFrame.addWindowListener(new WindowAdapter() {
                            public void windowClosed(WindowEvent e) {
                                mGroupsFrame = null;
                            }
                        });
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mShowGroupsAction.setToolTipText("Show the Groups Frame.");
        mAddToGroupAction = new MutableAction("Add To Group...",
                KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_MASK),
                IconFactory.getIconFactory().getIcon("GroupAdd24.gif")) {
            public void actionPerformed(ActionEvent e) {
                try {
                    Object[] values = mRolesList.getSelectedValues();
                    if (values.length > 0) {
                        GroupsDB db = Proxy.getGroupsDB();
                        //Vector groups = db.getGroups();
                        DefaultMutableTreeNode root = buildTree(db);
                        JTree tree = new JTree(root);
                        tree.setRootVisible(false);
                        tree.setCellRenderer(new RolesGroupTreeCellRenderer());
                        //JComboBox box = new JComboBox(groups);
                        JPanel panel = new JPanel();
                        panel.add(new JLabel("Group: "));
                        JScrollPane scroll = new JScrollPane(tree);
                        scroll.setPreferredSize(new Dimension(100, 100));
                        panel.add(scroll);
                        int choice = JOptionPane.showConfirmDialog(mainFrame, panel, "Add to Group",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (choice == JOptionPane.OK_OPTION) {
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                            RolesGroup group = (RolesGroup) node.getUserObject();
                            db.addRolesToGroup(values, group);
                            if (mGroupsFrame != null) {
                                mGroupsFrame.updateGroup(group);
                            }
                        }
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }

            private DefaultMutableTreeNode buildTree(GroupsDB pDb) throws SQLException, RemoteException {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root", true);
                Vector groups = pDb.getRootGroups();
                for (int i = 0; i < groups.size(); i++) {
                    RolesGroup roleGroup = (RolesGroup) groups.elementAt(i);
                    addGroupToNode(roleGroup, root, pDb);
                }
                return root;
            }

            private void addGroupToNode(RolesGroup pRoleGroup, DefaultMutableTreeNode pNode, GroupsDB pDb) throws SQLException, RemoteException {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(pRoleGroup);
                List groups = pDb.getGroupsInGroup(pRoleGroup.getId());
                for (int i = 0; i < groups.size(); i++) {
                    RolesGroup rolesGroup = (RolesGroup) groups.get(i);
                    addGroupToNode(rolesGroup, node, pDb);
                }
                pNode.add(node);
            }
        };
        mAddToGroupAction.setToolTipText("Add the selected roles to a group.");
        mPayMonthlyIncomeAction = new MutableAction("Pay Monthly Income") {
            public void actionPerformed(ActionEvent e) {
                try {
                    MonthlyIncomePayer payer = new MonthlyIncomePayer();
                    payer.payToAll();
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mInsertXPAction = new MutableAction("Insert XP", KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.ALT_MASK)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        insertXP();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mBackgroundAction = new MutableAction("Background/Will", KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Role role = (Role) mRolesList.getSelectedValue();
                        if (role != null) {
                            try {
                                new BackgroundsDialog(mainFrame, role);
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowDisciplinesAction = new MutableAction("Disciplines & Paths...") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            if (mDisciplinesDialog == null) {
                                mDisciplinesDialog = new DisciplinesDialog(sMainFrame);
                            }
                            if (!mDisciplinesDialog.isVisible()) {
                                mDisciplinesDialog.setVisible(true);
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
        };
        mShowMeritsFlawsAndOtherTraitsAction = new MutableAction("Merits, Flaws & Other Traits...") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            if (mMeritsFlawsAndOtherTraitsDialog == null) {
                                mMeritsFlawsAndOtherTraitsDialog = new MeritsFlawsOtherTraitsDialog(sMainFrame);
                            }
                            mMeritsFlawsAndOtherTraitsDialog.setVisible(true);
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mAboutAction = new MutableAction("About") {
            public void actionPerformed(ActionEvent e) {
                if (mAboutDialog == null) {
                    mAboutDialog = new AboutDialog(sMainFrame);
                }
                mAboutDialog.setVisible(true);
                mAboutDialog.requestFocus();
            }
        };
        mShowGeneratorAction = new MutableAction("Generator") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            Vector generations = mDB.getGenerations();
                            List<CreateRule> createRules = mCreateRules.getRules();
                            File f = mOutputCreator.makeGenerator(generations, createRules);
                            //Utilities.startExplorer(f.getAbsolutePath());
                            /*Process p = */Utilities.startExplorer(f.getAbsolutePath());
                            /*if (p != null) {
                                FileQuestionaire quest = new FileQuestionaire(p, f, sMainFrame);
                            }*/
                            f.deleteOnExit();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mSearchAction = new MutableAction("Search...", KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), IconFactory.getIconFactory().getIcon("Find24.gif")) {
            public void actionPerformed(ActionEvent e) {
                if (mSearchDialog == null) {
                    mSearchDialog = new SearchDialog(MainFrame.sMainFrame);
                }
                Runnable runnable = new Runnable() {
                    public void run() {
                        boolean search = mSearchDialog.showDialog();
                        if (search) {
                            try {
                                mRoleNamesListModel.search(mSearchDialog.getSearchText(), mSearchDialog.isSearchName(), mSearchDialog.isSearchPlayerName(), mSearchDialog.isSearchEmbraced(), mSearchDialog.isSearchNature(), mSearchDialog.isSearchDemeanor(), mSearchDialog.isSearchPath(), mSearchDialog.isSearchDerangement());
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mSearchAction.setToolTipText("Search for roles...");
        mPlotsAction = new MutableAction("Plots...") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            new PlotsDialog(sMainFrame);
                        }
                        catch (SQLException e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                        catch (RemoteException e1) {
                            if (DEBUG) e1.printStackTrace();
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mAssignPlotsAction = new MutableAction("Assign Plots...") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            new AssignPlotsDialog(sMainFrame);
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mListPlotsAction = new MutableAction("List Plots...") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            ListPlotsDialog dialog = new ListPlotsDialog(sMainFrame);
                            List<Plot> plots = dialog.getPlotsList();
                            HashMap<Integer, List<IntWithString>> assignmentMap = dialog.getAssignmentMap();
                            File f = mOutputCreator.makePlotsList(plots, assignmentMap);
                            /*Process p = */Utilities.startExplorer(f.getAbsolutePath());
                            /*if (p != null) {
                                FileQuestionaire quest = new FileQuestionaire(p, f, sMainFrame);
                            }*/
                            f.deleteOnExit();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowTemplatesAction = new MutableAction("Templates...", KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            if (mTemplatesFrame == null) {
                                mTemplatesFrame = new TemplatesFrame();
                            }
                            mTemplatesFrame.setVisible(true);
                            mTemplatesFrame.requestFocus();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mAddToTemplatesAction = new MutableAction("Add to Templates", KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                try {
                    Role selectedRole = (Role) mRolesList.getSelectedValue();
                    String templateName = JOptionPane.showInputDialog(sMainFrame, "Template Name: ", selectedRole.getName());
                    if (templateName != null) {
                        Role role = mDB.getRole(selectedRole.getId());
                        if (role != null) {
                            role.setName(templateName);
                            if (mTemplateDB == null) {
                                mTemplateDB = Proxy.getTemplateDB();
                            }
                            int id = mTemplateDB.addTemplate(role);
                            if (id >= 0) {
                                JOptionPane.showMessageDialog(sMainFrame, "\"" + selectedRole.getName() + "\" has been added to templates \n" +
                                        "as \"" + templateName + "\"", "Template Added", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(sMainFrame, "\"" + selectedRole.getName() + "\" wasn't added for unknown reason", "Error?", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mChangePasswordAction = new MutableAction("Change Password") {
            public void actionPerformed(ActionEvent e) {
                try {
                    JPasswordField currentPassword = new JPasswordField(20);
                    JPasswordField newPassword = new JPasswordField(20);
                    JPasswordField confirmPassword = new JPasswordField(20);
                    GridBagPanel panel = new GridBagPanel();
                    panel.addLine("Current Password: ", currentPassword);
                    panel.addLine("New Password: ", newPassword);
                    panel.addLine("Confirm Password: ", confirmPassword);
                    int choice = JOptionPane.showConfirmDialog(sMainFrame, panel, "Change Your Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (choice == JOptionPane.OK_OPTION) {
                        String currPassword = new String(currentPassword.getPassword());
                        String nPassword = new String(newPassword.getPassword());
                        String cPassword = new String(confirmPassword.getPassword());
                        if (nPassword.equals(cPassword)) {
                            AdminDB db = Proxy.getAdminDB();
                            if (db.changePassword(currPassword, nPassword)) {
                                JOptionPane.showMessageDialog(sMainFrame, "New Password set.", "Change Your Password", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(sMainFrame, "The password was not set for unknown reson.", "Change Your Password", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(sMainFrame, "The passwords doesn't match!", "Change Password", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mMembersListAction = new MutableAction("Members List") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            File f = mOutputCreator.membersList();
                            Utilities.startExplorer(f.getAbsolutePath());
                            f.deleteOnExit();
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mPlayersAction = new MutableAction("Players", KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0)) {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (mPlayersFrame == null) {
                        mPlayersFrame = new PlayersFrame();
                    }
                    if (!mPlayersFrame.isVisible()) {
                        mPlayersFrame.setVisible(true);
                    }
                    if (!mPlayersFrame.hasFocus()) {
                        mPlayersFrame.requestFocus();
                    }
                    Utilities.positionMe(mPlayersFrame, sMainFrame);
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mSqlListsAction = new MutableAction("SQL Lists", KeyStroke.getKeyStroke(KeyEvent.VK_F12, KeyEvent.ALT_MASK)) {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (mSqlListsFrame == null) {
                        mSqlListsFrame = new SqlListsFrame();
                    }
                    if(!mSqlListsFrame.isVisible()) {
                        mSqlListsFrame.setVisible(true);
                    }
                    if(!mSqlListsFrame.hasFocus()) {
                        mSqlListsFrame.requestFocus();
                    }
                    Utilities.positionMe(mSqlListsFrame, sMainFrame);
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
    }

    private void insertXP() {
        Object[] objs = mRolesList.getSelectedValues();
        List<Role> rolesList = new ArrayList<Role>(objs.length);
        if (objs != null && objs.length > 0) {
            for (int i = 0; i < objs.length; i++) {
                Role role = (Role) objs[i];
                rolesList.add(role);
            }
            JTextField text = new JTextField(25);
            JSpinner ammount = Utilities.createIntegerJSpinner(-7, 7, 1, 1);
            GridBagPanel panel = new GridBagPanel();
            panel.addLine("Ammount: ", ammount);
            panel.addLine("Message: ", text);
            int choice = JOptionPane.showConfirmDialog(mRolesList, panel, "Insert XP", JOptionPane.OK_CANCEL_OPTION);
            if (choice == JOptionPane.OK_OPTION) {
                try {
                    if (DEBUG) System.out.println("MainFrame.insertXP(826) Manipulationdb: " + mManipulationDB);
                    mManipulationDB.insertExperience(new ArrayList<RolesGroup>(1), rolesList, Utilities.getSpinnerInteger(ammount),
                            text.getText());
                }
                catch (Exception e) {
                    if (DEBUG) e.printStackTrace();
                    report(e);
                }
            }

        } else {
            JOptionPane.showMessageDialog(mRolesList, "Nothing selected");
        }
    }

    /*class ShowPage2Panel extends GridBagPanel {
        private JCheckBox mViewPage2;
        private JCheckBox mListPlots;
        private JCheckBox mListExperience;

        public ShowPage2Panel() {
            super();
            mViewPage2 = new JCheckBox("View Page 2", AppPreferences.isViewPage2());
            mListPlots = new JCheckBox("List Plots", AppPreferences.isListPlots());
            mListExperience = new JCheckBox("List Experience", AppPreferences.isListExperience());
            addLine(mViewPage2);
            addLine(mListPlots);
            addLine(mListExperience);
        }

        public boolean isViewPage2() {
            return mViewPage2.isSelected();
        }

        public boolean isListPlots() {
            return mListPlots.isSelected();
        }
        public boolean isListExperience() {
            return mListExperience.isSelected();
        }
    };*/

    private ShowPage2Panel mShowPage2Panel = null;

    private void showRoles(boolean pShowExtra) {
        if (mShowPage2Panel == null) {
            mShowPage2Panel = new ShowPage2Panel();
        }
        Object[] objs = mRolesList.getSelectedValues();
        ArrayList<Role> roles = new ArrayList<Role>(objs.length);
        for (int i = 0; i < objs.length; i++) {
            roles.add((Role) objs[i]);
        }
        try {
            if (pShowExtra) {
                int choise = JOptionPane.showConfirmDialog(sMainFrame, mShowPage2Panel, "Show 1-2", JOptionPane.OK_CANCEL_OPTION);
                if (choise == JOptionPane.OK_OPTION) {
                    AppPreferences.setViewPage2(mShowPage2Panel.isViewPage2());
                    AppPreferences.setListPlots(mShowPage2Panel.isListPlots());
                    AppPreferences.setListExperience(mShowPage2Panel.isListExperience());
                    File f = mOutputCreator.makeRoles(roles, mShowPage2Panel.isViewPage2(), mShowPage2Panel.isListPlots(), mShowPage2Panel.isListExperience());
                    Utilities.startExplorer(f.getAbsolutePath());
                    f.deleteOnExit();
                } else {
                    return;
                }
            } else {
                File f = mOutputCreator.makeRoles(roles, false, false, false);
                Utilities.startExplorer(f.getAbsolutePath());

                f.deleteOnExit();
            }
        }
        catch (Exception e1) {
            if (DEBUG) e1.printStackTrace();
            report(e1);
        }
    }

    public static void main(String[] args) throws Throwable, FileNotFoundException, RemoteException {
        try {
            AppPreferences.testFiles();

            if (Proxy.testConnection()) {
                new MainFrame();
                downloadNewGroupTypeIcons();
            } else {
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

    private static void downloadNewGroupTypeIcons() {
        try {
            ArrayList<String> names = IconFactory.getGroupTypeIconFileNames();
            GroupsDB db = Proxy.getGroupsDB();
            HashMap<String, byte[]> newIcons = db.getGroupTypeIconFiles(names);
            Iterator<String> iter = newIcons.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                byte[] bytes = newIcons.get(s);
                RandomAccessFile writeFile = new RandomAccessFile(new File(IconFactory.getGroupTypesIconDir(), s), "rw");
                writeFile.write(bytes);
                writeFile.close();
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private static void preferences() {
        PreferencesDialog diag = new PreferencesDialog((Frame) null);
        diag.setVisible(true);
    }

}
