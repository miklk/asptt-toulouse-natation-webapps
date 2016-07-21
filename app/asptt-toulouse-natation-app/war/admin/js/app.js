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
                                           'loginServices',
                                           'AdminController', 'BoutiqueCtrl', 'OrderController', 'ActualiteController',
                                           'PageEditionController',
                                           'GroupeEffectifController', 'RecordController', 'DossierStageController', 'dossierStageServices', 'paramServices']);

adminApp.config(['$routeProvider', '$httpProvider', '$sceDelegateProvider', function ($routeProvider, $httpProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/home.html'
		}).
		when('/home', {
			templateUrl: 'views/home.html'
		}).
		when('/dashboard', {
			templateUrl: 'views/dashboard/dashboard.html',
			access: 'ACCESS_DOSSIERS'
		}).
		when('/adherents-stat', {
			templateUrl: 'views/adherent/adherents-stat.html',
			access: 'ACCESS_DOSSIERS'
		}).
		when('/adherent-email', {
			templateUrl: 'views/adherent/adherent-email.html',
			controller: 'AdherentEmailCtrl',
			access: 'ACCESS_DOSSIERS_EMAIL'
		}).
		when('/enf', {
			templateUrl: 'views/enf/enf.html',
			access: 'ACCESS_ENF'
		}).
		when('/users', {
			templateUrl: 'views/user/users.html',
			controller: 'UserController',
			access: 'ACCESS_USERS'
		}).
		when('/users/creer', {
			templateUrl: 'views/user/user-creer.html',
			controller: 'UserController',
			access: 'ACCESS_USERS'
		}).
		when('/documents', {
			templateUrl: 'views/document/documents.html',
			access: 'ACCESS_DOCUMENTS'
		}).
		when('/libelles', {
			templateUrl: 'views/document/libelle/libelles.html',
			access: 'ACCESS_DOCUMENTS'
		}).
		when('/suivi-nageur-day', {
			templateUrl: 'views/suivi/suivi-nageur-day.html',
			access: 'ACCESS_SUIVI_NAGEURS'
		}).
		when('/suivi-nageur-week', {
			templateUrl: 'views/suivi/suivi-nageur-week.html',
			access: 'ACCESS_SUIVI_NAGEURS'
		}).
		when('/suivi-nageur-month', {
			templateUrl: 'views/suivi/suivi-nageur-month.html',
			access: 'ACCESS_SUIVI_NAGEURS'
		}).
		when('/suivi-nageur-year', {
			templateUrl: 'views/suivi/suivi-nageur-year.html',
			access: 'ACCESS_SUIVI_NAGEURS'
		}).
		when('/suivi-nageur-periode', {
			templateUrl: 'views/suivi/suivi-nageur-periode.html',
			access: 'ACCESS_SUIVI_NAGEURS'
		}).
		when('/groupes', {
			templateUrl: 'views/groupe/groupes.html',
			access: 'ACCESS_GROUPES'
		}).
		when('/dossiers', {
			templateUrl: 'views/dossier/dossiers.html',
			access: 'ACCESS_DOSSIERS'
		}).
		when('/dossiers-avance', {
			templateUrl: 'views/dossier/dossiers-avance.html',
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
		when('/dossiers-stage', {
			templateUrl: 'views/dossier-stage/dossiers-stage.html',
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
		when('/produits', {
			templateUrl: 'views/boutique/produits.html',
		}).
		when('/orders', {
			templateUrl: 'views/boutique/orders.html',
			access: 'ACCESS_BOUTIQUE'
		}).
		when('/actualites', {
			templateUrl: 'views/actualite/actualites.html',
			access: 'ACCESS_ACTUALITES'
		}).
		when('/pages', {
			templateUrl: 'views/page/pages.html',
			access: 'ACCESS_PAGEEDITION'
		}).
		when('/nageur-effectif', {
			templateUrl: 'views/nageur/effectifs.html',
			access: 'ACCESS_NAGEUR_EFFECTIF'
		}).
		when('/records', {
			templateUrl: 'views/record/records.html',
			access: 'ACCESS_SUIVI_NAGEURS'
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
	
	//$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	//$httpProvider.defaults.withCredentials = true;
    //$httpProvider.interceptors.push('XSRFInterceptor');

}]);

adminApp.run(function($rootScope, $location, LoginService) {
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
    	$rootScope.displayMenu = false;
    	var token = $rootScope.aspttToken;
    	if(token) {
    		LoginService.isLogged.query({token: token}, function(data) {
    			if(!data.logged) {
    					window.location.href = "index.html";
    			} else {
    				$rootScope.authenticated = true;
    			}
    		});
    	} else {
    		window.location.href = "index.html";
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
            	AuthorizationService.hasAccess($(element), attrs.showWhenConnected);
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
