/**
 * 
 */
var dossierController = angular.module('DossierController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices']);

dossierController.controller('DossierController', ['$rootScope', '$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', 'SlotService', function($rootScope, $http, $scope, $location, $filter, DossierService, GroupeService, SlotService) {
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	}); 
	
	$scope.search = function() {
		$rootScope.isLoading = true;
		DossierService.list.query({query: $scope.query, groupe: $scope.groupe, sansGroupe: $scope.sansGroupe}, function(data) {
			$scope.dossiers = data;
			$scope.dossierCount = data.length;
			$rootScope.isLoading = false;
		});
	},
	
	$scope.changerGroupe = function(index, dossier) {
		$scope.dossier = dossier;
		$scope.currentIndex = index;
		$('#groupe-popup').modal();
	}
	
	$scope.updateGroupe = function() {
		if(confirm("Souhaitez-vous changer le groupe ? (les créneaux vont être libérés)")) {
			$rootScope.isLoading = true;
			$http.post("/resources/dossiers/changerGroupe", $scope.dossier.nageur, {})
		       .success(function(dataFromServer, status, headers, config) {
		    	  $scope.dossiers.splice($scope.currentIndex, 1, dataFromServer);
		    	  $('#groupe-popup').modal('hide');
		    	  $rootScope.isLoading = false;
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
		$rootScope.isLoading = true;
		$http.post("/resources/dossiers/creer", $scope.creation, {})
	       .success(function(dataFromServer, status, headers, config) {
	    	   if(dataFromServer) {
	    		   alert("Dossier créé avec succès.");
	    		   $('#dossier-creer-popup').modal('hide');
	    		   $rootScope.isLoading = false;
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
			$rootScope.isLoading = true;
			DossierService.remove.query({dossier: dossier.dossierId}, function(data) {
				$rootScope.isLoading = false;
			});
		}
	}
	
	$scope.initPaiement = function(dossier) {
		$scope.paiementStatus = ['PAIEMENT_PARTIEL', 'PAIEMENT_COMPLET'];
		$scope.dossier = dossier;
		$scope.dossierPaiementParameters = {
				dossierId: dossier.dossierId,
				statutPaiement: 'PAIEMENT_COMPLET',
				montantReel: dossier.montantreel,
				commentaire: ''
		};
		$('#dossier-paiement-popup').modal();
	}
	
	$scope.paiement = function() {
		$rootScope.isLoading = true;
		$http.post("/resources/dossiers/paiement", $scope.dossierPaiementParameters, {})
	       .success(function(dataFromServer, status, headers, config) {
    		   alert("Paiement enregistré.");
    		   $('#dossier-paiement-popup').modal('hide');
    		   $rootScope.isLoading = false;
	    	   
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.displayComment = function(dossier) {
		$scope.currentCommentaire = dossier.comment;
		 $('#dossier-comment-popup').modal();
	}
	
	$scope.initCreneaux = function(dossier) {
		$scope.dossier = dossier;
		$scope.dossierCreneauxParameters = {
				nageurId: dossier.nageur.id,
				creneaux: null
		};
		SlotService.list.query({groupe: dossier.nageur.groupe}, function(data) {
			$scope.creneaux = data.creneaux;
			$('#dossier-creneaux-popup').modal();	
		});
		
	}
	
	$scope.updateCreneaux = function() {
		$rootScope.isLoading = true;
		$http.post("/resources/dossiers/creneaux", $scope.dossierCreneauxParameters, {})
	       .success(function(dataFromServer, status, headers, config) {
    		   alert("Créneaux modifiés avec succès.");
    		   $('#dossier-creneaux-popup').modal('hide');
    		   $rootScope.isLoading = false;
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.certificatRecu = function(dossier) {
		DossierService.certificat.query({nageur: dossier.nageur.id}, function(data) {
			alert("Mise à jour avec succès");
		});
	}
}]);