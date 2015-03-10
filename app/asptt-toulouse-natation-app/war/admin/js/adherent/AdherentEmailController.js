/**
 * 
 */
var adherentController = angular.module('AdherentEmailCtrl', ['ngRoute', 'textAngular', 'adherentsServices', 'groupeServices', 'slotServices', 'piscineServices']);
adherentController.controller('AdherentEmailCtrl', ['$http', '$scope', '$location', 'AdherentsService', 'GroupeService', 'SlotService', 'PiscineService', function($http, $scope, $location, AdherentsService, GroupeService, SlotService, PiscineService) {
	$scope.fichier = null;
	$scope.subject = "";
	$scope.htmlcontent = "";
	$scope.destinataire = "";
	$scope.groupesSelected = 0;
	$scope.creneau = 0;
	$scope.piscine = "";
	
	
	$scope.htmlcontent = "<img src=\"https://lh3.googleusercontent.com/-G9O-07NDcNY/VBvelTpt3lI/AAAAAAAABgI/yAYJInY7jU4/w917-h69-no/logo_entete.png\" /><p>Madame, Monsieur,</p>";
	
	$scope.showCreneau = false;

	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	});
	PiscineService.list.query({}, function(data) {
		$scope.piscines = data.piscines;
	});
	$scope.loadCreneau = function() {
		SlotService.get({groupe: $scope.groupesSelected}, function(data) {
			$scope.creneaux = data.creneaux;
			angular.forEach($scope.creneaux, function(creneau) {
				creneau.label = creneau.dayOfWeek + ' - ' + creneau.swimmingPool + ' - ' + creneau.beginStr + '-' + creneau.endStr; 
			});
			$scope.showCreneau = true;
		});
	};
	
	$scope.sendEmail = function() {
		var formData = new FormData();
		formData.append("file", $scope.fichier);
		formData.append("messageSubject", $scope.subject);
		formData.append("messageContent", $scope.htmlcontent);
		formData.append("destinataire", $scope.destinataire);
		formData.append("groupes", $scope.groupesSelected);
		formData.append("creneau", $scope.creneau);
		formData.append("piscine", $scope.piscine);
		$http.post("/resources/adherents/email", formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).
        success(function (data, status, headers, config) {
        	$scope.adherents = data;
			window.alert("E-mail envoyé à " + data.length + " adhérents.");
        }).
        error(function (data, status, headers, config) {
			window.alert("Erreur lors de l'envoi.");
        });
	};
	
	$scope.upload = function() {
		var file = null;
		if(files == null) {
			var elt = document.getElementById('fileUploadInput');
			file = elt.files[0]
		} else {
			file = files[0];
		}
		$scope.fichier = file;
	}
	
}]);