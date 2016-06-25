/**
 * 
 */
angular.module('sbzApp')
	.controller('prodavac_navBarController', ['$rootScope', '$scope', '$location', '$cookies',
		function($rootScope, $scope, $location, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			}
					
		   	$scope.racuni = function(){
				$location.path('/prodavac');
			};
		   
			$scope.narudzbe = function(){
				$location.path('/prodavac/narudzbe');
			};	
		
			$scope.odjava = function(){
				$cookies.remove("prodavacID");
				$cookies.remove("prodavac");
				$location.path('/prijava');
			};				
		}
	]);