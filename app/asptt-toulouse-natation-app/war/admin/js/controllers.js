var adminController = angular.module('AdminController', ['ngRoute']);
adminController.controller('AdminController', ['$rootScope', '$scope', '$cookieStore', '$http', '$location', function($rootScope, $scope, $cookieStore, $http, $location) {
	var token = $cookieStore.get("asptt-token");
    $rootScope.aspttToken = token;
    console.log(token);
    $rootScope.displayMenu = true;
    var tokenInfo = $cookieStore.get("asptt-token-info");
    $rootScope.aspttTokenInfo = tokenInfo;
    $scope.toogleMenu = function() {
    	$rootScope.displayMenu = ! $rootScope.displayMenu;
    }
    
	var access = $cookieStore.get("asptt-token-access");
	$rootScope.access = access;
	
	$scope.search = function() {
		$location = "/dossiers/" + $scope.query;
	}
	
}]);