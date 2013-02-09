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
import java.util.List;
import java.util.ArrayList;


/**
 * Description
 *
 * 
 * Created: 2004-feb-03 00:34:49
 * @author <a href="sandell.robert@gmail.com">Robert Sandell</a>
 */
public class Resource implements NamedIdentifiable {

    @Id
    private String id;
	private String name;
    private String description;
    private int income;
	private int percent;
	private List<DottedType<Influence>> influences;
	private float cost;
    private String type;
    private Domain domain;

	public Resource() {
	}

	public Resource(String pName, String pDescription, int pIncome, int pPercent, float pCost, String pType) {
		name = pName;
		description = pDescription;
		income = pIncome;
		percent = pPercent;
		cost = pCost;
        type = pType;
    }

    public Resource(String pName, String pDescription, int pIncome, int pPercent,
                    List<DottedType<Influence>> pInfluences, float pCost, Domain pDomain, String pType) {
        name = pName;
        description = pDescription;
        income = pIncome;
        percent = pPercent;
        influences = pInfluences;
        cost = pCost;
        domain = pDomain;
        type = pType;
    }


	public Resource(String pName, String pDescription, int pIncome, float pCost, String pType) {
		name = pName;
		description = pDescription;
		income = pIncome;
		cost = pCost;
        type = pType;
        percent = 0;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String pDescription) {
		description = pDescription;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int pIncome) {
		income = pIncome;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int pPercent) {
		percent = pPercent;
	}

	public String toString() {
		return name;
	}

	public void setInfluences(List<DottedType<Influence>> pInfluences) {
		influences = pInfluences;
	}

	public List<DottedType<Influence>> getInfluences() {
		return influences;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float pCost) {
		cost = pCost;
	}

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain pDomain) {
        domain = pDomain;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }
}
