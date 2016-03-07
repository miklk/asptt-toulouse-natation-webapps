/**
 * 
 */
var actualiteController = angular.module('ActualiteController', ['ngRoute', 'actualiteServices']);
actualiteController.controller('ActualiteController', ['$rootScope', '$scope', 'ActualiteService', function($rootScope, $scope, ActualiteService) {
	$rootScope.isLoading = true;
	$scope.showFull = true;
	$scope.currentActualite = {
			title: '',
			image: '',
			content : '',
			facebook: false,
			draft: false,
			beginAsString: new Date(),
			endAsString: new Date(2016, 7, 31, 23, 59, 59, 0),
			user: $rootScope.aspttTokenInfo.prenom + ' ' + $rootScope.aspttTokenInfo.nom.substring(0, 1) + '.',
	};
	
	ActualiteService.actualites.query(function (data) {
		$scope.actualites = data;
		$rootScope.isLoading = false;
	});
	
	$scope.edit = function(actualite) {
		if(actualite != null) {
			$scope.currentActualite = actualite;	
		} else {
			$scope.currentActualite = new Object();
			$scope.currentActualite.begin = new Date().getTime();
			$scope.currentActualite.end = new Date().getTime();
		}
		
	}
	
	$scope.publish = function() {
		ActualiteService.publish.query({}, $scope.currentActualite, function(data) {
			alert("Publié avec succès.");
			$scope.actualites = ActualiteService.actualites.query(function (data) {
				$scope.actualites = data;
				$rootScope.isLoading = false;
			});
		});
	}
}]);