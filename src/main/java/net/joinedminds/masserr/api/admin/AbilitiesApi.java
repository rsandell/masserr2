/*
 * The MIT License
 *
 * Copyright (c) 2014-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.api.admin;

import com.github.jmkgreen.morphia.Datastore;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import net.joinedminds.masserr.api.ApiListResponse;
import net.joinedminds.masserr.model.Ability;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.export.ExportedBean;

import java.util.List;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
@ExportedBean
public class AbilitiesApi extends ApiListResponse<Ability> {

    private Provider<Datastore> db;

    public AbilitiesApi(Provider<Datastore> db) {
        this.db = db;
    }

    @Override
    protected List<Ability> createList() {
        return db.get().find(Ability.class).order("type,name").asList();
    }

    @Override
    protected Ability get(String id) {
        return db.get().get(Ability.class, new ObjectId(id));
    }
}
