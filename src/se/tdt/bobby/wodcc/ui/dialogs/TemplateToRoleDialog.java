package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.Player;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-04 00:00:29
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class TemplateToRoleDialog extends JDialog implements ActionListener {
    private JTextField mRoleNameField;
    //private JTextField mPlayerName;
    private RetrievalDB mRetrievalDB;
    private JComboBox mDomainsCombo;
    private boolean mOKPerformed;
    private String mRoleName;
    private JComboBox mPlayerCombo;
    private static final boolean DEBUG = false;

    public TemplateToRoleDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Create Role From Template", true);
        init();
    }

    public TemplateToRoleDialog(Dialog owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Create Role From Template", true);
        init();
    }

    public TemplateToRoleDialog(Frame owner, String pRoleName) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Create Role From Template", true);
        mRoleName = pRoleName;
        init();
    }

    public TemplateToRoleDialog(Dialog owner, String pRoleName) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Create Role From Template", true);
        mRoleName = pRoleName;
        init();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("ok")) {
            mOKPerformed = true;
            setVisible(false);
        }
        else if(e.getActionCommand().equals("cancel")) {
            mOKPerformed = false;
            setVisible(false);
        }
        else if(e.getActionCommand().equals("addPlayer")) {
            PlayerDialog playerDialog = new PlayerDialog(this);
            playerDialog.setVisible(true);
            try {
                if(playerDialog.isOKPerformed()) {
                    Vector players = mRetrievalDB.getPlayers();
                    players.add(0, new IntWithString(-1, "None"));
                    mPlayerCombo.setModel(new DefaultComboBoxModel(players));
                }
            }
            catch (Exception e1) {
                if (DEBUG) e1.printStackTrace();
                report(e1);
            }
        }
    }

    private void report(Exception pE1) {
        JOptionPane.showMessageDialog(this, pE1.getMessage(), pE1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void init() throws SQLException, RemoteException {
        mOKPerformed = false;
        mRetrievalDB = Proxy.getRetrievalDB();
        mRoleNameField = new JTextField(32);
        if(mRoleName != null) {
            mRoleNameField.setText(mRoleName);
        }
        //mPlayerName = new JTextField(32);
        Vector players = mRetrievalDB.getPlayers();
        players.add(0, new IntWithString(-1, "None"));
        mPlayerCombo = new JComboBox(players);
        JButton btn = new JButton("Add");
        btn.setMargin(new Insets(1, 1, 1, 1));
        btn.setActionCommand("addPlayer");
        btn.addActionListener(this);
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.add(mPlayerCombo, BorderLayout.CENTER);
        playerPanel.add(btn, BorderLayout.EAST);
        Vector domains = mRetrievalDB.getDomains();
        mDomainsCombo = new JComboBox(domains);
        Domain preferredDomain = AppPreferences.getPreferredDomain();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = (Domain) domains.elementAt(i);
            if(domain.equals(preferredDomain)) {
                mDomainsCombo.setSelectedItem(domain);
                break;
            }
        }
        GridBagPanel panel = new GridBagPanel();
        panel.addLine("Role Name: ", mRoleNameField);
        panel.addLine("Player: ", playerPanel);
        panel.addLine("Domain: ", mDomainsCombo);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn = new JButton("OK");
        btn.setActionCommand("ok");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getRootPane().setDefaultButton(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        pack();
        Utilities.positionMe(this);
        setVisible(true);
    }

    public boolean isOKPerformed() {
        return mOKPerformed;
    }

    public String getRoleName() {
        return mRoleNameField.getText();
    }

    /*public String getPlayerName() {
        return mPlayerName.getText();
    }*/

    public Player getPlayer() {
        if(mPlayerCombo.getSelectedItem() instanceof IntWithString) {
            return null;
        }
        else {
            return (Player) mPlayerCombo.getSelectedItem();
        }
    }

    public Domain getDomain() {
        return (Domain) mDomainsCombo.getSelectedItem();
    }
}
