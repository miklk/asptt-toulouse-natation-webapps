/**
 * 
 */
var dossierNageurController = angular.module('DossierNageurController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierNageurController.controller('DossierNageurController', ['$http', '$scope', '$location', '$filter', '$routeParams', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, $routeParams, DossierService, GroupeService) {
	DossierService.findOne.query({'dossier': $routeParams.dossierId}, function(data) {
		$scope.dossier = data;
	});
}]);