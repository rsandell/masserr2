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

var newAbilityCounter = 0

function findAbility(abilityId) {
    var id = fromNavId(abilityId);
    for (var i = 0; i < abilities.length; i++) {
        if (abilities[i].id == id) {
            return abilities[i];
        }
    }
    return null;
}

function generateAbilityForm(ability) {
    var html = "" + //"<form action='abilitySubmit' method='POST' class='.form-inline'>" +
        "<tr ability='"+toNavId(ability.id)+"'>" +
        "<td><input type='hidden' name='id' value='"+ability.id+"'/><small>"+ability.id+"</small></td>" +
        "<td>"+generateTypesSelect(ability.type)+"</td>" +
        "<td><input type='text' name='name' value='"+ability.name+"' required/></td>" +
        "<td><input type='number' style='text-align: right' name='baseMonthlyIncome' value='"+ability.baseMonthlyIncome+"'/> </td>" +
        "<td><input type='url' name='docUrl' value='"+ability.docUrl+"'/> </td>" +
        "<td>" +
        "<button type='button' class='btn btn-mini btn-primary' onclick=\"submitAbility('"+toNavId(ability.id)+"')\">" +
        "<i class='icon-check icon-white'>" +
        "</button>" +
        "</td>" +
        "</tr>";
        //"</form>";
    return html;
}

function editAbilitiesRow(abilityId) {
    var ability = findAbility(abilityId);
    $('tr[ability~="'+ abilityId +'"]').replaceWith(generateAbilityForm(ability));
    //abilityRow.replaceWith("<tr><td colspan='6'>Hello</td></tr>")
}

function generateAbilityRow(a) {
    var urlPart = "";
    if (a.docUrl != null && a.docUrl.length > 0) {
        urlPart = "<a href='"+a.docUrl+"'>"+a.docUrl+"</a>";
    }
    return "<tr ability='"+toNavId(a.id)+"'>" +
        "<td><small>"+ a.id+"</small></td>" +
        "<td><small>"+ a.type+"</small></td>" +
        "<td>"+a.name+"</td>" +
        "<td style='text-align: right;'>"+ a.baseMonthlyIncome+"</td>" +
        "<td>"+ urlPart +"</td>" +
        "<td>" +
        "<a class='btn btn-mini' href='javascript:editAbilitiesRow(\""+toNavId(a.id)+"\")'><i class='icon-edit'/></a>" +
        "</td>" +
        "</tr>";
}

function generateAbilitiesTable() {
    var html = "";
    for (var i = 0; i < abilities.length; i++) {
        var a = abilities[i];
        html += generateAbilityRow(a);
    }
    return html;
}

function generateTypesSelect(selected) {
    var html = "<select name='type'>";
    for (var i = 0; i < aTypes.length; i++) {
        if (aTypes[i] == selected) {
            html += "<option value='"+aTypes[i]+"' selected='true'>"+aTypes[i]+"</option>";
        } else {
            html += "<option value='"+aTypes[i]+"'>"+aTypes[i]+"</option>";
        }
    }
    html += "</select>";
    return html;
}

function submitAbility(abilityId) {
    var id = toNavId(abilityId);
    var params = $("tr[ability~='"+id+"'] :input").serialize();
    $.get("abilitySubmit?" + params,
          function(data) {
              if(data.status == "OK") {
                  if (id.indexOf("new") == 0) {
                      location.reload(true);
                  } else {
                    $('tr[ability~="'+ abilityId +'"]').replaceWith(generateAbilityRow(data));
                  }
              } else {
                  alert(data.message);
              }
          }, "json");
}

function newAbility() {
    var newA = {id: "new" + (newAbilityCounter++), name: "", type: "", docUrl: "", baseMonthlyIncome: 0};
    $("#abilitiesTable tr.heading").after(generateAbilityForm(newA));
}

$("#abilitiesTable tr.heading").after(generateAbilitiesTable());


