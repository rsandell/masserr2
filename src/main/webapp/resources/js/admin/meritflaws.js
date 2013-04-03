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

var newMfsCounter = 0;

var templateRow = _.template($("#t_mfsRow").html());
var templateForm = _.template($("#t_mfsForm").html());

var mfs = null;

function generateTypesSelect(selected) {
    var html = "<select name='type'>";
    for (var i = 0; i < mfTypes.length; i++) {
        if (mfTypes[i] == selected) {
            html += "<option value='"+mfTypes[i]+"' selected='true'>"+mfTypes[i]+"</option>";
        } else {
            html += "<option value='"+mfTypes[i]+"'>"+mfTypes[i]+"</option>";
        }
    }
    html += "</select>";
    return html;
}

function generateForm(mf) {
    mf.navId = toNavId(mf.id);
    mf.generatedTypesSelect = generateTypesSelect(mf.type);
    return templateForm(mf);
}

function editRow(mfsId) {
    var mf = findById(mfsId, mfs)
    $('tr[mf~="'+ mfsId +'"]').replaceWith(generateForm(mf));
}

function generateRow(a) {
    var urlPart = "";
    if (a.docUrl != null && a.docUrl.length > 0) {
        urlPart = "<a href='"+a.docUrl+"'>"+a.docUrl+"</a>";
    }
    a.urlPart = urlPart;
    a.navId = toNavId(a.id);
    return templateRow(a);
}

function generateTable() {
    var html = "";
    for (var i = 0; i < mfs.length; i++) {
        var a = mfs[i];
        html += generateRow(a);
    }
    return html;
}

function newMf() {
    var newMf = {id: "new" + (newMfsCounter++), name: "", type: "", docUrl: "", points: 0};
    $("#mfsTable tr.heading").after(generateForm(newMf));
}

function submitMf(mfId) {
    var id = toNavId(mfId);
    var formObj = $("tr[mf~='"+id+"'] :input").serializeObject();
    admin.submitMeritOrFlaw(formObj, function(t) {
        var resp = t.responseObject();
        if (resp.ok) {
            if (id.indexOf("new") == 0) {
                location.reload(true);
            } else {
                $('tr[mf~="'+ mfId +'"]').replaceWith(generateRow(resp.data));
                replaceByObjectId(resp.data, mfs);
            }
        } else {
            alert(resp.message);
        }
    });
}

admin.getMeritOrFlaws(function(t) {
    mfs = t.responseObject();
    $("#mfsTable tr.heading").after(generateTable());
});

