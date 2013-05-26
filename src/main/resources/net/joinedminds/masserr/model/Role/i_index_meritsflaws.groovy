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



import lib.LayoutTagLib
import net.joinedminds.masserr.model.MeritOrFlaw
import net.joinedminds.masserr.model.NotedType
import net.joinedminds.masserr.model.Role

LayoutTagLib l = namespace(LayoutTagLib)
Role role = my

table(width: "100%") {
    tr {
        td(width: "50%", valign: "top", style: "padding-right: 2em") {
            table(class: "table table-condensed table-hover table-bordered", style: "width: 100%") {
                tr {
                    th(colspan: "3", _("Merits"))
                }
                role.merits.each {NotedType<MeritOrFlaw> m ->
                    tr {
                        td(width: "43%") {
                            l.doc(it: m.type)
                        }
                        td(width: "50%") {
                            small(m.notes)
                        }
                        td(width: "7%", m.type.points)
                    }
                }
            }
        }
        td(width: "50%", valign: "top", style: "padding-left: 2em") {
            table(class: "table table-condensed table-hover table-bordered", style: "width: 100%") {
                tr {
                    th(colspan: "3", _("Flaws"))
                }
                role.flaws.each {NotedType<MeritOrFlaw> m ->
                    tr {
                        td(width: "43%") {
                            l.doc(it: m.type)
                        }
                        td(width: "50%") {
                            small(m.notes)
                        }
                        td(width: "7%", Math.abs(m.type.points))
                    }
                }
            }
        }
    }
}