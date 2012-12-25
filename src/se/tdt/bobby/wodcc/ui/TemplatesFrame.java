package se.tdt.bobby.wodcc.ui;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.data.Vitals;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.FileQuestionaire;
import se.tdt.bobby.wodcc.logic.OutputCreator;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.TemplateDB;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.view.RolesListCellRenderer;
import se.tdt.bobby.wodcc.ui.dialogs.TemplateToRoleDialog;
import se.tdt.bobby.wodcc.ui.guide.CreateRoleDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-03 19:30:55
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class TemplatesFrame extends JFrame {
    private JList mList;
    private TemplateDB mTemplateDB;
    private MutableAction mNewTemplateAction;
    private static final boolean DEBUG = false;
    protected static TemplatesFrame sTemplateFrame;
    private MutableAction mNewGhoulTemplateAction;
    private MutableAction mUpdateListAction;
    private MutableAction mEditTemplateAction;
    private OutputCreator mOutputCreator;
    private MutableAction mCreateRoleAction;
    private ManipulationDB mManipulationDB;
    private MutableAction mShowTemplatesAction;

    public TemplatesFrame() throws Exception, SQLException, RemoteException {
        super("Templates");
        sTemplateFrame = this;
        setIconImage(MainFrame.sMainFrame.getIconImage());
        mTemplateDB = Proxy.getTemplateDB();
        mManipulationDB = Proxy.getManipulationDB();
        mOutputCreator = OutputCreator.getInstance();
        Vector<Role> templates = mTemplateDB.getMinTemplateInfo();
        mList = new JList(templates);

        mList.setCellRenderer(new RolesListCellRenderer());
        mList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            int row = mList.locationToIndex(e.getPoint());
                            if (row >= 0) {
                                mList.addSelectionInterval(row, row);
                                Role role = (Role) mList.getModel().getElementAt(row);
                                viewTemplate(role.getId());
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            }
        });
        mList.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getModifiers() == KeyEvent.CTRL_MASK) {
                        showTemplates();
                    }
                    else {
                        Role role = (Role) mList.getSelectedValue();
                        viewTemplate(role.getId());
                    }
                }
            }
        });
        mList.setVisibleRowCount(-1);
        mList.setLayoutOrientation(JList.VERTICAL_WRAP);
        getContentPane().add(new JScrollPane(mList), BorderLayout.CENTER);
        setupActions();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Templates");
        menu.add(mNewTemplateAction);
        menu.add(mNewGhoulTemplateAction);
        menu.addSeparator();
        menu.add(mEditTemplateAction);
        menu.addSeparator();
        menu.add(mCreateRoleAction);
        menu.addSeparator();
        menu.add(mShowTemplatesAction);
        menu.add(mUpdateListAction);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setSize(800, 600);
        Utilities.positionMe(this, MainFrame.sMainFrame);
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                updateList();
            }
        });
    }

    private void setupActions() {
        mNewTemplateAction = new MutableAction("New Template...", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        JDialog dialog = CreateRoleDialog.getTemplateDialog(sTemplateFrame);
                        dialog.setVisible(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mNewTemplateAction.setToolTipText("Create a new Role Template");
        mNewGhoulTemplateAction = new MutableAction("New Ghoul Template...", KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        JDialog dialog = CreateRoleDialog.getTemplateGhoulDialog(sTemplateFrame);
                        dialog.setVisible(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mNewGhoulTemplateAction.setToolTipText("Create a new Ghoul Template");
        mUpdateListAction = new MutableAction("Refresh List", KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        updateList();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mEditTemplateAction = new MutableAction("Edit Template", KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                try {
                    Role role = (Role) mList.getSelectedValue();
                    if (role != null) {
                        Role roleToEdit = mTemplateDB.getTemplate(role.getId());
                        JDialog dialog = CreateRoleDialog.getEditTemplateDialog(sTemplateFrame, roleToEdit);
                        dialog.setVisible(true);
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        };
        mEditTemplateAction.setToolTipText("Edit the selected Template");
        mCreateRoleAction = new MutableAction("Make Role...", KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            Role selectedRole = (Role) mList.getSelectedValue();
                            if (selectedRole != null) {
                                TemplateToRoleDialog dialog = new TemplateToRoleDialog(sTemplateFrame, selectedRole.getName());
                                if (dialog.isOKPerformed()) {
                                    Role role = mTemplateDB.getTemplate(selectedRole.getId());
                                    role.setVitals(Vitals.NORMAL);
                                    role.setDomain(dialog.getDomain());
                                    role.setName(dialog.getRoleName());
                                    //role.setPlayerName(dialog.getPlayerName());
                                    role.setPlayer(dialog.getPlayer());
                                    role.setSire(new IntWithString(-1, ""));
                                    int id = mManipulationDB.addRole(role);
                                    if (id >= 0) {
                                        JOptionPane.showMessageDialog(sTemplateFrame, "The Role \"" + role.getName() + "\" has been created based on\n" +
                                                                                      "the template \"" + selectedRole.getName() + "\"", "Created", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(sTemplateFrame, "The Role \"" + role.getName() + "\" wasn't created\n" +
                                                                                      "fo unknown reason", "Not Created!", JOptionPane.ERROR_MESSAGE);
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
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowTemplatesAction = new MutableAction("Show", KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        showTemplates();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mCreateRoleAction.setToolTipText("Create a role based on the selected template.");
    }

    private void showTemplates() {
        try {
            Object[] objs = mList.getSelectedValues();
            ArrayList<Role> roles = new ArrayList<Role>(objs.length);
            for (int i = 0; i < objs.length; i++) {
                roles.add((Role) objs[i]);
            }
            File f = mOutputCreator.makeTemplates(roles);
            Utilities.startExplorer(f.getAbsolutePath());
            f.deleteOnExit();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private void updateList() {
        try {
            Vector<Role> templates = mTemplateDB.getMinTemplateInfo();
            mList.setListData(templates);
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void viewTemplate(int pTemplateId) {
        try {
            File f = mOutputCreator.makeSingleRole(mTemplateDB.getTemplate(pTemplateId));
            Process p = Utilities.startExplorer(f.getAbsolutePath());
            if (p != null) {
                FileQuestionaire quest = new FileQuestionaire(p, f, this);
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }
}
