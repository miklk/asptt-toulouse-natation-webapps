var adherentsServices = angular.module('adherentsServices', ['ngResource']);

adherentsServices.factory('AdherentsService', ['$resource', 
                                       function($resource) {
	return {
		email: $resource('/resources/adherents/email/',{},{
			query:{method:'POST', isArray: true, params: {destinataire: '@destinataire', groupe: '@groupe', creneau: '@creneau', piscine: '@piscine'}}
		}),
		initEmail: $resource('/resources/email/initEmail/',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		findEmail: $resource('/resources/email/findEmail/:value',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
	};
}]);