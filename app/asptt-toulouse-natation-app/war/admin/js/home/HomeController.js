/**
 * 
 */
var homeController = angular.module('HomeController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices', 'paramServices']);

homeController.controller('HomeController', ['$scope', '$location', function($scope, $location) {
	$scope.search = function() {
		$location.path("/dossiers/" + $scope.query);
	}
	
}]);