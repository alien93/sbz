/**
 * 
 */
angular.module('sbzApp')
	.controller('kupac_navBarController', ['$scope', '$location', '$uibModal',
			function($scope, $location, $uibModal){
				$scope.podaciOKorisniku = function(){
					$location.path('/kupac/info');
				};
				
				 $scope.korpa = function() {
					$location.path('/kupac/korpa');
				 }
				 
				 $scope.odjava = function(){
					$location.path('/prijava');
				 }
	}])