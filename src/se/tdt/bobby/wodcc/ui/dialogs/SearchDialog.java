package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-12 02:19:46
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class SearchDialog extends JDialog implements ActionListener {
    private JTextField mSearchField;
    private JCheckBox mSearchName;
    private JCheckBox mSearchPlayerName;
    private JCheckBox mSearchEmbraced;
    private JCheckBox mSearchNature;
    private JCheckBox mSearchDemeanor;
    private JCheckBox mSearchPath;
    private JCheckBox mSearchDerangement;
    private boolean mOKPerformed;

    public SearchDialog(Frame owner) throws HeadlessException {
        super(owner, "Search", true);
        init();
    }

    public SearchDialog(Dialog owner) throws HeadlessException {
        super(owner, "Search", true);
        init();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("search")) {
            mOKPerformed = true;
            setVisible(false);
        } else if(e.getActionCommand().equals("close")) {
            mOKPerformed = false;
            setVisible(false);
        }
    }

    public boolean showDialog() {
        mOKPerformed = false;
        setVisible(true);
        return mOKPerformed;
    }

    public boolean isSearchDerangement() {
        return mSearchDerangement.isSelected();
    }
    public boolean isSearchPath() {
        return mSearchPath.isSelected();
    }
    public boolean isSearchDemeanor() {
        return mSearchDemeanor.isSelected();
    }
    public boolean isSearchNature() {
        return mSearchNature.isSelected();
    }
    public boolean isSearchEmbraced() {
        return mSearchEmbraced.isSelected();
    }
    public boolean isSearchPlayerName() {
        return mSearchPlayerName.isSelected();
    }
    public boolean isSearchName() {
        return mSearchName.isSelected();
    }
    public String getSearchText() {
        return mSearchField.getText();
    }

    private void init(){
        GridBagPanel panel = new GridBagPanel();
        JLabel label = new JLabel("Text: ");
        mSearchField = new JTextField(45);
        label.setLabelFor(mSearchField);
        panel.addLine(label, mSearchField);
        JPanel altPanel = new JPanel();
        mSearchName = new JCheckBox("Name", false);
        altPanel.add(mSearchName);
        mSearchPlayerName = new JCheckBox("Player Name", true);
        altPanel.add(mSearchPlayerName);
        mSearchEmbraced = new JCheckBox("Embraced", false);
        altPanel.add(mSearchEmbraced);
        mSearchNature = new JCheckBox("Nature", false);
        altPanel.add(mSearchNature);
        mSearchDemeanor = new JCheckBox("Demeanor", false);
        altPanel.add(mSearchDemeanor);
        mSearchPath = new JCheckBox("Path", false);
        altPanel.add(mSearchPath);
        mSearchDerangement = new JCheckBox("Derangement", false);
        altPanel.add(mSearchDerangement);
        panel.addLine(altPanel);
        getContentPane().add(panel, BorderLayout.CENTER);
        altPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("Search");
        btn.setActionCommand("search");
        btn.addActionListener(this);
        altPanel.add(btn);
        getRootPane().setDefaultButton(btn);
        btn = new JButton("Close");
        btn.setActionCommand("close");
        btn.addActionListener(this);
        altPanel.add(btn);
        getContentPane().add(altPanel, BorderLayout.SOUTH);
        pack();
        Utilities.positionMe(this);
        //setVisible(true);
    }
}
