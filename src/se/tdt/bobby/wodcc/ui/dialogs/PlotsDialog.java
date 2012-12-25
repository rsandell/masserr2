package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.Plot;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.InfluenceDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-25 17:47:54
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class PlotsDialog extends JDialog implements ActionListener, ListSelectionListener {
    private JList mList;
    private InfluenceDB mInfluenceDB;
    private JTextField mTitleField;
    private JTextArea mDescriptionArea;
    private JTextArea mPositiveArea;
    private JTextArea mNegativeArea;
    private JCheckBox mDoneCheck;
    private RetrievalDB mRetrievalDB;
    private JComboBox mSelectedDomainCombo;
    private JComboBox mDomainCombo;
    private static final boolean DEBUG = false;
    private Plot mSelectedPlot;
    private JTextArea mSLdescriptionArea;

    public PlotsDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Plots", false);
        init();
    }

    public PlotsDialog(Dialog owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Plots", false);
        init();
    }

    private void updateList() throws SQLException, RemoteException {
        Plot selected = (Plot) mList.getSelectedValue();
        Vector<Plot> plots = mInfluenceDB.getPlotTitles((Domain) mSelectedDomainCombo.getSelectedItem());
        mList.setListData(plots);
        if (selected != null) {
            mSelectedPlot = mInfluenceDB.getPlot(selected);
            for (int i = 0; i < plots.size(); i++) {
                Plot plot = plots.elementAt(i);
                if(plot.getId() == selected.getId()) {
                    mList.setSelectedIndex(i);
                    break;
                }
            }
        }
        else {
            if (plots.size() > 0) {
                mList.setSelectedIndex(0);
                mSelectedPlot = mInfluenceDB.getPlot(plots.get(0));
            }
            else {
                mSelectedPlot = new Plot(-1, "", (Domain) mSelectedDomainCombo.getSelectedItem());
            }
        }
        updateInfo();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("domainChanged")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        updateList();
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("new")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    mSelectedPlot = new Plot(-1, "", (Domain) mSelectedDomainCombo.getSelectedItem());
                    updateInfo();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("save")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        save();
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

    private void save() throws SQLException, RemoteException {
        if (mSelectedPlot != null) {
            mSelectedPlot.setTitle(mTitleField.getText());
            mSelectedPlot.setDescription(mDescriptionArea.getText());
            mSelectedPlot.setPositive(mPositiveArea.getText());
            mSelectedPlot.setNegative(mNegativeArea.getText());
            mSelectedPlot.setDone(mDoneCheck.isSelected());
            mSelectedPlot.setDomain((Domain) mDomainCombo.getSelectedItem());
            mSelectedPlot.setSLdescription(mSLdescriptionArea.getText());
            if (mSelectedPlot.getId() < 0) {
                mInfluenceDB.addPlot(mSelectedPlot);
                updateList();
            }
            else {
                mInfluenceDB.updatePlot(mSelectedPlot);
                updateList();
            }
        }
    }

    private void updateInfo() {
        if (mSelectedPlot != null) {
            mTitleField.setText(mSelectedPlot.getTitle());
            mDescriptionArea.setText(mSelectedPlot.getDescription());
            mPositiveArea.setText(mSelectedPlot.getPositive());
            mNegativeArea.setText(mSelectedPlot.getNegative());
            mDoneCheck.setSelected(mSelectedPlot.isDone());
            mSLdescriptionArea.setText(mSelectedPlot.getSLdescription());
            for (int i = 0; i < mDomainCombo.getModel().getSize(); i++) {
                Domain domain = (Domain) mDomainCombo.getModel().getElementAt(i);
                if (domain.equals(mSelectedPlot.getDomain())) {
                    mDomainCombo.setSelectedItem(domain);
                    break;
                }
            }
        }
        else {
            mTitleField.setText("");
            mDescriptionArea.setText("");
            mPositiveArea.setText("");
            mNegativeArea.setText("");
            mDoneCheck.setSelected(false);
            mSLdescriptionArea.setText("");
        }
    }

    private void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    public void valueChanged(ListSelectionEvent e) {
        try {
            Plot selected = (Plot) mList.getSelectedValue();
            if (selected != null) {
                mSelectedPlot = mInfluenceDB.getPlot(selected);
            }
            else {
                mSelectedPlot = null;
            }
            updateInfo();

        }
        catch (Exception e1) {
            if (DEBUG) e1.printStackTrace();
            report(e1);
        }
    }

    private void init() throws SQLException, RemoteException {
        mInfluenceDB = Proxy.getInfluenceDB();
        mRetrievalDB = Proxy.getRetrievalDB();
        Vector domains = mRetrievalDB.getDomains();
        Domain selected = null;
        Domain preferred = AppPreferences.getPreferredDomain();
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = (Domain) domains.elementAt(i);
            if (domain.getId() == preferred.getId()) {
                selected = domain;
                break;
            }
        }
        mSelectedDomainCombo = new JComboBox(domains);
        if (selected != null) {
            mSelectedDomainCombo.setSelectedItem(selected);
        }
        else {
            selected = (Domain) mSelectedDomainCombo.getSelectedItem();
        }
        mSelectedDomainCombo.setActionCommand("domainChanged");
        mSelectedDomainCombo.addActionListener(this);
        mList = new JList(mInfluenceDB.getPlotTitles(selected));
        mList.setPrototypeCellValue("1: Jag är normalt lång");
        mList.addListSelectionListener(this);
        JPanel wPanel = new JPanel(new BorderLayout());
        wPanel.add(new JScrollPane(mList), BorderLayout.CENTER);
        wPanel.add(mSelectedDomainCombo, BorderLayout.NORTH);
        getContentPane().add(wPanel, BorderLayout.WEST);
        mTitleField = new JTextField(35);
        mDescriptionArea = new JTextArea(35, 8);
        mDescriptionArea.setLineWrap(true);
        mDescriptionArea.setWrapStyleWord(true);
        mPositiveArea = new JTextArea(35, 5);
        mPositiveArea.setLineWrap(true);
        mPositiveArea.setWrapStyleWord(true);
        mNegativeArea = new JTextArea(35, 5);
        mNegativeArea.setLineWrap(true);
        mPositiveArea.setWrapStyleWord(true);
        mDoneCheck = new JCheckBox("Done");
        mDomainCombo = new JComboBox(domains);
        mSLdescriptionArea = new JTextArea(35, 8);
        mSLdescriptionArea.setWrapStyleWord(true);
        mSLdescriptionArea.setLineWrap(true);

        GridBagPanel panel = new GridBagPanel();
        panel.addLine("Title: ", mTitleField);
        panel.addLine("Domain: ", mDomainCombo);
        JScrollPane scrollPane = new JScrollPane(mDescriptionArea);
        scrollPane.setPreferredSize(new Dimension(320, 150));
        panel.addLine("Description: ", scrollPane);
        scrollPane = new JScrollPane(mSLdescriptionArea);
        scrollPane.setPreferredSize(new Dimension(320, 150));
        panel.addLine("SL Description: ", scrollPane);
        scrollPane = new JScrollPane(mPositiveArea);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        panel.addLine("Positive: ", scrollPane);
        scrollPane = new JScrollPane(mNegativeArea);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        panel.addLine("Negative: ", scrollPane);
        panel.addLine(mDoneCheck);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("New");
        btn.setActionCommand("new");
        btn.addActionListener(this);
        btnPanel.add(btn);
        btnPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        btn = new JButton("Save");
        btn.setActionCommand("save");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        pack();
        Utilities.positionMe(this);
        setVisible(true);
        mSelectedPlot = new Plot(-1, "", (Domain) mSelectedDomainCombo.getSelectedItem());
        if (mList.getModel().getSize() > 0) {
            mList.setSelectedIndex(0);
        }
    }
}
