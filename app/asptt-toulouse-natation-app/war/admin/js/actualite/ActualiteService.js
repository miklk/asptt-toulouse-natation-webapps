var actualiteServices = angular.module('actualiteServices', ['ngResource']);

actualiteServices.factory('ActualiteService', ['$resource', 
                                       function($resource) {
	return {
		actualites: $resource('/resources/actualites/all',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		publish: $resource('/resources/actualites',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
		remove: $resource('/resources/actualites/:actualite',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		})
	};
}]);