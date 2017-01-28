(function() {
	angular.module("myApp").controller('advertiseProfileController',
			advertiseProfileController);

	// login page controller
	function advertiseProfileController($http, $scope, $cookies, $window,
			$routeParams) {

		var vm = this;
		vm.rate = 0;
		vm.max = 5;
		vm.voted = true;
		vm.id = $routeParams.id;
		vm.fetchData = fetchData;
		vm.countRate = countRate;
		vm.rateMe = rateMe;
		vm.ratingStates = [ {
			stateOn : 'glyphicon-star',
			stateOff : 'glyphicon-star-empty'
		} ];
		vm.fetchData();
		loadAdvertisment();

		/**
		 * try to fetch data about advertiser
		 * 
		 */
		function fetchData() {
			$http.get('api/advertiser/profile/' + vm.id, {}).then(
					function(response) {

						vm.advertData = response.data;
						console.log(vm.advertData);
						checkIfVoted();
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

		/**
		 * check if user is already voted
		 */
		function checkIfVoted() {
			if ($scope.indexCtrl.loggedIn == true
					&& $scope.indexCtrl.authority == 'CUSTOMER') {
				var ratesArray = vm.advertData.rates;

				for (var i = 0; i < ratesArray.length; i++) {
					if (ratesArray[i].user.id == $cookies.getObject('userdata').id) {
						console.log("Already voted");
						vm.voted = true;
						return;
					}

				}
				vm.voted = false;
			} else
				vm.voted = true;
		}

		/**
		 * function for rating
		 */
		function rateMe() {
			var data = {
				"rate" : vm.rate
			};
			console.log(data);

			$http.post('/api/advertiser/rate/' + vm.id, data, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				if (response) {
					fetchData(); // fetch data
					checkIfVoted(); // hide vote div
					toastr.success(response.data.response, "Success");
				}
			}, function(response) {
				toastr.error(response.data.response, 'Error');
			});

		}
		function loadAdvertisment(){
			$http.get('api/advertisement/advert/' + vm.id, {}).then(
					function(response) {

						vm.adverts = response.data;
						console.log(vm.advertisements);
					}, function(response) {
						console.log("Error in fetching");
					})
		}

	}
})();