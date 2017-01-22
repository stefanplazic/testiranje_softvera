(function () {
    angular.module("myApp")
        .controller('loginController', loginController);

    //login page controller
    function loginController($http, $scope) {

        var vm = this;
        vm.login = login;

        //login method, takes form data (username and password) and calls login method from parent Controller
        function login() {
        	console.log(vm.id+" and "+vm.pass);
            var userData =  { "username": vm.id, "password": vm.pass };
            $scope.indexCtrl.login(userData);
        }
    }
})();