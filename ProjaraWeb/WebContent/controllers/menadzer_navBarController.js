/**
 * 
 */
angular.module('sbzApp')
	.controller('menadzer_navBarController', ['$rootScope', '$scope', '$location', '$cookies', '$uibModal', '$http',
			function($rootScope, $scope, $location, $cookies, $uibModal, $http){
		
			//pokupi oznaku ulogovanog menadzera
			if($cookies.get("menadzerID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.menadzerID = $cookies.get("menadzerID");
			}
		
		
			$scope.kupci = function(){
				$location.path('/menadzer/kupci');
			};
			
			$scope.kupci();
			
			$scope.artikli = function(){
				$location.path('/menadzer/artikli');
			};
			
			$scope.akcije = function(){
				$location.path('/menadzer/akcije');
			};
			
			$scope.logout = function(){
				$cookies.remove("menadzerID");
				$location.path('/prijava');
			};
			
			$scope.menadzerInfo = function(){
				$http({
					url : "http://localhost:8080/ProjaraWeb/rest/customerCategory/getUser/" + $cookies.get("menadzerID") +"/_/_/_/false",
					method : "get"
				}).then(function(result){
					$uibModal.open({
						  animation: false,
						  templateUrl : "views/menadzer_info.html",
						  controller: 'menadzer_infoController',
					      resolve: {
					    	 user : function(){
					    		 return result.data;
					    	 }
					      }
					});
				}, function(err){
					console.log(JSON.stringify(err));
				});
			};
	}]);