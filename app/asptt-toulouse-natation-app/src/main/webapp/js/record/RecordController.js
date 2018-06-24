/**
 * 
 */
var recordController = angular.module('RecordController', ['ngRoute', 'recordServices', 'ui.toggle']);
recordController.controller('RecordController', ['$rootScope', '$scope', 'RecordService', function($rootScope, $scope, RecordService) {
	$rootScope.isLoading = false;
	$scope.categories = ["Toutes Catégories", "17 ans (Cadet)", "16 ans (Cadet)", "15 ans (Minime)", "14 ans (Minime)", "13 ans (Benjamin)", "12 ans (Benjamin)"];
	$scope.bassin = "25";
	$scope.bassinToggle = true;
	$scope.filterRecord = '';
	$scope.maxlength = 10;
	$scope.displayF = true;
	$scope.displayM = true;
	
	
	RecordService.lastUpdated.query({}, function(data) {
		$scope.lastUpdate = new Date(parseInt(data.lastUpdated.trim(), 10));
	});
	
	$scope.loadRecordsF = function() {
		$rootScope.isLoading = true;
		RecordService.records.query({'bassin': $scope.bassin, 'sexe': 1}, function (data) {
			$scope.recordsF = data;
			$rootScope.isLoading = false;
		});
	}
	
	$scope.loadRecordsM = function() {
		$rootScope.isLoading = true;
		RecordService.records.query({'bassin': $scope.bassin, 'sexe': 0}, function (data) {
			$scope.recordsM = data;
			$rootScope.isLoading = false;
		});
	}
	
	$scope.loadRecordsF();
	$scope.loadRecordsM();
	
	$scope.load = function() {
		if($scope.bassinToggle) {
			$scope.bassin = "25";
		} else {
			$scope.bassin ="50";
		}
		$scope.loadRecordsF();
		$scope.loadRecordsM();
	}
	
	$scope.setFilter = function(categorie) {
		if($scope.filterRecord == categorie) {
			$scope.filterRecord = '';
		} else {
			$scope.filterRecord = categorie
		}
	}
	
	$scope.isTooLong = function(texte) {
		var tooLong = false;
		if(texte != null) {
			tooLong = texte.length > $scope.maxlength;
		}
		return tooLong;
	}
	
	$scope.toggleSexe = function(all) {
		if(!all) {
			$scope.displayF = !$scope.displayF;
			$scope.displayM = !$scope.displayM;
		} else {
			$scope.displayF = true;
			$scope.displayM = true;
		}
	}
	
}]);