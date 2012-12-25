package se.tdt.bobby.wodcc.server.ui;

import se.tdt.bobby.wodcc.server.ServerPreferences;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.components.view.MDBFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Description.
 * <p/>
 * Created: 2004-jul-12 00:16:12
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ServerSetupFrame extends JFrame implements ActionListener {
    private JTextField mBaseDirField;
    private JTextField mDatabaseFileField;
    private JFileChooser mFileChooser;
    private JFileChooser mDirectoryChooser;

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("browseDatabaseFile")) {
            if (mFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = mFileChooser.getSelectedFile();
                if (f != null && f.exists() && f.isFile()) {
                    mDatabaseFileField.setText(f.getAbsolutePath());
                    if(mBaseDirField.getText().length() < 0) {
                        mDirectoryChooser.setSelectedFile(f.getParentFile());
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "The file \"" + f.getPath() + "\" Does not exsist or is not a File!", "Illegal File", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getActionCommand().equals("browseBaseDir")) {
            if (mDirectoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = mDirectoryChooser.getSelectedFile();
                if (f != null && f.exists() && f.isDirectory()) {
                    mBaseDirField.setText(f.getAbsolutePath());
                    if(mDatabaseFileField.getText().length() < 0) {
                        mFileChooser.setCurrentDirectory(f);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "The directory \"" + f.getPath() + "\" Does not exsist or is not a Directory!", "Illegal File", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getActionCommand().equals("save")) {
            ServerPreferences.setDatabaseFile(mDatabaseFileField.getText());
            ServerPreferences.setBaseDir(mBaseDirField.getText().length() > 0 ? mBaseDirField.getText() : null);
            exit();
        }
        else if (e.getActionCommand().equals("cancel")) {
            exit();
        }
    }

    private void exit() {
        setVisible(false);
        System.exit(0);
    }


    public ServerSetupFrame() throws HeadlessException {
        super("Masserr - Server Setup");
        mBaseDirField = new JTextField(30);
        mDatabaseFileField = new JTextField(30);
        mFileChooser = new JFileChooser();
        mFileChooser.setMultiSelectionEnabled(false);
        mFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        mFileChooser.setAcceptAllFileFilterUsed(false);
        mFileChooser.addChoosableFileFilter(new MDBFileFilter());
        mDirectoryChooser = new JFileChooser();
        mDirectoryChooser.setMultiSelectionEnabled(false);
        mDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        GridBagPanel panel = new GridBagPanel();
        JButton btn = new JButton("Browse");
        btn.setMargin(new Insets(1, 1, 1, 1));
        btn.setActionCommand("browseBaseDir");
        btn.addActionListener(this);
        panel.add("Base Dir: ");
        panel.add(mBaseDirField);
        panel.addLine(btn);
        btn = new JButton("Browse");
        btn.setMargin(new Insets(1, 1, 1, 1));
        btn.setActionCommand("browseDatabaseFile");
        btn.addActionListener(this);
        panel.add("Database File: ");
        panel.add(mDatabaseFileField);
        panel.addLine(btn);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn = new JButton("Save");
        btn.setActionCommand("save");
        btn.addActionListener(this);
        btnPanel.add(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        if (ServerPreferences.getBaseDir() != null) {
            mBaseDirField.setText(ServerPreferences.getBaseDir());
            File dir = new File(ServerPreferences.getBaseDir());
            mDirectoryChooser.setSelectedFile(dir);
            mFileChooser.setCurrentDirectory(dir);
        }
        mDatabaseFileField.setText(ServerPreferences.getDatabaseFile());
        if (ServerPreferences.getDatabaseFile().length() > 0) {
            mFileChooser.setSelectedFile(new File(ServerPreferences.getDatabaseFile()));
        }
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int midX = screen.width / 2;
        int midY = screen.height / 2;
        setLocation(midX - getWidth() / 2, midY - getHeight() / 2);
        setVisible(true);
    }
}
