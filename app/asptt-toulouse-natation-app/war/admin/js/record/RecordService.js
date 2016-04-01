var recordServices = angular.module('recordServices', ['ngResource']);

recordServices.factory('RecordService', ['$resource', 
                                       function($resource) {
	return {
		areas: $resource('/resources/page-edition/area/all',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
	};
}]);