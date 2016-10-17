angular.module('JeViensService', ['ngResource'])
.service("JeViensService", function($resource) {
    return {
    	findNageur: $resource('/resources/presences/:token',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		validate: $resource('/resources/presences/:token',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
    };
});