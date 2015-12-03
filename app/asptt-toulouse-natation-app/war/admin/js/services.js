angular.module('authorizationService', [])
.service("AuthorizationService", function($rootScope, $http) {
    return {
        isConnected: function(credentials) {
        	/**var headers = credentials ? {authorization : "Basic "
    	        + btoa(credentials.email + ":" + credentials.password)
    	    } : {};
        	$http.get('/resources/authentication/isAuthenticated', {headers : headers}).success(function(data) {
        		$rootScope.authenticated = data.logged;
        		$rootScope.authenticatDetails = data;
        		return $rootScope.authenticated;
        	});**/
        	return true;
        },
        hasAccess: function(element, access) {
        	var token = $rootScope.aspttToken;
        	var found = false;
        	$http.get('/resources/authorization/hasAccess/' + token + '/' + access, {}).success(function(data) {
        		found = data.hasAccess;
        		if(found) {
        			element.show();
        		} else {
        			element.hide();
        		}
        	});
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