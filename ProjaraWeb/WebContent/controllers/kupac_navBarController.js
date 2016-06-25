/**
 * 
 */
angular.module('sbzApp')
.controller('kupac_navBarController', ['$scope', '$location', '$uibModal', '$cookies',
                                       function($scope, $location, $uibModal, $cookies){

	if($cookies.get("korisnikID") == undefined){
		$location.path('/prijava');
	}
	else{
		$scope.korisnikID = $cookies.get("korisnikID");
	}

	$scope.podaciOKorisniku = function(){
		$location.path('/kupac/info');
	};

	$scope.korpa = function() {
		$location.path('/kupac/korpa');
	}

	$scope.odjava = function(){
		$cookies.remove("korisnikID");
		$location.path('/prijava');
	}
}])