var mediaServices = angular.module('mediaServices', ['ngResource']);

mediaServices.factory('MediaService', ['$resource', 
                                       function($resource) {
	return {
		photos: $resource('/resources/media/photos',{},{
			query:{method:'GET', isArray: false, params: {}}
		})
	};
}]);