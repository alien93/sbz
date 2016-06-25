
angular.module('sbzApp')
	.controller('prodavac_racunInfoController', ['$rootScope', '$scope', '$location', 'items', '$uibModalInstance', '$cookies',
		function($rootScope, $scope, $location, items, $uibModalInstance, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};
		
			if ($rootScope.user.role != "V") {
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