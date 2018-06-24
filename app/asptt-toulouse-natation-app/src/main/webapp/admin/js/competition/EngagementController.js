/**
 *
 */
var engagementController = angular.module('EngagementController', ['ngRoute', 'competitionServices']);
engagementController.controller('EngagementController', ['$rootScope', '$scope', '$location', '$anchorScroll', 'CompetitionService', function($rootScope, $scope, $location, $anchorScroll, CompetitionService) {
	$rootScope.isLoading = true;

	CompetitionService.competitions.query(function(data) {
		$rootScope.isLoading = false;
		$scope.competitions = data;
	});

	var newEngagement = function() {
		return {
			nageur: 0,
			epreuves : [
				{bassin: 25}
			],
			};
	}

	$scope.engagements = [
		newEngagement(),
	];

	$scope.loadEpreuves = function() {
		CompetitionService.epreuves.query({competition : $scope.competition.id}, function(data) {
			$scope.epreuves = data;
		});
	}

	$scope.addEpreuve = function(engagement) {
		engagement.epreuves.push({bassin : 25});
	}
	$scope.addEngagement = function() {
		$scope.engagements.push(newEngagement());
	}
	$scope.displayEpreuve = function(epreuve) {
		return epreuve.distance + " " + epreuve.nage;
	}
}]);
