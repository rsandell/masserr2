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

var templateRow = _.template($("#t_disciplineRow").html());
var templateForm = _.template($("#t_disciplineForm").html());

var newDisciplineCounter = 0;

function newDiscipline() {
    var newD = {id: "new" + (newDisciplineCounter++), name: "",docUrl: "", retestAbility: null};
    $("#disciplinesTable tr.heading").after(generateForm(newD));
}

function submitDiscipline(navId) {
    var id = toNavId(navId);
    var formObj = $("tr[discipline~='"+id+"'] :input").serializeObject();
    if (formObj.retestAbility_id != '') {
        //Fill up the DataBoundConstructor
        formObj.retestAbility = Object();
        formObj.retestAbility.id = formObj.retestAbility_id;
        formObj.retestAbility.name = '';
        formObj.retestAbility.type = null;
        formObj.retestAbility.docUrl = '';
        formObj.retestAbility.baseMonthlyIncome = 0;
    } else {
        formObj.retestAbility = null;
    }
    formObj.retestAbility_id = undefined;
    admin.submitDiscipline(formObj, function(t) {
        var resp = t.responseObject();
        if (resp.ok) {
            if (id.indexOf("new") == 0) {
                location.reload(true);
            } else {
                $('tr[discipline~="'+ navId +'"]').replaceWith(generateRow(resp.data));
                replaceByObjectId(resp.data, disciplines);
            }
        } else {
            alert(resp.message);
        }
    });
}

function generateForm(obj) {
    obj.navId = toNavId(obj.id);
    return templateForm(obj);
}

function editRow(navId) {
    var rowObj = findById(navId, disciplines);
    $('tr[discipline~="'+ navId +'"]').replaceWith(generateForm(rowObj));
    if (rowObj.retestAbility == null) {
        $('tr[discipline~="'+ navId +'"] select[name="retestAbility_id"]').val('');
    } else {
        $('tr[discipline~="'+ navId +'"] select[name="retestAbility_id"]').val(rowObj.retestAbility.id);
    }
}

function generateRow(a) {
    var urlPart = "";
    if (a.docUrl != null && a.docUrl.length > 0) {
        urlPart = "<a href='"+a.docUrl+"'>"+a.docUrl+"</a>";
    }
    a.urlPart = urlPart;
    a.navId = toNavId(a.id);
    if (a.retestAbility == null) {
        a.retestAbilityName = "";
    } else {
        a.retestAbilityName = a.retestAbility.name;
    }
    return templateRow(a);
}

function generateTable() {
    var html = "";
    for (var i = 0; i < disciplines.length; i++) {
        var a = disciplines[i];
        html += generateRow(a);
    }
    return html;
}

$("#disciplinesTable tr.heading").after(generateTable());
