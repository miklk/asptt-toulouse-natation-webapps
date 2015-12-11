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
	
	$scope.goToIdentite = function() {
		angular.forEach($scope.calendriers, function(calendrier) {
			if(calendrier.quantite > 0) {
				var orderProduct = {
						id: calendrier.product.id,
						quantite: calendrier.quantite
				}
				$scope.commande.panier.push(orderProduct);
			}
		});
		
		if($scope.commande.panier.length > 0) {
			$('#selection').collapse('hide');
			$('#identite').collapse('show');
		} else {
			$('#commandeErrorPanierVide').modal('show');
		}
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