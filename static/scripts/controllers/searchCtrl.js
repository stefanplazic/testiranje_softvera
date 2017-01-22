(function () {
	angular.module("myApp").controller('searchController', searchController);
	//directive used to convert empty string values from input fields to 'undefined' values that backend expects
	angular.module("myApp").directive('emptyToNull', emptyToNull);
	
	function searchController($cookies, $http, $window, $scope) {
		
		var vm = this;
		
		vm.perPage = 8;				//number of adverts per page
		vm.pages = [];				//adverts in currently displayed page
		
		vm.getAdvert = getAdvert;	//method used to fetch one page of adverts
		vm.countPages = countPages; //method used to count total pages of objects that fit the search parameter
		
		countPages();
		getAdvert(0);
		
		//get adverts on specific page number and bind them to the scope
		function getAdvert(pageNumber){
			countPages();
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