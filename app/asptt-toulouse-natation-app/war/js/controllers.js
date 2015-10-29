/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['adherentsServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', 'Adherent', function($scope, Adherent) {
	$scope.adherents = Adherent.get();
}]);


aspttNatTlsApp.controller('LoadingAppCtrl', ['$scope', 'LoadingApp', '$sce', function($scope, LoadingApp, $sce) {
	LoadingApp.get({}, function(data) {
		$scope.loadingApp = data;
		setTimeout("loadCarous()", 100);
		$scope.showActualite = function(actualite) {
	    	$("#actualites").show();
	    	$scope.actualiteTitle = actualite.title;
	    	$scope.actualiteHtml = $sce.trustAsHtml(actualite.content);
	    	$scope.actuDocuments = actualite.documentSet;
	    	var documentSize = actualite.documentSet.length;
			$scope.actuNbDocuments = documentSize;
	    	$scope.actuHasDocument = documentSize > 0;
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
	    	$scope.actuHasDocument = false;
			$scope.actuNbDocuments = 0;
	    };
	});
	$scope.scrollToTop = function() {
		$('html, body').animate({  
            scrollTop:$("#body_top").offset().top  
        }, 'slow');
	};
}]);

aspttNatTlsApp.controller('PageCtrl', ['$scope', 'pageId', 'PageService', 'DocumentService', '$routeParams', '$sce', function($scope, pageId, PageService, DocumentService, $routeParams, $sce) {
	var pageIdentifier = pageId;
	if(pageId == null) {
		pageIdentifier = $routeParams.pageId;
	}
	PageService.get({pageId: pageIdentifier}, function(data) {
		var pageUi = angular.fromJson(data);
		$scope.pageHtml = $sce.trustAsHtml(pageUi.content);
		var documentSize = pageUi.documents.length;
		$scope.hasDocument = documentSize > 0;
		$scope.nbDocuments = documentSize;
		$scope.documents = pageUi.documents;
		$scope.displayPdf = function() {
			$scope.pdfUrl = $sce.trustAsResourceUrl("http://docs.google.com/viewer?url=http%3A%2F%2F1-dot-asptt-toulouse-natation.appspot.com%2FdownloadDocument%3FdocumentId%3D5241851221114880%26fileId%3D4961648628465664");
			$("#pdf-viewer").show();
			$('html, body').animate({  
	            scrollTop:$("#pdf-viewer").offset().top  
	        }, 'slow');	
		};
		DocumentService.byLibelles.query({libelles: pageIdentifier}, function (data) {
			$scope.pageDocuments = data.documents;
		});
	});
}]);

aspttNatTlsApp.controller('CompetitionActualiteCtrl', ['$scope', 'ActualiteService', 'PageService', '$routeParams', '$sce', function($scope, ActualiteService, PageService,  $routeParams, $sce) {
	PageService.get({pageId: 'competitions-actualites'}, function(data) {
		var pageUi = angular.fromJson(data);
		$scope.pageHtml = $sce.trustAsHtml(pageUi.content);
		var documentSize = pageUi.documents.length;
		$scope.hasDocument = documentSize > 0;
		$scope.nbDocuments = documentSize;
		$scope.documents = pageUi.documents;
		$scope.displayPdf = function() {
			$scope.pdfUrl = $sce.trustAsResourceUrl("http://docs.google.com/viewer?url=http%3A%2F%2F1-dot-asptt-toulouse-natation.appspot.com%2FdownloadDocument%3FdocumentId%3D5241851221114880%26fileId%3D4961648628465664");
			$("#pdf-viewer").show();
			$('html, body').animate({  
	            scrollTop:$("#pdf-viewer").offset().top  
	        }, 'slow');	
		};
	});
	$scope.actualites = ActualiteService.get({competition: 'true'});
	$scope.actualiteContent = function(value) {
		return $sce.trustAsHtml(value);
	};
}]);

aspttNatTlsApp.controller('ActualiteCtrl', ['$scope', 'ActualiteService', 'PageService', '$routeParams', '$sce', function($scope, ActualiteService, PageService,  $routeParams, $sce) {
	PageService.get({pageId: 'actualites'}, function(data) {
		var pageUi = angular.fromJson(data);
		$scope.pageHtml = $sce.trustAsHtml(pageUi.content);
		var documentSize = pageUi.documents.length;
		$scope.hasDocument = documentSize > 0;
		$scope.nbDocuments = documentSize;
		$scope.documents = pageUi.documents;
		$scope.displayPdf = function() {
			$scope.pdfUrl = $sce.trustAsResourceUrl("http://docs.google.com/viewer?url=http%3A%2F%2F1-dot-asptt-toulouse-natation.appspot.com%2FdownloadDocument%3FdocumentId%3D5241851221114880%26fileId%3D4961648628465664");
			$("#pdf-viewer").show();
			$('html, body').animate({  
	            scrollTop:$("#pdf-viewer").offset().top  
	        }, 'slow');	
		};
	});
	$scope.actualites = ActualiteService.get({competition: 'false'});
	$scope.actualiteContent = function(value) {
		return $sce.trustAsHtml(value);
	};
}]);


aspttNatTlsApp.controller('AuthenticationCtrl', ['$scope', '$location', 'AuthenticationService', '$sce', function($scope, $location, AuthenticationService, $sce) {
	$scope.authenticate = function(provider) {
	 	AuthenticationService.get({openIdService: provider}, function(data) {
	 		console.log(provider + " " + data.providerUrl);
	 		window.location.href = data.providerUrl;
		});
	};
}]);

aspttNatTlsApp.controller('CalendrierCtrl', ['$scope', '$location', '$sce', function($scope, $location, $sce) {
	$scope.$on('$routeChangeSuccess', function() {

	    	"use strict";

	    	var options = {
	    		language: 'fr-FR',
	    		events_source: '../calendrier.json',
	    		view: 'month',
	    		tmpl_path: '../js/bootstrap-calendar/tmpls/',
	    		tmpl_cache: false,
	    		onAfterEventsLoad: function(events) {
	    			if(!events) {
	    				return;
	    			}
	    			var list = $('#eventlist');
	    			list.html('');

	    			$.each(events, function(key, val) {
	    				$(document.createElement('li'))
	    					.html(val.groupe + ' ' + val.title + ' - ' + val.lieu)
	    					.appendTo(list);
	    			});
	    		},
	    		onAfterViewLoad: function(view) {
	    			$('.page-header h3').text(this.getTitle());
	    			$('.btn-group button').removeClass('active');
	    			$('button[data-calendar-view="' + view + '"]').addClass('active');
	    		},
	    		classes: {
	    			months: {
	    				general: 'label'
	    			}
	    		}
	    	};

	    	var calendar = $('#calendar').calendar(options);

	    	$('.btn-group button[data-calendar-nav]').each(function() {
	    		var $this = $(this);
	    		$this.click(function() {
	    			calendar.navigate($this.data('calendar-nav'));
	    		});
	    	});

	    	$('.btn-group button[data-calendar-view]').each(function() {
	    		var $this = $(this);
	    		$this.click(function() {
	    			calendar.view($this.data('calendar-view'));
	    		});
	    	});

	    	$('#events-modal .modal-header, #events-modal .modal-footer').click(function(e){
	    		//e.preventDefault();
	    		//e.stopPropagation();
	    	});
	});
}]);