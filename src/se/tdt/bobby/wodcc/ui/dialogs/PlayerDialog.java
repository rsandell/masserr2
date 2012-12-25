package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.Player;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.view.PlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description.
 * <p/>
 * Created: 2004-jun-29 22:20:39
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class PlayerDialog extends JDialog implements ActionListener {
    private Player mEditPlayer;
    private boolean mOKPerformed;
    private PlayerPanel mPlayerPanel;
    private static final boolean DEBUG = false;

    public PlayerDialog(Frame owner) throws HeadlessException {
        super(owner, "", true);
        init();
    }

    public PlayerDialog(Dialog owner) throws HeadlessException {
        super(owner, "", true);
        init();
    }

    public PlayerDialog(Frame owner, Player pEditPlayer) throws HeadlessException {
        super(owner, "", true);
        mEditPlayer = pEditPlayer;
        init();
    }

    public PlayerDialog(Dialog owner, Player pEditPlayer) throws HeadlessException {
        super(owner, "", true);
        mEditPlayer = pEditPlayer;
        init();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            try {
                Player player = mPlayerPanel.getNewPlayer();
                ManipulationDB db = Proxy.getManipulationDB();
                if (player.getId() > 0) {
                    db.updatePlayer(player);
                }
                else {
                    db.addPlayer(player);
                }
                mOKPerformed = true;
                setVisible(false);
            }
            catch (Exception e1) {
                if (DEBUG) e1.printStackTrace();
                report(e1);
            }
        }
        else if (e.getActionCommand().equals("cancel")) {
            mOKPerformed = false;
            setVisible(false);
        }
    }

    public boolean isOKPerformed() {
        return mOKPerformed;
    }

    private void report(Exception pE1) {
        JOptionPane.showMessageDialog(this, pE1.getMessage(), pE1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void init() {
        mOKPerformed = false;
        if (mEditPlayer != null) {
            setTitle("Edit Player");
        }
        else {
            setTitle("Add Player");
        }
        mPlayerPanel = new PlayerPanel(mEditPlayer, this);
        getContentPane().add(mPlayerPanel, BorderLayout.CENTER);
        pack();
        Utilities.positionMe(this);
    }
}
