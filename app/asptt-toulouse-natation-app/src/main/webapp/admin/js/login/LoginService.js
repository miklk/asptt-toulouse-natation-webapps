var loginServices = angular.module('loginServices', ['ngResource']);
loginServices.factory('LoginService', ['$resource',
                                     function($resource) {
	return {
		openId: $resource('/resources/authentication/:openIdService',{},{
			query:{method:'GET'}
		}),
		/**login: $resource('/resources/authentication/isAuthenticated',{},{
			query:{method:'POST'}
		}),**/
		login: $resource('/resources/authentication/login',{},{
				query:{method:'POST'}
		}),
		forget: $resource('/resources/authentication/forget/:password',{},{
			query:{method:'GET'}
		}),
		isLogged: $resource('/resources/authentication/isLogged/:token',{},{
			query:{method:'GET', isArray: false}
		}),
		logout: $resource('/resources/authentication/logout/:token',{},{
			query:{method:'GET', isArray: false}
		}),
	};
}]);
