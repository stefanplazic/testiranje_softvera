(function() {
	angular.module("myApp").controller('registerCompanyController',
			registerCompanyController);
	

	function registerCompanyController($cookies, $http, $route, googleMap) {
		var vm = this;

		vm.showMap = false;
		vm.initMap = initMap;
		vm.sendRequest = sendRequest;
		
		function initMap() {
			
			vm.showMap = true;
			googleMap.showMap(document.getElementById('map'), vm.address);
			
		}

		function sendRequest() {
			var companyData = {
				"name" : vm.name,
				"address" : vm.address
			};

			$http.post('/api/advertiser/sendRequestForCompany', companyData, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(result) {
				toastr.success(result.data.response, "Success");
				$route.reload();
			}, function(result) {
				toastr.error(result.data.response, 'Error');
			});

		}

	}

})();