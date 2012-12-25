package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.Ritual;
import se.tdt.bobby.wodcc.data.RitualType;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.db.Proxy;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-20 23:05:05
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class RitualsDialog extends JDialog implements ActionListener, ListSelectionListener {

    private JList mRitualsList;
    private static final boolean DEBUG = false;
    private JTextField mNameField;
    //private JComboBox mLevelCombo;
    //private IntWithString[] mLevels;
    private JTextArea mDescriptionArea;
    private Ritual mSelectedRitual;
    protected RetrievalDB mDB;
    private JSpinner mLevel;
    private JComboBox mTypesCombo;
    private ManipulationDB mManipulationDB;
    private JComboBox mFilterRitualTypesCombo;

    public void valueChanged(ListSelectionEvent e) {
        Object o = mRitualsList.getSelectedValue();
        if (o != null) {
            mSelectedRitual = (Ritual) o;
            fillRitual();
        }
    }

    private void fillRitual() {
        mNameField.setText(mSelectedRitual.getName());
        mLevel.setValue(new Integer(mSelectedRitual.getLevel()));
        mDescriptionArea.setText(mSelectedRitual.getDescription());
        if (mSelectedRitual.getRitualType() != null) {
            for (int i = 0; i < mTypesCombo.getModel().getSize(); i++) {
                RitualType type = (RitualType) mTypesCombo.getModel().getElementAt(i);
                if (type.getId() == mSelectedRitual.getRitualType().getId()) {
                    mTypesCombo.setSelectedItem(type);
                    break;
                }
            }
        }
        else {
            mTypesCombo.setSelectedIndex(0);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("new")) {
            mRitualsList.setSelectedValue(null, false);
            mSelectedRitual = new Ritual(-1, "", 1, (RitualType) mFilterRitualTypesCombo.getSelectedItem(), "");
            fillRitual();
        }
        else if (e.getActionCommand().equals("save")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mSelectedRitual != null) {
                        mSelectedRitual.setName(mNameField.getText());
                        int level = Utilities.getSpinnerInteger(mLevel);
                        mSelectedRitual.setLevel(level);
                        mSelectedRitual.setDescription(mDescriptionArea.getText());
                        mSelectedRitual.setRitualType((RitualType) mTypesCombo.getSelectedItem());
                        try {
                            if (mSelectedRitual.getId() >= 0) {
                                updateRitual(mSelectedRitual);
                            }
                            else {
                                addRitual(mSelectedRitual);
                            }
                            Vector rituals = mDB.getRituals((RitualType) mFilterRitualTypesCombo.getSelectedItem());
                            Ritual tmpRitual = mSelectedRitual;
                            mRitualsList.setListData(rituals);
                            for (int i = 0; i < rituals.size(); i++) {
                                Ritual ritual = (Ritual) rituals.elementAt(i);
                                if(ritual.getName().equals(tmpRitual.getName())) {
                                    mRitualsList.setSelectedValue(ritual, true);
                                    break;
                                }
                            }
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("typeChanged")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Vector rituals = mDB.getRituals((RitualType) mFilterRitualTypesCombo.getSelectedItem());
                        mRitualsList.setListData(rituals);
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

    //protected abstract Vector getRituals() throws SQLException, RemoteException;
    protected void addRitual(Ritual pRitual) throws SQLException, RemoteException {
        mManipulationDB.addRitual(pRitual);
    }

    protected void updateRitual(Ritual pRitual) throws SQLException, RemoteException {
        mManipulationDB.updateRitual(pRitual);
    }

    public RitualsDialog(Frame owner) throws HeadlessException {
        super(owner, "Rituals");
        init();
    }

    public RitualsDialog(Dialog owner) throws HeadlessException {
        super(owner, "Rituals");
        init();
    }

    protected void init() {
        try {
            mDB = Proxy.getRetrievalDB();
            mManipulationDB = Proxy.getManipulationDB();
            Vector<RitualType> ritualTypes = mDB.getRitualTypes();
            mFilterRitualTypesCombo = new JComboBox(ritualTypes);
            mFilterRitualTypesCombo.setActionCommand("typeChanged");
            mFilterRitualTypesCombo.addActionListener(this);
            JPanel ritualsListPanel = new JPanel(new BorderLayout());
            ritualsListPanel.add(mFilterRitualTypesCombo, BorderLayout.NORTH);
            Vector rituals = mDB.getRituals((RitualType) mFilterRitualTypesCombo.getSelectedItem());
            mRitualsList = new JList(rituals);
            mRitualsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            mRitualsList.addListSelectionListener(this);
            ritualsListPanel.add(new JScrollPane(mRitualsList), BorderLayout.CENTER);
            getContentPane().add(ritualsListPanel,BorderLayout.WEST);

            GridBagPanel panel = new GridBagPanel();
            panel.setIpadx(2);
            panel.setIpady(2);
            mNameField = new JTextField(25);
            panel.addLine(new JLabel("Name: "), mNameField);
            //Vector<RitualType> types = mDB.getRitualTypes();
            mTypesCombo = new JComboBox(ritualTypes);
            panel.addLine("Type: ", mTypesCombo);
            panel.add(new JLabel("Level: "));

            panel.setAnchor(GridBagConstraints.NORTHWEST);
            //mLevelCombo = new JComboBox(Ritual.LEVEL_NAMES);
            mLevel = Utilities.createIntegerJSpinner(1, 9, 1, 1);
            panel.addLine(mLevel, new JLabel("     "));
            panel.add(new JLabel("Description: "));
            mDescriptionArea = new JTextArea(4, 25);
            mDescriptionArea.setWrapStyleWord(true);
            mDescriptionArea.setLineWrap(true);
            panel.addLine(new JScrollPane(mDescriptionArea));
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
            pack();
            Utilities.positionMe(this);
            setVisible(true);
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    protected void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }
}
