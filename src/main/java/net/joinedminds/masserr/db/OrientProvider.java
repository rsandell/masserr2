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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

import java.io.File;

/**
 * Guice provider for the thread instance of a {@link OObjectDatabaseTx}.
 *
 * @author sandell.robert@gmail.com
 */
@Singleton
public class OrientProvider implements Provider<OObjectDatabaseTx> {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPasswd;

    ThreadLocal<OObjectDatabaseTx> context = new ThreadLocal<OObjectDatabaseTx>() {
        @Override
        protected OObjectDatabaseTx initialValue() {
            // OPEN THE DATABASE
            OObjectDatabaseTx db = OObjectDatabasePool.global().acquire(dbUrl, dbUser, dbPasswd);
            // REGISTER THE CLASS ONLY ONCE AFTER THE DB IS OPEN/CREATED
            db.getEntityManager().registerEntityClasses("net.joinedminds.masserr.model");
            return db;
        }

        @Override
        public void remove() {
            get().close();
        }
    };

    @Inject
    public OrientProvider(@Named("DB_URL") String dbUrl, @Named("DB_USER") String dbUser, @Named("DB_PASSWD") String dbPasswd) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPasswd = dbPasswd;
        setUpDbIfNeeded();
    }

    private void setUpDbIfNeeded() {
        if (dbUrl.startsWith("local:")) {
            System.out.println("Local db!");
            File file = new File(dbUrl.substring("local:".length()));
            if(!file.exists()) {
                System.out.println("Creating the database at " + file.getAbsolutePath());
                OObjectDatabaseTx db = new OObjectDatabaseTx(dbUrl).create();
                db.close();
            }
        }
    }

    @Override
    public OObjectDatabaseTx get() {
        return context.get();
    }
}
