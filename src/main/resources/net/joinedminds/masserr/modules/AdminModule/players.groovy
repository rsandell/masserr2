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
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Campaign
import net.joinedminds.masserr.model.Player
import net.joinedminds.masserr.modules.AdminModule


def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Players") + " " + Masserr.getInstance().getAppName()) {
    legend(_("Players"))
    AdminModule admin = my;
    Functions f = h;
    raw(f.bind(my, 'admin'))
    div(class: "container-fluid") {
        Campaign first = null
        div(class: "row-fluid") {
            div(class: "span2") {
                div(class: "row") {
                    select(id: "campaignSelect") {
                        admin.campaigns.each { Campaign c ->
                            if (first == null) {
                                first = c;
                            }
                            option(value: c.id, c.name)
                        }
                    }
                }
                div(class: "row") {
                    select(id: "playerSelect", multiple: true, size: 25) {
                        admin.getPlayers(first).each { Player p ->
                            option(value: p.id, p.name)
                        }
                    }
                }
                div(class: "row") {
                    button(class: "btn",_("New"), onclick: "newPlayer()")
                }
            }
            div(class: "span10") {
                //content
                st.include(class: Player.class, page: "i_playerForm.groovy")
            }
        }
    }
    script(src: "${resURL}/js/admin/players.js")
}