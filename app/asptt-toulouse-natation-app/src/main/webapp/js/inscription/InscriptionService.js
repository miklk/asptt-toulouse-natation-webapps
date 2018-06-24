var inscriptionServices = angular.module('inscriptionServices', ['ngResource']);

inscriptionServices.factory('InscriptionService', ['$resource', 
                                       function($resource) {
	return {
		authenticate: $resource('/resources/inscription/authenticate',{},{
			query:{method:'GET', isArray: false,params: {}}
		}),
		forget: $resource('/resources/inscription/forget',{email: '@email'},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		nouveau: $resource('/resources/inscription/nouveau',{email: '@email'},{
			query:{method:'GET', isArray: false, params: {}}
		}),
		preNouveau: $resource('/resources/inscription/pre-nouveau',{email: '@email'},{
			query:{method:'PUT', isArray: false, params: {}}
		}),
		remove: $resource('/resources/inscription/remove/:dossier',{},{
			query:{method:'DELETE', isArray: false, params: {}}
		}),
		ajouterEnfant: $resource('/resources/inscription/ajouter-enfant',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
	};
}]);