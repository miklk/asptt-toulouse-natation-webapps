var loginServices = angular.module('loginServices', ['ngResource']);
loginServices.factory('LoginService', ['$resource', 
                                     function($resource) {
	return {
		openId: $resource('/resources/authentication/:openIdService',{},{
			query:{method:'GET'}
		}),
		login: $resource('/resources/authentication/isAuthenticated',{},{
			query:{method:'POST'}
		})
	};
}]);