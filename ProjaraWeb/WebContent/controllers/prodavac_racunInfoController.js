
angular.module('sbzApp')
	.controller('prodavac_racunInfoController', ['$scope', 'items', '$uibModalInstance',
		function($scope, items, $uibModalInstance){
			$scope.racun = items;
			
			$scope.ok = function(){
				$uibModalInstance.close();
			};
			$scope.zatvori = function(){
				$uibModalInstance.close();
			};
		}
	]);