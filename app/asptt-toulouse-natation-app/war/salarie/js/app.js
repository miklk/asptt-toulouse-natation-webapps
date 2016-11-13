/**
 *
 */
var adminApp = angular.module('salarieApp', ['ngCookies', 'ngRoute', 'angular-spinkit', 'SalarieService', 'SalarieController', 'SaisieHeureController', 'saisieHeureServices']);
adminApp.config(['$routeProvider', '$httpProvider', '$sceDelegateProvider', function ($routeProvider, $httpProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/', {
			templateUrl : 'views/home.html'
		}).
		when('/saisie', {
			templateUrl : 'views/heure/saisie.html'
		}).
		when('/error', {
			templateUrl: 'views/error.html'
		}).
		when('/suivi', {
			templateUrl : 'views/suivi-heure.html'
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

adminApp.filter("toDate", function() {
    return function(x) {
        return new Date(x);
    };
});
