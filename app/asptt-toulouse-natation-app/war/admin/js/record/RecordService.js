var recordServices = angular.module('recordServices', ['ngResource']);

recordServices.factory('RecordService', ['$resource', 
                                       function($resource) {
	return {
		records: $resource('/resources/records/:bassin/:sexe',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		create: $resource('/resources/records/record',{},{
			query:{method:'POST', isArray: true, params: {}}
		}),
		epreuves: $resource('/resources/records/epreuves/:bassin',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
		byEpreuve: $resource('/resources/records/by-epreuve/:epreuve/:categorie',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
	};
}]);