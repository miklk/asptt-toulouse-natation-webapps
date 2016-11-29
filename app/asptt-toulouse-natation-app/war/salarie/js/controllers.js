var salarieController = angular.module('SalarieController', ['ngRoute']);
salarieController.controller('SalarieController', ['$rootScope', '$scope', '$cookieStore', '$http', '$location', '$routeParams', 'SalarieService', function($rootScope, $scope, $cookieStore, $http, $location, $routeParams, SalarieService) {
	var token = $routeParams.token;
    console.log(token);
    $rootScope.displayMenu = true;
    var tokenInfo = $cookieStore.get("asptt-token-info");
    $rootScope.aspttTokenInfo = tokenInfo;
    $scope.toogleMenu = function() {
    	$rootScope.displayMenu = ! $rootScope.displayMenu;
    }
    var access = $cookieStore.get("asptt-token-access");
	$rootScope.access = access;
    
}]);