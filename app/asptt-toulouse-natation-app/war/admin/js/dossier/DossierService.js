var dossierServices = angular.module('dossierServices', ['ngResource']);

dossierServices.factory('DossierService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/dossiers/find',{},{
			query:{method:'GET', isArray: true, params: {q: '@query', groupe: '@groupe'}}
		}),
		findOne: $resource('/resources/dossiers/:dossier',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		remove: $resource('/resources/dossiers/:dossier',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		paiement: $resource('/resources/dossiers/paiement/:dossier',{},{
			query:{method:'PUT', isArray: false, params: {}}
		})
	};
}]);