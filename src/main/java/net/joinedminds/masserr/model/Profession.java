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
import com.github.jmkgreen.morphia.annotations.Indexed;
import net.joinedminds.masserr.Functions;
import org.bson.types.ObjectId;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-feb-02 22:17:26
 *
 * @author <a href="sandell.robert@gmail.com">Robert Sandell</a>
 */
@Entity
public class Profession implements NamedIdentifiable {
    public static int MASK_INCOME_FACTOR = 10;

    @Id
    private ObjectId objectId;
    @Indexed
    private String name;
    private int monthlyIncome;
    private boolean mortal;
    private boolean mask;

    public Profession() {
    }

    public Profession(String pName, int pMonthlyIncome, boolean pMortal, boolean pMask) {
        name = pName;
        monthlyIncome = pMonthlyIncome;
        mortal = pMortal;
        mask = pMask;
    }

    public Profession(String pName, int pMonthlyIncome, boolean pMortal) {
        name = pName;
        monthlyIncome = pMonthlyIncome;
        mortal = pMortal;
        mask = false;
    }

    @Override
    public String getId() {
        return Functions.toString(objectId);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(int pMonthlyIncome) {
        monthlyIncome = pMonthlyIncome;
    }

    public boolean isMortal() {
        return mortal;
    }

    public void setMortal(boolean pMortal) {
        mortal = pMortal;
    }

    public boolean isMask() {
        return mask;
    }

    public void setMask(boolean pMask) {
        mask = pMask;
    }

    public String toString() {
        return name;
    }
}
