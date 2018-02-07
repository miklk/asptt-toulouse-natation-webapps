/**
 * 
 */
var boutiqueController = angular.module('BoutiqueCtrl', ['ngRoute', 'boutiqueServices']);
boutiqueController.controller('BoutiqueCtrl', ['$rootScope', '$scope', 'BoutiqueService', 'InscriptionService', function($rootScope, $scope, BoutiqueService, InscriptionService) {
	$rootScope.isLoading = false;
	
	$scope.calendriers = new Array();
	BoutiqueService.products.query(function(data) {
		angular.forEach(data, function(product) {
		var orderProduct = {
				quantite : 0,
				product: product
		};
		$scope.calendriers.push(orderProduct);
		});
	});
	
	$scope.commande = {
			panier : new Array(),
			dossier : null,
			nom: '',
			prenom: '',
			email: ''
	};
	
	$scope.productQuantite = function(calendrier, addMinus) {
		if(addMinus) {//Premier ajout au panier
			calendrier.quantite = calendrier.quantite + 1;
		} else if(calendrier.quantite > 0) {
			calendrier.quantite = calendrier.quantite - 1;
		}
	}
	
	$scope.goToPanier = function() {
		$scope.panier = {
				count: 0,
				total: 0
		}
		var tarif = 0;
		angular.forEach($scope.calendriers, function(calendrier) {
			if(calendrier.quantite > 0) {
				var orderProduct = {
						id: calendrier.product.id,
						quantite: calendrier.quantite
				}
				$scope.commande.panier.push(orderProduct);
				
				for(i = 1; i <= orderProduct.quantite; i++) {
					$scope.panier.count = $scope.panier.count + 1;
					if($scope.panier.count < 2) {
						tarif = tarif + calendrier.product.price;
					} else if($scope.panier.count >= 2 && $scope.panier.count <= 4) {
						tarif = tarif + calendrier.product.price2;
					} else {
						tarif = tarif + calendrier.product.price3;
					}
				}
			}
		});
		$scope.panier.total = tarif;
		if($scope.commande.panier.length > 0) {
			$('#selection').collapse('hide');
			$('#panier').collapse('show');
		} else {
			$('#commandeErrorPanierVide').modal('show');
		}
	}
	
	$scope.goToIdentite = function() {
		$('#panier').collapse('hide');
		$('#identite').collapse('show');
	}
	
	$scope.commander = function() {
		if($scope.email && $scope.mdp) {
			InscriptionService.authenticate.query({email: $scope.email, mdp: $scope.mdp}, function (data) {
				if(data.inconnu) {
					alert("Vous n'êtes pas dans nos listing. Peut être qu'ils ne sont pas à jour. Veuillez valider la commande avec votre nom, prénom et adresse e-mail.");
				} else {
					$scope.commande.dossier = data.dossier.id;
					BoutiqueService.commander.query({}, $scope.commande, function(data) {
						$('#commandeConfirm').modal('show');
					});
				}
			});
		} else if($scope.commande.email) {
			BoutiqueService.commander.query({}, $scope.commande, function(data) {
				$('#commandeConfirm').modal('show');
			});
		} else {
			$('#commandeErrorEmail').modal('show');
		}
	}
	
}]);