/**
 * 
 */
var recordController = angular.module('RecordController', ['ngRoute', 'recordServices', 'ui.toggle']);
recordController.controller('RecordController', ['$rootScope', '$scope', 'RecordService', function($rootScope, $scope, RecordService) {
	$rootScope.isLoading = false;
	$scope.categories = ["Toutes Catégories (Junior/Sérior)", "17 ans (Cadet)", "16 ans (Cadet)", "15 ans (Minime)", "14 ans (Minime)", "13 ans (Benjamin)", "12 ans (Benjamin)"];
	$scope.bassin = "25";
	$scope.bassinToggle = true;
	
	$scope.loadRecordsF = function() {
		RecordService.records.query({'bassin': $scope.bassin, 'sexe': 1}, function (data) {
			$scope.recordsF = data;
			$rootScope.isLoading = false;
		});
	}
	
	$scope.loadRecordsM = function() {
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
	
}]);