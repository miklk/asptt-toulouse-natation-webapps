/**
 * 
 */
var boutiqueController = angular.module('BoutiqueCtrl', ['ngRoute', 'boutiqueServices']);
boutiqueController.controller('BoutiqueCtrl', ['$rootScope', '$scope', 'BoutiqueService', function($rootScope, $scope, BoutiqueService) {
	$rootScope.isLoading = true;
	
	$scope.showFull = true;
	$scope.currentProduct = null;
	
	$scope.products = BoutiqueService.products.query(function (data) {
		$scope.products = data;
		$rootScope.isLoading = false;
	});
	
	$scope.displayPrice = function(product) {
		var price = product.price;
		if(product.price2 && product.price2 > 0) {
			price = price + ", " + product.price2;
			if(product.price3 && product.price3 > 0) {
				price = price + ", " + product.price3;
			}
		}
		return price + " euros";
	}
	
	$scope.edit = function(product) {
		if(product != null) {
			$scope.currentProduct = product;	
		} else {
			$scope.currentProduct = new Object();
		}
		
		$scope.showFull = false;
	}
	
	$scope.goBackToFull = function() {
		$scope.currentProduct = null;
		$scope.showFull = true;
	}
	
	$scope.createOrUpdate = function() {
		BoutiqueService.saveProduct.query({}, $scope.currentProduct, function(data) {
			alert("Enregistré avec succès.");
			$scope.products = BoutiqueService.products.query(function (data) {
				$scope.products = data;
				$scope.showFull = true;
				$rootScope.isLoading = false;
			});
		});
	}
}]);