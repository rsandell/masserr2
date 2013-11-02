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

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Merits & Flaws") + " " + Masserr.getInstance().getAppName()) {
    st.adjunct(includes: "org.kohsuke.stapler.angularjs")
    Functions f = h;
    st.bind(value: my, var: 'admin')

    div("ng-app": "") {
        script(src: "${resURL}/js/angular/admin/meritsflaws/controller.js")
        legend {
            span(_("Merit & Flaws"))
            form(class: "pull-right") {
                input(type: "text", "ng-model": "query", placeholder: _("Filter"), class: "input-medium search-query")
            }
        }
        table(class: "table table-hover", id: "mfsTable", "ng-controller": "MeritsFlawsCtrl") {
            tr(class: "heading") {
                th(width: "10%", _("Id"))
                th(width: "15%", _("Type"))
                th(width: "25%", _("Name"))
                th(width: "10%", _("Points"))
                th(width: "30%", _("Doc URL"))
                th(width: "10%") {
                    button(class: "btn btn-mini", "ng-click": "newMerit()") {
                        i(class: "icon-plus")
                    }
                }
            }
            tbody("ng-repeat": "trait in merits | filter:query") {
                tr(mf: "{{ trait.id }}", "ng-if": "!isEditing(trait)") {
                    td {
                        small("{{ trait.id }}")
                    }
                    td {
                        small("{{ trait.type }}")
                    }
                    td("{{ trait.name }}")
                    td(aligh: "right", "{{ trait.points }}")
                    td {
                        a("ng-if": "trait.docUrl", "ng-href": "{{ trait.docUrl }}", target: "_new", _("{{ trait.docUrl }}"))
                    }
                    td {
                        button(type: 'button', class: 'btn btn-mini btn-primary', "ng-click": "edit(trait)") {
                            i(class: 'icon-edit')
                        }
                    }
                }
                tr(mf: "{{ trait.id }}", "ng-if": "isEditing(trait)") {
                    td {
                        small("{{ trait.id }}")
                    }
                    td {
                        select("ng-model": "trait.type", "ng-options": "type for type in types")
                    }
                    td {
                        input(type: 'text', "ng-model": "trait.name", required: "true")
                    }
                    td(aligh: "right") {
                        input(type: 'number', max: 10, min: -10, "ng-model": 'trait.points', class: "input-mini")
                    }
                    td {
                        input(type: 'url', "ng-model": 'trait.docUrl')
                    }
                    td {
                        button(type: 'button', class: 'btn btn-mini btn-primary', "ng-click": "save(trait)") {
                            i(class: 'icon-check icon-white')
                        }
                        st.nbsp()
                        button(type: 'button', class: 'btn btn-mini', "ng-click": "stopEdit(trait)") {
                            i(class: 'icon-minus')
                        }
                    }
                }
            }
        }
    }

}


