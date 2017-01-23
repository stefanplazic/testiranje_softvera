(function() {
	angular.module("myApp").controller('estateController', estateController);

	
	function estateController($routeParams,$cookies,$http) {
		var vm=this;
		vm.estate;
		vm.rate;

		$http.get('api/estate/'+$routeParams.id,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){
			
			vm.estate=response.data;
		})

		vm.rateEstate=function(){

			$http.post('api/estate/rate/'+$routeParams.id,{rate:vm.rate}
				,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){

					alert(JSON.stringify(response.data))
				})
		}

	}





})();