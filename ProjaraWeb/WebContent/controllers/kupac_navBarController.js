angular.module('sbzApp')
.controller('kupac_navBarController', ['$scope', '$location', '$uibModal', '$cookies', 'KorpaService', 'IzvestajRacunaService',
                                       function($scope, $location, $uibModal, $cookies, KorpaService, IzvestajRacunaService){

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
		KorpaService.obrisiKorpu();
		IzvestajRacunaService.obrisiIzvestaj();
		$cookies.remove("korisnikID");
		$cookies.remove("korisnik");
		$location.path('/prijava');
	}
}])