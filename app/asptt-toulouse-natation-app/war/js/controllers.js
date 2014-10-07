/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['adherentsServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', 'Adherent', function($scope, Adherent) {
	$scope.adherents = Adherent.get();
}]);


var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute','loadingAppServices', 'pageServices', 'loadingAlbumServices', 'inscriptionServices', 'slotServices', 'groupeServices', 'inscriptionNouveauServices', 'removeAdherentServices', 'authenticationServices']);
aspttNatTlsApp.controller('LoadingAppCtrl', ['$scope', 'LoadingApp', '$sce', function($scope, LoadingApp, $sce) {
	LoadingApp.get({}, function(data) {
		$scope.loadingApp = data;
		$scope.showActualite = function(actualite) {
	    	$("#actualites").show();
	    	$scope.actualiteTitle = actualite.title;
	    	$scope.actualiteHtml = $sce.trustAsHtml(actualite.content);
	    	$scope.actuDocuments = actualite.documentSet;
	    	var documentSize = actualite.documentSet.length;
			$scope.actuNbDocuments = documentSize;
	    	$scope.actuHasDocument = documentSize > 0;
	    	$('html, body').animate({  
	            scrollTop:$("#actualites").offset().top  
	        }, 'slow');
	    };
	    $scope.hideActualite = function() {
	    	$('html, body').animate({  
	            scrollTop:$("#carous_container").offset().top  
	        }, 'slow');
	    	$("#actualites").hide();
	    	$scope.actualiteTitle = "";
	    	$scope.actualiteHtml = "";
	    	$scope.actuDocuments = null;
	    	$scope.actuHasDocument = false;
			$scope.actuNbDocuments = 0;
	    };
	});
	$scope.scrollToTop = function() {
		$('html, body').animate({  
            scrollTop:$("#body_top").offset().top  
        }, 'slow');
	};
}]);

aspttNatTlsApp.controller('PageCtrl', ['$scope', 'PageService', '$routeParams', '$sce', function($scope, PageService, $routeParams, $sce) {
	PageService.get({pageId: $routeParams.pageId}, function(data) {
		var pageUi = angular.fromJson(data);
		$scope.pageHtml = $sce.trustAsHtml(pageUi.content);
		var documentSize = pageUi.documents.length;
		$scope.hasDocument = documentSize > 0;
		$scope.nbDocuments = documentSize;
		$scope.documents = pageUi.documents;
		$scope.displayPdf = function() {
			$scope.pdfUrl = $sce.trustAsResourceUrl("http://docs.google.com/viewer?url=http%3A%2F%2F1-dot-asptt-toulouse-natation.appspot.com%2FdownloadDocument%3FdocumentId%3D5241851221114880%26fileId%3D4961648628465664");
			$("#pdf-viewer").show();
			$('html, body').animate({  
	            scrollTop:$("#pdf-viewer").offset().top  
	        }, 'slow');	
		};
	});
}]);

aspttNatTlsApp.controller('LoadingAlbumCtrl', ['$scope', 'LoadingAlbumService', function($scope, LoadingAlbumService) {
	$scope.albums = LoadingAlbumService.get();
	$scope.getPhotos = function(album) {
		//Build slider
		$("#diapo").fadeIn();
		$('html, body').animate({  
            scrollTop:$("#diapo").offset().top  
        }, 'slow');
		$("#slider3").empty();
		$("#slider3-pager").empty();
		$scope.albumTitle = album.intitule;
		$.each(album.photos, function() {
			var img = $("<img src=\"\" />");
			$(img).attr("src", this);
			var liElt = $("<li></li>");
			liElt.append(img);
			$("#slider3").append(liElt);
			
			var imgThumbl = $("<img src=\"\" />");
			$(imgThumbl).attr("src", this);
			$(imgThumbl).css("max-width", "50px");
			
			var aThumbl = $("<a href=\"#\"></a>");
			aThumbl.append(imgThumbl);
			var liThumbl = $("<li></li>");
			liThumbl.append(aThumbl);
			$("#slider3-pager").append(liThumbl);
		});
		$("#slider3").responsiveSlides({
	        manualControls: '#slider3-pager',
	        maxwidth: 540,
	        speed: 300
	      });
	};
	$scope.hidePhotos = function() {
		$('html, body').animate({  
            scrollTop:$("#albums").offset().top  
        }, 'slow');
		$("#diapo").fadeOut();
		$scope.albumTitle = "";
	};
}]);

