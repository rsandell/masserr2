package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.view.MDBFileFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-26 14:17:41
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class PreferencesDialog extends JDialog implements ActionListener {
    private JTextField mDatabaseFile;
    private JTextField mHeadingName;
    private JFileChooser mFileChooser;
    private static final boolean DEBUG = false;
    private JTextField mTempDir;
    private JTextField mExplorer;
    private JFileChooser mDirChooser;
    private JFileChooser mExplorerChooser;
    private JCheckBox mGoOnline;
    private JTextField mRemoteServerHost;
    private JButton mDbBrowseBtn;
    private JComboBox mXPReadCombo;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("browse")) {
            if (mFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = mFileChooser.getSelectedFile();
                if (f.exists()) {
                    try {
                        mDatabaseFile.setText(f.getCanonicalPath());
                    }
                    catch (IOException e1) {
                        if (DEBUG) e1.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, f.getName() + " does not exsist!", "Wrong file", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getActionCommand().equals("cancel")) {
            mHeadingName.setText(AppPreferences.getHeadingName());
            mDatabaseFile.setText(AppPreferences.getDatabaseFile());
            setVisible(false);
        }
        else if (e.getActionCommand().equals("ok")) {
            boolean restart = false;
            if (mGoOnline.isSelected() != AppPreferences.isGoOnline()) {
                JOptionPane.showMessageDialog(this, "You will need to restart the system or else everything will be fucked up", "Restart needed", JOptionPane.INFORMATION_MESSAGE);
                restart = true;
            }
            AppPreferences.setDatabaseFile(mDatabaseFile.getText());
            AppPreferences.setHeadingName(mHeadingName.getText());
            AppPreferences.setExplorer(mExplorer.getText());
            AppPreferences.setTempDir(mTempDir.getText());
            AppPreferences.setGoOnline(mGoOnline.isSelected());
            AppPreferences.setServerHostName(mRemoteServerHost.getText());
            IntWithString intWithString = (IntWithString) mXPReadCombo.getSelectedItem();
            AppPreferences.setReadXPFrom(intWithString.getNumber());
            setVisible(false);
            if (restart) {
                System.exit(0);
            }
        }
        else if (e.getActionCommand().equals("browseExplorer")) {
            if (mExplorerChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = mExplorerChooser.getSelectedFile();
                if (f.exists()) {
                    try {
                        mExplorer.setText(f.getCanonicalPath());
                    }
                    catch (IOException e1) {
                        if (DEBUG) e1.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, f.getName() + " does not exsist!", "Wrong file", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getActionCommand().equals("browseDir")) {
            if (mDirChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = mDirChooser.getSelectedFile();
                if (f.exists()) {
                    try {
                        mTempDir.setText(f.getCanonicalPath());
                    }
                    catch (IOException e1) {
                        if (DEBUG) e1.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, f.getName() + " does not exsist!", "Wrong file", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getActionCommand().equals("goOnline")) {
            mDbBrowseBtn.setEnabled(!mGoOnline.isSelected());
            mDatabaseFile.setEnabled(!mGoOnline.isSelected());
            mRemoteServerHost.setEnabled(mGoOnline.isSelected());
        }
    }

    /*class MDBFileFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            else if (f.exists()) {
                if (f.getName().toLowerCase().endsWith(".mdb")) {
                    return true;
                }
                else {
                    return false;
                }
            }

            return false;
        }

        public String getDescription() {
            return "MS Access Database Files (.mdb)";
        }
    }*/

    class EXEFileFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            else if (f.exists()) {
                if (f.getName().toLowerCase().endsWith(".exe")) {
                    return true;
                }
                else {
                    return false;
                }
            }

            return false;
        }

        public String getDescription() {
            return "Windows Executable Files (.exe)";
        }
    }

    public PreferencesDialog(Frame owner) throws HeadlessException {
        super(owner, "Preferences", true);
        init();
    }

    public PreferencesDialog(Dialog owner) throws HeadlessException {
        super(owner, "Preferences", true);
        init();
    }

    protected void init() {
        GridBagPanel panel = new GridBagPanel();
        mDatabaseFile = new JTextField(35);
        mDatabaseFile.setText(AppPreferences.getDatabaseFile());
        mDbBrowseBtn = new JButton("...");
        mDbBrowseBtn.setActionCommand("browse");
        mDbBrowseBtn.addActionListener(this);
        mDbBrowseBtn.setMargin(new Insets(0, 1, 0, 1));
        JPanel tP = new JPanel(new BorderLayout());
        tP.add(mDatabaseFile, BorderLayout.CENTER);
        tP.add(mDbBrowseBtn, BorderLayout.EAST);
        mDatabaseFile.setEnabled(!AppPreferences.isGoOnline());
        mDbBrowseBtn.setEnabled(!AppPreferences.isGoOnline());
        panel.addLine(new JLabel("Database File: "), tP);
        mHeadingName = new JTextField(35);
        mHeadingName.setText(AppPreferences.getHeadingName());
        panel.addLine(new JLabel("Heading: "), mHeadingName);
        mTempDir = new JTextField(35);
        mTempDir.setText(AppPreferences.getTempDir());
        tP = new JPanel(new BorderLayout());
        tP.add(mTempDir, BorderLayout.CENTER);
        JButton btn = new JButton("...");
        btn.setActionCommand("browseDir");
        btn.addActionListener(this);
        btn.setMargin(new Insets(0, 1, 0, 1));
        tP.add(btn, BorderLayout.EAST);
        panel.addLine("Temp Directory: ", tP);

        mExplorer = new JTextField(35);
        mExplorer.setText(AppPreferences.getExplorer());
        mExplorer.setToolTipText("Internet Explorer is preferred.");
        tP = new JPanel(new BorderLayout());
        tP.add(mExplorer, BorderLayout.CENTER);
        btn = new JButton("...");
        btn.setActionCommand("browseExplorer");
        btn.addActionListener(this);
        btn.setMargin(new Insets(0, 1, 0, 1));
        tP.add(btn, BorderLayout.EAST);
        panel.addLine("Html Browser: ", tP);
        Vector xpReadVector = new Vector(2);
        xpReadVector.add(new IntWithString(AppPreferences.XP_ROLE, "Role"));
        xpReadVector.add(new IntWithString(AppPreferences.XP_PLAYER, "Player"));
        mXPReadCombo = new JComboBox(xpReadVector);
        mXPReadCombo.setToolTipText("Choose if The experience list should be fetched from the Players or the Roles");
        if (AppPreferences.getReadXPFrom() == AppPreferences.XP_ROLE) {
            mXPReadCombo.setSelectedIndex(0);
        }
        else if (AppPreferences.getReadXPFrom() == AppPreferences.XP_PLAYER) {
            mXPReadCombo.setSelectedIndex(1);
        }
        else {
            mXPReadCombo.setSelectedIndex(1);
        }
        panel.addLine("Show XP from: ", mXPReadCombo);
        mGoOnline = new JCheckBox("Go Online", AppPreferences.isGoOnline());
        mGoOnline.setActionCommand("goOnline");
        mGoOnline.addActionListener(this);
        panel.addLine(mGoOnline);
        mRemoteServerHost = new JTextField(35);
        mRemoteServerHost.setText(AppPreferences.getServerHostName());
        mRemoteServerHost.setEnabled(AppPreferences.isGoOnline());
        panel.addLine("Server HostName: ", mRemoteServerHost);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel btnpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn = new JButton("OK");
        btn.setActionCommand("ok");
        btn.addActionListener(this);
        btnpanel.add(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnpanel.add(btn);
        getContentPane().add(btnpanel, BorderLayout.SOUTH);
        if (AppPreferences.getDatabaseFile() != null) {
            File f = new File(AppPreferences.getDatabaseFile());
            f = f.getParentFile();
            if (f != null && f.exists()) {
                mFileChooser = new JFileChooser(f);
            }
            else {
                mFileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            }
        }
        else {
            mFileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        }
        mFileChooser.setFileFilter(new MDBFileFilter());
        mFileChooser.setMultiSelectionEnabled(false);

        if (AppPreferences.getExplorer() != null) {
            File f = new File(AppPreferences.getExplorer());
            f = f.getParentFile();
            if (f.exists()) {
                mExplorerChooser = new JFileChooser(f);
            }
            else {
                mExplorerChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            }
        }
        else {
            mExplorerChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        }
        mExplorerChooser.setFileFilter(new EXEFileFilter());
        mExplorerChooser.setMultiSelectionEnabled(false);

        if (AppPreferences.getExplorer() != null) {
            File f = new File(AppPreferences.getExplorer());
            f = f.getParentFile();
            if (f.exists()) {
                mDirChooser = new JFileChooser(f);
            }
            else {
                mDirChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            }
        }
        else {
            mDirChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        }
        mDirChooser.setMultiSelectionEnabled(false);
        mDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                mHeadingName.setText(AppPreferences.getHeadingName());
                mDatabaseFile.setText(AppPreferences.getDatabaseFile());
                mTempDir.setText(AppPreferences.getTempDir());
                mExplorer.setText(AppPreferences.getExplorer());
                mRemoteServerHost.setText(AppPreferences.getServerHostName());
                mGoOnline.setSelected(AppPreferences.isGoOnline());
                mRemoteServerHost.setEnabled(AppPreferences.isGoOnline());
                mDatabaseFile.setEnabled(!AppPreferences.isGoOnline());
                mDbBrowseBtn.setEnabled(!AppPreferences.isGoOnline());
                if (AppPreferences.getReadXPFrom() == AppPreferences.XP_ROLE) {
                    mXPReadCombo.setSelectedIndex(0);
                }
                else if (AppPreferences.getReadXPFrom() == AppPreferences.XP_PLAYER) {
                    mXPReadCombo.setSelectedIndex(1);
                }
                else {
                    mXPReadCombo.setSelectedIndex(1);
                }
                Utilities.positionMe((JDialog) e.getWindow());
            }
        });
        pack();
        Utilities.positionMe(this);
    }
}
