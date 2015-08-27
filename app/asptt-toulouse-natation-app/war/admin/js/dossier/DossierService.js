var dossierServices = angular.module('dossierServices', ['ngResource']);

dossierServices.factory('DossierService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/dossiers/find',{},{
			query:{method:'GET', isArray: true, params: {q: '@query', groupe: '@groupe'}}
		}),
		findOne: $resource('/resources/dossiers/:dossier',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		remove: $resource('/resources/dossiers/:dossier',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		removeNageur: $resource('/resources/dossiers/nageur/:nageur',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		certificat: $resource('/resources/dossiers/certificat/:nageur',{nageur: '@nageur'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		relancer: $resource('/resources/dossiers/relancer/:dossier',{dossier: '@dossier'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		attente: $resource('/resources/dossiers/attente/:dossier',{dossier: '@dossier'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		statistiques: $resource('/resources/dossiers/statistiques',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		removeCertificat: $resource('/resources/dossiers/certificat/:nageur',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		facture: $resource('/resources/dossiers/facture/:dossier',{dossier: '@dossier'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
	};
}]);