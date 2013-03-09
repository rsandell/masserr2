package net.joinedminds.masserr.modules.AdminModule

import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Ability
import net.joinedminds.masserr.model.Discipline
import net.sf.json.JSONObject

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

l.layout(title: _("Disciplines") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    raw(f.bind(my, 'admin'))

    script() {
        raw("var disciplines = [")
        my.getDisciplines().each() { Discipline discipline ->
            raw("jQuery.parseJSON( '" + JSONObject.fromObject(new Discipline(discipline)).toString() + "' ), ")
        }
        raw("];\n")
    }

    script(type: "template", id: "t_disciplineForm") {
        tr(discipline: "{{ navId }}") {
            td {
                input(type: "hidden", name: "id", value: "{{ id }}")
                small("{{ id }}")
            }
            td {
                input(type: 'text', name: 'name', value: '{{ name }}', required: "true")
            }
            td {
                select(name: "retestAbility_id") {
                    option(value: '', '')
                    my.getAbilities().each() { Ability ability ->
                        option(value: ability.getId(), ability.getName())
                    }
                }
            }
            td {
                input(type: 'url', name: 'docUrl', value: '{{ docUrl }}')
            }
            td {
                button(type: 'button', class: 'btn btn-mini btn-primary', onclick: "submitDiscipline('{{ navId }}')") {
                    i(class: 'icon-check icon-white')
                }
            }
        }
    }
    script(type: "template", id: "t_disciplineRow") {
        tr(discipline: "{{ navId }}") {
            td {
                small("{{ id }}")
            }
            td('{{ name }}')
            td('{{ retestAbilityName }}')
            td("{{ urlPart }}")
            td {
                a(class: "btn btn-mini", href: "javascript:editRow('{{ navId }}')") {
                    i(class: 'icon-edit')
                }
            }
        }
    }
    legend(_("Disciplines"))
    table(class: "table table-hover", id:"disciplinesTable") {
        tr(class: "heading") {
            th(width: "10%", _("Id"))
            th(width: "30%", _("Name"))
            th(width: "25%", _("Retest Ability"))
            th(width: "25%", _("Doc URL"))
            th(width: "10%") {
                button(class: "btn btn-mini", onclick: "newDiscipline()") {
                    i(class: "icon-plus")
                }
            }
        }
    }
    script(src: "${resURL}/js/admin/disciplines.js")

}