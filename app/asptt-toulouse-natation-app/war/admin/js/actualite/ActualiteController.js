/**
 * 
 */
var actualiteController = angular.module('ActualiteController', ['ngRoute', 'actualiteServices']);
actualiteController.controller('ActualiteController', ['$rootScope', '$scope', 'ActualiteService', function($rootScope, $scope, ActualiteService) {
	$rootScope.isLoading = true;
	
	$scope.showFull = true;
	$scope.currentActualite = null;
	
	ActualiteService.actualites.query(function (data) {
		$scope.actualites = data;
		$rootScope.isLoading = false;
	});
	
	$scope.edit = function(actualite) {
		if(actualite != null) {
			$scope.currentActualite = actualite;	
		} else {
			$scope.currentActualite = new Object();
			$scope.currentActualite.begin = new Date().getTime();
			$scope.currentActualite.end = new Date().getTime();
		}
		
		$scope.showFull = false;
	}
	
	$scope.goBackToFull = function() {
		$scope.currentActualite = null;
		$scope.showFull = true;
	}
	
	$scope.publish = function() {
		ActualiteService.publish.query({}, $scope.currentActualite, function(data) {
			alert("Publié avec succès.");
			$scope.actualites = ActualiteService.actualites.query(function (data) {
				$scope.actualites = data;
				$scope.showFull = true;
				$rootScope.isLoading = false;
			});
		});
	}
}]);