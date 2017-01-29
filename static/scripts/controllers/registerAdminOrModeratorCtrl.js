(function () {
    angular.module("myApp")
        .controller('registerAdminOrModeratorController', registrationController);

    //register page controller
    function registrationController($http, $scope, $cookies, $window) {

        var vm = this;
        vm.register = register;
        vm.type = "administrator";
        
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
            
            $http.post('/api/administrator/register/'+vm.type, userData, { headers: { 'X-Auth-Token': $cookies.get("token") } })
	            .then(function (response) {
	            	toastr.success(response.data.response, "Success");
	            	if (response) {
	                	$window.location = "#/search";
	                }
	            },function(response) {
	            	toastr.error(response.data.response, 'Error');
                	//alert(response.data.response);
                }
	        );
        }
    }
})();