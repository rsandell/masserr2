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
var templateOption = _.template("<option value='{{ id }}'>{{ name }}</option>");

var clans = null;

function isClanDiscipline(d, clan) {
    "use strict";
    for(var i = 0; i < clan.clanDisciplines.length; i++) {
        var cd = clan.clanDisciplines[i];
        if(cd.id === d.id) {
            return true;
        }
    }
    return false;
}

function editDisciplines(theId) {
    "use strict";
    var clan = findById(theId, clans);
    $("#disciplinesModalClanLabel").html(clan.name);
    $("#disciplinesModalClanId").val(clan.id);
    var filteredDisciplines = _.filter(disciplines, function(d){ return !isClanDiscipline(d, clan); });
    var allSelect = $("#allDisciplinesSelect");
    allSelect.html("");
    _.each(filteredDisciplines, function(d) {
        allSelect.append(templateOption(d));
    });
    var clanSelect = $("#clanDisciplinesSelect");
    clanSelect.html("");
    _.each(clan.clanDisciplines, function(d) {
        clanSelect.append(templateOption(d));
    });
    $("#disciplinesModal").modal();
}

function generateDisciplinesUl(clan) {
    "use strict";
    var lis = _.reduce(clan.clanDisciplines, function(memo, d) { return memo + "<li>" + asDocumented(d) + "</li>"}, "");
    return "<ul>"+lis+"</ul>";
}

function generateForm(clan) {
    "use strict";
    clan.navId = toNavId(clan.id);
    clan.disciplinesUl = generateDisciplinesUl(clan);
    return templateForm(clan);
}

function editRow(theId) {
    "use strict";
    var clan = findById(theId, clans);
    $('tr[clan~="'+ theId +'"]').replaceWith(generateForm(clan));
}

function generateRow(a) {
    "use strict";
    var urlPart = "";
    if (a.docUrl !== null && a.docUrl.length > 0) {
        urlPart = "<a href='"+a.docUrl+"'>"+a.docUrl+"</a>";
    }
    a.urlPart = urlPart;
    a.navId = toNavId(a.id);
    a.logo = getLogoPathFor(a.name);
    a.disciplinesUl = generateDisciplinesUl(a);
    return templateRow(a);
}

function generateTable() {
    "use strict";
    var html = "";
    for (var i = 0; i < clans.length; i++) {
        var a = clans[i];
        html += generateRow(a);
    }
    return html;
}

function newClan() {
    "use strict";
    var newC = {id: "new" + (newClanCounter++), name: "", weaknesses: "", docUrl: "", baseIncome: 0, logo: ""};
    $("#clansTable tr.heading").after(generateForm(newC));
}

function submitClan(theId) {
    "use strict";
    var id = toNavId(theId);
    var formObj = $("tr[clan~='"+id+"'] :input").serializeObject();
    admin.submitClan(formObj, function(t) {
        var resp = t.responseObject();
        if (resp.ok) {
            if (id.indexOf("new") === 0) {
                location.reload(true);
            } else {
                replaceByObjectId(resp.data, clans);
                $('tr[clan~="'+ id +'"]').replaceWith(generateRow(resp.data));
            }
        } else {
            window.alert(resp.message);
        }
    });
}

function getLogoPathFor(name) {
    "use strict";
    var n = name.replaceAll("\\s+", "");
    n = n.toLowerCase();
    return logoUrlBase + n + ".png";
}

admin.getClans(function(t) {
    "use strict";
    clans = t.responseObject();
    $("#clansTable tr.heading").after(generateTable());
});

