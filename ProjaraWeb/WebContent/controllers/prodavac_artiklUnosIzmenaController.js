angular.module('sbzApp')
	.controller('prodavac_artiklUnosIzmenaController', ['$rootScope', '$scope', '$location', 'items', '$uibModalInstance', '$http', '$cookies',
		function($rootScope, $scope, $location, items, $uibModalInstance,  $http, $cookies){
		
			if($cookies.get("prodavacID") == undefined){
				$location.path('/prijava');
			}
			else{
				$scope.user.username = $cookies.get("prodavacID");
			};	
		
			$scope.original = items;
			$scope.artikl = angular.copy(items);
			$scope.izmena = false; //potrebno zbog disable-ovanja id polja, a i za razlikovanje add od update
			
			if ($scope.artikl.info.id == "") {
				$scope.naslovnaPoruka = "Unos novog artikla";
				$scope.izmena = false;
			}
			else {
				$scope.naslovnaPoruka = "Izmena artikla";
				$scope.izmena = true;
			}
				
			console.log("Izmena " + $scope.izmena);
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
				// TODO : rest poziv za update ili add	
				var fd = new FormData();
		  		
		  		fd.append("image", $scope.image);
		  		fd.append("format", $scope.format);
		  		fd.append("name",$scope.artikl.info.name);
		  		fd.append("category",$scope.artikl.category);
		  		fd.append("cost",$scope.artikl.info.cost);
		  		fd.append("inStock",$scope.artikl.info.inStock);
		  		fd.append("minQuantity",$scope.artikl.info.minQuantity);
		  		
				if ($scope.izmena == false) {
					console.log("Dodavanje...");
					$http.post("http://localhost:8080/ProjaraWeb/rest/items/add", fd, {
			  			withCredentials: true,
			  	        headers: {'Content-Type': undefined },
			  	        transformRequest: angular.identity
			  		 })
			  		    .success(function(response, status, headers, config) {
			  		           console.log(response);

			  		 })
			  		    .error(function(error, status, headers, config) {
			  		           console.log(error);

			  		  });
					
					$uibModalInstance.close();
				} else {
					
				}
				
				
				
//				console.log("Adresa " + "http://localhost:8080/ProjaraWeb/rest/items/" + $scope.artikl.info.id);
//				//Azuriranje reda u tabeli artikala
//	 			$http({
//					method: "GET", 
//					url : "http://localhost:8080/ProjaraWeb/rest/items/" + $scope.artikl.info.id,
//				}).then(function(value) {
//					console.log("dopuna");
//					console.log(value.data);
//					$scope.original = value.data;
//				}, function(reason){
//					console.log(JSON.stringify(reason));
//				});
//				$uibModalInstance.close();
				
			};
			
			$scope.zatvori = function(){
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
	  		     	
 		     		$scope.json.image = input.files[0];
 		     		$scope.json.format = input.files[0].name.split('.')[1];
 		     		console.log($scope.json);
 		     	};

 		     	dataBinReader.readAsArrayBuffer(input.files[0]); 
 		     };
				
		}
	]);