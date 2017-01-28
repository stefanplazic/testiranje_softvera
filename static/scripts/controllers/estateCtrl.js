(function() {
	angular.module("myApp").controller('estateController', estateController);

	
	function estateController($routeParams,$cookies,$http,$window) {
		var vm=this;
		vm.estate;
		vm.rate;
		vm.hasAd=false;

		$http.get('api/estate/'+$routeParams.id,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){
			
			vm.estate=response.data;
		})

		$http.get('api/advertisement/check/'+$routeParams.id).then(function(response){
			if(response.data.response!="false"){
				vm.hasAd=true;
				vm.adId=response.data.response;
			}
		})

		vm.rateEstate=function(){

			$http.post('api/estate/rate/'+$routeParams.id,{rate:vm.rate}
				,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){

					alert(JSON.stringify(response.data))
				})
		}

		vm.createAd=function(){
			a=confirm("create advert for this estate?/n ")

			if(a){
				pub=new Date()
				exp=new Date()
				exp.setMonth(pub.getMonth()+1)

				
				$http.post('api/advertisement/add/'+$routeParams.id,{publicationDate:pub,expiryDate:exp}
					,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){

						alert("advertisement created");
					})
				
			}
		}

		vm.goToAd=function(){
			$window.location='#/advertisement'+vm.adId;
		}
	}





})();