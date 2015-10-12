var loginController = angular.module('LoginController', ['ngRoute', 'loginServices']);
loginController.controller('LoginController', ['$rootScope', '$scope', '$http', '$location', 'LoginService', '$sce', function($rootScope, $scope, $http, $location, LoginService, $sce) {
	$scope.formData = {
			email: "",
			password: ""
	};
	$scope.credentials = {};
	
	$scope.authenticate = function() {

	    var headers = $scope.credentials ? {authorization : "Basic "
	        + btoa($scope.credentials.email + ":" + $scope.credentials.password)
	    } : {};

	    $scope.formData.email = $scope.credentials.email;
	    $scope.formData.password= $scope.credentials.password;
	    //$http.get('/resources/authentication/isAuthenticated', {emailheaders : headers}).success(function(data) {
	    LoginService.login.query({}, $scope.formData, function(data) {
	      if (data.logged) {
	        $rootScope.authenticated = true;
	      } else {
	        $rootScope.authenticated = false;
	      }
	      console.log(data);
	 		$scope.loginResult = data;
	 		window.location.href = "#/dashboard";
	    });/**.error(function() {
	      $rootScope.authenticated = false;
	      console.log(data);
	 		$scope.loginResult = data;
	 		window.location.href = "#/dashboard";
	    });**/
	};
	
	  
	
	$scope.authenticateFederate = function(provider) {
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
	
	$scope.forget = function() {
		LoginService.forget.query({email: $scope.credentials.email}, function(data) {
			forgetOk = data;
		});
	}
}]);
