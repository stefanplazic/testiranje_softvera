(function() {
	angular.module("myApp").controller('viewAdvertController',
			viewAdvertController);

	// all data about advertisement
	function viewAdvertController($http, $scope, $window, $routeParams,
			$cookies, googleMap) {

		var vm = this;
		vm.id = $routeParams.id;
		vm.isLogged = $scope.indexCtrl.loggedIn;
		vm.userData = $cookies.getObject('userdata');
		vm.sendReport = sendReport;
		vm.disableReport = disableReport;
		vm.checkIfReported = checkIfReported;
		
		getData();
		
		function getData() {
			var userDat = vm.isLogged == true ? {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			} : {};
			$http.get('api/advertisement/' + vm.id, userDat).then(
				function(response) {
					vm.data = response.data;
					console.log(vm.data);
					initMap();
					checkIfReported();
				}, 
				function(response) {
					console.log("Error in fetching");
				}
			);
		}

		function initMap() {
			if (vm.isLogged)
				googleMap.showMap(document.getElementById('map2'),
						vm.data.estate.address + " " + vm.data.estate.city);
		}

		function sendReport(msg) {

			$http.post("/api/users/report/" + vm.data.id, $("#message").val(), {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				toastr.success("Advertisement Report successfully sent to a Moderator for a review.");
				vm.disableReport();
			}, function(error) {
				// log error response and maybe send it to
				// error monitor app
				console.error("Error ocurred: " + error);
			});
		}
		
		function checkIfReported() {
			
			$http.post("/api/users/ifreported/" + vm.data.id, {}, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				if(response.data.reported) {
					vm.disableReport();
				}
			});
		}
		
		function disableReport() {
			vm.disabled = true;
			$("#flagButton").attr("title", "Your Report has already been submited, please wait for a Moderator's response!");

		}

	}
})();