(function() {
	// controller bound to application body, parent controller to all others
	angular.module("myApp").controller('indexController', indexController);
	
	//directive used to convert empty string values from input fields to 'undefined' values that backend expects
	angular.module("myApp").directive('emptyToNull', emptyToNull);
	
	function indexController($cookies, $scope, $http, $window, $location) {

		var vm = this;
		vm.perPage = 8;				//number of adverts per page
		vm.pages = [];				//adverts in currently displayed page
		
		// login and logout methods
		vm.logout = logout;
		vm.login = login;
		
		vm.selectPage = selectPage;
		vm.getAdvert = getAdvert;	//method used to fetch one page of adverts
		vm.countPages = countPages; //method used to count total pages of objects that fit the search parameter
		vm.loadLastSeen = loadLastSeen //method for displaying last seen adverts by the user
		vm.compare = compare;		//method used to sort pictures by url so that we display 
									//same image for the same estate
		checkIfLogged();

		// method for deleting user data - cookies
		function logout() {
			vm.loggedIn = false;
			vm.authority = null;
			
			var cookies = $cookies.getAll();
			for ( var x in cookies) {
				$cookies.remove(x);
			}
			
			$window.location = "#/";
			
		};

		// retrieving user token and saving it to cookie
		function login(userData) {
			$http.post('/api/users/login', userData).then(function(response) {
				// save user token to cookie
				$cookies.put("token", response.data.response);
				vm.loggedIn = true;
				//getUserData();
				$http.get("/api/users/data", {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
					// if status is ok - save user data to cookie
					$cookies.putObject('userdata', response.data);
					vm.authority = response.data.authority;//set user role to scope
					console.log(response.data);
					vm.loadLastSeen();
					$window.location = "#/";
				}, function(error) {
					// log error response and maybe send it to
					// error monitor app
					console.error("Error ocurred: " + response.status);
					
				});

			}, function(response) {
				//alert(response.data.response);
				console.log("Wrong username and password combination");
				toastr.error(response.data.response, 'Error');
			});
		}
		;
		
		//get if there is user cookie, if so - redirect user to profile page (#profile)
		function checkIfLogged(){
			
			if($cookies.get("token") != undefined){
				console.log("IF Logged");
				vm.loggedIn = true;
				vm.userData = $cookies.getObject("userdata");
				vm.authority = vm.userData.authority;
			}
			
		}
		
		//for adding active class to pagination
		function selectPage($event) {
			$($event.currentTarget).addClass("active").siblings().removeClass("active");
		}
		
		//count number of adverts in database so that we know how many pages we have
		function countPages() {
			var params = {"estate" : {"name" : vm.name, "city" : vm.city, "cityPart" : vm.cityPart, "address" : vm.address,
				"minArea" : vm.minArea, "maxArea" : vm.maxArea, "minPrice" : vm.minPrice, "maxPrice" : vm.maxPrice, 
				"heatingSystem" : vm.heating, "technicalEquipment" : vm.equipment}, "expiryDate" : vm.expiry,
				"publicationDate" : vm.publication};
			$http.post("/api/advertisement/count/", params, {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
				vm.pages = [];
				var pageNum = Math.floor((parseInt(response.data) + vm.perPage -1) / vm.perPage);
				console.log(pageNum);
				for(var i=1;i <= pageNum; i++)
					//page numbers for pagination buttons
					vm.pages.push(i);
				
			}, function(error) {
				// log error response and maybe send it to
				// error monitor app
				console.error("Error ocurred: " + response.status);
			});
		}
		
		//get adverts on specific page number and bind them to the scope
		function getAdvert(pageNumber){
			
			if($location.url() !== "/") {
				$window.location = "#/";
				console.log($location.url());
			}
			else {
				countPages();
				var params = {"estate" : {"name" : vm.name, "city" : vm.city, "cityPart" : vm.cityPart, "address" : vm.address,
					"minArea" : vm.minArea, "maxArea" : vm.maxArea, "minPrice" : vm.minPrice, "maxPrice" : vm.maxPrice, 
					"heatingSystem" : vm.heating, "technicalEquipment" : vm.equipment}, "expiryDate" : vm.expiry,
					"publicationDate" : vm.publication};
				
				$http.post("/api/advertisement/?" + "page=" + pageNumber + "&count=" + vm.perPage,
						params, {headers: {'X-Auth-Token': $cookies.get("token")}})
					.then(function (response){
						for(var i = 0; i < response.data.length; i++) {
							response.data[i].estate.images.sort(vm.compare);
						}
						vm.adverts = response.data;//list of my estates
						console.log(vm.adverts);
				 	}
				);
			}
		}
		
		//load last seen advertisements by the user
		function loadLastSeen() {
			
			if($scope.indexCtrl.authority) {
				$http.get("/api/view", { headers: { 'X-Auth-Token': $cookies.get("token")}})
					.then(function (response){
						for(var i = 0; i < response.data.length; i ++) {
							response.data[i].time = new Date(response.data[i].time);
							response.data[i].advert.estate.images.sort(vm.compare);
						}
						
						vm.lastSeen = response.data;  //list last seen adverts
				 	}
				);
			}
		}
		
		
		//sorting images in list by their url
		function compare(a,b) {
			if (a.url < b.url)
				return -1;
			if (a.url > b.url)
				return 1;
			return 0;
		}
		
	}
	
	function emptyToNull() {
		return {
	        restrict: 'A',
	        require: 'ngModel',
	        link: function (scope, elem, attrs, ctrl) {
	            ctrl.$parsers.push(function(viewValue) {
	                if(viewValue === "") {
	                    return undefined;
	                }
	                return viewValue;
	            });
	        }
	    };
	}

})();