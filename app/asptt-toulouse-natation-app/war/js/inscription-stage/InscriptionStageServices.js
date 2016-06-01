var boutiqueServices = angular.module('inscriptionStageServices', ['ngResource']);

boutiqueServices.factory('InscriptionStageService', ['$resource', 
                                       function($resource) {
	return {
		products: $resource('/resources/boutique/products',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		commander: $resource('/resources/boutique/order',{},{
			query:{method:'PUT', isArray: false, params: {}}
		})
	};
}]);