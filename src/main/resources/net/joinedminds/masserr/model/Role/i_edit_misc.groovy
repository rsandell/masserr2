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
import net.joinedminds.masserr.model.MeritOrFlaw
import net.joinedminds.masserr.model.OtherTrait
import net.joinedminds.masserr.model.Role
import net.joinedminds.masserr.modules.RolesModule

Role role = my;
RolesModule module = Masserr.getInstance().getRoles()
Functions f = h;

List<OtherTrait> otherTraits = module.getOtherTraits()
Map<MeritOrFlaw.Type, List<MeritOrFlaw>> merits = new HashMap<>()
MeritOrFlaw.Type.values().each { MeritOrFlaw.Type type ->
    merits.put(type, module.getMerits(type))
}
Map<MeritOrFlaw.Type, List<MeritOrFlaw>> flaws = new HashMap<>()
MeritOrFlaw.Type.values().each { MeritOrFlaw.Type type ->
    flaws.put(type, module.getFlaws(type))
}

script(type: "template", id: "t_otherTraitSelect") {
    div(class: "row") {
        div(class: "span3") {
            select(class: "span3", name: "otherTraits[][id]") {
                option(value: "", "")
                otherTraits.each { OtherTrait ot ->
                    option(value: ot.getId(), ot.getName())
                }
            }
        }
        div(class: "span1") {
            input(type: "number", name: "otherTraits[][dots]", class: "span1", max: 9, min: 0, value: 0)
        }
    }
}
script(type: "template", id: "t_derangementRow") {
    div(class: "row") {
        div(class: "span4") {
            input(type: "text", class: "span4", name: "derangements[]")
        }
    }
}
script(type: "template", id: "t_beastTraitRow") {
    div(class: "row") {
        div(class: "span4") {
            input(type: "text", class: "span4", name: "beastTraits[]")
        }
    }
}
script(type: "template", id: "t_meritRow") {
    div(class: "row") {
        div(class: "span2") {
            select(class: "span2", name: "merits[][id]", onchange: "calcMeritsFlawsStats()") {
                option(value: "", points: 0, "")
                MeritOrFlaw.Type.values().each { MeritOrFlaw.Type type ->
                    optgroup(label: type.name()) {
                        merits.get(type).each { MeritOrFlaw merit ->
                            option(value: merit.getId(), points: merit.getPoints(), merit.getName() + " ("+merit.getPoints()+")")
                        }
                    }
                }
            }
        }
        div(class: "span2") {
            input(type: "text", class: "span2", name: "merits[][notes]")
        }
    }
}
script(type: "template", id: "t_flawRow") {
    div(class: "row") {
        div(class: "span2") {
            select(class: "span2", name: "flaws[][id]", onchange: "calcMeritsFlawsStats()") {
                option(value: "", points: 0, "")
                MeritOrFlaw.Type.values().each { MeritOrFlaw.Type type ->
                    optgroup(label: type.name()) {
                        flaws.get(type).each { MeritOrFlaw flaw ->
                            option(value: flaw.getId(), points: flaw.getPoints(), flaw.getName() + " ("+flaw.getPoints()+")")
                        }
                    }
                }
            }
        }
        div(class: "span2") {
            input(type: "text", class: "span2", name: "flaws[][notes]")
        }
    }
}


div(class: "row") {
    table(class: "span10", cellpadding: 0, cellspacing: 0, border: 0) {
        tr {
            td(width: "50%", valign: "top") {
                div(class: "msr-region-bordered", id: "derangementsBox", regionlabel: _("Derangements")) {
                    button(class: "btn btn-mini region-btn", onclick: "addDerangement()") {
                        i(class: "icon-plus")
                    }
                    div(class: "row") {
                        div(class: "span4") {
                            input(type: "text", class: "span4", name: "derangements[]")
                        }
                    }

                }
            }
            td(width: "50%", valign: "top") {
                div(class: "msr-region-bordered", id: "meritsBox", regionlabel: _("Merits")) {
                    button(class: "btn btn-mini region-btn", onclick: "addMerit()") {
                        i(class: "icon-plus")
                    }
                }
            }
        }
        tr {
            td(width: "50%", valign: "top") {
                div(class: "msr-region-bordered", id: "beastTraitsBox", regionlabel: _("Beast Traits")) {
                    button(class: "btn btn-mini region-btn", onclick: "addBeastTrait()") {
                        i(class: "icon-plus")
                    }
                    input(type: "text", class: "span4")
                }
            }
            td(width: "50%", valign: "top") {
                div(class: "msr-region-bordered", id: "flawsBox", regionlabel: _("Flaws")) {
                    button(class: "btn btn-mini region-btn", onclick: "addFlaw()") {
                        i(class: "icon-plus")
                    }
                }
            }
        }
        tr {
            td(width: "50%", valign: "top") {
                div(class: "msr-region-bordered", id: "otherTraitsBox", regionlabel: _("Other Traits")) {
                    button(class: "btn btn-mini region-btn", onclick: "addOtherTrait()") {
                        i(class: "icon-plus")
                    }
                    div(class: "row") {
                        div(class: "span3") {
                            select(class: "span3", name: "otherTraits[][id]") {
                                option(value: "", "")
                                otherTraits.each { OtherTrait ot ->
                                    option(value: ot.getId(), ot.getName())
                                }
                            }
                        }
                        div(class: "span1") {
                            input(type: "number", name: "otherTraits[][dots]", class: "span1", max: 9, min: 0, value: 0)
                        }
                    }
                }
            }
            td(width: "50%", valign: "top") {
                raw("&nbsp;")
            }
        }
    }
}