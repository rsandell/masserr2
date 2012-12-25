package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.ManipulationDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.controllers.SpinnerTableCellEditor;
import se.tdt.bobby.wodcc.ui.components.models.MeritsNflawsEditorTableModel;
import se.tdt.bobby.wodcc.ui.components.models.OtherTraitsEditorTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.NumberFormat;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-26 00:23:58
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class MeritsFlawsOtherTraitsDialog extends JDialog implements ActionListener {
    private MeritsNflawsEditorTableModel mMeritsNflawsEditorTableModel;
    private OtherTraitsEditorTableModel mOtherTraitsEditorTableModel;
    private static final boolean DEBUG = false;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("close")) {
            setVisible(false);
        }
    }

    class OtherTraitsTableCellRenderer extends DefaultTableCellRenderer {
        private NumberFormat mFormat;

        public OtherTraitsTableCellRenderer() {
            mFormat = NumberFormat.getPercentInstance();
            mFormat.setMinimumFractionDigits(2);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            float usage = mOtherTraitsEditorTableModel.getUsage(row);
            int numberOfRoles = mOtherTraitsEditorTableModel.getNumberOfRolesUsing(row);
            if (usage >= 0) {
                label.setToolTipText("Usage: " + numberOfRoles + " (" + mFormat.format(usage) + ")");
            }
            return label;
        }
    }

    class MeritsNflawsTableCellRenderer extends DefaultTableCellRenderer {
        private NumberFormat mFormat;

        public MeritsNflawsTableCellRenderer() {
            mFormat = NumberFormat.getPercentInstance();
            mFormat.setMinimumFractionDigits(2);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            float usage = mMeritsNflawsEditorTableModel.getUsage(row);
            int numberOfRoles = mMeritsNflawsEditorTableModel.getNumberOfRolesUsing(row);
            if (usage >= 0) {
                label.setToolTipText("Usage: " + numberOfRoles + " (" + mFormat.format(usage) + ")");
            }
            return label;
        }
    }

    public MeritsFlawsOtherTraitsDialog(Frame owner) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Merits, Flaws & Other Traits Editor");
        JPanel panel = new JPanel(new GridLayout(1, 2));
        RetrievalDB retrievalDB = Proxy.getRetrievalDB();
        ManipulationDB manipulationDB = Proxy.getManipulationDB();
        mMeritsNflawsEditorTableModel = new MeritsNflawsEditorTableModel(retrievalDB, manipulationDB, this);
        JTable table = new JTable(mMeritsNflawsEditorTableModel);
        table.getColumnModel().getColumn(0).setCellRenderer(new MeritsNflawsTableCellRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new SpinnerTableCellEditor(-10, 10, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Merits & Flaws"));
        panel.add(scrollPane);
        mOtherTraitsEditorTableModel = new OtherTraitsEditorTableModel(retrievalDB, manipulationDB, this);
        table = new JTable(mOtherTraitsEditorTableModel);
        table.getColumnModel().getColumn(0).setCellRenderer(new OtherTraitsTableCellRenderer());
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Other Traits"));
        panel.add(scrollPane);

        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("Close");
        btn.setActionCommand("close");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        pack();
        Utilities.positionMe(this);

        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        refresh();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        });
    }

    private void refresh() {
        try {
            mMeritsNflawsEditorTableModel.refresh();
            mOtherTraitsEditorTableModel.refresh();
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
        }
    }
}
