/**
 * 
 */
var documentController = angular.module('DocumentController', ['ngRoute', 'documentServices']);

documentController.controller('DocumentListController', ['$scope', '$location', 'DocumentService', 'LibelleGroupeService', function($scope, $location, DocumentService, LibelleGroupeService) {
	$scope.formData = {
			search: ""
	};
	
	LibelleGroupeService.groupes.query({}, function(data) {
		$scope.groupes = data.groupes;
	});
	
	$scope.documentFindResult = null;
	
	$scope.documentFindAction = angular.copy($scope.initForm);
	
	$scope.search = function() {
		DocumentService.documents.query({}, function (data) {
			$scope.documentFindResult = data;
		});
	}	
	
	$scope.loadDocument = function(id) {
		DocumentService.document.get({document: id}, function (data) {
			console.log(data);
			$scope.document = data;
		});
	}
}]);

documentController.controller('DocumentController', ['$scope', '$location', '$routeParams', 'DocumentService', 'LibelleGroupeService', function($scope, $location, $routeParams, DocumentService, LibelleGroupeService) {
		DocumentService.document.get({document: $routeParams.documentId}, function (data) {
			console.log(data);
			$scope.document = data;
		});
}]);