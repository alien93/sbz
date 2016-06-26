angular.module('sbzApp')
.controller('kupac_korpaController', ['$scope', '$location', '$cookies', '$http', '$cookies', '$timeout', 'KorpaService', 'IzvestajRacunaService',
                                      function($scope, $location, $cookies, $http, $cookies, $timeout, KorpaService, IzvestajRacunaService){

	/**
	 * Proveri da li je korisnik ulogovan
	 */
	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}
	
	var user = $cookies.getObject("korisnik");

	$scope.artikli = [];
	$scope.zaUplatu = 0;
	$scope.maxBodovi = user.points;
	
	/**
	 * Dobavi korpu
	 */
	var korpa = KorpaService.dobaviKorpu();

	/**
	 * Dobavi artikle koji su vec bili u korpi
	 */
	var dobaviArtikle = function(){
		var retVal = [];
		for(var i=0; i<korpa.artikli.length; i++){
			var elem = {
					itemId: korpa.artikli[i].oznaka,
					quantity: korpa.artikli[i].kolicina
			}
			retVal.push(elem);
		}
		return retVal;
	}

	/**
	 * Potvrdi kupovinu
	 */
	$scope.potvrdaKorpe = function(){
		var korpa = KorpaService.dobaviKorpu();
		if(korpa == undefined || korpa.artikli.length == 0){
			$scope.greska = "Korpa je prazna. Molimo dodajte artikle u korpu preko \"Prodavnica\" stranice.";
		}
		else if($scope.bodovi < 0 || $scope.bodovi > $scope.maxBodovi ||$scope.bodovi == undefined ){
			$scope.greska = "Možete iskroistiti najmanje 0, a najviše " + $scope.maxBodovi + " bodova. Molimo izmenite broj bodova.";
			$timeout(function() {
				$scope.greska = "";
			}, 2500);
		}
		else{
			//generisi racun
			var generisiRacun = (function(){
				var artikli = dobaviArtikle();
				var racun = {
						customerId: user.id,
						items: artikli,
						points: $scope.bodovi
				};
				$http({
					method: "POST", 
					url : "http://localhost:8080/ProjaraWeb/rest/bills/create",
					data : racun,
					headers: {'Content-Type': 'application/json'}
				}).then(function(value) {
					if(value.statusText == "OK"){
						IzvestajRacunaService.postaviIzvestaj(value);
						$cookies.put("bodovi", $scope.bodovi);
						$location.path("/kupac/korpa/popusti");
					}
					else{
						$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
					}
				},function(reason){
					$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
				});
			}());
		}
	}


	if(korpa == undefined){
		return;
	}

	/**
	 * Gramatika :D
	 */
	$scope.bodovi = 0;
	$scope.tekst = "bodova.";
	$scope.izmenaBodova = function(){
		if($scope.bodovi == 1){
			$scope.tekst = "bod.";
		}
		else if($scope.bodovi>1 && $scope.bodovi<5){
			$scope.tekst = "boda."
		}
		else{
			$scope.tekst = "bodova.";
		}
	}

	/**
	 * Racunaj sumu za svaki artikal - kolicina*cena
	 */
	var racunajUkupno = (function(){
		if(korpa.artikli != null){
			for(var i=0; i<korpa.artikli.length; i++){
				korpa.artikli[i].ukupno = korpa.artikli[i].cenaSaPopustom * korpa.artikli[i].kolicina;
				$scope.artikli.push(korpa.artikli[i]);
			}
		}
	})();


	/**
	 * Racunaj koliko treba uplatiti (suma svih cena)
	 */
	var izracunajZaUplatu = function(){
		$scope.zaUplatu = 0;
		for(var i=0; i<$scope.artikli.length; i++){
			$scope.zaUplatu+=parseInt($scope.artikli[i].ukupno);
		}
	};
	izracunajZaUplatu();

	/**
	 * Obrisi artikal iz korpe (service)
	 */
	var brisiIzKorpeCookie = function(oznakaArtikla){
		var korpa = KorpaService.dobaviKorpu();
		var novaKorpa = {artikli:[]};
		for(var i=0; i<korpa.artikli.length; i++){
			if(korpa.artikli[i].oznaka !== oznakaArtikla){
				novaKorpa.artikli.push(korpa.artikli[i]);
			}
		}
		KorpaService.obrisiKorpu();
		KorpaService.postaviKorpu(novaKorpa);
	}

	/**
	 * Brisanje artikla iz korpe
	 */
	$scope.obrisiIzKorpe = function(indeks, oznakaArtikla){
		brisiIzKorpeCookie(oznakaArtikla);
		$scope.artikli.splice(indeks, 1);
		izracunajZaUplatu();
	}

	/**
	 * Brisanje sadrzaja korpe
	 */
	$scope.isprazniKorpu = function(){
		KorpaService.obrisiKorpu();
		$location.path("/kupac");
	}


}])