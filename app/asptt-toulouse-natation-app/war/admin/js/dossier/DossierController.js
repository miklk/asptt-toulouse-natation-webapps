/**
 * 
 */
var dossierController = angular.module('DossierController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierController.controller('DossierController', ['$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, DossierService, GroupeService) {
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	}); 
	
	$scope.search = function() {
		DossierService.list.query({query: ""}, function(data) {
			$scope.dossiers = data;
		});
	}
}]);