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
import net.joinedminds.masserr.model.Discipline
import net.joinedminds.masserr.model.Player
import net.joinedminds.masserr.model.Role
import net.joinedminds.masserr.modules.RolesModule

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Edit Role") + " " + Masserr.getInstance().getAppName()) {
    Role role = my;
    RolesModule module = Masserr.getInstance().getRoles();
    Functions f = h;
    raw(f.bind(module, "module"))
    st.include(page: "quickRole.groovy", class: RolesModule.class, it: module)
    div(id: "playerModal", class: "modal hide fade", tabindex: "-1", role: "dialog", 'aria-labelledby': "playerModalLabel", 'aria-hidden': "true") {
        div(class: "modal-header") {
            button(type: "button", class: "close", 'data-dismiss': "modal", 'aria-hidden': "true", '×')
            h3(id: "playerModalLabel", _("New Player"))
        }
        div(class: "modal-body") {
            st.include(class: Player.class, page: "i_playerForm.groovy")
        }
        div(class: "modal-footer") {
            button(class: "btn", 'data-dismiss': "modal", 'aria-hidden': "true", _("Close"))
            button(class: "btn btn-primary", onclick: "savePlayer()", _("Save"))
        }
    }
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
                li(class: "active") { a(href: "#basic", id: "aBasicTabLink", _("Basic")) }
                li { a(href: "#magic", id: "aMagicTabLink", _("Magic")) }
                li { a(href: "#attributes", id: "aAttributesTabLink", _("Attributes")) }
                li { a(href: "#misc", id: "aMiscTabLink", _("Misc")) }
            }

            div(id: "roleTabContent", class: "tab-content") {
                div(class: "tab-pane active", id: "basic") {
                    st.include(page: "i_edit_basic.groovy")
                    div(class: "row") {
                        div(class: "span11") {
                            div(class: "span2 offset3") {
                                button(class: "btn btn-small", onclick: "activateTab('#aMiscTabLink')") {
                                    i(class: "icon-chevron-left")
                                }
                                button(class: "btn btn-small", onclick: "activateTab('#aMagicTabLink')") {
                                    i(class: "icon-chevron-right")
                                }
                            }
                            div(class: "span3") {
                                button(class: "btn btn-primary", style: "margin-right: 1em", _("OK"))
                                button(class: "btn", _("Cancel"))
                            }
                        }
                    }
                }
                div(class: "tab-pane", id: "magic") {
                    st.include(page: "i_edit_magic.groovy")
                    div(class: "row") {
                        div(class: "span11") {
                            div(class: "span2 offset3") {
                                button(class: "btn btn-small", onclick: "activateTab('#aBasicTabLink')") {
                                    i(class: "icon-chevron-left")
                                }
                                button(class: "btn btn-small", onclick: "activateTab('#aAttributesTabLink')") {
                                    i(class: "icon-chevron-right")
                                }
                            }
                            div(class: "span3") {
                                button(class: "btn btn-primary", style: "margin-right: 1em", _("OK"))
                                button(class: "btn", _("Cancel"))
                            }
                        }
                    }
                }
                div(class: "tab-pane", id: "attributes") {
                    st.include(page: "i_edit_attributes.groovy")
                    div(class: "row") {
                        div(class: "span11") {
                            div(class: "span2 offset3") {
                                button(class: "btn btn-small", onclick: "activateTab('#aMagicTabLink')") {
                                    i(class: "icon-chevron-left")
                                }
                                button(class: "btn btn-small", onclick: "activateTab('#aMiscTabLink')") {
                                    i(class: "icon-chevron-right")
                                }
                            }
                            div(class: "span3") {
                                button(class: "btn btn-primary", style: "margin-right: 1em", _("OK"))
                                button(class: "btn", _("Cancel"))
                            }
                        }
                    }
                }
                div(class: "tab-pane", id: "misc") {
                    st.include(page: "i_edit_misc.groovy")
                    div(class: "row") {
                        div(class: "span11") {
                            div(class: "span2 offset3") {
                                button(class: "btn btn-small", onclick: "activateTab('#aAttributesTabLink')") {
                                    i(class: "icon-chevron-left")
                                }
                                button(class: "btn btn-small", onclick: "activateTab('#aBasicTabLink')") {
                                    i(class: "icon-chevron-right")
                                }
                            }
                            div(class: "span3") {
                                button(class: "btn btn-primary", style: "margin-right: 1em", _("OK"))
                                button(class: "btn", _("Cancel"))
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

