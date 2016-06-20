var sbzApp = angular.module('sbzApp', ['ngCookies', 'ngRoute','ngResource', 'ui.bootstrap']);

sbzApp.config(function($routeProvider){
				
				$routeProvider	
						.when(
							"/kupac",
							{
									templateUrl : "views/kupac.html"
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
							redirectTo: "/kupac"
							}	
						);
	}
);