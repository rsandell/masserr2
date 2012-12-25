package se.tdt.bobby.wodcc.ui.sqllist;

import se.tdt.bobby.wodcc.data.sqllists.ListStatement;
import se.tdt.bobby.wodcc.data.sqllists.ParamTypes;
import se.tdt.bobby.wodcc.ui.sqllist.components.models.ParametersTableModel;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.logic.sqllist.SqlListUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created: 2006-jul-24 23:34:25
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class CreateSqlStatementFrame extends JDialog implements ActionListener {
    private JScrollPane mSqlPane;
    private JTextArea mSqlArea;
    private JTable mParametersTable;
    private JButton mPrevButton;
    private JButton mNextButton;
    private JButton mFinishButton;
    private JButton mCloseButton;
    private JPanel mPagesPanel;
    private JPanel mMainPanel;
    private ListStatement mStatement;
    private CardLayout mCardLayout;
    private ParametersTableModel mParametersModel;
    private JScrollPane mParametersPane;
    private JTextField mNameField;
    private int mPage;
    private static final boolean DEBUG = false;
    private boolean mOKPerformed = false;

    public CreateSqlStatementFrame(ListStatement pStatement) throws HeadlessException {
        super();
        mStatement = pStatement;
        init();

    }

    public CreateSqlStatementFrame(Frame owner, ListStatement pStatement) throws HeadlessException {
        super(owner);
        mStatement = pStatement;
        init();
    }

    public CreateSqlStatementFrame(Dialog owner, ListStatement pStatement) throws HeadlessException {
        super(owner);
        mStatement = pStatement;
        init();
    }

    private void init() {
        if (mStatement == null) {
            setTitle("Create SQL List");
        }
        else {
            setTitle("Edit Sql List");
        }
        setModal(true);
        setContentPane(mMainPanel);


        mCardLayout = new CardLayout();
        mPagesPanel.setLayout(mCardLayout);
        mSqlArea = new JTextArea();
        mSqlPane = new JScrollPane(mSqlArea);
        mSqlPane.setBorder(BorderFactory.createTitledBorder("SQL"));
        //mCardLayout.addLayoutComponent(mSqlPane, "0");
        mPagesPanel.add(mSqlPane, "0");

        mParametersModel = new ParametersTableModel();
        mParametersTable = new JTable(mParametersModel);
        mParametersTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JComboBox(ParamTypes.ALL)));
        mParametersTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox(new String[] {"", "TextBox", "ComboBox"})));
        mParametersPane = new JScrollPane(mParametersTable);
        mParametersPane.setBorder(BorderFactory.createTitledBorder("Parameters"));
        //mCardLayout.addLayoutComponent(mParametersPane, "1");
        mPagesPanel.add(mParametersPane, "1");

        mCardLayout.first(mPagesPanel);
        mPage = 0;

        setSize(640, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mPrevButton.addActionListener(this);
        mNextButton.addActionListener(this);
        mFinishButton.addActionListener(this);
        mCloseButton.addActionListener(this);
        if(mStatement != null) {
            mNameField.setText(mStatement.getName());
            mSqlArea.setText(mStatement.getStatement());
            mParametersModel.reset(mStatement.getParameters(), SqlListUtil.countParameters(mStatement.getStatement()));
        }
        Utilities.positionMe(this);
    }

    public ListStatement showDialog() {
        setVisible(true);
        if(mOKPerformed) {
            int id = -1;
            if(mStatement != null) {
                id = mStatement.getId();
            }
            mStatement = new ListStatement(id, mNameField.getText(), mSqlArea.getText(), mParametersModel.getParameters());
            return mStatement;
        }
        else {
            mStatement = null;
        }
        return mStatement;
    }

    protected void setBtnEnables() {
        if(DEBUG) System.out.println("[CreateSqlStatementFrame.setBtnEnables(86)] mPage= " + mPage + " Count= " + mPagesPanel.getComponentCount());
        if (mPage <= 0) {
            if(DEBUG) System.out.println("[CreateSqlStatementFrame.setBtnEnables(89)] At beginning");
            mPrevButton.setEnabled(false);
            mNextButton.setEnabled(true);
            mFinishButton.setEnabled(false);
        }
        else {
            mPrevButton.setEnabled(true);
            if(DEBUG) System.out.println("[CreateSqlStatementFrame.setBtnEnables(96)] not at beginning");
            if (mPage >= mPagesPanel.getComponentCount() - 1) {
                if(DEBUG) System.out.println("[CreateSqlStatementFrame.setBtnEnables(98)] At end!");
                mNextButton.setEnabled(false);
                mFinishButton.setEnabled(true);
            }
            else {
                mNextButton.setEnabled(true);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mPrevButton) {
            mCardLayout.previous(mPagesPanel);
            mPage--;
            setBtnEnables();
            checkParametersUpdate();
        }
        else if (e.getSource() == mNextButton) {
            mCardLayout.next(mPagesPanel);
            mPage++;
            setBtnEnables();
            checkParametersUpdate();
        }
        else if (e.getSource() == mCloseButton) {
            mOKPerformed = false;
            setVisible(false);
        }
        else if (e.getSource() == mFinishButton) {
            mOKPerformed = true;
            setVisible(false);
        }
    }

    private void checkParametersUpdate() {
        if(mPage == 1) {
            mParametersModel.reset(mParametersModel.getParameters(), SqlListUtil.countParameters(mSqlArea.getText()));
        }
    }
}
