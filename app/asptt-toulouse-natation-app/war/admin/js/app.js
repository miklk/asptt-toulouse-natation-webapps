/**
 * 
 */
var adminApp = angular.module('adminApp', ['ngRoute', 'adherentsServices', 'adherentServices', 'groupeServices', 'slotServices', 'userServices', 'UserController', 'AdherentListCtrl', 'DocumentController', 'LibelleController', 'SuiviNageurController']);

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
			templateUrl: 'views/adherents.html',
			controller: 'AdherentListCtrl'
		}).
		when('/adherents/:adherentId', {
			templateUrl: 'views/adherent.html',
			controller: 'AdherentCtrl'
		}).
		when('/adherents/edit/:adherentId', {
			templateUrl: 'views/adherent-edit.html',
			controller: 'AdherentCtrl'
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
		when('/suiviNageur', {
			templateUrl: 'views/suivi/suiviNageur.html'
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