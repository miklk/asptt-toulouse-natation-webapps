/**
 *
 */
var competitionController = angular.module('CompetitionController', ['ngRoute', 'competitionServices']);
competitionController.controller('CompetitionController', ['$rootScope', '$scope', '$location', '$anchorScroll', 'CompetitionService', function($rootScope, $scope, $location, $anchorScroll, CompetitionService) {
	$rootScope.isLoading = false;
	$scope.competition = {
		title : '',
		begin : new Date(),
		end : new Date(),
		kind : 'INTERNE',
	};
	$scope.competitionKind = ['INTERNE'];

	var newEpreuve = function() {
		return {
			sexe : '',
			nage : '',
			distance : '',
			competition : -1,
			bassin : '25'
		}
	};
	$scope.epreuves = [
		newEpreuve(),
	]

	$scope.addEpreuve = function() {
		$scope.epreuves.push(newEpreuve());
	}

	$scope.create = function() {
		var parameters = {
			competition : $scope.competition,
			epreuves : $scope.epreuves,
		}
		CompetitionService.create.query(parameters, function(data) {
			console.log(data);
		});
	}

}]);
