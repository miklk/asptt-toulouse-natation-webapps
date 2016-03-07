var adminController = angular.module('AdminController', ['ngRoute']);
adminController.controller('AdminController', ['$rootScope', '$scope', '$cookieStore', function($rootScope, $scope, $cookieStore) {
	var token = $cookieStore.get("asptt-token");
    $rootScope.aspttToken = token;
    console.log(token);
    $rootScope.displayMenu = false;
    var tokenInfo = $cookieStore.get("asptt-token-info");
    $rootScope.aspttTokenInfo = tokenInfo;
    $scope.toogleMenu = function() {
    	$rootScope.displayMenu = ! $rootScope.displayMenu;
    }
}]);