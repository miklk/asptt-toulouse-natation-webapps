/**
 * 
 */
var groupeController = angular.module('GroupeController', ['ngRoute', 'groupeServices', 'slotServices', 'piscineServices']);

groupeController.controller('GroupeController', ['$http', '$scope', '$location', '$filter', '$timeout', 'GroupeService', 'SlotService', 'PiscineService', function($http, $scope, $location, $filter, $timeout, GroupeService, SlotService, PiscineService) {
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	}); 
	
	$scope.selectGroupe = function(index, groupe) {
		$scope.currentIndex = index;
		$scope.groupe = groupe;
		$scope.groupePopupTitle = "Modifier le groupe";
		$('#groupe-popup').modal();
	}
	
	$scope.prepareCreate = function() {
		$scope.groupe = new Object();
		$scope.groupePopupTitle = "Créer un groupe";
		$('#groupe-popup').modal();
	}
	
	$scope.create = function() {
		$http.post("/resources/groupes/create", $scope.groupe, {})
	       .success(function(dataFromServer, status, headers, config) {
	    	   if(dataFromServer.creation) {
	    		   $scope.groupes.push(dataFromServer.groupe);
	    	   }
	    	  $('#groupe-popup').modal('hide');
	    	  
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.remove = function(index, groupe) {
		if(confirm("Voulez-vous supprimer le groupe " + groupe.title + " ?")) {
			GroupeService.remove.query({groupe: groupe.id}, function(data) {
				$scope.groupes.splice(index, 1);
			  	$('#groupe-popup').modal('hide');	
			});
		}
	}
	
	$scope.loadCreneaux = function(groupe) {
		$scope.creneaux = null;
		$scope.groupe = groupe;
		SlotService.byDay.query({groupe: groupe.id}, function(data) {
			$scope.creneaux = data.slots;
			$timeout(function() {
				$('html, body').animate({  
		            scrollTop:$("#panel-creneaux").offset().top  
		        }, 'slow');
			}, 10);
		});
		PiscineService.all.query({}, function(data) {
			$scope.piscines = data;
		});
	}
	
	$scope.prepareCreneau = function() {
		SlotService.secondes.query({groupe: $scope.groupe.id}, function(data) {
			$scope.secondes = data.creneaux;
		});
		$scope.creneau = new Object();
		$scope.creneauPopupTitle = "Ajouter un créneau au groupe " + $scope.groupe.title;
		$('#creneau-popup').modal();
	}
	
	$scope.selectCreneau = function(index, creneau, jour) {
		SlotService.secondes.query({groupe: $scope.groupe.id}, function(data) {
			$scope.secondes = data.creneaux;
		});
		$scope.currentIndex = index;
		$scope.creneau = creneau;
		$scope.creneau.beginDt = new Date($scope.creneau.beginDt);
		$scope.creneau.endDt = new Date($scope.creneau.endDt);
		$scope.creneauPopupTitle = "Modifier le créneau " + creneau.dayOfWeek + " ("+$filter('date')($scope.creneau.beginDt,'HH:mm')+"/"+$filter('date')($scope.creneau.endDt,'HH:mm')+")";
		$('#creneau-popup').modal();
	}
	
	$scope.createCreate = function() {
		$http.post("/resources/creneaux/create/" + $scope.groupe.id, $scope.creneau, {})
	       .success(function(dataFromServer, status, headers, config) {
	    	  $scope.loadCreneaux($scope.groupe);
	    	  $('#creneau-popup').modal('hide');
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.removeCreneau = function() {
		if(confirm("Voulez-vous supprimer ce créneau ?")) {
			SlotService.remove.query({creneau: $scope.creneau.id}, function(data) {
				$scope.loadCreneaux($scope.groupe);
		    	$('#creneau-popup').modal('hide');	
			});
		}
	}
	
	$scope.clearCreneaux = function() {
		if(confirm("Voulez-vous supprimer tous les créneaux ?")) {
			SlotService.clear.query({groupe: $scope.groupe.id}, function(data) {
				$scope.loadCreneaux($scope.groupe);
			});
		}
	}
}]);