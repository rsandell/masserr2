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
    form(class: "form-horizontal", action: "configSubmit", method: "POST", id: "formConfig") {
        legend(_("Config"))
        div(class: "control-group") {
            label(class: "control-label", for: "inputAppName", _("App Name"))
            div(class: "controls") {
                input(type: "text", id: "inputAppName", name: "appName", value: config.getAppName(), placeholder: _("App Name"))
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputApplicationUrl", _("Application URL"))
            div(class: "controls") {
                input(type: "text", id: "inputApplicationUrl", name: "applicationUrl", value: config.applicationUrl)
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputDefaultMorality", _("Default Morality Path"))
            input(type: "hidden", name: "defaultMorality[staplerClass]", value: Morality.class.getName())
            div(class: "controls") {
                select(id: "inputDefaultMorality", name: "defaultMorality[id]") {
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
        legend("OAuth Providers")
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderGoogleEnabled", _("Google"))
            div(class: "controls") {
                if (config.getGoogleKeys() != null && config.googleKeys.enabled) {
                    input(type: "checkbox", id: "inputProviderGoogleEnabled", name: "googleKeys[enabled]", checked: true)
                } else {
                    input(type: "checkbox", id: "inputProviderGoogleEnabled", name: "googleKeys[enabled]")
                }
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderGoogleApi", _("API Key"))
            div(class: "controls") {
                input(type: "text", id: "inputProviderGoogleApi", name: "googleKeys[apiKey]", value: config.googleKeys?.apiKey)
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderGoogleSecret", _("API Secret"))
            div(class: "controls") {
                input(type: "text", id: "inputProviderGoogleSecret", name: "googleKeys[apiSecret]", value: config.googleKeys?.apiSecret)
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputFormJson", raw("&nbsp;"))
            div(class: "controls") {
                input(type: "hidden", id: "inputFormJson", name: "json", value: "")
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderYahooEnabled", _("Yahoo"))
            div(class: "controls") {
                if (config.getYahooKeys() != null && config.yahooKeys.enabled) {
                    input(type: "checkbox", id: "inputProviderYahooEnabled", name: "yahooKeys[enabled]", checked: true)
                } else {
                    input(type: "checkbox", id: "inputProviderYahooEnabled", name: "yahooKeys[enabled]")
                }
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderYahooApi", _("API Key"))
            div(class: "controls") {
                input(type: "text", id: "inputProviderYahooApi", name: "yahooKeys[apiKey]", value: config.yahooKeys?.apiKey)
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderYahooSecret", _("API Secret"))
            div(class: "controls") {
                input(type: "text", id: "inputProviderYahooSecret", name: "yahooKeys[apiSecret]", value: config.yahooKeys?.apiSecret)
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderFacebookEnabled", _("Facebook"))
            div(class: "controls") {
                if (config.getFacebookKeys() != null && config.facebookKeys.enabled) {
                    input(type: "checkbox", id: "inputProviderFacebookEnabled", name: "facebookKeys[enabled]", checked: true)
                } else {
                    input(type: "checkbox", id: "inputProviderFacebookEnabled", name: "facebookKeys[enabled]")
                }
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderFacebookApi", _("API Key"))
            div(class: "controls") {
                input(type: "text", id: "inputProviderFacebookApi", name: "facebookKeys[apiKey]", value: config.facebookKeys?.apiKey)
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputProviderFacebookSecret", _("API Secret"))
            div(class: "controls") {
                input(type: "text", id: "inputProviderFacebookSecret", name: "facebookKeys[apiSecret]", value: config.facebookKeys?.apiSecret)
            }
        }
        div(class: "control-group") {
            div(class: "controls") {
                button(type: "submit", class: "btn", _("Save"))
            }
        }
    }
    script(src: "${resURL}/js/admin/config.js")
}