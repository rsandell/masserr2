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
        div(class: "span2") {
            div(class: "row") {
                ul(class: "nav nav-list affix", 'data-offset-top': "200") {
                    li("Hello")
                    li("World")
                }
            }
        }
        div(class: "span10") {
            ul(class: "nav nav-tabs", id: "myTab") {
                li(class: "active") { a(href: "#basic", _("Basic")) }
                li { a(href: "#magic", _("Magic")) }
                li { a(href: "#attributes", _("Attributes")) }
                li { a(href: "#settings", _("Settings")) }
            }

            div(id: "myTabContent", class: "tab-content") {
                div(class: "tab-pane active", id: "basic") {
                    div(class: "row") {
                        div(class: "span5") {
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "name", _("Name"))
                                }
                                div(class: "span4") {
                                    input(type: "text", id: "name")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "player", _("Player"))
                                }
                                div(class: "span4") {
                                    input(type: "text", id: "player")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "generation", _("Generation"))
                                }
                                div(class: "span4") {
                                    select(id: "generation") {
                                        module.getGenerations().each { Generation gen ->
                                            option(value: gen.getId(), gen.getGeneration())
                                        }
                                    }
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "embraced", _("Embraced"))
                                }
                                div(class: "span4") {
                                    input(type: "text", id: "embraced")
                                }
                            }
                            div(class: "row") {
                                div(class: "span1") {
                                    label(for: "clan", _("Clan"))
                                }
                                div(class: "span4") {
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
                                div(class: "span4") {
                                    input(type: "text", id: "sire")
                                }
                            }
                        }
                        div(class: "span5") {
                            table(class: "table table-condensed") {
                                tr {
                                    th(colspan: "2", _("Disciplines"))
                                }
                                module.getClans().get(0).getClanDisciplines().each { Discipline discipline ->
                                    tr {
                                        td(discipline.getName())
                                        td {
                                            input(type: "number", class: "input-mini", value: 0)
                                        }
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


