var enfServices = angular.module('enfServices', ['ngResource']);

enfServices.factory('EnfService', ['$resource', 
                                       function($resource) {
	return {
		creneaux: $resource('/resources/enf/creneaux',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
	};
}]);