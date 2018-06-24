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
		authorizations: {}
	};
	
	$scope.userCreateAction = angular.copy($scope.initForm);
	
	//Load authorizations
	UserService.availableAuthorizations.query({}, function (data) {
		$scope.availableAuthorizations = data;
	});
	
	$scope.search = function() {
		UserService.users.query({search: $scope.formData.search}, function (data) {
			$scope.userFindResult = data;
		});
	}
	$scope.search();
	
	$scope.creer = function() {
		UserService.create.query({}, $scope.userCreateAction, function (data) {
			$scope.userCreateResult = data;
		});
	}
	
	$scope.remove = function(userId, index) {
		UserService.remove.query({user: userId}, function() {
			$scope.userFindResult.users.splice(index, 1);	
		});
	}
	
	$scope.loadUser = function(user) {
		$scope.userCreateAction.email = user.emailAddress;
		$scope.userCreateAction.nom = user.userData.lastName;
		$scope.userCreateAction.prenom = user.userData.firstName;
		$scope.userCreateAction.authorizations = user.profiles;
	}
}]);