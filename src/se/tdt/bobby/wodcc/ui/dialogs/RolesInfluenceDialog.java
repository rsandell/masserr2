package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.BankingDB;
import se.tdt.bobby.wodcc.proxy.interfaces.InfluenceDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering.Rules;
import se.tdt.bobby.wodcc.ui.components.models.BankAccountsTableModel;
import se.tdt.bobby.wodcc.ui.components.models.ProfessionsTableModel;
import se.tdt.bobby.wodcc.ui.components.models.RoleNamesListModel;
import se.tdt.bobby.wodcc.ui.components.models.VisiblesComboboxModel;
import se.tdt.bobby.wodcc.ui.components.verifiers.IntegerVerifier;
import se.tdt.bobby.wodcc.ui.components.verifiers.LongVerifier;
import se.tdt.bobby.wodcc.ui.components.view.MoneyTableCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.ResourceListCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.RolesListCellRenderer;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-feb-03 16:22:12
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class RolesInfluenceDialog extends JDialog implements ListSelectionListener, ActionListener {
    private static final int INFLUENCE_TAB = 0;
    private static final int BANKING_TAB = 1;
    private static final int FIRST_ACCOUNT_TAB = 2;
    private JTextField mBaseMoneyField;
    private JTextField mBaseMonthlyIncome;
    private JTextField mExtraBaseMoney;
    private JTextField mExtraMonthlyIncome;
    private ProfessionsTableModel mProfessionsTableModel;
    private RetrievalDB mDB;
    private ProfessionsTableModel mMortalProfessionsTableModel;
    private static final boolean DEBUG = false;

    private Role mSelectedRole = null;
    private long mBaseMoney;
    private RoleNamesListModel mRoleNamesListModel;
    private JList mRolesList;
    private JList mResourcesList;
    private InfluenceDB mInfluenceDB;
    private JTabbedPane mTPane;
    private BankingDB mBankingDB;
    private BankAccountsTableModel mBankAccountsTableModel;
    private MutableAction mWithdrawalAction;
    private MutableAction mDepositionAction;
    private JPopupMenu mBankPopup;
    private MutableAction mSetIncomeAction;
    private JTable mBankAccountsTable;
    private boolean mShowingRole = false;
    private HashMap mLastTPanelForRole;
    private Vector mWithdrawals;
    private JComboBox mAvailableInfluencesCombo;
    private JSpinner mInfluenceDots;
    private JList mAssignedInfluencesList;
    private JTextField mInfluencesNotes;
    private VisiblesComboboxModel mAvailableInfluencesModel;
    private JComboBox mViewClansCombo;
    private Rules mRolesFilterRules;
    private JComboBox mViewDomainsCombo;
    private JComboBox mViewVitalsCombo;

    public RolesInfluenceDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Influences And Banking");
        init();
    }

    public RolesInfluenceDialog(Dialog owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Influences And Banking");
        init();
    }

    public void valueChanged(ListSelectionEvent e) {
        if (DEBUG) System.out.println("[RolesInfluenceDialog][valueChanged][86] " + e);
        if (!e.getValueIsAdjusting()) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        if (mRoleNamesListModel.getSize() > 0) {
                            Role r = (Role) mRolesList.getSelectedValue();
                            if (r == null) {
                                mRolesList.setSelectedIndex(0);
                                return;
                            }
                            mSelectedRole = mDB.getRole(r.getId());
                            if (DEBUG) System.out.println("[RolesInfluenceDialog][run][92] filling info of: " + mSelectedRole);
                            fillInfo();
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
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("saveInfluences")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mSelectedRole != null) {
                        List professions = new ArrayList();
                        professions.addAll(mProfessionsTableModel.getProfessions());
                        professions.addAll(mMortalProfessionsTableModel.getProfessions());
                        try {
                            mInfluenceDB.updateRoleProfessions(mSelectedRole, professions);
                        }
                        catch (Exception e1) {
                            report(e1);
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("createNew")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mSelectedRole != null) {
                        try {
                            long money = Long.parseLong(mExtraBaseMoney.getText());
                            BankAccount account = new BankAccount(mSelectedRole.getName(), mBaseMoney + money, true);
                            mBankingDB.createAccount(mSelectedRole, account);
                            mExtraBaseMoney.setText("0");
                            mSelectedRole = mDB.getRole(mSelectedRole.getId());
                            fillInfo();
                        }
                        catch (NumberFormatException e1) {
                            if (DEBUG) e1.printStackTrace();
                            JOptionPane.showMessageDialog(mExtraBaseMoney, "Extra Base money has an illegal format", "Fprmat Error", JOptionPane.ERROR_MESSAGE);
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
        else if (e.getActionCommand().equals("removeResource")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mSelectedRole != null) {
                        try {
                            int[] indices = mResourcesList.getSelectedIndices();
                            Resource[] resources = new Resource[indices.length];
                            for (int i = 0; i < indices.length; i++) {
                                int indice = indices[i];
                                resources[i] = (Resource) mResourcesList.getModel().getElementAt(indice);
                            }
                            mInfluenceDB.removeResourcesFromRole(mSelectedRole, resources);
                            mSelectedRole = mDB.getRole(mSelectedRole.getId());
                            fillInfo();
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
        else if (e.getActionCommand().equals("newAccount")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mSelectedRole != null) {
                        String name = JOptionPane.showInputDialog("Owner name: ");
                        if (name != null) {
                            BankAccount ac = new BankAccount(name, 0, false);
                            try {
                                mBankingDB.createAccount(mSelectedRole, ac);
                                mSelectedRole = mDB.getRole(mSelectedRole.getId());
                                fillInfo();
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
        else if (e.getActionCommand().equals("saveExtraMonthlyIncome")) {
            if (mSelectedRole != null) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            mBankingDB.setExtraMonthlyIncome(mSelectedRole, Long.parseLong(mExtraMonthlyIncome.getText()));
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
        else if (e.getActionCommand().equals("buyResource")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    buyResource();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("buyMoreResource")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    buyMoreResource();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("addInfluence")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mSelectedRole != null) {
                        IntWithString intr = (IntWithString) mAvailableInfluencesCombo.getSelectedItem();
                        Influence inf = new Influence(intr.getNumber(), intr.getString(), Utilities.getSpinnerInteger(mInfluenceDots), mInfluencesNotes.getText());
                        try {
                            mInfluenceDB.addInfluenceToRole(mSelectedRole, inf);
                            mSelectedRole.addInfluence(inf);
                            mAssignedInfluencesList.setListData(mSelectedRole.getInfluences());
                            mAvailableInfluencesModel.hide(intr);
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
        else if (e.getActionCommand().equals("removeInfluence")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mSelectedRole != null) {
                        Object[] objs = mAssignedInfluencesList.getSelectedValues();
                        Influence[] influences = new Influence[objs.length];
                        for (int i = 0; i < objs.length; i++) {
                            influences[i] = (Influence) objs[i];
                        }
                        try {
                            mInfluenceDB.removeInfluencesFromRole(mSelectedRole, influences);
                            Vector infV = mSelectedRole.getInfluences();
                            for (int i = 0; i < influences.length; i++) {
                                Influence influence = influences[i];
                                infV.remove(influence);
                                mAvailableInfluencesModel.show(influence.getId());
                            }
                            mSelectedRole.setInfluences(infV);
                            mAssignedInfluencesList.setListData(infV);
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
    }

    private void buyMoreResource() {
        if (mSelectedRole != null) {
            Resource res = (Resource) mResourcesList.getSelectedValue();
            if (res != null) {
                try {
                    boolean ok = BuyResourceDialog.showDialog(this, mSelectedRole, res);
                    if (ok) {
                        mSelectedRole = mDB.getRole(mSelectedRole.getId());
                        fillInfo();
                    }
                }
                catch (Exception e) {
                    if (DEBUG) e.printStackTrace();
                    report(e);
                }
            }
        }
    }

    private void buyResource() {
        if (mSelectedRole != null) {
            try {
                boolean ok = BuyResourceDialog.showDialog(this, mSelectedRole);
                if (ok) {
                    mSelectedRole = mDB.getRole(mSelectedRole.getId());
                    fillInfo();
                }
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                report(e);
            }
        }
    }

    private void fillInfo() {
        mShowingRole = true;
        calculateBaseMoney();
        calculateBaseMonthlyIncome();
        if (mSelectedRole != null) {
            if (mSelectedRole.getBankAccounts() == null || mSelectedRole.getBankAccounts().size() <= 0) {
                mTPane.setSelectedIndex(FIRST_ACCOUNT_TAB);
                mTPane.setEnabledAt(INFLUENCE_TAB, false);
                mTPane.setEnabledAt(BANKING_TAB, false);
                mTPane.setEnabledAt(FIRST_ACCOUNT_TAB, true);
            }
            else {
                Integer lastIndex = (Integer) mLastTPanelForRole.get(new Integer(mSelectedRole.getId()));
                if (lastIndex != null) {
                    mTPane.setSelectedIndex(lastIndex.intValue());
                }
                else {
                    mTPane.setSelectedIndex(INFLUENCE_TAB);
                }
                mTPane.setEnabledAt(INFLUENCE_TAB, true);
                mTPane.setEnabledAt(BANKING_TAB, true);
                mTPane.setEnabledAt(FIRST_ACCOUNT_TAB, false);
            }
            List professions = mSelectedRole.getProfessions();
            ArrayList mortalProfessions = new ArrayList();
            ArrayList nonMortalProfessions = new ArrayList();
            for (int i = 0; i < professions.size(); i++) {
                Profession profession = (Profession) professions.get(i);
                if (profession.isMortal()) {
                    mortalProfessions.add(profession);
                }
                else {
                    nonMortalProfessions.add(profession);
                }
            }
            mMortalProfessionsTableModel.setProfessions(mortalProfessions);
            mProfessionsTableModel.setProfessions(nonMortalProfessions);
            mResourcesList.setListData(mSelectedRole.getResources().toArray());
            mBankAccountsTableModel.setBankAccounts(mSelectedRole.getBankAccounts());
            mExtraMonthlyIncome.setText(mSelectedRole.getExtraMonthlyIncome() + "");
            Vector influences = mSelectedRole.getInfluences();
            mAssignedInfluencesList.setListData(influences);
            if (DEBUG) System.out.println("[RolesInfluenceDialog][fillInfo][352] calling showAll");
            mAvailableInfluencesModel.showAll();
            for (int i = 0; i < influences.size(); i++) {
                Influence influence = (Influence) influences.elementAt(i);
                if (DEBUG) System.out.println("[RolesInfluenceDialog][fillInfo][356] calling hide on " + influence);
                mAvailableInfluencesModel.hide(influence.getId());
            }
        }
        mShowingRole = false;
    }

    private void updateBankAccounts() {
        if (mSelectedRole != null) {
            try {
                List bankAccounts = mBankingDB.getBankAccountsForRole(mSelectedRole.getId());
                mSelectedRole.setBankAccounts(bankAccounts);
                mBankAccountsTableModel.setBankAccounts(bankAccounts);
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                report(e);
            }
        }
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    protected void init() throws SQLException, RemoteException {
        mDB = Proxy.getRetrievalDB();
        mInfluenceDB = Proxy.getInfluenceDB();
        mBankingDB = Proxy.getBankingDB();
        //Vector v = mDB.getRoleNames();
        mRolesFilterRules = new Rules();
        mRolesFilterRules.setVisibleDomain(AppPreferences.getPreferredDomain().getId());
        mRolesFilterRules.setViewGhouls(true);
        mRolesFilterRules.setViewNotPlayedRoles(false);
        mRolesFilterRules.setVisibleVitals(Vitals.NORMAL);
        mRoleNamesListModel = new RoleNamesListModel(mRolesFilterRules);
        mRolesList = new JList(mRoleNamesListModel);
        mRolesList.setCellRenderer(new RolesListCellRenderer());
        mRolesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mRolesList.addListSelectionListener(this);
//        mRoleNamesListModel.addListDataListener(new ListDataListener() {
//            public void intervalAdded(ListDataEvent e) {
//                /*if (mRolesList.getSelectedIndex() < 0) {
//                    mRolesList.setSelectedIndex(0);
//                }*/
//            }
//
//            public void intervalRemoved(ListDataEvent e) {
//            }
//
//            public void contentsChanged(ListDataEvent e) {
//                if (mRoleNamesListModel.getSize() > 0) {
//                    if (mRolesList.getSelectedIndex() < 0) {
//                        mRolesList.setSelectedIndex(0);
//                    }
//                }
//            }
//        });
        if (mRoleNamesListModel.getSize() > 0) {
            mRolesList.setSelectedIndex(0);
        }
        getContentPane().add(new JScrollPane(mRolesList), BorderLayout.WEST);

        mTPane = new JTabbedPane();
        mTPane.addTab("Resources/Influences", createInfluencePanel());
        mTPane.addTab("Banking", createBankingPanel());
        mTPane.addTab("First account", createFirstAccountPanel());
        mTPane.setEnabledAt(2, false);
        mLastTPanelForRole = new HashMap();
        mTPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!mShowingRole) {
                    if (mSelectedRole != null) {
                        int index = mTPane.getSelectedIndex();
                        mLastTPanelForRole.put(new Integer(mSelectedRole.getId()), new Integer(index));
                    }
                }
            }
        });
        getContentPane().add(mTPane, BorderLayout.CENTER);
        getContentPane().add(createToolBar(), BorderLayout.NORTH);
        pack();
        Utilities.positionMe(this);
        setVisible(true);
    }

    private JToolBar createToolBar() throws SQLException, RemoteException {
        JToolBar toolBar = new JToolBar("Role visibility");
        toolBar.setRollover(true);
        toolBar.setMargin(null);
        toolBar.setBorderPainted(true);
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
                            if (mRoleNamesListModel.getSize() > 0) {
                                if (mRolesList.getSelectedIndex() < 0) {
                                    mRolesList.setSelectedIndex(0);
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
        };
        mViewClansCombo.setAction(action);
        toolBar.add(new JLabel("Clans: "));
        toolBar.add(mViewClansCombo);
        Vector domains = mDB.getDomains();
        domains.add(0, new Domain(-1, "All"));
        mViewDomainsCombo = new JComboBox(domains);
        int domainId = mRolesFilterRules.getVisibleDomain();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = (Domain) domains.elementAt(i);
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
                        mRolesFilterRules.setVisibleDomain(d.getId());
                        try {
                            mRoleNamesListModel.update();
                            if (mRoleNamesListModel.getSize() > 0) {
                                if (mRolesList.getSelectedIndex() < 0) {
                                    mRolesList.setSelectedIndex(0);
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
        };
        mViewDomainsCombo.setAction(action);
        toolBar.add(new JLabel("  Domains: "));
        toolBar.add(mViewDomainsCombo);

        DefaultComboBoxModel model = new DefaultComboBoxModel(Vitals.ALL);
        model.insertElementAt("All", 0);
        mViewVitalsCombo = new JComboBox(model);
        if (mRolesFilterRules.getVisibleVitals() != null) {
            mViewVitalsCombo.setSelectedItem(mRolesFilterRules.getVisibleVitals());
        }
        else {
            mViewVitalsCombo.setSelectedIndex(0);
        }
        action = new MutableAction("View Vitals") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Object o = mViewVitalsCombo.getSelectedItem();
                        if (o instanceof Vitals) {
                            mRolesFilterRules.setVisibleVitals((Vitals) o);
                        }
                        else {
                            mRolesFilterRules.setVisibleVitals(null);
                        }
                        try {
                            mRoleNamesListModel.update();
                            if (mRoleNamesListModel.getSize() > 0) {
                                if (mRolesList.getSelectedIndex() < 0) {
                                    mRolesList.setSelectedIndex(0);
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
        };
        mViewVitalsCombo.setAction(action);
        toolBar.add(new JLabel("  Vitals: "));
        toolBar.add(mViewVitalsCombo);
        ActionListener listen = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (e.getActionCommand().equals("viewGhouls")) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            JCheckBox box = (JCheckBox) e.getSource();
                            mRolesFilterRules.setViewGhouls(box.isSelected());
                            try {
                                mRoleNamesListModel.update();
                                if(mRoleNamesListModel.getSize() > 0) {
                                    if(mRolesList.getSelectedIndex() < 0) {
                                        mRolesList.setSelectedIndex(0);
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
                else if (e.getActionCommand().equals("viewNotPlayed")) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            JCheckBox box = (JCheckBox) e.getSource();
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
                }
            }
        };
        JCheckBox checkBox = new JCheckBox("Show Ghouls", mRolesFilterRules.isViewGhouls());
        checkBox.setActionCommand("viewGhouls");
        checkBox.addActionListener(listen);
        toolBar.add(checkBox);

        checkBox = new JCheckBox("Show Not played", mRolesFilterRules.isViewNotPlayedRoles());
        checkBox.setActionCommand("viewNotPlayed");
        checkBox.addActionListener(listen);
        toolBar.add(checkBox);
        toolBar.add(Box.createHorizontalGlue());
        return toolBar;
    }

    private JPanel createFirstAccountPanel() {
        GridBagPanel panel = new GridBagPanel();
        mBaseMoneyField = new JTextField(12);
        mBaseMoneyField.setEditable(false);
        panel.add("Base Money: ");
        panel.addLine(mBaseMoneyField);
        IntegerVerifier verf = new IntegerVerifier();
        mExtraBaseMoney = new JTextField(12);
        mExtraBaseMoney.setInputVerifier(verf);
        mExtraBaseMoney.setToolTipText("No decimals, no characters, only a number");
        panel.add("Extra Money: ");
        panel.addLine(mExtraBaseMoney);

        JPanel bPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("Create");
        btn.setActionCommand("createNew");
        btn.addActionListener(this);
        bPanel.add(btn);
        panel.addLine(bPanel);
        return panel;
    }

    private JPanel createBankingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        GridBagPanel incPanel = new GridBagPanel();

        mBaseMonthlyIncome = new JTextField(12);
        mBaseMonthlyIncome.setEditable(false);
        incPanel.add("Base Monthly Income: ");
        incPanel.addLine(mBaseMonthlyIncome);

        mExtraMonthlyIncome = new JTextField(12);
        mExtraMonthlyIncome.setInputVerifier(new LongVerifier());
        mExtraMonthlyIncome.setToolTipText("No decimals, no characters, only a number");
        incPanel.add("Extra Monthly Income: ");
        incPanel.addLine(mExtraMonthlyIncome);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("Save");
        btn.setActionCommand("saveExtraMonthlyIncome");
        btn.addActionListener(this);
        btnPanel.add(btn);
        incPanel.addLine(btnPanel);
        panel.add(incPanel, BorderLayout.NORTH);
        mBankAccountsTableModel = new BankAccountsTableModel(new ArrayList());
        mBankAccountsTable = new JTable(mBankAccountsTableModel);
        mBankAccountsTable.getColumnModel().getColumn(0).setMaxWidth(30);
        mBankAccountsTable.getColumnModel().getColumn(3).setMaxWidth(40);
        mBankAccountsTable.getColumnModel().getColumn(4).setMaxWidth(40);
        mBankAccountsTable.getColumnModel().getColumn(2).setCellRenderer(new MoneyTableCellRenderer());
        mBankAccountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(mBankAccountsTable);
        scroll.setBorder(BorderFactory.createTitledBorder("Accounts"));
        scroll.setPreferredSize(new Dimension(100, 100));
        panel.add(scroll, BorderLayout.CENTER);

        btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn = new JButton("New Account");
        btn.setActionCommand("newAccount");
        btn.addActionListener(this);
        btnPanel.add(btn);

        panel.add(btnPanel, BorderLayout.SOUTH);
        mWithdrawals = new Vector();
        mWithdrawalAction = new MutableAction("Withdrawal") {
            public void actionPerformed(ActionEvent e) {
                withdrawal();
            }
        };
        mDepositionAction = new MutableAction("Deposit") {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        };
        mSetIncomeAction = new MutableAction("Set as Income") {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        int row = mBankAccountsTable.getSelectedRow();
                        if (row >= 0) {
                            BankAccount account = mBankAccountsTableModel.setIncome(row);
                            try {
                                mBankingDB.setIncome(mSelectedRole, account);
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

        mBankPopup = new JPopupMenu("Banking");
        mBankPopup.add(mWithdrawalAction);
        mBankPopup.add(mDepositionAction);
        mBankPopup.add(mSetIncomeAction);
        mBankAccountsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    mBankPopup.show(mBankAccountsTable, e.getX(), e.getY());
                }
            }
        });

        return panel;
    }

    class DepositPanel extends GridBagPanel {
        public JComboBox mComboBox;
        public JCheckBox mCheckBox;
        public JTextField mTextField;

        public DepositPanel() {
            super();
            mComboBox = new JComboBox();
            mCheckBox = new JCheckBox("Deposit all");
            mTextField = new JTextField(20);
            addLine(mComboBox);
            addLine("Message: ", mTextField);
            addLine(mCheckBox);
            setBorder(BorderFactory.createTitledBorder("Select whitch withdrawal to deposit."));
        }
    }

    private DepositPanel mDepositPanel = null;

    private void deposit() {
        if (mDepositPanel == null) {
            mDepositPanel = new DepositPanel();
        }
        int row = mBankAccountsTable.getSelectedRow();
        if (row >= 0) {
            BankAccount bac = mBankAccountsTableModel.getBankAccount(row);
            if (mWithdrawals.size() > 0) {
                mDepositPanel.mComboBox.setModel(new DefaultComboBoxModel(mWithdrawals));
                int choice = JOptionPane.showConfirmDialog(mBankAccountsTable, mDepositPanel, "Deposit", JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    if (mDepositPanel.mCheckBox.isSelected()) {
                        String message = mDepositPanel.mTextField.getText();
                        try {
                            mBankingDB.deposit(mWithdrawals, bac, message);
                            mWithdrawals.removeAllElements();
                            updateBankAccounts();
                        }
                        catch (Exception e) {
                            if (DEBUG) e.printStackTrace();
                            report(e);
                        }
                    }
                    else {
                        Withdrawal with = (Withdrawal) mDepositPanel.mComboBox.getSelectedItem();
                        if (with != null) {
                            String message = mDepositPanel.mTextField.getText();
                            try {
                                mBankingDB.deposit(with, bac, message);
                                mWithdrawals.remove(with);
                                updateBankAccounts();
                            }
                            catch (Exception e) {
                                if (DEBUG) e.printStackTrace();
                                report(e);
                            }
                        }
                    }
                }
            }
            else {
                int choise = JOptionPane.showConfirmDialog(mBankAccountsTable, "There are no withdrawals made.\n Do you want to make a deposit from the Bank?", "Deposit", JOptionPane.YES_NO_OPTION);
                if (choise == JOptionPane.YES_OPTION) {
                    JTextField ammountField = new JTextField(20);
                    ammountField.setText("0");
                    ammountField.setInputVerifier(new LongVerifier());
                    JTextField messageField = new JTextField(20);
                    GridBagPanel panel = new GridBagPanel();
                    panel.addLine("Ammount: ", ammountField);
                    panel.addLine("Message: ", messageField);
                    choise = JOptionPane.showConfirmDialog(mBankAccountsTable, panel, "Deposit From Bank", JOptionPane.OK_CANCEL_OPTION);
                    if (choise == JOptionPane.OK_OPTION) {
                        try {
                            long ammount = Long.parseLong(ammountField.getText());
                            String message = messageField.getText();
                            mBankingDB.depositFromBank(bac, ammount, message);
                            updateBankAccounts();
                        }
                        catch (NumberFormatException e) {
                            report(e);
                        }
                        catch (Exception e) {
                            if (DEBUG) e.printStackTrace();
                            report(e);
                        }
                    }
                }
            }
        }
    }

    private void withdrawal() {
        int row = mBankAccountsTable.getSelectedRow();
        if (row >= 0) {
            BankAccount bac = mBankAccountsTableModel.getBankAccount(row);
            String sAmmount = JOptionPane.showInputDialog(mBankAccountsTable, "Ammount to withdrawal: ", "Withdrawal", JOptionPane.PLAIN_MESSAGE);
            if (sAmmount != null) {
                try {
                    long ammount = Long.parseLong(sAmmount);
                    if (ammount > 0) {
                        mWithdrawals.add(0, new Withdrawal(bac, ammount));
                    }
                    else {
                        JOptionPane.showMessageDialog(mBankAccountsTable, "You cannot withdrawal 0 or negative ammount of money!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(mBankAccountsTable, sAmmount + " is not a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private JPanel createInfluencePanel() throws SQLException, RemoteException {
        //GridBagPanel panel = new GridBagPanel();
        JPanel gridPanel = new JPanel(new GridLayout(3, 1));
        //panel.addLine("  ");

        /************************** PROFESSIONS *******************************/
        JPanel professionsPanel = new JPanel(new BorderLayout());
        JPanel tablePanel = new JPanel(new GridLayout(1, 2));
        mProfessionsTableModel = new ProfessionsTableModel(7, false);
        JTable table = new JTable(mProfessionsTableModel);
        Vector v = mDB.getNonMortalProfessions();
        v.add(0, "");
        JComboBox combo = new JComboBox(v);
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(combo));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(new Dimension(200, 100)));
        scroll.setBorder(BorderFactory.createTitledBorder("Professions"));
        tablePanel.add(scroll);

        mMortalProfessionsTableModel = new ProfessionsTableModel(7, true);
        table = new JTable(mMortalProfessionsTableModel);
        v = mDB.getMortalProfessions();
        v.add(0, "");
        combo = new JComboBox(v);
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(combo));
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(new Dimension(200, 100)));
        scroll.setBorder(BorderFactory.createTitledBorder("Mortal Professions"));
        tablePanel.add(scroll);
        professionsPanel.add(tablePanel, BorderLayout.CENTER);

        TableModelListener tbListener = new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                calculateBaseMonthlyIncome();
            }
        };
        mProfessionsTableModel.addTableModelListener(tbListener);
        mMortalProfessionsTableModel.addTableModelListener(tbListener);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("Save");
        btn.setActionCommand("saveInfluences");
        btn.addActionListener(this);
        btnPanel.add(btn);
        professionsPanel.add(btnPanel, BorderLayout.SOUTH);
        gridPanel.add(professionsPanel);
        /***************** /PROFESSIONS **************************************
         ****************** RESOURCES ****************************************/
        JPanel resourcesPanel = new JPanel(new BorderLayout());
        mResourcesList = new JList();
        mResourcesList.setCellRenderer(new ResourceListCellRenderer());

        JScrollPane pane = new JScrollPane(mResourcesList);
        pane.setPreferredSize(new Dimension(-1, 100));
        pane.setBorder(BorderFactory.createTitledBorder("Resources"));
        resourcesPanel.add(pane, BorderLayout.CENTER);

        btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btn = new JButton("Buy Resource");
        btn.setActionCommand("buyResource");
        btn.addActionListener(this);
        btnPanel.add(btn);

        btn = new JButton("Remove Resource");
        btn.setActionCommand("removeResource");
        btn.setToolTipText("Remove the selected Resources from the role's posession");
        btn.addActionListener(this);
        btnPanel.add(btn);

        btn = new JButton("Buy More / Sell");
        btn.setActionCommand("buyMoreResource");
        btn.setToolTipText("Buy more of the selected Resource or sell some of it to the Bank");
        btn.addActionListener(this);
        btnPanel.add(btn);

        resourcesPanel.add(btnPanel, BorderLayout.SOUTH);
        gridPanel.add(resourcesPanel);
        /***************************** / RESOURCES *******************************
         ******************************* INFLUENCES ******************************/
        JPanel influenceBorderPanel = new JPanel(new BorderLayout());
        influenceBorderPanel.setBorder(BorderFactory.createTitledBorder("Influences / Backgrounds"));
        GridBagPanel inflPanel = new GridBagPanel();
        inflPanel.setAnchor(GridBagConstraints.CENTER);
        JPanel avPanel = new JPanel(new BorderLayout());
        mAvailableInfluencesModel = new VisiblesComboboxModel(mDB.getInfluenceNames());
        mAvailableInfluencesCombo = new JComboBox(mAvailableInfluencesModel);
        mInfluenceDots = Utilities.createIntegerJSpinner(1, 10, 1, 1);
        avPanel.add(mAvailableInfluencesCombo, BorderLayout.CENTER);
        avPanel.add(mInfluenceDots, BorderLayout.EAST);
        mInfluencesNotes = new JTextField();
        mInfluencesNotes.setToolTipText("Notes");
        avPanel.add(mInfluencesNotes, BorderLayout.SOUTH);
        inflPanel.add(avPanel);
        GridBagPanel bPanel = new GridBagPanel();
        btn = new JButton(">>");
        btn.setActionCommand("addInfluence");
        btn.addActionListener(this);
        btn.setMargin(new Insets(1, 1, 1, 1));
        bPanel.addLine(btn);
        btn = new JButton("<<");
        btn.setActionCommand("removeInfluence");
        btn.addActionListener(this);
        btn.setMargin(new Insets(1, 1, 1, 1));
        bPanel.addLine(btn);
        inflPanel.addLine(bPanel);
        influenceBorderPanel.add(inflPanel, BorderLayout.WEST);
        mAssignedInfluencesList = new JList();
        mAssignedInfluencesList.setVisibleRowCount(4);
        scroll = new JScrollPane(mAssignedInfluencesList);
        scroll.setPreferredSize(new Dimension(120, 70));
        //inflPanel.addLine(scroll);
        influenceBorderPanel.add(scroll, BorderLayout.CENTER);
        gridPanel.add(influenceBorderPanel);

        return gridPanel;
    }

    /*class ResourceListMouseListener extends MouseAdapter implements MouseMotionListener {
        int lastRow = -1;
        private NumberFormat mFormat;

        public ResourceListMouseListener() {
            mFormat = NumberFormat.getCurrencyInstance();
        }

        public void mouseDragged(MouseEvent e) {
        }
        public void mouseMoved(MouseEvent e) {
            mResourcesList.getToolTipText(e)
        }

        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
        }

        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
        }
    }*/

    private void calculateBaseMoney() {
        if (mSelectedRole != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance();

            Clan clan = mSelectedRole.getClan();
            mBaseMoney = 0;
            if (clan != null) {
                mBaseMoney += clan.getBaseIncome();
            }
            try {
                int age = mSelectedRole.getAge();
                mBaseMoney += mDB.getBaseMoneyForAge(age);
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
            }
            mBaseMoneyField.setText(format.format(mBaseMoney));
        }
    }

    private void calculateBaseMonthlyIncome() {
        //Base money = clan + age
        //base income = abilities + proffessions + resources
        if (mSelectedRole != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            mBaseMonthlyIncome.setText(format.format(mSelectedRole.baseMonthlyIncome()));
        }
    }

    public static void main(String[] args) throws SQLException, RemoteException {
        JDialog dialog = new RolesInfluenceDialog((JFrame) null);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
