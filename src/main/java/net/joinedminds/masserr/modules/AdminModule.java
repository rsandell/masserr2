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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.model.Ability;
import net.joinedminds.masserr.model.mgm.Config;
import net.joinedminds.masserr.ui.NavItem;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

import java.io.IOException;
import java.util.List;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
public class AdminModule implements NavItem {
    private ManipulationDB manipulationDb;
    private AdminDB adminDb;

    @Inject
    public AdminModule(ManipulationDB manipulationDb, AdminDB adminDb) {
        this.manipulationDb = manipulationDb;
        this.adminDb = adminDb;
    }

    @Override
    public String getNavDisplay() {
        return Messages.nav_Admin();
    }

    public List<Ability> getAbilities() {
        return manipulationDb.getAbilities();
    }

    @JavaScriptMethod
    public Ability getAbility(String id) {
        if (id != null && !id.isEmpty()) {
            return manipulationDb.getAbility(Functions.fromNavId(id));
        } else {
            return null;
        }
    }

    public Config getConfig() {
        return adminDb.getConfig();
    }

    @SuppressWarnings("unused") //Form post
    public void doConfigSubmit(StaplerRequest request, StaplerResponse response) throws IOException {
        Config config = getConfig();
        request.bindParameters(config);
        adminDb.saveConfig(config);
        response.sendRedirect2("../");
    }
}
