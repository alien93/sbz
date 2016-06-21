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
							"/kupac/korpa",
							{
									templateUrl : "views/kupac_korpa.html"
							}
						)
						.when(
							"/menadzer",
							{
									templateUrl : "views/menadzer.html"
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
							redirectTo: "/prijava"
							}	
						);
	}
);