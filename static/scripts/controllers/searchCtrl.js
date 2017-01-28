(function () {
	angular.module("myApp").controller('searchController', searchController);
	
	function searchController($scope) {
		
		var vm = this;

		$scope.indexCtrl.countPages();
		$scope.indexCtrl.getAdvert(0);
	}

})();