var recordServices = angular.module('competitionServices', ['ngResource']);

recordServices.factory('CompetitionService', ['$resource',
                                       function($resource) {
	return {
		create: $resource('/resources/competitions/create',{},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
    competitions : $resource('/resources/competitions', {}, {
      query : {method: 'GET', isArray: true}
    }),
    epreuves : $resource('/resources/competitions/:competition', {}, {
      query : {method: 'GET', isArray: true}
    }),
	};
}]);
