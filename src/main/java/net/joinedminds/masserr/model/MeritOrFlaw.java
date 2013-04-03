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
import net.joinedminds.masserr.Functions;
import org.bson.types.ObjectId;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-13 20:48:42
 *
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
@Entity
public class MeritOrFlaw implements NamedIdentifiable {

    @Id
    private ObjectId objectId;
    private String name;
    private Type type;
    private int points;

    public MeritOrFlaw() {
    }

    public MeritOrFlaw(String pName, Type type, int pPoints) {
        name = pName;
        this.type = type;
        points = pPoints;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int pPoints) {
        points = pPoints;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String toString() {
        return getName() + " (" + getPoints() + ")";
    }

    public static enum Type {
        Physical('P'), Social('S'), Mental('M'), Supernatural('U');
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
