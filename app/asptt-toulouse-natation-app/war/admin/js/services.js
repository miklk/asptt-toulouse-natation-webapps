angular.module('authorizationService', [])
.service("AuthorizationService", function($rootScope, $http) {
    return {
        isConnected: function(credentials) {
        	var headers = credentials ? {authorization : "Basic "
    	        + btoa(credentials.email + ":" + credentials.password)
    	    } : {};
        	$http.get('/resources/authentication/isAuthenticated', {headers : headers}).success(function(data) {
        		$rootScope.authenticated = data.logged;
        		$rootScope.authenticatDetails = data;
        		return $rootScope.authenticated;
        	});
        	//return true;
        },
        hasAccess: function(access) {
        	var found = false;
        	var index = 0;
        	while(!found && index < $rootScope.authenticatDetails.authorizations.length) {
        		found = $rootScope.authenticatDetails.authorizations[index] == access;
        		index++;
        	}
        	return found;
        },
        signIn: function() {
            $rootScope.$broadcast("connectionStateChanged");
            return true;
        },
        signOut: function() {
            $rootScope.$broadcast("connectionStateChanged");
            return true;
        }
    };
});