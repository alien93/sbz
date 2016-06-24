angular.module('sbzApp')
	.controller('menadzer_kategorijeArtikalaController', ['$scope', '$uibModal', '$http',
	      function($scope, $uibModal, $http){
		$scope.kategorijeArtikala = [];/*[{code:"code1", name:"name1", maxDiscount:20, parentCategory:null},
									 {code:"code2", name:"name2", maxDiscount:30, parentCategory:"code1"}];*/
		
		$scope.refreshCat = function(){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategory/allCat",
				type : "get"
			}).then(function(result){
				$scope.kategorijeArtikala = result.data;
			}, function(err){
				console.log(JSON.stringify(err));
			});
		};
		
		$scope.refreshCat();
		
		$scope.addCategory = function(){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategory/allCat",
				type : "get"
			}).then(function(result){
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
				    		  return result.data;
				    	  }
				      }
				});
			}, function(err){
				console.log(JSON.stringify(err));
			});
		};
		
		$scope.modifyCategory = function(cat){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategory/parentCat/" + cat.info.code,
				type : "get"
			}).then(function(result){
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
				    		  return result.data;
				    	  }
				      }
				});
					  
			}, function(err){
				console.log(JSON.stringify(err));
			});
			
		};
		
		$scope.deleteCategory = function(catCode){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategory/delete",
				method : "post",
				data : catCode
			}).then(function(result){
				if(result.data.msg == "OK") $scope.refreshCat();
			}, function(err){
				console.log(JSON.stringify(err));
			});
		};
		
		$scope.getParentCat = function(cat){
			if(cat == null) return $scope.kategorijeArtikala;
			else{
				var ret = [null];
				for(var i = 0; i < $scope.kategorijeArtikala.length; i++){
					if(cat.info.code != $scope.kategorijeArtikala[i].info.code) ret.push($scope.kategorijeArtikala[i]);
				}
				return ret;
			}
		};
		
	}])
	
	.controller('menadzer_newItemController', ['$scope', '$uibModalInstance', 'modify', 'cat', 'parentScope', 'categories', '$http',
	            function($scope, $uibModalInstance, modify, cat, parentScope, categories, $http){
		
		$scope.headerMsg = modify ? "Izmena kategorije: " + cat.info.name : "Dodavanje nove kategorije";
		$scope.errorMsg = "";
		$scope.disableId = modify;
		
		$scope.categories = [null];
		for(var i = 0; i < categories.length; i++){
			$scope.categories.push({"name":categories[i].info.name, "code":categories[i].info.code});
		}
		
		$scope.itemCategory = {};
		if(modify){
			$scope.itemCategory = cat;
		}else{
			$scope.itemCategory.info = {};
			$scope.itemCategory.info.code = "";
			$scope.itemCategory.info.name = "";
			$scope.itemCategory.info.maxDiscount = null;
			$scope.itemCategory.parentCode = null;
		}
		
		$scope.cancel = function(){
			$uibModalInstance.close();
		};
		
		$scope.ok = function(){
			if(!$scope.f1.$valid) return;
			if($scope.itemCategory.info.code.trim().length > 3){
				$scope.errorMsg = "Maksimalan broj karaktera za kod kategorije je: 3";
				return;
			}else{
				$scope.itemCategory.info.code = $scope.itemCategory.info.code.trim();
			}
			if($scope.itemCategory.info.maxDiscount < 0.0 || $scope.itemCategory.info.maxDiscount > 100.0){
				$scope.errorMsg = "Neispravna vrednost za maksimalan popust.";
				return;
			}
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategory/add/" + (modify ? "modify" : "add"),
				method : "post",
				data : $scope.itemCategory
			}).then(function(result){
				if(result.data.msg == "OK"){
					parentScope.refreshCat();
					$uibModalInstance.close();
				}
				else $scope.errorMsg = result.data.msg;
			}, function(err){
				console.log(JSON.stringify(err));
			});
		};
	}]);