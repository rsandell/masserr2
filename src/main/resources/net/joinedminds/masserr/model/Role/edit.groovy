import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Clan
import net.joinedminds.masserr.model.Discipline
import net.joinedminds.masserr.model.Generation
import net.joinedminds.masserr.model.Role
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
                ul(class: "nav nav-list affix", 'data-offset-top': "200") {
                    li("Hello")
                    li("World")
                }
            }
        }
        div(class: "span11") {
            ul(class: "nav nav-tabs", id: "myTab") {
                li(class: "active") { a(href: "#basic", _("Basic")) }
                li { a(href: "#magic", _("Magic")) }
                li { a(href: "#attributes", _("Attributes")) }
                li { a(href: "#settings", _("Settings")) }
            }

            div(id: "myTabContent", class: "tab-content") {
                div(class: "tab-pane active", id: "basic") {
                    div(class: "row") {
                        div(class: "span4") {
                            div(class: "row") {
                                    div(class: "span1", _("Name"))
                                    input(type: "text", class: "span3", id: "name")
                            }
                            div(class: "row") {
                                div(class: "span1", _("Player"))

                                    input(class: "span3", type: "text", id: "player")

                            }
                            div(class: "row") {
                                div(class: "span1", _("Generation"))

                                    select(class: "span3", id: "generation", name: "generation") {
                                        module.getGenerations().each { Generation gen ->
                                            option(value: gen.getId(), gen.getGeneration())
                                        }
                                    }

                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "embraced", _("Embraced"))
                                }
                                div(class: "span3") {
                                    input(type: "text", id: "embraced")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "clan", _("Clan"))
                                }
                                div(class: "span3") {
                                    select(id: "clan") {
                                        module.getClans().each { Clan clan ->
                                            option(value: clan.getId(), clan.getName())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "sire", _("Sire"))
                                }
                                div(class: "span3") {
                                    input(type: "text", id: "sire")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "nature", _("Nature"))
                                }
                                div(class: "span3") {
                                    input(type: "text", id: "nature")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "demeanor", _("Demeanor"))
                                }
                                div(class: "span3") {
                                    input(type: "text", id: "demeanor")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "path", _("Path/Road"))
                                }
                                div(class: "span2") {
                                    input(type: "text", id: "path")
                                }
                                div(class: "span1") {
                                    input(type: "number", class: "input-mini", name: "pathDots", value: 0)
                                }
                            }
                        }
                        div(class: "span6") {
                            div(class: "row") {
                                div(class: "span5") {
                                    strong(_("Disciplines"))
                                }
                            }
                            module.getClans().get(0).getClanDisciplines().each { Discipline discipline ->
                                div(class: "row") {
                                    div(class: "span3") {
                                        select(name: "discipline") {
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
                                        input(type: "number", class: "input-mini", value: 0)
                                    }
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
    script(src: "${resURL}/js/role/edit.js")
}


