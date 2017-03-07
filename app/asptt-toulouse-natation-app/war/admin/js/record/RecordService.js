var recordServices = angular.module('recordServices', ['ngResource']);

recordServices.factory('RecordService', ['$resource', 
                                       function($resource) {
	return {
		records: $resource('/resources/records/:bassin',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		create: $resource('/resources/records/record',{},{
			query:{method:'POST', isArray: true, params: {}}
		}),
	};
}]);