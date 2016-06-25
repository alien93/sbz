angular.module('sbzApp')
.controller('kupac_ostvareniPopustiController', ['$scope', '$location', '$cookies',
                                                 function($scope, $location, $cookies){

	//proveri da li je korisnik vec prijavljen
	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}

	//dobavi izvestaj racuna
	var izvestaj = $cookies.getObject("izvestajRacuna");
	console.log("izvestaj");
	console.log(izvestaj);
	
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

	//-------------------------test podaci-------------------
	
	$scope.racun = {
			"ukupnaCena":"5000",
			"procenatUmanjenja":"50",
			"placenaCena":"2500",
			"brPotrosenihBodova":"2",
			"brOstvarenihBodova":"100"
	};

	//-------------------------/test podaci-------------------

	console.log($cookies.getObject("korpa"));
	console.log($cookies.get("bodovi"));

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