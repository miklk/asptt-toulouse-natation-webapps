/**
 * 
 */
var recordController = angular.module('RecordController', ['ngRoute', 'recordServices']);
recordController.controller('RecordController', ['$rootScope', '$scope', '$location', '$anchorScroll', '$filter', 'RecordService', function($rootScope, $scope, $location, $anchorScroll, $filter, RecordService) {
	$rootScope.isLoading = false;
	$scope.bassins = ["25", "50"];
	$scope.categories = ["Toutes Catégories (Junior/Sérior)", "17 ans (Cadet)", "16 ans (Cadet)", "15 ans (Minime)", "14 ans (Minime)", "13 ans (Benjamin)", "12 ans (Benjamin)"];
	$scope.bassin = "25";
	$scope.sexe = "1";
	$scope.showAddRecord = false;
	
	$scope.loadRecords = function() {
		RecordService.records.query({'bassin': $scope.bassin, 'sexe': $scope.sexe}, function (data) {
			$scope.records = data;
			$rootScope.isLoading = false;
		});
	}
	
	var loadEpreuves = function() {
		RecordService.epreuves.query({'bassin': $scope.bassin, 'sexe' : $scope.sexe}, function(data) {
			$scope.epreuves = data;
		});
	}
	
	$scope.load = function() {
		$scope.loadRecords();
		loadEpreuves();
	}
	
	var init = function() {
		$scope.load();
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
		$scope.epreuve = epreuve;
		$scope.showAddRecord = true;
	}
	
	var findEpreuve = function(id) {
		var epreuveFound = null;
		angular.forEach($scope.epreuves, function(epreuve) {
			if(epreuve.id == id) {
				epreuveFound = epreuve;
			}
		});
		return epreuveFound;
	}
	
	$scope.addRecordCat = function(record) {
		if(record != null) {
			$scope.recordCreation = {
					age: record.age,
					nom: record.nom,
					prenom: record.prenom,
					temps: record.temps,
					annee: record.annee,
					jour: new Date(record.jour),
					lieu: record.lieu,
					id : record.id,
					epreuve : record.epreuve.id
			};
			$scope.epreuve = findEpreuve(record.epreuve);
			$scope.showAddRecord = true;
		}
	}
	
	$scope.createRecord = function(epreuve) {
		$scope.recordCreation.epreuve = epreuve.id;
		console.log($scope.recordCreation);
		RecordService.create.query($scope.recordCreation, function(data) {
			alert("Record créé avec succès !");
			init();
		});
	}
	
	$scope.findRecord = function() {
		if($scope.epreuve && $scope.recordCreation.age) {
			RecordService.byEpreuve.query({'epreuve' : $scope.epreuve.id, 'categorie' : $scope.recordCreation.age}, function(data) {
				if(data.records.length > 0) {
					$scope.recordCreation = data.records[0];
					$scope.recordCreation.jour = new Date($scope.recordCreation.jour);
				} else {
					$scope.recordCreation.id = null;
				}
			});
		}
	}
}]);