aspttNatTlsApp.controller('InscriptionCtrl', ['$http', '$scope', 'InscriptionService', 'SlotService', 'GroupeService', 'InscriptionNouveauService', 'RemoveAdherentService', '$routeParams', '$sce', function($http, $scope, InscriptionService, SlotService, GroupeService, InscriptionNouveauService, RemoveAdherentService, $routeParams, $sce) {
	$scope.formData = {
			email: "",
			mdp: "",
			nouveau: ""
	};
	
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
					$('#myTab a[href="#nageur"]').tab('show');
				};
				$scope.validateNageur = function(dossier) {
					$('#myTab a[href="#dossiers"]').tab('show');
				};
				$scope.validateDossiers = function() {
					$('#myTab a[href="#info"]').tab('show');
				};
				$scope.validateInfo = function() {
					data.dossiers = $scope.dossiers;
					var responsePromise = $http.post("/resources/price", data, {});
				    responsePromise.success(function(dataFromServer, status, headers, config) {
				    	data = dataFromServer.dossiers;
				    	$scope.dossiers = dataFromServer.dossiers.dossiers;
				    	$scope.price = dataFromServer.dossiers.price;
				    	$scope.dossiersCount = dataFromServer.dossiers.dossiers.length;
						$scope.principal = dataFromServer.dossiers.principal;
						$scope.priceDetail = dataFromServer.price;
				       });
				        responsePromise.error(function(data, status, headers, config) {
				          alert("Erreur");
				       });
					$('#myTab a[href="#cotisation"]').tab('show');
				};
				$scope.validateInscription = function(dossier) {
					data.dossiers = $scope.dossiers;
					var responsePromise = $http.post("/resources/inscrire", data, {});
				    responsePromise.success(function(dataFromServer, status, headers, config) {
				    	$scope.links = dataFromServer.split(";");
				       });
				        responsePromise.error(function(data, status, headers, config) {
				          alert("Il n'y a pas le bulletin.");
				       });
				        
			        var responsePromise = $http.post("/adherentList.do?action=sendConfirmationNew&numero=" + data.principal.dossier.id, null, {});
				    responsePromise.success(function(dataFromServer, status, headers, config) {
				    	
				       });
				        responsePromise.error(function(data, status, headers, config) {
				          
				       });
				};
			}
		});
	};
	
}]);

aspttNatTlsApp.controller('AuthenticationCtrl', ['$scope', '$location', 'AuthenticationService', '$sce', function($scope, $location, AuthenticationService, $sce) {
	$scope.authenticate = function(provider) {
	 	AuthenticationService.get({openIdService: provider}, function(data) {
	 		console.log(data.providerUrl);
			$location.path(data.providerUrl);
		});
	};
}]);

aspttNatTlsApp.config(['$routeProvider', '$sceDelegateProvider', function ($routeProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/home.html',
			controller: 'LoadingAppCtrl'
		}).
		when('/page/Inscription', {
			templateUrl: 'views/inscription.html',
			controller: 'PageCtrl'
		}).
		when('/page/login', {
			templateUrl: 'views/login.html',
			controller: 'AuthenticationCtrl'
		}).
		when('/page/user-index', {
			templateUrl: 'views/user-index.html'
		}).
		when('/page/logout', {
			templateUrl: 'views/logout.html'
		}).
		when('/page/:pageId', {
			templateUrl: 'views/page.html',
			controller: 'PageCtrl'
		}).
		when('/partenaires', {
			templateUrl: 'views/partenaires.html'
		}).
		when('/mentions', {
			templateUrl: 'views/mentions-legales.html'
		}).
		when('/error', {
			templateUrl: 'views/error.html'
		}).
		otherwise({
			redirectTo: '/error'
		});
	$sceDelegateProvider.resourceUrlWhitelist([
       'self',
       'http://docs.google.com/viewer?url=*'
     ]);

}]);