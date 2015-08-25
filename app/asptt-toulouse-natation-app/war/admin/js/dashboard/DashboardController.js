/**
 * 
 */
var dashboardController = angular.module('DashboardController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices']);

dashboardController.controller('DashboardController', ['$rootScope', '$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', 'SlotService', function($rootScope, $http, $scope, $location, $filter, DossierService, GroupeService, SlotService) {
	DossierService.statistiques.query({}, function(data) {
		$scope.statistiques = data;
		dossiers("#dossiers-graph", data.dossiers);
		nageurs("#nageurs-graph", data.nageurs);
		
	});
}]);