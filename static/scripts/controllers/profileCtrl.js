(function() {
	angular.module("myApp").controller('profileController', profileController);

	// login page controller
	function profileController($http, $scope, $cookies, $window) {

		var vm = this;
		// redirect user to login page if they are not login in
		if ($cookies.get("token") === undefined)
			$window.location = "#/login";

		/*
		 * if($cookies.getObject('userdata') != undefined){
		 * 
		 * $scope.indexCtrl.loggedIn = true; vm.userData =
		 * $cookies.getObject('userdata'); var some = {"username":"stefan",
		 * "email":"stefi"}; console.log(vm.userData); }
		 */

		// do get request and send token with it
		$http.get("/api/users/data", {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
			// if status is ok - save user data to cookie
			$cookies.putObject('userdata', response.data);
			console.log(response.data);
			vm.userData = $cookies.getObject('userdata');
		}, function(error) {
			// log error response and maybe send it to
			// error monitor app
			console.error("Error ocurred: " + response.status);
		});
	}
})();