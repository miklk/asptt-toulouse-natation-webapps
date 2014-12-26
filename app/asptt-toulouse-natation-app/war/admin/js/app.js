/**
 * 
 */
var adminApp = angular.module('adminApp', ['ngRoute', 'adherentsServices', 'adherentServices', 'groupeServices', 'slotServices', 'userServices', 'UserController', 'AdherentListCtrl', 'DocumentController', 'LibelleController']);

adminApp.config(['$routeProvider', '$sceDelegateProvider', function ($routeProvider, $sceDelegateProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/adherents.html',
			controller: 'AdherentListCtrl'
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
			templateUrl: 'views/document/documents.html',
			controller: 'DocumentListController'
		}).
		when('/documents/:documentId', {
			templateUrl: 'views/document/document.html',
			controller: 'DocumentController'
		}).
		when('/libelles', {
			templateUrl: 'views/document/libelle/libelles.html',
			controller: 'LibelleController'
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