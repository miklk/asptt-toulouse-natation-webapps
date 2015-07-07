/**
 * 
 */
var adminApp = angular.module('adminApp', ['ngRoute', 'angular-spinkit', 'adherentsServices','adherentsStatServices', 'adherentServices', 'groupeServices', 'slotServices', 'userServices', 'dossierServices', 'UserController', 'AdherentListCtrl', 'AdherentEmailCtrl', 'AdherentsStatCtrl', 'DocumentController', 'LibelleController', 'SuiviNageurController', 'GroupeController','DossierController', 'DossierNageurController', 'CreneauStatController', 'creneauStatServices']);

adminApp.config(['$routeProvider', '$sceDelegateProvider', function ($routeProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/adherents.html',
			controller: 'AdherentListCtrl'
		}).
		when('/home', {
			templateUrl: 'views/index.html'
		}).
		when('/adherents', {
			templateUrl: 'views/adherent/adherents.html',
			controller: 'AdherentListCtrl'
		}).
		when('/adherents/:adherentId', {
			templateUrl: 'views/adherent/adherent.html',
			controller: 'AdherentCtrl'
		}).
		when('/adherents/edit/:adherentId', {
			templateUrl: 'views/adherent/adherent-edit.html',
			controller: 'AdherentCtrl'
		}).
		when('/adherents-stat', {
			templateUrl: 'views/adherent/adherents-stat.html',
			controller: 'AdherentListCtrl'
		}).
		when('/adherent-email', {
			templateUrl: 'views/adherent/adherent-email.html',
			controller: 'AdherentEmailCtrl'
		}).
		when('/adherent-email2', {
			templateUrl: 'views/adherent/adherent-email2.html',
			controller: 'AdherentEmailCtrl'
		}).
		when('/en-jours', {
			templateUrl: 'views/en-jours.html'
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
			templateUrl: 'views/groupe/groupes.html'
		}).
		when('/dossiers', {
			templateUrl: 'views/dossier/dossiers.html'
		}).
		when('/dossier/:dossierId', {
			templateUrl: 'views/dossier/dossier.html',
			controller: 'DossierNageurController',
		}).
		when('/remplissage', {
			templateUrl: 'views/dossier/stat/remplissage.html'
		}).
		when('/remplissage-detail', {
			templateUrl: 'views/dossier/stat/remplissage-piscine.html'
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