
angular.module('sbzApp')
.controller('kupac_infoController', ['$scope', '$uibModal', '$location', '$cookies', '$timeout', '$http',
                                     function($scope, $uibModal, $location, $cookies, $timeout, $http){

	/**
	 * Proveri da li je korisnik ulogovan
	 */
	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}
	
	/**
	 * dobavi info o kupcu
	 */
	$scope.kupac = $cookies.getObject("korisnik");
	//formatiraj datum
	var d = new Date($scope.kupac.registeredOn);
	var month = parseInt(d.getMonth()) + 1;
	$scope.registeredOn = d.getDate() + "." + month + "." + d.getFullYear() + ".";
	
	$scope.statusRacuna = [];
	$scope.racuni = [];
	
	/**
	 * Dobavi istoriju racuna
	 */
	$http({
		method: "GET", 
		url : "http://localhost:8080/ProjaraWeb/rest/bills",
		headers: {'Content-Type': 'application/json'}
	}).then(function(value) {
		console.log(value);
		if(value.statusText == "OK"){
			//uspesne (S), porucene (O), neuspele (C)
			$scope.racuni = value.data;
			for(var i=0; i<$scope.racuni.length; i++){
				//stanje narudzbine
				switch($scope.racuni[i].state){
				case("O"):$scope.statusRacuna[i] = "Poručen"; break;
				case("S"):$scope.statusRacuna[i] = "Uspešno realizovan"; break;
				case("C"):$scope.statusRacuna[i] = "Neuspešno realizovan"; break;
				default: $scope.statusRacuna[i] = "Neuspešno realizovan";
				}
				
				//formatiraj datum
				var d = new Date($scope.racuni[i].date);
				var month = parseInt(d.getMonth()) + 1;
				$scope.racuni[i].date = d.getDate() + "." + month + "." + d.getFullYear() + ".";
				
			}
		}
		else{
			$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
		}
	},function(reason){
		$scope.greska = "Došlo je do greške. Molimo pokušajte ponovo.";
	});
	
	/**
	 * Detaljnije o racunu - modalni dijalog
	 */
	$scope.pogledajRacun = function(oznaka){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: 'views/kupac_racunInfo_m.html',
			controller: 'kupac_racunInfoController',
			resolve:{
				oznaka : function(){
					return oznaka;
				},
				racuni : function(){
					return $scope.racuni;
				},
				statusRacuna : function(){
					return $scope.statusRacuna;
				}
			}
		});
	}

	/**
	 * Cuvanje izmenjenih podataka o korisniku
	 */
	$scope.cuvajIzmene = function(){
		$http({
			method: "POST", 
			url : "http://localhost:8080/ProjaraWeb/rest/user/update",
			data : $scope.kupac,
			headers: {'Content-Type': 'application/json'}
		}).then(function(value) {
			if(value.statusText == "OK"){
				$scope.uspesno = "Promene su sačuvane";
				$cookies.remove("korisnik");
				$cookies.putObject("korisnik",value.data);
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
	}
}])

.controller('kupac_racunInfoController', ['$scope', 'oznaka', 'racuni', 'statusRacuna', '$uibModalInstance',
                                          function($scope, oznaka, racuni, statusRacuna, $uibModalInstance){

	$scope.ukupanPopust=[];
	
	/**
	 * racuna ukupan popust
	 */
	var racunajPopust = function(id, popusti){
		for(var i=0; i<popusti.length; i++){
			var popust = 0;
			for(var j=0; j<popusti[i].itemDiscounts.length; j++){
				popust += popusti[i].itemDiscounts[j].percentage;
			}
			$scope.ukupanPopust[i]=popust;
		}
	};
	
	/**
	 * Dobavi podatke o popustima, statusu racuna, artiklima na popustu
	 */
	for(var i=0; i<racuni.length; i++){
		if(racuni[i].billId == oznaka){
			$scope.racun=racuni[i];
			$scope.statusRacuna = statusRacuna[i];
			$scope.artikli = racuni[i].billItems;
			$scope.popusti = racuni[i].billDiscounts;
			racunajPopust(i, $scope.artikli);
			break;
		}
	}

	/**
	 * Zatvaranje modalnog dijaloga
	 */
	$scope.zatvori = function(){
		$uibModalInstance.close();
	}
}]);