/**
 * 
 */
var adherentsServices = angular.module('adherentsServices', ['ngResource']);

adherentsServices.factory('Adherent', ['$resource', 
                                       function($resource) {
	return $resource('/resources/adherents',{},{
        query:{
            method:'GET',
            isArray:true
        }});
}]);