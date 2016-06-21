angular.module('sbzApp')
	.controller('menadzer_kategorijeKupacaController', ['$scope', '$uibModal',
			function($scope, $uibModal){
			
		$scope.kategorijeKupaca = [{categoryCode:"1", name:"Kategorija 1", thresholds:
									[{id:"1", from:"10", to:"100", percentage:"10"}]},
									{categoryCode:"2", name:"Kategorija 2", thresholds:
										[{id:"1", from:"10", to:"100", percentage:"10"}]}];
		
		$scope.deleteCategory = function(catCode){
			
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
			    	  }
			      }
			});
		};
	}])
	
	.controller('menadzer_newCustomerController', ['$scope', '$uibModalInstance', 'modify', 'cat',
	            function($scope, $uibModalInstance, modify, cat){
		
		$scope.headerMsg = modify ? "Izmena kategorije: " + cat.name : "Dodavanje nove kategorije";
		
		$scope.customerCategory = {};
		if(modify){
			$scope.customerCategory = cat;
		}else{
			$scope.customerCategory.categoryCode = "";
			$scope.name = "";
			$scope.customerCategory.thresholds = [{id:"1", from:"10", to:"100", percentage:"10"},
			         		                     {id:"2", from:"10", to:"100", percentage:"20"}];
		}
		
		$scope.newThreshold = {from:"", to:"", percentage:""};
		
		$scope.addThreshold = function(){
			$scope.customerCategory.thresholds.push({id:$scope.newThreshold.id, 
													from:$scope.newThreshold.from, 
													to:$scope.newThreshold.to, 
													percentage:$scope.newThreshold.percentage});
		};
		
		$scope.deleteThreshold = function(t){
			$scope.customerCategory.thresholds.splice(t, 1);
		};
		
		$scope.cancel = function(){
			$uibModalInstance.close();
		};
		
		$scope.ok = function(){
			$uibModalInstance.close();
		};
	}]);