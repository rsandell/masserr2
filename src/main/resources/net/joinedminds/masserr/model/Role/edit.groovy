import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.*
import net.joinedminds.masserr.modules.RolesModule

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
def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Edit Role") + " " + Masserr.getInstance().getAppName()) {
    Role role = my;
    RolesModule module = Masserr.getInstance().getRoles();
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
                li { a(href: "#settings", _("Settings")) }
            }

            div(id: "roleTabContent", class: "tab-content") {
                div(class: "tab-pane active", id: "basic") {
                    div(class: "row") {
                        div(class: "span4") {
                            div(class: "row") {
                                div(class: "span1") {
                                    p { raw("&nbsp;") }
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
                                    input(type: "text", class: "span3", id: "name")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Player"))
                                div(class: "span3") {
                                    input(class: "span3", type: "text", id: "player")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Generation"))
                                div(class: "span3") {
                                    select(class: "span3", id: "generation", name: "generation") {
                                        module.getGenerations().each { Generation gen ->
                                            option(value: gen.getId(), gen.getGeneration())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Embraced"))
                                div(class: "span3") {
                                    input(type: "text", class: "span3", id: "embraced")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Clan"))
                                div(class: "span3") {
                                    select(id: "clan", class: "span3") {
                                        module.getClans().each { Clan clan ->
                                            option(value: clan.getId(), clan.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Sire"))
                                div(class: "span3") {
                                    input(type: "text", id: "sire", class: "span3")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Nature"))
                                div(class: "span3") {
                                    input(type: "text", id: "nature", class: "span3")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Demeanor"))
                                div(class: "span3") {
                                    input(type: "text", id: "demeanor", class: "span3")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Path/Road"))
                                div(class: "span2") {
                                    input(type: "text", id: "path", class: "span2")
                                }
                                div(class: "span1") {
                                    input(type: "number", class: "span1", name: "pathDots", value: 0)
                                }
                            }
                        }
                        div(class: "span6") {
                            div(class: "row") {
                                div(class: "span3") {
                                    p { strong(_("Disciplines")) }
                                }
                                div(class: "span1") {
                                    button(class: "btn btn-mini", onclick: "addDiscipline()") {
                                        i(class: "icon-plus")
                                    }
                                }
                            }
                            module.getClans().get(0).getClanDisciplines().each { Discipline discipline ->
                                div(class: "row") {
                                    div(class: "span3") {
                                        select(name: "discipline", class: "span3") {
                                            module.getDisciplines().each { Discipline aD ->
                                                if (aD.getId() == discipline.getId()) {
                                                    option(value: aD.getId(), selected: "true", aD.getName())
                                                } else {
                                                    option(value: aD.getId(), aD.getName())
                                                }
                                            }
                                        }
                                    }
                                    div(class: "span1") {
                                        input(type: "number", class: "span1", value: 0)
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("+Health"))
                                div(class: "span1") {
                                    input(type: "number", class: "span1", value: 0, name: "healthLevels")
                                }
                                div(class: "span2") {
                                    input(type: "checkbox", class: "span1", name: "injury")
                                    span(_("-Injury"))
                                }
                            }
                            List<FightOrFlight> forms = module.getFightOrFlights()
                            div(class: "row") {
                                div(class: "span1", _("Fight"))
                                div(class: "span3") {
                                    select(name: "fightForm", class: "span3") {
                                        option(value: '', '')
                                        forms.each { FightOrFlight f ->
                                            option(value: f.getId(), f.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Flight"))
                                div(class: "span3") {
                                    select(name: "flightForm", class: "span3") {
                                        option(value: '', '')
                                        forms.each { FightOrFlight f ->
                                            option(value: f.getId(), f.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1", _("Quote"))
                                div(class: "span3") {
                                    input(type: "text", id: "quote", class: "span3")
                                }
                            }
                        }
                    }
                }
                div(class: "tab-pane", id: "magic") { p("Hello This is your profile") }
                div(class: "tab-pane", id: "attributes") { p("Hello You have messages") }
                div(class: "tab-pane", id: "settings") { p("Your settings") }
            }
        }
    }
    script(type: "template", id: "statsContent") {
        table(class: "table table-condensed") {
            tr {
                td(_("NPC"))
                td {
                    input(type: "checkbox", name: "npc")
                }
            }
            tr {
                td(_("Status"))
                td {
                    select(name: "vitals", class: 'input-small', style: "margin-bottom: 0") {
                        Vitals.values().each { Vitals v ->
                            option(value: v.name(), v.toString())
                        }
                    }
                }
            }
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


