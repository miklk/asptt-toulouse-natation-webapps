/**
 * 
 */
var competitionController = angular.module('CompetitionController', ['ngRoute', 'competitionServices']);
competitionController.controller('CompetitionController', ['$rootScope', '$scope', '$location', '$anchorScroll', 'CompetitionService', function($rootScope, $scope, $location, $anchorScroll, CompetitionService) {
	$rootScope.isLoading = false;
	

}]);