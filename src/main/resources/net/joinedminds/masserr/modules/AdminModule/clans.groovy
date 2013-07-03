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
import net.joinedminds.masserr.model.Discipline
import net.sf.json.JSONObject

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Clans & Bloodlines") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    st.bind(value: my, var: 'admin')

    script() {
        raw("var disciplines = [")
        my.getDisciplines().each() { Discipline discipline ->
            raw("jQuery.parseJSON( '" + JSONObject.fromObject(new Discipline(discipline)).toString() + "' ), ")
        }
        raw("];\n")
    }
    script() {
        raw("var logoUrlBase = '$resURL/logo/48/';")
    }
    script(type: "template", id: "t_cRow") {
        tr(clan: "{{ navId }}") {
            td {
                small("{{ id }}")
            }
            td {
                img(src: '{{ logo }}', alt: 'logo', width: '48', height: '48')
            }
            td("{{ name }}")
            td(style: "text-align: right;", "{{ baseIncome }}")
            td("{{ weaknesses }}")
            td("{{ disciplinesUl }}")
            td("{{ urlPart }}")
            td {
                a(class: "btn btn-mini", href: "javascript:editRow('{{ navId }}')", title: _("Edit")) {
                    i(class: 'icon-edit')
                }
                raw("&nbsp;")
                a(class: "btn btn-mini", href: "javascript:editDisciplines('{{ navId }}')", title: _("Disciplines")) {
                    raw("&nbsp;")
                    i(class: 'icon-bolt')
                    raw("&nbsp;")
                }
            }
        }
    }
    script(type: "template", id: "t_cForm") {
        tr(clan: "{{ navId }}") {
            td {
                input(type: 'hidden', name: 'id', value: "{{ id }}")
                small("{{ id }}")
            }
            td {
                img(src: '{{ logo }}', alt: 'logo', width: '48', height: '48')
            }
            td {
                input(type: 'text', name: 'name', value: '{{ name }}', required: "true", style: "width: 100%")
            }
            td {
                input(type: 'number', min: 0, name: 'baseIncome', value: '{{ baseIncome }}', style: "width: 100%")
            }
            td {
                input(type: 'text', name: 'weaknesses', value: '{{ weaknesses }}', required: "true", style: "width: 100%")
            }
            td("{{ disciplinesUl }}")
            td {
                input(type: 'url', name: 'docUrl', value: '{{ docUrl }}', style: "width: 100%")
            }
            td {
                button(type: 'button', class: 'btn btn-mini btn-primary', onclick: "submitClan('{{ navId }}')", title: _("Save")) {
                    i(class: 'icon-check icon-white')
                }
            }
        }
    }

    div(id: "disciplinesModal", class: "modal hide fade", tabindex: "-1", role: "dialog", 'aria-labelledby': "disciplinesModalLabel", 'aria-hidden': "true") {
        div(class: "modal-header") {
            button(type: "button", class: "close", 'data-dismiss': "modal", 'aria-hidden': "true", '×')
            h3(id: "disciplinesModalLabel", _("Edit disciplines"))
            h4(id: "disciplinesModalClanLabel", "Clan")
            input(type: "hidden", name: "id", id: "disciplinesModalClanId", value: "")
        }

        div(class: "modal-body") {
            table(width: "100%") {
                tr {
                    td(width: "45%") {
                        div(class: "top-labelled-div", divlabel: _("All"), style: "width: 100%") {
                            select(id: "allDisciplinesSelect", multiple: true, rows: 10, style: "width: 100%") {

                            }
                        }
                    }
                    td(width: "10%", align: "center") {
                        button(class: "btn btn-mini", onclick: "addClanDiscipline()", title: _("Add Clan Discipline")) {
                            i(class: "icon-double-angle-right")
                        }
                        br()
                        button(class: "btn btn-mini", onclick: "removeClanDiscipline()", title: _("Remove Clan Discipline")) {
                            i(class: "icon-double-angle-left")
                        }
                    }
                    td(width: "45%") {
                        div(class: "top-labelled-div", divlabel: _("Clan"), style: "width: 100%") {
                            select(id: "clanDisciplinesSelect", name: "clanDisciplines", multiple: true, rows: 10, style: "width: 100%") {

                            }
                        }
                    }
                }
            }
        }

        div(class: "modal-footer") {
            button(class: "btn", 'data-dismiss': "modal", 'aria-hidden': "true", _("Close"))
            button(class: "btn btn-primary", onclick: "saveClanDisciplines()", _("Save"))
        }
    }

    legend(_("Clans & Bloodlines"))
    table(class: "table table-hover", id: "clansTable") {
        tr(class: "heading") {
            th(width: "5%", _("Id"))
            th(width: "5%") { st.nbsp() }
            th(width: "15%", _("Name"))
            th(width: "10%", _("Base Income"))
            th(width: "20%", _("Weaknesses"))
            th(width: "10%", _("Disciplines"))
            th(width: "25%", _("Doc URL"))
            th(width: "10%") {
                button(class: "btn btn-mini", onclick: "newClan()", title: _("New")) {
                    i(class: "icon-plus")
                }
            }
        }
    }
    script(src: "${resURL}/js/admin/clans.js")

}
