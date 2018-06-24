/**
 *
 */
var suiviHeureController = angular.module('SuiviHeureController', ['ngRoute', 'saisieHeureServices']);

suiviHeureController.controller('SuiviHeureController', ['$rootScope', '$scope', '$location', '$filter', 'SaisieHeureService', function($rootScope, $scope, $location, $filter, SaisieHeureService) {
	$rootScope.isLoading = false;
	$scope.user = null;
	$scope.loadUsers = function() {
		$rootScope.isLoading = true;
		SaisieHeureService.users.query(function(data) {
			$rootScope.isLoading = false;
			$scope.users = data;
		});
	}
	$scope.loadMonth = function() {
		$rootScope.isLoading = true;
		$scope.endMonth = moment($scope.beginMonth.getTime()).endOf('month').toDate();
		var userId = -1;
		if($scope.user != null) {
			userId = $scope.user.id;
		}
		SaisieHeureService.month.query({month : $filter('date')($scope.beginMonth,'yyyy-MM'), user: userId}, function(data) {
			$rootScope.isLoading = false;
			$scope.days = data;
		});
	}

	var getSalarie = function(id) {
		var found = false;
		var i = 0;
		var user = null;
		while(!found && i < $scope.users.length) {
			if($scope.users[i].id === id) {
				user = $scope.users[i];
				found = true;
			}
			i++;
		}
		return user;
	};

	$scope.displaySalarie = function(id) {
		var salarie = getSalarie(id);
		var display = "";
		if(salarie != null) {
			display = salarie.firstName + " " + salarie.lastName;
		}
		return display;
	}

	$scope.canDisplaySalarie = function() {
		return $scope.user == null;
	}

	var init = function() {
		$scope.groupesSelected;
		$scope.beginMonth = moment().startOf('month').toDate();
		$scope.endMonth = moment().endOf('month').toDate();
		$scope.loadUsers();
		$scope.loadMonth();
	}

	init();
}]);
