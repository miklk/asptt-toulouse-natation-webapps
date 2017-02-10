/**
 * 
 */
var dossierNageurController = angular.module('DossierNageurController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices']);

dossierNageurController.controller('DossierNageurController', ['$http', '$scope', '$window', '$location', '$filter', '$routeParams', 'DossierService', 'GroupeService', 'SlotService', function($http, $scope, $window, $location, $filter, $routeParams, DossierService, GroupeService, SlotService) {
	$scope.cspList = ['Scolaire', 'Agriculteurs exploitants', 'Artisans, commerçants et chefs d\'entreprise', 'Cadres et professions intellectuelles supérieures','Professions Intermédiaires', 'Employés', 'Ouvriers', 'Retraités', 'Sans activité professionnelle'];
	
	$scope.paiementLoaded = null;
	DossierService.findOne.query({'dossier': $routeParams.dossierId}, function(data) {
		$scope.dossier = data;
		
		//Handle input date and photo
		angular.forEach($scope.dossier.nageurs, function(nageur) {
				nageur.nageur.naissance = new Date(nageur.nageur.naissance);
		});
		$scope.dossierUpdateParameters = {
				principal: null,
				nageurs: null
		};
	});
	
	$scope.montantCalcule = function() {
		var montant = 0;
		if($scope.dossier) {
			angular.forEach($scope.dossier.nageurs, function(nageur) {
				montant+=nageur.nageur.tarif;
			});
		}
		return montant;
	}
	
	$scope.update = function() {
		$scope.dossierUpdateParameters.principal = $scope.dossier.principal;
		$scope.dossierUpdateParameters.nageurs = $scope.dossier.nageurs;
		
		$http.post("/resources/dossiers/update", $scope.dossierUpdateParameters, {})
	       .success(function(dataFromServer, status, headers, config) {
    		   alert("Dossier mis à jour avec succès.");
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.changerGroupe = function(nageur) {
		GroupeService.all.query({}, function(data) {
			$scope.groupes = data.groups;
			var sansGroupe = {title:'Sans groupe', id:'-1'};
			$scope.groupes.push(sansGroupe);
		});
		$('#groupe-popup').modal();
	};
	
	$scope.changerCreneaux = function(nageur) {
		SlotService.list.query({groupe: nageur.groupe.id}, function(data) {
			$scope.creneaux = data.creneaux;
			$('#dossier-creneaux-popup').modal();
		});
	};
	
	$scope.uploadCertificat = function(element) {
		var formData = new FormData();
		//Récuperer les certificats
		var nageurId = 0;
		file = element.files[0];
		if(file != null) {
			formData.append("file", file);
			nageurId = element.name.split("_")[1];
			var responsePromise = $http.post("/resources/dossiers/uploadCertificat/" + nageurId, formData, {
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        })
		    .success(function(dataFromServer, status, headers, config) {
		    	$scope.uploadCertificatSuccess = true;
		       })
		        .error(function(data, status, headers, config) {
		        	$scope.uploadCertificatSuccess = false;
		          alert("Erreur lors de l'ajout du certificat médical.");
		       });
		}
	};
	
	$scope.creneauLabel = function(creneau) {
		var label = creneau.swimmingPool + ' - ' + creneau.dayOfWeek + ' - ' + $filter('date') (creneau.beginDt, 'HH:mm') + '-' + $filter('date') (creneau.endDt, 'HH:mm') + ' - (' + (creneau.placeDisponible - creneau.placeRestante) + '/' + creneau.placeDisponible + ')';
		if(creneau.second) {
			label = label + " #2";
		}
		return label;
	}
	
	$scope.removeCertificat = function(nageur) {
		if(confirm("Souhaitez-vous supprimer le certificat médical du nageur " + nageur.nageur.nom + " " + nageur.nageur.prenom + " ?")) {
			DossierService.removeCertificat.query({nageur: nageur.nageur.id}, function(data) {
				nageur.hasCertificat = false;
			});
		}
	}
	
	$scope.relancer = function(dossier) {
		DossierService.relancer.query({dossier: dossier.principal.id}, function(data) {
			alert("E-mail de relance envoyé avec succès");
		});
	}
	
	$scope.attente = function(dossier) {
		DossierService.attente.query({dossier: dossier.principal.id}, function(data) {
			alert("Dossier mis en attente (Si c'est une famille tous les dossiers sont en attentes)");
		});
	}
	
	$scope.removeDossier = function(dossier) {
		if(confirm("Voulez-vous supprimer le dossier (et les éventuels membre de la famille) ?")) {
			DossierService.remove.query({dossier: dossier.principal.id}, function(data) {
			});
		}
	}
	
	$scope.initPaiement = function(dossier) {
		$scope.paiementStatus = ['PAIEMENT_PARTIEL', 'PAIEMENT_COMPLET'];
		$scope.dossierPaiementParameters = {
				dossierId: dossier.dossierId,
				statutPaiement: 'PAIEMENT_COMPLET',
				paiement:  [{
					first: 'CHEQUES',
					second : '',
				}],
				
				montantReel: dossier.montantreel,
				commentaire: dossier.comment
		};
		$('#dossier-paiement-popup').modal();
	}
	
	$scope.paiement = function() {
		$http.post("/resources/dossiers/paiement", $scope.dossierPaiementParameters, {})
	       .success(function(dataFromServer, status, headers, config) {
    		   alert("Paiement enregistré.");
    		   $('#dossier-paiement-popup').modal('hide');
	    	   
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.factureSend = function(dossier) {
		DossierService.facture.query({dossier: dossier.principal.id}, null, function(data) {
		});
	}
	
	$scope.uploadIdPhoto = function(element) {
		var formData = new FormData();
		//Upload photo
		var nageurId = 0;
		file = element.files[0];
		if(file != null) {
			formData.append("file", file);
			nageurId = element.name.split("_")[1];
			var responsePromise = $http.post("/resources/dossiers/uploadPhoto/" + nageurId, formData, {
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        })
		    .success(function(dataFromServer, status, headers, config) {
		    	$scope.uploadPhotoSuccess = true;
		       })
		        .error(function(data, status, headers, config) {
		        	$scope.uploadPhotoSuccess = false;
		          alert("Erreur lors de l'ajout de la photo.");
		       });
		}
	};
	
	$scope.getPaiement = function() {
		var paiement = new Object();
		if($scope.dossier && $scope.paiementLoaded == null) {
			if($scope.dossier.principal.paiement) {
				paiement = angular.fromJson($scope.dossier.principal.paiement);
				console.log(paiement);
				if(paiement.length > 0) {
					$scope.paiementLoaded = paiement;	
				}
			}
		}
		return $scope.paiementLoaded;
	}
	
	$scope.addModePaiement = function() {
		$scope.dossierPaiementParameters.paiement.push({
					first: 'CHEQUES',
					second : '',
				});
	}
	
	$scope.adherons = function(nageur) {
			var selectedDossiers = new Array();
			selectedDossiers.push(nageur.dossier);
			DossierService.adherons.query({}, selectedDossiers, function(data) {
				var hiddenElement = document.createElement('a');
			    hiddenElement.href = 'data:attachment/csv,' + encodeURIComponent(data.csv);
			    hiddenElement.target = '_blank';
			    hiddenElement.download = 'adherons.csv';
			    hiddenElement.click();
			});
	}
}]);