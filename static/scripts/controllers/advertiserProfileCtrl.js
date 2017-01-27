(function() {
	angular.module("myApp").controller('advertiseProfileController',
			advertiseProfileController);

	// login page controller
	function advertiseProfileController($http, $scope, $cookies, $window,
			$routeParams) {

		var vm = this;
		vm.id = $routeParams.id;
		vm.fetchData = fetchData;
		vm.countRate = countRate;
		vm.fetchData();

		/**
		 * try to fetch data about advertiser
		 * 
		 */
		function fetchData() {
			$http.get('api/advertiser/profile/' + vm.id, {}).then(
					function(response) {

						vm.advertData = response.data;
						console.log(vm.advertData);
					}, function(response) {
						console.log("Error in fetching");
					})
		}

		/**
		 * using rate argument - calculates how many times myrate appear in
		 * vm.advertData.rates array
		 */
		function countRate(myrate) {
			if (vm.advertData != null) {
				var ratesArray = vm.advertData.rates
				var count = 0;

				for (var i = 0; i < ratesArray.length; i++) {
					if (ratesArray[i].rate == myrate)
						count++; // increment by one
				}

				return count;
			}
		}

	}
})();