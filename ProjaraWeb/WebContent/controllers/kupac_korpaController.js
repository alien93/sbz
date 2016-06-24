angular.module('sbzApp')
	.controller('kupac_korpaController', ['$scope', '$location', '$cookies', '$timeout',
	        function($scope, $location, $cookies, $timeout){
		
				$scope.artikli = [];
				$scope.zaUplatu = 0;
				var korpa = $cookies.getObject("korpa");
				
				
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