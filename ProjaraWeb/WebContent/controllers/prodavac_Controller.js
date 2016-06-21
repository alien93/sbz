angular.module('sbzApp')
	.controller('prodavac_Controller', ['$scope', '$uibModal', 
		function($scope, $uibModal){
			
		 	$scope.statusiRacuna = ["SVI RAČUNI", "NARUČENO", "USPEŠNO REALIZOVANO", "OTKAZANO"];
		 	$scope.izabraniStatus = $scope.statusiRacuna[0];
		 	$scope.prikaziOdgovarajuce = "";
		 	
		 	//------------------ Test data --------------------------------------------------
		 	var nn1 = {"orderID":"123", "customerID":"453", "status":$scope.statusiRacuna[1]};
		 	var nn2 = {"orderID":"124", "customerID":"454", "status":$scope.statusiRacuna[1]};
		 	var nn3 = {"orderID":"125", "customerID":"455", "status":$scope.statusiRacuna[1]};
		 	var nn4 = {"orderID":"126", "customerID":"456", "status":$scope.statusiRacuna[2]};
		 	var nn5 = {"orderID":"127", "customerID":"457", "status":$scope.statusiRacuna[3]};
		 	
		 	$scope.narudzbe = [nn1, nn2, nn3, nn4, nn5];
		 	$scope.prikaziOdgRacune = function() {
		 		// Postavljanje promjenljive po kojoj se vrsi filtriranje po txt sadrzaju
		 		if ($scope.izabraniStatus == $scope.statusiRacuna[0]) {
		 			$scope.prikaziOdgovarajuce = "";
		 		} else {
		 			$scope.prikaziOdgovarajuce = $scope.izabraniStatus;
		 		}		 		
		 	};
		 	
		 	$scope.showDetails = function(index) {
		 		var modalInstance = $uibModal.open({
					animation: false,
					templateUrl: 'views/prodavac_racunInfo_m.html',
					controller: 'prodavac_racunInfoController',
					resolve: {
						items: function(){
								return $scope.narudzbe[index];
							},
						index: function(){
								return index;
							}
						}
			});
		 		
		 	};

		}
	]);