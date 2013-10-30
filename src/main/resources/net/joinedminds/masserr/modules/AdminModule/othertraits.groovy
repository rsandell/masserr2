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
import net.joinedminds.masserr.model.OtherTrait
import net.sf.json.JSONObject

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Other Traits") + " " + Masserr.getInstance().getAppName()) {
    st.adjunct(includes: "org.kohsuke.stapler.angularjs")
    Functions f = h;
    st.bind(value: my, var: 'admin')
    div('ng-app': "") {
        script(src: "${resURL}/js/angular/admin/other-traits/controller.js")
        /*script(type: "template", id: "t_traitRow") {
            tr(ability: "{{ navId }}") {
                td {
                    small("{{ id }}")
                }
                td("{{ name }}")
                td("{{ urlPart }}")
                td {
                    a(class: "btn btn-mini",  href: "javascript:editTraitsRow('{{ navId }}')") {
                        i(class: 'icon-edit')
                    }
                }
            }
        }
        script(type: "template", id: "t_traitForm") {
            tr(ability: "{{ navId }}") {
                td {
                    input(type: 'hidden', name: 'id', value: "{{ id }}") {
                        small("{{ id }}")
                    }
                }
                td {
                    input(type: 'text', name: 'name', value: '{{ name }}', required: "true")
                }
                td {
                    input(type: 'url', name: 'docUrl', value: '{{ docUrl }}')
                }
                td {
                    button(type: 'button', class: 'btn btn-mini btn-primary', onclick: "submitTrait('{{ navId }}')") {
                        i(class: 'icon-check icon-white')
                    }
                }
            }
        }*/

        legend(_("Other Traits"))
        table(class: "table table-hover", id:"otherTraitsTable", "ng-controller": "OtherTraitsCtrl") {
            tr(class: "heading") {
                th(width: "10%", _("Id"))
                th(width: "40%", _("Name"))
                th(width: "40%", _("Doc URL"))
                th(width: "10%") {
                    button(class: "btn btn-mini", onclick: "newTrait()") {
                        i(class: "icon-plus")
                    }
                }
            }
            tr("ng-repeat": "trait in traits") {
                td {
                    small("{{ trait.id }}")
                }
                td(_("{{ trait.name }}"))
                td(_("{{ trait.docUrl }}"))
                td(raw("&nbsp;"))
            }
        }
    }
}


