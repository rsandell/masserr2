/*
 * The MIT License
 *
 * Copyright (c) 2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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
import net.joinedminds.masserr.util.IdentifiableBeanConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Entity
public class Morality implements NamedIdentifiable {

    @Id
    private ObjectId objectId;
    private String name;
    private Virtues.Adherence adherenceTeachings;
    private Virtues.Resistance resistanceTeachings;

    public Morality(String id, String name, Virtues.Adherence adherenceTeachings, Virtues.Resistance resistanceTeachings) {
        this.objectId = Functions.toObjectId(id);
        this.name = name;
        this.adherenceTeachings = adherenceTeachings;
        this.resistanceTeachings = resistanceTeachings;
    }

    @DataBoundConstructor
    public Morality(String id) {
        this.objectId = Functions.toObjectId(id);
    }

    public Morality() {
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Virtues.Adherence getAdherenceTeachings() {
        return adherenceTeachings;
    }

    public void setAdherenceTeachings(Virtues.Adherence adherenceTeachings) {
        this.adherenceTeachings = adherenceTeachings;
    }

    public Virtues.Resistance getResistanceTeachings() {
        return resistanceTeachings;
    }

    public void setResistanceTeachings(Virtues.Resistance resistanceTeachings) {
        this.resistanceTeachings = resistanceTeachings;
    }

    @Override
    public String getId() {
        return Functions.toString(objectId);
    }

    public static Morality idRef(String id) {
        ObjectId id1 = Functions.toObjectId(id);
        if(id1 == null) {
            return null;
        } else {
            Morality m = new Morality();
            m.objectId = id1;
            return m;
        }
    }

    static {
        BeanUtilsBean.getInstance().getConvertUtils().register(new IdentifiableBeanConverter(), Morality.class);
    }
}
