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

package net.joinedminds.masserr.db;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mongodb.Mongo;

import java.net.UnknownHostException;

/**
 * Guice provider for the thread instance of a {@link Morphia}.
 *
 * @author sandell.robert@gmail.com
 */
@Singleton
public class MorphiaProvider implements Provider<Datastore> {

    private final String dbHost;
    private final String dbName;
    private final String dbUser;
    private final String dbPasswd;
    private Morphia morphia = null;
    private Mongo mongo;
    private Datastore datastore;

    @Inject
    public MorphiaProvider(@Named("DB_HOST") String dbHost, @Named("DB_NAME") String dbName,
                           @Named("DB_USER") String dbUser, @Named("DB_PASSWD") String dbPasswd) throws UnknownHostException {
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPasswd = dbPasswd;
        startUp();
    }

    @Override
    public synchronized Datastore get() {
        return datastore;
    }

    private void startUp() throws UnknownHostException {
        mongo = new Mongo(dbHost);
        morphia = new Morphia();
        morphia.mapPackage("net.joinedminds.masserr.model", true);
        if (dbUser != null && !dbUser.isEmpty() && dbPasswd != null && !dbPasswd.isEmpty()) {
            datastore = morphia.createDatastore(mongo, dbName, dbUser, dbPasswd.toCharArray());
        } else {
            datastore = morphia.createDatastore(mongo, dbName);
        }
        datastore.ensureIndexes(); //creates indexes from @Index annotations in your entities
        datastore.ensureCaps(); //creates capped collections from @Entity
    }
}
