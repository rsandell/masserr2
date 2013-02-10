/*
 * The MIT License
 *
 * Copyright (c) 2004,2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr.db;

import net.joinedminds.masserr.model.*;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.List;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-15 12:42:11
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
public interface BankingDB extends BasicDB {

    StarterMoneyRule newStarterMoneyRule();

    StarterMoneyRule saveStarterMoneyRule(StarterMoneyRule rule);

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
