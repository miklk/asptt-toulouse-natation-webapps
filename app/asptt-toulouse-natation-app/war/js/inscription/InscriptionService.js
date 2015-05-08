var inscriptionServices = angular.module('inscriptionServices', ['ngResource']);

inscriptionServices.factory('InscriptionService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/inscription/authenticate',{},{
        query:{
            method:'GET',
            isArray: false,
            params: {}
        }});
}]);

var inscriptionNouveauServices = angular.module('inscriptionNouveauServices', ['ngResource']);
inscriptionNouveauServices.factory('InscriptionNouveauService', ['$resource', 
                                     function($resource) {
	return $resource('/resources/nouveau/',{email: '@email'},{
      'put':{
          method:'PUT'
      },
      'get': {
    	  method: 'GET'
      }});
}]);

var removeAdherentServices = angular.module('removeAdherentServices', ['ngResource']);
removeAdherentServices.factory('RemoveAdherentService', ['$resource', 
                                     function($resource) {
	return $resource('/resources/adherents/:adherentId',{},{
      'remove':{
          method:'DELETE'
      }});
}]);