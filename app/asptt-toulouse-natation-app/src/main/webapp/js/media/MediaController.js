/**
 * 
 */
var mediaController = angular.module('MediaCtrl', ['ngRoute', 'mediaServices']);
mediaController.controller('MediaCtrl', ['$rootScope', '$scope', 'MediaService', function($rootScope, $scope, MediaService) {
	$rootScope.isLoading = true;
	MediaService.photos.query({}, function(data) {
		var maxPerLine = 4;
		$scope.photos = new Array();
		var nbLignes = data.albums.length / maxPerLine;
		var debut = 0;
		for(var i = 0 ; i < nbLignes; i++) {
			$scope.photos.push(data.albums.slice(debut, debut + maxPerLine));
			debut = debut + maxPerLine;
		}
		if(data.albums.length % maxPerLine != 0) {
			$scope.photos.push(data.albums.slice(debut));
		}
		$scope.nblignes = nbLignes;
		console.log($scope.nblignes);
		console.log($scope.photos);
		$rootScope.isLoading = false;
	});
}]);