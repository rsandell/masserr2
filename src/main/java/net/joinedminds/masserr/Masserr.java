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
import net.joinedminds.masserr.dataimport.Importer;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.ui.NavItem;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Main class for the application.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class Masserr implements NavItem {

    private static final Logger logger = Logger.getLogger(Masserr.class.getName());

    private ManipulationDB manipulationDB;
    private Importer importer;

    @Inject
    public Masserr(ManipulationDB manipulationDB, Importer importer) throws ParserConfigurationException, SAXException, IOException {
        this.manipulationDB = manipulationDB;
        this.importer = importer;

        if (manipulationDB.isEmpty()) {
            logger.info("Starting first data input.");
            importer.importAll();
        }
    }

    @Override
    public String getNavDisplay() {
        return Messages.nav_Home();
    }
}
