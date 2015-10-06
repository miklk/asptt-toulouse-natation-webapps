/**
 * 
 */
var adminApp = angular.module('adminApp', ['ngCookies', 'ngRoute', 'angular-spinkit', 
                                           'adherentsServices','adherentsStatServices', 
                                           'groupeServices', 
                                           'slotServices', 'userServices', 
                                           'dossierServices', 'UserController', 
                                           'AdherentEmailCtrl', 'AdherentsStatCtrl', 
                                           'DocumentController', 'LibelleController', 
                                           'SuiviNageurController', 'GroupeController', 
                                           'DossierController', 'DossierNageurController', 
                                           'CreneauStatController', 'creneauStatServices', 
                                           'ExtractionController','DashboardController', 
                                           'EnfController', 'enfServices', 
                                           'authorizationService', 'LoginController', 
                                           'loginServices']);

adminApp.config(['$routeProvider', '$httpProvider', '$sceDelegateProvider', function ($routeProvider, $httpProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/dashboard/dashboard.html'
		}).
		when('/home', {
			templateUrl: 'views/index.html'
		}).
		when('/dashboard', {
			templateUrl: 'views/dashboard/dashboard.html'
		}).
		when('/adherents-stat', {
			templateUrl: 'views/adherent/adherents-stat.html'
		}).
		when('/adherent-email', {
			templateUrl: 'views/adherent/adherent-email.html',
			controller: 'AdherentEmailCtrl'
		}).
		when('/enf', {
			templateUrl: 'views/enf/enf.html'
		}).
		when('/users', {
			templateUrl: 'views/user/users.html',
			controller: 'UserController'
		}).
		when('/users/creer', {
			templateUrl: 'views/user/user-creer.html',
			controller: 'UserController'
		}).
		when('/documents', {
			templateUrl: 'views/document/documents.html'
		}).
		when('/libelles', {
			templateUrl: 'views/document/libelle/libelles.html'
		}).
		when('/suivi-nageur-day', {
			templateUrl: 'views/suivi/suivi-nageur-day.html'
		}).
		when('/suivi-nageur-week', {
			templateUrl: 'views/suivi/suivi-nageur-week.html'
		}).
		when('/suivi-nageur-month', {
			templateUrl: 'views/suivi/suivi-nageur-month.html'
		}).
		when('/suivi-nageur-year', {
			templateUrl: 'views/suivi/suivi-nageur-year.html'
		}).
		when('/groupes', {
			templateUrl: 'views/groupe/groupes.html',
			access: 'ACCESS_GROUPES'
		}).
		when('/dossiers', {
			templateUrl: 'views/dossier/dossiers.html',
			access: 'ACCESS_DOSSIERS'
		}).
		when('/dossier/:dossierId', {
			templateUrl: 'views/dossier/dossier.html',
			controller: 'DossierNageurController',
			access: 'ACCESS_DOSSIERS'
		}).
		when('/extraction', {
			templateUrl: 'views/dossier/extraction/extraction.html',
			access: 'ACCESS_DOSSIERS'
		}).
		when('/remplissage', {
			templateUrl: 'views/dossier/stat/remplissage.html',
			access: 'ACCESS_REMPLISSAGE'
		}).
		when('/remplissage-detail', {
			templateUrl: 'views/dossier/stat/remplissage-piscine.html',
			access: 'ACCESS_REMPLISSAGE'
		}).
		when('/page/login', {
			templateUrl: 'views/login.html'
		}).
		when('/error', {
			templateUrl: 'views/error.html'
		}).
		when('/unauthorized', {
			templateUrl: 'views/no-rights.html'
		}).
		otherwise({
			redirectTo: '/error'
		});
	$sceDelegateProvider.resourceUrlWhitelist([
       'self',
       'http://docs.google.com/viewer?url=*'
     ]);
	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	$httpProvider.defaults.withCredentials = true;
    //$httpProvider.interceptors.push('XSRFInterceptor');

}]);

adminApp.run(function($rootScope, $location, AuthorizationService) {
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if (!$rootScope.authenticated) {
            $location.url("/page/login");
        } else if(next && next.$$route && next.$$route.access && !AuthorizationService.hasAccess(next.$$route.access)){
        	$location.url("/unauthorized");
        }
    });
});

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

adminApp.directive("showWhenConnected", function (AuthorizationService) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var showIfConnected = function() {
                if(AuthorizationService.isConnected() && AuthorizationService.hasAccess(attrs.showWhenConnected)) {
                    $(element).show();
                } else {
                    $(element).hide();
                }
            };
 
            showIfConnected();
            scope.$on("connectionStateChanged", showIfConnected);
        }
    };
});

/**adminApp.factory('XSRFInterceptor', function ($cookies, $log) {

    var XSRFInterceptor = {

      request: function(config) {

        var token = $cookies.get('XSRF-TOKEN');

        if (token) {
          config.headers['X-XSRF-TOKEN'] = token;
          $log.info("X-XSRF-TOKEN: " + token);
        }

        return config;
      }
    };
    return XSRFInterceptor;
  });**/