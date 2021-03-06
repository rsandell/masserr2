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

package net.joinedminds.masserr.db.impl;

import com.github.jmkgreen.morphia.Datastore;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.joinedminds.masserr.db.CreateRulesDB;
import net.joinedminds.masserr.model.CreateRule;

import java.util.List;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class CreateRulesDbImpl extends BasicDbImpl implements CreateRulesDB {


    @Inject
    public CreateRulesDbImpl(Provider<Datastore> db) {
        super(db);
    }

    @Override
    public CreateRule getRule(int pYear) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CreateRule getRule(String pYear) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CreateRule> getRules() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CreateRule newRule() {
        return new CreateRule();
    }

    @Override
    public CreateRule saveRule(CreateRule rule) {
        return save(rule);
    }
}
