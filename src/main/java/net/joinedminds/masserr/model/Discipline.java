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
 * Created: 2004-jan-11 14:44:54
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
public class Discipline implements NamedIdentifiable  {
	@Id
    private String id;
	private String name;
	private boolean offClan;
	private Ability retestAbility = null;

	public Discipline() {
	}

	public Discipline(String pName, boolean pOfClan, Ability pRetestAbility) {
		name = pName;
		offClan = pOfClan;
		retestAbility = pRetestAbility;
	}

	public Discipline(String pName, boolean pOfClan) {
		name = pName;
		offClan = pOfClan;
	}

	public Discipline(String pName, Ability pRetestAbility) {
		name = pName;
		retestAbility = pRetestAbility;
		offClan = false;
	}

	public Discipline(String pName) {
		name = pName;
		offClan = false;
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

	public boolean isOffClan() {
		return offClan;
	}


	public void setOffClan(boolean pOfClan) {
		offClan = pOfClan;
	}

	public String toString() {
		return name;
	}

	public Ability getRetestAbility() {
		return retestAbility;
	}

	public void setRetestAbility(Ability pRetestAbility) {
		retestAbility = pRetestAbility;
	}
}
