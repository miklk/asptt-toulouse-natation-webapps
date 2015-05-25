/**
 * 
 */
var inscriptionController = angular.module('InscriptionController', ['inscriptionServices', 'inscriptionNouveauServices', 'removeAdherentServices']);

inscriptionController.controller('InscriptionCtrl', ['$http', '$scope', '$filter', 'InscriptionService', 'SlotService', 'GroupeService', 'InscriptionNouveauService', 'RemoveAdherentService', '$routeParams', '$sce', function($http, $scope, $filter, InscriptionService, SlotService, GroupeService, InscriptionNouveauService, RemoveAdherentService, $routeParams, $sce) {
	$scope.formData = {
			email: "",
			mdp: "",
			nouveau: ""
	};
	
	$scope.csp = ['Scolaire', 'Agriculteurs exploitants', 'Artisans, commerçants et chefs d\'entreprise', 'Cadres et professions intellectuelles supérieures','Professions Intermédiaires', 'Employés', 'Ouvriers', 'Retraités', 'Sans activité professionnelle'];
	
	var datepickerInput = $('.input-group.date').datepicker({
		format: "dd/mm/yyyy",
		language: "fr"
	});
	datepickerInput.on("changeDate", function(e) {
		$scope.dossiers[$scope.currentDossier].dossier.datenaissance = $filter('date') (e.date, "yyyy-MM-dd");
		$scope.dossiers[$scope.currentDossier].dossier.naissance = e.date.getTime();
		$scope.dossiers[$scope.currentDossier].dossier.naissanceAsString = $filter('date') (e.date, "dd/MM/yyyy");
		datepickerInput.datepicker('hide');
	});
	
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		});
	
	$scope.subscribe = function() {
		InscriptionNouveauService.put({email: $scope.formData.nouveau});
		alert("A l'ouverture des inscriptions, vous serez informé par e-mail. Bonne vacances !");
	};
	
	$scope.nouveauCode = function() {
		InscriptionNouveauService.get({email: $scope.formData.nouveau}, function(data) {
			if(data.exist) {
				alert("L'adresse e-mail "
							+ $scope.formData.nouveau
							+ " est déjà enregistrée. Vous avez dû recevoir votre code d'accès (pensez à vérifier vos spams). Dans le cas contraire contactez webmaster@asptt-toulouse-natation.com");
			} else {
				alert("Votre code d'accès vous a été envoyé par e-mail.");
			}
		});
	};

	// process the form
	$scope.oublie = function() {
		 var responsePromise = $http.get("/adherentList.do?action=forgetEmail&email=" + $scope.formData.email);
	       responsePromise.success(function(dataFromServer, status, headers, config) {
	    	  alert("Vous allez recevoir votre mot de passe par e-mail");
	       });
	        responsePromise.error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	};
	$scope.authenticate = function() {
		InscriptionService.get({email: $scope.formData.email, mdp: $scope.formData.mdp}, function (data) {
			if(data.inconnu) {
				alert("Vous n'êtes pas dans nos listing. Peut être qu'ils ne sont pas à jour. webmaster@asptt-toulouse-natation.com");
			} else {
				$scope.links = null;
				$scope.dossiersCount = data.dossiers.length;
				$scope.dossiers = data.dossiers;
				$scope.principal = data.principal;
				
				//Determine si les informations accordNomPrenom et accidentNom1 et accidentNom2 sont remplis (sauvé en base)
				$scope.accordNotFilled = !$scope.principal.dossier.accordNomPrenom;
				$scope.accidentNom1NotFilled = !$scope.principal.dossier.accidentNom1;
				$scope.accidentNom2NotFilled = !$scope.principal.dossier.accidentNom2;
				
				$scope.deleteDossier = function(index) {
					$scope.dossiers[index].supprimer = true;
					RemoveAdherentService.remove({adherentId: $scope.dossiers[index].dossier.id}, function() {
						InscriptionService.get({email: $scope.formData.email, mdp: $scope.formData.mdp}, function (data) {
							$scope.dossiersCount = data.dossiers.length;
							$scope.dossiers = data.dossiers;
							$scope.principal = data.principal;
						});
					});
					
				};
				
				if(data.principal.dossier.nouveau) {
					var index = 0;
					$scope.hideSeconde = true;
					$scope.unique = false;
					$scope.currentDossier = index;
					$scope.creneauxSelection = "";
					$scope.groupes = GroupeService.get({nouveau: $scope.dossiers[index].dossier.nouveau});
					if($scope.dossiers[index].groupe != null) {
						$scope.slots = SlotService.get({groupe: $scope.dossiers[index].groupe.id, creneaux: $scope.dossiers[index].dossier.creneaux});
						$scope.dossiers[index].creneaux = $scope.slots;
					}
					$('#myTab a[href="#activite"]').tab('show');
				} else {
					$('#myTab a[href="#dossiers"]').tab('show');
				}
				$scope.loadDossier = function(index) {
					$scope.hideSeconde = true;
					$scope.unique = false;
					$scope.currentDossier = index;
					$scope.creneauxSelection = "";
					$scope.groupes = GroupeService.get({nouveau: $scope.dossiers[index].dossier.nouveau});
					if($scope.dossiers[index].groupe != null) {
						$scope.slots = SlotService.get({groupe: $scope.dossiers[index].groupe.id, creneaux: $scope.dossiers[index].dossier.creneaux});
						$scope.dossiers[index].creneaux = $scope.slots;
					}
					$('#myTab a[href="#activite"]').tab('show');
				};
				$scope.loadSlots = function() {
					$scope.hideSeconde = true;
					$scope.unique = false;
					$scope.slots = SlotService.get({groupe: $scope.dossiers[$scope.currentDossier].groupe.id, creneaux: $scope.dossiers[$scope.currentDossier].dossier.creneaux});
					$scope.dossiers[$scope.currentDossier].creneaux = $scope.slots;
				};
				$scope.toggleCreneau = function(slots, slot) {
					if($scope.dossiers[$scope.currentDossier].groupe.seanceunique) {
						$scope.unique = true;
					} else {
						$scope.unique = false;	
					}
					if(!slot.second && slots.seconde) {
						$scope.hideSeconde = false;
						alert("Vous avez le droit à une seconde séance");
					}
				};
				$scope.resetSlot = function() {
					$scope.hideSeconde = true;
					$scope.unique = false;
					var slotsArray = $($scope.dossiers[$scope.currentDossier].creneaux.slots);
					slotsArray.each(function() {
						$(this.lundi).each(function() {
							this.selected = false;
						});
						$(this.mardi).each(function() {
							this.selected = false;
						});
						$(this.mercredi).each(function() {
							this.selected = false;
						});
						$(this.jeudi).each(function() {
							this.selected = false;
						});
						$(this.vendredi).each(function() {
							this.selected = false;
						});
						$(this.samedi).each(function() {
							this.selected = false;
						});
					});
				};
				$scope.ajouterEnfant = function() {
					 var responsePromise = $http.post("/resources/ajouterEnfant", data, {});
				       responsePromise.success(function(dataFromServer, status, headers, config) {
				    	  data = dataFromServer.dossiers;
				    	  $scope.dossiersCount = dataFromServer.dossiers.dossiers.length;
						  $scope.dossiers = dataFromServer.dossiers.dossiers;
				          $scope.currentDossier = dataFromServer.currentIndex;
				          $scope.slots = null;
				          $scope.groupes = GroupeService.get({nouveau: true});
				          $('#myTab a[href="#activite"]').tab('show');
				       });
				        responsePromise.error(function(data, status, headers, config) {
				          alert("Erreur");
				       });
				};
				$scope.dossiersValide = function(dossiers) {
					var areValide = true;
					$(dossiers).each(function () {
						if(!this.supprimer) {
							areValide = areValide && this.creneauSelected;
						}
					});
					return !areValide;
				};
				$scope.validateActivite = function(dossier) {
					var slotsArray = $($scope.dossiers[$scope.currentDossier].creneaux.slots);
					slotsArray.each(function() {
						$(this.lundi).each(function() {
							if(this.selected) {
								$scope.dossiers[$scope.currentDossier].creneauSelected = true;
							}
						});
						$(this.mardi).each(function() {
							if(this.selected) {
								$scope.dossiers[$scope.currentDossier].creneauSelected = true;
							}
						});
						$(this.mercredi).each(function() {
							if(this.selected) {
								$scope.dossiers[$scope.currentDossier].creneauSelected = true;
							}
						});
						$(this.jeudi).each(function() {
							if(this.selected) {
								$scope.dossiers[$scope.currentDossier].creneauSelected = true;
							}
						});
						$(this.vendredi).each(function() {
							if(this.selected) {
								$scope.dossiers[$scope.currentDossier].creneauSelected = true;
							}
						});
						$(this.samedi).each(function() {
							if(this.selected) {
								$scope.dossiers[$scope.currentDossier].creneauSelected = true;
							}
						});
					});
					
					var naissance = $scope.dossiers[$scope.currentDossier].dossier.naissance;
					if(naissance != null) {
						datepickerInput.datepicker('setDate', new Date(naissance));
					} else {
						datepickerInput.datepicker('setDate', new Date());
					}
					
					$('#myTab a[href="#nageur"]').tab('show');
				};
				$scope.hasMineur = function() {
					var gotMineur = false;
					$scope.dossiers.each(function() {
						adherentAge = new Date(this.dossier.naissance);
						var annee = (new Date()).getFullYear() - adherentAge.getFullYear();
						if(annee < 18) {
							gotMineur = true;
						}
					});
					return gotMineur;
				};
				
				$scope.isUniqueMajeur = function() {
					var result = false;
					if($scope.dossiers.length == 1) {
						adherentAge = new Date($scope.dossiers[0].dossier.naissance);
						var annee = (new Date()).getFullYear() - adherentAge.getFullYear();
						result = annee >= 18;
						if(result) {
							if($scope.accordNotFilled) {
								$scope.principal.dossier.accordNomPrenom = $scope.dossiers[0].dossier.nom + " " + $scope.dossiers[0].dossier.prenom;
							}
						}
					} else {
						if($scope.hasMineur) {
							if($scope.accordNotFilled && $scope.principal.dossier.parent1Nom != null) {
								$scope.principal.dossier.accordNomPrenom = $scope.principal.dossier.parent1Nom + " " + $scope.principal.dossier.parent1Prenom;
							}
							result = true;
						} else {
							result = false;
						}
					}
					return result;
				};
				
				$scope.fillAccident = function() {
					if($scope.hasMineur) {
						if($scope.accidentNom1NotFilled && $scope.principal.dossier.parent1Nom != null) {
							$scope.principal.dossier.accidentNom1 = $scope.principal.dossier.parent1Nom;
							$scope.principal.dossier.accidentPrenom1 = $scope.principal.dossier.parent1Prenom;
							$scope.principal.dossier.accidentTelephone1 = $scope.principal.dossier.telephone;
						}
						if($scope.accidentNom2NotFilled && $scope.principal.dossier.parent2Nom != null) {
							$scope.principal.dossier.accidentNom2 = $scope.principal.dossier.parent2Nom;
							$scope.principal.dossier.accidentPrenom2 = $scope.principal.dossier.parent2Prenom;
							$scope.principal.dossier.accidentTelephone2 = $scope.principal.dossier.telephoneSecondaire;
						}
					}
					return true;
				};
				
				$scope.validateNageur = function(dossier) {
					$('#myTab a[href="#dossiers"]').tab('show');
				};
				$scope.validateDossiers = function() {
					$('#myTab a[href="#info"]').tab('show');
				};
				$scope.validateInfo = function() {
					data.dossiers = $scope.dossiers;
					var responsePromise = $http.post("/resources/inscription/price", data, {});
				    responsePromise.success(function(dataFromServer, status, headers, config) {
				    	data = dataFromServer.dossiers;
				    	$scope.dossiers = dataFromServer.dossiers.dossiers;
				    	$scope.price = dataFromServer.dossiers.price;
				    	$scope.dossiersCount = dataFromServer.dossiers.dossiers.length;
						$scope.principal = dataFromServer.dossiers.principal;
						$scope.priceDetail = dataFromServer.price;
						var dateInscription = new Date();
				        dateInscription.setDate(dateInscription.getDate() + 15);
				        $scope.validite = dateInscription;
				        $('#myTab a[href="#cotisation"]').tab('show');
				       });
				        responsePromise.error(function(data, status, headers, config) {
				          alert("Erreur");
				       });
				};
				$scope.validateInscription = function(dossier) {
					var formData = new FormData();
					var adherentCertificat = new Object();
					//Récuperer les certificats
					$("input[type=file]").each(function() {
						console.log(this.files[0])
						file = this.files[0];
						if(file != null) {
							adherentCertificat[file.name] = this.name;
							formData.append("file", file);
						}
					});
					console.log(adherentCertificat);
					data.dossiers = $scope.dossiers;
					data.certificats = adherentCertificat;
					formData.append("action", angular.toJson(data));
					var responsePromise = $http.post("/resources/inscription/inscrire", formData, {
			            transformRequest: angular.identity,
			            headers: {'Content-Type': undefined}
			        });
				    responsePromise.success(function(dataFromServer, status, headers, config) {
				    	$scope.links = dataFromServer.split(";");
				    	$("#validation-popup").modal();
				    	var responsePromise = $http.post("/adherentList.do?action=sendConfirmationNew&numero=" + data.principal.dossier.id, null, {});
					    responsePromise.success(function(dataFromServer, status, headers, config) {
					    	
					       });
					        responsePromise.error(function(data, status, headers, config) {
					          
					       });
				       });
				        responsePromise.error(function(data, status, headers, config) {
				          alert("Il n'y a pas le bulletin.");
				       });
				};
			}
		});
	};
	
}]);