angular.module('sbzApp')
.controller('kupac_navBarController', ['$scope', '$location', '$uibModal', '$cookies',
                                       function($scope, $location, $uibModal, $cookies){

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
	 * Redirektuj na profil korisnika
	 */
	$scope.podaciOKorisniku = function(){
		$location.path('/kupac/info');
	};

	/**
	 * Redirektuj na korpu
	 */
	$scope.korpa = function() {
		$location.path('/kupac/korpa');
	}

	/**
	 * Odjava korisnika
	 */
	$scope.odjava = function(){
		$cookies.remove("korisnikID");
		$cookies.remove("korisnik");
		$location.path('/prijava');
	}
}])