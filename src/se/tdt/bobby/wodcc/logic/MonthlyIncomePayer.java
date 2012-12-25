package se.tdt.bobby.wodcc.logic;


import se.tdt.bobby.wodcc.data.BankAccount;
import se.tdt.bobby.wodcc.data.IntWithString;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.BankingDB;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;
import se.tdt.bobby.wodcc.ui.dialogs.ProgressDialog;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

/**
 * Description
 *
 * Created: 2004-feb-19 15:14:28
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class MonthlyIncomePayer implements Runnable {
    private RetrievalDB mRetrievalDB;
    private BankingDB mBankingDB;
    private ProgressDialog mProgressDialog;
    private Vector mRoles;
    private static final boolean DEBUG = false;

    public MonthlyIncomePayer() throws RemoteException, SQLException {
        mRetrievalDB = Proxy.getRetrievalDB();
        mBankingDB = Proxy.getBankingDB();
    }

    /**
     * This will spawn a new Thread and pay every role that has an income BankAccount its monthly income
     * @throws SQLException
     * @throws ParseException
     */
    public void payToAll() throws SQLException, ParseException, RemoteException {
        mRoles = mRetrievalDB.getRoleNames();
        mProgressDialog = new ProgressDialog();
        mProgressDialog.setMinimum(0);
        mProgressDialog.setMaximum(mRoles.size());
        mProgressDialog.setValue(0);
        mProgressDialog.setText("Retrieving Roles");
        mProgressDialog.setVisible(true);
        new Thread(this).start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     Thread#run()
     */
    public void run() {
        try {
            for (int i = 0; i < mRoles.size(); i++) {
                IntWithString roleName = (IntWithString) mRoles.elementAt(i);
                mProgressDialog.setText("Retrieving " + roleName);
                Role r = mRetrievalDB.getRole(roleName.getNumber());
                BankAccount bac = r.getIncomeBankAccount();
                if (bac != null) {
                    mProgressDialog.setText("Calculating " + roleName);
                    long income = r.totalMonthlyIncome();
                    mProgressDialog.setText("Paying " + roleName + " " + income);
                    mBankingDB.depositFromBank(bac, income, "Monthly Income");
                }
                mProgressDialog.setValue(i+1);
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            mProgressDialog.setText(e.getMessage());
        }

    }
}
