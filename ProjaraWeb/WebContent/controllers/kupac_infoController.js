
angular.module('sbzApp')
	.controller('kupac_infoController', ['$scope', '$uibModal', '$location',
	     function($scope, $uibModal, $location){
			
			//----------------------------------test podaci------------------------------------------
				
				var artikal1 = {
									"oznaka":"123", 
									"naziv":"Pegla", 
									"cena":"500", 
									"kolicina":"2", 
									"originalnaCena":"700", 
									"popust":"25", 
									"ukupno":"450"
								};
				var artikal2 = {
						"oznaka":"321", 
						"naziv":"Suncobran", 
						"cena":"1000", 
						"kolicina":"1", 
						"originalnaCena":"700", 
						"popust":"25", 
						"ukupno":"450"
					};
				var artikal3 = {
						"oznaka":"458", 
						"naziv":"Cipele", 
						"cena":"3500", 
						"kolicina":"1", 
						"originalnaCena":"4000", 
						"popust":"50", 
						"ukupno":"1750"
					};
				
				var popust1 = {
						"oznaka":"548",
						"naziv":"Novogodisnji popust",
						"opis":"50% popusta na jelke",
						"artikli":[artikal1, artikal2]
				};
				
				var popust2 = {
						"oznaka":"100",
						"naziv":"Popust za skolarce",
						"opis":"60% na skolski pribor",
						"artikli":[artikal1, artikal3]
				};
				
				
		
				var racun1 = {
								"oznaka":"123", 
								"datum":"23/12/2015", 
								"ukupanIznos":"50000",
								"oznakaKupca":"56487", 
								"stanje":"Poruceno", 
								"artikli":[artikal1, artikal2, artikal3],
								"popusti":[popust1, popust2],
								"suma":"25666",
								"popust":"30",
								"ukupnaSuma":"20000",
								"iskorisceniBodovi":"0",
								"steceniBodovi":"20"
								
				};

				var racun2 = {
						"oznaka":"325", 
						"datum":"15/12/2015", 
						"ukupanIznos":"25000",
						"oznakaKupca":"36000", 
						"stanje":"Otkazano", 
						"artikli":[artikal1, artikal3],
						"popusti":[popust2],
						"suma":"35000",
						"popust":"50",
						"ukupnaSuma":"16000",
						"iskorisceniBodovi":"7",
						"steceniBodovi":"17"
					  };
				
				$scope.racuni = [racun1, racun2];
		
		
			//----------------------------------/test podaci------------------------------------------

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
						}
					}
				});
			}
			
			$scope.cuvajIzmene = function(){
				$location.path('/kupac');
			}
	}])
	
	.controller('kupac_racunInfoController', ['$scope', 'oznaka', 'racuni', '$uibModalInstance',
	       function($scope, oznaka, racuni, $uibModalInstance){
				
				//----------------------------------test podaci------------------------------------------
					
					for(var i=0; i<racuni.length; i++){
						if(racuni[i].oznaka == oznaka){
							$scope.racun=racuni[i];
							break;
						}
					}
		
				//----------------------------------/test podaci------------------------------------------

				$scope.zatvori = function(){
					$uibModalInstance.close();
				}
	}]);