var dossierStageServices = angular.module('dossierStageServices', ['ngResource']);

dossierStageServices.factory('DossierStageService', ['$resource', 
                                       function($resource) {
	return {
		list: $resource('/resources/dossiers-stage/find',{},{
			query:{method:'GET', isArray: true, params: {q: '@query', groupe: '@groupe'}}
		}),
		findOne: $resource('/resources/dossiers-stage/:dossier',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		remove: $resource('/resources/dossiers-stage/:dossier',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		removeNageur: $resource('/resources/dossiers-stage/nageur/:nageur',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		certificat: $resource('/resources/dossiers-stage/certificat/:nageur',{nageur: '@nageur'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		relancer: $resource('/resources/dossiers-stage/relancer/:dossier',{dossier: '@dossier'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		attente: $resource('/resources/dossiers-stage/attente/:dossier',{dossier: '@dossier'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		statistiques: $resource('/resources/dossiers-stage/statistiques',{},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		removeCertificat: $resource('/resources/dossiers-stage/certificat/:nageur',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		facture: $resource('/resources/dossiers-stage/facture/:dossier',{dossier: '@dossier'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		annuler: $resource('/resources/dossiers-stage/annuler/:nageur',{nageur: '@nageur'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		byGroupeDay: $resource('/resources/dossiers-stage/findByGroupeDay/:groupe/:day',{},{
			query:{method:'GET', isArray: true, params: {}}
		}),
	};
}]);