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

function OtherTraitsCtrl($scope) {
    "use strict";
    $scope.traits = [];
    $scope.editingTraits = [];

    $scope.edit = function(trait) {
        if (!$scope.isEditing(trait)) {
            $scope.editingTraits.push(_.clone(trait));
        }
    };

    $scope.stopEdit = function(trait) {
        var i = $scope.findEditing(trait);
        if (i !== undefined) {
            $scope.editingTraits = _.reject($scope.editingTraits, function(t) {return t.id === i.id;});
            _.extend(trait, i);
        }
    };

    $scope.save = function(trait) {
        admin.submitOtherTrait(trait, function(t) {
            var resp = t.responseObject();
            if (resp.ok) {
                $scope.editingTraits = _.reject($scope.editingTraits, function(i) {return i.id === trait.id;});
                _.extend(trait, resp.data);
                $scope.$digest();
            } else {
                window.alert(resp.message);
            }
        });
    };

    $scope.findEditing = function(trait) {
        return _.find($scope.editingTraits, function(t) {return t.id === trait.id;});
    };

    $scope.isEditing = function(trait) {
        return  $scope.findEditing(trait) !== undefined;
    };

    admin.getOtherTraits(function(t){
        $scope.traits = t.responseObject();
        $scope.$digest();
    });

}