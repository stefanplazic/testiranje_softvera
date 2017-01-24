(function() {
	angular.module("myApp").controller('registerCompanyController',
			registerCompanyController);
	

	function registerCompanyController($cookies, $http) {
		var vm = this;

		vm.showMap = false;
		vm.initMap = initMap;
		vm.sendRequest = sendRequest;
		
		function initMap() {
			
			vm.showMap = true;
			var geocoder = new google.maps.Geocoder();
			geocoder.geocode({
				'address' : vm.address
			}, function(results) {
				var latitude = results[0].geometry.location.lat();
				var longitude = results[0].geometry.location.lng();

				var latLng = new google.maps.LatLng(latitude, longitude);

				var mapDiv = document.getElementById('map');
				var map = new google.maps.Map(mapDiv, {
					center : latLng,
					zoom : 16,
					mapTypeId : google.maps.MapTypeId.ROADMAP
				});

				var marker = new google.maps.Marker({
					position : latLng,
					map : map,
					title : vm.address
				});
			});
		}

		function sendRequest() {
			var companyData = {
				"name" : vm.name,
				"address" : vm.address
			};

			$http.post('/api/advertiser/sendRequestForCompany', companyData, {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(
					function(result) {
						toastr.success(result.data.response, "Success");
					}, function(result) {
						toastr.error(result.data.response, 'Error');
					});

		}

	}

})();