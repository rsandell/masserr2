package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.BankAccount;
import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.Resource;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.BankingDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.view.LineComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Vector;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-feb-11 22:19:44
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class BuyResourceDialog extends JDialog implements ListSelectionListener, ActionListener, ChangeListener {
    private RetrievalDB mRetrievalDB;
    private JList mResourcesList;
    private Role mRole;
    private Resource mResource;
    private BankingDB mBankingDB;
    private JComboBox mBankAccounts;
    private NumberFormat mFormat;
    private JLabel mAccountAmmountLabel;
    private JLabel mCostLabel;
    private JLabel mLeftLabel;
    private JSlider mPercentSlider;
    private JButton mOKBtn;
    private static final boolean DEBUG = false;
    private boolean mOKPerformed;
    //private JTabbedPane mResourcesTab;
    private JComboBox mResourceTypesCombo;
    private JComboBox mDomainsCombo;

    protected BuyResourceDialog(Frame owner, Role pRole) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Buy Resource", true);
        mRole = pRole;
        init();
    }

    protected BuyResourceDialog(Dialog owner, Role pRole) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Buy Resource", true);
        mRole = pRole;
        init();
    }

    protected BuyResourceDialog(Frame owner, Role pRole, Resource pResource) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Buy Resource", true);
        mRole = pRole;
        mResource = pResource;
        init();
    }

    protected BuyResourceDialog(Dialog owner, Role pRole, Resource pResource) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Buy Resource", true);
        mRole = pRole;
        mResource = pResource;
        init();
    }

    public static boolean showDialog(Frame owner, Role pRole) throws SQLException, RemoteException {
        BuyResourceDialog diag = new BuyResourceDialog(owner, pRole);
        return diag.isOKPerformed();
    }

    public static boolean showDialog(Dialog owner, Role pRole) throws SQLException, RemoteException {
        BuyResourceDialog diag = new BuyResourceDialog(owner, pRole);
        return diag.isOKPerformed();
    }

    public static boolean showDialog(Frame owner, Role pRole, Resource pResource) throws SQLException, RemoteException {
        BuyResourceDialog diag = new BuyResourceDialog(owner, pRole, pResource);
        return diag.isOKPerformed();
    }

    public static boolean showDialog(Dialog owner, Role pRole, Resource pResource) throws SQLException, RemoteException {
        BuyResourceDialog diag = new BuyResourceDialog(owner, pRole, pResource);
        return diag.isOKPerformed();
    }

    public boolean isOKPerformed() {
        return mOKPerformed;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("bankAccountChanged")) {
            calculate();
        }
        else if (e.getActionCommand().equals("ok")) {
            Resource res = (Resource) mResourcesList.getSelectedValue();
            if (res != null) {
                res.setPercent(mPercentSlider.getValue());
                try {
                    if (mResource != null) {
                        mBankingDB.buyMoreOrSellResource(mRole, (BankAccount) mBankAccounts.getSelectedItem(), res);
                    }
                    else {
                        mBankingDB.buyResource(mRole, (BankAccount) mBankAccounts.getSelectedItem(), res);
                    }
                    mOKPerformed = true;
                    setVisible(false);
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        }
        else if (e.getActionCommand().equals("close")) {
            mOKPerformed = false;
            setVisible(false);
        }
        else if (e.getActionCommand().equals("domainChanged")) {
            updateList();
        }
        else if (e.getActionCommand().equals("typeChanged")) {
            updateList();
        }
    }

    private void updateList() {
        try {
            Vector<Resource> resources = mRetrievalDB.getResources(((Domain) mDomainsCombo.getSelectedItem()).getId(), (String) mResourceTypesCombo.getSelectedItem());
            mResourcesList.setListData(resources);
            if (resources.size() > 0) {
                mResourcesList.setSelectedIndex(0);
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private void report(Exception pException) {
        JOptionPane.showMessageDialog(this, pException.getMessage(), pException.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    public void valueChanged(ListSelectionEvent e) {
        //Resource res = (Resource) mResourcesList.getSelectedValue();
        //mPercentSlider.setMaximum(100 - res.getPercent());
        calculate();
    }

    public void stateChanged(ChangeEvent e) {
        if (!mPercentSlider.getValueIsAdjusting()) {
            calculate();
            mPercentSlider.setToolTipText(mPercentSlider.getValue() + "%");
        }
    }

    private void calculate() {
        BankAccount ac = (BankAccount) mBankAccounts.getSelectedItem();
        int percent = mPercentSlider.getValue();
        Resource res = (Resource) mResourcesList.getSelectedValue();
        if (res != null) {
            mPercentSlider.setMaximum(100 - res.getPercent());
            if (mResource != null) {
                mPercentSlider.setMinimum(-mResource.getPercent());
            }
            mAccountAmmountLabel.setText(mFormat.format(ac.getAmmount()));
            float cost = res.getCost() * percent;
            mCostLabel.setText(mFormat.format(-cost));
            double left = ac.getAmmount() - cost;
            mLeftLabel.setText(mFormat.format(left));
            if (left < 0) {
                JOptionPane.showMessageDialog(this, "There is not enough money on that account\nto buy the specified ammount of that resource", "Not enough money!", JOptionPane.INFORMATION_MESSAGE);
                mOKBtn.setEnabled(false);
            }
            else {
                mOKBtn.setEnabled(true);
            }
        }
    }

    private void init() throws SQLException, RemoteException {
        mOKPerformed = false;
        mRetrievalDB = Proxy.getRetrievalDB();
        mBankingDB = Proxy.getBankingDB();
        Vector<String> types;
        Vector domains;
        Vector<Resource> resources;
        if (mResource != null) {
            //mResource = mRetrievalDB.getResource(mResource.getId());
            domains = new Vector();
            domains.add(mResource.getDomain());
            types = new Vector<String>();
            types.add(mResource.getType());
        }
        else {
            types = mRetrievalDB.getResourceTypes();
            domains = mRetrievalDB.getDomains();
        }

        mDomainsCombo = new JComboBox(domains);
        mResourceTypesCombo = new JComboBox(types);

        if (mResource != null) {
            resources = new Vector<Resource>();
            resources.add(mResource);
            mResourceTypesCombo.setEnabled(false);
            mDomainsCombo.setEnabled(false);
        }
        else {
            Domain preferredDomain = AppPreferences.getPreferredDomain();
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = (Domain) domains.elementAt(i);
                if (domain.equals(preferredDomain)) {
                    mDomainsCombo.setSelectedItem(domain);
                }
            }
            preferredDomain = (Domain) mDomainsCombo.getSelectedItem();
            resources = mRetrievalDB.getResources(preferredDomain.getId(), (String) mResourceTypesCombo.getSelectedItem());
        }

        mDomainsCombo.setActionCommand("domainChanged");
        mDomainsCombo.addActionListener(this);
        mResourceTypesCombo.setActionCommand("typeChanged");
        mResourceTypesCombo.addActionListener(this);

        mResourcesList = new JList(resources);
        /*if (mResource != null) {
            mResourcesList.setEnabled(false);
        }*/
        mResourcesList.setSelectedIndex(0);
        mResourcesList.addListSelectionListener(this);
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(mDomainsCombo, BorderLayout.NORTH);
        comboPanel.add(mResourceTypesCombo, BorderLayout.SOUTH);
        JPanel resourcePanel = new JPanel(new BorderLayout());
        resourcePanel.add(comboPanel, BorderLayout.NORTH);
        resourcePanel.add(new JScrollPane(mResourcesList), BorderLayout.CENTER);
        //mResourcesTab.addChangeListener(this);
        getContentPane().add(resourcePanel, BorderLayout.EAST);

        mBankAccounts = new JComboBox(mRole.getBankAccounts().toArray());
        mBankAccounts.setActionCommand("bankAccountChanged");
        mBankAccounts.addActionListener(this);
        mFormat = NumberFormat.getCurrencyInstance();
        Box box = Box.createVerticalBox();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        BankAccount ac = (BankAccount) mRole.getBankAccounts().get(0);
        mAccountAmmountLabel = new JLabel(mFormat.format(ac.getAmmount()));
        panel.add(mAccountAmmountLabel);
        box.add(panel);
        panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mCostLabel = new JLabel(mFormat.format(-0));
        panel.add(mCostLabel);
        box.add(panel);
        box.add(new LineComponent());
        panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mLeftLabel = new JLabel(mFormat.format(ac.getAmmount()));
        panel.add(mLeftLabel);
        box.add(panel);

        GridBagPanel gbPanel = new GridBagPanel();
        gbPanel.setAnchor(GridBagConstraints.NORTHWEST);
        gbPanel.add(mBankAccounts);
        gbPanel.addLine(box);
        if (mResource != null) {
            mPercentSlider = new JSlider(-mResource.getPercent(), 100);
        }
        else {
            mPercentSlider = new JSlider(0, 100, 0);
        }
        mPercentSlider.setMajorTickSpacing(10);
        mPercentSlider.setMinorTickSpacing(5);
        mPercentSlider.setSnapToTicks(true);
        mPercentSlider.setPaintLabels(true);
        mPercentSlider.setPaintTicks(true);
        mPercentSlider.setPaintTrack(true);
        mPercentSlider.addChangeListener(this);
        gbPanel.addLine(mPercentSlider);
        getContentPane().add(gbPanel, BorderLayout.CENTER);

        panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        mOKBtn = new JButton("OK");
        mOKBtn.setActionCommand("ok");
        mOKBtn.addActionListener(this);
        panel.add(mOKBtn);

        JButton btn = new JButton("Close");
        btn.setActionCommand("close");
        btn.addActionListener(this);
        panel.add(btn);

        getContentPane().add(panel, BorderLayout.SOUTH);
        calculate();
        pack();
        Utilities.positionMe(this);
        setVisible(true);
    }
}
