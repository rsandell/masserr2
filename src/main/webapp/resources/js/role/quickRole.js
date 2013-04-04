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

var templateQuickRoleAlert = _.template($("#t_quickRoleModalAlert").html());

var qrPostSaveHook = null;

function getQuickRoleObj() {
    "use strict";
    return $("#quickRoleModal :input").serializeObject();
}

function validateQuickRole(role) {
    "use strict";
    if (role.name === null || role.name.length <= 0) {
        alertQuickRole(quickRoleMsgs.noName.heading, quickRoleMsgs.noName.message);
        return false;
    }
    if (role.embraced !== null && role.embraced.match(/\d{3,4}(-\d{2}(-\d{2}){0,1}){0,1}/g)) {
        var parts = role.embraced.split("-");
        if(parts.length > 1) {
            var month = parseInt(parts[1]);
            if (month < 1 || month > 12) {
                alertQuickRole(quickRoleMsgs.embraced.heading, quickRoleMsgs.embraced.message);
                return false;
            }
            if(parts.length > 2) {
                var day = parseInt(parts[2]);
                if (day < 1 || day > 31) {
                    alertQuickRole(quickRoleMsgs.embraced.heading, quickRoleMsgs.embraced.message);
                    return false;
                }
            }
        }
    } else {
        alertQuickRole(quickRoleMsgs.embraced.heading, quickRoleMsgs.embraced.message);
        return false;
    }
    return true;
}

function alertQuickRole(errHeading, errMessage) {
    "use strict";
    $("#quickRoleModal .modal-body").append(templateQuickRoleAlert({heading: errHeading, message: errMessage}));
}

function saveQuickRole() {
    "use strict";
    var role = getQuickRoleObj();
    if (validateQuickRole(role)) {
        qrModule.submitQuickRole(role, function(t) {
            var resp = t.responseObject();
            if (resp.ok) {
                $("#quickRoleModal").modal('hide');
                if (qrPostSaveHook !== null && _.isFunction(qrPostSaveHook)) {
                    qrPostSaveHook(resp.data);
                }
                //location.reload(true);
            } else {
                window.alert(resp.message);
            }
        });
    }
    //$("#quickRoleModalAlert").alert();
}


