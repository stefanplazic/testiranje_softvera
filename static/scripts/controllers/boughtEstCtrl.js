/*CONTROLLER FOR BOUGHT AND SOLD ESTATES*/

(function () {
    angular.module("myApp")
        .controller('BoughtEstateController', BoughtEstateController);

    //login page controller
    function BoughtEstateController($http, $scope, $window, $cookies) {

        var vm = this;
        vm.perPage = 1;//size of page
        vm.pages = [];
        vm.getAdvert = getAdvert;
		if ($cookies.getObject('userdata') === undefined)
			$window.location="#/login";

		
		$scope.indexCtrl.loggedIn = true;
		vm.userData = $cookies.getObject('userdata');
		//load first 10 estates
		$http.get("/api/customer/myEstates/size", {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
			var pageNum = Math.floor((parseInt(response.data.response) + vm.perPage -1) / vm.perPage) ;
			for(var i=1;i <= pageNum; i++)
				vm.pages.push(i);
			getAdvert(0);//call the function
		}, function(error) {
			// log error response and maybe send it to
			// error monitor app
			console.error("Error ocurred: " + response.status);
		});
		
		/**
		 * get advertisments for given page number
		 * pNum - int
		 */
		function getAdvert(pNum){
			var pageRequest = {"page": pNum, "count": vm.perPage};
			 $http.post("/api/customer/myEstates", pageRequest, { headers: { 'X-Auth-Token': $cookies.get("token") } })
			 	.then(function (response){
			 		vm.myEstate = response.data;//list of my estates
			 		console.log(vm.myEstate);
			 	});
		}
		
		
    }
    
})();