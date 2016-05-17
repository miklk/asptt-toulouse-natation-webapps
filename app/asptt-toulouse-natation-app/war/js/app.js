var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute','ngMessages', 'angular-spinkit', 'loadingAppServices', 'pageServices', 'inscriptionServices', 'slotServices', 'groupeServices', 'authenticationServices', 'actualiteServices', 'DocumentController', 'DocumentationController', 'mediaServices', 'MediaCtrl', 'InscriptionController', 'CalendarController', 'BoutiqueCtrl']);
aspttNatTlsApp.config(['$routeProvider', '$sceDelegateProvider', function ($routeProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/home.html',
			controller: 'LoadingAppCtrl'
		}).
		when('/page/Inscription', {
			templateUrl: 'views/inscription.html',
			controller: 'PageCtrl',
			resolve: { pageId: function() {return null;}}
		}).
		when('/page/actualites', {
			templateUrl: 'views/actualites.html',
			controller: 'ActualiteCtrl'
		}).
		when('/page/competitions-actualites', {
			templateUrl: 'views/competitions-actualites.html',
			controller: 'CompetitionActualiteCtrl'
		}).
		when('/page/coin-du-nageur', {
			templateUrl: 'views/coin-du-nageur.html',
			controller: 'DocumentController'
		}).
		when('/page/documentation', {
			templateUrl: 'views/documentation.html',
			controller: 'DocumentationController'
		}).
		when('/page/section-sportive', {
			templateUrl: 'views/section-sportive.html',
			controller: 'PageCtrl',
			resolve: { pageId: function() {return "section-sportive";}}
		}).
		/**when('/page/vacances', {
			templateUrl: 'views/vacances.html'
		}).**/
		when('/page/login', {
			templateUrl: 'views/login.html',
			controller: 'AuthenticationCtrl'
		}).
		when('/page/no-rights', {
			templateUrl: 'views/no-rights.html'
		}).
		when('/page/unknow-user', {
			templateUrl: 'views/unknow-user.html'
		}).
		when('/page/user-index', {
			templateUrl: 'views/user-index.html'
		}).
		when('/page/logout', {
			templateUrl: 'views/logout.html'
		}).
		when('/page/calendriers', {
			templateUrl: 'views/calendrier.html',
			controller: 'CalendrierCtrl'
		}).
		when('/page/photos', {
			templateUrl: 'views/photos.html',
			controller: 'MediaCtrl',
			resolve: {albums: function() {return null;}}
		}).
		when('/page/vente-calendriers', {
			templateUrl: 'views/vente-calendriers.html',
			controller: 'BoutiqueCtrl'
		}).
		when('/page/:pageId', {
			templateUrl: 'views/page.html',
			controller: 'PageCtrl',
			resolve: { pageId: function() {return null;}}
		}).
		when('/partenaires', {
			templateUrl: 'views/partenaires.html'
		}).
		when('/mentions', {
			templateUrl: 'views/mentions-legales.html'
		}).
		when('/error', {
			templateUrl: 'views/error.html'
		}).
		otherwise({
			redirectTo: '/error'
		});
	$sceDelegateProvider.resourceUrlWhitelist([
       'self',
       'http://docs.google.com/viewer?url=*'
     ]);

}]);


aspttNatTlsApp.directive('ngLoadingIndicator', function($rootScope) {
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
aspttNatTlsApp.directive('routeLoadingIndicator', function($rootScope) {
	  return {
	    restrict: 'E',
	    template: "<div ng-show='isRouteLoading' class='loading-indicator'>" +
	    "<div class='loading-indicator-body'>" +
	    "<h3 class='loading-title'>Loading...</h3>" +
	    "<div class='spinner'><rotating-plane-spinner></rotating-plane-spinner></div>" +
	    "</div>" +
	    "</div>",
	    replace: true,
	    link: function(scope, elem, attrs) {
	      scope.isRouteLoading = false;

	      $rootScope.$on('$routeChangeStart', function() {
	        scope.isRouteLoading = true;
	      });
	      $rootScope.$on('$routeChangeSuccess', function() {
	        scope.isRouteLoading = false;
	      });
	    }
	  };
	});