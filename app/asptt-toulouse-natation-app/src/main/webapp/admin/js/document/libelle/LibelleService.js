var libelleServices = angular.module('libelleServices', ['ngResource']);

libelleServices.factory('LibelleService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/libelles',{},{
			query:{method:'GET', isArray: false,params: {}}
		}),
		create: $resource('/resources/libelles/',{},{
			query:{method:'POST', params: {intitule: '@intitule', parent: '@parent'}}
		}),
		update: $resource('/resources/libelles/:libelle',{libelle: '@libelle', newintitule: '@newintitule'},{
			query:{method:'PUT', params: {}}
		}),
		remove: $resource('/resources/libelles/:libelle',{},{
			query:{method:'DELETE', params: {}}
		})
	};
}]);