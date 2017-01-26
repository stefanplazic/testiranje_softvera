(function() {
	angular.module("myApp").controller('companyController',
			companyController);

	// advertiser can call others to his company
	function companyController($http, $scope, $window, $cookies, $route, googleMap) {
		var vm = this;
		vm.selected = undefined;
		vm.typeSelect = typeSelect;
		vm.formatDate = formatDate;
		vm.acceptCall = acceptCall;
		vm.sendRequest = sendRequest;
		vm.initMap = initMap;
		getCompany();
		getCompanyList();

		function getUnemployed() {
			$http.get("api/advertiser/unemployed", {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {

			}, function(response) {
				vm.adverts = response.data;
			});
		}

		/**
		 * this function is triggered every single time , when we select value
		 * in searchbox
		 */
		function typeSelect($item, $model, $label) {
			console.log($item);
			vm.advertiser=$item;
			//sendRequest($item);// send data to server
		}
		function initMap(){
			googleMap.showMap(document.getElementById('map1'), vm.myCompany.address)
		}
		/**
		 * this function enables to see if user is working in any company
		 */
		function getCompany() {
			$http.get("api/advertiser/getCompany", {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				if(response.data.name==null)
					vm.hasCompany = false;
				else
					vm.hasCompany = true;// if true, show search bar to user
				vm.myCompany = response.data;
				getUnemployed();// call the population function
			}, function(response) {
				//error send to monitor
			});
		}

		/**
		 * send request to user to start working in company.
		 */
		function sendRequest() {
			if(vm.advertiser === undefined)
				return;
			
			$http.post('api/advertiser/callToCompany', vm.advertiser, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(result) {
				
				if (result) {
					toastr.success(result.data.response, "Success");
				}
			}, function(result) {
				console.log("Error");
				toastr.error(result.data.response, "Error");
			});
		}

		/**
		 * fetch list of all company invitations
		 */
		function getCompanyList() {
			$http.get('api/advertiser/allCalls', {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
			}, function(response) {
				vm.callList = response.data;
				console.log(vm.callList);
			});
		}
		
		function formatDate(myDate){
			var date = new Date(myDate);
			return date.getUTCDate()+"/"+ date.getUTCMonth()+1 +"/"+date.getFullYear();
		}
		
		function acceptCall(companyCall){
			console.log(companyCall);
			$http.post('api/advertiser/acceptCall', companyCall, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(result) {
				toastr.success(result.data.response, "Success");
				$route.reload();
				
			}, function(result) {
				toastr.error(result.data.response, "Error");
			});
			
		}

	}
})();