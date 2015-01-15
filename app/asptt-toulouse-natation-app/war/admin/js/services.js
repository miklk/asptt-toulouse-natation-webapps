var adherentsServices = angular.module('adherentsServices', ['ngResource']);

adherentsServices.factory('AdherentsService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/adherents/',{},{
        query:{
            method:'GET',
            isArray: false,
            params: {}
        }});
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

var slotServices = angular.module('slotServices', ['ngResource']);

slotServices.factory('SlotService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/creneaux/:groupe',{},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);

var groupeServices = angular.module('groupeServices', ['ngResource']);

groupeServices.factory('GroupeService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/groupes',{nouveau: '@nouveau'},{
			query:{method:'GET', isArray: false,}
		}),
		all: $resource('/resources/groupes/all',{},{
			query:{method:'GET', isArray: false, params: {}}
		})
	};
}]);