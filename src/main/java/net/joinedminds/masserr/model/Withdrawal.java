/*
 * The MIT License
 *
 * Copyright (c) 2004-2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.model;

import java.text.NumberFormat;

/**
 * Description
 *
 * 
 * Created: 2004-feb-13 17:41:20
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Withdrawal implements java.io.Serializable, Cloneable {

    private static final NumberFormat format = NumberFormat.getCurrencyInstance();
    private BankAccount bankAccount;
	private long amount;


	public Withdrawal(BankAccount pBankAccount, long pAmount) {
		bankAccount = pBankAccount;
		amount = pAmount;
	}

    /**
     * For serialization
     */
    public Withdrawal() {
    }

    public Object clone() {
        return new Withdrawal((BankAccount) bankAccount.clone(), amount);
    }

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount pBankAccount) {
		bankAccount = pBankAccount;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long pAmount) {
		amount = pAmount;
	}

	public String toString() {
		return format.format(amount) + " from " + bankAccount.toString();
	}
}
