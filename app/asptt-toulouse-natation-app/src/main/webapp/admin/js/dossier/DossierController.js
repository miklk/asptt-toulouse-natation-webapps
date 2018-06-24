/**
 * 
 */
var dossierController = angular.module('DossierController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices', 'paramServices']);

dossierController.controller('DossierController', ['$rootScope', '$http', '$scope', '$q', '$location', '$filter', '$timeout', '$cookieStore', 'DossierService', 'GroupeService', 'SlotService', 'ParamService', function($rootScope, $http, $scope, $q, $location, $filter, $timeout, $cookieStore, DossierService, GroupeService, SlotService, ParamService) {
	$rootScope.isLoading = true;
	let promises = {
		param : ParamService.groupes.query({groupe: 'INSCRIPTION'}, function (data) {
			angular.forEach(data, function(param) {
				if(param.key == 'ENABLE_NOUVEAU') {
					$scope.enableNouveau = param;
				} else if(param.key == 'ENABLE_ANCIEN') {
					$scope.enableAncien = param;
				}
			});
		}).$promise,
	
		groupes : GroupeService.all.query({}, function(data) {
			$scope.groupes = data.groups;
			var sansGroupe = {title:'Sans groupe', id:'-1'};
			$scope.groupes.push(sansGroupe);
		}).$promise,
		
		creneaux : SlotService.allPerGroup.query({}, function(data) {
			$scope.creneaux = data;
			console.log($scope.creneaux);
		}).$promise
	};
	$q.all(promises).then(function(data) {
		$rootScope.isLoading = false;
	});
	
	$scope.dossierStatus = ['INITIALISE','PREINSCRIT', 'PAIEMENT_PARTIEL', 'PAIEMENT_COMPLET', 'INSCRIT', 'ANNULE', 'EXPIRE', 'ATTENTE'];
	$scope.certificat = false;
	$scope.showEmail = false;
	
	var query = $cookieStore.get("asptt-dossier-query");
	if(query != null) {
		$rootScope.isLoading = true;
		$scope.query = query[0];
		$scope.groupe = query[1];
		$scope.sansGroupe = query[2];
		$scope.dossierStatut = query[3];
		$scope.creneau = query[4];
		$scope.facture = query[5];
		$scope.facture2 = query[6];
		$scope.certificat = query[7];
		$scope.certificat2 = query[8];
		$scope.certificatNon = query[9];
		DossierService.list.query({query: query[0], groupe: query[1], sansGroupe: query[2], dossierStatut: query[3], creneau: query[4], filter_facture: query[5], filter_facture2: query[6], certificat: query[7], certificat2: query[8], certificatNon: query[9]}, function(data) {
			$scope.dossiers = data;
			$scope.dossierCount = data.length;
			$rootScope.isLoading = false;
		});
	} else {
		$scope.search();
	}
	
	$scope.search = function() {
		$rootScope.isLoading = true;
		var query = [$scope.query, $scope.groupe, $scope.sansGroupe, $scope.dossierStatut, $scope.creneau, $scope.facture, $scope.facture2, $scope.certificat, $scope.certificat2, $scope.certificatNon];
		$cookieStore.put("asptt-dossier-query", query);
		DossierService.list.query({query: query[0], groupe: query[1], sansGroupe: query[2], dossierStatut: query[3], creneau: query[4], filter_facture: query[5], filter_facture2: query[6], certificat: query[7], certificat2: query[8], certificatNon: query[9]}, function(data) {
			$scope.dossiers = data;
			$scope.dossierCount = data.length;
			$rootScope.isLoading = false;
		});
	},
	
	$scope.search2 = function() {
		$rootScope.isLoading = true;
		DossierService.list2.query({query: $scope.query}, function(data) {
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
		if(confirm("Voulez-vous supprimer le dossier (et les éventuels membre de la famille) ?")) {
			$rootScope.isLoading = true;
			DossierService.remove.query({dossier: dossier.dossierId}, function(data) {
				$rootScope.isLoading = false;
			});
		}
	}
	
	$scope.removeNageur = function(index, dossier) {
		if(confirm("Voulez-vous supprimer le dossier du nageur?")) {
			$rootScope.isLoading = true;
			DossierService.removeNageur.query({nageur: dossier.nageur.id}, function(data) {
				$rootScope.isLoading = false;
			});
		}
	}
	
	$scope.initPaiement = function(dossier) {
		$scope.paiementStatus = ['PAIEMENT_PARTIEL', 'PAIEMENT_COMPLET'];
		$scope.modePaiements = ['ESPECES', 'CHEQUES', 'CHEQUE_VACANCES','CHEQUE_SPORT','ANCV','AUTRES'];
		$scope.dossier = dossier;
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
		$rootScope.isLoading = true;
		$http.post("/resources/dossiers/paiement", $scope.dossierPaiementParameters, {})
	       .success(function(dataFromServer, status, headers, config) {
    		   alert("Paiement enregistré.");
    		   $('#dossier-paiement-popup').modal('hide');
    		   $rootScope.isLoading = false;
    		   $scope.search();
	    	   
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.addModePaiement = function() {
		$scope.dossierPaiementParameters.paiement.push({
					first: 'CHEQUES',
					second : '',
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
    		   $scope.search();
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.certificatRecu = function(dossier) {
		DossierService.certificat.query({nageur: dossier.nageur.id}, function(data) {
			alert("Mise à jour avec succès");
			$scope.search();
		});
	}
	
	$scope.initCommentaire = function(dossier) {
		$scope.editCommentaire = true;
		$scope.dossier = dossier;
		$scope.dossierCommentaireParameters = {
				dossier: dossier.dossierId,
				commentaire: dossier.comment
		};
		$('#dossier-comment-popup').modal();
	}
	
	$scope.updateCommentaire = function() {
		$rootScope.isLoading = true;
		$http.post("/resources/dossiers/commentaire", $scope.dossierCommentaireParameters, {})
	       .success(function(dataFromServer, status, headers, config) {
    		   alert("Commentaire modifié avec succès.");
    		   $scope.editCommentaire = false;
    		   $('#dossier-comment-popup').modal('hide');
    		   $rootScope.isLoading = false;
    		   $scope.search();
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });	
	}
	
	$scope.relancer = function(dossier) {
		DossierService.relancer.query({dossier: dossier.dossierId}, function(data) {
			alert("E-mail de relance envoyé avec succès");
			$scope.search();
		});
	}
	
	$scope.relancerSelected = function() {
		var selectedDossiers = getSelectedDossiers();
		DossierService.relancer.query({dossier: -1}, selectedDossiers, function(data) {
			alert("E-mail de relance envoyé avec succès");
			$scope.search();
		});
	}
	
	$scope.attente = function(dossier) {
		DossierService.attente.query({dossier: dossier.dossierId}, function(data) {
			alert("Dossier mis en attente (Si c'est une famille tous les dossiers sont en attentes)");
			$scope.search();
		});
	}
	
	$scope.loadCreneaux = function() {
		if($scope.groupe > 0) {
			SlotService.list.query({groupe: $scope.groupe}, function(data) {
				$scope.creneaux = data.creneaux;
				$scope.showCreneaux = $scope.creneaux.length > 0;
			});
		} else {
			$scope.showCreneaux = false;
		}
	};
	
	$scope.creneauLabel = function(creneau) {
		var label = creneau.swimmingPool + ' - ' + creneau.dayOfWeek + ' - ' + $filter('date') (creneau.beginDt, 'HH:mm') + '-' + $filter('date') (creneau.endDt, 'HH:mm') + ' - (' + (creneau.placeDisponible - creneau.placeRestante) + '/' + creneau.placeDisponible + ')';
		if(creneau.second) {
			label = label + " #2";
		}
		return label;
	}
	
	$scope.selectDossier = function(dossier) {
		dossier.selected = !dossier.selected;
	}
	
	$scope.factureSelected = function() {
		var selectedDossiers = getSelectedDossiers();
		DossierService.facture.query({dossier: -1}, selectedDossiers, function(data) {
			alert("Validation de l'envoi des factures");
		});
	}
	
	$scope.initEmail = function() {
		$scope.showEmail = true;
		$timeout(function() {
		$('html, body').animate({  
            scrollTop:$("#email-top").offset().top  
        }, 'slow');
		}, 10);
	}
	
	function getSelectedDossiers() {
		var selectedDossiers = new Array();
		angular.forEach($scope.dossiers, function(dossier) {
			if(dossier.selected) {
				selectedDossiers.push(dossier.dossierId);
			}
		});
		return selectedDossiers;
	};
	
	$scope.annulerNageur = function(dossier) {
		if(confirm("Voulez-vous annuler le dossier de ce nageur?")) {
			$rootScope.isLoading = true;
			DossierService.annuler.query({nageur: dossier.nageur.id}, function(data) {
				$rootScope.isLoading = false;
			});
		}
	}
	
	$scope.toggleParam = function(param) {
		if(param.value === 'true') {
			param.value = 'false';
		} else {
			param.value = 'true';
		}
		ParamService.save.query('', param, function(data) {
			
		});
	}
	
	$scope.getGroupe = function(id) {
		return $scope.groupes.find(function(element) {
			return element.id == id;
		});
		
	}
	
	$scope.getCreneaux = function(group, list) {
		var result = "";
		if(list != null) {
			var creneaux = list.split(";");
			angular.forEach($scope.creneaux[group], function(creneau) {
				if(creneaux.indexOf(creneau.id + "") != -1) {
					result = $scope.creneauLabel(creneau) + "\n";
				}
			});
		} else {
			result = "Pas de créneaux";
		}
		return result;
	}
	
	$scope.getMontantDossier = function(nageurs) {
		var montant = 0;
		angular.forEach(nageurs, function(nageur) {
			montant = montant + nageur.tarif;
			console.log(montant);
		});
		return montant;
	}
	
	$scope.adheronsSelected = function() {
		var selectedDossiers = getSelectedDossiers();
		DossierService.adherons.query({}, selectedDossiers, function(data) {
			var hiddenElement = document.createElement('a');
		    hiddenElement.href = 'data:attachment/csv,' + encodeURIComponent(data.csv);
		    hiddenElement.target = '_blank';
		    hiddenElement.download = 'adherons.csv';
		    hiddenElement.click();
		});
	}
	
}]);