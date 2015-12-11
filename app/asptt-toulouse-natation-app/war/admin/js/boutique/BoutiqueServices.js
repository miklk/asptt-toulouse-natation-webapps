var boutiqueServices = angular.module('boutiqueServices', ['ngResource']);

boutiqueServices.factory('BoutiqueService', ['$resource', 
                                       function($resource) {
	return {
		products: $resource('/resources/boutique/products',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		saveProduct: $resource('/resources/boutique/product/update',{},{
			query:{method:'POST', isArray: false, params: {}}
		})
	};
}]);