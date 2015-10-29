/**
 * 
 */
var documentationController = angular.module('DocumentationController', ['ngRoute', 'documentServices']);

documentationController.controller('DocumentationController', ['$http', '$scope', '$location', 'DocumentService', function($http, $scope, $location, DocumentService) {
	$scope.libelles = ['Documentation'];
	DocumentService.byLibelles.query({libelles: $scope.libelles}, function (data) {
		$scope.results = data.documents;
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