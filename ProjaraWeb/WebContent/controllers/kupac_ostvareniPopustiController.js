angular.module('sbzApp')
.controller('kupac_ostvareniPopustiController', ['$scope', '$location', '$cookies', '$http', '$timeout',
                                                 function($scope, $location, $cookies, $http, $timeout){

	//proveri da li je korisnik vec prijavljen
	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}
	
	$scope.uspesno = "";

	//dobavi izvestaj racuna
	if($cookies.getObject("izvestajRacuna") != undefined)
		var izvestaj = $cookies.getObject("izvestajRacuna");
	else
		$location.path("/kupac/korpa");
	console.log("izvestaj");
	console.log(izvestaj);
	$scope.izvestaj = izvestaj.data;
	
	$scope.artikli = izvestaj.data.billItems;
	console.log("artikli");
	console.log($scope.artikli);
	
	$scope.popusti = izvestaj.data.billDiscounts;
	
	$scope.ukupanPopust = [];

	
	//racunaj ukupan popust
	(function(){
		for(var i=0; i<$scope.artikli.length; i++){
			var popust = 0;
			for(var j=0; j<$scope.artikli[i].itemDiscounts.length; j++){
				popust += $scope.artikli[i].itemDiscounts[j].percentage;
			}
			$scope.ukupanPopust[i]=popust;
		}
	}());

	console.log($cookies.getObject("korpa"));
	console.log($cookies.get("bodovi"));

	$scope.otkaziKupovinu = function(){
		$location.path("/kupac");
	}
	$scope.potvrdiRacun = function(oznaka){
		console.log($scope.izvestaj.costInfos);
		switch(oznaka){
		case(1): 
			$http({
				method: "POST", 
				url : "http://localhost:8080/ProjaraWeb/rest/bills/confirm",
				data : $scope.izvestaj.costInfos[0],
				headers: {'Content-Type': 'application/json'}
			}).then(function(value) {
				if(value.statusText == "OK"){
					$cookies.remove("korpa");
					$cookies.remove("izvestajRacuna");
					$timeout(function() {
						$scope.uspesno = "Uspešno ste naručili artikle. Stanje Vašeg računa možete pratiti u istoriji kupovina.";
					}, 1500);
				}
				else{
					$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
				}
			},function(reason){
				$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
			});
			break;
		case(2): 
			$http({
				method: "POST", 
				url : "http://localhost:8080/ProjaraWeb/rest/bills/confirm",
				data : $scope.izvestaj.costInfos[1]==null?$scope.izvestaj.costInfos[0]:$scope.izvestaj.costInfos[1],
				headers: {'Content-Type': 'application/json'}
			}).then(function(value) {
				if(value.statusText == "OK"){
					$cookies.remove("korpa");
					$cookies.remove("izvestajRacuna");
					$timeout(function() {
						$scope.uspesno = "Uspešno ste naručili artikle. Stanje Vašeg računa možete pratiti u istoriji kupovina.";
					}, 1500);
				}
				else{
					$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
				}
			},function(reason){
				$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
			});
			break;
		} 
		$timeout(function() {
			$location.path("/kupac");
	     }, 1500);
	}

	$scope.povratakNaKorpu = function(){
		$location.path("/kupac/korpa");
	}
	
	$scope.otkaziKupovinu = function(){
		$cookies.remove("korpa");
		$cookies.remove("izvestajRacuna");
		
		//ukloni podatke o zapocetom racunu
		$http({
			method: "POST", 
			url : "http://localhost:8080/ProjaraWeb/rest/bills/reject/" + $scope.izvestaj.billId,
		}).then(function(value) {
			if(value.statusText == "OK"){
			}
			else{
				$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
			}
		},function(reason){
			$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
		});
		
		$location.path("/kupac");
	}
	
}]);