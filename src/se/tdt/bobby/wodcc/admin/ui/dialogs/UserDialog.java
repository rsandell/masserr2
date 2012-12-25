package se.tdt.bobby.wodcc.admin.ui.dialogs;

import se.tdt.bobby.wodcc.admin.ui.AdminFrame;
import se.tdt.bobby.wodcc.admin.ui.components.models.UserRightsTableModel;
import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.mgm.User;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.AdminDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.MutableAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-14 16:49:31
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class UserDialog extends JDialog implements ActionListener {
    private User mUser;
    private JTextField mUserNameField;
    private JPasswordField mPasswordField;
    private JPasswordField mConfirmPasswordField;
    private JTextField mFullNameField;
    private JTextArea mDescriptionArea;
    private UserRightsTableModel mUserRightsTableModel;
    private JComboBox mDomainCombo;
    private RetrievalDB mRetrievalDB;
    private AdminDB mAdminDB;
    private static final boolean DEBUG = false;
    private boolean mOKPerformed = false;
    private JCheckBox mOtherDomainsIsAll;
    private JList mOtherDomainsList;
    private JComboBox mOtherDomainsCombo;
    private JPopupMenu mOtherDomainsPopupMenu;
    private Vector<Domain> mOtherDomainsVector;


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        User user = compileUser();
                        if (user != null) {
                            if (mUser != null) {
                                user.setId(mUser.getId());
                                mAdminDB.updateUser(user);
                                mUser = user;
                            }
                            else {
                                user = mAdminDB.addUser(user);
                                if (user != null) {
                                    mUser = user;
                                }
                            }
                            mOKPerformed = true;
                            setVisible(false);
                            AdminFrame.sAdminFrame.updateTree();
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
        else if (e.getActionCommand().equals("cancel")) {
            mOKPerformed = false;
            setVisible(false);
        }
        else if (e.getActionCommand().equals("addOtherDomain")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    Domain domain = (Domain) mOtherDomainsCombo.getSelectedItem();
                    for (int i = 0; i < mOtherDomainsVector.size(); i++) {
                        Domain dom = mOtherDomainsVector.get(i);
                        if (dom.getId() == domain.getId()) {
                            return;
                        }
                    }
                    mOtherDomainsVector.add(domain);
                    mOtherDomainsList.setListData(mOtherDomainsVector);
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    private void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private User compileUser() {
        User user = null;
        if (mUserNameField.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "You must specify a Username!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (mPasswordField.getPassword().length <= 0) {
            JOptionPane.showMessageDialog(this, "You must specify a Password!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        else {
            String passwd = new String(mPasswordField.getPassword());
            String passwdCnf = new String(mConfirmPasswordField.getPassword());
            if (!passwd.equals(passwdCnf)) {
                JOptionPane.showMessageDialog(this, "Password and Confirm Password doesn't match!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        String userName = mUserNameField.getText();
        String passwd = new String(mPasswordField.getPassword());
        String fullName = mFullNameField.getText();
        String description = mDescriptionArea.getText();
        user = new User(-1, userName, passwd, (Domain) mDomainCombo.getSelectedItem(), mUserRightsTableModel.getUserRights(), fullName, description);
        user.setOtherDomainIsAll(mOtherDomainsIsAll.isSelected());        
        user.setOtherDomains(mOtherDomainsVector);
        return user;
    }

    public UserDialog(Frame owner, String title) throws HeadlessException, SQLException, RemoteException {
        super(owner, title, false);
        init();
    }

    public UserDialog(Dialog owner, String title) throws HeadlessException, SQLException, RemoteException {
        super(owner, title, false);
        init();
    }

    public UserDialog(Frame owner, String title, User pUser) throws HeadlessException, SQLException, RemoteException {
        super(owner, title, false);
        mUser = pUser;
        init();
    }

    public UserDialog(Dialog owner, String title, User pUser) throws HeadlessException, SQLException, RemoteException {
        super(owner, title, false);
        mUser = pUser;
        init();
    }

    private void init() throws SQLException, RemoteException {
        mRetrievalDB = Proxy.getRetrievalDB();
        mAdminDB = Proxy.getAdminDB();
        GridBagPanel topPanel = new GridBagPanel();
        mUserNameField = new JTextField(20);
        topPanel.addLine("Username: ", mUserNameField);
        mPasswordField = new JPasswordField(20);
        topPanel.addLine("Password: ", mPasswordField);
        mConfirmPasswordField = new JPasswordField(20);
        topPanel.addLine("Confirm Password: ", mConfirmPasswordField);
        mFullNameField = new JTextField(20);
        topPanel.addLine("Full Name: ", mFullNameField);
        Vector domains = mRetrievalDB.getDomains();
        mDomainCombo = new JComboBox(domains);
        topPanel.addLine("Domain: ", mDomainCombo);
        JPanel northWestPanel = new JPanel(new GridLayout(2, 1));
        northWestPanel.add(topPanel);
        mDescriptionArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(mDescriptionArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Description"));
        scrollPane.setPreferredSize(mDescriptionArea.getSize());
        northWestPanel.add(scrollPane);
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(northWestPanel, BorderLayout.CENTER);
        JPanel otherDomainsPanel = new JPanel(new BorderLayout());
        otherDomainsPanel.setBorder(BorderFactory.createTitledBorder("Other Domains"));
        mOtherDomainsIsAll = new JCheckBox("All");
        otherDomainsPanel.add(mOtherDomainsIsAll, BorderLayout.NORTH);
        mOtherDomainsList = new JList();
        String largestDomainName = "Carlshamn";
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = (Domain) domains.elementAt(i);
            if (domain.toString().length() > largestDomainName.length()) {
                largestDomainName = domain.toString();
            }
        }
        mOtherDomainsList.setPrototypeCellValue(largestDomainName);
        MutableAction delDomainAction = new MutableAction("Delete", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)) {
            public void actionPerformed(ActionEvent e) {
                Object[] selects = mOtherDomainsList.getSelectedValues();
                for (int i = 0; i < selects.length; i++) {
                    Object select = selects[i];
                    mOtherDomainsVector.removeElement(select);
                }
                mOtherDomainsList.setListData(mOtherDomainsVector);
            }
        };
        mOtherDomainsPopupMenu = new JPopupMenu("OtherDomains");
        mOtherDomainsPopupMenu.add(delDomainAction);
        mOtherDomainsPopupMenu.setInvoker(mOtherDomainsList);
        mOtherDomainsList.addMouseListener(new MouseAdapter() {
            /**
             * Invoked when the mouse has been clicked on a component.
             */
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    mOtherDomainsPopupMenu.show(mOtherDomainsList, e.getX(), e.getY());
                }
            }
        });
        otherDomainsPanel.add(new JScrollPane(mOtherDomainsList), BorderLayout.CENTER);
        if (Proxy.getLoggedInUser() != null) {
            if (Proxy.getLoggedInUser().isOtherDomainIsAll()) {
                mOtherDomainsCombo = new JComboBox(domains);
            }
            else {
                mOtherDomainsCombo = new JComboBox(Proxy.getLoggedInUser().getOtherDomains());
            }
        }
        else {
            mOtherDomainsCombo = new JComboBox(domains);
        }
        JPanel comboPanel = new JPanel(new BorderLayout(0, 0));
        comboPanel.add(mOtherDomainsCombo, BorderLayout.CENTER);
        JButton btn = new JButton("Add");
        btn.setActionCommand("addOtherDomain");
        btn.addActionListener(this);
        comboPanel.add(btn, BorderLayout.EAST);
        otherDomainsPanel.add(comboPanel, BorderLayout.SOUTH);
        if (mUser != null) {
            mOtherDomainsIsAll.setSelected(mUser.isOtherDomainIsAll());
            mOtherDomainsVector = mUser.getOtherDomains();
            mOtherDomainsList.setListData(mOtherDomainsVector);
        }
        else {
            mOtherDomainsVector = new Vector<Domain>();
            mOtherDomainsList.setListData(mOtherDomainsVector);
        }
        northPanel.add(otherDomainsPanel, BorderLayout.EAST);
        getContentPane().add(northPanel, BorderLayout.NORTH);
        if (mUser != null) {
            mUserRightsTableModel = new UserRightsTableModel(mUser.getUserRights());
        }
        else {
            mUserRightsTableModel = new UserRightsTableModel();
        }
        JTable table = new JTable(mUserRightsTableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rights"));
        table.getColumnModel().getColumn(1).setMaxWidth(55);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn = new JButton("OK");
        btn.setActionCommand("ok");
        btn.addActionListener(this);
        getRootPane().setDefaultButton(btn);
        btnPanel.add(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        if (mUser != null) {
            mUserNameField.setText(mUser.getUserName());
            mPasswordField.setText(mUser.getPassword());
            mConfirmPasswordField.setText(mUser.getPassword());
            mFullNameField.setText(mUser.getFullName());
            mDescriptionArea.setText(mUser.getDescription());
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = (Domain) domains.elementAt(i);
                if (domain.equals(mUser.getDomain())) {
                    mDomainCombo.setSelectedItem(domain);
                    break;
                }
            }
        }
        else {
            Domain prefDomain = AppPreferences.getPreferredDomain();
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = (Domain) domains.elementAt(i);
                if (domain.equals(prefDomain)) {
                    mDomainCombo.setSelectedItem(domain);
                    break;
                }
            }
        }
        pack();
        Dimension size = getSize();
        size.height = 590;
        setSize(size);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Utilities.positionMe(this);
        mOKPerformed = false;
    }

    public boolean isOKPerformed() {
        return mOKPerformed;
    }

    public static void main(String[] args) throws SQLException, RemoteException {
        UserDialog diag = new UserDialog(new JFrame("Test"), "Add User");
        diag.setVisible(true);
        diag.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
