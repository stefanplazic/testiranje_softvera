/*CONTROLLER FOR BOUGHT AND SOLD ESTATES*/

(function () {
    angular.module("myApp")
        .controller('BoughtEstateController', BoughtEstateController);

    //login page controller
    function BoughtEstateController($http, $scope, $window, $cookies) {

        var vm = this;
        vm.perPage = 2;//size of page
        vm.pages = [];
		if ($cookies.getObject('userdata') === undefined)
			$window.location="#/login";

		
		$scope.indexCtrl.loggedIn = true;
		vm.userData = $cookies.getObject('userdata');
		//load first 10 estates
		$http.get("/api/customer/myEstates/size", {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
			var pageNum = Math.floor((parseInt(response.data.response) + vm.perPage -1) / vm.perPage) ;
			for(var i=1;i <= pageNum; i++)
				vm.pages.push(i);
			console.log(vm.pages)
		}, function(error) {
			// log error response and maybe send it to
			// error monitor app
			console.error("Error ocurred: " + response.status);
		});
    }
    
})();