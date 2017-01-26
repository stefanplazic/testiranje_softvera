(function () {
    angular.module("myApp")
        .controller('registrationController', registrationController);

    //register page controller
    function registrationController($http, $scope, $window) {

        var vm = this;
        vm.register = register;
        vm.type = "advertiser";
        
        //method for user registration
        function register () {
        	if(vm.pass != vm.repeatpass){
        		alert("Passwords must match!");
        		return;
        	}
            var userData = {
                "username": vm.username, "password": vm.pass, "email": vm.email,
                "firstName": vm.firstName, "lastName": vm.lastName
            };
            //get radio option
            
            $http.post('/api/users/register/'+vm.type, userData).then(function (response) {
                if (response) {
                    //$scope.indexCtrl.login(userData);
                		alert(response.data.response);
                		$window.location="#/login";
                }
            },function(response){
            	alert(response.data.response);
            });
        }
    }
})();