'use strict';

/* Controllers */

angular.module('cassandraMonitor.controllers', [])
        .controller('WebSocketCtrl', ['$scope', function ($scope) {

            }])
        .controller('LineChartCtrl', ['$scope', function ($scope) {
                var json = '{"command":"CHART_SUBSCRIPTION", "chartSubscription":["LINE_SUCCESS_AND_FAILED"]}';
                websocket.send(json);
            }])
        .controller('SuccessChartCtrl', ['$scope', function ($scope) {
                var json = '{"command":"CHART_SUBSCRIPTION", "chartSubscription":["PIE_SUCCESS","RADAR_SUCCESS"]}';
                websocket.send(json);
            }])
        .controller('FailedChartCtrl', ['$scope', function ($scope) {
                var json = '{"command":"CHART_SUBSCRIPTION", "chartSubscription":["PIE_FAILED","RADAR_FAILED"]}';
                websocket.send(json);
            }]);
