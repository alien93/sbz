angular.module('sbzApp')
	.controller('prijava_Controller', ['$scope', 
			function($scope){
		
				$scope.error = false;
				$scope.errorMessage = "";
				
				$scope.roles = ["KUPAC", "PRODAVAC", "MENADZER"];
				$scope.user = { username: "pera", 
								password: "pera", 
								role: $scope.roles[0]
							  };
				
				$scope.login = function() {
					console.log('Prijava');
				};
		

			}
	]);