angular.module('sbzApp')
	.controller('prijava_Controller', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
			
				// Neuspjesno logovanje
				$scope.error = false;
				$scope.greska = "";

				// Promjenljive za ukljucivanje odg. menija
				$rootScope.ulogaKupac = false;
				$rootScope.ulogaProdavac = false;
				$rootScope.ulogaMenadzer = false;
		
				$scope.roles = ["KUPAC", "PRODAVAC", "MENADŽER"];
				$rootScope.user = { username: "", 
									password: "", 
									role: $scope.roles[0]
							  	  };
				$scope.user = $rootScope.user;
				
				$scope.login = function() {
					console.log('Prijava');
					
					var username = $scope.user.username;
					var password = $scope.user.password;
					var role = $scope.user.role;
					var roleCopy = role;
					
					switch(role){
					case ($scope.roles[0]): role="C"; break;
					case ($scope.roles[1]) : role="M"; break;
					case ($scope.roles[2]) : role="V"; break;
					default: role="C";
					}
					
					var user = {
							"username":username,
							"password":password,
							"role": role
					};
					console.log(user);
					$http({
						method: "POST", 
						url : "http://localhost:8080/ProjaraWeb/rest/user/login",
						data : $.param(user),
						headers : {
					        'Content-Type' : 'application/x-www-form-urlencoded'
					    }
					}).then(function(value) {
						console.log(value);
						if(value.statusText == "OK"){  
							$rootScope.user.username = username;
							$rootScope.user.password = password;
							$rootScope.user.role = role;

							if (role == "C") {	
								$scope.prikaziKupacMeni();
							}
							else if (role == "V") {
								$scope.prikaziProdavacMeni();
							}
							else if (role == "M") {
								$scope.prikaziMenadzerMeni();
							} 
						}else{
					        //Logovanje neuspjesno
							$scope.greska = "Ne postoji registrovan " + roleCopy + " sa tim korisničkim imenom ili lozinkom. Molimo proverite podatke.";
						}
					}, function(reason) {
						console.log(reason);
						console.log(reason.data.name);
						if(reason.data.name === "UserNotExistsException"){
							$scope.greska = "Ne postoji registrovan " + roleCopy + " sa tim korisničkim imenom ili lozinkom. Molimo proverite podatke.";
						}
						else{
							$scope.greska = "Podaci nisu ispravno popunjeni.";
						}
					}); 
					
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