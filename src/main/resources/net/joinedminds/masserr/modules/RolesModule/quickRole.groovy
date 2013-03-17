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
package net.joinedminds.masserr.modules.RolesModule

import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.Messages
import net.joinedminds.masserr.model.Clan
import net.joinedminds.masserr.model.Domain
import net.joinedminds.masserr.model.Generation
import net.joinedminds.masserr.modules.RolesModule

//alert

// Modal
div(id: "quickRoleModal", class: "modal hide fade", tabindex: "-1", role: "dialog", 'aria-labelledby': "quickRoleModalLabel", 'aria-hidden': "true") {
    script {
        raw("var quickRoleMsgs = {};\n")
        raw("quickRoleMsgs.noName = {};\n")
        raw("quickRoleMsgs.noName.heading = '" + Messages.QuickRoles_Msg_NoName_h() + "';\n")
        raw("quickRoleMsgs.noName.message = '" + Messages.QuickRoles_Msg_NoName_Msg() + "';\n")
        raw("quickRoleMsgs.embraced = {};\n")
        raw("quickRoleMsgs.embraced.heading = '" + Messages.QuickRoles_Msg_Embraced_h() + "';\n")
        raw("quickRoleMsgs.embraced.message = '" + Messages.QuickRoles_Msg_Embraced_Msg() + "';\n")
    }
    raw(h.bind(Masserr.getInstance().getRoles(), 'qrModule'))
    div(class: "modal-header") {
        button(type: "button", class: "close", 'data-dismiss': "modal", 'aria-hidden': "true", '×')
        h3(id: "quickRoleModalLabel", _("New Quick NPC"))
    }
    div(class: "modal-body") {
        form(class: "form-horizontal") {
            div(class: "control-group") {
                label(class: "control-label", for: "quickRoleModalInputDomain", _("Domain"))
                div(class: "controls") {
                    select(id: "quickRoleModalInputDomain", name: "domain") {
                        ((RolesModule) my).getDomains().each { Domain domain ->
                            option(value: domain.getId(), domain.getName())
                        }
                    }
                }
            }
            div(class: "control-group") {
                label(class: "control-label", for: "quickRoleModalInputName", _("Name"))
                div(class: "controls") {
                    input(type: "text", id: "quickRoleModalInputName", name: "name", placeholder: _("Name"), required: "true")
                }
            }
            div(class: "control-group") {
                label(class: "control-label", for: "quickRoleModalInputGeneration", _("Generation"))
                div(class: "controls") {
                    select(id: "quickRoleModalInputGeneration", name: "generation") {
                        ((RolesModule) my).getGenerations().each { Generation gen ->
                            option(value: gen.getGeneration(), gen.getGeneration())
                        }
                    }
                }
            }
            div(class: "control-group") {
                label(class: "control-label", for: "quickRoleModalInputEmbraced", _("Embraced"))
                div(class: "controls") {
                    input(type: "text", id: "quickRoleModalInputEmbraced", name: "embraced", placeholder: _("Embraced"),
                            required: "true", pattern: "\\d{3,4}(-\\d{2}(-\\d{2}){0,1}){0,1}")
                }
            }
            div(class: "control-group") {
                label(class: "control-label", for: "quickRoleModalInputClan", _("Clan"))
                div(class: "controls") {
                    select(id: "quickRoleModalInputClan", name: "clan") {
                        ((RolesModule) my).getClans().each { Clan clan ->
                            option(value: clan.getId(), clan.getName())
                        }
                    }
                }
            }
            div(class: "control-group") {
                label(class: "control-label", for: "quickRoleModalInputSire", _("Sire"))
                div(class: "controls") {
                    select(id: "quickRoleModalInputSire", name: "sire") {
                        option(value: '', '')
                    }
                }
            }
        }
    }

    div(class: "modal-footer") {
        button(class: "btn", 'data-dismiss': "modal", 'aria-hidden': "true", _("Close"))
        button(class: "btn btn-primary", onclick: "saveQuickRole()", _("Save"))
    }
}
script(type: "template", id: "t_quickRoleModalAlert") {
    div(id: "quickRoleModalAlert", class: "alert alert-block alert-error fade in") {
        button(type: "button", class: "close", 'data-dismiss': "alert", "×")
        h4(class: "alert-heading", _("heading"))
        p("{{ message }}")
    }
}
script(src: "${resURL}/js/role/quickRole.js")