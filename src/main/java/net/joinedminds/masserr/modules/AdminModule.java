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
import net.joinedminds.masserr.model.OtherTrait;
import net.joinedminds.masserr.model.mgm.Config;
import net.joinedminds.masserr.ui.NavItem;
import net.joinedminds.masserr.ui.dto.SubmitResponse;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.joinedminds.masserr.Functions.fromNavId;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
public class AdminModule implements NavItem {
    private static final Logger logger = Logger.getLogger(AdminModule.class.getName());
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

    public List<OtherTrait> getOtherTraits() {
        return manipulationDb.getOtherTraits();
    }

    public void doOtherTraitSubmit(@QueryParameter("id") String id,
                                @QueryParameter("name") String name,
                                @QueryParameter("docUrl") String docUrl,
                                StaplerResponse response) throws IOException {
        OtherTrait trait;
        if (id != null && id.startsWith("new")) {
            trait = manipulationDb.newOtherTrait();
        } else {
            trait = manipulationDb.getOtherTrait(fromNavId(id));
        }

        if (trait != null) {
            trait.setName(name);
            trait.setDocUrl(docUrl);
            trait = manipulationDb.saveOtherTrait(trait);
            JSONObject o = JSONObject.fromObject(new OtherTrait(trait));
            o.put("status", "OK");
            response.getWriter().print(o.toString());
        } else {
            JSONObject o = new JSONObject();
            o.put("status", "error");
            o.put("message", "Id ["+id+"] not found");
            response.getWriter().print(o.toString());
        }
    }

    @JavaScriptMethod
    public String testIt(StaplerRequest request, StaplerResponse response) {
        return "bah";
    }

    @JavaScriptMethod
    public SubmitResponse<OtherTrait> submitOtherTrait(OtherTrait submit) {
        String id = submit.getId();
        OtherTrait trait;
        if (id == null || id.startsWith("new")) {
            trait = manipulationDb.newOtherTrait();
        } else {
            trait = manipulationDb.getOtherTrait(fromNavId(id));
        }

        if (trait != null) {
            trait.setName(submit.getName());
            trait.setDocUrl(submit.getDocUrl());
            trait = manipulationDb.saveOtherTrait(trait);
            return new SubmitResponse<>(new OtherTrait(trait));
        } else {
            return new SubmitResponse<>("Id ["+id+"] not found");
        }
    }

    @JavaScriptMethod()
    public SubmitResponse<Ability> submitAbility(Ability submit) {
        String id = submit.getId();
        Ability ability;
        if (id == null || id.startsWith("new")) {
            ability = manipulationDb.newAbility();
        } else {
            ability = manipulationDb.getAbility(id);
        }

        if (ability != null) {
            ability.setName(submit.getName());
            ability.setDocUrl(submit.getDocUrl());
            ability.setType(submit.getType());
            ability.setBaseMonthlyIncome(submit.getBaseMonthlyIncome());
            ability = manipulationDb.saveAbility(ability);
            return new SubmitResponse<>(new Ability(ability));
        } else {
            return new SubmitResponse<>("Id ["+id+"] not found");
        }
    }

    public void doAbilitySubmit(@QueryParameter("id") String id,
                                @QueryParameter("type") String type,
                                @QueryParameter("name") String name,
                                @QueryParameter("baseMonthlyIncome") int baseMonthlyIncome,
                                @QueryParameter("docUrl") String docUrl,
                                StaplerResponse response) throws IOException {
        Ability ability;
        if (id != null && id.startsWith("new")) {
            ability = manipulationDb.newAbility();
        } else {
            ability = manipulationDb.getAbility(fromNavId(id));
        }

        if (ability != null) {
            ability.setName(name);
            ability.setType(Ability.Type.valueOf(type));
            ability.setBaseMonthlyIncome(baseMonthlyIncome);
            ability.setDocUrl(docUrl);
            ability = manipulationDb.saveAbility(ability);
            JSONObject o = JSONObject.fromObject(new Ability(ability));
            o.put("status", "OK");
            response.getWriter().print(o.toString());
        } else {
            JSONObject o = new JSONObject();
            o.put("status", "error");
            o.put("message", "Id ["+id+"] not found");
            response.getWriter().print(o.toString());
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
