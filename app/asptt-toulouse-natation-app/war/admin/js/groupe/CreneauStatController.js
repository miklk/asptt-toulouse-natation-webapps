/**
 * 
 */
var creneauStatController = angular.module('CreneauStatController', ['ngRoute', 'creneauStatServices']);

creneauStatController.controller('CreneauStatController', ['$http', '$rootScope', '$scope', '$location', '$filter', 'CreneauStatService', function($http, $rootScope, $scope, $location, $filter, CreneauStatService) {
	$rootScope.isLoading = true;
	CreneauStatService.byGroupe.query({}, function(data) {
		$scope.groupes = data;
		$rootScope.isLoading = false;
	});
	
	CreneauStatService.byGroupe.query({enf: true}, function(data) {
		$scope.groupesEnf = data;
		$rootScope.isLoading = false;
	});
	
	CreneauStatService.byPiscine.query({}, function(data) {
		$scope.piscines = data;
		$rootScope.isLoading = false;
	});
	
	$scope.groupeOccupe = function(groupe) {
		return groupe.capacite - groupe.disponibles;
	}
	$scope.groupeTaux = function(groupe) {
		return ((groupe.capacite - groupe.disponibles) / groupe.capacite ) * 100;
	}
	
	$scope.getTotalCapacite = function(liste) {
		var total = 0;
		angular.forEach(liste, function(element) {
			total = total + element.capacite;
		});
		return total;
	}
	
	$scope.getTotalDisponible = function(liste) {
		var total = 0;
		angular.forEach(liste, function(element) {
			total = total + element.disponibles;
		});
		return total;
	}
	
	$scope.getTotalOccupe = function(liste) {
		var total = 0;
		angular.forEach(liste, function(element) {
			total = total + (element.capacite - element.disponibles);
		});
		return total;
	}
	
	$scope.getTotalTaux = function(liste) {
		var occupe = 0;
		var capacite = 0;
		angular.forEach(liste, function(element) {
			occupe = occupe + (element.capacite - element.disponibles);
			capacite = capacite + element.capacite;
		});
		return (occupe / capacite) * 100;
	}
	
	$scope.jourSort = function(jour) {
		var result = 0;
		switch(jour.statTitle) {
		case "Lundi": result = 1;
		break;
		case "Mardi": result = 2;
		break;
		case "Mercredi": result = 3;
		break;
		case "Jeudi": result = 4;
		break;
		case "Vendredi": result = 5;
		break;
		case "Samedi": result = 6;
		break;
		case "Dimanche": result = 7;
		break;
		default: result = 0;
		}
		return result;
	}
}]);