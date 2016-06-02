var boutiqueServices = angular.module('inscriptionStageServices', ['ngResource']);

boutiqueServices.factory('InscriptionStageService', ['$resource', 
                                       function($resource) {
	return {
		inscrire: $resource('/resources/inscription-stage/inscrire',{},{
			query:{method:'POST', isArray: false, params: {}}
		}),
	};
}]);