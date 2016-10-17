/**
 * 
 */
var suiviNageurController = angular.module('SuiviNageurController', ['ngRoute', 'suiviNageurServices']);

suiviNageurController.controller('SuiviNageurController', ['$scope', '$location', '$filter', 'SuiviNageurService', function($scope, $location, $filter, SuiviNageurService) {
	$scope.groupesSelected;
	$scope.day = new Date();
	$scope.endDay = new Date();
	
	$scope.loadWeeksPrevu = function() {
		var groupes = [];
		groupes.push(1060001);
		SuiviNageurService.weeksPrevu.query({groupes: groupes,  week: $filter('date')($scope.day,'yyyy-Www')}, function(data) {
			$scope.presences = data.presences;
			console.log($scope.presences);
			console.log($scope.presences[0]);
			$scope.beginDt = data.begin;
			$scope.endDt = data.end;
		});
	};
	
	$scope.loadWeeksPrevu();
}]);