/**
 * 
 */
angular.module('sbzApp')
	.controller('kupac_navBarController', ['$scope', '$location', '$uibModal',
			function($scope, $location, $uibModal){
				$scope.podaciOKorisniku = function(){
					$location.path('/kupac/info');
				};
				
				 $scope.korpa = function(ev) {
						$location.path('/kupac/korpa');
				 }
	}])