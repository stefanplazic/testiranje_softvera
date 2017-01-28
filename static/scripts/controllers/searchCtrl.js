(function () {
	angular.module("myApp").controller('searchController', searchController);
	
	function searchController($scope, $http, $cookies) {
		
		var vm = this;
		vm.viewAdvert = viewAdvert;	//function used to record one advert view 
		
		$scope.indexCtrl.countPages();
		$scope.indexCtrl.getAdvert(0);
		
		function viewAdvert(id) {
			if ($scope.indexCtrl.loggedIn == true) {
				$http.post("/api/view/"+id, {}, {headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response) {
					
				}, function(error) {
					// log error response and maybe send it to
					// error monitor app
					console.error("Error ocurred: " + error.status);
				});
			}
		}
	}

})();