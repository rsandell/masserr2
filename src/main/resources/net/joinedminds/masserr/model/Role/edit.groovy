import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Role

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
    ul(class: "nav nav-tabs", id: "myTab") {
        li(class: "active") { a(href: "#home", _("Home")) }
        li { a(href: "#profile", _("Profile")) }
        li { a(href: "#messages", _("Messages")) }
        li { a(href: "#settings", _("Settings")) }
    }

    div(id: "myTabContent", class: "tab-content") {
        div(class: "tab-pane active", id: "home") { p("Hello I'm Home") }
        div(class: "tab-pane", id: "profile") { p("Hello This is your profile") }
        div(class: "tab-pane", id: "messages") { p("Hello You have messages") }
        div(class: "tab-pane", id: "settings") { p("Your settings") }
    }

    script(src: "${resURL}/js/role/edit.js")
}


