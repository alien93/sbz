angular.module('sbzApp')
	.controller('prodavac_Controller', ['$rootScope', '$scope', '$location', '$http', '$uibModal', '$cookies', 
		function($rootScope, $scope, $location, $http, $uibModal, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};	
			
		 	$scope.stanjaRacuna = ["SVI RAČUNI", "NARUČENO", "USPEŠNO REALIZOVANO", "OTKAZANO"];
		 	$scope.izabranoStanje = $scope.stanjaRacuna[0];
		 	$scope.prikaziOdgovarajuce = "";
		 	
		 	//------------------ Test data --------------------------------------------------
//		 	var aa1 = {"oznaka":"010", "naziv":"Pegla", "kategorija":"Mali kucni uredjaji", "popust":"", "cena":"500"};
//			var aa2 = {"oznaka":"011", "naziv":"Flasa", "kategorija":"Posudje", "popust":"", "cena":"1500"};
//			var aa3 = {"oznaka":"012", "naziv":"Trotinet", "kategorija":"Igracke", "popust":"Novogodisnji", "cena":"300"};
//			var aa4 = {"oznaka":"013", "naziv":"Krevet", "kategorija":"Spavaca soba", "popust":"", "cena":"5000"};
//			var aa5 = {"oznaka":"014", "naziv":"Prskalica", "kategorija":"Basta", "popust":"", "cena":"50000"};
//
//		 	var artikli = [aa1, aa2, aa3, aa4, aa5];
//		 	
//		 	var popust1 = {
//					"oznaka":"548",
//					"naziv":"Novogodisnji popust",
//					"opis":"50% popusta na jelke",
//					"artikli":[aa4, aa2, aa5]
//			};
//			
//			var popust2 = {
//					"oznaka":"100",
//					"naziv":"Popust za skolarce",
//					"opis":"60% na skolski pribor",
//					"artikli":[aa1, aa3]
//			};
//		 	
//		 	var racun1 = {
//					"oznaka":"123", 
//					"datum":"23/12/2015", 
//					"ukupanIznos":"50000",
//					"oznakaKupca":"56487", 
//					"stanje": $scope.stanjaRacuna[1], 
//					"artikli": artikli,
//					"popusti":[popust1, popust2],
//					"suma":"25666",
//					"popust":"30",
//					"ukupnaSuma":"20000",
//					"iskorisceniBodovi":"0",
//					"steceniBodovi":"20"
//					
//		 		};
//
//		 	var racun2 = {
//		 			"oznaka":"325", 
//		 			"datum":"15/12/2015", 
//		 			"ukupanIznos":"25000",
//		 			"oznakaKupca":"36000", 
//		 			"stanje": $scope.stanjaRacuna[2], 
//		 			"artikli": artikli,
//		 			"popusti":[popust2],
//		 			"suma":"35000",
//		 			"popust":"50",
//		 			"ukupnaSuma":"16000",
//		 			"iskorisceniBodovi":"7",
//		 			"steceniBodovi":"17"
//		 		};
//		 	
//		 	var racun3 = {
//		 			"oznaka":"445", 
//		 			"datum":"15/12/2015", 
//		 			"ukupanIznos":"25000",
//		 			"oznakaKupca":"36000", 
//		 			"stanje": $scope.stanjaRacuna[3], 
//		 			"artikli": artikli,
//		 			"popusti":[popust1],
//		 			"suma":"35000",
//		 			"popust":"50",
//		 			"ukupnaSuma":"16000",
//		 			"iskorisceniBodovi":"7",
//		 			"steceniBodovi":"17"
//		 		};
//	
//		 	$scope.racuni = [racun1, racun2, racun3];  
		 	
		 	
		 	$scope.getAllBills = function() {	 	
		 		$scope.racuni = [];
		 	$http({
				method: "GET", 
				url : "http://localhost:8080/ProjaraWeb/rest/bills",
			}).then(function(value) {
				
				for (var i = 0; i < value.data.length; i++) {
					console.log(value.data[i]);
					
					var stanje = "";
					if (value.data[i].state == "O") 
						stanje = "NARUČENO";
					else if (value.data[i].state == "C") 
						stanje = "OTKAZANO";
					else if (value.data[i].state == "S") 
						stanje = "USPEŠNO REALIZOVANO";
					
					//Lista artikala
					var artikli = [];
					for (var j = 0; j < value.data[i].billItems.length; j++) {
						var artiklIzJsona = value.data[i].billItems[j];
						var artikl = {
							"oznaka" : artiklIzJsona.item.id,
							"naziv" : artiklIzJsona.item.name,
							"kolicina" : artiklIzJsona.quantity,
							"cena" :  artiklIzJsona.item.cost * artiklIzJsona.quantity,
							"originalnaCena" : artiklIzJsona.originalCost,
							"popust" : artiklIzJsona.discountPercentage,
							"ukupno" : artiklIzJsona.totalCost
						};
						
						artikli.push(artikl);
					};
					
					//Lista popusta
					var popusti = [];
					for (var j = 0; j < value.data[i].billDiscounts.length; j++) {
						var popustiZaItem = value.data[i].billDiscounts[j];
						var popust = {
							"oznaka" : popustiZaItem.id,
							"naziv" : popustiZaItem.name,
							"procenat" : popustiZaItem.percentage,
							"tip" : popustiZaItem.type
						};
						
						popusti.push(popust);
					};
					
					var racun = {
	 						"oznaka" : value.data[i].billId,
	 						"oznakaKupca" : value.data[i].customer.id,
	 						"stanje" : stanje,
	 						"datum" : value.data[i].date,
	 						"ukupanIznos" : value.data[i].originalTotal,
	 						"popusti" : popusti,
	 						"artikli" : artikli,
	 						"suma" : value.data[i].originalTotal,
	 						"popust" : value.data[i].costInfos[0].discount,
	 						"ukupnaSuma" : value.data[i].costInfos[0].total,
	 						"iskorisceniBodovi": value.data[i].costInfos[0].spentPoints,
	 						"steceniBodovi": value.data[i].costInfos[0].awardPoints	
	 				};
					
					$scope.racuni.push(racun);
				}
			});	
		 	};
		 	
		 	$scope.getAllBills();
		 			 	
		 	$scope.prikaziOdgRacune = function() {
		 		// Postavljanje promjenljive po kojoj se vrsi filtriranje po txt sadrzaju
		 		if ($scope.izabranoStanje == $scope.stanjaRacuna[0]) {
		 			//$scope.prikaziOdgovarajuce = "";
		 			$scope.getAllBills();
		 		} else { 
		 			//$scope.prikaziOdgovarajuce = $scope.izabranoStanje;
		 			var izStanje = "";
					if ($scope.izabranoStanje == "NARUČENO") 
						izStanje = "O";
					else if ($scope.izabranoStanje == "OTKAZANO") 
						izStanje = "C";
					else if ($scope.izabranoStanje == "USPEŠNO REALIZOVANO") 
						izStanje = "S";
					
		 			$scope.getByState(izStanje);
		 		}	
		 	};
		 	
		 	$scope.potvrdiRacun = function(ind) {
		 		var billId = $scope.racuni[ind].oznaka;
		 		$http({
					method: "GET", 
					url : "http://localhost:8080/ProjaraWeb/rest/bills/approve/" + billId,
				}).then(function(value) {
					console.log("potvrda racuna");
					$scope.racuni[ind].stanje = "USPEŠNO REALIZOVANO";
				}, function(error) {
		 			console.log(error);
		 			console.log("Racun nije moguce uspjesno realizovati.");
		 			$scope.racuni[ind].stanje = "OTKAZANO";
		 		});
		 	};
		 	
		 	$scope.otkaziRacun = function(ind) {
		 		var billId = $scope.racuni[ind].oznaka;
		 		$http({
					method: "POST", 
					url : "http://localhost:8080/ProjaraWeb/rest/bills/cancel/" + billId,
				}).then(function(value) {
					console.log("otkazivanje racuna");
					$scope.racuni[ind].stanje = "OTKAZANO";
				}, function(error) {
		 			console.log(error);
		 		});			 		
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
		 	
		 	
		 	$scope.getByState = function(izStanje) {	 	
		 		$scope.racuni = [];
		 		$http({
		 			method: "GET", 
		 			url : "http://localhost:8080/ProjaraWeb/rest/bills/" + izStanje,
		 		}).then(function(value) {
				
		 			for (var i = 0; i < value.data.length; i++) {
		 				console.log(value.data[i]);
					
		 				var stanje = "";
		 				if (value.data[i].state == "O") 
		 					stanje = "NARUČENO";
		 				else if (value.data[i].state == "C") 
		 					stanje = "OTKAZANO";
		 				else if (value.data[i].state == "S") 
		 					stanje = "USPEŠNO REALIZOVANO";
					
		 				//Lista artikala
		 				var artikli = [];
		 				for (var j = 0; j < value.data[i].billItems.length; j++) {
		 					var artiklIzJsona = value.data[i].billItems[j];
		 					var artikl = {
									"oznaka" : artiklIzJsona.item.id,
									"naziv" : artiklIzJsona.item.name,
									"kolicina" : artiklIzJsona.quantity,
									"cena" :  artiklIzJsona.item.cost * artiklIzJsona.quantity,
									"originalnaCena" : artiklIzJsona.originalCost,
									"popust" : artiklIzJsona.discountPercentage,
									"ukupno" : artiklIzJsona.totalCost
								};
						
		 					artikli.push(artikl);
		 				};
					
		 				//Lista popusta
		 				var popusti = [];
		 				for (var j = 0; j < value.data[i].billDiscounts.length; j++) {
		 					var popustiZaItem = value.data[i].billDiscounts[j];
		 					var popust = {
		 							"oznaka" : popustiZaItem.id,
		 							"naziv" : popustiZaItem.name,
		 							"procenat" : popustiZaItem.percentage,
		 							"tip" : popustiZaItem.type
		 					};
						
		 					popusti.push(popust);
		 				};
					
		 				var racun = {
		 						"oznaka" : value.data[i].billId,
		 						"oznakaKupca" : value.data[i].customer.id,
		 						"stanje" : stanje,
		 						"datum" : value.data[i].date,
		 						"ukupanIznos" : value.data[i].originalTotal,
		 						"popusti" : popusti,
		 						"artikli" : artikli,
		 						"suma" : value.data[i].originalTotal,
		 						"popust" : value.data[i].costInfos[0].discount,
		 						"ukupnaSuma" : value.data[i].costInfos[0].total,
		 						"iskorisceniBodovi": value.data[i].costInfos[0].spentPoints,
		 						"steceniBodovi": value.data[i].costInfos[0].awardPoints	
		 				};
					
		 				$scope.racuni.push(racun);
		 			}
		 		});	
		 		};

		}
	]);