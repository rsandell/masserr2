package se.tdt.bobby.wodcc.server.db;

import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;
import se.tdt.bobby.wodcc.data.mgm.UserRights;
import se.tdt.bobby.wodcc.remote.db.RemoteBankingDB;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-feb-06 13:44:43
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class BankingDBImpl extends BasicDBImpl implements RemoteBankingDB {

    public BankingDBImpl() throws RemoteException {
        super();
    }

    public boolean hasAccount(int pRoleId) throws SQLException {
        boolean has = true;
        connect();
        ResultSet rs = query("SELECT COUNT(id) > 0 FROM bank_accounts WHERE owner = " + pRoleId);
        if (rs.next()) {
            has = rs.getBoolean(1);
        }
        disconnect();
        return has;
    }

    private static final String CREATE_ACCOUNT = "INSERT INTO bank_accounts (owner, owner_name, ammount, income, active) " +
            "VALUES ({0}, '{1}', {2}, {3}, {4})";

    public void createAccount(Role pRoleOwner, BankAccount pBankAccount) throws SQLException {
        if (mLoginUser.getDomain().equals(pRoleOwner.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.CREATE_BANKACCOUNT)) {
                throw new OperationDeniedException("You do not have permission to create a bank account in Domain " + pRoleOwner.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.CREATE_BANKACCOUNT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to create a bank account in Domain " + pRoleOwner.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRoleOwner.getDomain())) {
                throw new OperationDeniedException("You do not have permission to create a bank account in Domain " + pRoleOwner.getDomain());
            }
        }
        connect();
        ArrayList par = new ArrayList(5);
        par.add(pRoleOwner.getId() + "");
        par.add(pBankAccount.getOwnerName());
        par.add(pBankAccount.getAmmount() + "");
        par.add(String.valueOf(pBankAccount.isIncome()));
        par.add(String.valueOf(pBankAccount.isActive()));
        int result = update(replaceInString(CREATE_ACCOUNT, par));
        if (DEBUG) System.out.println("[BankingDB][createAccount][42] result: " + result);
        disconnect();
    }

    public void setIncome(Role pSelectedRole, BankAccount pAccount) throws SQLException {
        if (mLoginUser.getDomain().equals(pSelectedRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_BANKACCOUNT)) {
                throw new OperationDeniedException("You do not have permission to update a bank account in Domain " + pSelectedRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.UPDATE_BANKACCOUNT_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to update a bank account in Domain " + pSelectedRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pSelectedRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to update a bank account in Domain " + pSelectedRole.getDomain());
            }
        }
        connect();
        mStatement.addBatch("UPDATE bank_accounts " +
                            "SET income = false " +
                            "WHERE owner = " + pSelectedRole.getId());
        mStatement.addBatch("UPDATE bank_accounts " +
                            "SET income = true " +
                            "WHERE owner = " + pSelectedRole.getId() + " AND id = " + pAccount.getId());
        mStatement.executeBatch();
        disconnect();
    }

    public void setExtraMonthlyIncome(Role pRole, long pExtraMonthlyIncome) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.SET_EXTRA_INCOME)) {
                throw new OperationDeniedException("You do not have permission to set Extra Income for Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.SET_EXTRA_INCOME_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to set Extra Income for Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to set Extra Income for Roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        int rows = update("UPDATE roles SET extra_monthly_income = " + pExtraMonthlyIncome + " " +
                          "WHERE id = " + pRole.getId());
        if (DEBUG) System.out.println("[BankingDB][setExtraMonthlyIncome][63] rows: " + rows);
        disconnect();
    }

    protected static int BANK_OF_INFLUENCES_ID = 1;

    public void buyResource(Role pRole, BankAccount pBankAccount, Resource pResource) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.BUY_OR_SELL_RESOURCE)) {
                throw new OperationDeniedException("You do not have permission to buy Resources for Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.BUY_OR_SELL_RESOURCE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to buy Resources for Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to buy Resources for Roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        String q = "INSERT INTO roleResources(resource_id, role_id, [percent]) VALUES (" +
                pResource.getId() + ", " + pRole.getId() + ", " + pResource.getPercent() + ")";
        if (DEBUG) System.out.println("[BankingDB][buyResource][72] " + q);
        mStatement.addBatch(q);
        float cost = pResource.getCost() * pResource.getPercent();
        if (DEBUG) System.out.println("[BankingDB][buyResource][72] cost: " + cost);
        String message = "Bought " + pResource.getPercent() + "% of " + pResource.getName();
        q = "INSERT INTO bank_transactions (from_account, to_account, ammount, message) VALUES( " +
                pBankAccount.getId() + ", " + BANK_OF_INFLUENCES_ID + ", " + cost + ", '" + message + "')";
        if (DEBUG) System.out.println("[BankingDB][buyResource][78] " + q);
        mStatement.addBatch(q);

        /*double left = pBankAccount.getAmmount() - cost;
        if (DEBUG) System.out.println("[BankingDB][buyResource][78] left: " + left);*/
        q = "UPDATE bank_accounts SET ammount = ammount - " + cost + " WHERE id=" + pBankAccount.getId();
        if (DEBUG) System.out.println("[BankingDB][buyResource][85] " + q);
        mStatement.addBatch(q);
        mStatement.executeBatch();
        disconnect();
    }

    public void buyMoreOrSellResource(Role pRole, BankAccount pBankAccount, Resource pResource) throws SQLException {
        if (mLoginUser.getDomain().equals(pRole.getDomain())) {
            if (!mLoginUser.getUserRights().get(UserRights.BUY_OR_SELL_RESOURCE)) {
                throw new OperationDeniedException("You do not have permission to buy or sell Resources for Roles in Domain " + pRole.getDomain());
            }
        }
        else {
            if (!mLoginUser.getUserRights().get(UserRights.BUY_OR_SELL_RESOURCE_OTHER_DOMAIN)) {
                throw new OperationDeniedException("You do not have permission to buy or sell Resources for Roles in Domain " + pRole.getDomain());
            }
            else if (!mLoginUser.hasDomain(pRole.getDomain())) {
                throw new OperationDeniedException("You do not have permission to buy or sell Resources for Roles in Domain " + pRole.getDomain());
            }
        }
        connect();
        String q = "UPDATE roleResources set [percent] = [percent] + " + pResource.getPercent() + " " +
                "WHERE resource_id = " + pResource.getId() + " AND role_id = " + +pRole.getId();
        if (DEBUG) System.out.println("[BankingDB][buyMoreOrSellResource][97] " + q);
        mStatement.addBatch(q);
        float cost = pResource.getCost() * pResource.getPercent();

        String message;
        if (cost >= 0) {
            message = "Bought " + pResource.getPercent() + "% of " + pResource.getName();
            q = "INSERT INTO bank_transactions (from_account, to_account, ammount, message) VALUES( " +
                    pBankAccount.getId() + ", " + BANK_OF_INFLUENCES_ID + ", " + cost + ", '" + message + "')";
        }
        else {
            message = "Sold " + pResource.getPercent() + "% of " + pResource.getName();
            q = "INSERT INTO bank_transactions (from_account, to_account, ammount, message) VALUES( " +
                    BANK_OF_INFLUENCES_ID + ", " + pBankAccount.getId() + ", " + (-cost) + ", '" + message + "')";
        }
        if (DEBUG) System.out.println("[BankingDB][buyMoreOrSellResource][112] " + q);
        mStatement.addBatch(q);

        /*double left = pBankAccount.getAmmount() - cost;
        if (DEBUG) System.out.println("[BankingDB][buyResource][78] left: " + left);*/
        q = "UPDATE bank_accounts SET ammount = ammount - " + cost + " WHERE id=" + pBankAccount.getId();
        if (DEBUG) System.out.println("[BankingDB][buyMoreOrSellResource][118] " + q);
        mStatement.addBatch(q);
        mStatement.executeBatch();
        disconnect();
    }

    private void depositItToBatch(Withdrawal pWithdrawal, BankAccount pToAccount, String pMessage) throws SQLException {
        String q = "INSERT INTO bank_transactions (from_account, to_account, ammount, message) VALUES( " +
                pWithdrawal.getBankAccount().getId() + ", " + pToAccount.getId() + ", " + pWithdrawal.getAmmount() + ", '" + pMessage + "')";
        if (DEBUG) System.out.println("[BankingDB][depositItToBatch][127] " + q);
        mStatement.addBatch(q);
        q = "UPDATE bank_accounts SET ammount = ammount + " + pWithdrawal.getAmmount() + " WHERE id=" + pToAccount.getId();
        if (DEBUG) System.out.println("[BankingDB][depositItToBatch][130] " + q);
        mStatement.addBatch(q);
        q = "UPDATE bank_accounts SET ammount = ammount - " + pWithdrawal.getAmmount() + " WHERE id=" + pWithdrawal.getBankAccount().getId();
        if (DEBUG) System.out.println("[BankingDB][depositItToBatch][133] " + q);
        mStatement.addBatch(q);
    }

    /*
    * TODO withdrawal permission check
    *
    */
    public void deposit(Withdrawal pWithdrawal, BankAccount pToAccount, String pMessage) throws SQLException {
        if (pWithdrawal.getBankAccount().getId() != BANK_OF_INFLUENCES_ID) {
            Domain currentDomain = getCurrentDomainForBankAccount(pWithdrawal.getBankAccount());
            if (mLoginUser.getDomain().equals(currentDomain)) {
                if (!mLoginUser.getUserRights().get(UserRights.BANKACCOUNT_WITHDRAWAL)) {
                    throw new OperationDeniedException("You do not have permission to make a withdrawal from Back-Accounts in Domain " + currentDomain);
                }
            }
            else {
                if (!mLoginUser.getUserRights().get(UserRights.BANKACCOUNT_WITHDRAWAL_OTHER_DOMAIN)) {
                    throw new OperationDeniedException("You do not have permission to make a withdrawal from Back-Accounts in Domain " + currentDomain);
                }
                else if (!mLoginUser.hasDomain(currentDomain)) {
                    throw new OperationDeniedException("You do not have permission to make a withdrawal from Back-Accounts in Domain " + currentDomain);
                }
            }
        }
        connect();
        depositItToBatch(pWithdrawal, pToAccount, pMessage);
        mStatement.executeBatch();
        disconnect();
    }

    private Domain getCurrentDomainForBankAccount(BankAccount pBankAccount) throws SQLException {
        Domain dom = null;
        connect();
        String q = "SELECT domains.id, domains.name " +
                "FROM roles, domains, bank_accounts " +
                "WHERE bank_accounts.owner = roles.id AND roles.domain = domains.id " +
                "AND bank_accounts.id = " + pBankAccount.getId();
        ResultSet rs = query(q);
        if (rs.next()) {
            dom = new Domain(rs.getInt(1), rs.getString(2));
        }
        disconnect();
        return dom;
    }

    private List<Domain> getCurrentDomainsForBankAccounts(Vector pWithdrawals) throws SQLException {
        List<Domain> domains = new ArrayList<Domain>();
        connect();
        String q = "SELECT DISTINCT domains.id, domains.name " +
                "FROM roles, domains, bank_accounts " +
                "WHERE bank_accounts.owner = roles.id AND roles.domain = domains.id " +
                "AND (";
        for (int i = 0; i < pWithdrawals.size(); i++) {
            Withdrawal withdrawal = (Withdrawal) pWithdrawals.elementAt(i);
            if (i != 0) {
                q += " OR ";
            }
            q += "bank_accounts.id = " + withdrawal.getBankAccount().getId();
        }
        q += ")";
        ResultSet rs = query(q);
        while (rs.next()) {
            Domain dom = new Domain(rs.getInt(1), rs.getString(2));
            domains.add(dom);
        }
        disconnect();
        return domains;
    }

    public void deposit(Vector pWithdrawals, BankAccount pToAccount, String pMessage) throws SQLException {
        /*************** Permission Check *******************/
        List<Domain> currentDomains = getCurrentDomainsForBankAccounts(pWithdrawals);
        List<Domain> invalidDomains = new ArrayList<Domain>(currentDomains.size());
        for (int i = 0; i < currentDomains.size(); i++) {
            Domain domain = currentDomains.get(i);
            if (mLoginUser.getDomain().equals(domain)) {
                if (!mLoginUser.getUserRights().get(UserRights.BANKACCOUNT_WITHDRAWAL)) {
                    invalidDomains.add(domain);
                }
            }
            else {
                if (!mLoginUser.getUserRights().get(UserRights.BANKACCOUNT_WITHDRAWAL_OTHER_DOMAIN)) {
                    invalidDomains.add(domain);
                }
                else if (!mLoginUser.hasDomain(domain)) {
                    invalidDomains.add(domain);
                }
            }
        }
        if (invalidDomains.size() > 0) {
            if (DEBUG) System.out.println("BankingDBImpl.deposit(276) invalid size: " + invalidDomains.size());
            String domains = "";
            for (int i = 0; i < invalidDomains.size(); i++) {
                Domain domain = invalidDomains.get(i);
                if (i != 0 && i < invalidDomains.size() - 1) {
                    domains += ", ";
                }
                else if (i != 0 && i == invalidDomains.size() - 1) {
                    domains += " and ";
                }
                domains += domain.getName();
            }
            throw new OperationDeniedException("You do not have permission to make a withdrawal from Back-Accounts in Domain " + domains);
        }
        /*************** /Permission Check *******************/
        connect();
        for (int i = 0; i < pWithdrawals.size(); i++) {
            Withdrawal withdrawal = (Withdrawal) pWithdrawals.elementAt(i);
            depositItToBatch(withdrawal, pToAccount, pMessage);
        }
        mStatement.executeBatch();
        disconnect();
    }

    public void depositFromBank(BankAccount pToAccount, long pAmmount, String pMessage) throws SQLException {
        //TODO Permission check! 
        connect();
        String q = "INSERT INTO bank_transactions (from_account, to_account, ammount, message) VALUES( " +
                BANK_OF_INFLUENCES_ID + ", " + pToAccount.getId() + ", " + pAmmount + ", '" + pMessage + "')";
        if (DEBUG) System.out.println("[BankingDB][depositFromBank][159] " + q);
        mStatement.addBatch(q);
        q = "UPDATE bank_accounts SET ammount = ammount + " + pAmmount + " WHERE id=" + pToAccount.getId();
        if (DEBUG) System.out.println("[BankingDB][depositFromBank][162] " + q);
        mStatement.addBatch(q);
        mStatement.executeBatch();
        disconnect();
    }

    public List getBankAccountsForRole(int pRoleId) throws SQLException {
        ArrayList list = new ArrayList();
        connect();
        String q = "SELECT id, owner_name, ammount, income, active " +
                "FROM bank_accounts " +
                "WHERE owner = " + pRoleId + " AND active = true" +
                " ORDER BY income, owner_name";
        ResultSet rs = query(q);
        while (rs.next()) {
            BankAccount ba = new BankAccount(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getBoolean(4), rs.getBoolean(5));
            list.add(ba);
        }
        disconnect();
        return list;
    }
}
