var sbzApp = angular.module('sbzApp', ['ngCookies', 'ngRoute','ngResource', 'ui.bootstrap', 'ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

sbzApp.filter('iif', function () {
	   return function(input, trueValue, falseValue) {
	        return input ? trueValue : falseValue;
	   };
	});


sbzApp.config(function($routeProvider){
				
				$routeProvider	
						.when(
							"/kupac",
							{
									templateUrl : "views/kupac.html"
							}
						)
						.when(
							"/kupac/info",
							{
									templateUrl : "views/kupac_info.html"
							}
						)
						.when(
							"/kupac/korpa",
							{
									templateUrl : "views/kupac_korpa.html"
							}
						)
						.when(
							"/kupac/korpa/popusti",
							{
									templateUrl : "views/kupac_ostvareniPopusti.html"
							}
						)
						.when(
							"/menadzer",
							{
									templateUrl : "views/menadzer.html"
							}
						)
						.when(
							"/menadzer/kupci",
							{
									templateUrl : "views/menadzer_kategorije_kupaca.html"
							}
						)
						.when(
							"/menadzer/artikli",
							{
									templateUrl : "views/menadzer_kategorije_artikala.html"
							}
						)
						.when(
							"/menadzer/akcije",
							{
									templateUrl : "views/menadzer_akcije.html"
							}
						)
						.when(
							"/prodavac",
							{
									templateUrl : "views/prodavac.html"
							}
						)
						.when(
							"/prodavac/narudzbe",
							{
									templateUrl : "views/prodavac_narudzbe.html"
							}
						)
						.when(
							"/prodavac/artikli",
							{
									templateUrl : "views/prodavac_artikli.html"
							}
						)
						.when(
							"/prijava",
							{
									templateUrl: "views/prijava.html"
							}
						)
						.when(
							"/registracija",
							{
									templateUrl: "views/registracija.html"
							}
						)
						.otherwise(
							{
							redirectTo: "/prijava"
							}	
						);
	}
);
