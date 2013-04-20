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
import net.joinedminds.masserr.model.Ability
import net.joinedminds.masserr.model.DottedNotedType
import net.joinedminds.masserr.model.Generation
import net.joinedminds.masserr.model.Role
import net.joinedminds.masserr.modules.RolesModule
import net.joinedminds.masserr.util.RoleFormFunctions

import static net.joinedminds.masserr.Functions.ifNull

Role role = my;
RolesModule module = Masserr.getInstance().getRoles();
Functions f = h;
Generation roleGen = role?.generation
if(roleGen == null) {
    roleGen = module.defaultGeneration
}

div(class: "row") {
    div(class: "span3 msr-region-bordered", regionlabel: _("Physical")) {
        div(class: "row") {
            div(class: "span3") {
                input(type: "number", class: "span3", name: "physical",
                        value: ifNull(role?.physical, 0), min: 0, max: roleGen.traitsMax)
            }
        }
        div(class: "row") {
            div(class: "span3") {
                table(class: "table table-condensed", style: "width: 100%") {
                    List<DottedNotedType<Ability>> abilities = RoleFormFunctions.abilitiesMixIn(
                            module.getAbilities(Ability.Type.Physical), role?.physicalAbilities)
                    abilities.each { DottedNotedType<Ability> ability ->
                        tr {
                            td {
                                raw(ability.type.name)
                                input(type: "hidden", name: "ability[][id]", value: ability.type.id)
                                input(type: "hidden", name: "ability[][type]", value: ability.type.type.name())
                            }
                            td {
                                input(type: "text", class: "attribute-input", name: "ability[][notes]",
                                        value: ability.notes)
                            }
                            td {
                                input(type: "number", class: "attribute-input", name: "ability[][dots]",
                                        value: ability.dots, min: 0, max: roleGen.abilitiesMax)
                            }
                        }
                    }
                }
            }
        }
    }
    div(class: "span3 msr-region-bordered", regionlabel: _("Social")) {
        div(class: "row") {
            div(class: "span3") {
                input(type: "number", class: "span3", name: "social",
                        value: ifNull(role?.social, 0), min: 0, max: roleGen.traitsMax)
            }
        }
        div(class: "row") {
            div(class: "span3") {
                table(class: "table table-condensed", style: "width: 100%") {
                    List<DottedNotedType<Ability>> abilities = RoleFormFunctions.abilitiesMixIn(
                            module.getAbilities(Ability.Type.Social), role?.socialAbilities)
                    abilities.each { DottedNotedType<Ability> ability ->
                        tr {
                            td {
                                raw(ability.type.name)
                                input(type: "hidden", name: "ability[][id]", value: ability.type.id)
                                input(type: "hidden", name: "ability[][type]", value: ability.type.type.name())
                            }
                            td {
                                input(type: "text", class: "attribute-input", name: "ability[][notes]",
                                        value: ability.notes)
                            }
                            td {
                                input(type: "number", class: "attribute-input", name: "ability[][dots]",
                                        min: 0, max: roleGen.abilitiesMax, value: ability.dots)
                            }
                        }
                    }
                }
            }
        }
    }
    div(class: "span3 msr-region-bordered", regionlabel: _("Mental")) {
        div(class: "row") {
            div(class: "span3") {
                input(type: "number", class: "span3", name: "mental",
                        value: ifNull(role?.mental, 0), min: 0, max: roleGen.traitsMax)
            }
        }
        div(class: "row") {
            div(class: "span3") {
                table(class: "table table-condensed", style: "width: 100%") {
                    List<DottedNotedType<Ability>> abilities = RoleFormFunctions.abilitiesMixIn(
                            module.getAbilities(Ability.Type.Mental), role?.mentalAbilities)
                    abilities.each { DottedNotedType<Ability> ability ->
                        tr {
                            td {
                                raw(ability.type.name)
                                input(type: "hidden", name: "ability[][id]", value: ability.type.id)
                                input(type: "hidden", name: "ability[][type]", value: ability.type.type.name())
                            }
                            td {
                                input(type: "text", class: "attribute-input", name: "ability[][notes]",
                                        value: ability.notes)
                            }
                            td {
                                input(type: "number", class: "attribute-input", name: "ability[][dots]",
                                        min: 0, max: roleGen.abilitiesMax, value: ability.dots)
                            }
                        }
                    }
                }
            }
        }
    }
}
script(type: "template", id: "t_retestInfo") {
    div(class: "span9", "{{ discipline }} retests with {{ ability }}")
}
div(class: "row", id: "retestInfo") {
    div(class: "span9", "Obfuscate retests with Occult")
}