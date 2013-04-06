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

import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.*
import net.joinedminds.masserr.modules.RolesModule

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Edit Role") + " " + Masserr.getInstance().getAppName()) {
    Role role = my;
    RolesModule module = Masserr.getInstance().getRoles();
    Functions f = h;
    raw(f.bind(module, "module"))
    st.include(page: "quickRole.groovy", class: RolesModule.class, it: module)
    script(type: "template", id: "t_DisciplinesSelect") {
        div(class: "row") {
            div(class: "span3") {
                select(name: "discipline[][id]", class: "span3") {
                    option(value: "", "")
                    module.getDisciplines().each { Discipline aD ->
                        option(value: aD.getId(), aD.getName())
                    }
                }
            }
            div(class: "span1") {
                input(name: "discipline[][dots]", type: "number", class: "span1", value: 0, min: 0, max: 9)
            }
        }
    }
    div(class: "row") {
        div(class: "span1") {
            div(class: "row") {
                div(class: 'span1', raw("&nbsp;"))
            }
        }
        div(class: "span11") {
            ul(class: "nav nav-tabs", id: "roleTab") {
                li(class: "active") { a(href: "#basic", _("Basic")) }
                li { a(href: "#magic", _("Magic")) }
                li { a(href: "#attributes", _("Attributes")) }
                li { a(href: "#misc", _("Misc")) }
            }

            div(id: "roleTabContent", class: "tab-content") {
                div(class: "tab-pane active", id: "basic") {
                    div(class: "row") {
                        div(class: "span4") {
                            div(class: "row") {
                                div(class: "span1") {
                                    input(type: "hidden", name: "id", value: "")
                                    p ( _("NPC") )
                                }
                                div(class: "span3") {
                                    input(type: "checkbox", name: "npc")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Domain"))
                                div(class: "span3") {
                                    select(name: "domain", class: "span3") {
                                        module.getDomains().each { Domain domain ->
                                            option(value: domain.getId(), domain.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Name"))
                                div(class: "span3") {
                                    input(type: "text", class: "span3", name: "name", required: "true", placeholder: _("Name"))
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Player"))
                                div(class: "span3") {
                                    input(class: "span3", type: "text", name: "player")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Generation"))
                                div(class: "span3") {
                                    select(class: "span3", id: "generationSelect", name: "generation") {
                                        module.getGenerations().each { Generation gen ->
                                            option(value: gen.getId(), gen.getGeneration())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Embraced"))
                                div(class: "span3") {
                                    input(type: "text", class: "span3", name: "embraced",
                                            required: "true", pattern: "\\d{3,4}(-\\d{2}(-\\d{2}){0,1}){0,1}",
                                    placeholder: "YYYY[-MM[-DD]]")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Clan"))
                                div(class: "span3") {
                                    select(name: "clan", class: "span3", id: "clanSelect") {
                                        module.getClans().each { Clan clan ->
                                            option(value: clan.getId(), clan.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Sire"))
                                div(class: "span3 input-append") {
                                    select(id: "sireSelect", name: "sire") {
                                        option(value: "", "")
                                    }
                                    button(type: "button", class: "btn", id: "newSireBtn",
                                            role: "button", href: "#quickRoleModal", 'data-toggle': "modal",
                                            style: "padding-left: 6px; padding-right: 6px") {
                                        i(class: "icon-plus")
                                    }
                                }
                            }
                            List<Archetype> archetypes = module.getArchetypes()
                            div(class: "row") {
                                div(class: "span1", _("Nature"))
                                div(class: "span3") {
                                    select(name: "nature", class: "span3") {
                                        option(value: "", "")
                                        archetypes.each {Archetype at ->
                                            if(my?.getNature()?.getId() == at.getId()) {
                                                option(value: at.getId(), at.getName(), selected: true)
                                            } else {
                                                option(value: at.getId(), at.getName())
                                            }
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Demeanor"))
                                div(class: "span3") {
                                    select(name: "demeanor", class: "span3") {
                                        option(value: "", "")
                                        archetypes.each {Archetype at ->
                                            if(my?.getDemeanor()?.getId() == at.getId()) {
                                                option(value: at.getId(), at.getName(), selected: true)
                                            } else {
                                                option(value: at.getId(), at.getName())
                                            }
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Path/Road"))
                                div(class: "span2") {
                                    input(type: "text", name: "path[name]", class: "span2")
                                }
                                div(class: "span1") {
                                    input(type: "number", class: "span1", name: "path[dots]", value: 1, max: 10, min: 1)
                                }
                            }
                        }
                        div(class: "span6") {
                            div(class: "row") {
                                div(class: "span4 msr-region-bordered", id: "disciplinesDiv", regionlabel: _("Disciplines")) {
                                    button(class: "btn btn-mini region-btn", onclick: "addDiscipline()") {
                                        i(class: "icon-plus")
                                    }
                                    List<DottedType<Discipline>> myDisciplines = role.getDisciplines()
                                    if (myDisciplines == null || myDisciplines.isEmpty()) {
                                        List<Discipline> clanDisciplines = null;
                                        if(role.getClan() != null) {
                                            clanDisciplines = role.getClan().getClanDisciplines()
                                        } else {
                                            clanDisciplines = module.getClans().get(0).getClanDisciplines()
                                        }
                                        myDisciplines = new LinkedList<>();
                                        clanDisciplines.each { Discipline aD ->
                                            myDisciplines.add(new DottedType<Discipline>(aD, 0))
                                        }
                                    }

                                    myDisciplines.each { DottedType<Discipline> discipline ->
                                        div(class: "row") {
                                            div(class: "span3") {
                                                select(name: "discipline[][id]", class: "span3") {
                                                    module.getDisciplines().each { Discipline aD ->
                                                        if (aD.getId() == discipline.getType().getId()) {
                                                            option(value: aD.getId(), selected: "true", aD.getName())
                                                        } else {
                                                            option(value: aD.getId(), aD.getName())
                                                        }
                                                    }
                                                }
                                            }
                                            div(class: "span1") {
                                                input(name: "discipline[][dots]", type: "number", class: "span1",
                                                        value: discipline.getDots(), min: 0, max: 9)
                                            }
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("+Health"))
                                div(class: "span1") {
                                    input(type: "number", class: "span1", value: 0, name: "extraHealthLevels")
                                }
                                div(class: "span2") {
                                    input(type: "checkbox", class: "span1", name: "suffersOfInjury")
                                    span(_("-Injury"))
                                }
                            }
                            List<FightOrFlight> forms = module.getFightOrFlights()
                            div(class: "row") {
                                div(class: "span1", _("Fight"))
                                div(class: "span3") {
                                    select(name: "fightForm", class: "span3") {
                                        option(value: '', '')
                                        forms.each { FightOrFlight ff ->
                                            option(value: ff.getId(), ff.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Flight"))
                                div(class: "span3") {
                                    select(name: "flightForm", class: "span3") {
                                        option(value: '', '')
                                        forms.each { FightOrFlight ff ->
                                            option(value: ff.getId(), ff.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Quote"))
                                div(class: "span3") {
                                    input(type: "text", name: "quote", class: "span3")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Status"))
                                div(class: "span3") {
                                    select(name: "vitals", class: 'span3') {
                                        Vitals.values().each { Vitals v ->
                                            option(value: v.name(), v.toString())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                div(class: "tab-pane", id: "magic") {
                    div(class: "row") {
                        div(class: "span5 msr-region-bordered", regionlabel: _("Thaumaturgy Paths")) {
                            button(class: "btn btn-mini region-btn", onclick: "addThaumaPath()") {
                                i(class: "icon-plus")
                            }
                            div(class: "row") {
                                div(class: "span5") {
                                    input(type: "text", name: "thaumaSchoolName", placeholder: "School", class: "span5")
                                }
                            }
                            div(class: "row") {
                                div(class: "span4") {
                                    select(class: "span4") {
                                        option(value: "", "")
                                        module.getPaths(Path.Type.Thaumaturgy).each { Path p ->
                                            option(value: p.id, p.getName())
                                        }
                                    }
                                }
                                div(class: "span1") {
                                    input(type: "number", class: "span1")
                                }
                            }
                        }
                        div(class: "span5 msr-region-bordered", regionlabel: _("Necromancy Paths")) {
                            button(class: "btn btn-mini region-btn", onclick: "addNectromancyPath()") {
                                i(class: "icon-plus")
                            }
                            div(class: "row") {
                                div(class: "span5") {
                                    input(type: "text", name: "necromancySchoolName", placeholder: "School", class: "span5")
                                }
                            }
                            div(class: "row") {
                                div(class: "span4") {
                                    select(class: "span4") {
                                        option(value: "", "")
                                        module.getPaths(Path.Type.Necromancy).each { Path p ->
                                            option(value: p.id, p.getName())
                                        }
                                    }
                                }
                                div(class: "span1") {
                                    input(type: "number", class: "span1")
                                }
                            }
                        }
                    }
                    div(class: "row") {
                        div(class: "span10 msr-region-bordered", regionlabel: _("Rituals")) {
                            div(class: "row") {
                                div(class: "span7") {
                                    table(class: "table table-condensed span6") {
                                        tr {
                                            td(raw("&nbsp;"))
                                            td(raw("&nbsp;"))
                                            td(width: "10%", raw("&nbsp;"))
                                        }
                                    }
                                }
                                div(class: "span3") {
                                    div(class: "row") {
                                        div(class: "span3") {
                                            select(class: "span3", id: "ritualTypesSelect") {
                                                module.getRitualTypes().each { RitualType t ->
                                                    option(value: t.getId(), t.getName())
                                                }
                                            }
                                        }
                                    }
                                    div(class: "row") {
                                        div(class: "span3") {
                                            select(class: "span3", id: "ritualsSelect") {
                                                module.getRituals(module.getRitualTypes().get(0).getId()).each { Ritual ritual ->
                                                    option(value: ritual.getId(), ritual.getName())
                                                }
                                            }
                                        }
                                    }
                                    div(class: "row") {
                                        div(class: "span1 offset2") {
                                            button(class: "btn", onclick: "addRitual()", _("Add"))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                div(class: "tab-pane", id: "attributes") {
                    div(class: "row") {
                        div(class: "span3 msr-region-bordered", regionlabel: _("Physical")) {
                            div(class: "row") {
                                div(class: "span3") {
                                    input(type: "number", class: "span3")
                                }
                            }
                            div(class: "row") {
                                div(class: "span3") {
                                    table(class: "table table-condensed", style: "width: 100%") {
                                        module.getAbilities(Ability.Type.Physical).each { Ability ability ->
                                            tr {
                                                td(ability.getName())
                                                td(raw("&nbsp;"))
                                                td("0")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        div(class: "span3 msr-region-bordered", regionlabel: _("Social")) {
                            div(class: "row") {
                                div(class: "span3") {
                                    input(type: "number", class: "span3")
                                }
                            }
                            div(class: "row") {
                                div(class: "span3") {
                                    table(class: "table table-condensed", style: "width: 100%") {
                                        module.getAbilities(Ability.Type.Social).each { Ability ability ->
                                            tr {
                                                td(ability.getName())
                                                td(raw("&nbsp;"))
                                                td("0")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        div(class: "span3 msr-region-bordered", regionlabel: _("Mental")) {
                            div(class: "row") {
                                div(class: "span3") {
                                    input(type: "number", class: "span3")
                                }
                            }
                            div(class: "row") {
                                div(class: "span3") {
                                    table(class: "table table-condensed", style: "width: 100%") {
                                        module.getAbilities(Ability.Type.Mental).each { Ability ability ->
                                            tr {
                                                td(ability.getName())
                                                td(raw("&nbsp;"))
                                                td("0")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    div(class: "row") {
                        div(class: "span9", "Obfuscate retests with Occult")
                    }
                }
                div(class: "tab-pane", id: "misc") {
                    div(class: "row") {
                        div(class: "span4") {
                            div(class: "row") {
                                div(class: "span4") {
                                    div(class: "msr-region-bordered", style: "width: 100%;", regionlabel: _("Derangements")) {
                                        div(class: "row") {
                                            div(class: "span4") {
                                                input(type: "text", class: "span4")
                                            }
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span4") {
                                    div(class: "msr-region-bordered", style: "width: 100%;", regionlabel: _("Beast Traits")) {
                                        div(class: "row") {
                                            div(class: "span4") {
                                                input(type: "text", class: "span4")
                                            }
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span4") {
                                    div(class: "msr-region-bordered", style: "width: 100%;", regionlabel: _("Other Traits")) {
                                        div(class: "row") {
                                            div(class: "span4") {
                                                input(type: "text", class: "span4")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        div(class: "span4") {
                            div(class: "row") {
                                div(class: "span4") {
                                    div(class: "msr-region-bordered", style: "width: 100%;", regionlabel: _("Merits")) {
                                        div(class: "row") {
                                            div(class: "span4") {
                                                input(type: "text", class: "span4")
                                            }
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span4") {
                                    div(class: "msr-region-bordered", style: "width: 100%;", regionlabel: _("Flaws")) {
                                        div(class: "row") {
                                            div(class: "span4") {
                                                input(type: "text", class: "span4")
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            script(type: "template", id: "statsContent") {
                table(class: "table table-condensed") {
                    tr {
                        td(_("Disciplines"))
                        td(id: "disciplinesStats", raw("&nbsp;"))
                    }
                    tr {
                        td(_("Attributes"))
                        td(id: "attributesStats", raw("&nbsp;"))
                    }
                    tr {
                        td(_("Abilities"))
                        td(id: "abilitiesStats", raw("&nbsp;"))
                    }
                    tr {
                        td(_("Sum M&F"))
                        td(id: "mnfSumStats", raw("&nbsp;"))
                    }
                }
            }
            script(src: "${resURL}/js/role/edit.js")
        }
    }
}

