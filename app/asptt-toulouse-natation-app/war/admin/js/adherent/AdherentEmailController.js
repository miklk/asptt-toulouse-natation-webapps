/**
 * 
 */
var adherentController = angular.module('AdherentEmailCtrl', ['ngRoute', 'textAngular', 'adherentsServices', 'groupeServices', 'slotServices', 'piscineServices']);
adherentController.controller('AdherentEmailCtrl', ['$http', '$scope', '$location', '$filter', 'AdherentsService', 'GroupeService', 'SlotService', 'PiscineService', function($http, $scope, $location, $filter, AdherentsService, GroupeService, SlotService, PiscineService) {
	$scope.fichier = null;
	$scope.sender = "contact@asptt-toulouse-natation.com";
	$scope.subject = "";
	$scope.htmlcontent = "";
	$scope.destinataire = "";
	$scope.groupesSelected = 0;
	$scope.creneau = 0;
	$scope.piscine = "";
	$scope.recipient = "";
	
	
	$scope.htmlcontent = "<img src=\"https://lh3.googleusercontent.com/-G9O-07NDcNY/VBvelTpt3lI/AAAAAAAABgI/yAYJInY7jU4/w917-h69-no/logo_entete.png\" /><p>Madame, Monsieur,</p>";
	
	$scope.showCreneau = false;
	
	$scope.senderList = ['contact@asptt-toulouse-natation.com','loic.pilorget@asptt-toulouse-natation.com', 'cyril.cavagna@asptt-toulouse-natation.com', 'sebastien.borderas@asptt-toulouse-natation.com', 'remi.lacaze@asptt-toulouse-natation.com', 'elysabeth.patte@asptt-toulouse-natation.com'];

	GroupeService.all.query({}, function(data) {
		$scope.groupes = data.groups;
	});
	PiscineService.list.query({}, function(data) {
		$scope.piscines = data.piscines;
	});
	$scope.loadCreneau = function() {
		SlotService.list.query({groupe: $scope.groupesSelected}, function(data) {
			$scope.creneaux = data.creneaux;
			angular.forEach($scope.creneaux, function(creneau) {
				creneau.label = creneau.swimmingPool + ' - ' + creneau.dayOfWeek + ' - ' + $filter('date') (creneau.beginDt, 'HH:mm') + '-' + $filter('date') (creneau.endDt, 'HH:mm') + ' - (' + (creneau.placeDisponible - creneau.placeRestante) + '/' + creneau.placeDisponible + ')';
				if(creneau.second) {
					creneau.label = creneau.label + " #2";
				}
			});
			$scope.showCreneau = true;
		});
	};
	
	$scope.sendEmail = function() {
		var formData = new FormData();
		formData.append("file", $scope.fichier);
		formData.append("messageTo", $scope.recipient);
		formData.append("messageFrom", $scope.sender);
		formData.append("messageSubject", $scope.subject);
		formData.append("messageContent", $scope.htmlcontent);
		formData.append("destinataire", $scope.destinataire);
		formData.append("groupes", $scope.groupesSelected);
		formData.append("creneau", $scope.creneau);
		formData.append("piscine", $scope.piscine);
		$http.post("/resources/email", formData, {
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
	
	$scope.getRecipient = function() {
		switch($scope.destinataire) {
		case "all": $scope.recipient = "Tous les adhérents inscrits";
		break;
		case "enf": $scope.recipient = "Tous les adhérents des groupes ENF";
		break;
		case "competiteur": $scope.recipient = "Tous les adhérents des groupes compétitions";
		break;
		case "piscine": $scope.recipient = "Tous les adhérents ayant un créneau à la piscine sélectionnée";
		break;
		case "groupes": $scope.recipient = "Tous les adhérents appartenant au(x) groupe(s) sélectionné(s)";
		break;
		case "creneau": $scope.recipient = "Tous les adhérents ayant un créneau correspondant à celui sélectionné";
		break;
		default: $scope.recipient = "";
		}
	}	
}]);