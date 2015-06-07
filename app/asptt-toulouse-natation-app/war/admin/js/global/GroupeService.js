var groupeServices = angular.module('groupeServices', ['ngResource']);

groupeServices.factory('GroupeService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/groupes',{nouveau: '@nouveau'},{
			query:{method:'GET', isArray: false,}
		}),
		all: $resource('/resources/groupes/all',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		findOne: $resource('/resources/groupes/:groupe',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		remove: $resource('/resources/groupes/:groupe',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		})
	};
}]);