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

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

import java.text.NumberFormat;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-feb-06 13:48:10
 *
 * @author <a href="sandell.robert@gmail.com">Robert Sandell</a>
 */
@Entity
public class BankAccount implements Identifiable {
    @Id
    private String id;
    private String ownerName;
    private double amount;
    private boolean income;
    private boolean active;

    /**
     * For serialization
     */
    public BankAccount() {
    }

    public BankAccount(String pOwnerName, double pAmount, boolean pIncome, boolean pActive) {
        ownerName = pOwnerName;
        amount = pAmount;
        income = pIncome;
        active = pActive;
    }

    public BankAccount(String pOwnerName, float pAmount, boolean pIncome) {
        ownerName = pOwnerName;
        amount = pAmount;
        income = pIncome;
        active = true;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String pOwnerName) {
        ownerName = pOwnerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double pAmount) {
        amount = pAmount;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean pIncome) {
        income = pIncome;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean pActive) {
        active = pActive;
    }

    public String toString() {
        String str = "";
        str = id + ": ";
        str += ownerName + " ( " + NumberFormat.getCurrencyInstance().format(amount) + " )";
        return str;
    }
}
