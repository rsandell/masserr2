package se.tdt.bobby.wodcc.ui.guide;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.CreateRules;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.proxy.interfaces.TemplateDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.ui.components.controllers.SpinnerTableCellEditor;
import se.tdt.bobby.wodcc.ui.components.models.*;
import se.tdt.bobby.wodcc.ui.components.view.MeritsNflawsTableCellRenderer;
import se.tdt.bobby.wodcc.ui.components.view.SpringUtilities;
import se.tdt.bobby.wodcc.ui.dialogs.PlayerDialog;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-10 00:56:01
 *
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class CreateRoleDialog extends JDialog implements ActionListener {
    private static int mStdTextFieldLength = 20;
    private JTextField mRoleName;
    //private JTextField mPlayerName;
    private JComboBox mGenerationsCombo;
    private RetrievalDB mDB;
    private static final boolean DEBUG = false;
    private JTextField mEmbraced;
    private JComboBox mClansCombo;
    private JTextField mNature;
    private JTextField mDemeanor;
    private JSpinner mCourage;
    private JSpinner mConsciense;
    private JSpinner mSelfControl;
    private JSpinner mWillPower;
    private JTextField mPathName;
    private JSpinner mPathDots;
    private EditAbilitiesTableModel mPhysicalAbilities;
    private SpinnerTableCellEditor mCellEditor;
    private EditAbilitiesTableModel mSocialAbilities;
    private EditAbilitiesTableModel mMentalAbilities;
    private CardLayout mLayout;
    private int mCurrentPage = 1;
    private int mMaxPage = 4;
    private JPanel mContentPanel;
    private JButton mPrevButton;
    private JButton mNextButton;
    private JComboBox mSireCombo;
    private JSpinner mPhysical;
    private JSpinner mSocial;
    private JSpinner mMental;
    private DisciplinesTableModel mDisciplinesTableModel;
    private SpinnerTableCellEditor mDisciplinesEditor;
    private PathsTableModel mThamaPathsTableModel;
    private PathsTableModel mNecromancyPathsTableModel;
//    private RitualsTableModel mThamaRitualsModel;
//    private RitualsTableModel mNecromancyRitualsModel;
    private JTextArea mAttributeNotes;
    private MeritsNflawsTableModel mMeritsTableModel;
    private MeritsNflawsTableModel mFlawsTableModel;
    private DefaultTableModel mDerangementsTableModel;
    private OtherTraitsTableModel mOtherTraitsTableModel;
    private JTextField mMeritsSum;
    private JCheckBox mSuffersOfInjury;
    private JSpinner mExtraHealthLevels;
    private Role mRole = null;
    private boolean mFinished = false;
    private CreateRules mRulesDB;
    private CreateRule mCreateRule;
    private JTextField mDisciplinesStats;
    private JTextField mAttributesStats;
    private JTextField mAbilitiesStats;
//    private JTextField mBaseMoney;
//    private JTextField mBaseMonthlyIncome;
//    private JTextField mExtraBaseMoney;
//    private JTextField mExtraMonthlyIncome;
//    private ProfessionsTableModel mProfessionsTableModel;
//    private ProfessionsTableModel mMortalProfessionsTableModel;
    private JComboBox mVitalsCombo;
    private JComboBox mDomainsCombo;
    private ManipulationDB mManipulationDB;
    private RitualsTableModel mRitualsModel;
    private DefaultCellEditor mRitualsEditor;
    private JComboBox mRitualsCombo;
    private JComboBox mConcienseORconvictionCombo;
    private JComboBox mSelfControlORinstinctCombo;
    private JTextField mThaumaTypeFiled;
    private JTextField mNecromancyTypeField;
    private JTextField mXP;
    private boolean mCreateTemplate = false;
    private JTextArea mQuoteArea;
    private JCheckBox mSLPBox;
    private DefaultTableModel mBeastTraitsTableModel;
    private JComboBox mFightCombo;
    private JComboBox mFlightCombo;
    private Vector<FightOrFlight> mFightOrFlights;
    private JComboBox mPlayerCombo;
    private Vector mPlayers;
    private JButton mAddPlayerBtn;

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("prev")) {
            mCurrentPage--;
            mLayout.show(mContentPanel, mCurrentPage + "");
            if (mCurrentPage == 1) {
                mPrevButton.setEnabled(false);
                mNextButton.setEnabled(true);
            }
            else {
                mPrevButton.setEnabled(true);
                if (mCurrentPage != mMaxPage) {
                    mNextButton.setEnabled(true);
                }
            }
        }
        else if (e.getActionCommand().equals("next")) {
            mCurrentPage++;
            mLayout.show(mContentPanel, mCurrentPage + "");
            if (mCurrentPage == mMaxPage) {
                mPrevButton.setEnabled(true);
                mNextButton.setEnabled(false);
            }
            else {
                mNextButton.setEnabled(true);
                if (mCurrentPage != 1) {
                    mPrevButton.setEnabled(true);
                }
            }
        }
        else if (e.getActionCommand().equals("cancel")) {
            mFinished = false;
            setVisible(false);
        }
        else if (e.getActionCommand().equals("finish")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    Role role = compileToRole();
                    if (role != null) {
                        //report("Finish", "Role Compiled: " + role);
                        try {
                            saveToFile(role);
                            if (mCreateTemplate) {
                                TemplateDB db = Proxy.getTemplateDB();
                                if (mRole != null) {
                                    role.setGhoul(mRole.isGhoul());
                                    if (mRole.getId() >= 0) {
                                        role.setId(mRole.getId());
                                        db.updateTemplate(role);
                                    }
                                    else {
                                        db.addTemplate(role);
                                    }
                                }
                                else {
                                    db.addTemplate(role);
                                }
                            }
                            else {
                                if (mRole != null) {
                                    role.setGhoul(mRole.isGhoul());
                                    if (mRole.getId() >= 0) {
                                        role.setId(mRole.getId());
                                        mManipulationDB.updateRole(role);
                                    }
                                    else {
                                        mManipulationDB.addRole(role);
                                    }
                                }
                                else {
                                    mManipulationDB.addRole(role);
                                }
                            }
                            mFinished = true;
                            setVisible(false);
                        }
                        catch (sun.jdbc.odbc.JdbcOdbcBatchUpdateException buEx) {
                            if (DEBUG) buEx.printStackTrace();
                            int[] rows = buEx.getUpdateCounts();
                            for (int i = 0; i < rows.length; i++) {
                                int row = rows[i];
                                if (row == Statement.EXECUTE_FAILED) {
                                    if (DEBUG) System.out.println("[CreateRoleDialog][run][131] failed at batch-index " + i);
                                }
                                if (DEBUG) System.out.println("[CreateRoleDialog][run][133] row: " + i + " = " + row);
                            }
                            if (DEBUG) System.out.println("[CreateRoleDialog][run][135] sqlState: " + buEx.getSQLState());
                            report(buEx);
                        }
                        catch (SQLException e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                        catch (Exception ex1) {
                            if (DEBUG) ex1.printStackTrace();
                            report(ex1);
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
            //System.exit(0);
        }
        else if (e.getActionCommand().equals("generationChanged")) {
            generationChanged();
        }
        else if (e.getActionCommand().equals("clanChanged")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    clanChanged();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("addPlayer")) {
            PlayerDialog playerDialog = new PlayerDialog(this);
            playerDialog.setVisible(true);
            try {
                if (playerDialog.isOKPerformed()) {
                    Vector players = mDB.getPlayers();
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

    private void generationChanged() {
        if (DEBUG) System.out.println("CreateRoleDialog.generationChanged(240) TOP");
        Generation gen = (Generation) mGenerationsCombo.getSelectedItem();
        Utilities.changeSpinnerAttributes(mWillPower, 1, gen.getWillpowerMax(), 1);
        Utilities.changeSpinnerAttributes(mPhysical, 0, gen.getTraitsMax(), 1);
        Utilities.changeSpinnerAttributes(mSocial, 0, gen.getTraitsMax(), 1);
        Utilities.changeSpinnerAttributes(mMental, 0, gen.getTraitsMax(), 1);
        if (DEBUG) System.out.println("CreateRoleDialog.generationChanged(246) setting willpower: " + gen.getWillpowerStart());
        int willpower = Utilities.getSpinnerInteger(mWillPower);
        if (willpower < gen.getWillpowerStart()) {
            mWillPower.setValue(new Integer(gen.getWillpowerStart()));
        }
        mCellEditor.setSpinnerAttributes(0, gen.getAbilitiesMax(), 1);
        mDisciplinesEditor.setSpinnerAttributes(0, gen.getDisciplinesMax(), 1);
        updateSireCombo();
    }

    private void clanChanged() {
        if (mRole == null) {
            try {
                List<Discipline> clanDisciplines = mDB.getClanDisciplines((Clan) mClansCombo.getSelectedItem());
                mDisciplinesTableModel.replaceClanDisciplines(clanDisciplines);
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                report(e);
            }
        }
        updateSireCombo();
    }

    private void updateSireCombo() {
        IntWithString selectedSire = (IntWithString) mSireCombo.getSelectedItem();
        if (selectedSire == null) {
            selectedSire = new IntWithString(-1, "None");
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) mSireCombo.getModel();
        model.removeAllElements();

        if (selectedSire.getNumber() == -1) {
            IntWithString none = new IntWithString(-1, "None");
            model.addElement(none);
            model.setSelectedItem(none);
        }
        else {
            model.addElement(new IntWithString(-1, "None"));
        }
        try {
            Vector v = mDB.getRoleNames(getSelectedGeneration(), getSelectedClan());
            for (int i = 0; i < v.size(); i++) {
                IntWithString intWithString = (IntWithString) v.elementAt(i);
                model.addElement(intWithString);
                if (intWithString.getNumber() == selectedSire.getNumber()) {
                    model.setSelectedItem(intWithString);
                }
            }
            IntWithString intr = (IntWithString) model.getSelectedItem();
            if (intr == null || intr.getNumber() != selectedSire.getNumber()) {
                model.addElement(selectedSire);
                model.setSelectedItem(selectedSire);
            }
        }
        catch (Exception e1) {
            if (DEBUG) e1.printStackTrace();
            report(e1);
        }
        mSireCombo.setModel(model);
    }

    private static Role loadFromFile() {
        Role r = null;
        try {
            XMLDecoder in = new XMLDecoder(new FileInputStream("latestRole.xml"));
            r = (Role) in.readObject();
        }
        catch (FileNotFoundException e) {
            if (DEBUG) e.printStackTrace();
        }
        return r;
    }

    private void saveToFile(Role pRole) {
        try {
            XMLEncoder out = new XMLEncoder(new FileOutputStream("latestRole.xml"));
            out.writeObject(pRole);
            out.close();
        }
        catch (FileNotFoundException e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private int getSelectedGeneration() {
        Generation gen = (Generation) mGenerationsCombo.getSelectedItem();
        return gen.getGeneration();
    }

    private int getSelectedClan() {
        Clan c = (Clan) mClansCombo.getSelectedItem();
        return c.getId();
    }


    public CreateRoleDialog(Frame owner) throws HeadlessException {
        super(owner, "New Role", false);
        try {
            init();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private CreateRoleDialog(Frame owner, Role pFillWithRole) throws HeadlessException {
        super(owner, "New Role", false);
        mRole = pFillWithRole;
        try {
            init();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    public CreateRoleDialog(Dialog owner) throws HeadlessException {
        super(owner, "New Role", false);
        try {
            init();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    private CreateRoleDialog(Dialog owner, String pTitle, boolean pModal, Role pInitRole) throws HeadlessException {
        super(owner, pTitle, pModal);
        mRole = pInitRole;
        try {
            init();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }

    }

    private CreateRoleDialog(Frame owner, String pTitle, boolean pModal, Role pInitRole) throws HeadlessException {
        super(owner, pTitle, pModal);
        mRole = pInitRole;
        try {
            init();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }

    }

    private CreateRoleDialog(Frame owner, String pTitle, boolean pModal, boolean pCreateTemplate) throws HeadlessException {
        super(owner, pTitle, pModal);
        mCreateTemplate = pCreateTemplate;
        try {
            init();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }

    }

    private CreateRoleDialog(Frame owner, String pTitle, boolean pModal, Role pInitRole, boolean pCreateTemplate) throws HeadlessException {
        super(owner, pTitle, pModal);
        mRole = pInitRole;
        mCreateTemplate = pCreateTemplate;
        try {
            init();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }

    }

    public static CreateRoleDialog getEditDialog(Frame pOwner, Role pRoleToEdit) {
        return new CreateRoleDialog(pOwner, "Edit Role", false, pRoleToEdit);
    }

    public static CreateRoleDialog getEditTemplateDialog(Frame pOwner, Role pRoleToEdit) {
        return new CreateRoleDialog(pOwner, "Edit Template", false, pRoleToEdit, true);
    }

    public static CreateRoleDialog getEditDialog(Dialog pOwner, Role pRoleToEdit) {
        return new CreateRoleDialog(pOwner, "Edit Role", false, pRoleToEdit);
    }

    public static CreateRoleDialog getGhoulDialog(Frame pOwner) {
        Role r = new Role(-1, "", "", null, new IntWithString(-1, "None"), new Date(), new Clan(-1, ""), "", "", 1, 1, 1, 4, 1, "", 1, 1, 1, 0, true, true);
        r.setDomain(AppPreferences.getPreferredDomain());
        //r.setGhoul(true);
        return new CreateRoleDialog(pOwner, "Create Ghoul", false, r);
    }

    public static CreateRoleDialog getTemplateDialog(Frame pOwner) {
        //Role r = new Role(-1, "", "", null, new IntWithString(-1, "None"), new Date(), new Clan(-1, ""), "", "", 1, 1, 1, 4, 1, "", 1, 1, 1, 0, true, true);
        //r.setDomain(AppPreferences.getPreferredDomain());
        //r.setGhoul(true);
        return new CreateRoleDialog(pOwner, "Create Template", false, true);
    }

    public static CreateRoleDialog getTemplateGhoulDialog(Frame pOwner) {
        Role r = new Role(-1, "", "", null, new IntWithString(-1, "None"), new Date(), new Clan(-1, ""), "", "", 1, 1, 1, 4, 1, "", 1, 1, 1, 0, true, true);
        r.setDomain(AppPreferences.getPreferredDomain());
        //r.setGhoul(true);
        return new CreateRoleDialog(pOwner, "Create Template Ghoul", false, r, true);
    }

    public static CreateRoleDialog getGhoulDialog(Dialog pOwner) {
        Role r = new Role(-1, "", "", null, new IntWithString(-1, "None"), new Date(), new Clan(-1, ""), "", "", 1, 1, 1, 4, 1, "", 1, 1, 1, 0, true, true);
        r.setDomain(AppPreferences.getPreferredDomain());
        //r.setGhoul(true);
        return new CreateRoleDialog(pOwner, "Create Ghoul", false, r);
    }

    private void init() throws SQLException, IOException {
        mDB = Proxy.getRetrievalDB();
        mManipulationDB = Proxy.getManipulationDB();
        mRulesDB = Proxy.getCreateRules();
        getContentPane().setLayout(new BorderLayout(2, 2));
        mFinished = false;
        JPanel firstPanel = createGeneralPanel();
        JPanel abilitiesPanel = createAbilitiesPanel();
        JPanel disciplinesPanel = createExtraDisciplinesPanel();
        JPanel meritsPanel = createMeritsNflawsPanel();
        if (mRole != null) {
            fillGeneralPanel();
            generationChanged();
        }
        mLayout = new CardLayout();
        mContentPanel = new JPanel(mLayout);
        mContentPanel.add(firstPanel, "1");
        mContentPanel.add(disciplinesPanel, "2");
        mContentPanel.add(abilitiesPanel, "3");
        mContentPanel.add(meritsPanel, "4");
        mMaxPage = 4;
        getContentPane().add(mContentPanel, BorderLayout.CENTER);
        JPanel statsPanel = createStatsPanel();
        getContentPane().add(statsPanel, BorderLayout.WEST);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mPrevButton = new JButton("Previous");
        mPrevButton.setEnabled(false);
        mPrevButton.setActionCommand("prev");
        mPrevButton.addActionListener(this);
        btnPanel.add(mPrevButton);

        mNextButton = new JButton("Next");
        mNextButton.setActionCommand("next");
        mNextButton.addActionListener(this);
        btnPanel.add(mNextButton);

        JButton btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnPanel.add(btn);
        btn = new JButton("Finish");
        btn.setActionCommand("finish");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        pack();
        Utilities.positionMe(this);
    }

    private JPanel createGeneralPanel() throws SQLException, RemoteException {
        JPanel panel = new JPanel(new SpringLayout());
        mRoleName = new JTextField(mStdTextFieldLength);
        JLabel label = new JLabel("Role Name ");
        label.setLabelFor(mRoleName);
        panel.add(label);
        panel.add(mRoleName);
        /*mPlayerName = new JTextField(mStdTextFieldLength);
        label = new JLabel("Player Name ");
        label.setLabelFor(mPlayerName);
        panel.add(label);
        panel.add(mPlayerName);*/
        mPlayers = mDB.getPlayers();
        mPlayers.add(0, new IntWithString(-1, "None"));
        mPlayerCombo = new JComboBox(mPlayers);
        label = new JLabel("Player ");
        JPanel tmpPanel = new JPanel(new BorderLayout());
        tmpPanel.add(mPlayerCombo, BorderLayout.CENTER);
        mAddPlayerBtn = new JButton("Add");
        mAddPlayerBtn.setActionCommand("addPlayer");
        mAddPlayerBtn.setMargin(new Insets(1, 1, 1, 1));
        mAddPlayerBtn.addActionListener(this);
        tmpPanel.add(mAddPlayerBtn, BorderLayout.EAST);
        label.setLabelFor(tmpPanel);
        panel.add(label);
        panel.add(tmpPanel);
        Vector generations = mDB.getGenerations();
        mGenerationsCombo = new JComboBox(generations);
        mGenerationsCombo.setActionCommand("generationChanged");
        label = new JLabel("Generation ");
        label.setLabelFor(mGenerationsCombo);
        panel.add(label);
        panel.add(mGenerationsCombo);
        mEmbraced = new JTextField(mStdTextFieldLength);
        label = new JLabel("Embraced ");
        label.setLabelFor(mEmbraced);
        panel.add(label);
        panel.add(mEmbraced);
        mClansCombo = new JComboBox(mDB.getClans());
        mClansCombo.setActionCommand("clanChanged");
        mClansCombo.addActionListener(this);
        label = new JLabel("Clan ");
        label.setLabelFor(mClansCombo);
        panel.add(label);
        panel.add(mClansCombo);
        mSireCombo = new JComboBox();
        label = new JLabel("Sire ");
        label.setLabelFor(mSireCombo);
        panel.add(label);
        panel.add(mSireCombo);
        mNature = new JTextField(mStdTextFieldLength);
        label = new JLabel("Nature ");
        label.setLabelFor(mNature);
        panel.add(label);
        panel.add(mNature);
        mDemeanor = new JTextField(mStdTextFieldLength);
        label = new JLabel("Demeanor ");
        label.setLabelFor(mDemeanor);
        panel.add(label);
        panel.add(mDemeanor);
        mCourage = Utilities.createIntegerJSpinner(1, 5, 1, 1);
        label = new JLabel("Courage ");
        label.setLabelFor(mCourage);
        panel.add(label);
        panel.add(mCourage);
        mConsciense = Utilities.createIntegerJSpinner(1, 5, 1, 1);
        mConcienseORconvictionCombo = new JComboBox(new String[]{"Conciense", "Conviction"});
        mConcienseORconvictionCombo.setPrototypeDisplayValue("Self-Control");
        panel.add(mConcienseORconvictionCombo);
        panel.add(mConsciense);
        mSelfControl = Utilities.createIntegerJSpinner(1, 5, 1, 1);
        mSelfControlORinstinctCombo = new JComboBox(new String[]{"Self-Control", "Instinct"});
        mSelfControlORinstinctCombo.setPrototypeDisplayValue("Self-Control");
        panel.add(mSelfControlORinstinctCombo);
        panel.add(mSelfControl);
        mWillPower = Utilities.createIntegerJSpinner(1, 10, 1, 1);
        label = new JLabel("Willpower ");
        label.setLabelFor(mWillPower);
        panel.add(label);
        panel.add(mWillPower);
        mPathName = new JTextField(mStdTextFieldLength - 5);
        mPathDots = Utilities.createIntegerJSpinner(1, 5, 1, 1);
        JPanel pathPanel = new JPanel(new BorderLayout());
        pathPanel.add(mPathName, BorderLayout.CENTER);
        pathPanel.add(mPathDots, BorderLayout.EAST);
        label = new JLabel("Path / Road");
        label.setLabelFor(pathPanel);
        panel.add(label);
        panel.add(pathPanel);
        SpringUtilities.makeCompactGrid(panel,
                                        13, 2, //rows, cols
                                        6, 6, //initX, initY
                                        6, 6);       //xPad, yPad

        if (mRole == null) {
            for (int i = 0; i < mGenerationsCombo.getItemCount(); i++) {
                Generation gen = (Generation) mGenerationsCombo.getItemAt(i);
                if (gen.getGeneration() == 13) {
                    mGenerationsCombo.setSelectedIndex(i);
                    break;
                }
            }
            ChangeListener fListen = new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    int concience = Utilities.getSpinnerInteger(mConsciense);
                    int selfControl = Utilities.getSpinnerInteger(mSelfControl);
                    mPathDots.setValue(new Integer((concience + selfControl) / 2));
                }
            };
            mConsciense.addChangeListener(fListen);
            mSelfControl.addChangeListener(fListen);
        }
        else {
            if (mRole.isGhoul() && mRole.getId() <= 0) {
                for (int i = 0; i < generations.size(); i++) {
                    Generation generation = (Generation) generations.elementAt(i);
                    if (generation.getGeneration() == Role.GHOUL_GENERATION) {
                        mRole.setGeneration(generation);
                        mGenerationsCombo.setSelectedItem(generation);
                    }
                }
            }
        }
        mGenerationsCombo.addActionListener(this);
        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(panel, BorderLayout.WEST);
        JComponent disciplinesPanel = createDisciplinesPanel();
        JPanel centerPanel = new JPanel(new SpringLayout());
        centerPanel.add(disciplinesPanel);
        mQuoteArea = new JTextArea(3, 10);
        mQuoteArea.setWrapStyleWord(true);
        mQuoteArea.setLineWrap(true);

        GridBagPanel fightNflightPanel = new GridBagPanel();
        mFightOrFlights = mDB.getFightOrFlights();
        mFightOrFlights.add(0, new FightOrFlight(-1, "", ""));
        mFightCombo = new JComboBox(mFightOrFlights);
        mFlightCombo = new JComboBox(mFightOrFlights);

        fightNflightPanel.addLine("Fight Form: ", mFightCombo);
        fightNflightPanel.addLine("Flight Form: ", mFlightCombo);
        centerPanel.add(fightNflightPanel);
        JScrollPane scrollPane = new JScrollPane(mQuoteArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Quote"));
        scrollPane.setPreferredSize(new Dimension(50, 80));
        centerPanel.add(scrollPane);
        SpringUtilities.makeCompactGrid(centerPanel,
                                        3, 1, //rows, cols
                                        6, 6, //initX, initY
                                        2, 2);       //xPad, yPad
        borderPanel.add(centerPanel, BorderLayout.CENTER);
        if (mCreateTemplate) {
            mSireCombo.setEnabled(false);
            //mPlayerName.setEditable(false);
            //mPlayerName.setEnabled(false);
            mPlayerCombo.setEnabled(false);
            mAddPlayerBtn.setEnabled(false);
            mFightCombo.setEnabled(false);
            mFlightCombo.setEnabled(false);
        }
        return borderPanel;
    }

    private void fillGeneralPanel() {
        if (mRole == null) {
            return;
        }
        mRoleName.setText(mRole.getName());
        //mPlayerName.setText(mRole.getPlayerName());
        if (mRole.getPlayer() == null) {
            if (DEBUG) System.out.println("CreateRoleDialog.fillGeneralPanel(684) Player is null");
            mPlayerCombo.setSelectedIndex(0);
        }
        else {
            if (DEBUG) System.out.println("CreateRoleDialog.fillGeneralPanel(688) player is: " + mRole.getPlayer());
            //start with one since the first index is an IntWithString and means nothing!
            for (int i = 1; i < mPlayers.size(); i++) {
                Player player = (Player) mPlayers.elementAt(i);
                if (player.getId() == mRole.getPlayer().getId()) {
                    if (DEBUG) System.out.println("CreateRoleDialog.fillGeneralPanel(693) found player!");
                    mPlayerCombo.setSelectedItem(player);
                    break;
                }
            }
        }
        //mGenerationsCombo.setSelectedItem(mRole.getGeneration());
        if (mRole.getSelfControlORinstinct().equals("Instinct")) {
            mSelfControlORinstinctCombo.setSelectedIndex(1);
        }
        else {
            mSelfControlORinstinctCombo.setSelectedIndex(0);
        }
        if (mRole.getConcienseORconviction().equals("Conviction")) {
            mConcienseORconvictionCombo.setSelectedIndex(1);
        }
        else {
            mConcienseORconvictionCombo.setSelectedIndex(0);
        }
        if (mRole.isGhoul()) {
            for (int i = 0; i < mGenerationsCombo.getItemCount(); i++) {
                Generation gen = (Generation) mGenerationsCombo.getItemAt(i);
                if (gen.getGeneration() == Role.GHOUL_GENERATION) {
                    mGenerationsCombo.setSelectedIndex(i);
                    mRole.setGeneration(gen);
                    break;
                }
            }
            mGenerationsCombo.setEnabled(false);
        }
        else {
            for (int i = 0; i < mGenerationsCombo.getItemCount(); i++) {
                Generation gen = (Generation) mGenerationsCombo.getItemAt(i);
                if (gen.getGeneration() == mRole.getGeneration().getGeneration()) {
                    mGenerationsCombo.setSelectedIndex(i);
                    break;
                }
            }
            mGenerationsCombo.setEnabled(true);
        }
        mEmbraced.setText(mRole.getEmbracedString());
        for (int i = 0; i < mClansCombo.getItemCount(); i++) {
            Clan c = (Clan) mClansCombo.getItemAt(i);
            if (c.getId() == mRole.getClan().getId()) {
                mClansCombo.setSelectedIndex(i);
                break;
            }
        }
        //mClansCombo.setSelectedItem(mRole.getClan());
        if (mRole.getSire() == null || mRole.getSire().getNumber() < 0) {
            IntWithString intr = new IntWithString(-1, "None");
            mSireCombo.addItem(intr);
            mSireCombo.setSelectedItem(intr);
        }
        else {
            mSireCombo.addItem(mRole.getSire());
            mSireCombo.setSelectedItem(mRole.getSire());
        }
        mNature.setText(mRole.getNature());
        mDemeanor.setText(mRole.getDemeanor());
        mCourage.setValue(new Integer(mRole.getCourage()));
        mConsciense.setValue(new Integer(mRole.getConcience()));
        mSelfControl.setValue(new Integer(mRole.getSelfControl()));
        Utilities.changeSpinnerAttributes(mWillPower, 1, mRole.getGeneration().getWillpowerMax(), 1);
        mWillPower.setValue(new Integer(mRole.getWillpower()));
        mPathName.setText(mRole.getPath());
        mPathDots.setValue(new Integer(mRole.getPathDots()));
        mQuoteArea.setText(mRole.getQuote());

        if (mRole.getFightForm() == null && mRole.getFlightForm() == null) {
            mFightCombo.setSelectedIndex(0);
            mFlightCombo.setSelectedIndex(0);
        }
        else {
            for (int i = 0; i < mFightOrFlights.size(); i++) {
                FightOrFlight fightOrFlight = mFightOrFlights.elementAt(i);
                if (fightOrFlight.equals(mRole.getFightForm())) {
                    mFightCombo.setSelectedItem(fightOrFlight);
                }
                if (fightOrFlight.equals(mRole.getFlightForm())) {
                    mFlightCombo.setSelectedItem(fightOrFlight);
                }
            }
        }
    }

    private JPanel createAbilitiesPanel() throws SQLException, RemoteException {
        JPanel borderPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(1, 3, 3, 3));
        JPanel phyPanel = new JPanel(new BorderLayout());
        JPanel sosPanel = new JPanel(new BorderLayout());
        JPanel menPanel = new JPanel(new BorderLayout());
        mCellEditor = new SpinnerTableCellEditor(0, 5, 1);
        if (mRole != null) {
            mPhysicalAbilities = new EditAbilitiesTableModel(mDB.getAbilities(RetrievalDB.ABILITY_TYPE_PHYSICAL), mRole.getPhysicalAbilities());
        }
        else {
            mPhysicalAbilities = new EditAbilitiesTableModel(mDB.getAbilities(RetrievalDB.ABILITY_TYPE_PHYSICAL));
        }
        JTable table = new JTable(mPhysicalAbilities);
        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        table.getColumnModel().getColumn(2).setCellEditor(mCellEditor);
        table.getColumnModel().getColumn(0).setMinWidth(45);
        table.getColumnModel().getColumn(1).setMinWidth(105);
        table.getColumnModel().getColumn(2).setMinWidth(6);
        table.getColumnModel().getColumn(2).setMaxWidth(32);
        phyPanel.add(table, BorderLayout.CENTER);

        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("Physical   "), BorderLayout.WEST);
        if (mRole != null) {
            mPhysical = Utilities.createIntegerJSpinner(0, mRole.getGeneration().getTraitsMax(), 1, mRole.getPhysical());
        }
        else {
            mPhysical = Utilities.createIntegerJSpinner(0, 11, 1, 1);
        }
        p.add(mPhysical, BorderLayout.CENTER);
        phyPanel.add(p, BorderLayout.NORTH);
        if (mRole != null) {
            mSocialAbilities = new EditAbilitiesTableModel(mDB.getAbilities(RetrievalDB.ABILITY_TYPE_SOCIAL), mRole.getSocialAbilities());
        }
        else {
            mSocialAbilities = new EditAbilitiesTableModel(mDB.getAbilities(RetrievalDB.ABILITY_TYPE_SOCIAL));
        }
        table = new JTable(mSocialAbilities);
        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        table.getColumnModel().getColumn(2).setCellEditor(mCellEditor);
        table.getColumnModel().getColumn(0).setMinWidth(45);
        table.getColumnModel().getColumn(1).setMinWidth(105);
        table.getColumnModel().getColumn(2).setMinWidth(6);
        table.getColumnModel().getColumn(2).setMaxWidth(32);
        sosPanel.add(table, BorderLayout.CENTER);

        p = new JPanel(new BorderLayout());
        p.add(new JLabel("Social   "), BorderLayout.WEST);
        if (mRole != null) {
            mSocial = Utilities.createIntegerJSpinner(0, mRole.getGeneration().getTraitsMax(), 1, mRole.getSocial());
        }
        else {
            mSocial = Utilities.createIntegerJSpinner(0, 11, 1, 1);
        }
        p.add(mSocial, BorderLayout.CENTER);
        sosPanel.add(p, BorderLayout.NORTH);
        if (mRole == null) {
            mMentalAbilities = new EditAbilitiesTableModel(mDB.getAbilities(RetrievalDB.ABILITY_TYPE_MENTAL));
        }
        else {
            mMentalAbilities = new EditAbilitiesTableModel(mDB.getAbilities(RetrievalDB.ABILITY_TYPE_MENTAL), mRole.getMentalAbilities());
        }
        table = new JTable(mMentalAbilities);
        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        table.getColumnModel().getColumn(2).setCellEditor(mCellEditor);
        table.getColumnModel().getColumn(0).setMinWidth(45);
        table.getColumnModel().getColumn(1).setMinWidth(105);
        table.getColumnModel().getColumn(2).setMinWidth(6);
        table.getColumnModel().getColumn(2).setMaxWidth(32);
        menPanel.add(table, BorderLayout.CENTER);

        p = new JPanel(new BorderLayout());
        p.add(new JLabel("Mental   "), BorderLayout.WEST);
        if (mRole != null) {
            mMental = Utilities.createIntegerJSpinner(0, mRole.getGeneration().getTraitsMax(), 1, mRole.getMental());
        }
        else {
            mMental = Utilities.createIntegerJSpinner(0, 11, 1, 1);
        }
        p.add(mMental, BorderLayout.CENTER);
        menPanel.add(p, BorderLayout.NORTH);

        panel.add(phyPanel);
        panel.add(sosPanel);
        panel.add(menPanel);
        borderPanel.add(panel, BorderLayout.CENTER);

        mAttributeNotes = new JTextArea(6, 50);
        mAttributeNotes.setEditable(false);
        borderPanel.add(mAttributeNotes, BorderLayout.SOUTH);
        borderPanel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                List disciplinesList = mDisciplinesTableModel.getSelectedDiscsiplines();
                mAttributeNotes.setText("");
                for (int i = 0; i < disciplinesList.size(); i++) {
                    Discipline discipline = (Discipline) disciplinesList.get(i);
                    if (discipline.getRetestAbility() != null) {
                        String note = discipline.getName() + " retests with " + discipline.getRetestAbility().getName();
                        if (mAttributeNotes.getText().length() > 0) {
                            mAttributeNotes.append("\n" + note);
                        }
                        else {
                            mAttributeNotes.setText(note);
                        }
                    }
                }
            }
        });
        return borderPanel;
    }

    private JComponent createDisciplinesPanel() throws SQLException, RemoteException {
        Vector disciplines = mDB.getDisciplineNames();
        disciplines.add(0, new IntWithString(-1, ""));
        DefaultCellEditor editor = new DefaultCellEditor(new JComboBox(disciplines));
        if (mRole != null) {
            mDisciplinesTableModel = new DisciplinesTableModel(7, mRole.getDisciplines());
        }
        else {
            mDisciplinesTableModel = new DisciplinesTableModel(7, mDB.getClanDisciplines((Clan) mClansCombo.getSelectedItem()));
        }
        JTable table = new JTable(mDisciplinesTableModel);
        table.getColumnModel().getColumn(0).setCellEditor(editor);
        if (mRole != null) {
            mDisciplinesEditor = new SpinnerTableCellEditor(0, mRole.getGeneration().getDisciplinesMax(), 0);
        }
        else {
            mDisciplinesEditor = new SpinnerTableCellEditor(0, 5, 0);
        }
        table.getColumnModel().getColumn(2).setCellEditor(mDisciplinesEditor);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Disciplines"));
        scrollPane.setPreferredSize(new Dimension(220, 200));
        //panel.add(scrollPane, BorderLayout.WEST);
        return scrollPane;
    }

    private JPanel createExtraDisciplinesPanel() throws SQLException, RemoteException {
        //JPanel panel = new JPanel(new BorderLayout());
        JPanel pathsAndRitualsPanel = new JPanel(new GridLayout(2, 1));
        JPanel ugbugaPanel = new JPanel(new GridLayout(1, 2));
        /*ugbugaPanel.setFill(GridBagConstraints.BOTH);
        ugbugaPanel.setAnchor(GridBagConstraints.NORTH);*/
        /**
         * Thauma
         */
        mThaumaTypeFiled = new JTextField(15);
        if (mRole != null) {
            mThamaPathsTableModel = new PathsTableModel(5, mRole.getThaumaturgicalPaths());
            mThaumaTypeFiled.setText(mRole.getThaumaType());
        }
        else {
            mThamaPathsTableModel = new PathsTableModel(5);
        }
        JTable table = new JTable(mThamaPathsTableModel);
        table.getColumnModel().getColumn(1).setCellEditor(mDisciplinesEditor);
        Vector vector = mDB.getThaumaturgicalPathNames();
        vector.add(0, new IntWithString(-1, ""));
        DefaultCellEditor editor = new DefaultCellEditor(new JComboBox(vector));
        table.getColumnModel().getColumn(0).setCellEditor(editor);
        JScrollPane scrollPane = new JScrollPane(table);
        //scrollPane.setPreferredSize(new Dimension(320, 200));
        scrollPane.setPreferredSize(new Dimension(200, 140));
        JPanel pPanel = new JPanel(new BorderLayout());
        pPanel.setBorder(BorderFactory.createTitledBorder("Thaumaturgical Paths"));
        pPanel.add(scrollPane, BorderLayout.CENTER);
        pPanel.add(mThaumaTypeFiled, BorderLayout.NORTH);
        ugbugaPanel.add(pPanel);

        /**
         * Necromancy
         */
        mNecromancyTypeField = new JTextField(15);
        if (mRole != null) {
            mNecromancyPathsTableModel = new PathsTableModel(5, mRole.getNecromancyPaths());
            mNecromancyTypeField.setText(mRole.getNecromancyType());
        }
        else {
            mNecromancyPathsTableModel = new PathsTableModel(5);
        }
        table = new JTable(mNecromancyPathsTableModel);
        table.getColumnModel().getColumn(1).setCellEditor(mDisciplinesEditor);
        vector = mDB.getNectromancyPathNames();
        vector.add(0, new IntWithString(-1, ""));
        editor = new DefaultCellEditor(new JComboBox(vector));
        table.getColumnModel().getColumn(0).setCellEditor(editor);
        scrollPane = new JScrollPane(table);
        //scrollPane.setPreferredSize(new Dimension(320, 200));
        scrollPane.setPreferredSize(new Dimension(200, 140));
        pPanel = new JPanel(new BorderLayout());
        pPanel.add(scrollPane, BorderLayout.CENTER);
        pPanel.add(mNecromancyTypeField, BorderLayout.NORTH);
        pPanel.setBorder(BorderFactory.createTitledBorder("Necromancy Paths"));
        ugbugaPanel.add(pPanel);
        pathsAndRitualsPanel.add(ugbugaPanel);

        /**
         * Rituals
         */
        if (mRole != null) {
            mRitualsModel = new RitualsTableModel(7, mRole.getRituals());
        }
        else {
            mRitualsModel = new RitualsTableModel(7);
        }
        Vector<RitualType> ritualTypes = mDB.getRitualTypes();
        Box radioBox = Box.createVerticalBox();
        radioBox.setBorder(BorderFactory.createTitledBorder("Types Selector"));
        ButtonGroup radioGroup = new ButtonGroup();
        RitualType firstRitualType = null;
        for (int i = 0; i < ritualTypes.size(); i++) {
            RitualType ritualType = ritualTypes.elementAt(i);
            JRadioButton radio = new JRadioButton(new RitualTypeAction(ritualType));
            if (i == 0) {
                firstRitualType = ritualType;
                radio.setSelected(true);
            }
            radioBox.add(radio);
            radioGroup.add(radio);
        }
        mRitualsCombo = new JComboBox(mDB.getRituals(firstRitualType));
        mRitualsEditor = new DefaultCellEditor(mRitualsCombo);
        table = new JTable(mRitualsModel);
        table.getColumnModel().getColumn(1).setCellEditor(mRitualsEditor);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 140));
        JPanel ritualsPanel = new JPanel(new BorderLayout());
        ritualsPanel.add(scrollPane, BorderLayout.CENTER);
        ritualsPanel.add(radioBox, BorderLayout.EAST);
        ritualsPanel.setBorder(BorderFactory.createTitledBorder("Rituals"));
        pathsAndRitualsPanel.add(ritualsPanel);
        //panel.add(pathsAndRitualsPanel, BorderLayout.CENTER);
        //return panel;
        return pathsAndRitualsPanel;
    }

    class RitualTypeAction extends MutableAction {
        private RitualType mRitualType;

        public RitualTypeAction(RitualType pRitualType) {
            super(pRitualType.getName());
            mRitualType = pRitualType;
        }

        public void actionPerformed(ActionEvent e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Vector<Ritual> rituals = mDB.getRituals(mRitualType);
                        rituals.add(0, new Ritual(-1, "", mRitualType));
                        DefaultComboBoxModel model = new DefaultComboBoxModel(rituals);
                        mRitualsCombo.setModel(model);

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

    private JPanel createMeritsNflawsPanel() throws SQLException, IOException {
        JPanel panel = new JPanel(new GridLayout(2, 2, 3, 3));
        JPanel derPanel = new JPanel(new GridLayout(2, 1, 3, 3));
        mDerangementsTableModel = new DefaultTableModel(4, 1);
        mDerangementsTableModel.setColumnIdentifiers(new String[]{"Derangements"});
        JTable table = new JTable(mDerangementsTableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(50, 50));
        derPanel.add(scroll);
        mBeastTraitsTableModel = new DefaultTableModel(4, 1);
        mBeastTraitsTableModel.setColumnIdentifiers(new String[]{"Beast Traits"});
        table = new JTable(mBeastTraitsTableModel);
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(50, 50));
        derPanel.add(scroll);
        panel.add(derPanel);

        if (mRole != null) {
            mMeritsTableModel = new MeritsNflawsTableModel(7, "Merits", mRole.getMerits());
        }
        else {
            mMeritsTableModel = new MeritsNflawsTableModel(7, "Merits");
        }
        MeritsNflawsTableCellRenderer meritsCellRenderer = new MeritsNflawsTableCellRenderer();
        table = new JTable(mMeritsTableModel);
        table.getColumnModel().getColumn(0).setCellRenderer(meritsCellRenderer);
        Vector merits = mDB.getMerits();
        merits.add(0, new IntWithString(-1, ""));
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox(merits)));
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(100, 100));
        panel.add(scroll);
        if (mRole != null) {
            mOtherTraitsTableModel = new OtherTraitsTableModel(7, mRole.getOtherTraits());
        }
        else {
            mOtherTraitsTableModel = new OtherTraitsTableModel(7);
        }
        table = new JTable(mOtherTraitsTableModel);
        Vector otherTraits = mDB.getOtherTraitNames();
        otherTraits.add(0, new IntWithString(-1, ""));
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox(otherTraits)));
        table.getColumnModel().getColumn(1).setCellEditor(mCellEditor);
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(100, 100));
        panel.add(scroll);

        if (mRole != null) {
            mFlawsTableModel = new MeritsNflawsTableModel(7, "Flaws", mRole.getFlaws());
        }
        else {
            mFlawsTableModel = new MeritsNflawsTableModel(7, "Flaws");
        }
        table = new JTable(mFlawsTableModel);
        table.getColumnModel().getColumn(0).setCellRenderer(meritsCellRenderer);
        Vector flaws = mDB.getFlaws();
        flaws.add(0, new IntWithString(-1, ""));
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox(flaws)));
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(100, 100));
        panel.add(scroll);
        if (mRole != null) {
            List derangements = mRole.getDerangements();
            if (derangements.size() >= 4) {
                for (int i = 3; i <= derangements.size(); i++) {
                    mDerangementsTableModel.addRow(new String[]{""});
                }
            }
            for (int i = 0; i < derangements.size(); i++) {
                String s = (String) derangements.get(i);
                mDerangementsTableModel.setValueAt(s, i, 0);
            }
            List<String> beastTraits = mRole.getBeastTraits();
            if (beastTraits != null) {
                if (beastTraits.size() >= 4) {
                    for (int i = 3; i <= beastTraits.size(); i++) {
                        mBeastTraitsTableModel.addRow(new String[]{""});
                    }
                }
                for (int i = 0; i < beastTraits.size(); i++) {
                    String s = beastTraits.get(i);
                    mBeastTraitsTableModel.setValueAt(s, i, 0);
                }
            }
        }
        return panel;
    }

    private JPanel createStatsPanel() throws SQLException, RemoteException {
        if (DEBUG) System.out.println("[CreateRoleDialog][createStatsPanel][783] top");
        GridBagPanel gPanel = new GridBagPanel();

        mSuffersOfInjury = new JCheckBox("-Injury", true);
        mSuffersOfInjury.setToolTipText("Uncheck if the damage penalties shall be removed. i.e. if the role has Fortitude.");
        gPanel.addLine(mSuffersOfInjury);
        mExtraHealthLevels = Utilities.createIntegerJSpinner(0, 7, 1, 0);
        mExtraHealthLevels.setToolTipText("Number of extra Health Levels");
        gPanel.addLine(new JLabel("+Health: "), mExtraHealthLevels);
        mVitalsCombo = new JComboBox(Vitals.ALL);
        mVitalsCombo.setToolTipText("Vitals");
        gPanel.addLine(mVitalsCombo);
        mDomainsCombo = new JComboBox(mDB.getDomains());
        mDomainsCombo.setToolTipText("Domain");
        gPanel.addLine(mDomainsCombo);
        mSLPBox = new JCheckBox("SLP");
        gPanel.addLine(mSLPBox);
        //borderPanel.add(gPanel, BorderLayout.WEST);
        boolean createRules = true;
        if (mRole != null) {
            mSuffersOfInjury.setSelected(mRole.isSufferesOfInjury());
            mExtraHealthLevels.setValue(new Integer(mRole.getExtraHealthLevels()));
            mVitalsCombo.setSelectedItem(mRole.getVitals());
            mSLPBox.setSelected(mRole.isSLP());
            if (!mCreateTemplate) { //Templates does not contain domains so if you where to try and retrieve a domain it will be null
                Domain domain = mRole.getDomain();
                for (int i = 0; i < mDomainsCombo.getModel().getSize(); i++) {
                    Domain dom = (Domain) mDomainsCombo.getModel().getElementAt(i);
                    if (dom.getId() == domain.getId()) {
                        mDomainsCombo.setSelectedItem(dom);
                        break;
                    }
                }
            }
            if (mRole.getId() >= 0) {
                createRules = false;
            }
            else {
                createRules = true;
            }
        }
        else {
            Domain prefDom = AppPreferences.getPreferredDomain();
            for (int i = 0; i < mDomainsCombo.getModel().getSize(); i++) {
                Domain dom = (Domain) mDomainsCombo.getModel().getElementAt(i);
                if (dom.getId() == prefDom.getId()) {
                    mDomainsCombo.setSelectedItem(dom);
                    break;
                }
            }
            createRules = true;
        }


        final GridBagPanel statsPanel = new GridBagPanel();
        statsPanel.addLine(Box.createRigidArea(new Dimension(20, 10)));

        mEmbraced.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                updateCreateRules();
            }
        });

        mDisciplinesStats = new JTextField(3);
        mDisciplinesStats.setEditable(false);
        updateDisciplinesStat();
        statsPanel.addLine(new JLabel("Disciplines: "), mDisciplinesStats);
        TableModelListener disciplinesListener = new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                updateDisciplinesStat();
            }
        };
        mDisciplinesTableModel.addTableModelListener(disciplinesListener);
        mThamaPathsTableModel.addTableModelListener(disciplinesListener);
        mNecromancyPathsTableModel.addTableModelListener(disciplinesListener);
        mAttributesStats = new JTextField(3);
        mAttributesStats.setEditable(false);
        statsPanel.addLine(new JLabel("Attributes: "), mAttributesStats);

        ChangeListener attributesChangeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (DEBUG) System.out.println("[CreateRoleDialog][stateChanged][859] attributes state changed");
                updateAttributesStat();
            }
        };
        mPhysical.addChangeListener(attributesChangeListener);
        mSocial.addChangeListener(attributesChangeListener);
        mMental.addChangeListener(attributesChangeListener);

        mAbilitiesStats = new JTextField(3);
        mAbilitiesStats.setEditable(false);
        statsPanel.addLine(new JLabel("Abilities: "), mAbilitiesStats);
        TableModelListener abilitiesListener = new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                updateAbilitiesStat();
            }
        };
        mPhysicalAbilities.addTableModelListener(abilitiesListener);
        mSocialAbilities.addTableModelListener(abilitiesListener);
        mMentalAbilities.addTableModelListener(abilitiesListener);
        gPanel.addLine(statsPanel);
        updateCreateRules();
        if (createRules) {
            statsPanel.setVisible(true);
        }
        else {
            statsPanel.setVisible(false);
        }
        MutableAction showStatsAction = new MutableAction("Stats", KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK)) {
            public void actionPerformed(ActionEvent e) {
                statsPanel.setVisible(!statsPanel.isVisible());
                if (DEBUG) System.out.println("[CreateRoleDialog][actionPerformed][885] " + statsPanel.isVisible());
            }
        };
        mMeritsSum = new JTextField(3);
        mMeritsSum.setEditable(false);
        int sum = mMeritsTableModel.getSum() + mFlawsTableModel.getSum();
        mMeritsSum.setText(sum + "");
        TableModelListener listener = new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int sum = mMeritsTableModel.getSum() + mFlawsTableModel.getSum();
                if (DEBUG) System.out.println("[CreateRoleDialog][tableChanged][453] sum: " + sum);
                mMeritsSum.setText(sum + "");
            }
        };
        mMeritsTableModel.addTableModelListener(listener);
        mFlawsTableModel.addTableModelListener(listener);
        gPanel.addLine(new JLabel("Sum M&F:  "), mMeritsSum);
        mXP = new JTextField(3);
        mXP.setEditable(false);
        if (mRole != null) {
            if (mRole.getId() > 0) {
                mXP.setText(mRole.getExperience() + "");
                gPanel.addLine("XP: ", mXP);
            }
        }
        JPanel p = new JPanel(new BorderLayout());
        //gPanel.setBorder(BorderFactory.createEtchedBorder());
        p.setBorder(BorderFactory.createEtchedBorder());
        p.add(gPanel, BorderLayout.CENTER);
        JButton btn = new JButton(showStatsAction);
        p.add(btn, BorderLayout.SOUTH);
        //return gPanel;
        if (mCreateTemplate) {
            mVitalsCombo.setEnabled(false);
            mDomainsCombo.setEnabled(false);
            mSLPBox.setEnabled(false);
        }
        return p;
    }

    private void updateAbilitiesStat() {
        int sum = mPhysicalAbilities.getSum();
        sum += mSocialAbilities.getSum();
        sum += mMentalAbilities.getSum();
        if (mCreateRule != null) {
            int leftSum = mCreateRule.getAbilities() - sum;
            mAbilitiesStats.setText(leftSum + "");
        }
        /*if (mRole != null) { //A beginning to try and calculate ammount of XP spent on editing
            int oldSum = 0;
            for (int i = 0; i < mRole.getPhysicalAbilities().size(); i++) {
                Ability ability = (Ability) mRole.getPhysicalAbilities().get(i);
                oldSum += ability.getDots();
            }
            for (int i = 0; i < mRole.getSocialAbilities().size(); i++) {
                Ability ability = (Ability) mRole.getSocialAbilities().get(i);
                oldSum += ability.getDots();
            }
            for (int i = 0; i < mRole.getMentalAbilities().size(); i++) {
                Ability ability = (Ability) mRole.getMentalAbilities().get(i);
                oldSum += ability.getDots();
            }
            //if (DEBUG) System.out.println("CreateRoleDialog.updateAbilitiesStat(1164) oldSum: " + oldSum + " new sum: " + sum);
        }*/
    }

    private void updateDisciplinesStat() {
        int sum = mDisciplinesTableModel.getSum();
        sum += mThamaPathsTableModel.getSum();
        sum += mNecromancyPathsTableModel.getSum();
        if (mCreateRule != null) {
            int leftSum = mCreateRule.getDisciplines() - sum;
            mDisciplinesStats.setText(leftSum + "");
        }
        /*if (mRole != null) { //A beginning to try and calculate ammount of XP spent on editing
            int oldSum = 0;
            for (int i = 0; i < mRole.getDisciplines().size(); i++) {
                Discipline discipline = (Discipline) mRole.getDisciplines().get(i);
                oldSum += discipline.getDots();
            }
            for (int i = 0; i < mRole.getThaumaturgicalPaths().size(); i++) {
                Path path = (Path) mRole.getThaumaturgicalPaths().get(i);
                oldSum += path.getDots();
            }
            for (int i = 0; i < mRole.getNecromancyPaths().size(); i++) {
                Path path = (Path) mRole.getNecromancyPaths().get(i);
                oldSum += path.getDots();
            }
            //if (DEBUG) System.out.println("CreateRoleDialog.updateDisciplinesStat(1189) oldSum: " + oldSum + " new sum: " + sum);
        }*/
    }

    private void updateAttributesStat() {
        int sum = Utilities.getSpinnerInteger(mPhysical) + Utilities.getSpinnerInteger(mSocial) + Utilities.getSpinnerInteger(mMental);
        if (mCreateRule != null) {
            int leftSum = mCreateRule.getAttributes() - sum;
            mAttributesStats.setText(leftSum + "");
        }
        /*if (mRole != null) { //A beginning to try and calculate ammount of XP spent on editing
            int oldSum = mRole.getPhysical() + mRole.getSocial() + mRole.getMental();
            //if (DEBUG) System.out.println("CreateRoleDialog.updateAttributesStat(1201) oldSum: " + oldSum + " new sum: " + sum);
        }*/
    }

    private void updateCreateRules() {
        //if (DEBUG) System.out.println("[CreateRoleDialog][updateCreateRules][875] top");

        if (mEmbraced.getText().length() >= 1) {
            try {
                Date d = createEmbraced();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                int year = calendar.get(Calendar.YEAR);
                //if (DEBUG) System.out.println("[CreateRoleDialog][updateCreateRules][883] retrieving rule for year " + year);
                mCreateRule = mRulesDB.getRule(year);
                //if (DEBUG) System.out.println("[CreateRoleDialog][updateCreateRules][887] mCreateRule: " + mCreateRule);
                updateAbilitiesStat();
                updateAttributesStat();
                updateDisciplinesStat();
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                mCreateRule = null;
                report(e);
            }
        }
        else {
            mCreateRule = null;
        }
    }

    private Role compileToRole() {
        Role role = null;
        try {
            role = new Role(mRoleName.getText(), "", (Generation) mGenerationsCombo.getSelectedItem(),
                            (IntWithString) mSireCombo.getSelectedItem(), createEmbraced(),
                            (Clan) mClansCombo.getSelectedItem(), mNature.getText(), mDemeanor.getText(),
                            Utilities.getSpinnerInteger(mCourage), Utilities.getSpinnerInteger(mConsciense),
                            Utilities.getSpinnerInteger(mSelfControl), Utilities.getSpinnerInteger(mWillPower),
                            Utilities.getSpinnerInteger(mPathDots), mPathName.getText(),
                            Utilities.getSpinnerInteger(mPhysical), Utilities.getSpinnerInteger(mSocial),
                            Utilities.getSpinnerInteger(mMental), Utilities.getSpinnerInteger(mExtraHealthLevels),
                            mSuffersOfInjury.isSelected(), (String) mConcienseORconvictionCombo.getSelectedItem(), (String) mSelfControlORinstinctCombo.getSelectedItem());
            role.setDisciplines(mDisciplinesTableModel.getDisciplines());
            role.setThaumaturgicalPaths(mThamaPathsTableModel.getPaths());
            role.setNecromancyPaths(mNecromancyPathsTableModel.getPaths());
            role.setRituals(mRitualsModel.getList());
            role.setPhysicalAbilities(mPhysicalAbilities.getAbilities());
            role.setSocialAbilities(mSocialAbilities.getAbilities());
            role.setMentalAbilities(mMentalAbilities.getAbilities());
            role.setMerits(mMeritsTableModel.getSelectedMeritsORflaws());
            role.setFlaws(mFlawsTableModel.getSelectedMeritsORflaws());
            role.setOtherTraits(mOtherTraitsTableModel.getOtherTraits());
            FightOrFlight fightOrFlight = (FightOrFlight) mFightCombo.getSelectedItem();
            if (mPlayerCombo.getSelectedItem() instanceof IntWithString) {
                role.setPlayer(null);
            }
            else {
                role.setPlayer((Player) mPlayerCombo.getSelectedItem());
            }
            if (fightOrFlight.getId() >= 0) {
                role.setFightForm(fightOrFlight);
            }
            else {
                role.setFightForm(null);
            }
            fightOrFlight = (FightOrFlight) mFlightCombo.getSelectedItem();
            if (fightOrFlight.getId() >= 0) {
                role.setFlightForm(fightOrFlight);
            }
            else {
                role.setFlightForm(null);
            }
            Vector v = mDerangementsTableModel.getDataVector();
            ArrayList<String> li = new ArrayList<String>();
            for (int i = 0; i < v.size(); i++) {
                Vector row = (Vector) v.elementAt(i);
                if (row != null) {
                    String s = (String) row.elementAt(0);
                    if (s != null) {
                        s = s.trim();
                        if (s.length() > 0) {
                            li.add(s);
                        }
                    }
                }
            }
            role.setDerangements(li);
            v = mBeastTraitsTableModel.getDataVector();
            li = new ArrayList<String>();
            for (int i = 0; i < v.size(); i++) {
                Vector row = (Vector) v.elementAt(i);
                if (row != null) {
                    String s = (String) row.elementAt(0);
                    if (s != null) {
                        s = s.trim();
                        if (s.length() > 0) {
                            li.add(s);
                        }
                    }
                }
            }
            role.setBeastTraits(li);
            role.setVitals((Vitals) mVitalsCombo.getSelectedItem());
            role.setDomain((Domain) mDomainsCombo.getSelectedItem());
            role.setThaumaType(mThaumaTypeFiled.getText());
            role.setNecromancyType(mNecromancyTypeField.getText());
            role.setQuote(mQuoteArea.getText());
            role.setSLP(mSLPBox.isSelected());
        }
        catch (ParseException e) {
            if (DEBUG) e.printStackTrace();
            report("Wrongly formatted Embrace", e.getMessage() + "\nSimple year or full date.");
        }
        catch (NumberFormatException e) {
            report("Wrongly formatted Embrace", "That isn't even a number!");
        }

        return role;
    }

    private Date createEmbraced() throws ParseException {
        String date = mEmbraced.getText();
        try {
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            return format.parse(date);
        }
        catch (ParseException e) {
            try {
                int year = Integer.parseInt(date);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                return cal.getTime();
            }
            catch (NumberFormatException e1) {
                if (DEBUG) e1.printStackTrace();
            }
        }
        throw new ParseException(date, 0);
    }


    private void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void report(String pTitle, String pMessage) {
        JOptionPane.showMessageDialog(this, pMessage, pTitle, JOptionPane.ERROR_MESSAGE);
    }

    /*private Box severedComponents(JComponent pC1, JComponent pC2) {
        Box b = Box.createHorizontalBox();

        b.add(pC1);
        b.add(Box.createGlue());
        b.add(pC2);

        return b;
    }*/

    public static void main(String[] args) {
        JDialog diag = null;
        if (args.length > 0) {
            if (args[0].equals("reclaim")) {
                diag = new CreateRoleDialog((JFrame) null, loadFromFile());
            }
        }
        if (diag == null) {
            diag = new CreateRoleDialog((JFrame) null);
        }
        diag.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        diag.setVisible(true);
    }

    public boolean isFinished() {
        return mFinished;
    }
}
