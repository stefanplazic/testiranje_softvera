(function() {
	angular.module("myApp").controller('indexController', indexController);

	// controller bound to application body, parent controller to all others
	function indexController($cookies, $http, $window) {

		var vm = this;
		// is user logged in
		
		vm.loggedIn = false;
		// login and logout methods
		vm.logout = logout;
		vm.login = login;
		checkIfLogged();

		// method for deleting user data - cookies
		function logout() {
			vm.loggedIn = false;
			var cookies = $cookies.getAll();
			for ( var x in cookies) {
				$cookies.remove(x);
			}
		}
		;

		// retrieving user token and saving it to cookie
		function login(userData) {
			$http.post('/api/users/login', userData).then(function(response) {
				// save user token to cookie
				$cookies.put("token", response.data.response);
				vm.loggedIn = true;
				//getUserData();
				$http.get("/api/users/data", {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
					// if status is ok - save user data to cookie
					$cookies.putObject('userdata', response.data);
					vm.authority = response.data.authority;//set user role to scope
					console.log(response.data);
					$window.location = "#/search";
				}, function(error) {
					// log error response and maybe send it to
					// error monitor app
					console.error("Error ocurred: " + response.status);
				});

			}, function(response) {
				alert(response.data.response);
				console.log("Wrong username and password combination");
			});
		}
		;
		
		//get if there is user cookie, if so - redirect user to profile page (#profile)
		function checkIfLogged(){
			if($cookies.get("token") != undefined){
				console.log("IF Logged");
				vm.loggedIn = true;
				$window.location = "#/search";
			}
		}
		
	}

}


)();