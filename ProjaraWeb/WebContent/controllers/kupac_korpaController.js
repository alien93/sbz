angular.module('sbzApp')
.controller('kupac_korpaController', ['$scope', '$location', '$cookies', '$timeout', '$http', '$cookies',
                                      function($scope, $location, $cookies, $timeout, $http, $cookies){

	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}

	$scope.artikli = [];
	$scope.zaUplatu = 0;
	var korpa = $cookies.getObject("korpa");

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
		var korpa = $cookies.getObject("korpa");
		if(korpa == undefined || korpa.artikli.length == 0){
			$scope.greska = "Korpa je prazna. Molimo dodajte artikle u korpu preko \"Prodavnica\" stranice.";
			$timeout(function() {
				$scope.greska = "";
			}, 1500);
		}
		else{
			//generisi racun
			var generisiRacun = (function(){
				var artikli = dobaviArtikle();
				var racun = {
						customerId: "1",
						items: artikli,
						points: $scope.bodovi
				};
				$http({
					method: "POST", 
					url : "http://localhost:8080/ProjaraWeb/rest/bills/create",
					data : racun,
					header: "application/json"
				}).then(function(value) {
					console.log(value);
					$scope.kategorije = value.data;
				});
			}());
			//pokupi zeljeni broj bodova
			$cookies.put("bodovi", $scope.bodovi);
			$location.path("/kupac/korpa/popusti");
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
		for(var i=0; i<korpa.artikli.length; i++){
			korpa.artikli[i].ukupno = korpa.artikli[i].cenaSaPopustom * korpa.artikli[i].kolicina;
			$scope.artikli.push(korpa.artikli[i]);
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
		var korpa = $cookies.getObject("korpa");
		var novaKorpa = {artikli:[]};
		for(var i=0; i<korpa.artikli.length; i++){
			if(korpa.artikli[i].oznaka !== oznakaArtikla){
				novaKorpa.artikli.push(korpa.artikli[i]);
			}
		}
		$cookies.remove("korpa");
		$cookies.putObject("korpa", novaKorpa);
	}

	//brisanje artikla iz korpe
	$scope.obrisiIzKorpe = function(indeks, oznakaArtikla){
		brisiIzKorpeCookie(oznakaArtikla);
		$scope.artikli.splice(indeks, 1);
		izracunajZaUplatu();
	}

	//brisanje sadrzaja korpe
	$scope.isprazniKorpu = function(){
		$cookies.remove("korpa");
		$location.path("/kupac");
	}


}])