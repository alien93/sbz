angular.module('sbzApp')
	.controller('kupac_racun2Controller', ['$scope', 
	        function($scope){
		
				//-----------------------------------test podaci------------------------------------
				var artikal1={
						"oznaka":"12345",
						"naziv":"Pegla",
						"cena":"1500",
						"kolicina":"2",
						"originalnaCena":"2000",
						"popust":"10",
						"ukupno":"1000"
				};
				var artikal2={
						"oznaka":"12345",
						"naziv":"Pegla",
						"cena":"1500",
						"kolicina":"2",
						"originalnaCena":"2000",
						"popust":"10",
						"ukupno":"1000"
				};
				var artikal3={
						"oznaka":"12345",
						"naziv":"Pegla",
						"cena":"1500",
						"kolicina":"2",
						"originalnaCena":"2000",
						"popust":"10",
						"ukupno":"1000"
				};
		
		
				$scope.artikli = [artikal1, artikal2, artikal3];
				
				//-----------------------------------/test podaci------------------------------------

				//za uplatu
				var izracunajZaUplatu = function(){
					$scope.zaUplatu = 0;
					for(var i=0; i<$scope.artikli.length; i++){
						$scope.zaUplatu+=parseInt($scope.artikli[i].ukupno);
					}
				};
				izracunajZaUplatu();
				
				$scope.obrisiIzKorpe = function(indeks, oznakaArtikla){
					$scope.artikli.splice(indeks, 1);
					izracunajZaUplatu();
				}
	}])