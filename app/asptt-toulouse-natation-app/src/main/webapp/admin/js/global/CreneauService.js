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
		secondes: $resource('/resources/creneaux/:groupe',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		remove: $resource('/resources/creneaux/:creneau',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		clear: $resource('/resources/creneaux/clear/:groupe',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		all: $resource('/resources/creneaux/all',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		allPerGroup: $resource('/resources/creneaux/all-per-group',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		days: $resource('/resources/creneaux/days/:groupe',{},{
			query:{method:'GET', isArray: true, params: {}}
		})
	};
}]);