angular.module('sbzApp')
	.controller('prijava_Controller', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
			
				// Neuspjesno logovanje
				$scope.error = false;
				$scope.errorMessage = "";
				
				// Promjenljive za ukljucivanje odg. menija
				$rootScope.ulogaKupac = false;
				$rootScope.ulogaProdavac = false;
				$rootScope.ulogaMenadzer = false;
		
				$scope.roles = ["KUPAC", "PRODAVAC", "MENADZER"];
				$rootScope.user = { username: "pera", 
									password: "pera", 
									role: $scope.roles[0]
							  	  };
				$scope.user = $rootScope.user;
				
				$scope.login = function() {
					console.log('Prijava');
					
					var username = $scope.user.username;
					var password = $scope.user.password;
					var role = $scope.user.role;
					
					/*
					$http({
						method: "POST", 
						url : "http://localhost:8080/ProjaraWeb/rest/user/login",
						data : JSON.stringify($scope.user)
					}).then(function(value) {
						if(value.data == "OK"){  */
							$rootScope.user.username = username;
							$rootScope.user.password = password;
							
							if (role == "KUPAC") {	
								$rootScope.user.role = role;
								$scope.prikaziKupacMeni();
							}
							else if (role == "PRODAVAC") {
								$rootScope.user.role = role;
								$scope.prikaziProdavacMeni();
							}
							else if (role == "MENADZER") {
								$rootScope.user.role = role;
								$scope.prikaziMenadzerMeni();
							} 
					/*	}else{
					        //Logovanje neuspjesno
							$scope.errorMessage = value.data;
							$scope.errorMessage = value.data;
							$scope.error = true;
						}
					}, function(reason) {
						console.log(JSON.stringify(reason));
					}); */
					
				};
				
				$scope.prikaziKupacMeni = function() {
					$rootScope.ulogaKupac = true;
					$rootScope.ulogaProdavac = false;
					$rootScope.ulogaMenadzer = false;
					$location.path('/kupac');
				};
				
				$scope.prikaziProdavacMeni = function() {
					$rootScope.ulogaKupac = false;
					$rootScope.ulogaProdavac = true;
					$rootScope.ulogaMenadzer = false;
					$location.path('/prodavac');
				};
				
				$scope.prikaziMenadzerMeni = function() {
					$rootScope.ulogaKupac = false;
					$rootScope.ulogaProdavac = false;
					$rootScope.ulogaMenadzer = true;
					$location.path('/menadzer');
				};
				
				$scope.registrujSe = function(){
					$location.path('/registracija');
				}
			}
	]);