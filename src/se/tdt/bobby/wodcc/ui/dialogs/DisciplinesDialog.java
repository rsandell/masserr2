package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.Ability;
import se.tdt.bobby.wodcc.data.Clan;
import se.tdt.bobby.wodcc.data.Discipline;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.models.NecromancyPathsEditorTableModel;
import se.tdt.bobby.wodcc.ui.components.models.ThaumaturgyPathsEditorTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-25 16:31:53
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class DisciplinesDialog extends JDialog implements ActionListener {
    private RetrievalDB mRetrievalDB;
    private ManipulationDB mManipulationDB;
    private JList mDisciplinesList;
    private JComboBox mClansCombo;
    private JList mClanDisciplinesList;
    private static final boolean DEBUG = false;
    private DisciplineEditorDialog mDisciplinesEditor;
    private NecromancyPathsEditorTableModel mNecromancyPathsEditorTableModel;
    private ThaumaturgyPathsEditorTableModel mThaumaturgyPathsEditorTableModel;

    class DisciplineEditorDialog extends JDialog implements ActionListener {
        private JTextField mDisciplineNameField;
        private JComboBox mAbilitiesCombo;
        private Discipline mDiscipline;
        private boolean mOKPerformed = false;

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

        public DisciplineEditorDialog(Dialog owner) throws HeadlessException, SQLException, RemoteException {
            super(owner, "Discipline", true);
            mDisciplineNameField = new JTextField(15);
            Vector abilities = mRetrievalDB.getAbilities();
            abilities.add(0, new Ability(-1, "None Specific", 'N'));
            mAbilitiesCombo = new JComboBox(abilities);
            GridBagPanel panel = new GridBagPanel();
            panel.addLine("Name: ", mDisciplineNameField);
            panel.addLine("Retest: ", mAbilitiesCombo);
            getContentPane().add(panel, BorderLayout.CENTER);
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btn = new JButton("OK");
            btn.setActionCommand("OK");
            btn.addActionListener(this);
            getRootPane().setDefaultButton(btn);
            btnPanel.add(btn);
            btn = new JButton("Cancel");
            btn.setActionCommand("cancel");
            btn.addActionListener(this);
            btnPanel.add(btn);

            getContentPane().add(btnPanel, BorderLayout.SOUTH);
            pack();
            Utilities.positionMe(this);
        }

        public Discipline showNewDialog() {
            setDiscipline(new Discipline(-1, "", null));
            setTitle("New Discipline");
            mOKPerformed = false;
            setVisible(true);
            if (mOKPerformed) {
                return getDiscipline();
            }
            else {
                return null;
            }
        }

        public Discipline showEditDialog(Discipline pDiscipline) {
            setDiscipline(pDiscipline);
            setTitle("Edit Discipline");
            mOKPerformed = false;
            setVisible(true);
            if (mOKPerformed) {
                return getDiscipline();
            }
            else {
                return null;
            }
        }

        public String getName() {
            return mDisciplineNameField.getText();
        }

        public Ability getAbility() {
            Ability ability = (Ability) mAbilitiesCombo.getSelectedItem();
            if (ability.getId() > 0) {
                return ability;
            }
            else {
                return null;
            }
        }

        public Discipline getDiscipline() {
            mDiscipline.setName(mDisciplineNameField.getText());
            mDiscipline.setRetestAbility(getAbility());
            return mDiscipline;
        }

        public void setDiscipline(Discipline pDiscipline) {
            mDiscipline = pDiscipline;
            mDisciplineNameField.setText(mDiscipline.getName());
            ComboBoxModel model = mAbilitiesCombo.getModel();
            if (mDiscipline.getRetestAbility() == null) {
                mAbilitiesCombo.setSelectedIndex(0);
            }
            else {
                for (int i = 0; i < model.getSize(); i++) {
                    Ability ability = (Ability) model.getElementAt(i);
                    if (ability.getId() == mDiscipline.getRetestAbility().getId()) {
                        mAbilitiesCombo.setSelectedItem(ability);
                        break;
                    }
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("clanChanged")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        mClanDisciplinesList.setListData(mRetrievalDB.getClanDisciplines((Clan) mClansCombo.getSelectedItem()));
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("addToClan")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    Object[] selected = mDisciplinesList.getSelectedValues();
                    Discipline[] disciplines = new Discipline[selected.length];
                    for (int i = 0; i < selected.length; i++) {
                        disciplines[i] = (Discipline) selected[i];
                    }
                    try {
                        mManipulationDB.addClanDisciplines((Clan) mClansCombo.getSelectedItem(), disciplines);
                        mClanDisciplinesList.setListData(mRetrievalDB.getClanDisciplines((Clan) mClansCombo.getSelectedItem()));
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("removeFromClan")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    Object[] selected = mClanDisciplinesList.getSelectedValues();
                    Discipline[] disciplines = new Discipline[selected.length];
                    for (int i = 0; i < selected.length; i++) {
                        disciplines[i] = (Discipline) selected[i];
                    }
                    try {
                        mManipulationDB.removeClanDisciplines((Clan) mClansCombo.getSelectedItem(), disciplines);
                        mClanDisciplinesList.setListData(mRetrievalDB.getClanDisciplines((Clan) mClansCombo.getSelectedItem()));
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("edit")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    edit();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("New")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    newDiscipline();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("close")) {
            setVisible(false);
        }
    }

    private void newDiscipline() {

        Discipline discipline = mDisciplinesEditor.showNewDialog();
        if (discipline != null) {
            try {
                mManipulationDB.addDiscipline(discipline);
                mDisciplinesList.setListData(mRetrievalDB.getDisciplines());
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                report(e);
            }
        }
    }

    private void edit() {
        Discipline discipline = (Discipline) mDisciplinesList.getSelectedValue();
        discipline = mDisciplinesEditor.showEditDialog(discipline);
        if (discipline != null) {
            try {
                mManipulationDB.updateDiscipline(discipline);
                mDisciplinesList.setListData(mRetrievalDB.getDisciplines());
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                report(e);
            }
        }
    }

    private void report(Exception pE1) {
        JOptionPane.showMessageDialog(this, pE1.getMessage(), pE1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void refresh() {
        try {
            mDisciplinesList.setListData(mRetrievalDB.getDisciplines());
            mClansCombo.setModel(new DefaultComboBoxModel(mRetrievalDB.getClans()));
            mClanDisciplinesList.setListData(mRetrievalDB.getClanDisciplines((Clan) mClansCombo.getSelectedItem()));
            mThaumaturgyPathsEditorTableModel.refresh();
            mNecromancyPathsEditorTableModel.refresh();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
        if(DEBUG) System.out.println("DisciplinesDialog.refresh(272) ending");
    }

    public DisciplinesDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Disciplines");
        mRetrievalDB = Proxy.getRetrievalDB();
        mManipulationDB = Proxy.getManipulationDB();
        mDisciplinesEditor = new DisciplineEditorDialog(this);
        JTabbedPane tPane = new JTabbedPane();
        JPanel disciplinesContent = new JPanel(new BorderLayout());
        Vector<Discipline> disciplines = mRetrievalDB.getDisciplines();
        mDisciplinesList = new JList(disciplines);
        //mDisciplinesList.setPrototypeCellValue("Thaumaturgy Path");
        //mDisciplinesList.setVisibleRowCount(10);
        disciplinesContent.add(new JScrollPane(mDisciplinesList), BorderLayout.WEST);
        JPanel rightPanel = new JPanel(new BorderLayout());
        mClansCombo = new JComboBox(mRetrievalDB.getClans());
        mClansCombo.setActionCommand("clanChanged");
        mClansCombo.addActionListener(this);
        JPanel clanDisciplinesPanel = new JPanel(new BorderLayout());
        clanDisciplinesPanel.add(mClansCombo, BorderLayout.NORTH);
        mClanDisciplinesList = new JList(mRetrievalDB.getClanDisciplines((Clan) mClansCombo.getSelectedItem()));
        mClanDisciplinesList.setVisibleRowCount(5);
        clanDisciplinesPanel.add(new JScrollPane(mClanDisciplinesList));
        rightPanel.add(clanDisciplinesPanel, BorderLayout.CENTER);

        GridBagPanel btnPanel = new GridBagPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        JButton btn = new JButton(">");
        btn.setActionCommand("addToClan");
        btn.setToolTipText("Add Selected disciplines as Clan-Disciplines\nof the selected Clan");
        btn.addActionListener(this);
        btnPanel.addLine(btn);
        btn = new JButton("<");
        btn.setToolTipText("Remove the selected Clan-Disciplines");
        btn.setActionCommand("removeFromClan");
        btn.addActionListener(this);
        btnPanel.addLine(btn);
        btnPanel.addLine("  ");
        btn = new JButton("Edit");
        btn.setToolTipText("Edit the \"left-selected\" Discipline");
        btn.setActionCommand("edit");
        btn.addActionListener(this);
        btnPanel.addLine(btn);
        btn = new JButton("New");
        btn.setToolTipText("Create a new Discipline");
        btn.setActionCommand("New");
        btn.addActionListener(this);
        btnPanel.addLine(btn);
        rightPanel.add(btnPanel, BorderLayout.WEST);
        disciplinesContent.add(rightPanel, BorderLayout.CENTER);
        tPane.addTab("Disciplines", disciplinesContent);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn = new JButton("Close");
        btn.setActionCommand("close");
        btn.addActionListener(this);
        p.add(btn);
        getContentPane().add(p, BorderLayout.SOUTH);

        JPanel pathsPanel = new JPanel(new GridLayout(1, 2));
        mThaumaturgyPathsEditorTableModel = new ThaumaturgyPathsEditorTableModel(mRetrievalDB, mManipulationDB, tPane);
        JTable table = new JTable(mThaumaturgyPathsEditorTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thaumaturgy Paths"));
        pathsPanel.add(scrollPane);
        mNecromancyPathsEditorTableModel = new NecromancyPathsEditorTableModel(mRetrievalDB, mManipulationDB, tPane);
        table = new JTable(mNecromancyPathsEditorTableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Necromancy Paths"));
        pathsPanel.add(scrollPane);
        tPane.addTab("Paths", pathsPanel);

        getContentPane().add(tPane, BorderLayout.CENTER);
        pack();
        Utilities.positionMe(this);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                if(DEBUG) System.out.println("DisciplinesDialog.windowActivated(352) ");
                refresh();
            }
        });
    }
}
