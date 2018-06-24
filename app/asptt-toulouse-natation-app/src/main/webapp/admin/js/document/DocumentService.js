var documentServices = angular.module('documentServices', ['ngResource']);

documentServices.factory('DocumentService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/documents',{},{
			query:{method:'GET', isArray: false,params: {}}
		}),
		create: $resource('/resources/documents/',{},{
			query:{method:'POST', params: {intitule: '@intitule', parent: '@parent'}}
		}),
		update: $resource('/resources/documents/:documentId',{},{
			query:{method:'PUT', params: {}}
		}),
		remove: $resource('/resources/documents/:documentId',{},{
			query:{method:'DELETE', params: {}}
		})
	};
}]);