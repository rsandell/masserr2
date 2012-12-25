package se.tdt.bobby.wodcc.ui.dialogs;


import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.data.Resource;
import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.verifiers.IntegerVerifier;
import se.tdt.bobby.wodcc.ui.components.models.InfluencesTableModel;
import se.tdt.bobby.wodcc.ui.components.verifiers.FloatVerifier;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.text.NumberFormat;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 * Description
 *
 *
 * Created: 2004-feb-03 00:46:44
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class ResourcesDialog extends JDialog implements ActionListener, ListSelectionListener {
    private RetrievalDB mRetrievalDB;
    private JList mResourcesList;
    private Resource mSelectedResource;
    private JTextField mNameField;
    private JTextArea mDescriptionArea;
    private JTextField mIncomeField;
    private JProgressBar mPercentBar;
    private InfluencesTableModel mInfluencesTableModel;
    private static final boolean DEBUG = false;
    private ManipulationDB mManipulationDB;
    private JTextField mCostField;
    private JComboBox mDomainsCombo;
    private JComboBox mViewDomainCombo;
    private JComboBox mViewResourceTypesCombo;
    private JComboBox mResourceTypeCombo;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("new")) {
            if (DEBUG) System.out.println("[ResourcesDialog][actionPerformed][40] ");
            mResourcesList.clearSelection();
            mSelectedResource = new Resource(-1, "", "", 0, 0, "Other");
            mSelectedResource.setDomain(AppPreferences.getPreferredDomain());
            //updateList();
            updateInfo();
        }
        else if (e.getActionCommand().equals("save")) {
            if (mSelectedResource != null) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        mSelectedResource.setName(mNameField.getText());
                        mSelectedResource.setDescription(mDescriptionArea.getText());
                        mSelectedResource.setIncome(Integer.parseInt(mIncomeField.getText()));
                        mSelectedResource.setCost(Float.parseFloat(mCostField.getText()));
                        mSelectedResource.setInfluences(mInfluencesTableModel.getInfluences());
                        mSelectedResource.setDomain((Domain)mDomainsCombo.getSelectedItem());
                        String newType = (String) mResourceTypeCombo.getSelectedItem();
                        boolean updateTypesOK = false;
                        if(!newType.equals(mSelectedResource.getType())) {
                            updateTypesOK = true;
                        }
                        mSelectedResource.setType(newType);
                        try {
                            if (mSelectedResource.getId() < 0) {
                                mManipulationDB.addResource(mSelectedResource);
                            }
                            else {
                                mManipulationDB.updateResource(mSelectedResource);
                            }
                            updateList();
                            if(updateTypesOK) {
                                updateTypes();
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
        else if (e.getActionCommand().equals("domainChanged")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    mResourcesList.setSelectedIndices(new int[0]);
                    updateList();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("typeChanged")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    mResourcesList.setSelectedIndices(new int[0]);
                    updateList();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    private void updateTypes() throws SQLException, RemoteException {
        String oldSelection = (String) mViewResourceTypesCombo.getSelectedItem();
        Vector<String> types = mRetrievalDB.getResourceTypes();
        mResourceTypeCombo.setModel(new DefaultComboBoxModel(types));
        mViewResourceTypesCombo.setModel(new DefaultComboBoxModel(types));
        mViewResourceTypesCombo.setSelectedItem(oldSelection);
    }

    private void updateList() {
        try {
            Domain domain = (Domain) mViewDomainCombo.getSelectedItem();
            Vector v = mRetrievalDB.getResources(domain.getId(), (String) mViewResourceTypesCombo.getSelectedItem());
            int selectedIndex = mResourcesList.getSelectedIndex();
            mResourcesList.setListData(v);
            if (selectedIndex >= 0) {
                mResourcesList.setSelectedIndex(selectedIndex);
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        mSelectedResource = (Resource) mResourcesList.getSelectedValue();
        updateInfo();
    }

    public ResourcesDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Resources");
        init();
    }

    public ResourcesDialog(Dialog owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Resources");
        init();
    }

    private void init() throws SQLException, RemoteException {
        mRetrievalDB = Proxy.getRetrievalDB();
        mManipulationDB = Proxy.getManipulationDB();
        Vector<String> resourceTypes = mRetrievalDB.getResourceTypes();
        mViewResourceTypesCombo = new JComboBox(resourceTypes);
        mViewResourceTypesCombo.setActionCommand("typeChanged");
        mViewResourceTypesCombo.setToolTipText("Selected Resource Type");
        mViewResourceTypesCombo.addActionListener(this);
        Vector resources = mRetrievalDB.getResources(AppPreferences.getPreferredDomain().getId(), (String) mViewResourceTypesCombo.getSelectedItem());
        mResourcesList = new JList(resources);
        mResourcesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mResourcesList.addListSelectionListener(this);
        JScrollPane scroll = new JScrollPane(mResourcesList);
        scroll.setPreferredSize(new Dimension(220, 220));
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scroll, BorderLayout.CENTER);
        Vector domains = mRetrievalDB.getDomains();
        mViewDomainCombo = new JComboBox(domains);
        mViewDomainCombo.setToolTipText("Selected Domain");
        Domain prDomain = AppPreferences.getPreferredDomain();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = (Domain) domains.elementAt(i);
            if(domain.getId() == prDomain.getId()) {
                mViewDomainCombo.setSelectedItem(domain);
                break;
            }
        }
        mViewDomainCombo.setActionCommand("domainChanged");
        mViewDomainCombo.addActionListener(this);
        GridBagPanel gbPanel = new GridBagPanel();
        gbPanel.addLine(mViewDomainCombo);
        gbPanel.addLine(mViewResourceTypesCombo);
        listPanel.add(gbPanel, BorderLayout.NORTH);
        getContentPane().add(listPanel, BorderLayout.WEST);

        GridBagPanel panel = new GridBagPanel();
        panel.setIpadx(4);
        panel.setIpady(2);
        mNameField = new JTextField(25);
        panel.addLine(new JLabel("Name: "), mNameField);
        panel.add(new JLabel("Description: "));
        mDescriptionArea = new JTextArea(4, 25);
        mDescriptionArea.setWrapStyleWord(true);
        mDescriptionArea.setLineWrap(true);
        panel.addLine(new JScrollPane(mDescriptionArea));
        mResourceTypeCombo = new JComboBox(resourceTypes);
        mResourceTypeCombo.setEditable(true);
        panel.addLine("Type: ", mResourceTypeCombo);
        mInfluencesTableModel = new InfluencesTableModel(3);
        JTable table = new JTable(mInfluencesTableModel);
        Vector v = mRetrievalDB.getInfluenceNames();
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox(v)));
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(100, 70));
        panel.addLine("Influences: ", scroll);
        mIncomeField = new JTextField(25);
        IntegerVerifier verify = new IntegerVerifier();
        mIncomeField.setInputVerifier(verify);
        mIncomeField.setToolTipText("No decimals, no characters, only a number");
        panel.addLine("Percent Income: ", mIncomeField);
        mCostField = new JTextField(25);
        mCostField.setInputVerifier(new FloatVerifier());
        mCostField.setToolTipText("Cost of buying 1%");
        panel.addLine("Percent Cost: ", mCostField);
        //Vector domains = mRetrievalDB.getDomains();
        mDomainsCombo = new JComboBox(domains);
        panel.addLine("Domain: ", mDomainsCombo);
        mPercentBar = new JProgressBar(0, 100);
        mPercentBar.setStringPainted(true);
        panel.addLine("Taken %: ", mPercentBar);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("New");
        btn.setActionCommand("new");
        btn.addActionListener(this);
        btnPanel.add(btn);
        btn = new JButton("Save");
        btn.setActionCommand("save");
        btn.addActionListener(this);
        btnPanel.add(btn);
        panel.addLine(btnPanel);

        getContentPane().add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if (resources.size() > 0) {
            mResourcesList.setSelectedIndex(0);
            updateInfo();
        }
        pack();
        setResizable(false);
        Utilities.positionMe(this);
        setVisible(true);
    }

    private void updateInfo() {
        if (DEBUG) System.out.println("[ResourcesDialog][updateInfo][158] ");
        if (mSelectedResource != null) {
            if (DEBUG) System.out.println("[ResourcesDialog][updateInfo][160] mSelectedResource != null");
            mNameField.setText(mSelectedResource.getName());
            mDescriptionArea.setText(mSelectedResource.getDescription());
            mPercentBar.setValue(Math.round(mSelectedResource.getPercent()));
            NumberFormat format = NumberFormat.getPercentInstance();
            mPercentBar.setString(format.format(mSelectedResource.getPercent() / 100f));
            mIncomeField.setText(mSelectedResource.getIncome() + "");
            mCostField.setText(mSelectedResource.getCost() + "");
            mResourceTypeCombo.setSelectedItem(mSelectedResource.getType());
            if (mSelectedResource.getId() >= 0) {
                try {
                    mInfluencesTableModel.setInfluences(mRetrievalDB.getInfluencesForResource(mSelectedResource));
                }
                catch (Exception e) {
                    if (DEBUG) e.printStackTrace();
                    report(e);
                }
            }
            else {
                mInfluencesTableModel.clear();
            }
            for (int i = 0; i < mDomainsCombo.getModel().getSize(); i++) {
                Domain dom = (Domain) mDomainsCombo.getModel().getElementAt(i);
                if(dom.getId() == mSelectedResource.getDomain().getId()) {
                    mDomainsCombo.setSelectedItem(dom);
                    break;
                }
            }
        }
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) throws SQLException, RemoteException {
        JDialog di = new ResourcesDialog((JFrame) null);
        di.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
