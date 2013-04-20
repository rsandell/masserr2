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
import net.joinedminds.masserr.model.*
import net.joinedminds.masserr.modules.RolesModule

Role role = my;
RolesModule module = Masserr.getInstance().getRoles();
Functions f = h;

div(class: "row") {
    List<Path> thaumaPaths = module.getPaths(Path.Type.Thaumaturgy)
    List<Path> necromancyPaths = module.getPaths(Path.Type.Necromancy)
    script(type: "template", id: "t_thaumaSelect") {
        div(class: "row") {
            div(class: "span4") {
                select(class: "span4", name: "thaumaturgicalPaths[][id]") {
                    option(value: "", "")
                    thaumaPaths.each { Path p ->
                        option(value: p.id, p.getName())
                    }
                }
            }
            div(class: "span1") {
                input(type: "number", class: "span1", min: 0, max: 5, name: "thaumaturgicalPaths[][dots]")
            }
        }
    }
    script(type: "template", id: "t_necromancySelect") {
        div(class: "row") {
            div(class: "span4") {
                select(class: "span4", name: "necromancyPaths[][id]") {
                    option(value: "", "")
                    necromancyPaths.each { Path p ->
                        option(value: p.id, p.getName())
                    }
                }
            }
            div(class: "span1") {
                input(type: "number", class: "span1", min: 0, max: 5, name: "necromancyPaths[][dots]")
            }
        }
    }
    div(id: "thaumaPathsDiv", class: "span5 msr-region-bordered", regionlabel: _("Thaumaturgy Paths")) {
        button(class: "btn btn-mini region-btn", onclick: "addThaumaPath()") {
            i(class: "icon-plus")
        }
        div(class: "row") {
            div(class: "span5") {
                input(type: "text", name: "thaumaSchoolName", placeholder: "School", class: "span5", value: role?.thaumaType)
            }
        }
        List<DottedType<Path>> roleThaumaPaths = role?.thaumaturgicalPaths
        if (roleThaumaPaths != null && !roleThaumaPaths.isEmpty()) {
            roleThaumaPaths.each { DottedType<Path> rp ->
                div(class: "row") {
                    div(class: "span4") {
                        select(class: "span4", name: "thaumaturgicalPaths[][id]") {
                            option(value: "", "")
                            thaumaPaths.each { Path p ->
                                if (rp.type.id == p.id) {
                                    option(value: p.id, p.getName(), selected: true)
                                } else {
                                    option(value: p.id, p.getName())
                                }
                            }
                        }
                    }
                    div(class: "span1") {
                        input(type: "number", class: "span1", min: 0, max: 5,
                                name: "thaumaturgicalPaths[][dots]", value: rp.dots)
                    }
                }
            }
        } else {
            div(class: "row") {
                div(class: "span4") {
                    select(class: "span4", name: "thaumaturgicalPaths[][id]") {
                        option(value: "", "")
                        thaumaPaths.each { Path p ->
                            option(value: p.id, p.getName())
                        }
                    }
                }
                div(class: "span1") {
                    input(type: "number", class: "span1", min: 0, max: 5, name: "thaumaturgicalPaths[][dots]")
                }
            }
        }
    }
    div(id: "necromancyPathsDiv", class: "span5 msr-region-bordered", regionlabel: _("Necromancy Paths")) {
        button(class: "btn btn-mini region-btn", onclick: "addNecromancyPath()") {
            i(class: "icon-plus")
        }
        div(class: "row") {
            div(class: "span5") {
                input(type: "text", name: "necromancySchoolName", placeholder: "School", class: "span5", value: role?.necromancyType)
            }
        }
        List<DottedType<Path>> roleNecroPaths = role?.necromancyPaths
        if (roleNecroPaths != null && !roleNecroPaths.isEmpty()) {
            roleNecroPaths.each { DottedType<Path> rp ->
                div(class: "row") {
                    div(class: "span4") {
                        select(class: "span4", name: "necromancyPaths[][id]") {
                            option(value: "", "")
                            necromancyPaths.each { Path p ->
                                if (rp.type.id == p.id) {
                                    option(value: p.id, p.getName(), selected: true)
                                } else {
                                    option(value: p.id, p.getName())
                                }
                            }
                        }
                    }
                    div(class: "span1") {
                        input(type: "number", class: "span1", min: 0, max: 5,
                                name: "necromancyPaths[][dots]", value: rp.dots)
                    }
                }
            }
        } else {
            div(class: "row") {
                div(class: "span4") {
                    select(class: "span4", name: "necromancyPaths[][id]") {
                        option(value: "", "")
                        necromancyPaths.each { Path p ->
                            option(value: p.id, p.getName())
                        }
                    }
                }
                div(class: "span1") {
                    input(type: "number", class: "span1", min: 0, max: 5, name: "necromancyPaths[][dots]")
                }
            }
        }
    }
}
div(class: "row") {
    div(class: "span10 msr-region-bordered", regionlabel: _("Rituals")) {
        div(class: "row") {
            script(type: "template", id: "t_ritualRow") {
                tr(ritual: "{{ id }}") {
                    input(type: "hidden", name: "rituals[]", value: "{{ id }}")
                    td("{{ type }}")
                    td("{{ name }}")
                    td(width: "7%", "{{ level }}")
                    td(width: "5%") {
                        button(class: "btn btn-mini", onclick: "removeRitual('{{ id }}')") {
                            i(class: "icon-remove-circle")
                        }
                    }
                }
            }
            div(class: "span7") {
                table(id: "ritualsTable", class: "table table-condensed span6") {
                    tr {
                        td(raw("&nbsp;"))
                        td(raw("&nbsp;"))
                        td(width: "7%", raw("&nbsp;"))
                        td(width: "5%", raw("&nbsp;"))
                    }
                    role?.rituals?.each {Ritual ritual ->
                        tr(ritual: ritual.id) {
                            input(type: "hidden", name: "rituals[]", value: ritual.id)
                            td(ritual.ritualType.name)
                            td(ritual.name)
                            td(width: "7%", ritual.level)
                            td(width: "5%") {
                                button(class: "btn btn-mini", onclick: "removeRitual('"+ritual.id+"')") {
                                    i(class: "icon-remove-circle")
                                }
                            }
                        }
                    }
                }
            }
            div(class: "span3") {
                List<RitualType> ritualTypes = module.getRitualTypes()
                div(class: "row") {
                    div(class: "span3") {
                        select(class: "span3", id: "ritualTypesSelect") {
                            ritualTypes.each { RitualType t ->
                                option(value: t.getId(), t.getName())
                            }
                        }
                    }
                }
                div(class: "row") {
                    div(class: "span3") {
                        select(class: "span3", id: "ritualsSelect") {
                            module.getRituals(ritualTypes.get(0).getId()).each { Ritual ritual ->
                                option(value: ritual.getId(), ritual.getName())
                            }
                        }
                    }
                }
                div(class: "row") {
                    div(class: "span1 offset2") {
                        button(class: "btn", onclick: "addRitual()", _("Add"))
                    }
                }
            }
        }
    }
}