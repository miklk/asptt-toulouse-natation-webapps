/**
 * 
 */
var dossierNageurController = angular.module('DossierNageurController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierNageurController.controller('DossierNageurController', ['$http', '$scope', '$location', '$filter', '$routeParams', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, $routeParams, DossierService, GroupeService) {
	$scope.cspList = ['Scolaire', 'Agriculteurs exploitants', 'Artisans, commerçants et chefs d\'entreprise', 'Cadres et professions intellectuelles supérieures','Professions Intermédiaires', 'Employés', 'Ouvriers', 'Retraités', 'Sans activité professionnelle'];
	
	DossierService.findOne.query({'dossier': $routeParams.dossierId}, function(data) {
		$scope.dossier = data;
	});
	
	$scope.montantCalcule = function() {
		var montant = 0;
		angular.forEach($scope.dossier.nageurs, function(nageur) {
			montant+=nageur.tarif;
		});
		return montant;
	}
}]);