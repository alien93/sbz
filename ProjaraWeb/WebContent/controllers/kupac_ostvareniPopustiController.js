angular.module('sbzApp')
	.controller('kupac_ostvareniPopustiController', ['$scope', '$location',
	       function($scope, $location){
			
			//-------------------------test podaci-------------------
			var popust1 = {"oznaka":"123","naziv":"Popust1", "opis":"Opis popusta 1"};
			var popust2 = {"oznaka":"231","naziv":"Popust2", "opis":"Opis popusta 2"};
			var popust3 = {"oznaka":"312","naziv":"Popust3", "opis":"Opis popusta 3"};

			var artikal1 = {"popust":popust1, "naziv":"Naziv artikla 1"};
			var artikal2 = {"popust":popust2, "naziv":"Naziv artikla 2"};
			var artikal3 = {"popust":popust3, "naziv":"Naziv artikla 3"};
			var artikal4 = {"popust":popust2, "naziv":"Naziv artikla 4"};
			
			$scope.artikli = [artikal1, artikal2, artikal3, artikal4];
			
			var popustncr1 = {"oznaka":"123","naziv":"Popust na ceo racun 1", "opis":"Opis popusta 1"};
			var popustncr2 = {"oznaka":"231","naziv":"Popust na ceo racun 2", "opis":"Opis popusta 2"};
			var popustncr3 = {"oznaka":"312","naziv":"Popust na ceo racun 3", "opis":"Opis popusta 3"};

			
			
			$scope.popusti = [popustncr1, popustncr2, popustncr3];

			$scope.racun = {
					"ukupnaCena":"5000",
					"procenatUmanjenja":"50",
					"placenaCena":"2500",
					"brPotrosenihBodova":"2",
					"brOstvarenihBodova":"100"
			};
			
			//-------------------------/test podaci-------------------

			$scope.otkaziKupovinu = function(){
				$location.path("/kupac");
			}
			$scope.potvrdiRacun = function(oznaka){
				switch(oznaka){
					case(1): console.log(1); break;
					case(2): console.log(2); break;
				}
				$location.path("/kupac");
			}
		
	}]);