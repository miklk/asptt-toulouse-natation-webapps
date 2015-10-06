var userServices = angular.module('userServices', ['ngResource']);

userServices.factory('UserService', ['$resource', 
                                       function($resource) {
	return {
		users: $resource('/resources/users/',{},{
			query:{method:'GET', isArray: false,params: {}}
		}),
		create: $resource('/resources/users/create',{},{
			query:{method:'POST', params: {}}
		}),
		remove: $resource('/resources/users/:user',{},{
			query:{method:'DELETE', params: {}}
		}),
		availableAuthorizations: $resource('/resources/users/available-authorizations',{},{
			query:{method:'GET', isArray: true,params: {}}
		})
	};
}]);