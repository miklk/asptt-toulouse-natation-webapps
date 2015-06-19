/**
 * 
 */
var dossierNageurController = angular.module('DossierNageurController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierNageurController.controller('DossierNageurController', ['$http', '$scope', '$location', '$filter', '$routeParams', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, $routeParams, DossierService, GroupeService) {
	$scope.cspList = ['Scolaire', 'Agriculteurs exploitants', 'Artisans, commerçants et chefs d\'entreprise', 'Cadres et professions intellectuelles supérieures','Professions Intermédiaires', 'Employés', 'Ouvriers', 'Retraités', 'Sans activité professionnelle'];
	
	DossierService.findOne.query({'dossier': $routeParams.dossierId}, function(data) {
		$scope.dossier = data;
		
		//Handle input date
		angular.forEach($scope.dossier.nageurs, function(nageur) {
				nageur.naissance = new Date(nageur.naissance);
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
				montant+=nageur.tarif;
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
}]);