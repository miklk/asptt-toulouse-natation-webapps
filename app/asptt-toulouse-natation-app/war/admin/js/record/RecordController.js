/**
 * 
 */
var recordController = angular.module('RecordController', ['ngRoute', 'recordServices']);
recordController.controller('RecordController', ['$rootScope', '$scope', '$location', '$anchorScroll', 'RecordService', function($rootScope, $scope, $location, $anchorScroll, RecordService) {
	$rootScope.isLoading = false;
	$scope.bassins = ["25", "50"];
	$scope.nages = [ "NL", "Dos", "Brasse", "Papillon", "4N" ];
	$scope.categories = ["Toutes Catégories (Junior/Sérior)", "17 ans (Cadet)", "16 ans (Cadet)", "15 ans (Minime)", "14 ans (Minime)", "13 ans (Benjamin)", "12 ans (Benjamin)"];
	$scope.bassin = "25";
	$scope.showAddRecord = false;
	
	var loadRecords = function() {
		RecordService.records.query({'bassin': $scope.bassin}, function (data) {
			$scope.records = data;
			$rootScope.isLoading = false;
		});
	}
	
	var init = function() {
		loadRecords();
		$scope.showAddRecord = false;
	}
	
	init();
	
	$scope.addRecord = function(epreuve) {
		$scope.recordCreation = {
				age: '',
				nom: '',
				prenom: '',
				temps: '',
				annee: '',
				jour: '',
				lieu: ''
		};
		$scope.showAddRecord = true;
	}
	$scope.addRecordCat = function(record) {
		$scope.recordCreation = {
				age: record.age,
				nom: '',
				prenom: '',
				temps: '',
				annee: '',
				jour: '',
				lieu: ''
		};
		$scope.showAddRecord = true;
	}
	
	$scope.createRecord = function() {
		console.log($scope.recordCreation);
		RecordService.create.query($scope.recordCreation, function(data) {
			console.log(data);
		});
	}
}]);