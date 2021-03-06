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
import com.github.jmkgreen.morphia.annotations.Reference;
import net.joinedminds.masserr.Functions;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-11 14:44:54
 *
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
@Entity
public class Discipline implements NamedIdentifiable, Documented {
    @Id
    private ObjectId objectId;
    @Indexed
    private String name;
    @Reference
    private Ability retestAbility = null;
    private String docUrl;

    public Discipline() {
    }

    @DataBoundConstructor
    public Discipline(String id, String name, Ability retestAbility, String docUrl) {
        this.objectId = Functions.toObjectId(id);
        this.name = name;
        this.retestAbility = retestAbility;
        this.docUrl = docUrl;
    }

    public Discipline(String pName, Ability pRetestAbility) {
        name = pName;
        retestAbility = pRetestAbility;
    }

    public Discipline(String pName) {
        name = pName;
    }

    public Discipline(Discipline discipline) {
        this.objectId = new ObjectId(discipline.getId());
        this.name = discipline.getName();
        if (discipline.getRetestAbility() != null) {
            this.retestAbility = new Ability(discipline.getRetestAbility());
        }
        this.docUrl = discipline.getDocUrl();
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

    public String toString() {
        return name;
    }

    public Ability getRetestAbility() {
        return retestAbility;
    }

    public void setRetestAbility(Ability pRetestAbility) {
        retestAbility = pRetestAbility;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public static Discipline idRef(String id) {
        ObjectId oId = Functions.toObjectId(id);
        if (oId != null) {
            Discipline d = new Discipline();
            d.objectId = oId;
            return d;
        } else {
            return null;
        }
    }
}
