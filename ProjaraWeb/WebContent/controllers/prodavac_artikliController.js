angular.module('sbzApp')
	.controller('prodavac_artikliController', ['$rootScope', '$scope', '$location', '$http', '$uibModal', '$cookies', 
		function($rootScope, $scope, $location, $http, $uibModal, $cookies){
			 
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};
			
			$scope.ucitajSveArtikle = function() {
				$scope.artikli = [];
				$http({
					method: "GET", 
					url : "http://localhost:8080/ProjaraWeb/rest/items/",
				}).then(function(value) {
					for (var i = 0; i<value.data.length; i++)
						$scope.artikli.push(value.data[i]);	
				}, function(error) {
		 			console.log(error);
		 		});
			};
			
			$scope.ucitajSveArtikle();
			
			$scope.moguceBrisanje = function(index) {
				return !$scope.artikli[index].info.active;
			};			
			
			$scope.artiklModal = function(index) {
				console.log('Novi unos/izmjena');
				$scope.noviArtikl = {};
				if (index == 'na') {
					$scope.noviArtikl = {
						"info": {
							    "id" : "",
							    "picture" : "defaultImage.png",
							    "name" : "",
							    "cost" : 0,
							    "inStock" : 0,
							    "minQuantity" : 0,
							    "active" : false
						},
						"category" : {"code":"", "name":"", "maxDiscount":""},
					};
					$scope.artikli.push($scope.noviArtikl);
					index = $scope.artikli.length - 1;
				} 
				
		 		var modalInstance = $uibModal.open({
					animation: false,
					templateUrl: 'views/prodavac_artiklUnosIzmena_m.html',
					controller: 'prodavac_artiklUnosIzmenaController',
					backdrop  : 'static',
					keyboard  : false,
					resolve: {
						items: function(){
								return $scope.artikli[index];
							},
							index: function(){
								return index;
							},
							parentScope : function(){
					    		  return $scope;
					    	}
						}
		 		});
		 		
		 	};

		 	$scope.obrisiArtikl = function(index) {
		 		$http({  
	                method: "DELETE", 
	                url: 'http://localhost:8080/ProjaraWeb/rest/items/' + $scope.artikli[index].info.id
		 		}).then(function(res) {
		 			console.log(res.data);
		 			
		 			//Azuriranje reda u tabeli artikala
		 			$http({
						method: "GET", 
						url : "http://localhost:8080/ProjaraWeb/rest/items/" + $scope.artikli[index].info.id,
					}).then(function(value) {
						$scope.artikli[index] = value.data;
					});	
		 			
		 		}, function(error) {
		 			console.log(error);
		 		});
		 	};
		 	
		 	$scope.refreshReda = function(index, artikl) {
		 		$http({
					method: "GET", 
					url : "http://localhost:8080/ProjaraWeb/rest/items/" + artikl.info.id,
				}).then(function(value) {
					console.log(value.data);

			 		$scope.artikli.splice(index, 1);
			 		$scope.artikli.push(value.data);
					
				}, function(reason){
					console.log(JSON.stringify(reason));
				});
		 	};
		 	
		 	$scope.ukloniRed = function(index) {
		 		$scope.artikli.splice(index, 1);		
		 	};
		 	
		 	$scope.ucitajPonovo = function() {
		 		$scope.artikli.splice(0, $scope.artikli.length);
		 		$scope.ucitajSveArtikle();
		 	};


		}
	]);