/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['adherentsServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', 'Adherent', function($scope, Adherent) {
	$scope.adherents = Adherent.get();
}]);


var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute','loadingAppServices', 'pageServices']);
aspttNatTlsApp.controller('LoadingAppCtrl', ['$scope', 'LoadingApp', function($scope, LoadingApp) {
	$scope.loadingApp = LoadingApp.get();
}]);

aspttNatTlsApp.controller('PageCtrl', ['$scope', 'PageService', '$routeParams', function($scope, PageService, $routeParams) {
	$scope.pageId = PageService.get({pageId: $routeParams.pageId});
}]);

aspttNatTlsApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/home.html',
			controller: 'LoadingAppCtrl'
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