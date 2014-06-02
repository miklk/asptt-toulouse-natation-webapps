/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['adherentsServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', 'Adherent', function($scope, Adherent) {
	$scope.adherents = Adherent.get();
}]);


var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute','loadingAppServices', 'pageServices', 'loadingAlbumServices']);
aspttNatTlsApp.controller('LoadingAppCtrl', ['$scope', 'LoadingApp', '$sce', function($scope, LoadingApp, $sce) {
	$scope.loadingApp = LoadingApp.get();
	$scope.showActualite = function(actualite) {
    	$("#myCarousel").hide();
    	$("#actualites").show();
    	$scope.actualiteTitle = actualite.title;
    	$scope.actualiteHtml = $sce.trustAsHtml(actualite.content);
    	$scope.actuDocuments = actualite.documentSet;
    	$scope.hasDocument = actualite.hasDocument;
    };
    $scope.hideActualite = function() {
    	$("#myCarousel").show();
    	$("#actualites").hide();
    	$scope.actualiteTitle = "";
    	$scope.actualiteHtml = "";
    	$scope.actuDocuments = null;
    	$scope.hasDocument = false;
    };
}]);

aspttNatTlsApp.controller('PageCtrl', ['$scope', 'PageService', '$routeParams', '$sce', function($scope, PageService, $routeParams, $sce) {
	PageService.get({pageId: $routeParams.pageId}, function(data) {
		var pageUi = angular.fromJson(data);
		$scope.pageHtml = $sce.trustAsHtml(pageUi.content);
	});
}]);

aspttNatTlsApp.controller('LoadingAlbumCtrl', ['$scope', 'LoadingAlbumService', function($scope, LoadingAlbumService) {
	$scope.albums = LoadingAlbumService.get();
	$scope.getPhotos = function(album) {
		//Build slider
		$("#diapo").fadeIn();
		$("#slider3").empty();
		$("#slider3-pager").empty();
		$.each(album.photos, function() {
			var img = $("<img src=\"\" />");
			$(img).attr("src", this);
			var liElt = $("<li></li>");
			liElt.append(img);
			$("#slider3").append(liElt);
			
			var imgThumbl = $("<img src=\"\" />");
			$(imgThumbl).attr("src", this);
			$(imgThumbl).css("max-width", "50px");
			
			var aThumbl = $("<a href=\"#\"></a>");
			aThumbl.append(imgThumbl);
			var liThumbl = $("<li></li>");
			liThumbl.append(aThumbl);
			$("#slider3-pager").append(liThumbl);
		});
		$("#slider3").responsiveSlides({
	        manualControls: '#slider3-pager',
	        maxwidth: 540,
	        speed: 300
	      });
	};
}]);

aspttNatTlsApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/home.html',
			controller: 'LoadingAppCtrl'
		}).
		when('/page/Partenaires', {
			templateUrl: 'views/partenaires.html'
		}).
		when('/page/:pageId', {
			templateUrl: 'views/page.html',
			controller: 'PageCtrl'
		}).
		when('/error', {
			templateUrl: 'views/error.html'
		}).
		otherwise({
			redirectTo: '/error'
		});
}]);