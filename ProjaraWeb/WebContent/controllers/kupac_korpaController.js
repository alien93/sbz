angular.module('sbzApp')
.controller('kupac_korpaController', ['$scope', '$location', '$cookies', '$http', '$cookies', '$timeout', 'KorpaService', 'IzvestajRacunaService',
                                      function($scope, $location, $cookies, $http, $cookies, $timeout, KorpaService, IzvestajRacunaService){

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
	
	//var korpa = $cookies.getObject("korpa");
	var korpa = KorpaService.dobaviKorpu();

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

	//potvrda sadrzaja korpe
	$scope.potvrdaKorpe = function(){
		//var korpa = $cookies.getObject("korpa");
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
						console.log("izvestaj racuna");
						console.log(value);
						
						//$cookies.putObject("izvestajRacuna", value);
						IzvestajRacunaService.postaviIzvestaj(value);
						//pokupi zeljeni broj bodova
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

	//bodovi
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

	//racunaj sumu za artikle
	var racunajUkupno = (function(){
		if(korpa.artikli != null){
			for(var i=0; i<korpa.artikli.length; i++){
				korpa.artikli[i].ukupno = korpa.artikli[i].cenaSaPopustom * korpa.artikli[i].kolicina;
				$scope.artikli.push(korpa.artikli[i]);
			}
		}
	})();


	//za uplatu
	var izracunajZaUplatu = function(){
		$scope.zaUplatu = 0;
		for(var i=0; i<$scope.artikli.length; i++){
			$scope.zaUplatu+=parseInt($scope.artikli[i].ukupno);
		}
	};
	izracunajZaUplatu();

	var brisiIzKorpeCookie = function(oznakaArtikla){
		//var korpa = $cookies.getObject("korpa");
		var korpa = KorpaService.dobaviKorpu();
		var novaKorpa = {artikli:[]};
		for(var i=0; i<korpa.artikli.length; i++){
			if(korpa.artikli[i].oznaka !== oznakaArtikla){
				novaKorpa.artikli.push(korpa.artikli[i]);
			}
		}
		//$cookies.remove("korpa");
		KorpaService.obrisiKorpu();
		//$cookies.putObject("korpa", novaKorpa);
		KorpaService.postaviKorpu(novaKorpa);
	}

	//brisanje artikla iz korpe
	$scope.obrisiIzKorpe = function(indeks, oznakaArtikla){
		brisiIzKorpeCookie(oznakaArtikla);
		$scope.artikli.splice(indeks, 1);
		izracunajZaUplatu();
	}

	//brisanje sadrzaja korpe
	$scope.isprazniKorpu = function(){
		//$cookies.remove("korpa");
		KorpaService.obrisiKorpu();
		$location.path("/kupac");
	}


}])