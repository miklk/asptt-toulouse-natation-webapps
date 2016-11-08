var saisieHeureServices = angular.module('saisieHeureServices', ['ngResource']);

saisieHeureServices.factory('SaisieHeureService', ['$resource', 
                                       function($resource) {
	return {
		week: $resource('/resources/salarie/heure/',{},{
			query:{method:'GET', isArray: true,params: {}}
		}),
	};
}]);