/**
 * 
 */
angular.module('sbzApp')
	.controller('menadzer_navBarController', ['$rootScope', '$scope', '$location',
			function($rootScope, $scope, $location){
			
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
				$rootScope.user = null;
				$location.path("/prijava");
			};
	}]);