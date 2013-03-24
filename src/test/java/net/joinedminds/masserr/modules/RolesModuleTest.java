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

package net.joinedminds.masserr.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import net.joinedminds.masserr.GuiceModule;
import net.joinedminds.masserr.Masserr;
import net.joinedminds.masserr.model.Clan;
import net.joinedminds.masserr.model.Discipline;
import org.junit.*;

import javax.servlet.ServletContext;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class RolesModuleTest {

    static int port = 27017;
    static MongodConfig mongodConfig = null;
    static MongodProcess mongod = null;
    static MongodStarter runtime = MongodStarter.getDefaultInstance();
    static MongodExecutable mongodExecutable = null;
    static ServletContext context = null;
    static private Masserr masserr;
    static private RolesModule module;

    @BeforeClass
    public static void setUp() throws Exception {
        mongodConfig = new MongodConfig(Version.Main.V2_3, port, Network.localhostIsIPv6());
        mongodExecutable = runtime.prepare(mongodConfig);
        mongod = mongodExecutable.start();
        context = mock(ServletContext.class);
        Injector injector = Guice.createInjector(new GuiceModule(context, "localhost", "msrtst001", "", ""));
        masserr = injector.getInstance(Masserr.class);
        module = injector.getInstance(RolesModule.class);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @Test
    public void testGetClans() throws Exception {
        List<Clan> clans = module.getClans();
        assertFalse(clans.isEmpty());
        boolean found = false;
        for (Clan clan : clans) {
            if("Brujah".equals(clan.getName())) {
                found = true;
                break;
            }
        }
        assertTrue("Could not find clan Brujah", found);
    }

    @Test
    public void testGetDisciplines() throws Exception {
        List<Discipline> disciplines = module.getDisciplines();
        assertFalse(disciplines.isEmpty());
        boolean found = false;
        for (Discipline discipline : disciplines) {
            if("Potence".equals(discipline.getName())) {
                found = true;
                break;
            }
        }
        assertTrue("Could not find Potence", found);
    }

}
