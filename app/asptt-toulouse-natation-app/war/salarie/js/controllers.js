var salarieController = angular.module('SalarieController', ['ngRoute']);
salarieController.controller('SalarieController', ['$rootScope', '$scope', '$cookieStore', '$http', '$location', 'SalarieService', 'LoginService', function($rootScope, $scope, $cookieStore, $http, $location, SalarieService, LoginService) {
	var token = $cookieStore.get("asptt-token");
    $rootScope.aspttToken = token;
    $rootScope.displayMenu = true;
    var tokenInfo = $cookieStore.get("asptt-token-info");
    $rootScope.aspttTokenInfo = tokenInfo;
    $scope.toogleMenu = function() {
    	$rootScope.displayMenu = ! $rootScope.displayMenu;
    }
    
	var access = $cookieStore.get("asptt-token-access");
	$rootScope.access = access;
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
    
    LoginService.isLogged.query({token: token}, function(data) {
		console.log("IsLogged " + data);
		if(data.logged) {
			$rootScope.aspttTokenInfo = tokenInfo;
		    $scope.toogleMenu = function() {
		    	$rootScope.displayMenu = ! $rootScope.displayMenu;
		    }
		    var access = $cookieStore.get("asptt-token-access");
			$rootScope.access = access;
		} else {
			$scope.logout();
		}
	});
    
    
}]);