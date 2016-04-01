/**
 * 
 */
var suiviNageurController = angular.module('SuiviNageurController', ['ngRoute', 'suiviNageurServices', 'groupeServices']);

suiviNageurController.controller('SuiviNageurController', ['$scope', '$location', '$filter', 'SuiviNageurService', 'GroupeService', function($scope, $location, $filter, SuiviNageurService, GroupeService) {
	$scope.groupesSelected;
	$scope.day = new Date();
	$scope.endDay = new Date();
	$scope.groupes = GroupeService.all.query({});
	$scope.swimmerStatUpdateAllAction = {
	                                     dayTime:'',
	                                     day: '',
	                                     valeur: '',
	                                     presence: '',
	                                     swimmers: null};
	function load(selectedNageur) {
		var groupes = [];
		groupes.push($scope.groupesSelected.id);
		SuiviNageurService.days.query({groupes: groupes, day: $scope.day.getTime()}, function(data) {
			var enf = true;
			angular.forEach($scope.groupesSelected, function(groupe) {
				enf = enf && groupe.enf;
			});
			$scope.enf = enf;
			if(enf) {
				$scope.swimmerStatUpdateAllAction.dayTime = 'PRESENCE';
			} else {
				$scope.swimmerStatUpdateAllAction.dayTime = 'MATIN';
			}
			$scope.nageurs = $filter('orderBy') (data.swimmers, ['+nom', '+prenom']);
			angular.forEach($scope.nageurs, function(nageur) {
				if(nageur.adherent == selectedNageur) {
					nageur.updated = true;
				}
			});
		});
	}
	
	$scope.loadSuivi = function() {
		load();
	};
	
	$scope.updateSuivi = function(nageur, stat, dayTime) {
		$scope.swimmerStatUpdateAction = {
				dayTime: dayTime,
				day: $scope.day.getTime(),
				stat: stat};
		SuiviNageurService.update.query({nageur: nageur}, $scope.swimmerStatUpdateAction, function(data) {
			console.log(data);
			load(nageur);
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
				case 'PRESENCE': nageur.presence.presence = $scope.swimmerStatUpdateAllAction.presence;
				break;
				default://
				}
			}
		});
		$scope.swimmerStatUpdateAllAction.day = $scope.day.getTime();
		$scope.swimmerStatUpdateAllAction.swimmers = $scope.nageurs;
		SuiviNageurService.updateAll.query({}, $scope.swimmerStatUpdateAllAction, function(data) {
			console.log(data);
			load();
		});
	};
	
	$scope.loadWeeks = function() {
		var groupes = [];
		groupes.push($scope.groupesSelected.id);
		SuiviNageurService.weeks.query({groupes: groupes,  week: $filter('date')($scope.day,'yyyy-Www')}, function(data) {
			$scope.nageurs = $filter('orderBy') (data.nageurs, ['+nom', '+prenom']);
			$scope.beginDt = data.begin;
			$scope.endDt = data.end;
			var enf = true;
			angular.forEach($scope.groupesSelected, function(groupe) {
				enf = enf && groupe.enf;
			});
			$scope.enf = enf;
		});
	};
	
	$scope.loadMonths = function() {
		var groupes = [];
		groupes.push($scope.groupesSelected.id);
		SuiviNageurService.months.query({groupes: groupes,  month: $filter('date')($scope.day,'yyyy-MM')}, function(data) {
			$scope.nageurs = $filter('orderBy') (data.nageurs, ['+nom', '+prenom']);
			$scope.beginDt = data.begin;
			$scope.endDt = data.end;
			$scope.weeks = data.weeks;
			var enf = true;
			angular.forEach($scope.groupesSelected, function(groupe) {
				enf = enf && groupe.enf;
			});
			$scope.enf = enf;
		});
	};
	
	$scope.loadYears = function() {
		var groupes = [];
		groupes.push($scope.groupesSelected.id);
		SuiviNageurService.years.query({groupes: groupes, year: $filter('date')($scope.day,'yyyy')}, function(data) {
			$scope.nageurs = $filter('orderBy') (data.nageurs, ['+nom', '+prenom']);
			$scope.beginDt = data.begin;
			$scope.endDt = data.end;
			var enf = true;
			angular.forEach($scope.groupesSelected, function(groupe) {
				enf = enf && groupe.enf;
			});
			$scope.enf = enf;
		});
	};
	
	$scope.loadPeriode = function() {
		var groupes = [];
		groupes.push($scope.groupesSelected.id);
		SuiviNageurService.periode.query({groupes: groupes, beginDay: $scope.day.getTime(), endDay: $scope.endDay.getTime()}, function(data) {
			$scope.nageurs = $filter('orderBy') (data.nageurs, ['+nom', '+prenom']);
			$scope.beginDt = data.begin;
			$scope.endDt = data.end;
			$scope.weeks = data.weeks;
			var enf = true;
			angular.forEach($scope.groupesSelected, function(groupe) {
				enf = enf && groupe.enf;
			});
			$scope.enf = enf;
		});
	}
	
	$scope.computeTotalPresence = function(nageur) {
		var total = 0;
		angular.forEach(nageur.distances, function(distance) {
			if(distance[0] > 0) {
				total++;
			}
			if(distance[1] > 0) {
				total++;
			}
			if(distance[2] > 0) {
				total++;
			}
		});
		return total;
	}
}]);