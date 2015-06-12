var dossierServices = angular.module('dossierServices', ['ngResource']);

dossierServices.factory('DossierService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/dossiers',{},{
			query:{method:'GET', isArray: false,params: {}}
		})
	};
}]);