var adherentsStatServices = angular.module('adherentsStatServices', ['ngResource']);

adherentsStatServices.factory('AdherentsStatService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/adherentsStat/',{},{
        query:{
            method:'GET',
            isArray: false,
            params: {}
        }});
}]);