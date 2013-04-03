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
import net.joinedminds.masserr.model.Ability
import net.joinedminds.masserr.model.MeritOrFlaw
import net.sf.json.JSONObject

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Merits & Flaws") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    raw(f.bind(my, 'admin'))
    script {
        raw("var mfTypes =[")
        MeritOrFlaw.Type.values().each { type ->
            raw("'"+type.name()+"',")
        }
        raw("];\n")
    }
    script(type: "template", id: "t_mfsRow") {
        tr(mf: "{{ navId }}") {
            td {
                small("{{ id }}")
            }
            td {
                small("{{ type }}")
            }
            td("{{ name }}")
            td("{{ points }}")
            td("{{ urlPart }}")
            td {
                a(class: "btn btn-mini",  href: "javascript:editRow('{{ navId }}')") {
                    i(class: 'icon-edit')
                }
            }
        }
    }
    script(type: "template", id: "t_mfsForm") {
        tr(mf: "{{ navId }}") {
            td {
                input(type: 'hidden', name: 'id', value: "{{ id }}")
                small("{{ id }}")
            }
            td("{{ generatedTypesSelect }}")
            td {
                input(type: 'text', name: 'name', value: '{{ name }}', required: "true")
            }
            td {
                input(type: 'number', max: 10, min: -10, name: 'points', value: '{{ points }}')
            }
            td {
                input(type: 'url', name: 'docUrl', value: '{{ docUrl }}')
            }
            td {
                button(type: 'button', class: 'btn btn-mini btn-primary', onclick: "submitMf('{{ navId }}')") {
                    i(class: 'icon-check icon-white')
                }
            }
        }
    }

    legend(_("Merit & Flaws"))
    table(class: "table table-hover", id:"mfsTable") {
        tr(class: "heading") {
            th(width: "10%", _("Id"))
            th(width: "15%", _("Type"))
            th(width: "25%", _("Name"))
            th(width: "10%", _("Points"))
            th(width: "30%", _("Doc URL"))
            th(width: "10%") {
                button(class: "btn btn-mini", onclick: "newMf()") {
                    i(class: "icon-plus")
                }
            }
        }
    }
    script(src: "${resURL}/js/admin/meritflaws.js")

}


