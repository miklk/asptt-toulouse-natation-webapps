var suiviNageurServices = angular.module('suiviNageurServices', ['ngResource']);

suiviNageurServices.factory('SuiviNageurService', ['$resource', 
                                       function($resource) {
	return {
		days: $resource('/resources/swimmerStats/days/:groupe',{},{
			query:{method:'GET', isArray: false,params: {day: '@day'}}
		}),
		weeks: $resource('/resources/swimmerStats/weeks/:groupe',{},{
			query:{method:'GET', isArray: false,params: {week: '@week'}}
		}),
		update: $resource('/resources/swimmerStats/:nageur',{nageur:'@nageur'},{
			query:{method:'PUT', params: {}}
		}),
		updateAll: $resource('/resources/swimmerStats/',{},{
			query:{method:'POST', params: {}}
		})
	};
}]);