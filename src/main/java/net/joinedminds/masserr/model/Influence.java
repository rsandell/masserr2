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
 * Created: 2004-feb-03 00:43:53
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Influence implements Serializable, Cloneable {

    @Id
    private String id;
    private String name;
    private int dots;
    private String notes;

    public Influence() {
    }

    public Influence(String pId, String pName, int pDots) {
        id = pId;
        name = pName;
        dots = pDots;
    }

    public Influence(String pId, String pName, int pDots, String pNotes) {
        id = pId;
        name = pName;
        dots = pDots;
        notes = pNotes;
    }

    public Influence(String pId, String pName) {
        id = pId;
        name = pName;
        dots = 0;
    }

    public Object clone()  {
        return new Influence(id, name, dots, notes);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String pNotes) {
        notes = pNotes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public int getDots() {
        return dots;
    }

    public void setDots(int pDots) {
        dots = pDots;
    }

    public String toString() {
        String str = name;
        if (dots > 0) {
            str += " " + dots;
        }
        if (notes != null && notes.length() > 0) {
            str += " (" + notes + ")";
        }
        return str;
    }
}
