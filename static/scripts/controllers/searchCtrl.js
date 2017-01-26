(function () {
	angular.module("myApp").controller('searchController', searchController);
	
	function searchController($cookies, $http, $window, $scope) {
		
		var vm = this;

		$scope.indexCtrl.countPages();
		$scope.indexCtrl.getAdvert(0);
	}

})();