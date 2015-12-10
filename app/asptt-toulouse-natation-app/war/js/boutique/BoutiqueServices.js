var boutiqueServices = angular.module('boutiqueServices', ['ngResource']);

boutiqueServices.factory('BoutiqueService', ['$resource', 
                                       function($resource) {
	return {
		commander: $resource('/resources/boutique/commander',{},{
			query:{method:'PUT', isArray: false, params: {}}
		})
	};
}]);