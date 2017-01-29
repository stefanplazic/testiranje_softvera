(function() {
	angular.module("myApp").controller('registrationController',
			registrationController);

	// register page controller
	function registrationController($http, $scope, $window, errorService) {

		var vm = this;
		vm.register = register;
		vm.type = "advertiser";

		// method for user registration
		function register() {
			if (vm.pass != vm.repeatpass) {
				toastr.error("Passwords must match!", 'Error');
				return;
			}
			var userData = {
				"username" : vm.username,
				"password" : vm.pass,
				"email" : vm.email,
				"firstName" : vm.firstName,
				"lastName" : vm.lastName
			};
			// get radio option

			$http.post('/api/users/register/' + vm.type, userData).then(
					function(response) {
						if (response) {
							// $scope.indexCtrl.login(userData);
							toastr.success(response.data.response, "Success");
							$window.location = "#/login";
						}
					}, function(response) {
						toastr.error(response.data.response, 'Error');
						sendErr(response.data.response);
					});
		}

		function sendErr(errorMsg) {
			var event = {
				"stack" : errorMsg,
				"version" : "1.8",
				"fragment" : "user registration"
			};
			errorService.sendEvent(event);
		}
	}
})();