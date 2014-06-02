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
	return $resource('/resources/pages/:pageId',{},{
        query:{
            method:'GET',
            isArray:false
        }});
}]);

var loadingAlbumServices = angular.module('loadingAlbumServices', ['ngResource']);

loadingAlbumServices.factory('LoadingAlbumService', ['$resource', 
                                       function($resource) {
	return $resource('/resources/loading/albums',{},{
        query:{
            method:'GET',
            isArray:true
        }});
}]);