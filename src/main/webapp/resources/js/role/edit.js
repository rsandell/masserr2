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

var t_option = _.template("<option value='{{ id }}'>{{ name }}</option>");

$("#clanSelect").change(function sireUpdate(event) {

    var clanId = $("#clanSelect").val();
    var sire = $("#sireSelect").val();
    if(clanId != null && (sire == "" || sire == null)) {
        module.getRolesOfClan(clanId, function(t) {
            var sires = t.responseObject();
            if(sires != null) {
                $("#sireSelect option").remove();
                $("#sireSelect").append(t_option({id: '', name: ''}));
                for(var i = 0; i < sires.length; i++) {
                    var role = sires[i];
                    $("#sireSelect").append(t_option(role));
                }
            }
        });
    }
});

$(document).ready(function() {
    var clanWidth = $("#clanSelect").width();
    var sireBtnWidth = $("#newSireBtn").width();
    $("#sireSelect").width(clanWidth - sireBtnWidth - 12);
});

qrPostSaveHook = function(role) {
    $("#sireSelect").prepend(t_option(role));
    $("#sireSelect").val(role.id);
}


$('#roleTab a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
})

$('#roleTabContent').popover({placement: 'left', html: true, title: 'Stats', container: 'body', trigger: 'manual', content: $('#statsContent').html()});
$('#roleTabContent').popover('show');