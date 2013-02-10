/*
 * The MIT License
 *
 * Copyright (c) 2004,2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 00:14:20
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
public class Ability implements NamedIdentifiable  {

	@Id
    private String id;
	private String name;
	private Type type;
	private String specialisation;
	private int baseMonthlyIncome;

	public Ability() {
	}

	public Ability(String pId, String pName, Type pType) {
		id = pId;
		name = pName;
		type = pType;
		specialisation = null;
	}

	public Ability(String pId, String pName, Type pType, int pBaseMonthlyIncome) {
		id = pId;
		name = pName;
		type = pType;
		baseMonthlyIncome = pBaseMonthlyIncome;
	}

	public Ability(String pId, String pName, Type pType, String pSpecialisation, int pBaseMonthlyIncome) {
		id = pId;
		name = pName;
		specialisation = pSpecialisation;
		baseMonthlyIncome = pBaseMonthlyIncome;
	}

	public Ability(String pId, String pName, Type pType, String pSpecialisation) {
		id = pId;
		name = pName;
		type = pType;
		specialisation = pSpecialisation;
	}

	public int getBaseMonthlyIncome() {
		return baseMonthlyIncome;
	}

	public void setBaseMonthlyIncome(int pBaseMonthlyIncome) {
		baseMonthlyIncome = pBaseMonthlyIncome;
	}

	@Override
    public String getId() {
		return id;
	}

	@Override
    public String getName() {
		return name;
	}

	public void setName(String pName) {
		name = pName;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type pType) {
		type = pType;
	}

	public String toString() {
		return name;
	}

	public String getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(String pSpecialisation) {
		specialisation = pSpecialisation;
	}

    public static enum Type {
        Physical('P'),
        Social('S'),
        Mental('M');

        private char sign;

        Type(char sign) {
            this.sign = sign;
        }

        public char getSign() {
            return sign;
        }

        public static Type findByChar(char sign) {
            for (Type t : Type.values()) {
                if (t.getSign() == sign) {
                    return t;
                }
            }
            return null;
        }
    }
}
