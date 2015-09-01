var enfServices = angular.module('enfServices', ['ngResource']);

enfServices.factory('EnfService', ['$resource', 
                                       function($resource) {
	return {
		presence: $resource('/resources/enf/presences/:creneau',{creneau: '@creneau'},{
			query:{method:'GET', isArray: true, params: {}}
		}),
	};
}]);