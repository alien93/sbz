angular.module('sbzApp')
	.controller('prodavac_artiklUnosIzmenaController', ['$rootScope', '$scope', '$location', 'items', 'index', '$uibModalInstance', '$http', 'parentScope', '$cookies',
		function($rootScope, $scope, $location, items, index, $uibModalInstance, $http, parentScope, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};	
		
			$scope.original = items;
			$scope.artikl = angular.copy(items);
			$scope.izmena = false; //potrebno zbog disable-ovanja id polja, a i za razlikovanje add od update
			
			$scope.image = "";
			$scope.format = "";
			
			
			if ($scope.artikl.info.id == "") {
				$scope.naslovnaPoruka = "Unos novog artikla";
				$scope.izmena = false;
			}
			else {
				console.log($scope.artikl);
				$scope.naslovnaPoruka = "Izmena artikla";
				$scope.izmena = true;
			}
				
			$scope.sveKategorije = [];
			$http({
				method: "GET", 
				url : "http://localhost:8080/ProjaraWeb/rest/itemCategory/allCat",
			}).then(function(value) {
				console.log("sve kategorije");
				for (var i = 0; i < value.data.length; i++) {
					$scope.sveKategorije.push(value.data[i]);	
					if ($scope.artikl.category.name == $scope.sveKategorije[i].info.name) {
						$scope.artikl.category = $scope.sveKategorije[i];
					} 
				}
				
				if ($scope.artikl.category.name == "") {
					$scope.artikl.category = $scope.sveKategorije[0];
				} 
			});	
			
			
			$scope.potvrdi = function(){				
				var fd = new FormData();
		  		
		  		fd.append("image", $scope.imageBin);
		  		fd.append("format", $scope.format);
		  		fd.append("name",$scope.artikl.info.name);
		  		fd.append("category",$scope.artikl.category.info.code);
		  		fd.append("cost",$scope.artikl.info.cost);
		  		fd.append("inStock",$scope.artikl.info.inStock);
		  		fd.append("minQuantity",$scope.artikl.info.minQuantity);
		  		
				if ($scope.izmena == false) {
					$http.post("http://localhost:8080/ProjaraWeb/rest/items/add", fd, {
			  			withCredentials: true,
			  	        headers: {'Content-Type': undefined },
			  	        transformRequest: angular.identity
			  		 })
			  		    .success(function(response, status, headers, config) {
			  		           console.log(response);
			  		           parentScope.ucitajPonovo();

			  		 })
			  		    .error(function(error, status, headers, config) {
			  		           console.log(error);

			  		  });
				} else {
					fd.append("id", $scope.artikl.info.id);
					
					$http.post("http://localhost:8080/ProjaraWeb/rest/items/update", fd, {
			  			withCredentials: true,
			  	        headers: {'Content-Type': undefined },
			  	        transformRequest: angular.identity
			  		 })
			  		    .success(function(response, status, headers, config) {
			  		           console.log(response);
			  		     parentScope.refreshReda(index, $scope.artikl);

			  		 })
			  		    .error(function(error, status, headers, config) {
			  		           console.log(error);

			  		  });
					
				}

	 			
				$uibModalInstance.close();
				
			};
			
			$scope.zatvori = function(){
				if ($scope.izmena == false)
					parentScope.ukloniRed(index);
				$uibModalInstance.close();
			};
			
			 //UCITAVANJE SLIKE
 		     $scope.openFile = function(event){
 		     	console.log(event);
 		     	var input = event.target;

 		     	//ucitava za preview
 		     	var dataURLreader = new FileReader();
 		     	dataURLreader.onload = function(file){     
 		     	  var dataURL = dataURLreader.result;

 		     	  var output = document.getElementById('output');

 		     	  $scope.image = dataURL;
 		     	  $scope.format = input.files[0].name.split('.')[1];

 		     	  output.src = dataURL;
 		     	  
 		     	};
 		     	
 		     	dataURLreader.readAsDataURL(input.files[0]);

 		     	//ucitava za slanje
 		     	var dataBinReader = new FileReader();
 		     	dataBinReader.onload = function(){
	  		     	
 		     		$scope.imageBin = input.files[0];
 		     		$scope.format = input.files[0].name.split('.')[1];
 		     	};

 		     	dataBinReader.readAsArrayBuffer(input.files[0]); 
 		     };
 		     
				
		}
	]);