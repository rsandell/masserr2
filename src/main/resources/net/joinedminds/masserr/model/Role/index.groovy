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
    div(class: "row") {
        table(class: "span3 table table-condensed table-hover table-bordered") {
            tr {
                td {
                    strong(_("Player"))
                }
                td {
                    if (role.player != null) {
                        span(role.player.name)
                    } else {
                        _("NPC")
                    }
                }
            }
            tr {
                if (role.ghoul) {
                    td(colspan: "2") {strong(_("Ghoul"))}
                } else {
                    td {
                        strong(_("Generation"))
                    }
                    td {
                        span(role.generation.generation)
                    }
                }
            }
            tr {
                td {
                    strong(_("Embraced"))
                }
                td {
                    raw(Functions.formatDate(role.embraced))
                }
            }
            tr {
                td {
                    strong(_("Age"))
                }
                td {
                    raw(role.age)
                }
            }
            tr {
                td {
                    strong(_("Clan"))
                }
                td {
                    span(role.clan.name)
                }
            }
            tr {
                td {
                    strong(_("Nature"))
                }
                td {
                    span(role.nature.name)
                }
            }
            tr {
                td {
                    strong(_("Demeanor"))
                }
                td {
                    span(role.demeanor.name)
                }
            }
        }
        table(class: "span3 offset1 table table-condensed table-hover table-bordered") {
            tr {
                td {
                    strong(_("Domain"))
                }
                td {
                    span(role.domain.name)
                }
            }
            tr {
                td {
                    strong(_("Courage"))
                }
                td {
                    raw(role.virtues.courageDots)
                }
            }
            tr {
                td {
                    strong(_(role.virtues.adherence.name()))
                }
                td {
                    raw(role.virtues.adherenceDots)
                }
            }
            tr {
                td {
                    strong(_(role.virtues.resistance.name()))
                }
                td {
                    raw(role.virtues.resistanceDots)
                }
            }
            tr {
                td {
                    strong(_("Willpower"))
                }
                td {
                    raw(role.willpower)
                }
            }
            tr {
                td {
                    strong(_("Bloodpool"))
                }
                td {
                    raw(role.generation.bloodPool)
                    if (role.generation.humanBlood > 0) {
                        raw("/")
                        raw(role.generation.humanBlood)
                    }
                    raw("/")
                    raw(role.generation.spendBlood)
                }
            }
            tr {
                td {
                    strong(role.morality.type.name)
                }
                td {
                    raw(role.morality.dots)
                }
            }
        }
        table(class: "span3 offset1 table table-condensed table-hover table-bordered") {
            int emptyRows = 7
            if (role.derangements?.size() > 0) {
                emptyRows = emptyRows - role.derangements.size()
                tr {
                    td {
                        strong(_("Derangements"))
                    }
                }
                role.derangements.each { String der ->
                    tr {
                        td(der)
                    }
                }
                if(role.beastTraits?.size() > 0) {
                    emptyRows -= 1
                    tr {
                        td {
                            raw("&nbsp;")
                        }
                    }
                }
            }
            if(role.beastTraits?.size() > 0) {
                emptyRows = emptyRows - role.beastTraits.size()
                tr {
                    td {
                        strong(_("Beast Traits"))
                    }
                }
                role.beastTraits.each {String bt ->
                    tr {
                        td(bt)
                    }
                }
            }
            if (emptyRows > 0) {
                for (i in 1..emptyRows) {
                    tr {
                        td {
                            raw("&nbsp;")
                        }
                    }
                }
            }
        }
    }
}
