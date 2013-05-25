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

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.dataimport.Wiki;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.model.Ability;
import net.joinedminds.masserr.model.Campaign;
import net.joinedminds.masserr.model.Config;
import net.joinedminds.masserr.model.Discipline;
import net.joinedminds.masserr.model.MeritOrFlaw;
import net.joinedminds.masserr.model.Morality;
import net.joinedminds.masserr.model.OtherTrait;
import net.joinedminds.masserr.model.Path;
import net.joinedminds.masserr.model.Player;
import net.joinedminds.masserr.model.Ritual;
import net.joinedminds.masserr.model.RitualType;
import net.joinedminds.masserr.ui.NavItem;
import net.joinedminds.masserr.ui.dto.NameId;
import net.joinedminds.masserr.ui.dto.SubmitResponse;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;
import org.kohsuke.stapler.interceptor.JsonOutputFilter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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

    @JavaScriptMethod
    public List<MeritOrFlaw> getMeritOrFlaws() {
        return manipulationDb.getMeritOrFlaws();
    }

    public List<OtherTrait> getOtherTraits() {
        return manipulationDb.getOtherTraits();
    }

    public List<Discipline> getDisciplines() {
        return manipulationDb.getDisciplines();
    }

    public List<Path> getPaths() {
        return manipulationDb.getPaths();
    }

    @JavaScriptMethod
    public SubmitResponse<Path> submitPath(Path submit) {
        String id = submit.getId();
        Path path;
        if (id == null || id.startsWith("new")) {
            path = manipulationDb.newPath();
        } else {
            path = manipulationDb.getPath(fromNavId(id));
        }

        if (path != null) {
            path.setName(submit.getName());
            path.setType(submit.getType());
            path.setDocUrl(submit.getDocUrl());
            path = manipulationDb.savePath(path);
            return new SubmitResponse<>(new Path(path));
        } else {
            return SubmitResponse.idNotFound(id);
        }
    }

    @JavaScriptMethod
    public SubmitResponse<String> deleteDiscipline(String id) {
        if (manipulationDb.deleteDiscipline(id)) {
            return new SubmitResponse<>(id, true, "OK");
        } else {
            return SubmitResponse.idNotFound(id);
        }
    }

    @JavaScriptMethod
    public SubmitResponse<Discipline> submitDiscipline(Discipline submit) {
        String id = submit.getId();
        Discipline discipline;
        if (id == null || id.startsWith("new")) {
            discipline = manipulationDb.newDiscipline();
        } else {
            discipline = manipulationDb.getDiscipline(fromNavId(id));
        }

        if (discipline != null) {
            discipline.setName(submit.getName());
            discipline.setDocUrl(submit.getDocUrl());
            if (discipline.getRetestAbility() != null) {
                if (submit.getRetestAbility() != null &&
                        !discipline.getRetestAbility().getId().equals(submit.getRetestAbility().getId())) {
                    discipline.setRetestAbility(manipulationDb.getAbility(submit.getRetestAbility().getId()));
                } else if (submit.getRetestAbility() == null || submit.getRetestAbility().getId().isEmpty()) {
                    discipline.setRetestAbility(null);
                }
            } else if (submit.getRetestAbility() != null && !submit.getRetestAbility().getId().isEmpty()) {
                discipline.setRetestAbility(manipulationDb.getAbility(submit.getRetestAbility().getId()));
            }
            discipline = manipulationDb.saveDiscipline(discipline);
            return new SubmitResponse<>(new Discipline(discipline));
        } else {
            return SubmitResponse.idNotFound(id);
        }
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
            return SubmitResponse.idNotFound(id);
        }
    }

    @JavaScriptMethod
    public SubmitResponse<MeritOrFlaw> submitMeritOrFlaw(MeritOrFlaw submit) {
        String id = submit.getId();
        MeritOrFlaw mf;
        if (id == null || id.startsWith("new")) {
            mf = manipulationDb.newMeritOrFlaw();
        } else {
            mf = manipulationDb.getMeritOrFlaw(id);
        }

        if (mf != null) {
            mf.setName(submit.getName());
            mf.setType(submit.getType());
            mf.setPoints(submit.getPoints());
            mf.setDocUrl(submit.getDocUrl());
            mf = manipulationDb.saveMeritOrFlaw(mf);
            return new SubmitResponse<>(mf);
        } else {
            return SubmitResponse.idNotFound(id);
        }
    }

    @JavaScriptMethod
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
            return SubmitResponse.idNotFound(id);
        }
    }

    public Config getConfig() {
        return adminDb.getConfig();
    }

    @SuppressWarnings("unused") //Form post
    public void doConfigSubmit(StaplerRequest request, StaplerResponse response) throws IOException, ServletException {
        Config config = getConfig();
        //request.bindJSON();
        JSONObject form = Functions.getSubmittedForm(request);
        request.bindJSON(config, form);

        adminDb.saveConfig(config);
        response.sendRedirect2("./");
    }

    public List<Morality> getMoralityPaths() {
        return manipulationDb.getMoralityPaths();
    }

    public AdminWiki getWiki() {
        return wiki;
    }

    private AdminWiki wiki = new AdminWiki();

    public List<Campaign> getCampaigns() {
        return adminDb.getCampaigns();
    }

    public List<Player> getPlayers(Campaign campaign) {
        return adminDb.getPlayers(campaign);
    }

    @JavaScriptMethod
    public List<Player> getPlayers(String campaignId) {
        Campaign c = Campaign.idRef(campaignId);
        if(c != null) {
            return adminDb.getPlayers(c);
        } else {
            return Collections.emptyList();
        }
    }

    @JavaScriptMethod
    @JsonOutputFilter(excludes = {"acl"})
    public Player getPlayer(String id) {
        return adminDb.getPlayer(id);
    }

    @JavaScriptMethod
    @JsonOutputFilter(excludes = {"acl"})
    public SubmitResponse<Player> savePlayer(JSONObject jsonPlayer) {
        String id = jsonPlayer.optString("id");
        String name = jsonPlayer.optString("name");
        if(name == null || name.isEmpty()) {
            return new SubmitResponse<>(null, false, "No name!");
        }
        String email = jsonPlayer.optString("email");
        if(email == null || email.isEmpty()) {
            return new SubmitResponse<>(null, false, "No email!");
        } else {

            if(adminDb.isPlayerEmailTaken(email, id)) {
                return new SubmitResponse<>(null, false, "Email taken!");
            }
        }
        Player player;
        if(id != null && !id.isEmpty()) {
            player = adminDb.getPlayer(id);
        } else {
            player = adminDb.newPlayer();
        }
        if (player != null) {
            player.setName(jsonPlayer.getString("name"));
            player.setAddress(jsonPlayer.getString("address"));
            player.setCampaign(Campaign.idRef(jsonPlayer.getString("campaign")));
            player.setEmail(jsonPlayer.getString("email"));
            player.setPhone(jsonPlayer.getString("phone"));
            Player saved = adminDb.savePlayer(player);
            return new SubmitResponse<>(saved);
        } else {
            return SubmitResponse.idNotFound(id);
        }
    }

    public class AdminWiki extends Wiki {
        public void doAbilities(StaplerRequest request, StaplerResponse response) throws IOException {

            String text = generateWikiText("Abilities", 2, manipulationDb.getAbilities(), new PostText<Ability>() {
                @Override
                public String txt(Ability item, Wiki helper) {
                    StringBuilder s = new StringBuilder();
                    s.append(helper.bold("Type: ")).append(item.getType().name());
                    return s.toString();
                }
            });
            writeResponse(text, response);
        }

        public void doOtherTraits(StaplerRequest request, StaplerResponse response) throws IOException {
            response.setContentType("text/plain");
            String text = generateWikiText("Other Traits", 2, manipulationDb.getOtherTraits(), null);
            writeResponse(text, response);
        }

        public void doDisciplines(StaplerRequest request, StaplerResponse response) throws IOException {
            response.setContentType("text/plain");
            String text = generateWikiText("Disciplines", 2, manipulationDb.getDisciplines(), new PostText<Discipline>() {
                @Override
                public String txt(Discipline item, Wiki helper) {
                    StringBuilder s = new StringBuilder();
                    Ability retestAbility = item.getRetestAbility();
                    if (retestAbility != null) {
                        s.append(bold("Retest: "));
                        if (retestAbility.getDocUrl() != null && !retestAbility.getDocUrl().isEmpty()) {
                            s.append(externalLink(retestAbility.getDocUrl(), retestAbility.getName()));
                        } else {
                            s.append(retestAbility.getName());
                        }
                        s.append("\n");
                    }
                    s.append("====Basic ").append(item.getName()).append("====\n")
                            .append("=====X=====\n\n")
                            .append("=====Y=====\n\n")
                            .append("====Intermediate ").append(item.getName()).append("====\n")
                            .append("=====X=====\n\n")
                            .append("=====Y=====\n\n")
                            .append("====Advanced ").append(item.getName()).append("====\n")
                            .append("=====X=====\n");
                    return s.toString();
                }
            });
            writeResponse(text, response);
        }

        public void doMagicPaths(StaplerRequest request, StaplerResponse response) throws IOException {
            response.setContentType("text/plain");
            String text = generateWikiText("Thaumaturgy Paths", 2, manipulationDb.getPaths(Path.Type.Thaumaturgy), null);
            text += generateWikiText("Necromancy Paths", 2, manipulationDb.getPaths(Path.Type.Necromancy), null);
            writeResponse(text, response);
        }

        public void doRituals(StaplerRequest request, StaplerResponse response) throws IOException {
            response.setContentType("text/plain");
            List<RitualType> types = manipulationDb.getRitualTypes();
            StringBuilder s = new StringBuilder();
            for (RitualType type : types) {
                s.append(generateWikiText(type.getName(), 2, manipulationDb.getRituals(type.getId()), new PostText<Ritual>() {
                    @Override
                    public String txt(Ritual item, Wiki helper) {
                        StringBuilder ss = new StringBuilder();
                        ss.append(helper.bold("Level: ")).append(item.getLevel()).append("\n");
                        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
                            ss.append("\n").append(helper.italic(item.getDescription())).append("\n");
                        }
                        return ss.toString();
                    }
                }));
            }
            String text = s.toString();
            writeResponse(text, response);
        }

        public void doMeritsFlaws(StaplerRequest request, StaplerResponse response) throws IOException {
            StringBuilder s = new StringBuilder();
            ListMultimap<String, MeritOrFlaw> map;
            List<NameId> names;
            for (MeritOrFlaw.Type type : MeritOrFlaw.Type.values()) {
                s.append(this.heading(2, type.name()));
                for (MeritOrFlaw.MoF mof : MeritOrFlaw.MoF.values()) {
                    List<MeritOrFlaw> list;
                    switch (mof) {
                        case Merits:
                        default:
                            list = manipulationDb.getMerits(type);
                            break;
                        case Flaws:
                            list = manipulationDb.getFlaws(type);
                            break;
                    }
                    map = LinkedListMultimap.create();
                    names = new LinkedList<>();
                    for (MeritOrFlaw mf : list) {
                        map.put(mf.getName(), mf);
                        if (map.get(mf.getName()).size() == 1) {
                            names.add(new NameId(mf.getName(), mf.getId()));
                        }
                    }
                    s.append(generateWikiText(mof.name(), 3, names, new MeritOrFlawPostText(map)));
                }
            }
            writeResponse(s.toString(), response);
        }

        class MeritOrFlawPostText implements PostText<NameId> {
            ListMultimap<String, MeritOrFlaw> map;

            MeritOrFlawPostText(ListMultimap<String, MeritOrFlaw> map) {
                this.map = map;
            }

            @Override
            public String txt(NameId item, Wiki helper) {
                StringBuilder mfs = new StringBuilder();
                List<MeritOrFlaw> ml = map.get(item.getName());
                if (ml.size() > 1) {
                    mfs.append(bold(Math.abs(ml.get(0).getPoints()) + "-" + Math.abs(ml.get(ml.size()-1).getPoints())));
                } else {
                    mfs.append(bold(String.valueOf(Math.abs(ml.get(0).getPoints()))));
                }
                mfs.append(" Points\n\n");
                return mfs.toString();
            }
        }
    }
}
