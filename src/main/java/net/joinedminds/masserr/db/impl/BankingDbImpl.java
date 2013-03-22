package net.joinedminds.masserr.db.impl;

import com.github.jmkgreen.morphia.Datastore;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.joinedminds.masserr.db.BankingDB;
import net.joinedminds.masserr.model.*;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class BankingDbImpl extends BasicDbImpl implements BankingDB {

    @Inject
    public BankingDbImpl(Provider<Datastore> db) {
        super(db);
    }

    @Override
    public StarterMoneyRule newStarterMoneyRule() {
        return new StarterMoneyRule();
    }

    @Override
    public StarterMoneyRule saveStarterMoneyRule(StarterMoneyRule rule) {
        return save(rule);
    }

    @Override
    public boolean hasAccount(int pRoleId) throws SQLException, RemoteException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void createAccount(Role pRoleOwner, BankAccount pBankAccount) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setIncome(Role pSelectedRole, BankAccount pAccount) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setExtraMonthlyIncome(Role pRole, long pExtraMonthlyIncome) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buyResource(Role pRole, BankAccount pBankAccount, Resource pResource) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buyMoreOrSellResource(Role pRole, BankAccount pBankAccount, Resource pResource) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deposit(Withdrawal pWithdrawal, BankAccount pToAccount, String pMessage) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deposit(Vector pWithdrawals, BankAccount pToAccount, String pMessage) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void depositFromBank(BankAccount pToAccount, long pAmmount, String pMessage) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List getBankAccountsForRole(int pRoleId) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
