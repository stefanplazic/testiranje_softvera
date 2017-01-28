(function () {
    angular.module("myApp")
        .controller('viewAdvertController', viewAdvertController);

    //all data about advertisement
    function viewAdvertController($http, $scope, $window, $routeParams, $cookies, googleMap, Lightbox) {

        var vm = this;
        vm.id = $routeParams.id;
        vm.isLogged = $scope.indexCtrl.loggedIn;
        vm.userData = $cookies.getObject('userdata');
        vm.openLightBoxModal = openLightBoxModal;
        vm.rate = 0;
		vm.max = 5;
		vm.voted = true;
		vm.countRate = countRate;
		vm.rateMe = rateMe;
        getData();

        function getData(){
        	var userDat = vm.isLogged == true? { headers: { 'X-Auth-Token': $cookies.get("token")}}: {};
        	$http.get('api/advertisement/' + vm.id, userDat).then(function(response) {
						vm.data = response.data;
						console.log(vm.data);
						initMap();
						checkIfVoted();
        	}, function(response) {
						console.log("Error in fetching");
					});
        }
        
        function initMap(){
        	if(vm.isLogged)
        		googleMap.showMap(document.getElementById('map2'), vm.data.estate.address + " " + vm.data.estate.city);
		}
        
        function openLightBoxModal (index){
        	console.log("Modal box");
        	Lightbox.openModal(vm.data.estate.images, index);
        }
        
        function checkIfVoted() {
			if ($scope.indexCtrl.loggedIn == true
					&& $scope.indexCtrl.authority == 'CUSTOMER') {
				var ratesArray = vm.data.estate.rates;

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
		 * using rate argument - calculates how many times myrate appear in
		 * vm.advertData.rates array
		 */
		function countRate(myrate) {
			if (vm.data != null) {
				var ratesArray = vm.data.estate.rates
				var count = 0;

				for (var i = 0; i < ratesArray.length; i++) {
					if (ratesArray[i].rate == myrate)
						count++; // increment by one
				}

				return count;
			}
		}
		/**
		 * function for rating
		 */
		function rateMe() {
			var data = {
				"rate" : vm.rate
			};
			console.log(data);

			$http.post('api/estate/rate/' + vm.id, data, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				if (response) {
					getData(); // fetch data
					checkIfVoted(); // hide vote div
					toastr.success(response.data.response, "Success");
				}
			}, function(response) {
				toastr.error(response.data.response, 'Error');
			});

		}
       
    }
})();