package se.tdt.bobby.wodcc.admin.ui.dialogs;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.AdminDB;
import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-27 15:49:37
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class DomainDialog extends JDialog implements ActionListener {
    private JTextField mNameField;
    private JTextArea mHistoryArea;
    private JTextField mHistoryFile;
    private JFileChooser mFileChooser;
    private Domain mDomain;
    private static final boolean DEBUG = false;
    private AdminDB mAdminDB;
    private boolean mOKPerformed = false;

    public DomainDialog(Frame owner, String title) throws HeadlessException, SQLException, RemoteException {
        super(owner, title, true);
        init();
    }

    public DomainDialog(Dialog owner, String title) throws HeadlessException, SQLException, RemoteException {
        super(owner, title, true);
        init();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("browseHTML")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    int choise = mFileChooser.showOpenDialog(mHistoryFile);
                    if (choise == JFileChooser.APPROVE_OPTION) {
                        File f = mFileChooser.getSelectedFile();
                        if (!f.exists()) {
                            JOptionPane.showMessageDialog(mHistoryFile, "The file " + f.getAbsolutePath() + " does not exsist!", "File Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (f.isDirectory()) {
                            JOptionPane.showMessageDialog(mHistoryFile, "The file " + f.getAbsolutePath() + " is a directory!", "File Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            mHistoryFile.setText(f.getAbsolutePath());
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("ok")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        save();
                        mOKPerformed = true;
                        setVisible(false);
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("cancel")) {
            mOKPerformed = false;
            setVisible(false);
        }
    }

    public static boolean showAddDialog(Frame owner) throws SQLException, RemoteException {
        DomainDialog dialog = new DomainDialog(owner, "Add Domain");
        dialog.setVisible(true);
        return dialog.isOKPerformed();
    }

    public static boolean showUpdateDialog(Frame owner, Domain pDomainToUpdate) throws SQLException, RemoteException {
        DomainDialog dialog = new DomainDialog(owner, "Update Domain");
        dialog.setDomain(pDomainToUpdate);
        dialog.setVisible(true);
        return dialog.isOKPerformed();
    }

    private void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void save() throws IOException, SQLException {
        mDomain.setName(mNameField.getText());
        if (mHistoryFile.getText().length() > 0) {
            mDomain.setHistory(readFile(mHistoryFile.getText()));
        }
        else {
            mDomain.setHistory(mHistoryArea.getText());
        }
        if (mDomain.getId() < 0) {
            mAdminDB.addDomain(mDomain);
        }
        else {
            mAdminDB.updateDomain(mDomain);
        }
    }

    public boolean isOKPerformed() {
        return mOKPerformed;
    }

    private String readFile(String pFileName) throws IOException {
        RandomAccessFile file = new RandomAccessFile(pFileName, "r");
        byte[] bytes = new byte[(int) file.length()];
        file.readFully(bytes);
        file.close();
        return new String(bytes);
    }

    public void setDomain(Domain pDomain) {
        mDomain = pDomain;
        mNameField.setText(mDomain.getName());
        mHistoryArea.setText(mDomain.getHistory());
    }

    class HTMLFileFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            else {
                String name = f.getName().toLowerCase();
                if (name.endsWith(".html") || name.endsWith(".htm")) {
                    return true;
                }
            }
            return false;
        }

        public String getDescription() {
            return "HTML Files (*.html *.htm)";
        }
    }

    private void init() throws SQLException, RemoteException {
        mAdminDB = Proxy.getAdminDB();
        mNameField = new JTextField(35);
        mHistoryArea = new JTextArea(20, 35);
        mHistoryFile = new JTextField(35);
        mFileChooser = new JFileChooser();
        mFileChooser.setFileFilter(new HTMLFileFilter());
        mFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        mFileChooser.setMultiSelectionEnabled(false);
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Name: ");
        label.setLabelFor(mNameField);
        panel.add(label, BorderLayout.WEST);
        panel.add(mNameField, BorderLayout.CENTER);
        centerPanel.add(panel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(mHistoryArea);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder("History"));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        panel = new JPanel(new BorderLayout());
        label = new JLabel("History File: ");
        label.setLabelFor(mHistoryFile);
        panel.add(label, BorderLayout.WEST);
        panel.add(mHistoryFile, BorderLayout.CENTER);
        JButton btn = new JButton("Browse");
        btn.setActionCommand("browseHTML");
        btn.addActionListener(this);
        panel.add(btn, BorderLayout.EAST);
        centerPanel.add(panel, BorderLayout.SOUTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btn = new JButton("OK");
        btn.setActionCommand("ok");
        btn.addActionListener(this);
        getRootPane().setDefaultButton(btn);
        panel.add(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        panel.add(btn);
        getContentPane().add(panel, BorderLayout.SOUTH);
        pack();
        setDomain(new Domain(-1, "", ""));
        Utilities.positionMe(this);
    }
}
