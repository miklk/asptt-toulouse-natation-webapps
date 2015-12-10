/**
 * 
 */
var boutiqueController = angular.module('BoutiqueCtrl', ['ngRoute', 'boutiqueServices']);
boutiqueController.controller('BoutiqueCtrl', ['$rootScope', '$scope', 'BoutiqueService', function($rootScope, $scope, BoutiqueService) {
	$rootScope.isLoading = false;
	$scope.calendriers = [
	                      {id: 1, title:'Cal1', description: 'Jeudi soir', quantite: 0, prices: [5, 4, 3], image: 'img/logo.png'},
	                      {id: 2, title:'Cal1', description: 'Jeudi soir', quantite: 0, prices: [5, 4, 3], image: 'img/logo.png'},
	                      ];
	
	$scope.panier = new Array();
	
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
				$scope.panier.push(calendrier);
			}
		});
		$('#selection').collapse('hide');
		$('#identite').collapse('show');
	}
	
	$scope.commander = function() {
		$scope.commande = {
				panier : $scope.panier,
				dossier : 0,
				nom: '',
				prenom: '',
		};
		if($scope.email && $scope.mdp) {
			InscriptionService.authenticate.query({email: $scope.email, mdp: $scope.mdp}, function (data) {
				if(data.inconnu) {
					alert("Vous n'êtes pas dans nos listing. Peut être qu'ils ne sont pas à jour. Veuillez valider la commande avec votre nom et prénom.");
				} else {
					$scope.commande.dossier = data.dossier.id;
					BoutiqueService.commander.query({}, $scope.commande, function(data) {
						alert("Ok");
					});
				}
			});
		} else if($scope.nom) {
			$scope.commande.nom = $scope.nom;
			$scope.commande.prenom = $scope.prenom;
			BoutiqueService.commander.query({}, $scope.commande, function(data) {
				alert("Ok");
			});
		}
	}
	
}]);