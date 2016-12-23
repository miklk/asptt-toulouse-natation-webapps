var boutiqueServices = angular.module('boutiqueServices', ['ngResource']);

boutiqueServices.factory('BoutiqueService', ['$resource', 
                                       function($resource) {
	return {
		products: $resource('/resources/boutique/products',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		saveProduct: $resource('/resources/boutique/product/update',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
		orders: $resource('/resources/boutique/orders',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		deleteProduct: $resource('/resources/boutique/product/delete/:product',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		saveOrder: $resource('/resources/boutique/order/update',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
		deleteOrder: $resource('/resources/boutique/order/delete/:order',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		deleteOrderProduct: $resource('/resources/boutique/order/delete/:order/:product',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		validateOrder: $resource('/resources/boutique/order/validate/:order',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
	};
}]);