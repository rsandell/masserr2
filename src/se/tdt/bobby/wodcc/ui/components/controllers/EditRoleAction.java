package se.tdt.bobby.wodcc.ui.components.controllers;

import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.models.RoleNamesListModel;
import se.tdt.bobby.wodcc.ui.guide.CreateRoleDialog;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 * Description.
 * <p/>
 * Created: 2004-jun-30 22:52:42
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class EditRoleAction extends MutableAction {

    private JList mRolesList;
    private JFrame mParentFrame;
    private RetrievalDB mDB;
    private static final boolean DEBUG = false;

    public EditRoleAction(JList pRolesList, JFrame pParentFrame) throws SQLException, RemoteException {
        super("Edit Role...", KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK),
                                            IconFactory.getIconFactory().getIcon("Edit24.gif"));
        mRolesList = pRolesList;
        mParentFrame = pParentFrame;
        mDB = Proxy.getRetrievalDB();
        setToolTipText("Edit the first selected role.");
    }

    public void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
                    public void run() {
                        setEnabled(false);
                        Object o = mRolesList.getSelectedValue();
                        if (o != null && o instanceof Role) {
                            int id = ((Role) o).getId();
                            try {
                                Role role = mDB.getRole(id);
                                CreateRoleDialog dialog = CreateRoleDialog.getEditDialog(mParentFrame, role);
                                dialog.setVisible(true);
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                        setEnabled(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
    }

    private void report(Exception pE1) {
        JOptionPane.showMessageDialog(mParentFrame, pE1.getMessage(), pE1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }
}
