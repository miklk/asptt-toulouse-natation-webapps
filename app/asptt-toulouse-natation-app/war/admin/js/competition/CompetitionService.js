var recordServices = angular.module('competitionServices', ['ngResource']);

recordServices.factory('CompetitionService', ['$resource', 
                                       function($resource) {
	return {
		records: $resource('/resources/competitions/',{},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
	};
}]);