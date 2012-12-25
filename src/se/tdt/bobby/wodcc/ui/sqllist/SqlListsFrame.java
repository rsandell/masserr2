package se.tdt.bobby.wodcc.ui.sqllist;

import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.sqllists.ListStatement;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.StatementsDB;
import se.tdt.bobby.wodcc.ui.MainFrame;
import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created: 2006-jul-24 22:29:30
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class SqlListsFrame extends JFrame implements ActionListener {
    private JPanel mMainPanel;
    private JButton mRunBtn;
    private JButton mEditBtn;
    private JButton mNewBtn;
    private JList mStatementsList;
    private StatementsDB mStatementsDB;
    private static final boolean DEBUG = false;
    static SqlListsFrame sInstance;
    private JButton mDelButton;

    public SqlListsFrame() throws HeadlessException, SQLException, RemoteException {
        super("SQL Lists");
        sInstance = this;
        setContentPane(mMainPanel);
        mRunBtn.addActionListener(this);
        mEditBtn.addActionListener(this);
        mNewBtn.addActionListener(this);
        mDelButton.addActionListener(this);
        getRootPane().setDefaultButton(mRunBtn);
        setSize(640, 480);
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mStatementsDB = Proxy.getStatementsDB();
        mStatementsList.setListData(mStatementsDB.listStatements());
        if (mStatementsList.getModel().getSize() > 0) {
            mStatementsList.setSelectedIndex(0);
        }

        mStatementsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    runBtn();
                }
            }
        });

        mStatementsList.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER) {
                    runBtn();
                }
            }
        });
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                try {
                    int oldIndex = mStatementsList.getSelectedIndex();
                    mStatementsList.setListData(mStatementsDB.listStatements());
                    if (mStatementsList.getModel().getSize() > 0) {
                        if (mStatementsList.getModel().getSize() >= oldIndex) {
                            mStatementsList.setSelectedIndex(oldIndex);
                        }
                        else {
                            mStatementsList.setSelectedIndex(0);
                        }
                    }
                }
                catch (Exception e1) {
                    if (DEBUG) e1.printStackTrace();
                    report(e1);
                }
            }
        });
        Utilities.positionMe(this, MainFrame.sMainFrame);
    }

    private void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mEditBtn) {
            Runnable runnable = new Runnable() {
                public void run() {
                    IntWithString id = (IntWithString) mStatementsList.getSelectedValue();
                    if (id != null) {
                        try {
                            ListStatement statement = mStatementsDB.getStatement(id.getNumber());
                            CreateSqlStatementFrame dialog = new CreateSqlStatementFrame(sInstance, statement);
                            statement = dialog.showDialog();
                            if (statement != null) {
                                mStatementsDB.updateStatement(statement);
                                mStatementsList.setListData(mStatementsDB.listStatements());
                                setSelectedId(statement.getId());
                            }
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);

        }
        else if (e.getSource() == mRunBtn) {
            runBtn();
        }
        else if (e.getSource() == mNewBtn) {
            Runnable runnable = new Runnable() {
                public void run() {
                    CreateSqlStatementFrame dialog = new CreateSqlStatementFrame(sInstance, null);
                    ListStatement statement = dialog.showDialog();
                    if (statement != null) {
                        try {
                            int id = mStatementsDB.addStatement(statement);
                            mStatementsList.setListData(mStatementsDB.listStatements());
                            setSelectedId(id);
                        }
                        catch (Exception e1) {
                            if (DEBUG) e1.printStackTrace();
                            report(e1);
                        }

                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getSource() == mDelButton) {
            Runnable runnable = new Runnable() {
                public void run() {
                    IntWithString id = (IntWithString) mStatementsList.getSelectedValue();
                    if (id != null) {
                        int choice = JOptionPane.showConfirmDialog(sInstance, "Are you shure you want to delete \"" + id.getString() + "\"?", "Delete?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (choice == JOptionPane.YES_OPTION) {
                            try {
                                mStatementsDB.removeStatement(id.getNumber());
                                mStatementsList.setListData(mStatementsDB.listStatements());
                            }
                            catch (Exception e1) {
                                if (DEBUG) e1.printStackTrace();
                                report(e1);
                            }
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    protected void runBtn() {
        Runnable runnable = new Runnable() {
            public void run() {
                IntWithString id = (IntWithString) mStatementsList.getSelectedValue();
                if (id != null) {
                    try {
                        ListStatement statement = mStatementsDB.getStatement(id.getNumber());
                        new RunStatementFrame(statement);
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    private void setSelectedId(int pId) {
        ListModel list = mStatementsList.getModel();
        for (int i = 0; i < list.getSize(); i++) {
            IntWithString ids = (IntWithString) list.getElementAt(i);
            if (ids.getNumber() == pId) {
                mStatementsList.setSelectedIndex(i);
                return;
            }
        }
    }
}
