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

package net.joinedminds.masserr.util;

import com.google.common.collect.LinkedListMultimap;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.db.ManipulationDB;
import net.joinedminds.masserr.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static net.joinedminds.masserr.Functions.ifNull;
import static net.joinedminds.masserr.Functions.isEmpty;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class RoleFormFunctions {

    public static DottedNotedType<Ability> find(Ability ability, List<DottedNotedType<Ability>> abilities) {
        if (abilities != null) {
            for (DottedNotedType<Ability> type : abilities) {
                if (type.getType().getId().equals(ability.getId())) {
                    return type;
                }
            }
        }
        return null;
    }

    public static List<DottedNotedType<Ability>> abilitiesMixIn(List<Ability> fullList, List<DottedNotedType<Ability>> abilities) {
        List<DottedNotedType<Ability>> ret = new LinkedList<>();
        for (Ability ability : fullList) {
            DottedNotedType<Ability> ra = find(ability, abilities);
            if (ra != null) {
                ret.add(ra);
            } else {
                ret.add(new DottedNotedType<>(ability, 0, ""));
            }
        }
        return ret;
    }

    public static String setRoleBasics(Role role, JSONObject formObject, ManipulationDB manipulationDB) {
        role.setNpc(formObject.has("npc"));
        role.setDomain(Domain.idRef(formObject.getString("domain")));
        if (isEmpty(formObject.optString("name"))) {
            return Messages.QuickRoles_Msg_NoName_Msg();
        }
        role.setName(formObject.getString("name"));
        role.setPlayer(Player.idRef(formObject.optString("player")));
        role.setGeneration(manipulationDB.getGeneration(formObject.getInt("generation")));
        Calendar c = calcEmbraced(formObject);
        role.setEmbraced(c.getTime());
        role.setClan(Clan.idRef(formObject.getString("clan")));
        role.setSire(Role.idRef(formObject.optString("sire")));
        if (isEmpty(formObject.optString("nature"))) {
            return "Missing Nature";
        }
        if (isEmpty(formObject.optString("demeanor"))) {
            return "Missing Demeanor";
        }
        role.setNature(Archetype.idRef(formObject.getString("nature")));
        role.setDemeanor(Archetype.idRef(formObject.getString("demeanor")));
        JSONObject jsonMorality = formObject.optJSONObject("morality");
        if (jsonMorality != null && jsonMorality.optJSONObject("type") != null &&
                !isEmpty(jsonMorality.getJSONObject("type").optString("id"))) {
            DottedType<Morality> morality = new DottedType<>(
                    Morality.idRef(jsonMorality.getJSONObject("type").getString("id")), jsonMorality.getInt("dots"));
            role.setMorality(morality);
        } else {
            return "Missing Morality";
        }
        JSONObject jsonVirtues = formObject.optJSONObject("virtues");
        if (jsonVirtues != null) {
            Virtues virtues = new Virtues(
                    Virtues.Adherence.valueOf(jsonVirtues.getString("adherence")),
                    jsonVirtues.getInt("adherenceDots"),
                    Virtues.Resistance.valueOf(jsonVirtues.getString("resistance")),
                    jsonVirtues.getInt("resistanceDots"),
                    jsonVirtues.getInt("courageDots"));
            role.setVirtues(virtues);
        } else {
            return "Missing Virtues";
        }
        setDisciplines(role, formObject);
        role.setExtraHealthLevels(formObject.getInt("extraHealthLevels"));
        role.setSufferesOfInjury(formObject.has("suffersOfInjury"));
        role.setFightForm(FightOrFlight.idRef(formObject.optString("fightForm")));
        role.setFlightForm(FightOrFlight.idRef(formObject.optString("flightForm")));
        role.setQuote(Functions.emptyIfNull(formObject.optString("quote")));
        role.setVitals(Vitals.valueOf(formObject.getString("vitals")));
        return null;
    }

    private static void setDisciplines(Role role, JSONObject formObject) {
        JSONArray jDisciplines = formObject.getJSONArray("discipline");
        List<DottedType<Discipline>> disciplines = new LinkedList<>();
        for (int i = 0; i < jDisciplines.size(); i++) {
            JSONObject d = jDisciplines.getJSONObject(i);
            if (!isEmpty(d.optString("id")) && d.has("dots") && d.getInt("dots") > 0) {
                disciplines.add(new DottedType<>(
                        Discipline.idRef(d.getString("id")),
                        d.getInt("dots")));
            }
        }
        role.setDisciplines(disciplines);
    }

    public static Calendar calcEmbraced(JSONObject jsonRole) {
        String embr = jsonRole.getString("embraced");
        String[] parts = embr.split("-");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(parts[0]));
        if (parts.length > 1) {
            c.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);
        }
        if (parts.length > 2) {
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));
        }
        return c;
    }

    public static String setRoleMagic(Role role, JSONObject formObject, ManipulationDB manipulationDB) {
        role.setThaumaType(ifNull(formObject.optString("thaumaSchoolName"), ""));
        role.setNecromancyType(ifNull(formObject.optString("necromancySchoolName"), ""));
        role.setThaumaturgicalPaths(parsePaths(formObject.optJSONArray("thaumaturgicalPaths")));
        role.setNecromancyPaths(parsePaths(formObject.optJSONArray("necromancyPaths")));
        List<Ritual> rituals = new LinkedList<>();
        JSONArray jsonArray = formObject.optJSONArray("rituals");
        if (jsonArray != null) {
            for(int i = 0; i< jsonArray.size(); i++) {
                String id = jsonArray.getString(i);
                if(!isEmpty(id)) {
                    rituals.add(Ritual.idRef(id));
                }
            }
        }
        role.setRituals(rituals);

        return null;
    }

    private static List<DottedType<Path>> parsePaths(JSONArray paths) {
        if(paths != null) {
            List<DottedType<Path>> list = new LinkedList<>();
            for(int i = 0; i < paths.size(); i++) {
                JSONObject o = paths.getJSONObject(i);
                if(!isEmpty(o.optString("id")) && o.has("dots") && o.getInt("dots") > 0) {
                    list.add(new DottedType<>(Path.idRef(o.getString("id")), o.getInt("dots")));
                }
            }
            return list;
        }
        return null;
    }

    public static String setRoleAttributes(Role role, JSONObject formObject, ManipulationDB manipulationDB) {
        role.setPhysical(ifNull(formObject.optInt("physical"), 0));
        role.setSocial(ifNull(formObject.optInt("social"), 0));
        role.setMental(ifNull(formObject.optInt("mental"), 0));

        LinkedListMultimap<Ability.Type, DottedNotedType<Ability>> multiMap = LinkedListMultimap.create();
        JSONArray abilities = formObject.optJSONArray("ability");
        if(abilities != null) {
            for (int i = 0; i< abilities.size(); i++) {
                JSONObject a = abilities.getJSONObject(i);
                int dots = a.optInt("dots");
                String type = a.optString("type");
                String notes = a.optString("notes");
                String id = a.optString("id");
                if(ifNull(dots, 0) > 0 && !isEmpty(type) && !isEmpty(id)) {
                    DottedNotedType<Ability> ability = new DottedNotedType<>(Ability.idRef(id), dots, ifNull(notes, ""));
                    multiMap.put(Ability.Type.valueOf(type), ability);
                }
            }
        }
        role.setPhysicalAbilities(multiMap.get(Ability.Type.Physical));
        role.setSocialAbilities(multiMap.get(Ability.Type.Social));
        role.setMentalAbilities(multiMap.get(Ability.Type.Mental));

        return null;
    }

    public static String setRoleMisc(Role role, JSONObject formObject, ManipulationDB manipulationDB) {
        List<String> list = new LinkedList<>();
        if (formObject.has("derangements")) {
            JSONArray d = formObject.getJSONArray("derangements");
            for(int i = 0; i < d.size(); i++) {
                String string = d.getString(i);
                if (!isEmpty(string)) {
                    list.add(string);
                }
            }
        }
        role.setDerangements(list);
        list = new LinkedList<>();
        if (formObject.has("beastTraits")) {
            JSONArray d = formObject.getJSONArray("beastTraits");
            for(int i = 0; i < d.size(); i++) {
                String string = d.getString(i);
                if (!isEmpty(string)) {
                    list.add(string);
                }
            }
        }
        role.setBeastTraits(list);
        List<NotedType<MeritOrFlaw>> mfList = parseMeritOrFlaws("merits", formObject);
        role.setMerits(mfList);
        mfList = parseMeritOrFlaws("flaws", formObject);
        role.setFlaws(mfList);
        List<DottedType<OtherTrait>> otherTraits = new LinkedList<>();
        if (formObject.has("otherTraits")) {
            JSONArray jos = formObject.getJSONArray("otherTraits");
            for(int i = 0; i < jos.size(); i++) {
                JSONObject jo = jos.getJSONObject(i);
                if(!isEmpty(jo.optString("id")) && ifNull(jo.optInt("dots"), 0) > 0) {
                    DottedType<OtherTrait> ot = new DottedType<>(OtherTrait.idRef(jo.getString("id")), jo.getInt("dots"));
                    otherTraits.add(ot);
                }
            }
        }
        role.setOtherTraits(otherTraits);

        return null;
    }

    private static List<NotedType<MeritOrFlaw>> parseMeritOrFlaws(String jsonKey, JSONObject formObject) {
        List<NotedType<MeritOrFlaw>> mfList = new LinkedList<>();
        if (formObject.has(jsonKey)) {
            JSONArray jms = formObject.getJSONArray(jsonKey);
            for(int i = 0; i < jms.size(); i++) {
                JSONObject jm = jms.getJSONObject(i);
                if(!isEmpty(jm.optString("id"))) {
                    NotedType<MeritOrFlaw> m = new NotedType<>(MeritOrFlaw.idRef(jm.getString("id")),
                                                               ifNull(jm.optString("notes"), ""));
                    mfList.add(m);
                }
            }
        }
        return mfList;
    }
}
