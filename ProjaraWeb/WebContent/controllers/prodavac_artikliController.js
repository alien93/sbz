angular.module('sbzApp')
	.controller('prodavac_narudzbeController', ['$rootScope', '$scope', '$location', '$http', '$cookies',
		function($rootScope, $scope, $location, $http, $cookies){
			 
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


		}
	]);