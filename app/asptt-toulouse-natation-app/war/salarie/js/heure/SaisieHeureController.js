/**
 * 
 */
var saisieHeureController = angular.module('SaisieHeureController', ['ngRoute', 'saisieHeureServices']);

saisieHeureController.controller('SaisieHeureController', ['$scope', '$location', '$filter', 'SaisieHeureService', function($scope, $location, $filter, SaisieHeureService) {
	$scope.groupesSelected;
	$scope.beginWeek = moment().startOf('week').toDate();
	$scope.endWeek = moment().endOf('week').toDate();
	$scope.loading = false;
	
	SaisieHeureService.week.query(function(data) {
		$scope.days = data;
	});
	
	$scope.valider = function() {
		console.log($scope.days);
	}
}]);