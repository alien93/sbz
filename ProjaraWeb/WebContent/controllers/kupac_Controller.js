angular.module('sbzApp')
	.controller('kupac_Controller', ['$scope', 
			function($scope){
			
				//-------------------------------------------test podaci-------------------------------------
					
					var kategorija1 = {"naziv":"Elektronika", "podkategorije":["Televizori", "Racunari"]};
					var kategorija2 = {"naziv":"Namestaj", "podkategorije":["Kuhinja", "Dnevna soba", "Spavaca soba"]};
					
					var artikal1 = {"oznaka":"123", "naziv":"Pegla", "kategorija":"Mali kucni uredjaji", "popust":"", "cena":"500"};
					var artikal2 = {"oznaka":"123", "naziv":"Flasa", "kategorija":"Posudje", "popust":"", "cena":"1500"};
					var artikal3 = {"oznaka":"123", "naziv":"Trotinet", "kategorija":"Igracke", "popust":"Novogodisnji", "cena":"300"};
					var artikal4 = {"oznaka":"123", "naziv":"Krevet", "kategorija":"Spavaca soba", "popust":"", "cena":"5000"};
					var artikal5 = {"oznaka":"123", "naziv":"Prskalica", "kategorija":"Basta", "popust":"", "cena":"50000"};

					$scope.kategorije = [kategorija1, kategorija2];
					$scope.artikli = [artikal1, artikal2, artikal3, artikal4, artikal5];
					
				//-------------------------------------------/test podaci-------------------------------------

					$scope.viseInformacija = function(oznakaArtikla){
						console.log('hello from vise informacija');
						console.log(oznakaArtikla);
					}
					
	}]);