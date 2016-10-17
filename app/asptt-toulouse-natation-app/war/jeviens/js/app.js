/**
 *
 */
var adminApp = angular.module('jeviensApp', ['ngCookies', 'ngRoute', 'JeViensService', 'JeViensController', 'SuiviNageurController', 'suiviNageurServices']);
adminApp.config(['$routeProvider', '$httpProvider', '$sceDelegateProvider', function ($routeProvider, $httpProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/error', {
			templateUrl: 'views/error.html'
		}).
		when('/suivi', {
			templateUrl : 'views/suivi-nageur-week-prevu.html'
		}).
		when('/:token', {
			templateUrl: 'views/home.html'
		}).
		otherwise({
			redirectTo: '/error'
		});
	$sceDelegateProvider.resourceUrlWhitelist([
       'self',
       'http://docs.google.com/viewer?url=*'
     ]);
}]);

adminApp.directive('ngLoadingIndicator', function($rootScope) {
	return {
		restrict : 'E',
		template: '<rotating-plane-spinner ng-show="isLoading"></rotating-plane-spinner>',
		link: function(scope, elem, attrs) {
			$rootScope.isLoading = true;
		      scope.isRouteLoading = false;

		      $rootScope.$on('$routeChangeStart', function() {
		        scope.isRouteLoading = true;
		        $rootScope.isLoading = false;
		      });
		      $rootScope.$on('$routeChangeSuccess', function() {
		        scope.isRouteLoading = false;
		        $rootScope.isLoading = false;
		      });
		    }
	}
});

