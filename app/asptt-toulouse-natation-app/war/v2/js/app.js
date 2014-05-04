var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute', 'pagesControllers']);
aspttNatTlsApp.config(['$routeProvider', 
                       function($routeProvider) {
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
			redirectTo: '/'
		});
}]);