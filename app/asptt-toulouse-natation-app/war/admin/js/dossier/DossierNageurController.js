/**
 * 
 */
var dossierNageurController = angular.module('DossierNageurController', ['ngRoute', 'dossierServices', 'groupeServices']);

dossierNageurController.controller('DossierNageurController', ['$http', '$scope', '$location', '$filter', '$routeParams', 'DossierService', 'GroupeService', function($http, $scope, $location, $filter, $routeParams, DossierService, GroupeService) {
	$scope.cspList = ['Scolaire', 'Agriculteurs exploitants', 'Artisans, commerçants et chefs d\'entreprise', 'Cadres et professions intellectuelles supérieures','Professions Intermédiaires', 'Employés', 'Ouvriers', 'Retraités', 'Sans activité professionnelle'];
	
	DossierService.findOne.query({'dossier': $routeParams.dossierId}, function(data) {
		$scope.dossier = data;
		
		//Handle input date
		angular.forEach($scope.dossier.nageurs, function(nageur) {
				nageur.nageur.naissance = new Date(nageur.nageur.naissance);
		});
		$scope.dossierUpdateParameters = {
				principal: null,
				nageurs: null
		};
	});
	
	$scope.montantCalcule = function() {
		var montant = 0;
		if($scope.dossier) {
			angular.forEach($scope.dossier.nageurs, function(nageur) {
				montant+=nageur.nageur.tarif;
			});
		}
		return montant;
	}
	
	$scope.update = function() {
		$scope.dossierUpdateParameters.principal = $scope.dossier.principal;
		$scope.dossierUpdateParameters.nageurs = $scope.dossier.nageurs;
		
		$http.post("/resources/dossiers/update", $scope.dossierUpdateParameters, {})
	       .success(function(dataFromServer, status, headers, config) {
    		   alert("Dossier mis à jour avec succès.");
	       })
	        .error(function(data, status, headers, config) {
	          alert("Erreur");
	       });
	}
	
	$scope.changerGroupe = function(nageur) {
		GroupeService.all.query({}, function(data) {
			$scope.groupes = data.groups;
			var sansGroupe = {title:'Sans groupe', id:'-1'};
			$scope.groupes.push(sansGroupe);
		});
		$('#groupe-popup').modal();
	}
	
	$scope.uploadCertificat = function() {
		var formData = new FormData();
		//Récuperer les certificats
		var nageurId = 0;
		var elt = document.getElementById('certificat');
		file = elt.files[0];
			if(file != null) {
				formData.append("file", file);
				nageurId = elt.name.split("_")[1];
			}
		var responsePromise = $http.post("/resources/dossiers/uploadCertificat/" + nageurId, formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
	    .success(function(dataFromServer, status, headers, config) {
	    	$scope.uploadCertificatSuccess = true;
	       })
	        .error(function(data, status, headers, config) {
	        	$scope.uploadCertificatSuccess = false;
	          alert("Erreur lors de l'ajout du certificat médical.");
	       });
	};
}]);