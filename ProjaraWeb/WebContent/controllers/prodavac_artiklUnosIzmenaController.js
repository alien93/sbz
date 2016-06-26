angular.module('sbzApp')
	.controller('prodavac_artiklUnosIzmenaController', ['$rootScope', '$scope', '$location', 'items', '$uibModalInstance', '$cookies',
		function($rootScope, $scope, $location, items, $uibModalInstance, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};	
		
			$scope.artikl = items;
			if ($scope.artikl.id == "")
				$scope.naslovnaPoruka = "Unos novog artikla";
			else
				$scope.naslovnaPoruka = "Izmena artikla";
				
			
			
			
			$scope.potvrdi = function(){
				$uibModalInstance.close();
			};
			$scope.zatvori = function(){
				$uibModalInstance.close();
			};
		}
	]);