angular.module('sbzApp')
	.controller('kupac_korpaController', ['$scope', 
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
				$scope.zaUplatu = 0;
				
				//-----------------------------------/test podaci------------------------------------

				//za uplatu
				for(var i=0; i<$scope.artikli.length; i++){
					$scope.zaUplatu+=parseInt($scope.artikli[i].ukupno);
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
	}])