var loginController = angular.module('LoginController', ['ngRoute', 'loginServices']);
loginController.controller('LoginController', ['$scope', '$location', 'LoginService', '$sce', function($scope, $location, LoginService, $sce) {
	$scope.formData = {
			email: "",
			password: ""
	};
	$scope.authenticate = function(provider) {
	 	LoginService.openId.query({openIdService: provider}, function(data) {
	 		window.location.href = data.providerUrl;
		});
	};
	$scope.login = function() {
	 	LoginService.login.query({email: $scope.email, password: $scope.password}, function(data) {
	 		console.log(data);
	 		$scope.loginResult = data;
	 		window.location.href = "admin/#/home";
		});
	};
}]);
