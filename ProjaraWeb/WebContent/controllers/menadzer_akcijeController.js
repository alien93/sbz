angular.module('sbzApp')
	.controller('menadzer_akcijeController', ['$scope', '$http', '$uibModal', '$filter',
	        function($scope, $http, $uibModal, $filter){
		$scope.akcije = [{id:1, name:"Novogodisnja akcija", from:new Date(), until:new Date(), discount:20, 
						categories: [{code:"code1", name:"name1", maxDiscount:20, parentCategory:null},
									 {code:"code2", name:"name2", maxDiscount:30, parentCategory:"code1"}]},
		                 {id:2, name:"Bozicna akcija", from:new Date(), until:new Date(), discount:30,
		                categories: [{code:"code1", name:"name1", maxDiscount:20, parentCategory:null},
									 {code:"code2", name:"name2", maxDiscount:30, parentCategory:"code1"},
									 {code:"code3", name:"name3", maxDiscount:30, parentCategory:"code1"}]}];
		
		$scope.addAction = function(){
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
			    	  }
			      }
			});
		};
		
		$scope.filterDate = function(date){
			return $filter('date')(date, "yyyy-MM-dd"); 
		};
		
		$scope.deleteAction = function(actionId){
			
		};
		
		$scope.modifyAction = function(action){
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
			    	  }
			      }
			});
		};
		
	}])
	
	.controller('menadzer_newActionController', ['$scope', '$uibModalInstance', '$http', 'parentScope', 'action', 'modify',
	        function($scope, $uibModalInstance, $http, parentScope, action, modify){
		
		$scope.headerMsg = modify ? "Izmena akcije: " + action.name : "Dodavanje nove akcije";
		
		$scope.newAction = {};
		if(modify){
			$scope.newAction = action;
		}else{
			$scope.newAction.id = null;
			$scope.newAction.name = "";
			$scope.newAction.from = "";
			$scope.newAction.until = "";
			$scope.newAction.discount = "";
		}
		
		$scope.ok = function(){
			$uibModalInstance.close();
		};
		
		$scope.cancel = function(){
			$uibModalInstance.close();
		};
		
	}]);