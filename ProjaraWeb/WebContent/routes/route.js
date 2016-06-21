var sbzApp = angular.module('sbzApp', ['ngCookies', 'ngRoute','ngResource', 'ui.bootstrap', 'ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

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
							"/prijava",
							{
									templateUrl: "views/prijava.html"
							}
						)
						.otherwise(
							{
							redirectTo: "/kupac"
							}	
						);
	}
);