angular.module('sbzApp')
	.controller('menadzer_akcijeController', ['$rootScope', '$scope', '$http', '$uibModal', '$filter',
	        function($rootScope, $scope, $http, $uibModal, $filter){
		
		
		$scope.akcije = [];/*{id:1, name:"Novogodisnja akcija", from:new Date(), until:new Date(), discount:20, 
						categories: [{code:"code1", name:"name1", maxDiscount:20, parentCategory:null},
									 {code:"code2", name:"name2", maxDiscount:30, parentCategory:"code1"}]},
		                 {id:2, name:"Bozicna akcija", from:new Date(), until:new Date(), discount:30,
		                categories: [{code:"code1", name:"name1", maxDiscount:20, parentCategory:null},
									 {code:"code2", name:"name2", maxDiscount:30, parentCategory:"code1"},
									 {code:"code3", name:"name3", maxDiscount:30, parentCategory:"code1"}]}];*/
		
		$scope.refreshActions = function(){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/actionEvent/getAll", 
				method : "get"
			}).then(function(result){
				console.log(JSON.stringify(result.data));
				$scope.akcije = result.data;
			}, function(err){
				console.log(JSON.stringify(err));
			});
		};
		
		$scope.refreshActions();
		
		$scope.addAction = function(){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/actionEvent/getItemCat/-1",
				method : "get"
			}).then(function(result){
				$uibModal.open({
					  animation: false,
					  templateUrl : "views/menadzer_newActionEvent.html",
					  controller: 'menadzer_newActionController',
				      resolve: {
				    	  modify : function(){
				    		  return false;
				    	  },
				    	  action : function(){
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
		
		$scope.filterDate = function(date){
			return $filter('date')(date, "yyyy-MM-dd"); 
		};
		
		$scope.modifyAction = function(action){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/actionEvent/getItemCat/" + action.info.id,
				method : "get"
			}).then(function(result){
				$uibModal.open({
					  animation: false,
					  templateUrl : "views/menadzer_newActionEvent.html",
					  controller: 'menadzer_newActionController',
				      resolve: {
				    	  modify : function(){
				    		  return true;
				    	  },
				    	  action : function(){
				    		  return action;
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
		
	}])
	
	.controller('menadzer_newActionController', ['$scope', '$uibModalInstance', '$http', 'parentScope', 'action', 'modify', 'categories',
	        function($scope, $uibModalInstance, $http, parentScope, action, modify, categories){
		
		$scope.headerMsg = modify ? "Izmena akcije: " + action.name : "Dodavanje nove akcije";
		$scope.selectedCategory = {};
		$scope.categories = categories;
		
		$scope.minDate = new Date();
		$scope.parentScope = parentScope;
		$scope.selectedCategory = null;
		
		$scope.newAction = {"info":{}};
		if(modify){
			$scope.newAction = action;
			$scope.newAction.info.from = new Date(action.info.from);
			$scope.newAction.info.until = new Date(action.info.until);
		}else{
			$scope.newAction.info.id = null;
			$scope.newAction.info.name = "";
			$scope.newAction.info.from = "";
			$scope.newAction.info.until = "";
			$scope.newAction.info.dicount = "";
			$scope.newAction.categories = [];
		}
		
		$scope.addCat = function(){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/actionEvent/getSubCat/" + $scope.selectedCategory,
				method : "get"
			}).then(function(result){
				for(var i = 0; i < result.data.length; i++){
					$scope.replace(result.data[i].code, $scope.newAction.categories, $scope.categories);
				}
				$scope.selectedCategory = null;
			}, function(err){
				console.log(JSON.stringify(err));
			});
		};
		
		$scope.removeCat = function(cat){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/actionEvent/getSubCat/" + cat.code,
				method : "get"
			}).then(function(result){
				for(var i = 0; i < result.data.length; i++){
					$scope.replace(result.data[i].code, $scope.categories, $scope.newAction.categories);
				}
				$scope.selectedCategory = null;
			}, function(err){
				console.log(JSON.stringify(err));
			});
		};
		
		$scope.replace = function(code, c_push, c_delete){
			var cat = null;
			var index = -1;
			for(var i = 0; i < c_delete.length; i++){
				if(code == c_delete[i].code){
					cat = c_delete[i];
					index = i;
					break;
				}
			}
			if(cat != null){
				c_delete.splice(index, 1);
				c_push.push(cat);
			}
		};
		
		$scope.ok = function(){
			$http({
				url : "http://localhost:8080/ProjaraWeb/rest/actionEvent/add/" + (modify ? "modify" : "add"),
				method : "post", 
				data : $scope.newAction
			}).then(function(result){
				$scope.parentScope.refreshActions();
			}, function(err){
				console.log(JSON.stringify(err));
			});
			$uibModalInstance.close();
		};
		
		$scope.cancel = function(){
			$uibModalInstance.close();
		};
		
	}]);