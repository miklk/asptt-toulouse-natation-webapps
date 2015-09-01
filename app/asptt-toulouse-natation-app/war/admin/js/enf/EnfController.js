/**
 * 
 */
var enfController = angular.module('EnfController', ['ngRoute', 'enfServices', 'groupeServices', 'slotServices']);

enfController.controller('EnfController', ['$rootScope', '$http', '$scope', '$location', '$filter', 'EnfService', 'SlotService', function($rootScope, $http, $scope, $location, $filter, EnfService, SlotService) {
	$scope.creneauSelected = 0;
	
	SlotService.all.query({}, function(data) {
		$scope.creneaux = data.creneaux;
	});
	
	$scope.jours = function(day, piscine) {
		$http.get("/resources/enf/jours/" + day + "/" + piscine, {})
	       .success(function(dataFromServer, status, headers, config) {
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.creneauLabel = function(creneau) {
		var label = creneau.swimmingPool + ' - ' + creneau.dayOfWeek + ' - ' + $filter('date') (creneau.beginDt, 'HH:mm') + '-' + $filter('date') (creneau.endDt, 'HH:mm') + ' - (' + (creneau.placeDisponible - creneau.placeRestante) + '/' + creneau.placeDisponible + ')';
		if(creneau.second) {
			label = label + " #2";
		}
		return label;
	}
	
	$scope.presences = function() {
		$http.get("/resources/enf/presences/" + $scope.creneauSelected, {})
	       .success(function(dataFromServer, status, headers, config) {
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
}]);