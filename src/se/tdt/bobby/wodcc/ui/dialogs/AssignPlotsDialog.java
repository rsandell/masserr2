package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.proxy.interfaces.InfluenceDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering.Rules;
import se.tdt.bobby.wodcc.ui.components.models.RoleNamesListModel;
import se.tdt.bobby.wodcc.ui.components.view.PlotsListCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.RolesListCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-25 19:35:58
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class AssignPlotsDialog extends JDialog implements ActionListener, ListSelectionListener {
    private InfluenceDB mInfluenceDB;
    private RetrievalDB mDB;
    private Rules mRolesFilterRules;
    private RoleNamesListModel mRoleNamesListModel;
    private JList mRolesList;
    private static final boolean DEBUG = false;
    private JComboBox mViewClansCombo;
    private JComboBox mViewDomainsCombo;
    private JComboBox mViewVitalsCombo;
    private JComboBox mSelectedPlotDomainsCombo;
    private JList mAvailablePlotsList;
    private JList mAssignedPlotsList;

    public AssignPlotsDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Assign Plots");
        init();
    }

    public AssignPlotsDialog(Dialog owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Assign Plots");
        init();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("assign")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        mInfluenceDB.assignPlots(mAvailablePlotsList.getSelectedValues(), (Role) mRolesList.getSelectedValue());
                        updateInfo();
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("unAssign")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        mInfluenceDB.unAssignPlots(mAssignedPlotsList.getSelectedValues(), (Role) mRolesList.getSelectedValue());
                        updateInfo();
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("plotDomainChanged")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        updateInfo();
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

    public void valueChanged(ListSelectionEvent e) {
        if (DEBUG) System.out.println("AssignPlotsDialog.valueChanged(107) top");
        if (!e.getValueIsAdjusting()) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        if (DEBUG) System.out.println("AssignPlotsDialog.run(111) caloling updateinfo");
                        updateInfo();
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

    private void updateInfo() throws SQLException, RemoteException {
        if (DEBUG) System.out.println("AssignPlotsDialog.updateInfo(122) top");
        if (mRoleNamesListModel.getSize() > 0) {
            Role role = (Role) mRolesList.getSelectedValue();
            if (role == null) {
                if (DEBUG) System.out.println("AssignPlotsDialog.updateInfo(126) role is null");
                mRolesList.setSelectedIndex(0);
                return;
            }
            Domain domain = (Domain) mSelectedPlotDomainsCombo.getSelectedItem();
            Vector<Plot> available = mInfluenceDB.getAvailablePlotsForRole(role, domain);
            Vector<Plot> assigned = mInfluenceDB.getAssignedPlotsForRole(role);
            mAvailablePlotsList.setListData(available);
            mAssignedPlotsList.setListData(assigned);
        }
    }

    private void init() throws SQLException, RemoteException {
        mDB = Proxy.getRetrievalDB();
        mInfluenceDB = Proxy.getInfluenceDB();
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
        PlotsListCellRenderer plotsCellRenderer = new PlotsListCellRenderer();
        mSelectedPlotDomainsCombo = new JComboBox(mDB.getDomains());
        mSelectedPlotDomainsCombo.setActionCommand("plotDomainChanged");
        mSelectedPlotDomainsCombo.addActionListener(this);
        mAvailablePlotsList = new JList();
        mAvailablePlotsList.setCellRenderer(plotsCellRenderer);
        JPanel centerpanel = new JPanel(new GridLayout(2, 1));
        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.add(mSelectedPlotDomainsCombo, BorderLayout.NORTH);
        panel.add(new JScrollPane(mAvailablePlotsList), BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createTitledBorder("Available Plots"));
        centerpanel.add(panel);

        panel = new JPanel(new BorderLayout(2, 2));
        mAssignedPlotsList = new JList();
        mAssignedPlotsList.setCellRenderer(plotsCellRenderer);
        JScrollPane scrollPane = new JScrollPane(mAssignedPlotsList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Assigned Plots"));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon icon = IconFactory.getIconFactory().getIcon("Down24.gif");
        Image img = icon.getImage().getScaledInstance(-1, 16, Image.SCALE_DEFAULT);
        icon = new ImageIcon(img);
        JButton btn = new JButton(icon);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setToolTipText("Assign Selected Plots");
        btn.setActionCommand("assign");
        btn.addActionListener(this);
        btnPanel.add(btn);
        icon = IconFactory.getIconFactory().getIcon("Up24.gif");
        img = icon.getImage().getScaledInstance(-1, 16, Image.SCALE_DEFAULT);
        icon = new ImageIcon(img);
        btn = new JButton(icon);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setToolTipText("Remove Selected Plots from Role");
        btn.setActionCommand("unAssign");
        btn.addActionListener(this);
        btnPanel.add(btn);
        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        centerpanel.add(panel);

        getContentPane().add(centerpanel, BorderLayout.CENTER);

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
                else if (e.getActionCommand().equals("viewNotPlayed")) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            JCheckBox box = (JCheckBox) e.getSource();
                            mRolesFilterRules.setViewNotPlayedRoles(box.isSelected());
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

    private void report(Exception pE1) {
        JOptionPane.showMessageDialog(this, pE1.getMessage(), pE1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }
}
