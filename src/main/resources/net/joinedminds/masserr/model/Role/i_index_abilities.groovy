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

table(style: "width: 100%") {
    tr {
        td(width: "33%", align: "right") {
            table(class: "table table-hover table-bordered abilities-table", style: "width: 50%") {
                tr {
                    td {
                        strong(_("Physical"))
                        raw("&nbsp;")
                        small {
                            em("Max: " + role.generation.traitsMax)
                        }
                    }
                    td {
                        strong(role.physical)
                    }
                }
            }
        }
        td(width: "33%", align: "center") {
            table(class: "table table-hover table-bordered abilities-table", style: "width: 50%") {
                tr {
                    td {
                        strong(_("Social"))
                        raw("&nbsp;")
                        small {
                            em("Max: " + role.generation.traitsMax)
                        }
                    }
                    td {
                        strong(role.social)
                    }
                }
            }
        }
        td(width: "33%", align: "left") {
            table(class: "table table-hover table-bordered abilities-table", style: "width: 50%") {
                tr {
                    td {
                        strong(_("Mental"))
                        raw("&nbsp;")
                        small {
                            em("Max: " + role.generation.traitsMax)
                        }
                    }
                    td {
                        strong(role.mental)
                    }
                }
            }
        }
    }
}

int rowCount = Functions.max(role.physicalAbilities.size(), role.socialAbilities.size(), role.mentalAbilities.size())
table(class: "table table-condensed table-hover table-bordered abilities-table", style: "width: 100%") {
    for (int i = 0; i < rowCount; i++) {
        tr {
            if (role.physicalAbilities.size() > i) {
                DottedNotedType<Ability> ability = role.physicalAbilities[i]
                td(width: "30%") {
                    span(ability.type.name)
                    raw("&nbsp;")
                    small {
                        em(ability.notes)
                    }
                }
                td(width: "3%", ability.dots)
            } else {
                td {
                    raw("&nbsp")
                }
                td {
                    raw("&nbsp")
                }
            }
            if (role.socialAbilities.size() > i) {
                DottedNotedType<Ability> ability = role.socialAbilities[i]
                td(width: "30%") {
                    span(ability.type.name)
                    raw("&nbsp;")
                    small {
                        em(ability.notes)
                    }
                }
                td(width: "3%", ability.dots)
            } else {
                td {
                    raw("&nbsp")
                }
                td {
                    raw("&nbsp")
                }
            }
            if (role.mentalAbilities.size() > i) {
                DottedNotedType<Ability> ability = role.mentalAbilities[i]
                td(width: "30%") {
                    span(ability.type.name)
                    raw("&nbsp;")
                    small {
                        em(ability.notes)
                    }
                }
                td(width: "3%", ability.dots)
            } else {
                td {
                    raw("&nbsp")
                }
                td {
                    raw("&nbsp")
                }
            }
        }
    }
}

if (role.otherTraits.size() > 0) {
    table(class: "table table-condensed table-hover table-bordered abilities-table", style: "width: 100%") {
        for (int i = 0; i < role.otherTraits.size(); i = i + 5) {
            tr {
                for (int j = i; j < i + 5; j++) {
                    if (role.otherTraits.size() > j) {
                        DottedType<OtherTrait> trait = role.otherTraits[j]
                        td(width: "17%", trait.type.name)
                        td(width: "3%", trait.dots)
                    } else {
                        td(width: "17%") {
                            raw("&nbsp")
                        }
                        td(width: "3%") {
                            raw("&nbsp")
                        }
                    }
                }
            }
        }
    }
}
