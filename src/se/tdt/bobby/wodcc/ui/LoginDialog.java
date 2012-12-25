package se.tdt.bobby.wodcc.ui;

import se.tdt.bobby.wodcc.ui.components.GridBagPanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-23 16:11:43
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class LoginDialog extends JDialog implements ActionListener {
    private JTextField mUserNameField;
    private JPasswordField mPasswordField;
    private boolean mOKPerformed;
    private static final boolean DEBUG = false;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            mOKPerformed = true;
            setVisible(false);

        }
        else if (e.getActionCommand().equals("cancel")) {
            mOKPerformed = false;
            setVisible(false);
        }
    }
    public LoginDialog(Dialog pOwner) throws HeadlessException {
        super(pOwner, "Login", true);
        init();
    }

    public LoginDialog(Frame pOwner) throws HeadlessException {
        super(pOwner, "Login", true);
        init();
    }

    private void init() {

        mUserNameField = new JTextField(25);
        mPasswordField = new JPasswordField(25);
        GridBagPanel panel = new GridBagPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        panel.addLine("Username: ", mUserNameField);
        panel.addLine("Password: ", mPasswordField);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btn = new JButton("OK");
        btn.setActionCommand("OK");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getRootPane().setDefaultButton(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnPanel.add(btn);

        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        requestFocus();
        mUserNameField.requestFocus();
        Utilities.centerMeOnScreen(this);
    }

    public boolean isOKPerformed() {
        return mOKPerformed;
    }

    public String getUserName() {
        return mUserNameField.getText();
    }

    public String getPassword() {
        return new String(mPasswordField.getPassword());
    }
}
