var creneauStatServices = angular.module('creneauStatServices', ['ngResource']);

creneauStatServices.factory('CreneauStatService', ['$resource', 
                                       function($resource) {
	return {
		byGroupe: $resource('/resources/creneauxStat/groupes',{},{
			query:{method:'GET', isArray: true,}
		}),
		byGroupeEnf: $resource('/resources/creneauxStat/groupes',{},{
			query:{method:'GET', isArray: true,}
		}),
		byPiscine: $resource('/resources/creneauxStat/piscines',{},{
			query:{method:'GET', isArray: true, params: {}}
		})
	};
}]);