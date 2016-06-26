angular.module('sbzApp')
.controller('kupac_Controller', ['$scope', '$uibModal', '$http', '$timeout', '$cookies', '$location', 'KorpaService',
                                 function($scope, $uibModal, $http, $timeout, $cookies, $location, KorpaService){

	$scope.artikli = [];
	$scope.kategorije = [];
	$scope.kolicinaArtikla = 1;


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
	 * Dobavi podatke iz korpe ukoliko postoje i smanji inStock parametar
	 */
	var proveriMaksimum = function(artikal){
		//proveri je l' korpa postoji
		//var korpa = $cookies.getObject("korpa");
		var korpa = KorpaService.dobaviKorpu();
		console.log(korpa);
		//izmeni maksimum (in stock)
		if(KorpaService.dobaviArtikle() != undefined){
			for(var i=0; i<korpa.artikli.length; i++){
				if(korpa.artikli[i].oznaka == artikal.oznaka){
					artikal.maksimum -= korpa.artikli[i].kolicina;
					break;
				}
			}
		}
		return artikal;
	}

	/**
	 * Kaci artikle na $scope
	 */
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

	/**
	 * Dobavljanje artikala
	 */
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



	/**
	 * Dobavljanje kategorija artikla
	 */
	$http({
		method: "GET", 
		url : "http://localhost:8080/ProjaraWeb/rest/itemCategories",
	}).then(function(value) {
		$scope.kategorije = value.data;
	});

	/**
	 * Dodaje artikal u korpu (service)
	 */
	var dodajArtikal = function(korpa, artikal){
		for(var i=0; i<korpa.artikli.length; i++){
			if(korpa.artikli[i].oznaka === artikal.oznaka){
				korpa.artikli[i].kolicina += artikal.kolicina;
				return;
			}
		}
		korpa.artikli.push(artikal);
	}

	/**
	 * Dodaj artikal u korpu
	 */
	$scope.dodajUKorpu = function(kolicinaArtikla, artikal){
		if(artikal.maksimum == 0){	//proveri da li artikla ima na stanju
			//upozorenje
			$scope.greska = "Artikla više nema na stanju.";
			$timeout(function() {
				$scope.greska = "";
			}, 1500);
		}
		else if(kolicinaArtikla == undefined || kolicinaArtikla<1 || kolicinaArtikla > artikal.maksimum){	//proveri da li je kolicina artikla odgovarajuca
			//upozorenje
			$scope.greska = "Količina artikala nije ispravno definisana. Mozete naručiti najmanje 1, a najviše " + artikal.maksimum + " artikala. Artikal nije dodat u korpu.";
			$timeout(function() {
				$scope.greska = "";
			}, 1500);
		}
		else{
			//ukoliko korpa ne postoji, kreiraj korpu i dodaj artikal u korpu
			if(KorpaService.dobaviArtikle() == undefined){
				var korpa = {artikli:[]};
				artikal.kolicina = kolicinaArtikla;
				korpa.artikli.push(artikal);
				KorpaService.postaviKorpu(korpa);
			}
			else{
				//dodaj artikal u korpu
				var korpa = KorpaService.dobaviKorpu();
				artikal.kolicina = kolicinaArtikla;
				dodajArtikal(korpa, artikal);
				KorpaService.obrisiKorpu();
				KorpaService.postaviKorpu(korpa);
			}
			//promeni stanje artikla na lageru
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

	/**
	 * Vise informacija o artiklu - modal
	 */
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

	/**
	 * Objekat za naprednu pretragu
	 */
	$scope.advancedSearch = {
			id :"",
			name:"",
			category:"",
			costRange:{
				minCost:null,
				maxCost:null,
			}
	}

	/**
	 * Napredna pretraga
	 */
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

	/**
	 * Filtriranje kategorija
	 */
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

	var artikal = artikli[oznakaArtikla];
	//korekcija datuma za akcije
	for(var i=0; i<artikal.akcije.length; i++){
		var d = new Date(artikal.akcije[i].from);
		var month = parseInt(d.getMonth()) + 1;
		artikal.akcije[i].from = d.getDate() + "." + month + "." + d.getFullYear() + ".";

		d = new Date(artikal.akcije[i].until);
		var month = parseInt(d.getMonth()) + 1;
		artikal.akcije[i].until = d.getDate() + "." + month + "." + d.getFullYear() + ".";
	}

	$scope.artikal = artikal;

	/**
	 * Zatvaranje modalnog dijaloga
	 */
	$scope.zatvori = function(){
		$uibModalInstance.close();
	}
}]);