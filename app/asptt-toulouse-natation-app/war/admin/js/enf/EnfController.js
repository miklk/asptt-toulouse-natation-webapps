/**
 * 
 */
var enfController = angular.module('EnfController', ['ngRoute', 'enfServices', 'groupeServices', 'slotServices']);

enfController.controller('EnfController', ['$rootScope', '$http', '$scope', '$location', '$filter', 'EnfService', 'SlotService', function($rootScope, $http, $scope, $location, $filter, EnfService, SlotService) {
	$rootScope.isLoading = true;
	$scope.creneauSelected = null;
	SlotService.all.query({}, function(data) {
		$scope.creneaux = data.creneaux;
	});
	
	EnfService.creneaux.query({}, function(data) {
		$scope.days = data;
		$rootScope.isLoading = false;
	});
	
	$scope.creneauLabel = function(creneau) {
		var label = creneau.swimmingPool + ' - ' + creneau.dayOfWeek + ' - ' + $filter('date') (creneau.beginDt, 'HH:mm') + '-' + $filter('date') (creneau.endDt, 'HH:mm') + ' - (' + (creneau.placeDisponible - creneau.placeRestante) + '/' + creneau.placeDisponible + ')';
		if(creneau.second) {
			label = label + " #2";
		}
		return label;
	}
}]);