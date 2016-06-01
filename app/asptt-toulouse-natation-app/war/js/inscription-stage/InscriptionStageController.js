/**
 * 
 */
var inscriptionStageController = angular.module('InscriptionStageCtrl', ['ngRoute', 'inscriptionStageServices']);
inscriptionStageController.controller('InscriptionStageCtrl', ['$rootScope', '$scope', 'InscriptionStageService', function($rootScope, $scope, InscriptionStageService) {
	$rootScope.isLoading = false;
	$scope.dossier = new Object();
	
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
		var dateInscription = new Date();
        dateInscription.setDate(dateInscription.getDate() + 8);
        $scope.validite = dateInscription;
        $('#myTab a[href="#cotisation"]').tab('show');
	};
	
	$scope.validateInscription = function(dossier) {
	    	$("#validation-popup").modal();
	};
}]);