angular.module('sbzApp')
	.controller('kupac_Controller', ['$scope', '$uibModal', '$http',
			function($scope, $uibModal, $http){
		
	    $(".collapse").click(function () {

	        $(this).parent().children().toggle();
	        $(this).toggle();

	    });

	$(".collapse").each(function(){

	        $(this).parent().children().toggle();
	        $(this).toggle();
	});
			
				//-------------------------------------------test podaci-------------------------------------
					
					var kategorija1 = {"naziv":"Elektronika", "podkategorije":["Televizori", "Racunari"]};
					var kategorija2 = {"naziv":"Namestaj", "podkategorije":["Kuhinja", "Dnevna soba", "Spavaca soba"]};
					
					var artikal1 = {"oznaka":"123", "naziv":"Pegla", "kategorija":"Mali kucni uredjaji", "popust":"", "cena":"500"};
					var artikal2 = {"oznaka":"123", "naziv":"Flasa", "kategorija":"Posudje", "popust":"", "cena":"1500"};
					var artikal3 = {"oznaka":"123", "naziv":"Trotinet", "kategorija":"Igracke", "popust":"Novogodisnji", "cena":"300"};
					var artikal4 = {"oznaka":"123", "naziv":"Krevet", "kategorija":"Spavaca soba", "popust":"", "cena":"5000"};
					var artikal5 = {"oznaka":"123", "naziv":"Prskalica", "kategorija":"Basta", "popust":"", "cena":"50000"};

					//$scope.kategorije = [kategorija1, kategorija2];
					//$scope.artikli = [artikal1, artikal2, artikal3, artikal4, artikal5];
					
				//-------------------------------------------/test podaci-------------------------------------
					$scope.artikli = [];
					$scope.kategorije = [];
					
					//dobavljanje artikala
					$http({
						method: "GET", 
						url : "http://localhost:8080/ProjaraWeb/rest/items",
					}).then(function(value) {
						for(var i=0; i<value.data.length; i++){
							if(value.data[i].info.inStock > 0){
								var artikal = {
										"oznaka":value.data[i].info.id,
										"naziv":value.data[i].info.name,
										"kategorija":value.data[i].category.name,
										"popust":value.data[i].discountPerc,
										"cena":value.data[i].info.cost,
										"cenaSaPopustom":value.data[i].costWithDiscount,
										"akcije":value.data[i].actions,
										"slika":"images/"+value.data[i].info.picture
								}
								$scope.artikli.push(artikal);
							}
						}
					});
					
					//dobavljanje kategorija artikla
					$http({
						method: "GET", 
						url : "http://localhost:8080/ProjaraWeb/rest/itemCategories",
					}).then(function(value) {
						console.log(value);
						$scope.kategorije = value.data;
					});
					
					
					$scope.viseInformacija = function(oznakaArtikla){
						var modalInstance = $uibModal.open({
							animation: true,
							templateUrl: 'views/kupac_artikalInfo_m.html',
							controller: 'kupac_artikalInfoController',
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
							$scope.artikli = [];
							for(var i=0; i<value.data.length; i++){
							if(value.data[i].info.inStock > 0){
								var artikal = {
										"oznaka":value.data[i].info.id,
										"naziv":value.data[i].info.name,
										"kategorija":value.data[i].category.name,
										"popust":value.data[i].discountPerc,
										"cena":value.data[i].info.cost,
										"cenaSaPopustom":value.data[i].costWithDiscount,
										"akcije":value.data[i].actions,
										"slika":"images/"+value.data[i].info.picture
								}
								$scope.artikli.push(artikal);
							}
						}
						})
					}
					
	}])
	
	.controller('kupac_artikalInfoController', ['$scope', '$uibModalInstance', 'oznakaArtikla', 'artikli',
	       function($scope, $uibModalInstance, oznakaArtikla, artikli){
								
				$scope.artikal = artikli[oznakaArtikla];
				
				$scope.zatvori = function(){
					$uibModalInstance.close();
				}
	}]);