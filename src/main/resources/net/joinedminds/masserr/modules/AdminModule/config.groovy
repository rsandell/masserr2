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
import net.joinedminds.masserr.model.Morality
import net.joinedminds.masserr.model.Config

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Config") + " " + Masserr.getInstance().getAppName()) {
    Config config = my.getConfig()
    form(class: "form-horizontal", action: "configSubmit", method: "POST") {
        legend(_("Config"))
        div(class: "control-group") {
            label(class: "control-label", for: "inputAppName", _("App Name"))
            div(class: "controls") {
                input(type: "text", id: "inputAppName", name: "appName", value: config.getAppName(), placeholder: _("App Name"))
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputDefaultMorality", _("Default Morality Path"))
            div(class: "controls") {
                select(id: "inputDefaultMorality", name: "defaultMorality") {
                    my.getMoralityPaths().each {Morality morality ->
                        if(morality.getId() == config.getDefaultMorality()?.getId()) {
                            option(value: morality.getId(), selected: true, morality.getName())
                        } else {
                            option(value: morality.getId(), morality.getName())
                        }
                    }
                }
            }
        }
        div(class: "control-group") {
            div(class: "controls") {
                button(type: "submit", class: "btn", _("Save"))
            }
        }
    }
}