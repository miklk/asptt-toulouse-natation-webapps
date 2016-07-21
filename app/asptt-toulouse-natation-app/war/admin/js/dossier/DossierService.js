var dossierServices = angular.module('dossierServices', ['ngResource']);

dossierServices.factory('DossierService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/dossiers/find',{},{
			query:{method:'GET', isArray: true, params: {q: '@query', groupe: '@groupe'}}
		}),
		list2: $resource('/resources/dossiers/find2',{},{
			query:{method:'GET', isArray: true, params: {q: '@query'}}
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
		annuler: $resource('/resources/dossiers/annuler/:nageur',{nageur: '@nageur'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		byGroupeDay: $resource('/resources/dossiers/findByGroupeDay/:groupe/:day',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
	};
}]);