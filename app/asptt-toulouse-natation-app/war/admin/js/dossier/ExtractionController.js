/**
 * 
 */
var extractionController = angular.module('ExtractionController', ['ngRoute', 'dossierServices', 'groupeServices', 'slotServices']);

extractionController.controller('ExtractionController', ['$rootScope', '$http', '$scope', '$location', '$filter', 'DossierService', 'GroupeService', 'SlotService', function($rootScope, $http, $scope, $location, $filter, DossierService, GroupeService, SlotService) {
	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
		var sansGroupe = {title:'Sans groupe', id:'-1'};
		$scope.groupes.push(sansGroupe);
	});
	
	$scope.possibleFields = ['NOM','PRENOM', 'GROUPE', 'SHORT', 'TSHIRT', 'MAILLOT', 'PROFESSION'];
	
	$scope.selectedFields = null;
	$scope.groupe = null;
	
	$scope.extraire = function() {
		var fieldsAsString = "";
		angular.forEach($scope.selectedFields, function(selectedField, index) {
			fieldsAsString+=selectedField;
			if(index < ($scope.selectedFields.length - 1)) {
				fieldsAsString+="_";
			}
		});
		
		var groupeAsString = "";
		angular.forEach($scope.groupe, function(selectedGroupe, index) {
			groupeAsString+="groupes=" + selectedGroupe;
			if(index < ($scope.groupe.length - 1)) {
				groupeAsString+="&";
			}
		});
		var url = "/resources/dossiers/extraction/" + fieldsAsString;
		if(groupeAsString) {
			url+="?" + groupeAsString;
		}
		
		return url;
	}
}]);