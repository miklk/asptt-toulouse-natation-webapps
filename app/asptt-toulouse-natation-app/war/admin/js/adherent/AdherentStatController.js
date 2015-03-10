/**
 * 
 */
var adherentStatController = angular.module('AdherentsStatCtrl', ['ngRoute', 'textAngular', 'adherentsStatServices', 'adherentsServices', 'adherentServices', 'groupeServices', 'slotServices', 'piscineServices']);
adherentStatController.controller('AdherentsStatCtrl', ['$scope', '$location', 'AdherentsStatService', 'AdherentsService', 'GroupeService', 'SlotService', 'PiscineService', function($scope, $location, AdherentsStatService, AdherentsService, GroupeService, SlotService, PiscineService) {
	$scope.htmlcontent = "<p>Toto</p>";
	$scope.writeEmail = false;
	$scope.formData = {
			search: "",
			groupes: null,
			creneaux: null,
			piscines: null
	};

	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	});
	PiscineService.list.query({}, function(data) {
		$scope.piscines = data.piscines;
	});
	$scope.loadCreneau = function() {
		SlotService.get({groupe: $scope.formData.groupes[0]}, function(data) {
			$scope.creneaux = data.creneaux;
			angular.forEach($scope.creneaux, function(creneau) {
				creneau.label = creneau.dayOfWeek + ' - ' + creneau.swimmingPool + ' - ' + creneau.beginStr + '-' + creneau.endStr; 
			});
		});
	};
	
	$scope.search = function() {
		AdherentsStatService.get({q: $scope.formData.search, groupes:$scope.formData.groupes, creneaux:$scope.formData.creneaux, piscines:$scope.formData.piscines}, function (data) {
			$scope.adherents = data.adherents;
		});
	};
	
	$scope.loadAdherent = function(id) {
		console.log("#/adherents/" + id);
		$location.path("/adherents/" + id);
	};
}]);