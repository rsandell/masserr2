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
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.model.auth.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-18 16:18:17
 *
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 */
@Entity
public class Experience implements Serializable {
    private int amount;
    private Date date;
    private String reason;
    @Reference
    private User setByUser;

    public Experience(int pAmount, Date pDate, String pReason) {
        amount = pAmount;
        date = pDate;
        reason = pReason;
    }

    public Experience(int pAmount, Date pDate, String pReason, User pSetByUser) {
        amount = pAmount;
        date = pDate;
        reason = pReason;
        setByUser = pSetByUser;
    }

    /**
     * Only for Serialization
     */
    public Experience() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int pAmmount) {
        amount = pAmmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date pDate) {
        date = pDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String pReason) {
        reason = pReason;
    }

    public User getSetByUser() {
        return setByUser;
    }

    public void setSetByUser(User pSetByUser) {
        setByUser = pSetByUser;
    }
}
