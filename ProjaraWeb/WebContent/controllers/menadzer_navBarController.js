/**
 * 
 */
angular.module('sbzApp')
	.controller('menadzer_navBarController', ['$scope', '$location',
			function($scope, $location){
			
			$scope.kupci = function(){
				$location.path('/menadzer/kupci');
			};
			
			$scope.artikli = function(){
				$location.path('/menadzer/artikli');
			};
			
			$scope.akcije = function(){
				$location.path('/menadzer/akcije');
			};
	}]);