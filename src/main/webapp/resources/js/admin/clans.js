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

var newClanCounter = 0;

var templateRow = _.template($("#t_cRow").html());
var templateForm = _.template($("#t_cForm").html());

var clans = null;


function generateDisciplinesUl(clan) {
    "use strict"
    var lis = _.reduce(clan.clanDisciplines, function(memo, d) { return memo + "<li>"+ d.name+ "</li>"}, "");
    return "<ul>"+lis+"</ul>";
}

function generateForm(mf) {
    mf.navId = toNavId(mf.id);
    mf.generatedTypesSelect = generateTypesSelect(mf.type);
    return templateForm(mf);
}

function editRow(theId) {
    var clan = findById(theId, clans)
    $('tr[clan~="'+ theId +'"]').replaceWith(generateForm(clan));
}

function generateRow(a) {
    var urlPart = "";
    if (a.docUrl != null && a.docUrl.length > 0) {
        urlPart = "<a href='"+a.docUrl+"'>"+a.docUrl+"</a>";
    }
    a.urlPart = urlPart;
    a.navId = toNavId(a.id);
    a.logo = getLogoPathFor(a.name);
    a.disciplinesUl = generateDisciplinesUl(a)
    return templateRow(a);
}

function generateTable() {
    var html = "";
    for (var i = 0; i < clans.length; i++) {
        var a = clans[i];
        html += generateRow(a);
    }
    return html;
}

function newClan() {
    var newClan = {id: "new" + (newClanCounter++), name: "", weaknesses: "", docUrl: "", baseIncome: 0, logo: ""};
    $("#clansTable tr.heading").after(generateForm(newClan));
}

function submitClan(theId) {
    var id = toNavId(theId);
    var formObj = $("tr[clan~='"+id+"'] :input").serializeObject();
    admin.submitClan(formObj, function(t) {
        var resp = t.responseObject();
        if (resp.ok) {
            if (id.indexOf("new") == 0) {
                location.reload(true);
            } else {
                $('tr[clan~="'+ mfId +'"]').replaceWith(generateRow(resp.data));
                replaceByObjectId(resp.data, clans);
            }
        } else {
            alert(resp.message);
        }
    });
}

function getLogoPathFor(name) {
    "use strict";
    var n = name.replaceAll("\\s+", "");
    n = n.toLowerCase();
    return logoUrlBase + n + ".png"
}

admin.getClans(function(t) {
    clans = t.responseObject();
    $("#clansTable tr.heading").after(generateTable());
});

