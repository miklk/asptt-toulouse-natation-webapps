var paramServices = angular.module('paramServices', ['ngResource']);

paramServices.factory('ParamService', ['$resource', 
                                       function($resource) {
	return {
		groupes: $resource('/resources/params/groupes/:groupe',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		save: $resource('/resources/params/',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
	};
}]);