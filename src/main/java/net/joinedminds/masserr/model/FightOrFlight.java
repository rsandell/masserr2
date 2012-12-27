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
 * Description.
 * <p/>
 * Created: 2004-maj-14 22:29:57
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class FightOrFlight implements NamedIdentifiable {
    @Id
    private String id;
    private String name;
    private String description;

    public FightOrFlight(String pName, String pDescription) {
        name = pName;
        description = pDescription;
    }

    public FightOrFlight(String pName) {
        name = pName;
        description = "";
    }

    public boolean equals(Object obj) {
        return equals((FightOrFlight)obj);
    }

    public boolean equals(FightOrFlight pFightOrFlight) {
        if (pFightOrFlight == null) {
            return false;
        }
        else {
            return id.equals(pFightOrFlight.id);
        }
    }

    /**
     * For Serialization
     */
    public FightOrFlight() {
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

    public String toString() {
        return name;
    }
}
