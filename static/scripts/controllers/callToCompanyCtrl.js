(function() {
	angular.module("myApp").controller('callToCompanyController',
			callToCompanyController);

	// advertiser can call others to his company
	function callToCompanyController($http, $scope, $window, $cookies) {
		var vm = this;
		vm.selected = undefined;
		vm.typeSelect = typeSelect;
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
		 * this function is triggered every single time , when we select value in searchbox
		 */
		function typeSelect($item, $model, $label) {console.log($item);}
		
		function getCompany() {$http.get("api/advertiser/getCompany", {
				headers : {'X-Auth-Token' : $cookies.get("token")}})
				.then(function(response) {
					vm.hasCompany = true;//if true, show search bar to user
					vm.myCompany = response.data;
					getUnemployed();//call the population function
				}, 
						function(response) {console.log("Not found company"); 
				vm.hasCompany = false;		
				});
		}

	}
})();