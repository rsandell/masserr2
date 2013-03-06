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

import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.modules.AdminModule

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("MasserrAdmin") + " " + Masserr.getInstance().getAppName()) {
    AdminModule module = my

    div(class: "row") {
        div(class: "span3") {
            div(class: "well") {
                a(href: "config") {h3(_("Config"))}
                p(_("Change global configuration properties"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                a(href: "abilities") {h3(_("Abilities"))}
                p(_("Edit global Abilities list"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                a(href: "othertraits") {h3(_("Other Traits"))}
                p(_("Edit global Other Traits list"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                a(href: "disciplines") {h3(_("Disciplines"))}
                p(_("Edit global Disciplines list"))
            }
        }
    }
    div(class: "row") {
        div(class: "span3") {
            div(class: "well") {
                a(href: "paths") {h3(_("Paths"))}
                p(_("Edit Thaumaturgy and Necromancy Paths"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                h3(_("Config"))
                p(_("Change global configuration properties"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                h3(_("Config"))
                p(_("Change global configuration properties"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                h3(_("Config"))
                p(_("Change global configuration properties"))
            }
        }
    }
}