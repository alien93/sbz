angular.module('sbzApp')
	.controller('prodavac_Controller', ['$scope', '$uibModal', 
		function($scope, $uibModal){
			
		 	$scope.stanjaRacuna = ["SVI RAČUNI", "NARUČENO", "USPEŠNO REALIZOVANO", "OTKAZANO"];
		 	$scope.izabranoStanje = $scope.stanjaRacuna[0];
		 	$scope.prikaziOdgovarajuce = "";
		 	
		 	//------------------ Test data --------------------------------------------------
		 	var aa1 = {"oznaka":"010", "naziv":"Pegla", "kategorija":"Mali kucni uredjaji", "popust":"", "cena":"500"};
			var aa2 = {"oznaka":"011", "naziv":"Flasa", "kategorija":"Posudje", "popust":"", "cena":"1500"};
			var aa3 = {"oznaka":"012", "naziv":"Trotinet", "kategorija":"Igracke", "popust":"Novogodisnji", "cena":"300"};
			var aa4 = {"oznaka":"013", "naziv":"Krevet", "kategorija":"Spavaca soba", "popust":"", "cena":"5000"};
			var aa5 = {"oznaka":"014", "naziv":"Prskalica", "kategorija":"Basta", "popust":"", "cena":"50000"};

		 	var artikli = [aa1, aa2, aa3, aa4, aa5];
		 	
		 	var rr1 = {"oznaka":"123", "oznakaKupca":"453", "stanje":$scope.stanjaRacuna[1], 
		 				"datum": "21-06-2016", "artikli": artikli};
		 	var rr2 = {"oznaka":"124", "oznakaKupca":"454", "stanje":$scope.stanjaRacuna[1], 
		 				"datum": "21-06-2016", "artikli": artikli};
		 	var rr3 = {"oznaka":"125", "oznakaKupca":"455", "stanje":$scope.stanjaRacuna[1], 
		 				"datum": "21-06-2016", "artikli": artikli};
		 	var rr4 = {"oznaka":"126", "oznakaKupca":"456", "stanje":$scope.stanjaRacuna[2], 
		 				"datum": "21-06-2016", "artikli": artikli};
		 	var rr5 = {"oznaka":"127", "oznakaKupca":"457", "stanje":$scope.stanjaRacuna[3],
		 				"datum": "21-06-2016", "artikli": artikli};
		 	
		 	$scope.racuni = [rr1, rr2, rr3, rr4, rr5];
		 	
		 	$scope.prikaziOdgRacune = function() {
		 		// Postavljanje promjenljive po kojoj se vrsi filtriranje po txt sadrzaju
		 		if ($scope.izabranoStanje == $scope.stanjaRacuna[0]) {
		 			$scope.prikaziOdgovarajuce = "";
		 		} else {
		 			$scope.prikaziOdgovarajuce = $scope.izabranoStanje;
		 		}		 		
		 	};
		 	
		 	$scope.showDetails = function(index) {
		 		var modalInstance = $uibModal.open({
					animation: false,
					templateUrl: 'views/prodavac_racunInfo_m.html',
					controller: 'prodavac_racunInfoController',
					size: 'lg',
					resolve: {
						items: function(){
								return $scope.racuni[index];
							},
						index: function(){
								return index;
							}
						}
			});
		 		
		 	};

		}
	]);