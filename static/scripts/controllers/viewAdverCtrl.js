(function () {
    angular.module("myApp")
        .controller('viewAdvertController', viewAdvertController);

    //all data about advertisement
    function viewAdvertController($http, $scope, $window, $routeParams, $cookies, googleMap) {

        var vm = this;
        vm.id = $routeParams.id;
        vm.isLogged = $scope.indexCtrl.loggedIn;
        vm.formatDate = formatDate;
        vm.userData = $cookies.getObject('userdata');
        getData();

        function getData(){
        	var userDat = vm.isLogged == true? { headers: { 'X-Auth-Token': $cookies.get("token")}}: {};
        	$http.get('api/advertisement/' + vm.id, userDat).then(function(response) {
						vm.data = response.data;
						console.log(vm.data);
						initMap();
        	}, function(response) {
						console.log("Error in fetching");
					});
        }
        
        /**
         * function for showing date in nice format : day/month/year
         */
        function formatDate(myDate){
			var date = new Date(myDate);
			return date.getUTCDate()+"/"+ date.getUTCMonth()+1 +"/"+date.getFullYear();
		}
        
        function initMap(){
        	if(vm.isLogged)
        		googleMap.showMap(document.getElementById('map2'), vm.data.estate.address);
		}
       
    }
})();