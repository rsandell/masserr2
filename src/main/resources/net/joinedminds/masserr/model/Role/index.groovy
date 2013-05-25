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
import net.joinedminds.masserr.model.Role

Role role = my;

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Role") + " " + role.name + " " + Functions.emptyIfNull(Masserr.getInstance().getAppName())) {
    legend(role.name) {
        img(src: role.clan.getLogoPath(resURL, "48"))
        raw("&nbsp;")
    }
    div(class: "accordion", id: "accordion2") {
        div(class: "accordion-group") {
            div(class: "accordion-heading") {
                a(class: "accordion-toggle", "data-toggle": "collapse", "data-parent": "#accordion2", href: "#collapseGeneral", _("General"))
            }
            div(id: "collapseGeneral", class: "accordion-body collapse in") {
                div(class: "accordion-inner") {
                    st.include(page: "i_index_general.groovy")
                }
            }
        }
        div(class: "accordion-group") {
            div(class: "accordion-heading") {
                a(class: "accordion-toggle", "data-toggle": "collapse", "data-parent": "#accordion2", href: "#collapseAbilities", _("Abilities"))
            }
            div(id: "collapseAbilities", class: "accordion-body collapse") {
                div(class: "accordion-inner") {
                    st.include(page: "i_index_abilities.groovy")
                }
            }
        }
        div(class: "accordion-group") {
            div(class: "accordion-heading") {
                a(class: "accordion-toggle", "data-toggle": "collapse", "data-parent": "#accordion2", href: "#collapseDisciplines", _("Disciplines & Magic"))
            }
            div(id: "collapseDisciplines", class: "accordion-body collapse") {
                div(class: "accordion-inner") {
                    st.include(page: "i_index_disciplines.groovy")
                }
            }
        }
    }
}
