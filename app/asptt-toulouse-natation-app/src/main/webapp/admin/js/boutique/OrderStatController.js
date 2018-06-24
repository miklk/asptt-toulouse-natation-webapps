/**
 * 
 */
var orderStatController = angular.module('OrderStatController', ['ngRoute', 'boutiqueServices']);
orderStatController.controller('OrderStatController', ['$rootScope', '$scope', '$q', 'BoutiqueService', function($rootScope, $scope, $q, BoutiqueService) {
	$rootScope.isLoading = true;
	
	let promises = {
		products : BoutiqueService.products.query(function (data) {
			$scope.products = new Map();
			angular.forEach(data, function(product) {
				$scope.products[product.id] = product;
			});
		}).$promise,

		orders : BoutiqueService.orders.query(function (data) {
			$scope.orders = data;
			
		}).$promise
	};
	
	$q.all(promises).then(function(data) {
		$scope.buildOrders($scope.orders);
		$rootScope.isLoading = false;
	});
	
	
	$scope.buildOrders = function(orders) {
		var tarif = 0;
		var count = 0;
		var panier = new Map();
		var parProduits = new Map();
		angular.forEach(orders, function(order) {
			var countParOrder = 0;
			angular.forEach(order.produits, function(produit) {
				if(parProduits.has(produit.first.id)) {
					parProduits.set(produit.first.id, parProduits.get(produit.first.id) + 1);
				} else {
					parProduits.set(produit.first.id, 1);
				}
				countParOrder = countParOrder + produit.second;
				if(countParOrder < 2) {
					tarif = tarif + (produit.first.price * produit.second);
				} else if(countParOrder >= 2 && countParOrder < 5) {
					tarif = tarif + (produit.first.price2 * produit.second);
				} else {
					tarif = tarif + (produit.first.price3 * produit.second);
				}
			});
			count = count + countParOrder;
			if(panier.has(countParOrder)) {
				panier.set(countParOrder, panier.get(countParOrder) + 1);
			} else {
				panier.set(countParOrder, 1);
			}
		});
		$scope.nbOrder = count;
		$scope.nbOrderPrice = tarif;
		var maxPanier = 0;
		var panierMoyen = 0;
		for(var [key, value] of panier) {
			if(maxPanier < value) {
				maxPanier = value;
				panierMoyen = key;
			}
		}
		$scope.panier = panierMoyen;
		$scope.panierPrice = 5 + (4 * (panierMoyen - 1));
		$scope.parProduits = Array.from(parProduits);
		console.log($scope.parProduits);
		$scope.parProduits.sort(function(a, b) {
			var v1 = a[1];
			var v2 = b[1];
			var res = 0
			if(v1 < v2) {
				res = -1;
			} else if(v1 > v2) {
				res = 1;
			}
			return res * -1;
		});
	}
	
}]);