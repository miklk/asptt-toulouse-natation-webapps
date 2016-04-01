var suiviNageurServices = angular.module('suiviNageurServices', ['ngResource']);

suiviNageurServices.factory('SuiviNageurService', ['$resource', 
                                       function($resource) {
	return {
		days: $resource('/resources/swimmerStats/days/',{},{
			query:{method:'GET', isArray: false,params: {groupes: '@groupes', day: '@day'}}
		}),
		weeks: $resource('/resources/swimmerStats/weeks',{},{
			query:{method:'GET', isArray: false,params: {groupes: '@groupes', week: '@week'}}
		}),
		months: $resource('/resources/swimmerStats/months',{},{
			query:{method:'GET', isArray: false,params: {groupes: '@groupes', month: '@month'}}
		}),
		years: $resource('/resources/swimmerStats/years',{},{
			query:{method:'GET', isArray: false,params: {groupes: '@groupes', year: '@year'}}
		}),
		update: $resource('/resources/swimmerStats/:nageur',{nageur:'@nageur'},{
			query:{method:'PUT', params: {}}
		}),
		updateAll: $resource('/resources/swimmerStats/',{},{
			query:{method:'POST', params: {}}
		}),
		periode: $resource('/resources/swimmerStats/periode/',{},{
			query:{method:'GET', isArray: false,params: {groupes: '@groupes', beginDay: '@beginDay', endDay: '@endDay'}}
		}),
	};
}]);