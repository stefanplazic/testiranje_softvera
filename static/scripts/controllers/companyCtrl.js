(function() {
	angular.module("myApp").controller('companyController', companyController);

	function companyController($cookies, $scope, $http, $window) {
		
		var vm = this;
		vm.userData = $cookies.getObject("userdata");
		
		$http.get("/api/advertiser/getCompany", {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
			vm.company = response.data;
		}, function(error) {
			// log error response and maybe send it to
			// error monitor app
			
			console.error("Error ocurred: " + response.status);
		});
	}

}


)();