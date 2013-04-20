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
package net.joinedminds.masserr.model.Role

import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Archetype
import net.joinedminds.masserr.model.Clan
import net.joinedminds.masserr.model.Discipline
import net.joinedminds.masserr.model.Domain
import net.joinedminds.masserr.model.DottedType
import net.joinedminds.masserr.model.FightOrFlight
import net.joinedminds.masserr.model.Generation
import net.joinedminds.masserr.model.Morality
import net.joinedminds.masserr.model.Player
import net.joinedminds.masserr.model.Role
import net.joinedminds.masserr.model.Virtues
import net.joinedminds.masserr.model.Vitals
import net.joinedminds.masserr.modules.RolesModule

import static net.joinedminds.masserr.Functions.ifNull

Role role = my;
RolesModule module = Masserr.getInstance().getRoles();
Functions f = h;

List<Clan> clans = module.getClans()
Generation roleGen = role?.generation
if(roleGen == null) {
    roleGen = module.defaultGeneration
}

div(class: "row") {
    div(class: "span4") {
        div(class: "row") {
            div(class: "span1") {
                input(type: "hidden", name: "id", value: role.id)
                p ( _("NPC") )
            }
            div(class: "span3") {
                if(role?.npc) {
                    input(type: "checkbox", name: "npc", checked: true)
                } else {
                    input(type: "checkbox", name: "npc")
                }
            }
        }
        div(class: "row") {
            div(class: "span1", _("Domain"))
            div(class: "span3") {
                select(name: "domain", class: "span3") {
                    boolean foundD = false
                    module.getDomains().each { Domain domain ->
                        if (role?.domain?.id == domain.id) {
                            option(value: domain.getId(), domain.getName(), selected: true)
                            foundD = true
                        } else {
                            option(value: domain.getId(), domain.getName())
                        }
                    }
                    if (!foundD && role.domain != null) {
                        option(value: role.domain.getId(), role.domain.getName(), selected: true)
                    }
                }
            }
        }
        div(class: "row") {
            div(class: "span1", _("Name"))
            div(class: "span3") {
                input(type: "text", class: "span3", name: "name", required: "true",
                        placeholder: _("Name"), value: role?.name)
            }
        }
        div(class: "row") {
            div(class: "span1", _("Player"))
            div(class: "span3 input-append") {
                select(id: "playerSelect", class: "span3", name: "player") {
                    option(value: "", "")
                    module.players.each {Player player ->
                        if(role.getPlayer()?.id == player.id) {
                            option(value: player.id, selected: true, player.name)
                        } else {
                            option(value: player.id, player.name)
                        }
                    }
                }
                button(type: "button", class: "btn", id: "newPlayerBtn",
                        role: "button", href: "#playerModal", 'data-toggle': "modal",
                        style: "padding-left: 0.5em; padding-right: 0.5em") {
                    i(class: "icon-plus")
                }
            }
        }
        div(class: "row") {
            div(class: "span1", _("Generation"))
            div(class: "span3") {
                select(class: "span3", id: "generationSelect", name: "generation") {
                    module.getGenerations().each { Generation gen ->
                        if (roleGen.id == gen.id) {
                            option(value: gen.getId(), gen.getGeneration(), selected: true)
                        } else {
                            option(value: gen.getId(), gen.getGeneration())
                        }
                    }
                }
            }
        }
        div(class: "row") {
            div(class: "span1", _("Embraced"))
            div(class: "span3") {
                input(type: "text", class: "span3", name: "embraced",
                        required: "true", pattern: "\\d{3,4}(-\\d{2}(-\\d{2}){0,1}){0,1}",
                        placeholder: "YYYY[-MM[-DD]]", value: Functions.isoDate(role.embraced))
            }
        }
        div(class: "row") {
            div(class: "span1", _("Clan"))
            div(class: "span3") {
                select(name: "clan", class: "span3", id: "clanSelect") {
                    clans.each { Clan clan ->
                        if (role?.clan?.id == clan.id) {
                            option(value: clan.getId(), clan.getName(), selected: true)
                        } else {
                            option(value: clan.getId(), clan.getName())
                        }
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
                        style: "padding-left: 0.5em; padding-right: 0.5em") {
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
            div(class: "span1", _("Morality"))
            div(class: "span2") {
                select(name: "morality[type][id]", id: "moralitySelect", class: "span2") {
                    module.getMoralityPaths().each {Morality morality ->
                        if (role.morality?.type?.id == morality.id) {
                            option(value: morality.id, selected: true, morality.name)
                        } else {
                            option(value: morality.id, morality.name)
                        }
                    }
                }
            }
            div(class: "span1") {
                input(type: "number", class: "span1", name: "morality[dots]",
                        value: ifNull(role?.morality?.dots, 1), max: 5, min: 1)
            }
        }
        div(class: "row") {
            div(class: "span1") {
                raw("&nbsp;")
            }
            div(class: "span2") {
                select(class: "span2", id: "adherenceSelect", name: "virtues[adherence]") {
                    Virtues.Adherence.values().each {Virtues.Adherence ad ->
                        if(role.virtues?.adherence == ad) {
                            option(value: ad.name(), selected: true, ad.name())
                        } else {
                            option(value: ad.name(), ad.name())
                        }
                    }
                }
            }
            div(class: "span1") {
                input(type: "number", class: "span1", name: "virtues[adherenceDots]",
                        value: ifNull(role?.virtues?.adherenceDots, 1), max: 5, min: 1)
            }
        }
        div(class: "row") {
            div(class: "span1") {
                raw("&nbsp;")
            }
            div(class: "span2") {
                select(class: "span2", id: "resistanceSelect", name: "virtues[resistance]") {
                    Virtues.Resistance.values().each {Virtues.Resistance re ->
                        if(role.virtues?.resistance == re) {
                            option(value: re.name(), selected: true, re.name())
                        } else {
                            option(value: re.name(), re.name())
                        }
                    }
                }
            }
            div(class: "span1") {
                input(type: "number", class: "span1", name: "virtues[resistanceDots]",
                        value: ifNull(role?.virtues?.resistanceDots, 1), max: 5, min: 1)
            }
        }
        div(class: "row") {
            div(class: "span1") {
                raw("&nbsp;")
            }
            div(class: "span2", _("Courage"))
            div(class: "span1") {
                input(type: "number", class: "span1", name: "virtues[courageDots]",
                        value: ifNull(role?.virtues?.courageDots, 1), max: 5, min: 1)
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
                    List<Discipline> clanDisciplines;
                    if(role.getClan() != null) {
                        clanDisciplines = role.getClan().getClanDisciplines()
                    } else {
                        clanDisciplines = clans.get(0).getClanDisciplines()
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
                                    value: discipline.getDots(), min: 0, max: roleGen.disciplinesMax)
                        }
                    }
                }
            }
        }
        div(class: "row") {
            div(class: "span1", _("+Health"))
            div(class: "span1") {
                input(type: "number", class: "span1", min: 0,
                        value: ifNull(role?.extraHealthLevels, 0), name: "extraHealthLevels")
            }
            div(class: "span2") {
                if (role?.sufferesOfInjury) {
                    input(type: "checkbox", class: "span1", name: "suffersOfInjury", checked: true)
                } else {
                    input(type: "checkbox", class: "span1", name: "suffersOfInjury")
                }
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
                        if (ff.id == role?.fightForm?.id) {
                            option(value: ff.getId(), ff.getName(), selected: true)
                        } else {
                            option(value: ff.getId(), ff.getName())
                        }
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
                        if (ff.id == role?.flightForm?.id) {
                            option(value: ff.getId(), ff.getName(), selected: true)
                        } else {
                            option(value: ff.getId(), ff.getName())
                        }
                    }
                }
            }
        }
        div(class: "row") {
            div(class: "span1", _("Quote"))
            div(class: "span3") {
                input(type: "text", name: "quote", class: "span3", value: role?.quote)
            }
        }
        div(class: "row") {
            div(class: "span1", _("Status"))
            div(class: "span3") {
                select(name: "vitals", class: 'span3') {
                    Vitals.values().each { Vitals v ->
                        if (v == role?.vitals) {
                            option(value: v.name(), v.toString(), selected: true)
                        } else {
                            option(value: v.name(), v.toString())
                        }
                    }
                }
            }
        }
    }
}


