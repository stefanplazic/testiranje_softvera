angular
  .module('myApp', [
    'ngResource',
    'ngRoute',
    'ngCookies',
    'restangular',
    'ui.bootstrap',
    'lodash'
  ])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: '/views/login.html',
        controller: "loginController",
        controllerAs: "loginCtrl"
        
      })
      .when('/profile',{
    	  templateUrl: '/views/profile.html',
    	  controller: "profileController",
    	  controllerAs: "profileCtrl"
      })
      .when('/register',{
    	  templateUrl: '/views/registration.html',
    	  controller:  'registrationController',
    	  controllerAs:'registerCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  }])
  .run(['Restangular', '$log', function(Restangular, $log) {
    Restangular.setBaseUrl("api");
    Restangular.setErrorInterceptor(function(response) {
      if (response.status === 500) {
        $log.info("internal server error");
        return true; 
      }
      return true; 
    });
  }]);