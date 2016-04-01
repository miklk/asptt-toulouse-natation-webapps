/**
 * 
 */
var recordController = angular.module('RecordController', ['ngRoute', 'recordServices']);
recordController.controller('RecordController', ['$rootScope', '$scope', '$location', '$anchorScroll', 'RecordService', function($rootScope, $scope, $location, $anchorScroll, RecordService) {
	$rootScope.isLoading = false;
	
	var loadRecords = function() {
		RecordService.records.query(function (data) {
			$scope.records = data;
			$rootScope.isLoading = false;
		});
	}
	
	var init = function() {
		loadRecords();
	}
	
	init();
}]);