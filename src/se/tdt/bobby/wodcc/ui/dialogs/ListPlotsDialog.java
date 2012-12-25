package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.Plot;
import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.InfluenceDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.components.view.SpringUtilities;
import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-27 00:10:34
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ListPlotsDialog extends JDialog implements ActionListener {
    private RetrievalDB mRetrievalDB;
    private JComboBox mDomainsCombo;
    private JCheckBox mDoneCheck;
    private static boolean sDoneCheck = false;
    private InfluenceDB mInfluenceDB;
    private static final boolean DEBUG = false;
    private List<Plot> mPlotsList;
    private HashMap<Integer, List<IntWithString>> mAssignmentMap;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    sDoneCheck = mDoneCheck.isSelected();
                    Domain domain = (Domain) mDomainsCombo.getSelectedItem();
                    try {
                        if (domain.getId() >= 0) {
                            mPlotsList = mInfluenceDB.getPlots(domain, mDoneCheck.isSelected());
                            mAssignmentMap = mInfluenceDB.getAssignedRolesForPlots(domain, mDoneCheck.isSelected());
                        }
                        else {
                            mPlotsList = mInfluenceDB.getPlots(null, mDoneCheck.isSelected());
                            mAssignmentMap = mInfluenceDB.getAssignedRolesForPlots(null, mDoneCheck.isSelected());
                        }
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
            mPlotsList = null;
            setVisible(false);
        }
    }

    private void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    public ListPlotsDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "List Plots", true);
        init();
    }

    public ListPlotsDialog(Dialog owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "List Plots", true);
        init();
    }

    private void init() throws SQLException, RemoteException {
        mRetrievalDB = Proxy.getRetrievalDB();
        mInfluenceDB = Proxy.getInfluenceDB();
        Vector domains = mRetrievalDB.getDomains();
        domains.add(new Domain(-1, "All"));
        mDomainsCombo = new JComboBox(domains);
        mDoneCheck = new JCheckBox("Show Done", sDoneCheck);
        JPanel panel = new JPanel(new SpringLayout());
        JLabel label = new JLabel("Domain ");
        label.setLabelFor(mDomainsCombo);
        panel.add(label);
        panel.add(mDomainsCombo);
        label = new JLabel(" ");
        label.setLabelFor(mDoneCheck);
        panel.add(label);
        panel.add(mDoneCheck);
        SpringUtilities.makeCompactGrid(panel,
                                        2, 2, //rows, cols
                                        6, 6, //initX, initY
                                        6, 6);       //xPad, yPad
        getContentPane().add(panel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("OK");
        btn.setActionCommand("ok");
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
        setVisible(true);
    }

    public List<Plot> getPlotsList() {
        return mPlotsList;
    }

    public HashMap<Integer, List<IntWithString>> getAssignmentMap() {
        return mAssignmentMap;
    }
}
