var jeviensController = angular.module('JeViensController', ['ngRoute']);
jeviensController.controller('JeViensController', ['$rootScope', '$scope', '$cookieStore', '$http', '$location', '$routeParams', 'JeViensService', function($rootScope, $scope, $cookieStore, $http, $location, $routeParams, JeViensService) {
	var token = $routeParams.token;
    console.log(token);
    JeViensService.findNageur.query({token: token}, function(data) {
    	if(!data.access) {
    		$location.path("/error");
    	} else {
    		$scope.presence = data;
    	}
    });
    
    $scope.validate = function() {
    	JeViensService.validate.query({token: token}, $scope.presence, function(data) {
    		if(data) {
    			alert("Présences enregistrées, à la semaine prochaine !");
    		} else {
    			alert("Oups il y a eu une erreur !");
    		}
    	});
    }
}]);