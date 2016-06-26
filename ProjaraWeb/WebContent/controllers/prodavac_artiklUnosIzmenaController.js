angular.module('sbzApp')
	.controller('prodavac_artiklUnosIzmenaController', ['$rootScope', '$scope', '$location', 'items', '$uibModalInstance', '$http', '$cookies',
		function($rootScope, $scope, $location, items, $uibModalInstance,  $http, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};	
		
			$scope.artikl = items;
			if ($scope.artikl.id == "")
				$scope.naslovnaPoruka = "Unos novog artikla";
			else
				$scope.naslovnaPoruka = "Izmena artikla";
				
			
			$scope.sveKategorije = [];
			$http({
				method: "GET", 
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategories/",
			}).then(function(value) {
				console.log("sve kategorije");
				$scope.sveKategorije = value.data;				
			});	
			
//			if ($scope.artikl.category.name == "")
//				$scope.artikl.category.name = $scope.sveKategorije[0].info.name;
			
			$scope.potvrdi = function(){
				$uibModalInstance.close();
			};
			$scope.zatvori = function(){
				$uibModalInstance.close();
			};
		}
	]);