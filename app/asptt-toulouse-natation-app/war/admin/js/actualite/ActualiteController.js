/**
 * 
 */
var actualiteController = angular.module('ActualiteController', ['ngRoute', 'actualiteServices']);
actualiteController.controller('ActualiteController', ['$rootScope', '$scope', '$http', '$location', '$anchorScroll', 'ActualiteService', function($rootScope, $scope, $http, $location, $anchorScroll, ActualiteService) {
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
		var formData = new FormData();
		var file = null;
		if(files == null) {
			var elt = document.getElementById('fileUploadInput');
			file = elt.files[0]
		} else {
			file = files[0];
		}
		if(file != null) {
			formData.append("file", $scope.fichier);
		}
		formData.append("data", angular.toJson($scope.currentActualite));
		$http.post("/resources/actualites", formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).
        success(function (data, status, headers, config) {
        	alert("Publié avec succès.");
			init();
			load();
        }).
        error(function (data, status, headers, config) {
			window.alert("Erreur lors dde la publication de l'actualité.");
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