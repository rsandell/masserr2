package se.tdt.bobby.wodcc.ui.sqllist;

import se.tdt.bobby.wodcc.data.sqllists.ListStatement;
import se.tdt.bobby.wodcc.data.sqllists.ListStatementResult;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.sqllist.SqlListUtil;
import se.tdt.bobby.wodcc.proxy.interfaces.StatementsDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.sqllist.components.models.ListStatementResultTableModel;
import se.tdt.bobby.wodcc.ui.sqllist.components.view.ExcelFileFilter;
import se.tdt.bobby.wodcc.ui.sqllist.components.view.HtmlFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created: 2006-jul-25 01:06:45
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class RunStatementFrame extends JFrame implements ActionListener {
    private JPanel mMainPanel;
    private JButton mCloseButton;
    private JButton mExportButton;
    private JButton mRefreshButton;
    private JTable mTable;
    private StatementsDB mStatementsDB;
    private ListStatementResultTableModel mResultsModel;
    private ListStatement mStatement;
    private static final boolean DEBUG = false;
    private JFileChooser mFileChooser;
    private ExcelFileFilter mExcelFileFilter;
    private HtmlFileFilter mHtmlFileFilter;
    private ListStatementResult mResult;
    private boolean mSetVariables = false;
    private ListStatement mOriginalStatement;

    public RunStatementFrame(ListStatement pStatement) throws HeadlessException, SQLException, RemoteException {
        super("Masserr - " + pStatement.getName());
        mStatement = pStatement;
        mOriginalStatement = pStatement;
        mCloseButton.addActionListener(this);
        mExportButton.addActionListener(this);
        mRefreshButton.addActionListener(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if (DEBUG) System.out.println("[RunStatementFrame.RunStatementFrame(37)] Fetching DB");
        mStatementsDB = Proxy.getStatementsDB();
        mResultsModel = new ListStatementResultTableModel();
        mTable.setModel(mResultsModel);
        setContentPane(mMainPanel);

        mFileChooser = new JFileChooser();
        mExcelFileFilter = new ExcelFileFilter();
        mFileChooser.addChoosableFileFilter(mExcelFileFilter);
        mHtmlFileFilter = new HtmlFileFilter();
        mFileChooser.addChoosableFileFilter(mHtmlFileFilter);
        mFileChooser.setAcceptAllFileFilterUsed(false);
        mFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        mFileChooser.setMultiSelectionEnabled(false);

        setSize(640, 480);
        Utilities.positionMe(this, SqlListsFrame.sInstance);
        setVisible(true);
        refresh();
    }

    protected void refresh() {
        try {
            if (SqlListUtil.containsVariables(mStatement) && !mSetVariables) {
                if (!setVariabels()) {
                    return;
                }
            }
            if (DEBUG) System.out.println("[RunStatementFrame.refresh(49)] Refreshing!");
            mResult = mStatementsDB.executeStatement(mStatement);
            mResultsModel.setResult(mResult);
            if (DEBUG) System.out.println("[RunStatementFrame.refresh(51)] Done!");
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    protected void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private boolean setVariabels() {
        try {
            StatementVariabelsDialog dialog = new StatementVariabelsDialog(this, mOriginalStatement.getParameters(), mStatementsDB);
            if (dialog.showDialog()) {
                mStatement = new ListStatement(mOriginalStatement.getId(), mOriginalStatement.getName(), mOriginalStatement.getStatement());
                mStatement.setParameters(dialog.getParameters());
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mRefreshButton) {
            Runnable runnable = new Runnable() {
                public void run() {
                    refresh();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getSource() == mExportButton) {
            Runnable runnable = new Runnable() {
                public void run() {
                    export();
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getSource() == mCloseButton) {
            setVisible(false);
        }
    }

    private void export() {

        mFileChooser.setSelectedFile(new File(mFileChooser.getCurrentDirectory(), mResult.getStatement().getName() + ".xls"));
        mFileChooser.setFileFilter(mExcelFileFilter);
        boolean loop = true;
        while (loop) {
            if (mFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = mFileChooser.getSelectedFile();
                boolean overwrite = true;
                if (f.exists()) {
                    int choice = JOptionPane.showConfirmDialog(this, "The file exsists!\nDo you want to overwrite it?", f.getPath(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (choice == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                    else if (choice == JOptionPane.YES_OPTION) {
                        overwrite = true;
                        loop = false;
                    }
                    else {
                        overwrite = false;
                        loop = true;
                    }
                }
                else {
                    loop = false;
                }
                if (overwrite) {
                    try {
                        if (mFileChooser.getFileFilter() == mHtmlFileFilter) {
                            String name = f.getName().toLowerCase();
                            if (!name.endsWith(".html") && !name.endsWith(".htm")) {
                                f = new File(f.getParent(), f.getName() + ".html");
                            }
                        }
                        else if (mFileChooser.getFileFilter() == mExcelFileFilter) {
                            String name = f.getName().toLowerCase();
                            if (!name.endsWith(".xls")) {
                                f = new File(f.getParent(), f.getName() + ".xls");
                            }
                        }
                        String html = SqlListUtil.generateHtmlTable(mResult);
                        PrintWriter writer = new PrintWriter(f);
                        writer.print(html);
                        writer.close();
                    }
                    catch (Exception e) {
                        if (DEBUG) e.printStackTrace();
                        report(e);
                        return;
                    }
                }
            }
        }
    }
}
