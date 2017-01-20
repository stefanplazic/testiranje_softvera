(function () {
	angular.module("myApp").controller('searchController', searchController);
	//directive used to convert empty string values from input fields to 'undefined' values that backend expects
	angular.module("myApp").directive('emptyToNull', emptyToNull);
	
	function searchController($cookies, $http, $window, $scope) {
		
		var vm = this;
		
		vm.getAdvert = getAdvert;	//method used to fetch one page of adverts
		vm.perPage = 8;				//number of adverts per page
		vm.pages = [];				//adverts in currently displayed page
		
		if ($cookies.getObject('userdata') === undefined)
			$window.location="#/login";

		$scope.indexCtrl.loggedIn = true;
		vm.userData = $cookies.getObject('userdata');
		
		//count number of adverts in database so e know how many pages we have
		$http.get("/api/advertisement/count/", {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
			var pageNum = Math.floor((parseInt(response.data) + vm.perPage -1) / vm.perPage);
			for(var i=1;i <= pageNum; i++)
				//page numbers for pagination buttons
				vm.pages.push(i);
			//display adverts on page index 0
			getAdvert(0);
		}, function(error) {
			// log error response and maybe send it to
			// error monitor app
			console.error("Error ocurred: " + response.status);
		});
		
		//get adverts on specific page number and bind them to the scope
		function getAdvert(pageNumber){
			var params = {"estate" : {"name" : vm.name, "city" : vm.city, "cityPart" : vm.cityPart, "address" : vm.address,
				"minArea" : vm.minArea, "maxArea" : vm.maxArea, "minPrice" : vm.minPrice, "maxPrice" : vm.maxPrice, 
				"heatingSystem" : vm.heating, "technicalEquipment" : vm.equipment}, "expiryDate" : vm.expiry,
				"publicationDate" : vm.publication};
			
			$http.post("/api/advertisement/?" + "page=" + pageNumber + "&count=" + vm.perPage,
					params, { headers: { 'X-Auth-Token': $cookies.get("token") } })
				.then(function (response){
					vm.adverts = response.data;//list of my estates
			 		console.log(vm.adverts);
			 	}
			);
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