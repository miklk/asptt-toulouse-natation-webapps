/**
 * 
 */
var dossierController = angular.module('DossierController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierController.controller('DossierController', ['$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, DossierService, GroupeService) {
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	}); 
}]);