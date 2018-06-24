/**
 * 
 */
var saisieHeureController = angular.module('SaisieHeureController', ['ngRoute', 'saisieHeureServices']);

saisieHeureController.controller('SaisieHeureController', ['$rootScope', '$scope', '$location', '$filter', 'SaisieHeureService', function($rootScope, $scope, $location, $filter, SaisieHeureService) {
	$rootScope.isLoading = false;
	$scope.loadWeeks = function() {
		$rootScope.isLoading = true;
		$scope.endWeek = moment($scope.beginWeek.getTime()).endOf('week').toDate();
		SaisieHeureService.week.query({week : $filter('date')($scope.beginWeek,'yyyy-Www'), user: $rootScope.aspttTokenInfo.token }, function(data) {
			$rootScope.isLoading = false;
			$scope.days = data;
			//Conversion des dates
			angular.forEach($scope.days, function(day) {
				angular.forEach(day.heures, function(heure) {
					heure.begin = moment(heure.begin).toDate();
					heure.end = moment(heure.end).toDate();
				});
			});
		});
	}
	
	var init = function() {
		$scope.groupesSelected;
		$scope.beginWeek = moment().startOf('week').toDate();
		$scope.endWeek = moment().endOf('week').toDate();
		$scope.loadWeeks();
	}
	
	init();
	
	$scope.add = function(heures) {
		day = {
				id : null,
				user: null,
				activite : null,
				begin : moment().startOf('second').seconds(0).toDate(),
				end : moment().startOf('second').seconds(0).toDate(),
				commentaire : null,
				updatedBy : null,
				updated : null,
				createdBy : null,
				created :  null
		};
		heures.push(day);
	}
	
	$scope.valider = function() {
		$rootScope.isLoading = true;
		SaisieHeureService.valider.query({user: $rootScope.aspttTokenInfo.token}, $scope.days, function(data) {
			$rootScope.isLoading = false;
			alert("Toutes les saisies ont été enregistrées.");
		});
	}
}]);