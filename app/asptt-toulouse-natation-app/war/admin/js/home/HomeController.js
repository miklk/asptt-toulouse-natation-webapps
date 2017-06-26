/**
 * 
 */
var homeController = angular.module('HomeController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices', 'paramServices', 'inscriptionServices']);

homeController.controller('HomeController', ['$scope', '$location', 'InscriptionService', function($scope, $location, InscriptionService) {
	$scope.search = function() {
		$location.path("/dossiers/" + $scope.query);
	}
	
	$scope.forget = function() {
		InscriptionService.forget.query({email: $scope.forgetEmail}, function(data) {
			console.log(data);
			if(data.found) {
				alert("Vous allez recevoir votre mot de passe par e-mail");
			} else {
				alert("L'adresse e-mail " + $scope.formData.email + " n'est pas connue");
			}
		});
	}
	
}]);