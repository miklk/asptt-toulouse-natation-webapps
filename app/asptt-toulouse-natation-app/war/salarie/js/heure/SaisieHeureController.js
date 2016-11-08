/**
 * 
 */
var saisieHeureController = angular.module('SaisieHeureController', ['ngRoute']);

saisieHeureController.controller('SaisieHeureController', ['$scope', '$location', '$filter', function($scope, $location, $filter) {
	$scope.groupesSelected;
	$scope.beginWeek = moment().startOf('week').toDate();
	$scope.endWeek = moment().endOf('week').toDate();
	$scope.loading = false;
}]);