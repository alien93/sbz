angular.module('sbzApp').
	controller('menadzer_infoController', ['$scope', '$http', '$uibModalInstance', 'user',
	     function($scope, $http, $uibModalInstance, user){
		
		$scope.headerMsg = "Vaši korisnički podaci";
		$scope.user = {"username":user.username, "password":user.password, "firstName":user.firstName, "lastName":user.lastName};
		
		$scope.sacuvaj = function(){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/customerCategory/getUser/" + $scope.user.username +"/"+ $scope.user.password + 
				"/" + $scope.user.firstName +  "/" + $scope.user.lastName + "/true",
				method : "get"
			}).then(function(result){
			}, function(err){
				console.log(JSON.stringify(err));
			});
			$uibModalInstance.close();
		};
		
	}]);