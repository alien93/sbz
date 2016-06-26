angular.module('sbzApp')
	.controller('prodavac_artiklUnosIzmenaController', ['$rootScope', '$scope', '$location', 'items', '$uibModalInstance', '$http', '$cookies',
		function($rootScope, $scope, $location, items, $uibModalInstance,  $http, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};	
		
			$scope.original = items;
			$scope.artikl = angular.copy(items);
			$scope.izmena = false; //potrebno zbog disable-ovanja id polja, a i za razlikovanje add od update
			
			if ($scope.artikl.info.id == "") {
				$scope.naslovnaPoruka = "Unos novog artikla";
				$scope.izmena = false;
			}
			else {
				$scope.naslovnaPoruka = "Izmena artikla";
				$scope.izmena = true;
			}
				
			console.log("Izmena " + $scope.izmena);
			$scope.sveKategorije = [];
			$http({
				method: "GET", 
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategory/allCat",
			}).then(function(value) {
				console.log("sve kategorije");
				for (var i = 0; i < value.data.length; i++) {
					$scope.sveKategorije.push(value.data[i]);	
					if ($scope.artikl.category.name == $scope.sveKategorije[i].info.name) {
						$scope.artikl.category = $scope.sveKategorije[i];
					} 
				}
				
				if ($scope.artikl.category.name == "") {
					$scope.artikl.category = $scope.sveKategorije[0];
				} 
			});	
			
			
			
			$scope.potvrdi = function(){
				// TODO : rest poziv za update ili add				
				if ($scope.izmena == false) {
					$http({
						method:"POST",
						url:"http://localhost:8080/ProjaraWeb/rest/items/add",
						data:$scope.artikl,
						headers: {'Content-Type': 'application/json'}
					}).then(function(value){
						console.log("Uspjesno dodat artikl.");
						$uibModalInstance.close();
					})
				} else {
					
				}
				
			};
			$scope.zatvori = function(){
				$uibModalInstance.close();
			};
		}
	]);