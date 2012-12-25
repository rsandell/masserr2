package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.logic.FileQuestionaire;
import se.tdt.bobby.wodcc.logic.OutputCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-17 19:12:27
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class BackgroundsDialog extends JDialog implements ActionListener {
    private Role mRole;
    private RetrievalDB mRetrievalDB;
    private ManipulationDB mManipulationDB;
    private JTextArea mBackgroundTextArea;
    private JTextArea mWillTextArea;
    private static final boolean DEBUG = false;

    public BackgroundsDialog(Frame owner, Role pRole) throws HeadlessException, SQLException, RemoteException, NullPointerException {
        super(owner, "Background/Will for " + pRole.getName());
        mRole = pRole;
        init();
    }

    public BackgroundsDialog(Dialog owner, Role pRole) throws HeadlessException, SQLException, RemoteException, NullPointerException {
        super(owner, "Background/Will for " + pRole.getName());
        mRole = pRole;
        init();
    }

    private void init() throws SQLException, RemoteException {
        mRetrievalDB = Proxy.getRetrievalDB();
        mManipulationDB = Proxy.getManipulationDB();

        String[] backNwill = mRetrievalDB.getBackgroundAndWill(mRole.getId());

        mBackgroundTextArea = new JTextArea(backNwill[0]);
        mBackgroundTextArea.setWrapStyleWord(true);
        mBackgroundTextArea.setLineWrap(true);
        mWillTextArea = new JTextArea(backNwill[1]);
        mWillTextArea.setWrapStyleWord(true);
        mWillTextArea.setLineWrap(true);

        JSplitPane sPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JScrollPane scroll = new JScrollPane(mBackgroundTextArea);
        scroll.setPreferredSize(new Dimension(800, 280));
        scroll.setBorder(BorderFactory.createTitledBorder("Background"));
        sPane.add(scroll, JSplitPane.TOP);

        scroll = new JScrollPane(mWillTextArea);
        scroll.setPreferredSize(new Dimension(800, 280));
        scroll.setBorder(BorderFactory.createTitledBorder("Will"));
        sPane.add(scroll, JSplitPane.BOTTOM);
        getContentPane().add(sPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn;
        btn = new JButton("Export");
        btn.setActionCommand("export");
        btn.addActionListener(this);
        btnPanel.add(btn);
        ImageIcon icon = IconFactory.getIconFactory().getIcon("PrintPreview24.gif");
        Image image = icon.getImage();
        image = image.getScaledInstance(-1, 16, Image.SCALE_DEFAULT);
        icon.setImage(image);
        btn = new JButton("Show", icon);
        btn.setActionCommand("show");
        btn.addActionListener(this);
        btnPanel.add(btn);
        btnPanel.add(new JLabel("     "));
        btn = new JButton("Save");
        btn.setActionCommand("save");
        btn.addActionListener(this);
        getRootPane().setDefaultButton(btn);
        btnPanel.add(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnPanel.add(btn);

        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        Utilities.positionMe(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        mManipulationDB.updateBackgroundAndWill(mRole, mBackgroundTextArea.getText(), mWillTextArea.getText());
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
            setVisible(false);
        }
        else if (e.getActionCommand().equals("export")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setMultiSelectionEnabled(false);
                    File dir = chooser.getCurrentDirectory();
                    File f = new File(dir, mRole.getName() + " BackgroundAndWill.txt");
                    chooser.setSelectedFile(f);
                    while (chooser.showSaveDialog(mBackgroundTextArea) == JFileChooser.APPROVE_OPTION) {


                        File file = chooser.getSelectedFile();
                        if (file.isDirectory()) {
                            JOptionPane.showMessageDialog(mBackgroundTextArea, "That is a directory!", "Save", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            boolean save = false;
                            if (file.exists()) {
                                int choise = JOptionPane.showConfirmDialog(mBackgroundTextArea, "The file " + file.getName() + " exists.\nDo you want to overwrite it?", "Overwrite?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (choise == JOptionPane.YES_OPTION) {
                                    save = true;
                                }
                                else if (choise == JOptionPane.NO_OPTION) {
                                    save = false;
                                }
                                else {
                                    break;
                                }
                            }
                            else {
                                save = true;
                            }
                            if (save) {
                                try {
                                    BufferedWriter br = new BufferedWriter(new FileWriter(file));
                                    br.write("Background");
                                    br.newLine();
                                    StringTokenizer token = new StringTokenizer(mBackgroundTextArea.getText(), "\n");
                                    while (token.hasMoreTokens()) {
                                        br.write(token.nextToken());
                                        br.newLine();
                                    }
                                    br.write("Will");
                                    br.newLine();
                                    token = new StringTokenizer(mWillTextArea.getText(), "\n");
                                    while (token.hasMoreTokens()) {
                                        br.write(token.nextToken());
                                        br.newLine();
                                    }
                                    br.close();
                                    break;
                                }
                                catch (IOException e1) {
                                    if (DEBUG) e1.printStackTrace();
                                    report(e1);
                                    break;
                                }
                            }
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("show")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    showBackground();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    private void showBackground() {
        try {
            File f = OutputCreator.getInstance().makeBackgrounds(mRole.getName(), mBackgroundTextArea.getText(), mWillTextArea.getText());
            Process p = Utilities.startExplorer(f.getAbsolutePath());
            if (p != null) {
                FileQuestionaire quest = new FileQuestionaire(p, f, this);
            }
        }
        catch (Exception e1) {
            if (DEBUG) e1.printStackTrace();
            report(e1);
        }
    }

    private void report(Exception pE1) {
        JOptionPane.showMessageDialog(this, pE1.getMessage(), pE1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }


}
