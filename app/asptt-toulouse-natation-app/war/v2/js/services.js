var loadingAppServices = angular.module('loadingAppServices', ['ngResource']);

loadingAppServices.factory('LoadingApp', ['$resource', 
                                       function($resource) {
	return $resource('/resources/loading',{},{
        query:{
            method:'GET',
            isArray:true
        }});
}]);

var pageServices = angular.module('pageServices', ['ngResource']);

pageServices.factory('PageService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/page/:pageId',{},{
        query:{
            method:'GET',
            params: {pageId: "@pageId"},
            isArray:true
        }});
}]);