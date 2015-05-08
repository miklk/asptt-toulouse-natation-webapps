var loadingAppServices = angular.module('loadingAppServices', ['ngResource']);

loadingAppServices.factory('LoadingApp', ['$resource', 
                                       function($resource) {
	return $resource('/resources/loading',{},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);

var pageServices = angular.module('pageServices', ['ngResource']);

pageServices.factory('PageService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/pages/:pageId',{},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);

var actualiteServices = angular.module('actualiteServices', ['ngResource']);

actualiteServices.factory('ActualiteService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/loading/actualites',{},{
        query:{
            method:'GET',
            isArray:true
        }});
}]);

var slotServices = angular.module('slotServices', ['ngResource']);

slotServices.factory('SlotService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/slots/:groupe',{creneaux: '@creneaux'},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);

var groupeServices = angular.module('groupeServices', ['ngResource']);

groupeServices.factory('GroupeService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/groupes',{nouveau: '@nouveau'},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);

var authenticationServices = angular.module('authenticationServices', ['ngResource']);
authenticationServices.factory('AuthenticationService', ['$resource', 
                                     function($resource) {
	return $resource('/resources/authentication/:openIdService',{},{
      'get':{
          method:'GET'
      }});
}]);

var actualiteServices = angular.module('actualiteServices', ['ngResource']);

actualiteServices.factory('ActualiteService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/actualites/list/1',{},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);