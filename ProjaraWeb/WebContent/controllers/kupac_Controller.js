angular.module('sbzApp')
.controller('kupac_Controller', ['$scope', '$uibModal', '$http', '$timeout', '$cookies', '$location',
                                 function($scope, $uibModal, $http, $timeout, $cookies, $location){

	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}
	
	$(".collapse").click(function () {

		$(this).parent().children().toggle();
		$(this).toggle();

	});

	$(".collapse").each(function(){

		$(this).parent().children().toggle();
		$(this).toggle();
	});

	$scope.artikli = [];
	$scope.kategorije = [];
	$scope.kolicinaArtikla = 1;
	
	//dobavi podatke iz korpe ukoliko postoje i smanji inStock parametar
	var proveriMaksimum = function(artikal){
		//proveri je l' korpa postoji
		var korpa = $cookies.getObject("korpa");
		//izmeni maksimum (in stock)
		if(korpa != undefined){
			for(var i=0; i<korpa.artikli.length; i++){
				if(korpa.artikli[i].oznaka == artikal.oznaka){
					artikal.maksimum -= korpa.artikli[i].kolicina;
					break;
				}
			}
		}
		return artikal;
	}

	var populateHelp = function(value){
		$scope.artikli = [];
		if(!value)
			return;
		for(var i=0; i<value.data.length; i++){
			if(value.data[i].info.inStock > 0 && value.data[i].info.active){
				var artikal = {
						"oznaka":value.data[i].info.id,
						"naziv":value.data[i].info.name,
						"kategorija":value.data[i].category.name,
						"popust":value.data[i].discountPerc,
						"cena":value.data[i].info.cost,
						"cenaSaPopustom":value.data[i].costWithDiscount,
						"akcije":value.data[i].actions,
						"slika":"images/"+value.data[i].info.picture,
						"maksimum":value.data[i].info.inStock
				}
				artikal = proveriMaksimum(artikal);
				$scope.artikli.push(artikal);
			}
		}
	}

	//dobavljanje artikala
	$scope.allItems = (function(){
		var reloadHelp = function(){
			$http({
				method: "GET", 
				url : "http://localhost:8080/ProjaraWeb/rest/items",
			}).then(function(value) {
				populateHelp(value);
			});
		};
		reloadHelp();
		return{
			"reload":function(){reloadHelp();}
		}
	}());
	//loadAll();


	//dobavljanje kategorija artikla
	$http({
		method: "GET", 
		url : "http://localhost:8080/ProjaraWeb/rest/itemCategories",
	}).then(function(value) {
		$scope.kategorije = value.data;
	});

	//dodaje artikal u cookie
	var dodajArtikal = function(korpa, artikal){
		for(var i=0; i<korpa.artikli.length; i++){
			if(korpa.artikli[i].oznaka === artikal.oznaka){
				korpa.artikli[i].kolicina += artikal.kolicina;
				return;
			}
		}
		korpa.artikli.push(artikal);
	}

	//dodavanje u korpu
	$scope.dodajUKorpu = function(kolicinaArtikla, artikal){
		if(artikal.maksimum == 0){
			//upozorenje
			$scope.greska = "Artikla više nema na stanju.";
			$timeout(function() {
				$scope.greska = "";
			}, 1500);
		}
		else if(kolicinaArtikla == undefined || kolicinaArtikla<1 || kolicinaArtikla > artikal.maksimum){
			//upozorenje
			$scope.greska = "Količina artikala nije ispravno definisana. Mozete naručiti najmanje 1, a najviše " + artikal.maksimum + " artikala. Artikal nije dodat u korpu.";
			$timeout(function() {
				$scope.greska = "";
			}, 1500);
		}
		else{
			//ukoliko korpa ne postoji, kreiraj korpu
			if($cookies.get("korpa") == undefined){	
				var korpa = {artikli:[]};
				artikal.kolicina = kolicinaArtikla;
				korpa.artikli.push(artikal);
				$cookies.putObject("korpa", korpa);
			}
			else{
				var korpa = $cookies.getObject("korpa");
				artikal.kolicina = kolicinaArtikla;
				dodajArtikal(korpa, artikal);
				$cookies.remove("korpa");
				$cookies.putObject("korpa", korpa);
			}
			//promeni stanje artikla
			for(var i=0; i<$scope.artikli.length; i++){
				if($scope.artikli[i].oznaka == artikal.oznaka){
					$scope.artikli[i].maksimum -= kolicinaArtikla;
				}
			}
			
			//status uspesnosti
			$scope.izvestajUspesnosti = "Artikal je dodat u korpu.";
			$timeout(function() {
				$scope.izvestajUspesnosti = "";
			}, 1000);
		}
	}

	//vise informacija o artiklu - modal
	$scope.viseInformacija = function(oznakaArtikla){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: 'views/kupac_artikalInfo_m.html',
			controller: 'kupac_artikalInfoController',
			windowClass: 'my-dialog',
			resolve:{
				oznakaArtikla : function(){
					return oznakaArtikla;
				},
				artikli : function(){
					var artikli = {};
					for (var i = 0, len = $scope.artikli.length; i < len; i++) {
						artikli[$scope.artikli[i].oznaka] = $scope.artikli[i];
					}
					return artikli;
				}
			}
		});
	}

	$scope.advancedSearch = {
			id :"",
			name:"",
			category:"",
			costRange:{
				minCost:null,
				maxCost:null,
			}
	}

	$scope.search = function(){
		$http({
			method:"POST",
			url:"http://localhost:8080/ProjaraWeb/rest/items/search",
			data:$scope.advancedSearch,
			headers: {'Content-Type': 'application/json'}
		}).then(function(value){
			populateHelp(value);
		})
	}

	$scope.catFilter = function(catCode){
		$http({
			method:"GET",
			url:"http://localhost:8080/ProjaraWeb/rest/items/category/"+catCode
		}).then(function(value){
			$scope.artikli = [];
			populateHelp(value);

		})
	}



}])

.controller('kupac_artikalInfoController', ['$scope', '$uibModalInstance', 'oznakaArtikla', 'artikli',
                                            function($scope, $uibModalInstance, oznakaArtikla, artikli){

	$scope.artikal = artikli[oznakaArtikla];
	console.log("artikal");
	console.log($scope.artikal);

	$scope.zatvori = function(){
		$uibModalInstance.close();
	}
}]);