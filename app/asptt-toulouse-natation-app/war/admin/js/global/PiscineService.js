var piscineServices = angular.module('piscineServices', ['ngResource']);

piscineServices.factory('PiscineService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/piscines',{},{
			query:{method:'GET', isArray: false,params: {}}
		})
	};
}]);