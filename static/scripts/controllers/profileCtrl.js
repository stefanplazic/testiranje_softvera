(function() {
	angular.module("myApp").controller('profileController', profileController);

	// login page controller
	function profileController($http, $scope, $cookies, $window) {

		var vm = this;

		if ($cookies.getObject('userdata') != undefined) {

			$scope.indexCtrl.loggedIn = true;
			vm.userData = $cookies.getObject('userdata');
			console.log(vm.userData);
		}

	}
})();