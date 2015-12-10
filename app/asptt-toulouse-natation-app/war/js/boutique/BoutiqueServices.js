var boutiqueServices = angular.module('boutiqueServices', ['ngResource']);

boutiqueServices.factory('BoutiqueService', ['$resource', 
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