var recordServices = angular.module('recordServices', ['ngResource']);

recordServices.factory('RecordService', ['$resource', 
                                       function($resource) {
	return {
		records: $resource('/resources/records/:bassin/:sexe',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		lastUpdated: $resource('/resources/records/last-updated',{},{
			query:{method:'GET', isArray: false, params: {}, transformResponse: function (data, headersGetter) {
			    return { lastUpdated: data }
		    }}
		}),
	};
}]);