/**
 * 
 */
var actualiteController = angular.module('ActualiteController', ['ngRoute', 'actualiteServices']);
actualiteController.controller('ActualiteController', ['$rootScope', '$scope', '$location', '$anchorScroll', 'ActualiteService', function($rootScope, $scope, $location, $anchorScroll, ActualiteService) {
	$rootScope.isLoading = true;
	$scope.showFull = true;
	
	var init = function() {
		$scope.currentActualite = {
				id: null,
				title: '',
				image: '',
				content : '',
				facebook: false,
				draft: false,
				beginAsString: new Date(),
				endAsString: new Date(2017, 7, 31, 23, 59, 59, 0),
				user: $rootScope.aspttTokenInfo.prenom + ' ' + $rootScope.aspttTokenInfo.nom.substring(0, 1) + '.',
		};
	}
	init();
	
	var load = function() {
		ActualiteService.actualites.query(function (data) {
			$scope.actualites = data;
			$rootScope.isLoading = false;
		});
	}
	load();
	
	$scope.edit = function(actualite) {
		$scope.currentActualite = {
				id: actualite.id,
				title: actualite.title,
				image: actualite.imageUrl,
				content : actualite.content.value,
				facebook: false,
				draft: false,
				beginAsString: new Date(actualite.creationDate),
				endAsString: new Date(actualite.expiration),
				user: $rootScope.aspttTokenInfo.prenom + ' ' + $rootScope.aspttTokenInfo.nom.substring(0, 1) + '.',
		};
		$location.hash('creation');
		$anchorScroll();
	}
	
	$scope.publish = function() {
		ActualiteService.publish.query({}, $scope.currentActualite, function(data) {
			alert("Publié avec succès.");
			init();
			load();
		});
	}
	
	$scope.remove = function(actualite) {
		if(window.confirm("Voulez-vous supprimer cette publication ?")) {
			ActualiteService.remove.query({actualite: actualite.id}, function(data) {
				load();
			});
		}
	}
	
	$scope.clear = function() {
		init();
	}
}]);