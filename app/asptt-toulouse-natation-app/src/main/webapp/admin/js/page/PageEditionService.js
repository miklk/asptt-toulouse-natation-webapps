var pageEdtionServices = angular.module('pageEditionServices', ['ngResource']);

pageEdtionServices.factory('PageEditionService', ['$resource', 
                                       function($resource) {
	return {
		areas: $resource('/resources/page-edition/area/all',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		saveArea: $resource('/resources/page-edition/area/save',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
		findPages: $resource('/resources/page-edition/area/pages/:areaId',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		savePage: $resource('/resources/page-edition/page/save/:areaId',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
	};
}]);