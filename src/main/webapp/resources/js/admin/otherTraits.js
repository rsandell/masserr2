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

var newTraitsCounter = 0

function findTrait(traitId) {
    var id = fromNavId(traitId);
    for (var i = 0; i < traits.length; i++) {
        if (traits[i].id == id) {
            return traits[i];
        }
    }
    return null;
}

function submitTrait(traitId) {
    var id = toNavId(traitId);
    var params = $("tr[ability~='"+id+"'] :input").serialize();
    $.get("otherTraitSubmit?" + params,
        function(data) {
            if(data.status == "OK") {
                if (id.indexOf("new") == 0) {
                    location.reload(true);
                } else {
                    $('tr[ability~="'+ traitId +'"]').replaceWith(generateTraitRow(data));
                }
            } else {
                alert(data.message);
            }
        }, "json");
}

function generateTraitsForm(trait) {
    var html = "" +
        "<tr ability='"+toNavId(trait.id)+"'>" +
        "<td><input type='hidden' name='id' value='"+trait.id+"'/><small>"+trait.id+"</small></td>" +
        "<td><input type='text' name='name' value='"+trait.name+"' required/></td>" +
        "<td><input type='url' name='docUrl' value='"+trait.docUrl+"'/> </td>" +
        "<td>" +
        "<button type='button' class='btn btn-mini btn-primary' onclick=\"submitTrait('"+toNavId(trait.id)+"')\">" +
        "<i class='icon-check icon-white'>" +
        "</button>" +
        "</td>" +
        "</tr>";
    //"</form>";
    return html;
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
    return "<tr ability='"+toNavId(a.id)+"'>" +
        "<td><small>"+ a.id+"</small></td>" +
        "<td>"+a.name+"</td>" +
        "<td>"+ urlPart +"</td>" +
        "<td>" +
        "<a class='btn btn-mini' href='javascript:editTraitsRow(\""+toNavId(a.id)+"\")'><i class='icon-edit'/></a>" +
        "</td>" +
        "</tr>";
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

