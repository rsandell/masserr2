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

var templateOption = _.template("<option value='{{ id }}'>{{ name }}</option>");

function setPlayerForm(player) {
    "use strict";
    $("#inputPlayerId").val(player.id);
    $("#inputPlayerName").val(player.name);
    $("#inputPlayerCampaign").val(player.campaign.id);
    $("#inputPlayerEmail").val(player.email);
    $("#inputPlayerPhone").val(player.phone);
    $("#inputPlayerAddress").val(player.address);
}

function updatePlayersList() {
    "use strict";
    var cid = $("#campaignSelect").val();
    admin.getPlayers(cid, function(t) {
        var list = t.responseObject();
        $("#playerSelect option").remove();
        _.each(list, function(player) {
            $("#playerSelect").append(templateOption(player));
        });
    });
}

$("#campaignSelect").change(updatePlayersList);
$("#playerSelect").change(function(event) {
    "use strict";
    var pid = $("#playerSelect").val();
    if(pid.length > 1) {
        var spid = pid[0];
        $("#playerSelect").val(spid);
    } else if(pid.length <= 0) {
        return;
    }
    admin.getPlayer(pid[0], function(t){
        var player = t.responseObject();
        if(player !== null) {
            setPlayerForm(player);
        }
    });
});

function savePlayer() {
    "use strict";
    var player = $("#playerForm :input").serializeObject();
    admin.savePlayer(player, function(t) {
        var res = t.responseObject();
        if (res.ok) {
            updatePlayersList();
            if ($("#campaignSelect").val() === res.data.campaign) {
                $("#playerSelect").val(res.data.id);
            } else {
                $("#playerSelect").val("");
            }
        } else {
            window.alert(res.message);
        }
    });
}

function newPlayer() {
    "use strict";
    setPlayerForm({id: "", name: "", campaign: {id: ""}, email: "", phone: "", address: ""});
}