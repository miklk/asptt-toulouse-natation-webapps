var documentServices = angular.module('documentServices', ['ngResource']);

documentServices.factory('DocumentService', ['$resource', 
                                       function($resource) {
	return {
		byLibelles: $resource('/resources/documents/byLibelles',{libelles: '@libelles'},{
			query:{method:'GET', isArray: false,params: {}}
		})
	};
}]);