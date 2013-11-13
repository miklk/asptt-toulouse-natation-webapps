/**
 * 
 */
var adherentsApp = angular.module('adherentsApp', ['adherentsServices']);

adherentsApp.controller('AdherentListCtrl', ['$scope', 'Adherent', function($scope, Adherent) {
	$scope.adherents = Adherent.get();
}]);