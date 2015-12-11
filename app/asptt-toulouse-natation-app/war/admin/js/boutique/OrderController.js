/**
 * 
 */
var orderController = angular.module('OrderController', ['ngRoute', 'boutiqueServices']);
orderController.controller('OrderController', ['$rootScope', '$scope', 'BoutiqueService', function($rootScope, $scope, BoutiqueService) {
	$rootScope.isLoading = true;
	
	BoutiqueService.orders.query(function (data) {
		$scope.orders = data;
		$rootScope.isLoading = false;
	});
	
	$scope.displayMontant = function(order) {
		var tarif = 0;
		var count = 0;
		angular.forEach(order.produits, function(produit) {
			count = count + produit.second;
			if(count < 3) {
				tarif = tarif + (produit.first.price * produit.second);
			} else if(count >= 3 && count < 5) {
				tarif = tarif + (produit.first.price2 * produit.second);
			} else {
				tarif = tarif + (produit.first.price3 * produit.second);
			}
		});
		return tarif;
	}
}]);