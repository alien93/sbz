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
	
					$scope.artikli = [];
					$scope.kategorije = [];
					
					var populateHelp = function(value){
						$scope.artikli = [];
						console.log(value);
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
										"slika":"images/"+value.data[i].info.picture
								}
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
							populateHelp(value);
							/*$scope.artikli = [];
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
							}*/
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
				
				$scope.zatvori = function(){
					$uibModalInstance.close();
				}
	}]);