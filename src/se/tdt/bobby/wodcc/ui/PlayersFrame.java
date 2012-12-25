package se.tdt.bobby.wodcc.ui;

import se.tdt.bobby.wodcc.data.Experience;
import se.tdt.bobby.wodcc.data.Player;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.logic.OutputCreator;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.models.ExperienceListTableModel;
import se.tdt.bobby.wodcc.ui.components.controllers.EditRoleAction;
import se.tdt.bobby.wodcc.ui.components.view.PlayerPanel;
import se.tdt.bobby.wodcc.ui.components.view.RolesListCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.ShowPage2Panel;
import se.tdt.bobby.wodcc.ui.dialogs.PlayerDialog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
 * Created: 2004-jun-30 18:24:30
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class PlayersFrame extends JFrame implements ListSelectionListener, ActionListener {
    private RetrievalDB mRetrievalDB;
    private JList mPlayersList;
    private PlayerPanel mEditPlayerPanel;
    private JList mPlayingRolesList;
    private JLabel mXPLabel;
    private Player mSelectedPlayer;
    private static final boolean DEBUG = false;
    private EditRoleAction mEditRoleAction;
    private MutableAction mShowRolesAction;
    private MutableAction mShowRoles2Action;
    private ShowPage2Panel mShowPage2Panel;
    private OutputCreator mOutputCreator;
    private MutableAction mNewPlayerAction;
    private static PlayersFrame sPlayersFrame;
    private MutableAction mAddXPAction;
    private ManipulationDB mManipulationDB;

    public PlayersFrame() throws Exception, SQLException, RemoteException {
        super("Players");
        sPlayersFrame = this;
        if (MainFrame.sMainFrame != null) {
            setIconImage(MainFrame.sMainFrame.getIconImage());
        }
        mRetrievalDB = Proxy.getRetrievalDB();
        mManipulationDB = Proxy.getManipulationDB();
        Vector players = mRetrievalDB.getPlayers();
        JPanel playerInfoPanel = new JPanel(new BorderLayout(2, 2));
        mPlayersList = new JList(players);
        //mPlayersList.setPrototypeCellValue("Robert \"Bobby\" Sandell");
        mPlayersList.addListSelectionListener(this);
        getContentPane().add(new JScrollPane(mPlayersList), BorderLayout.WEST);
        mEditPlayerPanel = new PlayerPanel(this);
        playerInfoPanel.add(mEditPlayerPanel, BorderLayout.CENTER);
        mPlayingRolesList = new JList();
        mPlayingRolesList.setVisibleRowCount(1);
        mPlayingRolesList.setLayoutOrientation(JList.VERTICAL_WRAP);
        mPlayingRolesList.setCellRenderer(new RolesListCellRenderer());
        mPlayingRolesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            int row = mPlayingRolesList.locationToIndex(e.getPoint());
                            if (row >= 0) {
                                mPlayingRolesList.setSelectedIndex(row);
                                showRoles(false);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            }
        });
        mPlayingRolesList.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getModifiers() == KeyEvent.CTRL_MASK) {
                        showRoles(false);
                    }
                    else {
                        Role role = (Role) mPlayingRolesList.getSelectedValue();
                        mPlayingRolesList.setSelectedValue(role, true);
                        showRoles(false);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(mPlayingRolesList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Playing Roles"));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        playerInfoPanel.add(scrollPane, BorderLayout.NORTH);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mXPLabel = new JLabel("XP: ");
        panel.add(mXPLabel);
        JButton btn = new JButton("Add");
        btn.setActionCommand("addXP");
        btn.addActionListener(this);
        panel.add(btn);
        btn = new JButton("List");
        btn.setActionCommand("listXP");
        btn.addActionListener(this);
        panel.add(btn);
        playerInfoPanel.add(panel, BorderLayout.SOUTH);
        getContentPane().add(playerInfoPanel, BorderLayout.CENTER);
        if (players.size() > 0) {
            mPlayersList.setSelectedIndex(0);
        }
        mOutputCreator = OutputCreator.getInstance();
        setupActions();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Players");
        menu.add(mNewPlayerAction);
        menu.add(mAddXPAction);
        menuBar.add(menu);
        menu = new JMenu("Roles");
        menu.add(mShowRolesAction);
        menu.add(mShowRoles2Action);
        menu.addSeparator();
        menu.add(mEditRoleAction);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        /*Dimension dimension = scrollPane.getPreferredSize();
        dimension.height = dimension.height + 15;
        scrollPane.setPreferredSize(dimension);*/
        pack();
        setVisible(true);
    }

    private void setupActions() throws SQLException, RemoteException {
        mEditRoleAction = new EditRoleAction(mPlayingRolesList, this);
        mShowRolesAction = new MutableAction("Show", KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK),
                                             IconFactory.getIconFactory().getIcon("PrintPreview24.gif")) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        showRoles(false);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowRolesAction.setToolTipText("Show the character sheets for the selected roles.");
        mShowRoles2Action = new MutableAction("Show 1-2",
                                              KeyStroke.getKeyStroke(KeyEvent.VK_O,
                                                                     KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK),
                                              IconFactory.getIconFactory().getIcon("PrintPreview224.gif")) {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        showRoles(true);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        mShowRoles2Action.setToolTipText("Show the character sheets and the \"Second Page\" for the selected roles.");
        mNewPlayerAction = new MutableAction("New Player...", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK)) {
            public void actionPerformed(ActionEvent e) {
                PlayerDialog dialog = new PlayerDialog(sPlayersFrame);
                dialog.setVisible(true);
                if (dialog.isOKPerformed()) {
                    updateList();
                }
            }
        };
        mNewPlayerAction.setToolTipText("Add a New Player...");
        mAddXPAction = new MutableAction("Add XP") {
            public void actionPerformed(ActionEvent e) {
                addXP();
            }
        };
    }

    private void addXP() {
        Object[] players = mPlayersList.getSelectedValues();
        java.util.List<Player> plList = new ArrayList<Player>(players.length);
        for (int i = 0; i < players.length; i++) {
            Object player = players[i];
            if (player instanceof Player) {
                plList.add((Player) player);
            }
        }
        JTextField text = new JTextField(25);
        JSpinner ammount = Utilities.createIntegerJSpinner(-17, 17, 1, 1);
        GridBagPanel panel = new GridBagPanel();
        panel.addLine("Ammount: ", ammount);
        panel.addLine("Message: ", text);
        int choice = JOptionPane.showConfirmDialog(this, panel, "Insert XP", JOptionPane.OK_CANCEL_OPTION);
        if (choice == JOptionPane.OK_OPTION) {
            try {
                ManipulationDB manipulationDB = Proxy.getManipulationDB();
                manipulationDB.insertPlayersExperience(plList, Utilities.getSpinnerInteger(ammount), text.getText());
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                report(e);
            }
        }
    }

    private void updateList() {
        try {
            Vector players = mRetrievalDB.getPlayers();
            mPlayersList.setListData(players);
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            Player toUpdate = mEditPlayerPanel.getNewPlayer();
            try {
                mManipulationDB.updatePlayer(toUpdate);
                mSelectedPlayer = mRetrievalDB.getPlayer(toUpdate.getId());
                fillInfo();
            }
            catch (Exception e1) {
                if (DEBUG) e1.printStackTrace();
                report(e1);
            }
        }
        else if (e.getActionCommand().equals("cancel")) {
            mEditPlayerPanel.setPlayer(mSelectedPlayer);
        }
        else if (e.getActionCommand().equals("addXP")) {
            addXP();
        }
        else if (e.getActionCommand().equals("listXP")) {
            String xp = "";
            JTable table = new JTable(new ExperienceListTableModel(mSelectedPlayer.getExperienceList()));

            JOptionPane.showMessageDialog(this, new JScrollPane(table), "XP List For " + mSelectedPlayer.getName(), JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        Player player = (Player) mPlayersList.getSelectedValue();
        if (player != null) {
            try {
                mSelectedPlayer = mRetrievalDB.getPlayer(player.getId());
                fillInfo();
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

    private void fillInfo() {
        mEditPlayerPanel.setPlayer(mSelectedPlayer);
        mXPLabel.setText("XP: " + mSelectedPlayer.getXP());
        mPlayingRolesList.setListData(mSelectedPlayer.getRoles());
    }


    private void showRoles(boolean pShowExtra) {
        if (mShowPage2Panel == null) {
            mShowPage2Panel = new ShowPage2Panel();
        }
        Object[] objs = mPlayingRolesList.getSelectedValues();
        ArrayList<Role> roles = new ArrayList<Role>(objs.length);
        for (int i = 0; i < objs.length; i++) {
            roles.add((Role) objs[i]);
        }
        try {
            if (pShowExtra) {
                int choise = JOptionPane.showConfirmDialog(this, mShowPage2Panel, "Show 1-2", JOptionPane.OK_CANCEL_OPTION);
                if (choise == JOptionPane.OK_OPTION) {
                    AppPreferences.setViewPage2(mShowPage2Panel.isViewPage2());
                    AppPreferences.setListPlots(mShowPage2Panel.isListPlots());
                    AppPreferences.setListExperience(mShowPage2Panel.isListExperience());
                    File f = mOutputCreator.makeRoles(roles, mShowPage2Panel.isViewPage2(), mShowPage2Panel.isListPlots(), mShowPage2Panel.isListExperience());
                    Utilities.startExplorer(f.getAbsolutePath());
                    f.deleteOnExit();
                }
                else {
                    return;
                }
            }
            else {
                File f = mOutputCreator.makeRoles(roles, false, false, false);
                Utilities.startExplorer(f.getAbsolutePath());

                f.deleteOnExit();
            }
        }
        catch (Exception e1) {
            if (DEBUG) e1.printStackTrace();
            report(e1);
        }
    }
}
