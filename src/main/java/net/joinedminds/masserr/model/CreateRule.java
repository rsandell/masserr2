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

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-feb-02 10:01:20
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class CreateRule implements Identifiable {

    @Id
    private String id;
    private int yearMin;
    private int yearMax;
    private int disciplines;
    private int attributes;
    private int abilities;

	public CreateRule() {
	}

	public CreateRule(int pYearMin, int pYearMax, int pDisciplines, int pAttributes, int pAbilities) {
		yearMin = pYearMin;
		yearMax = pYearMax;
		disciplines = pDisciplines;
		attributes = pAttributes;
		abilities = pAbilities;
	}

	@Override
    public String getId() {
		return id;
	}

	public int getYearMin() {
		return yearMin;
	}

	public void setYearMin(int pYearMin) {
		yearMin = pYearMin;
	}

	public int getYearMax() {
		return yearMax;
	}

	public void setYearMax(int pYearMax) {
		yearMax = pYearMax;
	}

	public int getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(int pDisciplines) {
		disciplines = pDisciplines;
	}

	/**
	 * a.k. Traits
	 * @return
	 */
	public int getAttributes() {
		return attributes;
	}

	/**
	 * a.k. Traits
	 * @param pAttributes
	 */
	public void setAttributes(int pAttributes) {
		attributes = pAttributes;
	}

	public int getAbilities() {
		return abilities;
	}

	public void setAbilities(int pAbilities) {
		abilities = pAbilities;
	}
}
