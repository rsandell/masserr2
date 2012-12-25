package se.tdt.bobby.wodcc.proxy.interfaces;

import se.tdt.bobby.wodcc.data.BankAccount;
import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.data.Resource;
import se.tdt.bobby.wodcc.data.Withdrawal;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:42:11
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface BankingDB extends BasicDB {
    boolean hasAccount(int pRoleId) throws SQLException, RemoteException;

    void createAccount(Role pRoleOwner, BankAccount pBankAccount) throws SQLException, RemoteException;

    void setIncome(Role pSelectedRole, BankAccount pAccount) throws SQLException, RemoteException;

    void setExtraMonthlyIncome(Role pRole, long pExtraMonthlyIncome) throws SQLException, RemoteException;

    void buyResource(Role pRole, BankAccount pBankAccount, Resource pResource) throws SQLException, RemoteException;

    void buyMoreOrSellResource(Role pRole, BankAccount pBankAccount, Resource pResource) throws SQLException, RemoteException;

    void deposit(Withdrawal pWithdrawal, BankAccount pToAccount, String pMessage) throws SQLException, RemoteException;

    void deposit(Vector pWithdrawals, BankAccount pToAccount, String pMessage) throws SQLException, RemoteException;

    void depositFromBank(BankAccount pToAccount, long pAmmount, String pMessage) throws SQLException, RemoteException;

    List getBankAccountsForRole(int pRoleId) throws SQLException, RemoteException;
}
