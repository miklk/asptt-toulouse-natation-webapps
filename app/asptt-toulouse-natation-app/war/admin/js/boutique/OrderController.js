/**
 * 
 */
var orderController = angular.module('OrderController', ['ngRoute', 'boutiqueServices']);
orderController.controller('OrderController', ['$rootScope', '$scope', 'BoutiqueService', function($rootScope, $scope, BoutiqueService) {
	$rootScope.isLoading = true;
	$scope.displayOrder = false;
	$scope.currentOrder = new Object();
	$scope.productList = null;
	
	BoutiqueService.orders.query(function (data) {
		$scope.orders = data;
		$rootScope.isLoading = false;
	});
	
	$scope.displayMontant = function(order) {
		var tarif = 0;
		var count = 0;
		angular.forEach(order.produits, function(produit) {
			count = count + produit.second;
			if(count < 2) {
				tarif = tarif + (produit.first.price * produit.second);
			} else if(count >= 2 && count < 5) {
				tarif = tarif + (produit.first.price2 * produit.second);
			} else {
				tarif = tarif + (produit.first.price3 * produit.second);
			}
		});
		return tarif;
	}
	
	$scope.editOrder = function(order) {
		if($scope.productList == null) {
			$rootScope.isLoading = true;
			BoutiqueService.products.query(function (data) {
				$scope.productList = data;
				$rootScope.isLoading = false;
			});
		}
		$scope.displayOrder = true;
		$scope.currentOrder = order;
	}
	
	$scope.createOrUpdate = function() {
		BoutiqueService.saveOrder.query({}, $scope.currentOrder, function(data) {
			alert("Enregistré avec succès.");
			BoutiqueService.orders.query(function (data) {
				$scope.orders = data;
				$scope.currentOrder = null;
				$scope.displayOrder = false;
				$rootScope.isLoading = false;
			});
		});
	}
	
	$scope.deleteOrder = function(order) {
		BoutiqueService.deleteOrder.query({'order' : order.id}, function(data) {
			alert("Supprimé avec succès.");
			BoutiqueService.orders.query(function (data) {
				$scope.orders = data;
				$scope.currentOrder = null;
				$scope.displayOrder = false;
				$rootScope.isLoading = false;
			});
		});
	}
	
	$scope.deleteOrderProduct = function(order, product) {
		BoutiqueService.deleteOrderProduct.query({'order' : order.id, 'product': product.id}, function(data) {
			alert("Supprimé avec succès.");
			BoutiqueService.orders.query(function (data) {
				$scope.orders = data;
				$scope.currentOrder = null;
				$scope.displayOrder = false;
				$rootScope.isLoading = false;
			});
		});
	}
}]);