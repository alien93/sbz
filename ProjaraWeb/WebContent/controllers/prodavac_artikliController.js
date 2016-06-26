angular.module('sbzApp')
	.controller('prodavac_artikliController', ['$rootScope', '$scope', '$location', '$http', '$uibModal', '$cookies',
		function($rootScope, $scope, $location, $http, $uibModal, $cookies){
			 
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};
			
			$scope.artikli = [];
			$http({
				method: "GET", 
				url : "http://localhost:8080/ProjaraWeb/rest/items/",
			}).then(function(value) {
				console.log("svi artikli");
				$scope.artikli = value.data;				
			});	
			
			
			$scope.artiklModal = function(index) {
				console.log('Novi unos/izmjena');
				$scope.noviArtikl = {};
				if (index == 'na') {
					$scope.noviArtikl = {
						"info.id" : "",
						"info.picture" : "",
						"info.name" : "",
						"category.name" : "",
						"info.cost" : 0,
						"info.inStock" : 0,
						"info.minQuantity" : 0
					};
					$scope.artikli.push($scope.noviArtikl);
					index = $scope.artikli.length - 1;
				} else {
					$scope.noviArtikl = {
							"info.id" :  $scope.artikli[index].info.id,
							"info.picture" : $scope.artikli[index].info.picture,
							"info.name" : $scope.artikli[index].info.name,
							"category.name" : $scope.artikli[index].category.name,
							"info.cost" : $scope.artikli[index].info.cost,
							"info.inStock" : $scope.artikli[index].info.inStock,
							"info.minQuantity" : $scope.artikli[index].info.minQuantity	
					};
				}
					
		 		var modalInstance = $uibModal.open({
					animation: false,
					templateUrl: 'views/prodavac_artiklUnosIzmena_m.html',
					controller: 'prodavac_artiklUnosIzmenaController',
					resolve: {
						items: function(){
								return $scope.artikli[index];
							},
						index: function(){
								return index;
							}
						}
		 		});
		 		
		 	};


		}
	]);