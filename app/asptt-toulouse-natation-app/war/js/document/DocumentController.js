/**
 * 
 */
var documentController = angular.module('DocumentController', ['ngRoute', 'documentServices']);

documentController.controller('DocumentController', ['$http', '$scope', '$location', 'DocumentService', function($http, $scope, $location, DocumentService) {
	$scope.libelles = ['Coin du nageur'];
	DocumentService.byLibelles.query({libelles: $scope.libelles}, function (data) {
		$scope.results = data.documents;
		console.log($scope.results);
	});
	
	$scope.download = function(document) {
		alert("Download file " + document.title);
	};
	
	$scope.scrollToTop = function(identifier) {
		var id = "#" + identifier;
		$('html, body').animate({  
            scrollTop:$(id).offset().top  
        }, 'slow');
	};
}]);