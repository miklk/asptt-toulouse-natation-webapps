/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['adherentsServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', 'Adherent', function($scope, Adherent) {
	$scope.adherents = Adherent.get();
}]);


var aspttNatTlsApp = angular.module('aspttNatTlsApp', ['ngRoute','loadingAppServices', 'pageServices', 'loadingAlbumServices']);
aspttNatTlsApp.controller('LoadingAppCtrl', ['$scope', 'LoadingApp', '$sce', function($scope, LoadingApp, $sce) {
	LoadingApp.get({}, function(data) {
		$scope.loadingApp = data;
		$scope.showActualite = function(actualite) {
	    	$("#actualites").show();
	    	$scope.actualiteTitle = actualite.title;
	    	$scope.actualiteHtml = $sce.trustAsHtml(actualite.content);
	    	$scope.actuDocuments = actualite.documentSet;
	    	$scope.hasDocument = actualite.hasDocument;
	    	$('html, body').animate({  
	            scrollTop:$("#actualites").offset().top  
	        }, 'slow');
	    };
	    $scope.hideActualite = function() {
	    	$('html, body').animate({  
	            scrollTop:$("#carous_container").offset().top  
	        }, 'slow');
	    	$("#actualites").hide();
	    	$scope.actualiteTitle = "";
	    	$scope.actualiteHtml = "";
	    	$scope.actuDocuments = null;
	    	$scope.hasDocument = false;
	    };
	});
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
		$('html, body').animate({  
            scrollTop:$("#diapo").offset().top  
        }, 'slow');
		$("#slider3").empty();
		$("#slider3-pager").empty();
		$scope.albumTitle = album.intitule;
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
	$scope.hidePhotos = function() {
		$('html, body').animate({  
            scrollTop:$("#albums").offset().top  
        }, 'slow');
		$("#diapo").fadeOut();
		$scope.albumTitle = "";
	};
}]);

aspttNatTlsApp.config(['$routeProvider', function ($routeProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/home.html',
			controller: 'LoadingAppCtrl'
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