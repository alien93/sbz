angular.module('sbzApp')
	.controller('registracijaController', ['$scope', '$http', '$timeout', '$location',
	     function($scope, $http, $timeout, $location){
			$scope.korisnik = {};
			
			//provera lozinki
			$scope.proveraLozinki = function(){
				if($scope.password === $scope.password2){
					$scope.greska = "";
				}
			}
			
			//registracija
			$scope.registrujMe = function(){
				if($scope.password !== $scope.password2){
					$scope.greska = "Lozinke se ne poklapaju."
				}
				else{
					$http({
						method: "POST", 
						url : "http://localhost:8080/ProjaraWeb/rest/user/register",
						data: $.param({
										"username": $scope.username, 
										"password":$scope.password, 
										"firstName": $scope.firstName, 
										"lastName":$scope.lastName,
										"address": $scope.address}
									  ),
						headers : {
					        'Content-Type' : 'application/x-www-form-urlencoded'
					    }
					}).then(function(value) {
						if(value.statusText === "OK"){
							$scope.izvestajUspesnosti = "Uspešno ste se registrovali.";
							$scope.greska = "";
							 $timeout(function() {
							      $location.path('/prijava');
							 }, 1000);
						}
						else{
							$scope.greska = "Došlo je do neočekivane greške. Molimo pokušajte ponovo.";
						}
						
					},function(reason){
						if(reason.data.name === "UserAlreadyExistsException"){
							$scope.greska = "Korisnik sa korisničkim imenom '" + $scope.username + "' već postoji."
						}
						else{
							$scope.greska = "Došlo je do neočekivane greške. Molimo pokušajte ponovo.";
						}
					});
				}
			}
}]);