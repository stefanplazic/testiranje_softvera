(function() {
	angular.module("myApp").controller('callToCompanyController',
			callToCompanyController);

	// advertiser can call others to his company
	function callToCompanyController($http, $scope, $window, $cookies) {
		var vm = this;
		vm.selected = undefined;
		vm.typeSelect = typeSelect;
		vm.getCompanyList = getCompanyList;
		getCompany();

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
			sendRequest($item);// send data to server
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
				vm.hasCompany = true;// if true, show search bar to user
				vm.myCompany = response.data;
				getUnemployed();// call the population function
			}, function(response) {
				console.log("Not found company");
				vm.hasCompany = false;
			});
		}

		/**
		 * send request to user to start working in company.
		 */
		function sendRequest(advertiser) {
			var userData = {
				"username" : vm.username,
				"password" : vm.pass,
				"email" : vm.email,
				"firstName" : vm.firstName,
				"lastName" : vm.lastName
			};
			$http.post('api/advertiser/callToCompany', advertiser, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				if (response) {
					alert(response.data.response);
				}
			}, function(response) {
				alert(response.data.response);
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

	}
})();