var documentServices = angular.module('documentServices', ['ngResource']);

documentServices.factory('DocumentService', ['$resource', 
                                       function($resource) {
	return {
		documents: $resource('/resources/documents/list',{},{
			query:{method:'GET', isArray: false,params: {}}
		}),
		document: $resource('/resources/documents/:document', {document: '@documentId'},{
  			'get': {method: 'GET', isArray: false}
  		})
	};
}]);

documentServices.factory('LibelleGroupeService', ['$resource', 
                                             function($resource) {
      	return {
      		groupes: $resource('/resources/libellegroupes/list',{},{
      			query:{method:'GET', isArray: false,params: {}}
      		})
      	};
}]);