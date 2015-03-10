var slotServices = angular.module('slotServices', ['ngResource']);

slotServices.factory('SlotService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/creneaux/:groupe',{},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);