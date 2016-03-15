/**
 * 
 */
var pageEdtionController = angular.module('PageEditionController', ['ngRoute', 'pageEditionServices']);
pageEdtionController.controller('PageEditionController', ['$rootScope', '$scope', '$location', '$anchorScroll', 'PageEditionService', function($rootScope, $scope, $location, $anchorScroll, PageEditionService) {
	$rootScope.isLoading = false;
	
	var loadAreas = function() {
		PageEditionService.areas.query(function (data) {
			$scope.areas = data;
			$rootScope.isLoading = false;
		});
	}
	
	var init = function() {
		$scope.showAddArea = false;
		$scope.showPages = false;
		$scope.showAddPage = false;
		$scope.showAddDivider = false;
		loadAreas();
	}
	
	init();
	
	$scope.addArea = function() {
		$scope.showAddArea = true;
		$scope.showPages = false;
		$scope.currentArea = {
				id: null,
				title: '',
				order: 0
		}
	}
	
	$scope.addPage = function() {
		$scope.showAddPage = true;
		$scope.currentPage = {
				title: '',
				order: 0,
				content: '',
				divider: false,
				alone: false,
				identifier: '',
				display: false
		}
	}
	
	$scope.saveArea = function() {
		console.log($scope.currentArea);
		PageEditionService.saveArea.query({}, $scope.currentArea, function () {
			loadAreas();
			$scope.showAddArea = false;
		});
	}
	
	$scope.findPages = function() {
		$scope.showAddArea = true;
		$rootScope.isLoading = true;
		PageEditionService.findPages.query({'areaId': $scope.currentArea.id}, function (data) {
			$scope.pages = data;
			$scope.showPages = true;
			$rootScope.isLoading = false;
		});
	}
	
	$scope.savePage = function() {
		console.log($scope.currentPage);
		PageEditionService.savePage.query({'areaId': $scope.currentArea.id}, $scope.currentPage, function () {
			$scope.showAddPage = false;
			$scope.findPages();
		});
	}
	
	$scope.addDivider = function() {
		$scope.showAddDivider = true;
		$scope.currentPage = {
				title: 'separateur',
				order: 0,
				content: '',
				divider: true,
				alone: false,
				identifier: '',
				display: false
		}
	}
	
	$scope.editPage = function(page) {
		$scope.currentPage = page;
		$scope.showAddDivider = false;
		$scope.showAddPage = false;
		if(page.divider) {
			$scope.showAddDivider = true;
		} else {
			$scope.showAddPage = true;
		}
	}
	
}]);