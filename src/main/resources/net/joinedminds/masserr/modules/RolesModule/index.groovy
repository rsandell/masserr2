import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
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

l.layout(title: _("Roles") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    RolesModule module = my;
    st.include(page: "quickRole.groovy")
    legend {
        span(_("Roles"))
        a(class: "btn btn-mini", role: "button", href: "#quickRoleModal", 'data-toggle': "modal", style: "float: right", title: _("New Quick NPC")) {
            i(class: "icon-asterisk", alt: _("New Quick NPC"))
        }
        a(class: "btn btn-mini", href: "newRole/edit", style: "float: right", title: _("New Role")) {
            i(class: "icon-plus", alt: _("New Role"))
        }
    }
    div(class: "row") {
        module.getRoles().each {Role role ->
            div(class: "span1") {
                div(class: "well") {
                    h4(role.getName())
                }
            }
        }
    }
}


