/**
 * 
 */
var inscriptionStageController = angular.module('InscriptionStageCtrl', ['ngRoute', 'inscriptionStageServices']);
inscriptionStageController.controller('InscriptionStageCtrl', ['$rootScope', '$scope', 'InscriptionStageService', function($rootScope, $scope, InscriptionStageService) {
	$rootScope.isLoading = false;
	$scope.sessions = [{"id": 1, "description": "Mercredi 06 au Vendredi 08 juillet 2016 (3 jours)", "price": 150},
	                   {"id": 2, "description": "Lundi 11 au Mercredi 13 juillet 2016 (3 jours)", "price": 150},
	                   {"id": 3, "description": "Lundi 22 au Vendredi 26 juillet 2016 (5 jours)", "price": 250},
	                   ];
	$scope.dossier = new Object();
	
	var dateInscription = new Date();
    dateInscription.setDate(dateInscription.getDate() + 8);
    $scope.validite = dateInscription;
	
	$scope.validateActivite = function() {
		$('#myTab a[href="#nageur"]').tab('show');
	}
	$scope.validateNageur = function() {
		if($scope.dossier.adherent) {
			$('#myTab a[href="#cotisation"]').tab('show');
		} else {
			$('#myTab a[href="#info"]').tab('show');
		}
	};
	$scope.validateInfo = function() {
        $('#myTab a[href="#cotisation"]').tab('show');
	};
	
	$scope.validateInscription = function(dossier) {
		InscriptionStageService.inscrire.query({}, $scope.dossier, function() {
			$("#validation-popup").modal();
		});
	};
}]);