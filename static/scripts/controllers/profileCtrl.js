(function () {
    angular.module("myApp")
        .controller('profileController', profileController);

    //login page controller
    function profileController($http, $scope, $cookies, $window) {

        var vm = this;
        //redirect user to login page if they are not login in
        if($scope.indexCtrl.loggedIn == false)
        	$window.location = "#/login";
        
        
    }
})();