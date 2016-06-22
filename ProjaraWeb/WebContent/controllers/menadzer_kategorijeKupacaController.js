angular.module('sbzApp')
	.controller('menadzer_kategorijeKupacaController', ['$scope', '$uibModal', '$http',
			function($scope, $uibModal, $http){
		
		$scope.refreshCat = function(){
			$http({
				url: "http://localhost:8080/ProjaraWeb/rest/customerCategory/getAll",
				type : "get"
			}).then(function(result){
				$scope.kategorijeKupaca = result.data;
				console.log(JSON.stringify(result.data));
			}, function(err){
				alert(JSON.stringify(err));
			});
		};
		$scope.refreshCat();
		
		$scope.deleteCategory = function(catCode){
			$http({
				url: "http://localhost:8080/ProjaraWeb/rest/customerCategory/delete/" + catCode,
				type : "get",
			}).then(function(result){
				$scope.refreshCat();
			}, function(err){
				alert(JSON.stringify(err));
			});
		};
		
		$scope.modifyCategory = function(cat){
			$uibModal.open({
				  animation: false,
				  templateUrl : "views/menadzer_newCatCustomer.html",
				  controller: 'menadzer_newCustomerController',
			      resolve: {
			    	  modify : function(){
			    		  return true;
			    	  },
			    	  cat : function(){
			    		  return cat;
			    	  },
			    	  parentScope : function(){
			    		  return $scope;
			    	  }
			      }
			});
		};
		
		$scope.addCategory = function(){
			$uibModal.open({
				  animation: false,
				  templateUrl : "views/menadzer_newCatCustomer.html",
				  controller: 'menadzer_newCustomerController',
			      resolve: {
			    	  modify : function(){
			    		  return false;
			    	  },
			    	  cat : function(){
			    		  return null;
			    	  },
			    	  parentScope : function(){
			    		  return $scope;
			    	  }
			      }
			});
		};
	}])
	
	.controller('menadzer_newCustomerController', ['$scope', '$uibModalInstance', 'modify', 'cat', 'parentScope', '$http',
	            function($scope, $uibModalInstance, modify, cat, parentScope, $http){
		
		$scope.headerMsg = modify ? "Izmena kategorije: " + cat.name : "Dodavanje nove kategorije";
		$scope.errorMsg = "";
		
		$scope.customerCategory = {};
		if(modify){
			$scope.customerCategory = cat;
		}else{
			$scope.customerCategory.categoryCode = "";
			$scope.name = "";
			$scope.customerCategory.thresholds = [];/*[{id:"1", from:"10", to:"100", percentage:"10"},
			         		                     {id:"2", from:"10", to:"100", percentage:"20"}];*/
		}
		
		$scope.newThreshold = {id: null, from:null, to:null, percentage:null};
		
		$scope.addThreshold = function(){
			if($scope.newThreshold.from == null || 
					$scope.newThreshold.to == null || 
					$scope.newThreshold.percentage== null) return;
			
			if($scope.newThreshold.from < 0 || 
					$scope.newThreshold.to < 0 || 
					$scope.newThreshold.percentage < 0) return;
			
			if($scope.newThreshold.percentage > 100) return;
			
			$scope.customerCategory.thresholds.push({id:$scope.newThreshold.id, 
													from:$scope.newThreshold.from, 
													to:$scope.newThreshold.to, 
													percentage:$scope.newThreshold.percentage});
			$scope.newThreshold = {id: null, from:null, to:null, percentage:null};
		};
		
		$scope.deleteThreshold = function(t){
			$scope.customerCategory.thresholds.splice(t, 1);
		};
		
		$scope.cancel = function(){
			$uibModalInstance.close();
		};
		
		$scope.ok = function(){
			if($scope.customerCategory.categoryCode.trim().length > 1){
				$scope.errorMsg = "Dozvoljen broj karaktera za oznaku kategorije je: 1";
				return;
			}else{
				$scope.customerCategory.categoryCode = $scope.customerCategory.categoryCode.trim();
				$scope.customerCategory.name = $scope.customerCategory.name.trim();
			}
			$http({ 
				url : "http://localhost:8080/ProjaraWeb/rest/customerCategory/add/" + (modify ? "modify" : "add"),
			    method : "POST",
			    data : $scope.customerCategory
			}).then(function(result){
				if(result.data.msg == "OK"){
					parentScope.refreshCat();
					$uibModalInstance.close();
				}else{
					$scope.errorMsg = result.data.msg;
				}
			}, function(err){
				alert(JSON.stringify(err));
			});
		};
	}]);