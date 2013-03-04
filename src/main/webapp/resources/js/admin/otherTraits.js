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

var newTraitsCounter = 0;

var templateTraitRow = _.template($("#t_traitRow").html());
var templateTraitForm = _.template($("#t_traitForm").html());

function findTrait(traitId) {
    return findById(traitId, traits);
}

function submitTrait(traitId) {
    var id = toNavId(traitId);
    var formObj = $("tr[ability~='"+id+"'] :input").serializeObject();
    admin.submitOtherTrait(formObj, function(t) {
            var resp = t.responseObject();
            if (resp.ok) {
                if (id.indexOf("new") == 0) {
                    location.reload(true);
                } else {
                    $('tr[ability~="'+ traitId +'"]').replaceWith(generateTraitRow(resp.data));
                    replaceByObjectId(resp.data, traits);
                }
            } else {
                alert(resp.message);
            }
        });
}

function generateTraitsForm(trait) {
    trait.navId = toNavId(trait.id);
    return templateTraitForm(trait);
}

function editTraitsRow(traitId) {
    var trait = findTrait(traitId);
    $('tr[ability~="'+ traitId +'"]').replaceWith(generateTraitsForm(trait));
}

function generateTraitRow(a) {
    var urlPart = "";
    if (a.docUrl != null && a.docUrl.length > 0) {
        urlPart = "<a href='"+a.docUrl+"'>"+a.docUrl+"</a>";
    }
    a.navId = toNavId(a.id);
    a.urlPart = urlPart;
    return templateTraitRow(a);
}

function generateTraitsTable() {
    var html = "";
    for (var i = 0; i < traits.length; i++) {
        var a = traits[i];
        html += generateTraitRow(a);
    }
    return html;
}

function newTrait() {
    var newA = {id: "new" + (newTraitsCounter++), name: "", docUrl: ""};
    $("#otherTraitsTable tr.heading").after(generateTraitsForm(newA));
}

$("#otherTraitsTable tr.heading").after(generateTraitsTable());

