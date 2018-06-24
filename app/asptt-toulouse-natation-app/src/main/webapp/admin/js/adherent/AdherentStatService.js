var adherentsStatServices = angular.module('adherentsStatServices', ['ngResource']);

adherentsStatServices.factory('AdherentsStatService', ['$resource', 
                                       function($resource) {
	return {
		sexeAge: $resource('/resources/adherentsStat/sexe-age',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		familles: $resource('/resources/adherentsStat/familles',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		localisations: $resource('/resources/adherentsStat/localisations',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		professions: $resource('/resources/adherentsStat/professions',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
	};
}]);