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
import net.joinedminds.masserr.model.Campaign
import net.joinedminds.masserr.model.Player

st = namespace("jelly:stapler")
div(class: "form-horizontal", id: "playerForm") {
    input(type: "hidden", name: "id", id: "inputPlayerId")
    div(class: "control-group") {
        label(class: "control-label", for: "inputPlayerName", _("Name"))
        div(class: "controls") {
            input(type: "text", id: "inputPlayerName", name: "name", placeholder: _("Name"), required: true)
        }
    }
    div(class: "control-group") {
        label(class: "control-label", for: "inputPlayerCampaign", _("Campaign"))
        div(class: "controls") {
            select(id: "inputPlayerCampaign", name: "campaign") {
                Masserr.instance.admin.campaigns.each { Campaign c ->
                    option(value: c.id, c.name)
                }
            }
        }
    }
    div(class: "control-group") {
        label(class: "control-label", for: "inputPlayerEmail", _("Email"))
        div(class: "controls") {
            input(type: "text", id: "inputPlayerEmail", name: "email", placeholder: _("player@domain.com"), required: true)
        }
    }
    div(class: "control-group") {
        label(class: "control-label", for: "inputPlayerPhone", _("Phone"))
        div(class: "controls") {
            input(type: "text", id: "inputPlayerPhone", name: "phone", placeholder: _("#### ######"))
        }
    }
    div(class: "control-group") {
        label(class: "control-label", for: "inputPlayerAddress", _("Address"))
        div(class: "controls") {
            textarea(id: "inputPlayerAddress", name: "address", placeholder: _(""), rows: 3)
        }
    }
}



