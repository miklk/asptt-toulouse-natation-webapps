var suiviNageurServices = angular.module('suiviNageurServices', ['ngResource']);

suiviNageurServices.factory('SuiviNageurService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/swimmerStats/days/:groupe',{},{
			query:{method:'GET', isArray: false,params: {day: '@day'}}
		}),
		update: $resource('/resources/swimmerStats/:nageur',{nageur:'@nageur'},{
			query:{method:'PUT', params: {}}
		}),
		updateAll: $resource('/resources/swimmerStats/',{},{
			query:{method:'POST', params: {}}
		})
	};
}]);