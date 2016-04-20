/**
 * 
 */
var adherentStatController = angular.module('AdherentsStatCtrl', ['ngRoute', 'textAngular', 'adherentsStatServices', 'adherentsServices', 'groupeServices', 'slotServices', 'piscineServices']);
adherentStatController.controller('AdherentsStatCtrl', ['$rootScope', '$scope', '$location', 'AdherentsStatService', 'AdherentsService', 'GroupeService', 'SlotService', 'PiscineService', function($rootScope, $scope, $location, AdherentsStatService, AdherentsService, GroupeService, SlotService, PiscineService) {
	$rootScope.isLoading = true;
	$scope.showStatusDetails = false;
	AdherentsStatService.sexeAge.query({}, function(data) {
		console.log(data);
		$scope.nbAdherents = data.nbAdherents;
		$scope.perStatus = data.perStatus;
		$scope.nbComplet = data.complets;
		$scope.pourcentageComplet = Math.floor((data.complets / data.nbAdherents) * 100);
		$scope.nbNouveau = data.nouveau;
		$scope.pourcentageNouveau = Math.floor((data.nouveau / data.nbAdherents) * 100);
		$scope.nbRenouvellement = data.renouvellement;
		$scope.pourcentageRenouvellement = Math.floor((data.renouvellement / data.nbAdherents) * 100);
		$scope.nbAnnuler = data.perStatus['ANNULER'];
		$scope.pourcentageAnnuler = Math.floor(($scope.nbAnnuler / data.nbAdherents) * 100);
		$scope.averageAge = data.averageAge;
		$scope.tranche1 = data.tranche1;
		$scope.tranche2 = data.tranche2;
		//console.log(data.sexeAgeStat);
		sexeAge("#sexe-age", data.sexeAges);
		localisations("#localisations-toulouse", data.localisationsToulouse);
		professions("#professions", data.professions);
		ages("#ages", data.ageArray);
		$rootScope.isLoading = false;
	});
	AdherentsStatService.familles.query({}, function(data) {
		$scope.nbFamilles = data.nbFamilles;
		familles("#familles", data.piscineArray);
		famillesGroupe("#familles-groupes", data.groupeArray);
		$rootScope.isLoading = false;
	});
}]);