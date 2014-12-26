/**
 * 
 */
var libelleController = angular.module('LibelleController', ['ngRoute', 'libelleServices']);

libelleController.controller('LibelleController', ['$scope', '$location', 'LibelleService', function($scope, $location, LibelleService) {
	function init() {
	LibelleService.list.query({}, function(data) {
		$scope.showLibelleCreationPanel = false;
		$scope.currentLibelle = null;
		$scope.libelles = data.libelles;
		$scope.newLibelle = {
				intitule: '',
				libelles: null,
				parent: ''
		};
		$scope.newLibelle.libelles = data.wholeLibelles;
	});
	};
	init();
	
	$scope.createLibelle = function() {
		LibelleService.create.query({intitule: $scope.newLibelle.intitule, parent: $scope.newLibelle.parent}, function(data) {
			console.log(data);
			$scope.libelleCreateResult = data;
			if(!data.exists && !data.noTitle) {
				init();
				$scope.libelleCreateResult = {
						success : true
				}
			}
		});
	};
	
	$scope.selectLibelle = function(libelle) {
		$scope.newintitule = libelle.intitule;
		$scope.currentLibelle = libelle;
		$scope.showLibelleCreationPanel = false;
	};
	
	$scope.editLibelle = function() {
		console.log($scope.currentLibelle.intitule);
		LibelleService.update.query({libelle: $scope.currentLibelle.intitule, newintitule: $scope.newintitule}, function(data) {
			console.log(data);
			$scope.libelleUpdateResult = data;
			if($scope.libelleUpdateResult.success) {
				init();
			}
		}
		);
	};
	
	$scope.remove = function() {
		if(confirm("Supprimer définitivement ce libellé ?")) {
			LibelleService.remove.query({libelle: $scope.currentLibelle.intitule}, function(data) {
				console.log(data);
				init();
			});
		}
	}
}]);