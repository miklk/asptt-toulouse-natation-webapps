/**
 * 
 */
var adherentStatController = angular.module('AdherentsStatCtrl', ['ngRoute', 'textAngular', 'adherentsStatServices', 'adherentsServices', 'adherentServices', 'groupeServices', 'slotServices', 'piscineServices']);
adherentStatController.controller('AdherentsStatCtrl', ['$scope', '$location', 'AdherentsStatService', 'AdherentsService', 'GroupeService', 'SlotService', 'PiscineService', function($scope, $location, AdherentsStatService, AdherentsService, GroupeService, SlotService, PiscineService) {
	AdherentsStatService.sexeAge.query({}, function(data) {
		console.log(data);
		$scope.nbAdherents = data.nbAdherents;
		//console.log(data.sexeAgeStat);
		sexeAge("#sexe-age", data.sexeAges);
		localisations("#localisations", data.localisations);
		localisations("#localisations-toulouse", data.localisationsToulouse);
		professions("#professions", data.professions);
		ages("#ages", data.ageArray);
	});
	AdherentsStatService.familles.query({}, function(data) {
		$scope.nbFamilles = data.nbFamilles;
		familles("#familles", data.piscineArray);
		famillesGroupe("#familles-groupes", data.groupeArray);
	});
}]);