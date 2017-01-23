(function() {
	angular.module("myApp").controller('estateController', estateController);

	
	function estateController($routeParams,$cookies,$http) {
		var vm=this;
		vm.estate;

		$http.get('api/estate/'+$routeParams.id,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){
			console.log(JSON.stringify(response.data))
			vm.estate=response.data;
		})


	}





})();