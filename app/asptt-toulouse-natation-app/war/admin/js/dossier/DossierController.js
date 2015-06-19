/**
 * 
 */
var dossierController = angular.module('DossierController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierController.controller('DossierController', ['$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, DossierService, GroupeService) {
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	}); 
	
	$scope.search = function() {
		DossierService.list.query({query: $scope.query, groupe: $scope.groupe}, function(data) {
			$scope.dossiers = data;
			$scope.dossierCount = data.length;
		});
	},
	
	$scope.changerGroupe = function(index, dossier) {
		$scope.dossier = dossier;
		$scope.currentIndex = index;
		$('#groupe-popup').modal();
	}
	
	$scope.updateGroupe = function() {
		if(confirm("Souhaitez-vous changer le groupe ? (les créneaux vont être libérés)")) {
			$http.post("/resources/dossiers/changerGroupe", $scope.dossier.nageur, {})
		       .success(function(dataFromServer, status, headers, config) {
		    	  $scope.dossiers.splice($scope.currentIndex, 1, dataFromServer);
		    	  $('#groupe-popup').modal('hide');
		       })
		        .error(function(data, status, headers, config) {
		          alert("Erreur");
		       });
		} else {
			$('#groupe-popup').modal('hide');	
		}
	}
	
	$scope.initCreerDossier = function() {
		$scope.creation = {
				email: '',
				mdp: '',
				nom: '',
				prenom: ''
		};
		$('#dossier-creer-popup').modal();
	}
	
	$scope.creerDossier = function() {
		$http.post("/resources/dossiers/creer", $scope.creation, {})
	       .success(function(dataFromServer, status, headers, config) {
	    	   if(dataFromServer) {
	    		   alert("Dossier créé avec succès.");
	    		   $('#dossier-creer-popup').modal('hide');
	    	   } else {
	    		   alert("Un dossier avec l'adresse e-mail: " + $scope.creation.email + " existe déjà.");
	    	   }
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.remove = function(index, dossier) {
		if(confirm("Voulez-vous supprimer le dossier ?")) {
			DossierService.remove.query({dossier: dossier.dossierId}, function(data) {
			});
		}
	}
}]);