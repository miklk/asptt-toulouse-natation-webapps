/**
 * 
 */
var userController = angular.module('UserController', ['ngRoute', 'userServices']);

userController.controller('UserController', ['$scope', '$location', 'UserService', function($scope, $location, UserService) {
	$scope.formData = {
			search: ""
	};
	$scope.userFindResult = null;
	
	$scope.initForm = {
		email: "",
		nom: "",
		prenom: "",
		profiles: {}
	};
	
	$scope.userCreateAction = angular.copy($scope.initForm);
	
	$scope.search = function() {
		UserService.users.query({search: $scope.formData.search}, function (data) {
			$scope.userFindResult = data;
		});
	}
	
	$scope.creer = function() {
		UserService.create.query({userCreateAction: $scope.userCreateAction}, function (data) {
			$scope.userCreateResult = data;
			$scope.email = $scope.userCreation.email;
			$scope.userCreateAction = angular.copy($scope.initForm);
		});
	}
	
	$scope.remove = function(userId, index) {
		UserService.remove.query({user: userId}, function() {
			$scope.userFindResult.users.splice(index, 1);	
		});
	}
}]);