/**
 * 
 */
angular.module('sbzApp')
	.controller('menadzer_navBarController', ['$rootScope', '$scope', '$location', '$cookies',
			function($rootScope, $scope, $location, $cookies){
		
			//pokupi oznaku ulogovanog menadzera
			if($cookies.get("menadzerID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.menadzerID = $cookies.get("menadzerID");
			}
		
		
			$scope.kupci = function(){
				$location.path('/menadzer/kupci');
			};
			
			$scope.kupci();
			
			$scope.artikli = function(){
				$location.path('/menadzer/artikli');
			};
			
			$scope.akcije = function(){
				$location.path('/menadzer/akcije');
			};
			
			$scope.logout = function(){
				$cookies.remove("menadzerID");
				$cookies.remove("korisnik");
				$location.path('/prijava');
			};
	}]);