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

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import net.joinedminds.masserr.Functions;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-10 00:14:20
 *
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
@Entity
public class Ability implements NamedIdentifiable, Documented {
    @Id
    private ObjectId objectId;
    @Indexed
    private String name;
    @Indexed
    private Type type;
    //TODO private String specialisation;
    private String docUrl;
    private int baseMonthlyIncome;

    public Ability() {
    }

    public Ability(String id, String name, Type type) {
        this.objectId = new ObjectId(id);
        this.name = name;
        this.type = type;
        docUrl = null;
    }

    public Ability(String id, String pName, Type pType, int pBaseMonthlyIncome) {
        this.objectId = new ObjectId(id);
        name = pName;
        type = pType;
        baseMonthlyIncome = pBaseMonthlyIncome;
    }

    @DataBoundConstructor
    public Ability(String id, String name, Type type, String docUrl, int baseMonthlyIncome) {
        this.objectId = Functions.toObjectId(id);
        this.name = name;
        this.type = type;
        this.docUrl = docUrl;
        this.baseMonthlyIncome = baseMonthlyIncome;
    }

    public Ability(String id, String name, Type type, String docUrl) {
        this.objectId = new ObjectId(id);
        this.name = name;
        this.type = type;
        this.docUrl = docUrl;
    }

    public Ability(Ability ability) {
        this.objectId = new ObjectId(ability.getId());
        name = ability.getName();
        type = ability.getType();
        docUrl = ability.getDocUrl();
        baseMonthlyIncome = ability.getBaseMonthlyIncome();
    }

    public int getBaseMonthlyIncome() {
        return baseMonthlyIncome;
    }

    public void setBaseMonthlyIncome(int baseMonthlyIncome) {
        this.baseMonthlyIncome = baseMonthlyIncome;
    }

    @Override
    public String getId() {
        return Functions.toString(objectId);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String toString() {
        return getName();
    }

    public static Ability idRef(String id) {
        ObjectId oId = Functions.toObjectId(id);
        if(oId != null) {
            Ability a = new Ability();
            a.objectId = oId;
            return a;
        }
        return null;
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
