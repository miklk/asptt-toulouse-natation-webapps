/**
 * 
 */
var suiviNageurController = angular.module('SuiviNageurController', ['ngRoute', 'suiviNageurServices', 'groupeServices']);

suiviNageurController.controller('SuiviNageurController', ['$scope', '$location', 'SuiviNageurService', 'GroupeService', function($scope, $location, SuiviNageurService, GroupeService) {
	$scope.groupe = "";
	$scope.day = new Date();
	$scope.groupes = GroupeService.all.query({});
	$scope.swimmerStatUpdateAllAction = {
	                                     dayTime:'',
	                                     day: '',
	                                     valeur: '',
	                                     swimmers: null};
	
	$scope.loadSuivi = function() {
		SuiviNageurService.list.query({groupe: $scope.groupe.id, day: $scope.day.getTime()}, function(data) {
			$scope.nageurs = data.swimmers;
		});
	};
	
	$scope.updateSuivi = function(nageur, stat, dayTime) {
		$scope.swimmerStatUpdateAction = {
				dayTime: dayTime,
				day: $scope.day.getTime(),
				stat: stat};
		SuiviNageurService.update.query({nageur: nageur}, $scope.swimmerStatUpdateAction, function(data) {
			console.log(data);
		});
	};
	
	$scope.setSelected = function(nageur) {
		console.log(nageur);
		nageur.selected = !nageur.selected;
	};
	
	$scope.updateSelected = function() {
		angular.forEach($scope.nageurs, function(nageur) {
			if(nageur.selected) {
				switch($scope.swimmerStatUpdateAllAction.dayTime) {
				case 'MATIN': nageur.morning.distance = $scope.swimmerStatUpdateAllAction.valeur;
				break;
				case 'MIDI': nageur.midday.distance = $scope.swimmerStatUpdateAllAction.valeur;
				break;
				case 'SOIR': nageur.night.distance = $scope.swimmerStatUpdateAllAction.valeur;
				break;
				default://
				}
			}
		});
		$scope.swimmerStatUpdateAllAction.day = $scope.day.getTime();
		$scope.swimmerStatUpdateAllAction.swimmers = $scope.nageurs;
		SuiviNageurService.updateAll.query({}, $scope.swimmerStatUpdateAllAction, function(data) {
			console.log(data);
		});
	}
}]);