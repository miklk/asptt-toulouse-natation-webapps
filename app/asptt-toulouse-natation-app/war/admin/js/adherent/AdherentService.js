var adherentsServices = angular.module('adherentsServices', ['ngResource']);

adherentsServices.factory('AdherentsService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/adherents/',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		email: $resource('/resources/adherents/email/',{},{
			query:{method:'POST', isArray: true, params: {destinataire: '@destinataire', groupe: '@groupe', creneau: '@creneau', piscine: '@piscine'}}
		})
	};
}]);


var adherentServices = angular.module('adherentServices', ['ngResource']);

adherentServices.factory('AdherentService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/adherents/:adherent',{adherent: '@adherentId'},{
           'save':{
                method:'POST'
            },
            'get': {
            	method: 'GET',
            	isArray:false
            }
        });
}]);