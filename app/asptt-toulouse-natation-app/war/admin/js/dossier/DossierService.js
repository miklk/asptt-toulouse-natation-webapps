var dossierServices = angular.module('dossierServices', ['ngResource']);

dossierServices.factory('DossierService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/dossiers/find',{},{
			query:{method:'GET', isArray: true, params: {q: '@query', groupe: '@groupe'}}
		}),
		findOne: $resource('/resources/dossiers/:dossier',{},{
			query:{method:'GET', isArray: false, params: {}}
		})
	};
}]);