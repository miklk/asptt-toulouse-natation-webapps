/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['ngRoute', 'adherentsServices', 'adherentServices', 'groupeServices', 'slotServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', '$location', 'AdherentsService', function($scope, $location, AdherentsService) {
	$scope.formData = {
			search: ""
	};

	$scope.search = function() {
		AdherentsService.get({search: $scope.formData.search}, function (data) {
			$scope.adherents = data.adherents;
		});
	}
	$scope.loadAdherent = function(id) {
		console.log("#/adherents/" + id);
		$location.path("/adherents/" + id);
	}
}]);

adherentsApp.controller('AdherentCtrl', ['$scope', 'AdherentService', 'GroupeService', 'SlotService', '$routeParams', '$sce', function($scope, AdherentService, GroupeService, SlotService, $routeParams, $sce) {
	AdherentService.get({adherent: $routeParams.adherentId}, function (data) {
		$scope.adherent = data;
		$scope.loadGroupe = function() {
			GroupeService.get({nouveau: false}, function (data) {
				$scope.groupes = data;
				$("#panel-adherent").toggleClass("col-md-11", false);
				$("#panel-adherent").toggleClass("col-md-6", true);
				$("#panel-groupe").toggleClass("col-md-6", true);
				$("#panel-groupe").toggleClass("hidden", false);
				$("#panel-groupe").toggleClass("show", true);
				$("#panel-creneaux").toggleClass("hidden", true);
				$("#panel-creneaux").toggleClass("show", false);
				$('html, body').animate({  
		            scrollTop:$("#panel-adherent").offset().top  
		        }, 'slow');
			});
		}
		$scope.loadCreneaux = function(groupe) {
			SlotService.get({groupe: $scope.adherent.groupe.id}, function (data) {
				$scope.creneaux = data.creneaux;
				$("#panel-adherent").toggleClass("col-md-11", false);
				$("#panel-adherent").toggleClass("col-md-6", true);
				$("#panel-groupe").toggleClass("show", false);
				$("#panel-groupe").toggleClass("hidden", true);
				$("#panel-creneaux").toggleClass("col-md-6", true);
				$("#panel-creneaux").toggleClass("hidden");
				$("#panel-creneaux").toggleClass("show");
				$('html, body').animate({  
		            scrollTop:$("#panel-adherent").offset().top  
		        }, 'slow');
			});
		}
		$scope.updateAdherent = function() {
			var req = new AdherentService($scope.adherent);
			req.$save();
			$("#panel-creneaux").toggleClass("hidden");
			$("#panel-adherent").toggleClass("col-md-11");
			$("#panel-adherent").toggleClass("col-md-6");
			$('html, body').animate({  
	            scrollTop:$("#panel-adherent").offset().top  
	        }, 'slow');
		}
	});
}]);

adherentsApp.config(['$routeProvider', '$sceDelegateProvider', function ($routeProvider, $sceDelegateProvider) {
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