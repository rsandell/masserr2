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

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 01:27:51
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"generation"}))
public class Generation implements Identifiable {
    private int generation;
	private int bloodPool;
	private int spendBlood;
    private int abilitiesMax;
    private int disciplinesMax;
    private int traitsMax;
    private int willpowerStart;
    private int willpowerMax;
    private int humanBlood;

    public Generation() {
	}

	public Generation(int pGeneration, int pBloodPool, int pSpendBlood, int pAbilitiesMax, int pDisciplinesMax, int pTraitsMax, int pWillpowerStart, int pWillpowerMax, int pHumanBlood) {
		generation = pGeneration;
		bloodPool = pBloodPool;
		spendBlood = pSpendBlood;
		abilitiesMax = pAbilitiesMax;
		disciplinesMax = pDisciplinesMax;
		traitsMax = pTraitsMax;
		willpowerStart = pWillpowerStart;
		willpowerMax = pWillpowerMax;
        humanBlood = pHumanBlood;
    }

	public int getGeneration() {
		return generation;
	}

	public int getBloodPool() {
		return bloodPool;
	}

	public int getSpendBlood() {
		return spendBlood;
	}

	public int getAbilitiesMax() {
		return abilitiesMax;
	}

	public int getDisciplinesMax() {
		return disciplinesMax;
	}

	public int getTraitsMax() {
		return traitsMax;
	}

	public int getWillpowerStart() {
		return willpowerStart;
	}

	public int getWillpowerMax() {
		return willpowerMax;
	}


	public void setGeneration(int pGeneration) {
		generation = pGeneration;
	}

	public void setBloodPool(int pBloodPool) {
		bloodPool = pBloodPool;
	}

	public void setSpendBlood(int pSpendBlood) {
		spendBlood = pSpendBlood;
	}

	public void setAbilitiesMax(int pAbilitiesMax) {
		abilitiesMax = pAbilitiesMax;
	}

	public void setDisciplinesMax(int pDisciplinesMax) {
		disciplinesMax = pDisciplinesMax;
	}

	public void setTraitsMax(int pTraitsMax) {
		traitsMax = pTraitsMax;
	}

	public void setWillpowerStart(int pWillpowerStart) {
		willpowerStart = pWillpowerStart;
	}

	public void setWillpowerMax(int pWillpowerMax) {
		willpowerMax = pWillpowerMax;
	}

    public int getHumanBlood() {
        return humanBlood;
    }

    public void setHumanBlood(int pHumanBlood) {
        humanBlood = pHumanBlood;
    }

	public String toString() {
		return String.valueOf(generation);
	}

    /**
     * The generation actually.
     *
     * @return the "id"
     */
    @Override
    public String getId() {
        return String.valueOf(generation);
    }
}
