/**
 * 
 */
angular.module('sbzApp')
	.controller('kupac_navBarController', ['$scope', '$location',
			function($scope, $location){
				$scope.podaciOKorisniku = function(){
					$location.path('/kupac/info');
				};
	}]);