/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['adherentsServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', 'Adherent', function($scope, Adherent) {
	$scope.adherents = Adherent.get();
}]);


var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute','loadingAppServices', 'pageServices', 'loadingAlbumServices', 'loadingPhotosServices']);
aspttNatTlsApp.controller('LoadingAppCtrl', ['$scope', 'LoadingApp', '$sce', function($scope, LoadingApp, $sce) {
	$scope.loadingApp = LoadingApp.get();
	$scope.showActualite = function(actualite) {
    	$("#myCarousel").hide();
    	$("#actualites").show();
    	$scope.actualiteTitle = actualite.title;
    	$scope.actualiteHtml = $sce.trustAsHtml(actualite.content);
    };
    $scope.hideActualite = function() {
    	$("#myCarousel").show();
    	$("#actualites").hide();
    	$scope.actualiteTitle = "";
    	$scope.actualiteHtml = "";
    	
    };
}]);

aspttNatTlsApp.controller('PageCtrl', ['$scope', 'PageService', '$routeParams', '$sce', function($scope, PageService, $routeParams, $sce) {
	PageService.get({pageId: $routeParams.pageId}, function(data) {
		var pageUi = angular.fromJson(data);
		$scope.pageHtml = $sce.trustAsHtml(pageUi.content);
	});
}]);

aspttNatTlsApp.controller('LoadingAlbumCtrl', ['$scope', 'LoadingAlbumService', 'LoadingPhotosService', function($scope, LoadingAlbumService, LoadingPhotosService) {
	$scope.albums = LoadingAlbumService.get();
	$scope.getPhotos = function(album) {
		$scope.photos = LoadingPhotosService.get({albumId: album.albumId});
	};
}]);

aspttNatTlsApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/home.html',
			controller: 'LoadingAppCtrl'
		}).
		when('/page/Partenaires', {
			templateUrl: 'views/partenaires.html'
		}).
		when('/page/:pageId', {
			templateUrl: 'views/page.html',
			controller: 'PageCtrl'
		}).
		when('/error', {
			templateUrl: 'views/error.html'
		}).
		otherwise({
			redirectTo: '/error'
		});
}]);