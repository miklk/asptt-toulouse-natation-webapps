var saisieHeureServices = angular.module('saisieHeureServices', ['ngResource']);

saisieHeureServices.factory('SaisieHeureService', ['$resource', 
                                       function($resource) {
	return {
		week: $resource('/resources/salarie/heure/:week/:user',{week: '@week', user: '@user'},{
			query:{method:'GET', isArray: true,params: {}}
		}),
		valider : $resource('/resources/salarie/heure/:user',{user:'@user'},{
			query:{method:'POST', params: {}}
		}),
	};
}]);