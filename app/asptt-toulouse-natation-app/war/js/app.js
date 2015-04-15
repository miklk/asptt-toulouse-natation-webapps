var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute','loadingAppServices', 'pageServices', 'loadingAlbumServices', 'inscriptionServices', 'slotServices', 'groupeServices', 'inscriptionNouveauServices', 'removeAdherentServices', 'authenticationServices', 'actualiteServices', 'LoginController', 'DocumentController', 'mediaServices', 'MediaCtrl']);
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
		when('/page/section-sportive', {
			templateUrl: 'views/section-sportive.html',
			controller: 'PageCtrl',
			resolve: { pageId: function() {return "section-sportive";}}
		}).
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
			controller: 'MediaCtrl'
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