/**
 * 
 */
var dossierController = angular.module('DossierController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierController.controller('DossierController', ['$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, DossierService, GroupeService) {
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	}); 
	
	$scope.search = function() {
		DossierService.list.query({query: $scope.query}, function(data) {
			$scope.dossiers = data;
		});
	},
	
	$scope.changerGroupe = function(index, dossier) {
		$scope.dossier = dossier;
		$scope.currentIndex = index;
		$('#groupe-popup').modal();
	}
	
	$scope.updateGroupe = function() {
		if(confirm("Souhaitez-vous changer le groupe ? (les créneaux vont être libérés)")) {
			$http.post("/resources/dossiers/changerGroupe", $scope.dossier.nageur, {})
		       .success(function(dataFromServer, status, headers, config) {
		    	  $scope.dossiers.splice($scope.currentIndex, 1, dataFromServer);
		    	  $('#groupe-popup').modal('hide');
		       })
		        .error(function(data, status, headers, config) {
		          alert("Erreur");
		       });
		} else {
			$('#groupe-popup').modal('hide');	
		}
	}
}]);