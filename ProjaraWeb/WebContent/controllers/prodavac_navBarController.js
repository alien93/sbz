/**
 * 
 */
angular.module('sbzApp')
	.controller('prodavac_navBarController', ['$rootScope', '$scope', '$location',
		function($rootScope, $scope, $location){
		   $scope.user.username = $rootScope.user.username;
			
		   	$scope.racuni = function(){
				$location.path('/prodavac');
			};
		   
			$scope.narudzbe = function(){
				$location.path('/prodavac/narudzbe');
			};	
		
			$scope.odjava = function(){
				$location.path('/prijava');
			};				
		}
	]);