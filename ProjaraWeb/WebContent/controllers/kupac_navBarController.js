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
					var modalInstance = $uibModal.open({
							backdrop: 'static',
							keyboard: false,
							animation: true,
							templateUrl: 'views/kupac_unosBodova_m.html',
							controller: 'kupac_unosBodovaController',
							resolve:{
							}
						});
				 }
	}])
	
	.controller('kupac_unosBodovaController', ['$scope', '$uibModalInstance', '$location',
	         function($scope, $uibModalInstance, $location){
					$scope.potvrdi = function(){
						$location.path('/kupac/korpa');
						$uibModalInstance.close();
					}
	}]);