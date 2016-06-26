angular.module('sbzApp')
	.controller('prodavac_narudzbeController', ['$rootScope', '$scope', '$location', '$http', '$cookies',
		function($rootScope, $scope, $location, $http, $cookies){
			
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};
		 	
		 	//------------------ Test data --------------------------------------------------
//			var aa1 = {"oznaka":"010", "naziv":"Pegla", "razlog":"Nestalo", "kolicina": "50"};
//			var aa2 = {"oznaka":"011", "naziv":"Flasa", "razlog":"Nestalo", "kolicina":"100"};
//			var aa3 = {"oznaka":"012", "naziv":"Trotinet", "razlog":"Nestalo", "kolicina":"20"};
//			var aa4 = {"oznaka":"013", "naziv":"Krevet", "razlog":"Pri kraju", "kolicina":"40"};
//			var aa5 = {"oznaka":"014", "naziv":"Prskalica", "razlog":"Praznik se blizi", "kolicina":"100"};
//		 	
//		 	$scope.narudzbe = [aa1, aa2, aa3, aa4, aa5];
			$scope.narudzbe = [];
		 	$scope.zaPoruciti = [];
		 	
		 	$http({
				method: "GET", 
				url : "http://localhost:8080/ProjaraWeb/rest/items/",
			}).then(function(value) {
				console.log("narucivanje");
				for (var i = 0; i < value.data.length; i++) {
					console.log(value.data[i].info.needOrdering);
				    if (value.data[i].info.needOrdering == true) {
						var narudzba = {
							"oznaka": value.data[i].info.id,	
							"naziv": value.data[i].info.name,
							"razlog": value.data[i].info.minQuantity
						};
						$scope.narudzbe.push(narudzba);
					}
				};
				
			});	
		 	
		 	//Inicijalizacija stanja, svi su na inicijalno oznaceni sa true
		 	$scope.azurirajCheckbox = function() {
		 		for (var i = 0; i < $scope.narudzbe.length; i++) {
		 			$scope.zaPoruciti.push(true);
		 		};		 		
		 	};
		 	
		 	$scope.azurirajCheckbox();
		 	
		 	// Binding - ng-checked za svaki checkbox
		 	$scope.checkBoxArtikl = function(ind) {
		 		return $scope.zaPoruciti[ind];
		 	};
		 	
		 	// Klik na checkbox
		 	$scope.stateChanged = function(ind) {
		 		$scope.zaPoruciti[ind] = !$scope.zaPoruciti[ind];
		 	};
		 	
		 	$scope.poruciArtikle = function() {
		 		//Provjera stanja checkbox-ova, ispis u labeli ispod buttona
		 		$scope.ispis = "";
		 		for (var i = 0; i < $scope.narudzbe.length; i++) {
//		 			$scope.ispis += $scope.zaPoruciti[i];
//		 			$scope.ispis += ' ';
		 			
		 			if ($scope.zaPoruciti[i] == true) {
		 				$http({
		 					method: "POST", 
							url : "http://localhost:8080/ProjaraWeb/rest/items/orderItems",
		 				}).then(function(value) {
							console.log("Narucivanje artikla " + $scope.narudzbe[i].oznaka + " uspjesno.");
										
						});
		 			};
		 			// TODO : Obrisati iz liste ove narucene
		 			
		 		};
		 	};

		}
	]);