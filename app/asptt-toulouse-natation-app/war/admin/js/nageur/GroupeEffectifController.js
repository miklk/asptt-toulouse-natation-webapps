/**
 * 
 */
var groupeController = angular.module('GroupeEffectifController', ['ngRoute', 'groupeServices', 'slotServices', 'dossierServices']);

groupeController.controller('GroupeEffectifController', ['$http', '$scope', '$location', '$filter', '$timeout', 'GroupeService', 'SlotService', 'DossierService', function($http, $scope, $location, $filter, $timeout, GroupeService, SlotService, DossierService) {
	$scope.showDays = false;
	$scope.showNageurs = false;
	
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	}); 
	
	$scope.findDays = function() {
		$scope.showNageurs = false;
		SlotService.days.query({groupe: $scope.selectedGroupe.id}, function(data) {
			$scope.days = data;
			$scope.showDays = true;
		});
	}
	
	$scope.findNageurs = function() {
		$scope.showNageurs = false;
		DossierService.byGroupeDay.query({groupe: $scope.selectedGroupe.id, day: $scope.selectedDay}, function(data) {
			$scope.nageurs = data;
			$scope.showNageurs = true;
		});
	}
	
}]);