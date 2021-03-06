var loginController = angular.module('LoginController', ['ngRoute', 'loginServices']);
loginController.controller('LoginController', ['$rootScope', '$scope', '$http', '$location', 'LoginService', '$sce', '$cookieStore', function($rootScope, $scope, $http, $location, LoginService, $sce, $cookieStore) {
	$scope.formData = {
			email: "",
			password: ""
	};
	$scope.credentials = {};

	var token = $cookieStore.get("asptt-token");
	if(token) {
		LoginService.isLogged.query({token: token}, function(data) {
			console.log("IsLogged " + data);
			if(data.logged) {
				window.location.href = "admin.html";
			}
		});
	}


	$scope.authenticate = function() {

	    var headers = $scope.credentials ? {authorization : "Basic "
	        + btoa($scope.credentials.email + ":" + $scope.credentials.password)
	    } : {};

	    $scope.formData.email = $scope.credentials.email;
	    $scope.formData.password= $scope.credentials.password;
	    $scope.authenticationResponse = false;
	    //$http.get('/resources/authentication/isAuthenticated', {emailheaders : headers}).success(function(data) {
	    LoginService.login.query({}, $scope.formData, function(data) {
	      $scope.authenticationResponse = true;
	      if (data.logged) {
	    	var token = data.token;
			$cookieStore.put("asptt-token", data.token);
			$cookieStore.put("asptt-token-info", data);
			
	        $rootScope.authenticated = true;
	        $http.get('/resources/authorization/access/' + token, {}).success(function(data) {
				$cookieStore.put("asptt-token-access", data);
				window.location.href = "admin.html";
			  });
	      } else {
	        $rootScope.authenticated = false;
	      }
	      console.log(data);
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

	$scope.logout = function() {
		var token = $cookieStore.get("asptt-token");
		$cookieStore.remove("asptt-token");
		$cookieStore.remove("asptt-token-info");
		$cookieStore.remove("asptt-token-access");
		$cookieStore.remove("asptt-dossier-query");
		LoginService.logout.query({token: token}, function(data) {
			window.location.href = "index.html";
		});
	}
}]);
