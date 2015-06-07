var slotServices = angular.module('slotServices', ['ngResource']);

slotServices.factory('SlotService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/creneaux/:groupe',{},{
			query:{method:'GET', isArray: false,}
		}),
		byDay: $resource('/resources/slots/:groupe',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		remove: $resource('/resources/creneaux/:creneau',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		})
	};
}]);