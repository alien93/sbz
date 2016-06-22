angular.module('sbzApp')
	.controller('menadzer_kategorijeArtikalaController', ['$scope', '$uibModal', '$http',
	      function($scope, $uibModal, $http){
		$scope.kategorijeArtikala = [{code:"code1", name:"name1", maxDiscount:20, parentCategory:null},
									 {code:"code2", name:"name2", maxDiscount:30, parentCategory:"code1"}];
		
		$scope.addCategory = function(){
			$uibModal.open({
				  animation: false,
				  templateUrl : "views/menadzer_newCatItem.html",
				  controller: 'menadzer_newItemController',
			      resolve: {
			    	  modify : function(){
			    		  return false;
			    	  },
			    	  cat : function(){
			    		  return null;
			    	  },
			    	  parentScope : function(){
			    		  return $scope;
			    	  },
			    	  categories : function(){
			    		  return $scope.getParentCat(null);
			    	  }
			      }
			});
		};
		
		$scope.modifyCategory = function(cat){
			$uibModal.open({
				  animation: false,
				  templateUrl : "views/menadzer_newCatItem.html",
				  controller: 'menadzer_newItemController',
			      resolve: {
			    	  modify : function(){
			    		  return true;
			    	  },
			    	  cat : function(){
			    		  return cat;
			    	  },
			    	  parentScope : function(){
			    		  return $scope;
			    	  },
			    	  categories : function(){
			    		  return $scope.getParentCat(cat);
			    	  }
			      }
			});
		};
		
		$scope.deleteCategory = function(catCode){
			
		};
		
		$scope.getParentCat = function(cat){
			if(cat == null) return $scope.kategorijeArtikala;
			else{
				var ret = [null];
				for(var i = 0; i < $scope.kategorijeArtikala.length; i++){
					if(cat.code != $scope.kategorijeArtikala[i].code) ret.push($scope.kategorijeArtikala[i]);
				}
				return ret;
			}
		};
		
	}])
	
	.controller('menadzer_newItemController', ['$scope', '$uibModalInstance', 'modify', 'cat', 'parentScope', 'categories', '$http',
	            function($scope, $uibModalInstance, modify, cat, parentScope, categories, $http){
		
		$scope.headerMsg = modify ? "Izmena kategorije: " + cat.name : "Dodavanje nove kategorije";
		
		$scope.categories = categories;
		
		$scope.itemCategory = {};
		if(modify){
			$scope.itemCategory = cat;
		}else{
			$scope.itemCategory.code = "";
			$scope.itemCategory.name = "";
			$scope.itemCategory.maxDiscount = null;
			$scope.itemCategory.parentCategory = null;
		}
		
		$scope.cancel = function(){
			$uibModalInstance.close();
		};
		
		$scope.ok = function(){
			console.log($scope.itemCategory.parentCategory);
		};
	}]);