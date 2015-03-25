'use strict';

var websocket;
initWebSocket();

// Declare app level module which depends on filters, and services
angular.module('cassandraMonitor', [
  'ngRoute',
  'cassandraMonitor.filters',
  'cassandraMonitor.services',
  'cassandraMonitor.directives',
  'cassandraMonitor.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/lineChartView', {templateUrl: 'chartview/lineChartView.html', controller: 'LineChartCtrl'});
  $routeProvider.when('/successChartView', {templateUrl: 'chartview/successChartView.html', controller: 'SuccessChartCtrl'});
  $routeProvider.when('/failedChartView', {templateUrl: 'chartview/failedChartView.html', controller: 'FailedChartCtrl'});
  $routeProvider.otherwise({redirectTo: '/lineChartView'});
}]);
