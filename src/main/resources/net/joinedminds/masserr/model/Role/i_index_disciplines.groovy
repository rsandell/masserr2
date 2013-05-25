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
import net.joinedminds.masserr.model.*

Role role = my
Functions f = h;

table(style: "width: 100%") {
    tr {
        td(style: "width: 33%; padding-right: 2em", align: "left", valign: "top") {
            table(class: "table table-condensed table-hover table-bordered") {
                tr {
                    th(colspan: 2, _("Disciplines"))
                }
                role.disciplines.each { DottedType<Discipline> discipline ->
                    tr {
                        td(discipline.type.name)
                        td(discipline.dots)
                    }
                }
            }
        }

        td(style: "width: 33%; padding-right: 2em; padding-left: 2em", align: "center", valign: "top") {
            if (role.thaumaturgicalPaths != null && !role.thaumaturgicalPaths.empty) {
                table(class: "table table-condensed table-hover table-bordered") {
                    tr {
                        th(colspan: 2, _("Thaumaturgy") + (f.prependIfNotNullOrEmpty(role.thaumaType, " - ", "")))
                    }
                    role.thaumaturgicalPaths.each { DottedType<Path> path ->
                        tr {
                            td(path.type.name)
                            td(path.dots)
                        }
                    }
                }
            }
        }
        td(style: "width: 33%; padding-left: 2em", align: "right", valign: "top") {
            if (role.necromancyPaths != null && !role.necromancyPaths.empty) {
                table(class: "table table-condensed table-hover table-bordered") {
                    tr {
                        th(colspan: 2, _("Necromancy") + (f.prependIfNotNullOrEmpty(role.necromancyType, " - ", "")))
                    }
                    role.necromancyPaths.each { DottedType<Path> path ->
                        tr {
                            td(path.type.name)
                            td(path.dots)
                        }
                    }
                }
            }
        }
    }
}
if (role.rituals != null && !role.rituals.empty) {
    table(class: "table table-condensed table-hover table-bordered") {
        tr {
            th(colspan: 3, _("Rituals"))
        }
        role.rituals.sort { Ritual r -> r.level * -1 }
        .sort { Ritual r, Ritual t -> r.ritualType.name <=> t.ritualType.name }
        .each { Ritual ritual ->
            tr {
                td(ritual.ritualType.name)
                td(ritual.name)
                td(ritual.level)
            }
        }
    }
}