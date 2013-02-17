/*
 * The MIT License
 *
 * Copyright (c) 2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.joinedminds.masserr.dataimport.Importer;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.modules.AdminModule;
import net.joinedminds.masserr.ui.NavItem;
import org.kohsuke.stapler.framework.adjunct.AdjunctManager;

import javax.servlet.ServletContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for the application.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
public class Masserr implements NavItem {

    private static final Logger logger = Logger.getLogger(Masserr.class.getName());
    private static Masserr instance = null;

    private ServletContext context;
    private AdminModule admin;
    private ManipulationDB manipulationDb;
    private AdminDB adminDb;
    private Importer importer;
    public final AdjunctManager adjuncts;

    @Inject
    public Masserr(ServletContext context, AdminModule admin, ManipulationDB manipulationDb, AdminDB adminDb, Importer importer) throws Exception {
        this.context = context;
        adjuncts = new AdjunctManager(context, getClass().getClassLoader(), "/adjuncts");
        this.admin = admin;
        this.manipulationDb = manipulationDb;
        this.adminDb = adminDb;
        this.importer = importer;

        if (instance != null) {
            throw new IllegalStateException("Second Instance!");
        }
        instance = this;

        if (manipulationDb.isEmpty()) {
            logger.info("Starting first data input.");
            try {
                importer.importAll();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to first-time-initialize the database", e);
                throw new Exception("Failed to first-time-initialize the database", e);
            }
        }
    }

    public String getAppName() {
        return adminDb.getConfig().getAppName();
    }

    @Override
    public String getNavDisplay() {
        return Messages.nav_Home();
    }

    public AdminModule getAdmin() {
        return admin;
    }

    public static Masserr getInstance() {
        return instance;
    }

    public AdjunctManager getAdjuncts() {
        return adjuncts;
    }
}
