angular.module('sbzApp')
.controller('kupac_ostvareniPopustiController', ['$scope', '$location', '$cookies', '$http', '$timeout', 'KorpaService', 'IzvestajRacunaService',
                                                 function($scope, $location, $cookies, $http, $timeout, KorpaService, IzvestajRacunaService){

	$scope.uspesno = "";	//alert-success
	$scope.ukupanPopust = [];	//ukupan popust na racun

	
	/**
	 * Proveri da li je korisnik vec prijavljen
	 */
	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}
	

	/**
	 * Dobavi izvestaj racuna
	 */
	if(IzvestajRacunaService.dobaviIzvestaj() != undefined)
		var izvestaj = IzvestajRacunaService.dobaviIzvestaj();
	else
		$location.path("/kupac/korpa");
	$scope.izvestaj = izvestaj.data;
	
	/**
	 * Postavi artikle koji ce se naci na izvestaju
	 */
	$scope.artikli = izvestaj.data.billItems;
	
	/**
	 * Postavi popuste koji ce se naci na izvestaju
	 */
	$scope.popusti = izvestaj.data.billDiscounts;
	

	/**
	 * Racuna ukupan popust
	 */
	(function(){
		for(var i=0; i<$scope.artikli.length; i++){
			var popust = 0;
			for(var j=0; j<$scope.artikli[i].itemDiscounts.length; j++){
				popust += $scope.artikli[i].itemDiscounts[j].percentage;
			}
			$scope.ukupanPopust[i]=popust;
		}
	}());
	
	/**
	 * Potvrda racuna
	 */
	$scope.potvrdiRacun = function(oznaka){
		console.log($scope.izvestaj.costInfos);
		//proveri koji je racun odabran
		switch(oznaka){
		case(1): 
			$http({
				method: "POST", 
				url : "http://localhost:8080/ProjaraWeb/rest/bills/confirm",
				data : $scope.izvestaj.costInfos[0],
				headers: {'Content-Type': 'application/json'}
			}).then(function(value) {
				if(value.statusText == "OK"){
					KorpaService.obrisiKorpu();
					IzvestajRacunaService.obrisiIzvestaj();
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
					KorpaService.obrisiKorpu();
					IzvestajRacunaService.obrisiIzvestaj();
					$scope.uspesno = "Uspešno ste naručili artikle. Stanje Vašeg računa možete pratiti u istoriji kupovina.";
					$timeout(function() {
						$scope.uspesno = "";
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

	/**
	 * Povratak na korpu
	 */
	$scope.povratakNaKorpu = function(){
		$location.path("/kupac/korpa");
	}
	
	/**
	 * Otkaz kupovine
	 */
	$scope.otkaziKupovinu = function(){
		KorpaService.obrisiKorpu();
		IzvestajRacunaService.obrisiIzvestaj();
		
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