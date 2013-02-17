import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Ability
import net.joinedminds.masserr.model.OtherTrait
import net.sf.json.JSONObject

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
def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Other Traits") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    script() {
        raw("var traits = [")
        my.getOtherTraits().each() { OtherTrait trait ->
            raw("jQuery.parseJSON( '" + JSONObject.fromObject(new OtherTrait(trait)).toString() + "' ), ")
        }
        raw("];\n")
    }

    legend(_("Other Traits"))
    table(class: "table table-hover", id:"otherTraitsTable") {
        tr(class: "heading") {
            th(width: "10%", _("Id"))
            th(width: "40%", _("Name"))
            th(width: "40%", _("Doc URL"))
            th(width: "10%") {
                button(class: "btn btn-mini", onclick: "newTrait()") {
                    i(class: "icon-plus")
                }
            }
        }
    }
    script(src: "${resURL}/js/admin/otherTraits.js")
}


