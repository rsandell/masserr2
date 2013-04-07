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

/**
 * template for select option
 */
var t_option = _.template("<option value='{{ id }}'>{{ name }}</option>");
var t_disciplinesSelect = _.template($("#t_DisciplinesSelect").html());
var t_thaumaSelect = _.template($("#t_thaumaSelect").html());
var t_necromancySelect = _.template($("#t_necromancySelect").html());
var t_ritualRow = _.template($("#t_ritualRow").html());

function addRitual() {
    "use strict";
    var rId = $("#ritualsSelect").val();
    module.getRitual(rId, function(t){
        var ritual = t.responseObject();
        var tr = {id: ritual.id, type: ritual.ritualType.name, name: ritual.name, level: ritual.level};
        $("#ritualsTable").append(t_ritualRow(tr));
    });
}

function addDiscipline() {
    "use strict";
    $("#disciplinesDiv").append(t_disciplinesSelect());
}

function addThaumaPath() {
    "use strict";
    $("#thaumaPathsDiv").append(t_thaumaSelect());
}

function addNecromancyPath() {
    "use strict";
    $("#necromancyPathsDiv").append(t_necromancySelect());
}

function updatePlayers(player) {
    "use strict";
    $("#playerSelect").prepend(t_option(player));
    $("#playerSelect").val(player.id);
}

function savePlayer() {
    "use strict";
    var player = $("#playerForm :input").serializeObject();
    module.savePlayer(player, function(t) {
        var res = t.responseObject();
        if (res.ok) {
            $("#playerModal").modal('hide');
            updatePlayers(res.data);
        } else {
            window.alert(res.message);
        }
    });
}

$("#ritualTypesSelect").change(function(event) {
    "use strict";
    var typeId = $("#ritualTypesSelect").val();
    module.getRituals(typeId, function(t){
        var list = t.responseObject();
        $("#ritualsSelect option").remove();
        _.each(list, function(ritual){
            $("#ritualsSelect").append(t_option(ritual));
        });
    });
});

/**
 * Updates the sires select when the clan is changed.
 */
$("#clanSelect").change(function sireUpdate(event) {
    "use strict";
    var clanId = $("#clanSelect").val();
    var sire = $("#sireSelect").val();
    if (clanId !== null && (sire === "" || sire === null)) {
        module.getRolesOfClan(clanId, function (t) {
            var sires = t.responseObject();
            if (sires !== null) {
                $("#sireSelect option").remove();
                $("#sireSelect").append(t_option({id: '', name: ''}));
                for (var i = 0; i < sires.length; i++) {
                    var role = sires[i];
                    $("#sireSelect").append(t_option(role));
                }
            }
        });
    }
    //Update Disciplines
    if(clanId !== null) {
        var dotsSum = 0;
        $("#disciplinesDiv input[name='discipline[][dots]']").each(function(index) {
            dotsSum = dotsSum + Number($(this).val());
        });
        if (dotsSum <= 0) {
            module.getClanDisciplines(clanId, function(t) {
                var list = t.responseObject();
                $("#disciplinesDiv .row").remove();
                var theDiv = $("#disciplinesDiv");
                for (var i = 0; i < list.length; i++) {
                    theDiv.append(t_disciplinesSelect());
                    $("#disciplinesDiv select[name='discipline[][id]']:last").val(list[i].id);
                }
            });
        }
    }
});

$("#moralitySelect").change(function moralityUpdate(event) {
    "use strict";
    module.getMoralityPath($("#moralitySelect").val(), function(t) {
        var res = t.responseObject();
        $("#adherenceSelect").val(res.adherenceTeachings);
        $("#resistanceSelect").val(res.resistanceTeachings);
    });
});

/**
 * Adjust the width of combined select and button inputs.
 */
$(document).ready(function() {
    "use strict";
    var clanWidth = $("#clanSelect").width();
    var sireBtnWidth = $("#newSireBtn").width();
    $("#sireSelect").width(clanWidth - sireBtnWidth - 15);

    var playerWidth = $("#playerSelect").width();
    var playerBtnWidth = $("#newPlayerBtn").width();
    $("#playerSelect").width(playerWidth - playerBtnWidth - 14);
});

/**
 * When save on new quick role dialog has been successful.
 */
qrPostSaveHook = function(role) {
    "use strict";
    $("#sireSelect").prepend(t_option(role));
    $("#sireSelect").val(role.id);
};

/**
 * Handler for the tabs
 */
$('#roleTab a').click(function (e) {
    "use strict";
    e.preventDefault();
    $(this).tab('show');
});

/**
 * Left side stats popover.
 */
$('#roleTabContent').popover({placement: 'left', html: true, title: 'Stats', container: 'body', trigger: 'manual', content: $('#statsContent').html()});
$('#roleTabContent').popover('show');