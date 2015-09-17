angular.module('authorizationService', [])
.service("AuthorizationService", function($rootScope, $http) {
    return {
        isConnected: function() {
            return true;
        },
        hasAccess: function(access) {
        	return true;
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