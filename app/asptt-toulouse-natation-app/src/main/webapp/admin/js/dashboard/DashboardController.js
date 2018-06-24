/**
 * 
 */
var dashboardController = angular.module('DashboardController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices']);

dashboardController.controller('DashboardController', ['$rootScope', '$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', 'SlotService', function($rootScope, $http, $scope, $location, $filter, DossierService, GroupeService, SlotService) {
	$rootScope.isLoading = true;
	DossierService.statistiques.query({}, function(data) {
		$scope.statistiques = data;
		nageurs("#nageurs-graph", data.nageursDetail);
		$rootScope.isLoading = false;
	});
}]);