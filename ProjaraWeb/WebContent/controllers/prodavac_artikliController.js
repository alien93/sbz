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
				if (index == 'na') {
					var artikl = {
						"info.id" : "",
						"info.picture" : "",
						"info.name" : "",
						"category.name" : "",
						"info.cost" : 0,
						"info.inStock" : 0,
						"info.minQuantity" : 0
					};
					$scope.artikli.push(artikl);
					index = $scope.artikli.length - 1;
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