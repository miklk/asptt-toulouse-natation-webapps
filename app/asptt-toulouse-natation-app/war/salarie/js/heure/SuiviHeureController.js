/**
 * 
 */
var suiviHeureController = angular.module('SuiviHeureController', ['ngRoute', 'saisieHeureServices']);

suiviHeureController.controller('SuiviHeureController', ['$rootScope', '$scope', '$location', '$filter', 'SaisieHeureService', function($rootScope, $scope, $location, $filter, SaisieHeureService) {
	$rootScope.isLoading = false;
	$scope.loadMonth = function() {
		$rootScope.isLoading = true;
		$scope.endMonth = moment($scope.beginMonth.getTime()).endOf('month').toDate();
		SaisieHeureService.month.query({month : $filter('date')($scope.beginMonth,'yyyy-MM')}, function(data) {
			$rootScope.isLoading = false;
			$scope.days = data;
		});
	}
	
	var init = function() {
		$scope.groupesSelected;
		$scope.beginMonth = moment().startOf('month').toDate();
		$scope.endMonth = moment().endOf('month').toDate();
		$scope.loadMonth();
	}
	
	init();
}]);