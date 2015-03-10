/**
 * 
 */
var adherentController = angular.module('AdherentListCtrl', ['ngRoute', 'textAngular', 'adherentsServices', 'adherentServices', 'groupeServices', 'slotServices', 'piscineServices']);
adherentController.controller('AdherentListCtrl', ['$scope', '$location', 'AdherentsService', 'GroupeService', 'SlotService', 'PiscineService', function($scope, $location, AdherentsService, GroupeService, SlotService, PiscineService) {
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
		AdherentsService.list.query({q: $scope.formData.search, groupes:$scope.formData.groupes, creneaux:$scope.formData.creneaux, piscines:$scope.formData.piscines}, function (data) {
			$scope.adherents = data.adherents;
		});
	};
	
	$scope.loadAdherent = function(id) {
		console.log("#/adherents/" + id);
		$location.path("/adherents/" + id);
	};
}]);

adherentController.controller('AdherentCtrl', ['$scope', 'AdherentService', 'GroupeService', 'SlotService', '$routeParams', '$sce', function($scope, AdherentService, GroupeService, SlotService, $routeParams, $sce) {
	AdherentService.get({adherent: $routeParams.adherentId}, function (data) {
		$scope.adherent = data;
		$scope.loadGroupe = function() {
			GroupeService.list.query({nouveau: false}, function (data) {
				$scope.groupes = data;
				$("#panel-adherent").toggleClass("col-md-11", false);
				$("#panel-adherent").toggleClass("col-md-6", true);
				$("#panel-groupe").toggleClass("col-md-6", true);
				$("#panel-groupe").toggleClass("hidden", false);
				$("#panel-groupe").toggleClass("show", true);
				$("#panel-creneaux").toggleClass("hidden", true);
				$("#panel-creneaux").toggleClass("show", false);
				$('html, body').animate({  
		            scrollTop:$("#panel-adherent").offset().top  
		        }, 'slow');
			});
		}
		$scope.loadCreneaux = function(groupe) {
			SlotService.get({groupe: $scope.adherent.groupe.id}, function (data) {
				$scope.creneaux = data.creneaux;
				$("#panel-adherent").toggleClass("col-md-11", false);
				$("#panel-adherent").toggleClass("col-md-6", true);
				$("#panel-groupe").toggleClass("show", false);
				$("#panel-groupe").toggleClass("hidden", true);
				$("#panel-creneaux").toggleClass("col-md-6", true);
				$("#panel-creneaux").toggleClass("hidden");
				$("#panel-creneaux").toggleClass("show");
				$('html, body').animate({  
		            scrollTop:$("#panel-adherent").offset().top  
		        }, 'slow');
			});
		}
		$scope.updateAdherent = function() {
			var req = new AdherentService($scope.adherent);
			req.$save();
			$("#panel-creneaux").toggleClass("hidden");
			$("#panel-adherent").toggleClass("col-md-11");
			$("#panel-adherent").toggleClass("col-md-6");
			$('html, body').animate({  
	            scrollTop:$("#panel-adherent").offset().top  
	        }, 'slow');
		}
	});
}]);