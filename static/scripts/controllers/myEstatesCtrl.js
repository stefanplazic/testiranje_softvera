(function(){
	angular.module("myApp")
	    .controller('myEstatesController', myEstatesController);

    function myEstatesController($http, $scope,$cookies){
		var vm = this;
		vm.estate={};
		vm.estate.images=[]

		

		$http.get('api/estate/user',{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){
			vm.estates=response.data;
		
		})


		vm.a=function(id){
			$window.location='#/estate/'+id
		}

		vm.addEstate=function(){
			alert(JSON.stringify(vm.estate))





			
			$http.post('api/estate/add',vm.estate,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){

				alert(JSON.stringify(response.data.response))
			})

		}



	}

})();