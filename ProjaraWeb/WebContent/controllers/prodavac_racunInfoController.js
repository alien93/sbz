
angular.module('sbzApp')
	.controller('prodavac_racunInfoController', ['$rootScope', '$scope', '$location', 'items', '$uibModalInstance',
		function($rootScope, $scope, $location, items, $uibModalInstance){
		
			if ($rootScope.user.role != "PRODAVAC") {
				$location.path('/prijava');
			};	
		
			$scope.racun = items;
			
			$scope.ok = function(){
				$uibModalInstance.close();
			};
			$scope.zatvori = function(){
				$uibModalInstance.close();
			};
		}
	]);